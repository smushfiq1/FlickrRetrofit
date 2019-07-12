package com.example.shoobs.flickrretrofit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.shoobs.flickrretrofit.adapter.ImagePagerAdapter;
import com.example.shoobs.flickrretrofit.adapter.NoNetworkAlert;
import com.example.shoobs.flickrretrofit.adapter.ZoomOutPageTransformer;
import com.example.shoobs.flickrretrofit.model.Feed;
import com.example.shoobs.flickrretrofit.network.DataWrapper;
import com.example.shoobs.flickrretrofit.network.FeedViewModel;
import com.example.shoobs.flickrretrofit.network.NetworkCheck;
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;
import com.google.android.material.snackbar.Snackbar;
import com.viewpagerindicator.CirclePageIndicator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;


public class MainActivity extends AppCompatActivity {

	private static final String LOG_TAG = "SHUAIB";
	private SwipeRefreshLayout SwipeRefresh;
	ImagePagerAdapter adapter;
	ViewPager viewPager;
	NetworkCheck networkCheck;
	FeedViewModel feedViewModel;



	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SwipeRefresh = findViewById(R.id.swipeRefresh);
		SwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh () {
				feedViewModel.loadData(MainActivity.this);
			}
		});

		viewPager = findViewById(R.id.view_pager);
		viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
		adapter = new ImagePagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapter);
		CirclePageIndicator mIndicator = findViewById(R.id.indicator);
		mIndicator.setViewPager(viewPager);

		/**
		 * initializing viewModel
		 */
		feedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);
		feedViewModel.getFlickerData().observe(this, new Observer<DataWrapper<Feed>>() {

			@Override
			public void onChanged (final DataWrapper<Feed> dataWrapper) {
				Log.d(LOG_TAG, "onChange");

				switch (dataWrapper.getStatus()) {
					case NONE:
						SwipeRefresh.setRefreshing(false);
						adapter.update(dataWrapper.getData());
						viewPager.setCurrentItem(0);
						Log.d(LOG_TAG, "NONE");
						break;
					case ERROR:
						SwipeRefresh.setRefreshing(false);
						errorData();
						Log.d(LOG_TAG, "ERROR");
						break;
					case LOADING:
						SwipeRefresh.setRefreshing(true);
						Log.d(LOG_TAG, "LOADING");
						break;

				}
			}
		});

		/**
		 * Checks to see if the device has network to load the data or settings to connect to a network
		 */
		networkCheck = new NetworkCheck(this);
		if (networkCheck.isOnline()) {
			feedViewModel.initialize();
			feedViewModel.loadData(this);
		} else {
			settingsDialog();
		}
	}



	/**
	 * Snackbar message loads when the dataWrapper status is ERROR
	 */
	private void errorData () {
		Snackbar snackbar = Snackbar.make(SwipeRefresh, "Error Loading", Snackbar.LENGTH_LONG);
		snackbar.show();

	}



	/**
	 * Loads AlertDialog if there is no network
	 */
	private void settingsDialog () {
		DialogFragment dialogFragment = new NoNetworkAlert();
		dialogFragment.show(getSupportFragmentManager(), "network");

	}



	/**
	 * The MenuInflator class allows to inflate actions defined in the library.xml file and adds it to the action bar.
	 */

	@Override
	public boolean onCreateOptionsMenu (Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.library, menu);
		return true;
	}



	@Override
	public boolean onOptionsItemSelected (MenuItem item) {
		if (item.getItemId() == R.id.license_info) {
			startActivity(new Intent(this, OssLicensesMenuActivity.class));
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

}