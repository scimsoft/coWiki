package com.scimsoft.whatsnear.view;

import android.os.Bundle;

import com.scimsoft.whatsnear.R;
import com.scimsoft.whatsnear.adapters.CustomArrayAdapter;



public class WikiResultsListViewActivityProvider extends ListViewActivityProvider{

	public WikiResultsListViewActivityProvider() {
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new CustomArrayAdapter(this,list,getResources().getDrawable(R.drawable.ic_wiki_logo)));
		
	}
}