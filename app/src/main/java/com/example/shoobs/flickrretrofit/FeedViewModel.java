package com.example.shoobs.flickrretrofit;

import android.util.Log;

import com.example.shoobs.flickrretrofit.retrofit.Feed;
import com.example.shoobs.flickrretrofit.retrofit.FlickerData;
import com.example.shoobs.flickrretrofit.retrofit.FlickrApi;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class FeedViewModel extends ViewModel {

	private static final String LOG_TAG = FeedViewModel.class.getSimpleName();

	private MutableLiveData<List<Feed>> feedList;



	LiveData<List<Feed>> getFlickerDatas () {


		feedList = new MutableLiveData<List<Feed>>();

		loadData();


		return feedList;
	}



	private void loadData () {

		final String BASE_URL = "https://api.flickr.com/services/feeds/";
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

				feedList.setValue(response.body().getItems());
			}



			@Override
			public void onFailure (Call<FlickerData> call, Throwable t) {
				Log.e(LOG_TAG, "onFailure: Something went wrong: " + t.getMessage());

			}
		});
	}

}
