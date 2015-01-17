package com.scimsoft.whatsnear.providers;

import java.util.List;

import com.scimsoft.whatsnear.MainActivity;
import com.scimsoft.whatsnear.helpers.NearLocation;

public class Providers {
	
	protected MainActivity mainActivity;

	protected Providers(MainActivity mainActivity){
		this.mainActivity = mainActivity;
	}
	
	public <T extends NearLocation> T getDetails(String name){
		return null;
	}
	
	

}
