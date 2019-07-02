package com.example.shoobs.flickrretrofit;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

public class ImagePagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;
    private FragmentManager fm;
    private Media media;


    ImagePagerAdapter(FragmentManager fm, List<Item> result) {
        super(fm);
        fragments = new ArrayList<>();

        //adds a fragment for each result with the m(link) and title
        for (Item flickerData : result) {
//            flickerData.getMedia().getM();
            fragments.add(FlickrItemFragment.getFragment(flickerData.getMedia().getM(), flickerData.getTitle()));
        }
        this.fm = fm;
    }

    //get the position of the image item
    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    //get the number of the views by getting the size of the image list
    @Override
    public int getCount() {
        return fragments.size();
    }

    //called from postExecute to begin the removal of old data if the adapter is not empty
    void clear() {
        for (Fragment fragment : fragments) {
            fm.beginTransaction()
                    .remove(fragment)
                    .commitNowAllowingStateLoss();
        }
        fragments.clear();
    }


}