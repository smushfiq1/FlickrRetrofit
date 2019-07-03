package com.example.shoobs.flickrretrofit.Model;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.shoobs.flickrretrofit.Adapter.ImagePagerAdapter;
import com.example.shoobs.flickrretrofit.R;
import com.example.shoobs.flickrretrofit.Retrofit.Feed;
import com.example.shoobs.flickrretrofit.Retrofit.FlickerData;
import com.example.shoobs.flickrretrofit.Retrofit.FlickrApi;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

	private static final String LOG_TAG = MainActivity.class.getSimpleName();

	String BASE_URL = "https://api.flickr.com/services/feeds/";

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

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		FlickrApi flickrApi = retrofit.create(FlickrApi.class);
		Call<FlickerData> call = flickrApi.getFlickerDatas();
		call.enqueue(new Callback<FlickerData>() {

			@Override
			public void onResponse (Call<FlickerData> call, Response<FlickerData> response) {
				Log.d(LOG_TAG, "onResponse: Server Response: " + response.toString());
				Log.d(LOG_TAG, "onResponse: received information: " + response.body().toString());

				showData(response.body().getItems());
			}



			@Override
			public void onFailure (Call<FlickerData> call, Throwable t) {
				Log.e(LOG_TAG, "onFailure: Something went wrong: " + t.getMessage());
				Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

			}
		});
	}



	private void showData (List<Feed> result) {

		ViewPager viewPager = findViewById(R.id.view_pager);
		viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

		/**
		 * set adapter with the data parsed
		 */
		viewPager.setAdapter(new ImagePagerAdapter(getSupportFragmentManager(), result));
		CirclePageIndicator mIndicator = findViewById(R.id.indicator);
		mIndicator.setViewPager(viewPager);

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







