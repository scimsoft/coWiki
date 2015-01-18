package com.scimsoft.whatsnear.providers;

import com.scimsoft.whatsnear.MainActivity;
import com.scimsoft.whatsnear.helpers.JSONParser;
import com.scimsoft.whatsnear.helpers.NearLocation;

public abstract class Providers {
	protected JSONParser parser;
	protected MainActivity mainActivity;

	protected Providers(MainActivity mainActivity){
		this.mainActivity = mainActivity;
		parser = new JSONParser();
	}
	
	public <T extends NearLocation> T getDetails(String name){
		return null;
	}

	public void setListShown() {
		// TODO Auto-generated method stub
		
	}

	
	
	

}
