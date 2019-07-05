package com.example.shoobs.flickrretrofit.Model;

import android.os.Bundle;
import android.util.Log;

import com.example.shoobs.flickrretrofit.Adapter.ImagePagerAdapter;
import com.example.shoobs.flickrretrofit.R;
import com.example.shoobs.flickrretrofit.Retrofit.Feed;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

	private static final String LOG_TAG = MainActivity.class.getSimpleName();

	private SwipeRefreshLayout SwipeRefresh;



	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Log.e(LOG_TAG, "onCreate()");


		SwipeRefresh = findViewById(R.id.swipeRefresh);
		SwipeRefresh.setOnRefreshListener(this);

		loadData();

	}



	private void loadData () {

		FeedViewModel feedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);
		feedViewModel.getFlickerDatas().observe(this, new Observer<List<Feed>>() {
			@Override
			public void onChanged (final List<Feed> feedList) {

				// update UI
				Log.d(LOG_TAG, "Done with FeedViewModel");
				ViewPager viewPager = findViewById(R.id.view_pager);
				viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

				/**
				 * set adapter with the data parsed
				 */
				viewPager.setAdapter(new ImagePagerAdapter(getSupportFragmentManager(), feedList));
				CirclePageIndicator mIndicator = findViewById(R.id.indicator);
				mIndicator.setViewPager(viewPager);

			}
		});

	}



	/**
	 * On down swipe, it executes the AsyncTask to parse JSON
	 */
	public void swipeRefresh () {


		ViewPager viewPager = findViewById(R.id.view_pager);
		/**
		 * clear adapter if it is not empty
		 */
		((ImagePagerAdapter) viewPager.getAdapter()).clear();
		viewPager.setAdapter(null);

		loadData();

	}



	@Override
	public void onRefresh () {
		swipeRefresh();
		SwipeRefresh.setRefreshing(false);

	}

}







