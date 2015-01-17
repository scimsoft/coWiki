package com.scimsoft.whatsnear.providers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;
import android.net.Uri;

import com.scimsoft.whatsnear.MainActivity;
import com.scimsoft.whatsnear.helpers.Coordinates;
import com.scimsoft.whatsnear.helpers.Restaurant;
import com.scimsoft.whatsnear.helpers.Restaurants;

public class TripAdvisorProvider extends Providers {

	private Restaurants allRestaurants;
	private static String country;

	public TripAdvisorProvider(MainActivity mainActivity) {
		super(mainActivity);
		allRestaurants = new Restaurants();
		country = mainActivity.localeProvider.getLocale().getLanguage();
	}

	
	JSONObject tripResponse = null;

	@SuppressWarnings("unchecked")
	@Override
	public Restaurant getDetails(String title) {
		
		Restaurant selectedRestaurant = allRestaurants.getRestaurant(title);
		ArrayList<String> result = new ArrayList<String>();
		result.add(selectedRestaurant.getName() + "... ");
		String wikiUrl = "http://api.tripadvisor.com/api/partner/2.0/location/"+String.valueOf(selectedRestaurant.getId());
		
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("key", "ce0438de86eb46bf80da67f774ae36b3"));
		params.add(new BasicNameValuePair("lang",country));
		try {			
			
			JSONObject apiResponse = parser.makeHttpRequest(wikiUrl,params);
			JSONArray reviews = apiResponse.getJSONArray("reviews");
			for (int i = 0; i< reviews.length(); i++) {
				JSONObject review = (JSONObject) reviews.get(i);
				String reviewText = review.getString("text");
				String strippedText = new String(reviewText.getBytes("ISO-8859-1"), "UTF-8");
				result.add(strippedText);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		selectedRestaurant.adReviews(result);
		return selectedRestaurant;
		
	}
	public Restaurant getDetailExtract(String name) {
		return allRestaurants.getRestaurant(name);
	}

	public List<String> getNearbyRestaurantsList(Coordinates coordinates) {
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		
		params.add(new BasicNameValuePair("key", "ce0438de86eb46bf80da67f774ae36b3"));
		params.add(new BasicNameValuePair("lang",country));
		try {
			String wikiUrl = "http://api.tripadvisor.com/api/partner/2.0/map/";
			wikiUrl += coordinates.getLatitude() + "," + coordinates.getLongtitude();
			wikiUrl += "/restaurants";
			tripResponse = parser.makeHttpRequest(wikiUrl, params);
			JSONArray restaurantNameArray = tripResponse.getJSONArray("data");
			for (int i = 0; i< restaurantNameArray.length(); i++) {
				JSONObject tripObject = (JSONObject) restaurantNameArray.get(i);
				tripObject.get("latitude");
				Location location = new Location("");
				location.setLatitude(Double.valueOf(tripObject.getString("latitude")));
				location.setLongitude(Double.valueOf(tripObject.getString("longitude")));

				Restaurant restaurant = new Restaurant(Long.valueOf(tripObject.getString("location_id")),
						tripObject.getString("name"),
						location,
						Uri.parse(tripObject.getString("web_url")));
				restaurant.addRatingUri(Uri.parse(tripObject.getString("rating_image_url")));
				restaurant.addDetailUri(Uri.parse(tripObject.getString("api_detail_url")));
				allRestaurants.addRestaurant(restaurant);
				}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return allRestaurants.getNameList();
	}

}
