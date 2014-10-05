package com.scimsoft.cowiki;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.scimsoft.cowiki.helpers.Coordinates;
import com.scimsoft.cowiki.helpers.JSONParser;





public class WikiProvider extends Providers{

	WikiProvider(MainActivity mainActivity) {
		super(mainActivity);
		
	}

	JSONParser parser = new JSONParser();
	List<NameValuePair> params =new ArrayList<NameValuePair>(5); ;
	private JSONObject wikiResponse;
	private JSONArray titles;
	
	public List<String> getNearbyWikiEntries(Coordinates coordinates){
		
		 params.add(new BasicNameValuePair("action","query") );
		 params.add(new BasicNameValuePair("list","geosearch") );
		 params.add(new BasicNameValuePair("gsradius","10000") );
		 params.add(new BasicNameValuePair("format","json") );
		String coordinatesParam = coordinates.getLatitude() + "|"+coordinates.getLongtitude();
		 //String coordinatesParam ="37.2141571|-7.4021457";
		 params.add(new BasicNameValuePair("gscoord",coordinatesParam) );
		 
		 
		 
		try {
			wikiResponse = parser.makeHttpRequest("https://nl.wikipedia.org/w/api.php", params);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			JSONObject query = wikiResponse.getJSONObject("query");
			titles = query.getJSONArray("geosearch");
			
			//JSONObject geoLoc = query.getJSONObject("geosearch");
			//titles = results.getJSONArray(1);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String> titleList = new ArrayList<String>();
		for (int i=0; i<titles.length(); i++) {
		    try {
				titleList.add( titles.getJSONObject(i).getString("title") );
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return titleList;
	}
	
}
