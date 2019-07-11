package com.example.shoobs.flickrretrofit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

	private static final String LOG_TAG = "SHUAIB";
	private SwipeRefreshLayout SwipeRefresh;
	ImagePagerAdapter adapter;
	ViewPager viewPager;
	NetworkCheck networkCheck;



	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SwipeRefresh = findViewById(R.id.swipeRefresh);
		SwipeRefresh.setOnRefreshListener(this);
		SwipeRefresh.setRefreshing(true);

		viewPager = findViewById(R.id.view_pager);
		viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
		adapter = new ImagePagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapter);
		CirclePageIndicator mIndicator = findViewById(R.id.indicator);
		mIndicator.setViewPager(viewPager);


		/**
		 * Added a handler here to test the loading indicator the first time the app runs. An IF statement in the handler to test network and then
		 * loadData();
		 */
		networkCheck = new NetworkCheck(this);
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run () {

				if (networkCheck.isOnline()) {
					loadData();
				} else {

					settingsDialog();
				}
			}
		}, 2000);

		//loadData();
	}



	private void loadData () {
		Log.d(LOG_TAG, "loadData");
		FeedViewModel feedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);
		feedViewModel.getFlickerDatas().observe(this, new Observer<DataWrapper<Feed>>() {


			@Override
			public void onChanged (final DataWrapper<Feed> dataWrapper) {
				Log.d(LOG_TAG, "onChange");
//				Handler handler = new Handler();
//				handler.postDelayed(new Runnable() {
//					@Override
//					public void run() {

				switch (dataWrapper.getStatus()) {
					case NONE:
						viewPager.setCurrentItem(0);
						adapter.update(dataWrapper.getData());
						SwipeRefresh.setRefreshing(false);
						break;
					case ERROR:
						SwipeRefresh.setRefreshing(false);
						errorData();
						break;
					case LOADING:
						break;

				}
//
//					}
//				}, 1000);
//				}

			}

		});

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



	@Override
	public void onRefresh () {
		loadData();

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