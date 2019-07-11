package com.example.shoobs.flickrretrofit.network;


import com.example.shoobs.flickrretrofit.model.Feed;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class FlickerData {

	@SerializedName("items")
	@Expose

	private ArrayList<Feed> items;



	public ArrayList<Feed> getItems () {
		return items;
	}



	@Override
	public String toString () {
		return "FlickerData{" +
				", items=" + items +
				'}';
	}
}

