package com.example.shoobs.flickrretrofit.adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoobs.flickrretrofit.R;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class FlickrItemFragment extends Fragment {

	private static final String LOG_TAG = FlickrItemFragment.class.getName();


	private String imageUrl;
	private String imageTitle;
	private String imageDate;
	private String imageAuthor;
	private String imageTags;



	public static FlickrItemFragment getFragment (String url, String title, String author, String date_taken, String tags) {
		Bundle bundle = new Bundle();
		bundle.putString("DATA.URL", url);
		bundle.putString("DATA.TITLE", title);
		bundle.putString("DATA.AUTHOR", author);
		bundle.putString("DATA.DATE_TAKEN", date_taken);
		bundle.putString("DATA.TAGS", tags);
		FlickrItemFragment flickrItemFragment = new FlickrItemFragment();
		flickrItemFragment.setArguments(bundle);
		return flickrItemFragment;
	}



	@Override
	public void onCreate (@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/**
		 * get the arguments needed for the fragments. If both arguments not true, return blank.
		 */


		if (getArguments() != null && getArguments().containsKey("DATA.URL")) {
			imageUrl = getArguments().getString("DATA.URL");
		} else {
			imageUrl = null;
		}
		if (getArguments() != null && getArguments().containsKey("DATA.TITLE")) {
			imageTitle = getArguments().getString("DATA.TITLE");
		} else {
			imageTitle = "";
		}
		if (getArguments() != null && getArguments().containsKey("DATA.AUTHOR")) {
			imageAuthor = getArguments().getString("DATA.AUTHOR");
		} else {
			imageAuthor = "";
		}
		if (getArguments() != null && getArguments().containsKey("DATA.DATE_TAKEN")) {
			imageDate = getArguments().getString("DATA.DATE_TAKEN");
		} else {
			imageDate = "";
		}

		if (getArguments() != null && getArguments().containsKey("DATA.TAGS")) {
			imageTags = getArguments().getString("DATA.TAGS");
		} else {
			imageTags = "";
		}

	}



	@Nullable
	@Override
	public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_flickr_item, container, false);

	}



	@Override
	public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {

		/**
		 * Log tag to see both the image url and title created during each swipe
		 */
		Log.d(LOG_TAG, "onViewCreated() called with: imageUrl =" + imageUrl + ", imageTitle = " + imageTitle);
		super.onViewCreated(view, savedInstanceState);

		/**
		 * Picasso library to load the images from the url, fit and center it onto the ImageView with Text
		 */
		Picasso.get()
				.load(imageUrl)
				.fit()
				.centerInside()
				.into((ImageView) view.findViewById(R.id.image_view));

		((com.alespero.expandablecardview.ExpandableCardView) view.findViewById(R.id.image_title)).setTitle(imageTitle);

		if (imageAuthor.contains("nobody@flickr.com")) {
			imageAuthor = imageAuthor.replace("nobody@flickr.com", "");
			imageAuthor = imageAuthor.replace("(", "");
			imageAuthor = imageAuthor.replace(")", "");
			imageAuthor = imageAuthor.replace("\"", "");
		}
		((TextView) view.findViewById(R.id.image_author)).setText(imageAuthor);

		String date = imageDate.split("T")[0];
		((TextView) view.findViewById(R.id.image_date)).setText(date);

		((TextView) view.findViewById(R.id.image_tags)).setText(imageTags);

	}

}