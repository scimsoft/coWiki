package com.scimsoft.whatsnear.view;

import android.os.Bundle;

import com.scimsoft.cowiki.R;
import com.scimsoft.whatsnear.adapters.CustomArrayAdapter;

public class TripResultsListViewActivityProvider extends ListViewActivityProvider {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new CustomArrayAdapter(this, list, getResources().getDrawable(R.drawable.ic_trip_logo)));

	}

}
