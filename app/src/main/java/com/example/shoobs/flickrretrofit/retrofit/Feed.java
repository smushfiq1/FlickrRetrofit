package com.example.shoobs.flickrretrofit.retrofit;


public class Feed {

	private String title;

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
