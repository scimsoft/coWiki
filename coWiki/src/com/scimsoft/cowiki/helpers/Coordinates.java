package com.scimsoft.cowiki.helpers;

public class Coordinates {
	private String latitude;
	private String longtitude;

	public Coordinates(double latitude, double longtitude){
		this.latitude = String.valueOf(latitude);
		this.longtitude = String.valueOf(longtitude);		
	}
	
	public Coordinates() {
		this.latitude = "00";
		this.longtitude="00";
	}

	public String getLatitude(){
		return latitude;
	}
	public String getLongtitude(){
		return longtitude;
	}
}
