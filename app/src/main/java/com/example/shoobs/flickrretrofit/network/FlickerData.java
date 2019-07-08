package com.example.shoobs.flickrretrofit.network;


import com.example.shoobs.flickrretrofit.model.Feed;

import java.util.ArrayList;


public class FlickerData {

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

