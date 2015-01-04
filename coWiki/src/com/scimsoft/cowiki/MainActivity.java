package com.scimsoft.cowiki;




import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.scimsoft.cowiki.helpers.Coordinates;

public class MainActivity extends ActionBarActivity implements
		TextToSpeech.OnInitListener {

	private Button searchNearbyButton;

	public LocationProvider locationProvider;
	private WikiProvider wikiProvider;
	private SpeechProvider speechProvider;
	private DialogResultProvider dialogProvider;
	
	 
	 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		searchNearbyButton = (Button) findViewById(R.id.start_reg);

		startProviders();
		if(!locationProvider.isGPSEnabled)locationProvider.showSettingsAlert();
		searchNearbyButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				locationProvider.getLocation();
				noticeNewResults();
			}

		});
		
		ImageButton stopSpeach = (ImageButton) findViewById(R.id.stopSpeach);
		stopSpeach.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				speechProvider.interuptSpeech();
			
			}

		});
		

	}

	protected void noticeNewResults() {
		Coordinates coordinates = new Coordinates(locationProvider.getLatitude(), locationProvider.getLongitude());
		java.util.List<String> results = wikiProvider
				.getNearbyWikiEntries(coordinates);
		
		dialogProvider.showResults(results);
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
		dialogProvider = new DialogResultProvider(this);

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

}
