package com.example.shoobs.flickrretrofit;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    String BASE_URL = "https://api.flickr.com/services/feeds/";

    private SwipeRefreshLayout SwipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e(LOG_TAG, "onCreate()");


        SwipeRefresh = findViewById(R.id.swipeRefresh);
        SwipeRefresh.setOnRefreshListener(this);

        loadData();


    }

private void loadData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FlickrApi flickrApi = retrofit.create(FlickrApi.class);
        Call<FlickerData> call = flickrApi.getFlickerDatas();
        call.enqueue(new Callback<FlickerData>() {

                         @Override
                         public void onResponse(Call<FlickerData> call, Response<FlickerData> response) {
                             Log.d(LOG_TAG, "onResponse: Server Response: " + response.toString());
                             Log.d(LOG_TAG, "onResponse: received information: " + response.body().toString());

                             showData(response.body().getItems());
                         }

                         @Override
                         public void onFailure(Call<FlickerData> call, Throwable t) {
                             Log.e(LOG_TAG, "onFailure: Something went wrong: " + t.getMessage() );
                             Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                         }
    });
}


    private void showData(List<Item> result) {

       ViewPager viewPager = findViewById(R.id.view_pager);

        //set adapter with the data parsed
        viewPager.setAdapter(new ImagePagerAdapter(getSupportFragmentManager(), result));
        CirclePageIndicator mIndicator = findViewById(R.id.indicator);
        mIndicator.setViewPager(viewPager);


    }

    //On down swipe, it executes the AsyncTask to parse JSON
    public void swipeRefresh() {

        ViewPager viewPager = findViewById(R.id.view_pager);
        //clear adapter if it is not empty
        ((ImagePagerAdapter) viewPager.getAdapter()).clear();
            viewPager.setAdapter(null);
            loadData();
    }

    @Override
    public void onRefresh() {
        swipeRefresh();
        SwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.e(LOG_TAG, "onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.e(LOG_TAG, "onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.e(LOG_TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.e(LOG_TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.e(LOG_TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.e(LOG_TAG, "onDestroy()");
    }
}







