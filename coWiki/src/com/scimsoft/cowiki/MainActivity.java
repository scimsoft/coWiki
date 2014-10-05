package com.scimsoft.cowiki;

import java.util.List;
import java.util.Locale;

import android.app.Dialog;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity implements
		TextToSpeech.OnInitListener {

	private Button Start;

	private LocationProvider locationProvider;
	private WikiProvider wikiProvider;
	
	private List<String> results;
	private TextToSpeech tts;

	private SpeechProvider speechProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Start = (Button) findViewById(R.id.start_reg);

		startProviders();

		Start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				noticeNewResults();
			}

		});

	}

	protected void noticeNewResults() {
		results = wikiProvider.getNearbyWikiEntries(locationProvider
				.getCurrentLocationCoordinates());
		showResults(results);
	}

	
	

	private void speakOut(String text) {

		tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}

	private void startProviders() {
		locationProvider = new LocationProvider(this);
		wikiProvider = new WikiProvider();
		speechProvider = new SpeechProvider(this)
		tts = new TextToSpeech(this, this);

	}

	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			// Locale loc = new Locale ("spa", "ESP");
			Locale loc = new Locale("nl", "NL");
			int result = tts.setLanguage(loc);

			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "This Language is not supported");
			} else {
			}

		} else {
			Log.e("TTS", "Initilization Failed!");
		}
	}

	@Override
	public void onDestroy() {
		// Don't forget to shutdown tts!
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
		super.onDestroy();
	}

}
