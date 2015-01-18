package com.scimsoft.whatsnear.helpers;

import java.util.ArrayList;
import java.util.List;

import android.location.Location;
import android.net.Uri;

public class Restaurants {
	private ArrayList<Restaurant> restaurants;
	private ArrayList<Restaurant> newRestaurants;
	public Restaurants(){
		restaurants = new ArrayList<Restaurant>();
		newRestaurants = new ArrayList<Restaurant>();
	}
	public void addRestaurant(Restaurant restaurant){
		if(getRestaurant(restaurant.getId()).getId()==0)
			newRestaurants.add(restaurant);
	}
	public void addRestaurantData(long id, String name, Location location, Uri url){
		newRestaurants.add(new Restaurant(id, name, location, url));
	}
	public Restaurant getRestaurant(long id){
		for(Restaurant res: newRestaurants){
			if(res.getId()== id){
				return res;
			}
		}
		return new Restaurant();
	}
	public List<String> getNameList() {
		ArrayList<String> nameList = new ArrayList<String>();
		for(Restaurant res: newRestaurants){
			nameList.add(res.getName());
		}
		return nameList;
	}
	public Restaurant getRestaurant(String name) {
		for(Restaurant res: newRestaurants){
			if(res.name.equalsIgnoreCase(name)){
				return res;
			}
		}
		return new Restaurant();
	}
	public void setListShown() {
		restaurants.addAll(newRestaurants);
		newRestaurants.clear();
		
	}
	public void renewAllEntries() {
		newRestaurants.addAll(newRestaurants);
		restaurants.clear();		
	}
	

}
