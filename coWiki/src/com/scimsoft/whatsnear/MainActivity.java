package com.scimsoft.whatsnear;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.scimsoft.cowiki.R;
import com.scimsoft.whatsnear.helpers.Coordinates;
import com.scimsoft.whatsnear.helpers.NearLocation;
import com.scimsoft.whatsnear.providers.LocalesProvider;
import com.scimsoft.whatsnear.providers.LocationProvider;
import com.scimsoft.whatsnear.providers.Providers;
import com.scimsoft.whatsnear.providers.SpeechProvider;
import com.scimsoft.whatsnear.providers.TripAdvisorProvider;
import com.scimsoft.whatsnear.providers.WikiProvider;
import com.scimsoft.whatsnear.view.TripResultsListViewActivityProvider;
import com.scimsoft.whatsnear.view.WikiResultsListViewActivityProvider;

public class MainActivity extends Activity implements OnInitListener {

	private static final String PROPERTY_ID = "UA-37086905-2";
	public LocationProvider locationProvider;
	private WikiProvider wikiProvider;
	private TripAdvisorProvider tripProvider;
	private SpeechProvider speechProvider;
	public LocalesProvider localeProvider;

	public java.util.List<String> results;

	public Tracker tracker;
	private ShareActionProvider mShareActionProvider;
	private Intent mShareIntent;

	private String lasteslected;
	private Intent resultViewIntent;
	private Providers lastProvider;
	private NearLocation currentDetailLocation;
	private boolean navigationButtonShown;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy);

		addShareButton();

		addActionButtons();

		startProviders();

		initGoogleTracker();

		if (!locationProvider.isGPSEnabled) {
			locationProvider.showSettingsAlert();
		}

		resultViewIntent = new Intent();

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
				locationProvider.getLocation();
				speechProvider.interuptSpeech();
				wikiProvider.renewAllResults();
				noticeNewWikiResults();
			}

		});

		ImageButton pauseSpeech = (ImageButton) findViewById(R.id.stopSpeach);
		pauseSpeech.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				speechProvider.interuptSpeech();

			}

		});

		ImageButton goSpeach = (ImageButton) findViewById(R.id.goSpeach);
		goSpeach.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDetails(lasteslected);
			}
		});

		ImageButton goRestaurant = (ImageButton) findViewById(R.id.goRestaurant);
		goRestaurant.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				locationProvider.getLocation();
				speechProvider.interuptSpeech();
				noticeNewTripResults();
			}
		});
		ImageButton goWikipedia = (ImageButton) findViewById(R.id.goWiki);
		goWikipedia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				locationProvider.getLocation();
				speechProvider.interuptSpeech();
				noticeNewWikiResults();
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
		lastProvider = wikiProvider;
		if (!isMyServiceRunning()&& results.size()>0) {
			resultViewIntent.setClass(MainActivity.this, WikiResultsListViewActivityProvider.class);
			resultViewIntent.putStringArrayListExtra("list", (ArrayList<String>) results);
			startActivityForResult(resultViewIntent, 0);
			speechProvider.speekResults(results);
			setLocationsViewed();
		}
	}

	public void noticeNewTripResults() {
		Coordinates coordinates = new Coordinates(locationProvider.getLatitude(), locationProvider.getLongitude());
		java.util.List<String> results = tripProvider.getNearbyRestaurantsList(coordinates);
		lastProvider = tripProvider;
		if (!isMyServiceRunning()&& results.size()>0) {

			resultViewIntent.setClass(MainActivity.this, TripResultsListViewActivityProvider.class);
			resultViewIntent.putStringArrayListExtra("list", (ArrayList<String>) results);
			startActivityForResult(resultViewIntent, 0);
			speechProvider.speekResults(results);
		}
	}

	private boolean isMyServiceRunning() {

		return false;
	}

	public void showDetails(String selected) {
		this.lasteslected = selected;
		currentDetailLocation = lastProvider.getDetails(selected);
		setDetailViewImages();

		List<String> extract = currentDetailLocation.getDetailTexts();
		speechProvider.speekStringList(extract);
		tracker.setScreenName("details");
		tracker.send(new HitBuilders.AppViewBuilder().build());
	}

	private void setDetailViewImages() {
		if (!navigationButtonShown) {
			navigationButtonShown = true;
			LinearLayout layout = (LinearLayout) findViewById(R.id.upperButtonLayout);

			ImageButton navigationButton = new ImageButton(this);
			navigationButton.setImageResource(R.drawable.btn_navigate);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			// params.gravity=Gravity.CENTER_VERTICAL;
			params.gravity = Gravity.CENTER_HORIZONTAL;
			navigationButton.setAdjustViewBounds(true);

			navigationButton.setLayoutParams(params);
			int[] attrs = new int[] { android.R.attr.selectableItemBackground };

			TypedArray ta = this.obtainStyledAttributes(attrs);

			Drawable drawableFromTheme = ta.getDrawable(0);

			// Finally, free the resources used by TypedArray
			ta.recycle();
			navigationButton.setBackground(drawableFromTheme);
			navigationButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Location geo = currentDetailLocation.getLocation();
					speechProvider.interuptSpeech();
					Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr="
							+ String.valueOf(geo.getLatitude()).replace(",", ".") + "," + String.valueOf(geo.getLongitude()).replace(",", ".")));
					startActivity(intent);

				}
			});
			layout.addView(navigationButton);

			View view1 = new View(this);
			LinearLayout.LayoutParams paramsView = new LinearLayout.LayoutParams(0, 1);
			view1.setLayoutParams(paramsView);
			layout.addView(view1);
		}

	}

	private void startProviders() {
		localeProvider = new LocalesProvider(this);
		locationProvider = new LocationProvider(this);
		wikiProvider = new WikiProvider(this);
		tripProvider = new TripAdvisorProvider(this);
		speechProvider = new SpeechProvider(this);

	}

	@Override
	public void onInit(int status) {
		speechProvider.onInit(status);
		tracker.setScreenName("mainScreen");
		tracker.send(new HitBuilders.AppViewBuilder().build());
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
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
		if (resultCode == RESULT_OK) {
			
			String result = data.getStringExtra("result");
			setMessageText(result);
			showDetails(result);
			setLocationsViewed();
		}
	}

	private void setLocationsViewed() {
		if(lastProvider.getClass()==WikiProvider.class)
		lastProvider.setListShown();
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
