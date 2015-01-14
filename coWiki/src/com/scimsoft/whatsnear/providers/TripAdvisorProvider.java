package com.scimsoft.whatsnear.providers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.scimsoft.whatsnear.MainActivity;
import com.scimsoft.whatsnear.helpers.Coordinates;
import com.scimsoft.whatsnear.helpers.JSONParser;

public class TripAdvisorProvider extends Providers {

	public TripAdvisorProvider(MainActivity mainActivity) {
		super(mainActivity);
	}

	JSONParser parser = new JSONParser();

	private JSONObject tripResponse;
	private String titles;

	private JSONArray restaurantNameArray;
	private JSONArray locationIdArray;

	public String getDetailExtract(String title) {
		List<NameValuePair> params = new ArrayList<NameValuePair>(5);
		params.add(new BasicNameValuePair("key", "ce0438de86eb46bf80da67f774ae36b3"));

		try {
			String country = mainActivity.localeProvider.getLocale().getLanguage();
			String wikiUrl = "http://api.tripadvisor.com/api/partner/2.0/map/";

			tripResponse = parser.makeHttpRequest(wikiUrl, params);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String text = "";
		try {
			JSONObject query = tripResponse.getJSONObject("query");
			JSONObject pages = query.getJSONObject("pages");
			@SuppressWarnings("unchecked")
			Iterator<String> iterator = pages.keys();
			String key = "";
			while (iterator.hasNext()) {
				key = iterator.next();
				text = (String) ((JSONObject) pages.get(key)).get("extract");
			}

		} catch (JSONException e) {

			e.printStackTrace();
		}

		return text;
	}

	public List<String> getNearbyRestaurantsList(Coordinates coordinates) {
		List<NameValuePair> params = new ArrayList<NameValuePair>(5);
		params.add(new BasicNameValuePair("key", "ce0438de86eb46bf80da67f774ae36b3"));

		try {
			String wikiUrl = "http://api.tripadvisor.com/api/partner/2.0/map/";
			wikiUrl += coordinates.getLatitude() + "," + coordinates.getLongtitude();
			tripResponse = parser.makeHttpRequest(wikiUrl, params);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			restaurantNameArray = tripResponse.getJSONArray("data");
			locationIdArray= tripResponse.getJSONArray("location_id");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String> restaurantList = new ArrayList<String>();
		for (int i = 0; i < restaurantNameArray.length(); i++) {
			try {
				restaurantList.add(restaurantNameArray.getJSONObject(i).getString("name"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return restaurantList;
	}

}
