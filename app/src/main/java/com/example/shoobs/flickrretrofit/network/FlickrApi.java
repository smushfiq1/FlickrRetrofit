package com.example.shoobs.flickrretrofit.network;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * This class turns the HTTP API into a java interface
 */

public interface FlickrApi {

	String PATH = "photos_public.gne?format=json&nojsoncallback=1";

	@GET(PATH)
	Call<FlickerData> getFlickerDatas ();
}
