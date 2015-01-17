package com.scimsoft.whatsnear.view;

import java.util.ArrayList;

import android.R.color;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListView;

import com.scimsoft.cowiki.R;

public class ListViewActivityProvider extends ListActivity {

	protected ArrayList<String> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		list = getIntent().getExtras().getStringArrayList("list");
		getListView().setTextFilterEnabled(true);
		getListView().setBackgroundColor(color.black);
		LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this, R.anim.list_layout_controller);
		getListView().setLayoutAnimation(controller);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("result", l.getItemAtPosition(position).toString());
		intent.putExtras(bundle);
		setResult(RESULT_OK, intent);
		finish();
	}
	

}