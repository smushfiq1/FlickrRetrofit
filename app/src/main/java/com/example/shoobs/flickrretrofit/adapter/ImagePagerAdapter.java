package com.example.shoobs.flickrretrofit.adapter;


import com.example.shoobs.flickrretrofit.model.Feed;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ImagePagerAdapter extends FragmentStatePagerAdapter {

	private List<Feed> data;



	public ImagePagerAdapter (FragmentManager fm) {
		super(fm);
		this.data = new ArrayList<>();
	}



	/**
	 * get the position of the image item
	 */
	@Override
	public Fragment getItem (int i) {
		Feed feed = data.get(i);
		return FlickrItemFragment.getFragment(feed.getMedia().getM(), feed.getTitle(), feed.getAuthor(), feed.getDateTaken(), feed.getTags());
	}



	/**
	 * get the number of the views by getting the size of the image list
	 */

	@Override
	public int getCount () {
		return data.size();
	}



	public void update (final List<Feed> newData) {
		data = newData;
		notifyDataSetChanged();
	}



	@Override
	public int getItemPosition (Object object) {
		// refresh all fragments when data set changed
		return POSITION_NONE;

	}

}



