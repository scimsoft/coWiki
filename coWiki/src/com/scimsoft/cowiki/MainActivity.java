package com.scimsoft.cowiki;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.scimsoft.cowiki.helpers.Coordinates;
import com.scimsoft.cowiki.providers.LocationProvider;
import com.scimsoft.cowiki.providers.SpeechProvider;
import com.scimsoft.cowiki.providers.WikiProvider;
import com.scimsoft.view.ListViewActivityProvider;

public class MainActivity extends ActionBarActivity implements TextToSpeech.OnInitListener {

	

	public LocationProvider locationProvider;
	private WikiProvider wikiProvider;
	private SpeechProvider speechProvider;

	public java.util.List<String> results;

	private Animation scale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		startProviders();

		if (!locationProvider.isGPSEnabled) {
			locationProvider.showSettingsAlert();
		}

		scale = AnimationUtils.loadAnimation(this, R.anim.scale_button);

		ImageButton searchNearbyButton = (ImageButton) findViewById(R.id.goRefresh);
		searchNearbyButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				v.startAnimation(scale);
				locationProvider.getLocation();
				speechProvider.interuptSpeech();
				noticeNewResults();
			}

		});

		ImageButton stopSpeach = (ImageButton) findViewById(R.id.stopSpeach);
		stopSpeach.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				v.startAnimation(scale);
				speechProvider.interuptSpeech();

			}

		});
		
		ImageButton goSpeach = (ImageButton) findViewById(R.id.goSpeach);
		goSpeach.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				v.startAnimation(scale);
				locationProvider.getLocation();

			}

		});
	}
	

	public void noticeNewResults() {
		Coordinates coordinates = new Coordinates(locationProvider.getLatitude(), locationProvider.getLongitude());
		java.util.List<String> results = wikiProvider.getNearbyWikiEntries(coordinates);

		// dialogProvider.showResults(results);
		// resultViewProvider.noticeNewResults(results);

		Intent intent = new Intent();
		intent.setClass(MainActivity.this, ListViewActivityProvider.class);
		intent.putStringArrayListExtra("list", (ArrayList<String>) results);
		startActivityForResult(intent, 0);

		speechProvider.speekResults(results);
	}

	public void showDetails(String selected) {
		String extract = wikiProvider.getDetailExtract(selected);
		speechProvider.speekWikiExtract(extract);
	}

	private void startProviders() {
		locationProvider = new LocationProvider(this);
		wikiProvider = new WikiProvider(this);
		speechProvider = new SpeechProvider(this);

	}

	@Override
	public void onInit(int status) {
		speechProvider.onInit(status);
	}

	@Override
	public void onDestroy() {
		speechProvider.onDestroy();
		locationProvider.stopUsingGPS();
	}

	@Override
	public void setTitle(CharSequence title) {
		super.setTitle(title);
		TextView upperText = (TextView) findViewById(R.id.welcome);
		upperText.setText(title);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) {
			return;
		}
		String result = data.getStringExtra("result");
		setTitle(result);
		showDetails(result);
	}

}
