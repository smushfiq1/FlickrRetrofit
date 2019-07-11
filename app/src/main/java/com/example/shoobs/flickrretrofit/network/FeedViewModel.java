package com.example.shoobs.flickrretrofit.network;

import android.util.Log;

import com.example.shoobs.flickrretrofit.model.Feed;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.shoobs.flickrretrofit.network.DataWrapper.*;

public class FeedViewModel extends ViewModel {

	private static final String LOG_TAG = FeedViewModel.class.getSimpleName();

	private MutableLiveData<DataWrapper<Feed>> mutableLiveData;



	public LiveData<DataWrapper<Feed>> getFlickerDatas () {

		mutableLiveData = new MutableLiveData<>();
		final DataWrapper<Feed> dataWrapper = new DataWrapper();
		mutableLiveData.setValue(dataWrapper);
		loadData();
		return mutableLiveData;
	}



	private void loadData () {

		final String BASE_URL = "https://api.flickr.com/services/feeds/";
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		FlickrApi flickrApi = retrofit.create(FlickrApi.class);
		Call<FlickerData> call = flickrApi.getFlickerDatas();


		mutableLiveData.getValue().setStatus(Status.LOADING);

		mutableLiveData.setValue(mutableLiveData.getValue());

		call.enqueue(new Callback<FlickerData>() {

			@Override
			public void onResponse (Call<FlickerData> call, Response<FlickerData> response) {

				if (response.isSuccessful()) {

					mutableLiveData.getValue().setData(response.body().getItems());
					mutableLiveData.getValue().setStatus(Status.NONE);
					mutableLiveData.setValue(mutableLiveData.getValue());

				} else {

					mutableLiveData.getValue().setStatus(Status.ERROR);

				}

			}



			@Override
			public void onFailure (Call<FlickerData> call, Throwable t) {
				Log.e(LOG_TAG, "onFailure: Something went wrong: " + t.getMessage());

			}
		});
	}

}
