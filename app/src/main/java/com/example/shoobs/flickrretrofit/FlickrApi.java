package com.example.shoobs.flickrretrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrApi {

    String PATH = "photos_public.gne?format=json&nojsoncallback=1";

    @GET(PATH)
//    Call<List<FlickerData>> getFlickerDatas();
    Call<FlickerData> getFlickerDatas();
}
