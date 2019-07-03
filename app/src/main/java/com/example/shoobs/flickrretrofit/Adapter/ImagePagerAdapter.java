package com.example.shoobs.flickrretrofit.Adapter;


import com.example.shoobs.flickrretrofit.Retrofit.Feed;
import com.example.shoobs.flickrretrofit.Retrofit.Media;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ImagePagerAdapter extends FragmentPagerAdapter {

	private ArrayList<Fragment> fragments;
	private FragmentManager fm;
	private Media media;



	public ImagePagerAdapter (FragmentManager fm, List<Feed> result) {
		super(fm);
		fragments = new ArrayList<>();

		/**
		 * adds a fragment for each result with the m(link) and title
		 */
		for (Feed flickerData : result) {
			fragments.add(FlickrItemFragment.getFragment(flickerData.getMedia().getM(), flickerData.getTitle()));
		}
		this.fm = fm;
	}



	/**
	 * get the position of the image item
	 */
	@Override
	public Fragment getItem (int i) {
		return fragments.get(i);
	}



	/**
	 * get the number of the views by getting the size of the image list
	 */

	@Override
	public int getCount () {
		return fragments.size();
	}



	/**
	 * called from postExecute to begin the removal of old data if the adapter is not empty
	 */

	public void clear () {
		for (Fragment fragment : fragments) {
			fm.beginTransaction()
					.remove(fragment)
					.commitNowAllowingStateLoss();
		}
		fragments.clear();
	}

}