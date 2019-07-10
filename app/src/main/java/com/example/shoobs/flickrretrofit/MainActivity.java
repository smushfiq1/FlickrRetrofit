package com.example.shoobs.flickrretrofit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.shoobs.flickrretrofit.adapter.ImagePagerAdapter;
import com.example.shoobs.flickrretrofit.adapter.ZoomOutPageTransformer;
import com.example.shoobs.flickrretrofit.model.Feed;
import com.example.shoobs.flickrretrofit.network.DataWrapper;
import com.example.shoobs.flickrretrofit.network.FeedViewModel;
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;
import com.viewpagerindicator.CirclePageIndicator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import static com.example.shoobs.flickrretrofit.network.DataWrapper.Status;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

	private static final String LOG_TAG = MainActivity.class.getSimpleName();
	private SwipeRefreshLayout SwipeRefresh;

	ImagePagerAdapter adapter;
	ViewPager viewPager ;

	Status status;




	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SwipeRefresh = findViewById(R.id.swipeRefresh);
		SwipeRefresh.setOnRefreshListener(this);

		viewPager = findViewById(R.id.view_pager);

		viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
		adapter = new ImagePagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapter);

		CirclePageIndicator mIndicator = findViewById(R.id.indicator);
		mIndicator.setViewPager(viewPager);

		loadData();
	}



	private void loadData () {

		FeedViewModel feedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);
		feedViewModel.getFlickerDatas().observe(this, new Observer<DataWrapper<Feed>>() {

			@Override
			public void onChanged (final DataWrapper<Feed> dataWrapper) {

				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {

						switch (dataWrapper.getStatus()){
							case NONE:
								viewPager.setCurrentItem(0);
								adapter.update(dataWrapper.getData());
								SwipeRefresh.setRefreshing(false);
								break;
							case ERROR:
								SwipeRefresh.setRefreshing(false);
								break;
							case LOADING:
								break;

						}

					}
				}, 2000);
//				viewPager.setCurrentItem(0);
//				adapter.update(feedList);
			}
		});




	}



	@Override
	public void onRefresh () {
		loadData();
//		SwipeRefresh.setRefreshing(false);

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