package com.scimsoft.whatsnear.helpers;

import java.util.ArrayList;
import java.util.List;

import android.location.Location;
import android.net.Uri;

public class Restaurants {
	private ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
	
	public void addRestaurant(Restaurant restaurant){
		if(getRestaurant(restaurant.getId()).getId()==0)
		restaurants.add(restaurant);
	}
	public void addRestaurantData(long id, String name, Location location, Uri url){
		restaurants.add(new Restaurant(id, name, location, url));
	}
	public Restaurant getRestaurant(long id){
		for(Restaurant res: restaurants){
			if(res.getId()== id){
				return res;
			}
		}
		return new Restaurant();
	}
	public List<String> getNameList() {
		ArrayList<String> nameList = new ArrayList<String>();
		for(Restaurant res: restaurants){
			nameList.add(res.getName());
		}
		return nameList;
	}
	public Restaurant getRestaurant(String name) {
		for(Restaurant res: restaurants){
			if(res.name.equalsIgnoreCase(name)){
				return res;
			}
		}
		return new Restaurant();
	}
	

}
