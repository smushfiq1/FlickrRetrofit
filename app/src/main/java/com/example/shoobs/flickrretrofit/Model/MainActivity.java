package com.example.shoobs.flickrretrofit.Model;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.shoobs.flickrretrofit.Adapter.ImagePagerAdapter;
import com.example.shoobs.flickrretrofit.R;
import com.example.shoobs.flickrretrofit.Retrofit.Feed;
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


	private SwipeRefreshLayout SwipeRefresh;



	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);



		SwipeRefresh = findViewById(R.id.swipeRefresh);
		SwipeRefresh.setOnRefreshListener(this);

		loadData();

	}



	private void loadData () {

		FeedViewModel feedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);
		feedViewModel.getFlickerDatas().observe(this, new Observer<List<Feed>>() {
			@Override
			public void onChanged (final List<Feed> feedList) {

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







