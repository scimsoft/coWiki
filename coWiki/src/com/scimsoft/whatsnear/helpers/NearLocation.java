package com.scimsoft.whatsnear.helpers;

import java.util.List;

import android.location.Location;

public class NearLocation {

	protected String name;
	protected Location location;

	public NearLocation() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public List<String> getDetailTexts(){
		return null;
	}

}