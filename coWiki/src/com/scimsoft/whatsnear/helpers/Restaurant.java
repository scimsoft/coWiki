package com.scimsoft.whatsnear.helpers;

import java.util.ArrayList;
import java.util.List;

import android.location.Location;
import android.net.Uri;

public class Restaurant extends NearLocation {
	private long id;
	private Uri url;
	private Uri ratingUri;
	private Uri detailApiUri;
	private ArrayList<String> reviews;
	
	public Restaurant() {
		this.id = 0;
		this.name = "No Restaurant";
	}
	
	public Restaurant(long id, String name, Location location, Uri uri) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.url = uri;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Uri getUrl() {
		return url;
	}
	public void setUrl(Uri url) {
		this.url = url;
	}

	public void addRatingUri(Uri parse) {
		this.ratingUri=parse;		
	}
	public Uri getRatingUri(){
		return this.ratingUri;
	}

	public void addDetailUri(Uri parse) {
		this.detailApiUri = parse;
		
	}
	public Uri getDetailUri(){
		return detailApiUri;
	}

	public void adReviews(ArrayList<String> result) {
		this.reviews = result;
		
	}
	public List<String> getReviews(){
		return reviews;
	}

	public List<String> getDetailTexts(){
		
		return getReviews();
	}
	
	
	
	
	

}
