package com.example.shoobs.flickrretrofit.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Feed {

	@SerializedName("title")
	@Expose
	private String title;

	@SerializedName("media")
	@Expose
	private Media media;



	public String getTitle () {
		return title;
	}



	public Media getMedia () {
		return media;
	}



	@Override
	public String toString () {
		return "Feed{" +
				"title='" + title + '\'' +
				", media=" + media +
				'}';
	}

}
