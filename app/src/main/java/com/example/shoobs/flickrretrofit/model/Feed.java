package com.example.shoobs.flickrretrofit.model;


import androidx.fragment.app.Fragment;

public class Feed extends Fragment {

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
