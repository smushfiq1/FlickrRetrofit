package com.example.shoobs.flickrretrofit;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoadFlickrData {

    private static final String LOG_TAG = LoadFlickrData.class.getSimpleName();

    String BASE_URL = "https://api.flickr.com/services/feeds/";

    public void loadData(final OnFinishedListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FlickrApi flickrApi = retrofit.create(FlickrApi.class);
        Call<FlickerData> call = flickrApi.getFlickerDatas();
//        flickrApi = new FlickrApi();
        call.enqueue(new Callback<FlickerData>() {
            @Override
            public void onResponse(Call<FlickerData> call, Response<FlickerData> response) {
                if (response.isSuccessful()) {
                    Log.e(LOG_TAG, "Code: " + response.code());
                    listener.onFinished(response.body().getItems());
                    Log.e(LOG_TAG, "Got: " + response.body().toString());

//                    List<Item> childrenList = response.body().g
//                    for( int i = 0; i<childrenList.size(); i++){
//                        Log.d(TAG, "onResponse: \n" +
//                                "kind: " + childrenList.get(i).getKind() + "\n" +
//                                "contest_mode: " + childrenList.get(i).getData().getContest_mode() + "\n" +
//                                "subreddit: " + childrenList.get(i).getData().getSubreddit()  + "\n" +
//                                "author: " + childrenList.get(i).getData().getAuthor()  + "\n" +
//                                "-------------------------------------------------------------------------\n\n");
                    return;
                }
                else {
                    Log.e(LOG_TAG, "Code Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<FlickerData> call, Throwable t) {
                Log.e(LOG_TAG, "Code Failure Error: " + t.getMessage());
                //listener.onError();
            }
        });
    }

    public interface OnFinishedListener {
        void onFinished(List<Item> items);


        //void onError();
    }
}

