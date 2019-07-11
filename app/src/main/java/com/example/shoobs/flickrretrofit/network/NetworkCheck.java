package com.example.shoobs.flickrretrofit.network;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * This class does a network connectivity boolean check. Checks whether or not the device is connected to a network
 */

public class NetworkCheck {

	private Activity activity;



	public NetworkCheck (Activity activity) {
		this.activity = activity;

	}



	public boolean isOnline () {
		ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
			return true;
		} else {
			return false;
		}

	}
}
