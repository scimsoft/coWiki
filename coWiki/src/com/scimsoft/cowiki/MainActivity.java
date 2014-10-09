package com.scimsoft.cowiki;

import java.util.List;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity implements
		TextToSpeech.OnInitListener {

	private Button Start;

	private LocationProvider locationProvider;
	private WikiProvider wikiProvider;
	private  SpeechProvider speechProvider;
	private DialogResultProvider dialogProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Start = (Button) findViewById(R.id.start_reg);

		startProviders();

		Start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				locationProvider.updateLocation();
				noticeNewResults();
			}

		});

	}

	protected void noticeNewResults() {
		List<String> results = wikiProvider
				.getNearbyWikiEntries(locationProvider
						.getCurrentLocationCoordinates());
		dialogProvider.showResults(results);
		speechProvider.speekResults(results);
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
	}

}
