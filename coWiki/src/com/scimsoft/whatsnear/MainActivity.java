package com.scimsoft.whatsnear;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import com.scimsoft.whatsnear.helpers.Coordinates;
import com.scimsoft.whatsnear.providers.LocalesProvider;
import com.scimsoft.whatsnear.providers.LocationProvider;
import com.scimsoft.whatsnear.providers.SpeechProvider;
import com.scimsoft.whatsnear.providers.TripAdvisorProvider;
import com.scimsoft.whatsnear.providers.WikiProvider;
import com.scimsoft.whatsnear.view.ListViewActivityProvider;

public class MainActivity extends Activity implements OnInitListener {

	private static final String PROPERTY_ID = "UA-37086905-2";
	public LocationProvider locationProvider;
	private WikiProvider wikiProvider;
	private SpeechProvider speechProvider;
	public LocalesProvider localeProvider;

	public java.util.List<String> results;

	private Animation scale;
	public Tracker tracker;
	private ShareActionProvider mShareActionProvider;
	private Intent mShareIntent;
	private String lasteslected;
	private TripAdvisorProvider tripProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		addShareButton();
		
		addActionButtons();

		startProviders();

		initGoogleTracker();

		if (!locationProvider.isGPSEnabled) {
			locationProvider.showSettingsAlert();
		}

		scale = AnimationUtils.loadAnimation(this, R.anim.scale_button);

		
	}

	private void initGoogleTracker() {
		GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);

		tracker = analytics.newTracker(PROPERTY_ID);
	}

	private void addActionButtons() {
		ImageButton refreshButton = (ImageButton) findViewById(R.id.goRefresh);
		refreshButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				v.startAnimation(scale);
				locationProvider.getLocation();
				speechProvider.interuptSpeech();
				noticeNewTripResults();
			}

		});

		ImageButton pauseSpeech = (ImageButton) findViewById(R.id.stopSpeach);
		pauseSpeech.setOnClickListener(new OnClickListener() {

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
				showDetails(lasteslected);
			}
		});

	}

	private void addShareButton() {
		mShareIntent = new Intent();
		mShareIntent.setAction(Intent.ACTION_SEND);
		mShareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		mShareIntent.setType("text/plain");
		mShareIntent.putExtra(Intent.EXTRA_TEXT, this.getString(R.string.share_text));
	}

	public void noticeNewWikiResults() {
		Coordinates coordinates = new Coordinates(locationProvider.getLatitude(), locationProvider.getLongitude());
		java.util.List<String> results = wikiProvider.getNearbyWikiEntries(coordinates);

		if (!isMyServiceRunning()) {
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, ListViewActivityProvider.class);
			intent.putStringArrayListExtra("list", (ArrayList<String>) results);
			startActivityForResult(intent, 0);
			speechProvider.speekResults(results);
		}
	}
	public void noticeNewTripResults() {
		Coordinates coordinates = new Coordinates(locationProvider.getLatitude(), locationProvider.getLongitude());
		java.util.List<String> results = tripProvider.getNearbyWikiEntries(coordinates);

		if (!isMyServiceRunning()) {
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, ListViewActivityProvider.class);
			intent.putStringArrayListExtra("list", (ArrayList<String>) results);
			startActivityForResult(intent, 0);
			speechProvider.speekResults(results);
		}
	}

	private boolean isMyServiceRunning() {
		ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if ("com.scimsoft.view.ListViewActivityProvider.class".equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	public void showDetails(String selected) {
		this.lasteslected = selected;
		String extract = wikiProvider.getDetailExtract(selected);
		speechProvider.speekWikiExtract(extract);
		tracker.setScreenName("details");
		tracker.send(new HitBuilders.AppViewBuilder().build());
	}

	private void startProviders() {
		locationProvider = new LocationProvider(this);
		wikiProvider = new WikiProvider(this);
		tripProvider = new TripAdvisorProvider(this);
		speechProvider = new SpeechProvider(this);
		localeProvider = new LocalesProvider(this);

	}

	@Override
	public void onInit(int status) {
		speechProvider.onInit(status);
		tracker.setScreenName("mainScreen");
		tracker.send(new HitBuilders.AppViewBuilder().build());
	}

	@Override
	public void onDestroy() {
		speechProvider.onDestroy();
		locationProvider.stopUsingGPS();
	}

	public void setMessageText(CharSequence title) {
		TextView upperText = (TextView) findViewById(R.id.welcome);
		upperText.setText(title);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) {
			return;
		}
		String result = data.getStringExtra("result");
		setMessageText(result);
		showDetails(result);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		// Find the MenuItem that we know has the ShareActionProvider
		MenuItem item = menu.findItem(R.id.menu_item_share);

		// Get its ShareActionProvider
		mShareActionProvider = (ShareActionProvider) item.getActionProvider();

		// Connect the dots: give the ShareActionProvider its Share Intent
		if (mShareActionProvider != null) {
			mShareActionProvider.setShareIntent(mShareIntent);
		}

		// Return true so Android will know we want to display the menu
		return true;
	}

}