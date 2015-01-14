package com.scimsoft.whatsnear.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.scimsoft.whatsnear.R;
import com.scimsoft.whatsnear.providers.Providers;
import com.scimsoft.whatsnear.view.WikiResultsListViewActivityProvider;

public class CustomArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final ArrayList<String> values;
	private Drawable drawableLogo;

	public CustomArrayAdapter(Context context, ArrayList<String> values, Drawable drawable) {
		super(context, R.layout.customsimple_list, values);
		this.context = context;
		this.values = values;
		this.drawableLogo=drawable;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.customsimple_list, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		textView.setText(values.get(position));

		
		imageView.setImageDrawable(drawableLogo);
	

	return rowView;
	}
}
