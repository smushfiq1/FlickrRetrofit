package com.example.shoobs.flickrretrofit.adapter;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import com.example.shoobs.flickrretrofit.R;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

/**
 * An Alert Dialog that shows up when the device is not connected to the internet.
 */

public class NoNetworkAlert extends DialogFragment {

	@Override
	public Dialog onCreateDialog (Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.no_connection_title);
		builder.setMessage(R.string.no_connection)
				.setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
					public void onClick (DialogInterface dialog, int id) {
						/**
						 * When settings is clicked, the app goes to the device network settings page
						 */
						startActivityForResult(new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS), 0);
					}
				})
				.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					public void onClick (DialogInterface dialog, int id) {
						/**
						 * When Cancel is clicked, the app will close.
						 */
						Intent intent = new Intent(Intent.ACTION_MAIN);
						intent.addCategory(Intent.CATEGORY_HOME);
						startActivity(intent);

					}
				});

		return builder.create();
	}
}
