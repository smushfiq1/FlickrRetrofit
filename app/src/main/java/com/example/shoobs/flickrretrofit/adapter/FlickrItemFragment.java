package com.example.shoobs.flickrretrofit.adapter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.shoobs.flickrretrofit.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class FlickrItemFragment extends Fragment {

	private static final String LOG_TAG = "THIS";


	private String imageUrl;
	private String imageTitle;
	private String imageDate;
	private String imageAuthor;
	private String imageAuthor_Id;
	private String imageTags;



	public static FlickrItemFragment getFragment (String url, String title, String author, String author_id, String date_taken, String tags) {
		Bundle bundle = new Bundle();
		bundle.putString("DATA.URL", url);
		bundle.putString("DATA.TITLE", title);
		bundle.putString("DATA.AUTHOR", author);
		bundle.putString("DATA.AUTHOR_ID", author_id);
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

		if (getArguments() != null && getArguments().containsKey("DATA.AUTHOR_ID")) {
			imageAuthor_Id = getArguments().getString("DATA.AUTHOR_ID");
		} else {
			imageAuthor_Id = "";
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
		 * Changed the ending of the URL from _m to _b to fill screen.
		 */
		if (imageUrl.contains("https://live.staticflickr.com/")) {
			imageUrl = imageUrl.replace("_m", "_b");

		}

		/**
		 * Picasso library to load the images from the url and load onto the ImageView with Text
		 */
		Picasso.get()
				.load(imageUrl)
				.into((ImageView) view.findViewById(R.id.image_view));

		((com.alespero.expandablecardview.ExpandableCardView) view.findViewById(R.id.image_title)).setTitle(imageTitle);


		/**
		 * Removes the unnecessary text from the Author name
		 */
		if (imageAuthor.contains("nobody@flickr.com")) {
			imageAuthor = imageAuthor.replace("nobody@flickr.com", "");
			imageAuthor = imageAuthor.replace("(", "");
			imageAuthor = imageAuthor.replace(")", "");
			imageAuthor = imageAuthor.replace("\"", "");
		}


		/**
		 * Set the Author name in the expandable chip
		 */
		((com.google.android.material.chip.Chip) view.findViewById(R.id.image_author_chips)).setText(imageAuthor);


		/**
		 * Set the Author name clickable to go to the Authors Flickr page
		 */
		Chip chip = view.findViewById(R.id.image_author_chips);
		chip.setOnClickListener(view1 -> {

			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flickr.com/photos/" + imageAuthor_Id));
			startActivity(intent);
		});


		/**
		 * Set the date taken, deleteing everything after the "T" in the date string
		 */
		String date = imageDate.split("T")[0];
		((Chip) view.findViewById(R.id.image_date_chips)).setText(date);


		/**
		 * Set the tag for the image
		 */
		ChipGroup chipGroup = view.findViewById(R.id.chipgroup);
		String[] tags = imageTags.split(" ");
		for (String tag : tags) {
			Chip chip_tag = new Chip(Objects.requireNonNull(this.getContext()));
			chip_tag.setText(tag);
			chip_tag.setChipBackgroundColorResource(R.color.colorSecondaryText);
			chipGroup.addView(chip_tag);
		}

	}
}