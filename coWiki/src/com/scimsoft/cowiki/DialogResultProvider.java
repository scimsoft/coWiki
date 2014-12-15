package com.scimsoft.cowiki;

import java.util.List;

import android.app.Dialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DialogResultProvider extends Providers {
	

	private Dialog match_text_dialog;
	private ListView textlist;
	DialogResultProvider(MainActivity mainActivity) {
		super(mainActivity);
	}
	void showResults(List<String> results) {
		match_text_dialog = new Dialog(super.mainActivity);
		match_text_dialog.setContentView(R.layout.dialog_matches_frag);
		match_text_dialog.setTitle("Select Matching Text");
		textlist = (ListView) match_text_dialog.findViewById(R.id.list);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(super.mainActivity,
				android.R.layout.simple_list_item_1, results);
		textlist.setAdapter(adapter);
		
		textlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String selected = textlist.getItemAtPosition(position).toString();
				//smatch_text_dialogpeakOut(results.get(position));
				match_text_dialog.hide();
				mainActivity.showDetails(selected);
			}

			
		});
		
		match_text_dialog.show();
	}
	
}
