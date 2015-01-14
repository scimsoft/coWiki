package com.scimsoft.whatsnear.view;

import com.google.android.gms.drive.internal.GetDriveIdFromUniqueIdentifierRequest;
import com.scimsoft.whatsnear.R;
import com.scimsoft.whatsnear.adapters.CustomArrayAdapter;

import android.R.color;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class WikiResultsListViewActivityProvider extends ListViewActivityProvider{
	

	public WikiResultsListViewActivityProvider() {
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		list = getIntent().getExtras().getStringArrayList("list");		
		setListAdapter(new CustomArrayAdapter(this,list,getResources().getDrawable(R.drawable.ic_wiki_logo)));
		getListView().setTextFilterEnabled(true);
		getListView().setBackgroundColor(color.black);
		LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this, R.anim.list_layout_controller);
		getListView().setLayoutAnimation(controller);	
		
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		Intent intent = new Intent();
		Bundle bundle = new Bundle();

		bundle.putString("result", l.getItemAtPosition(position).toString());
		intent.putExtras(bundle);
		setResult(RESULT_OK, intent);
		finish();
	}
}