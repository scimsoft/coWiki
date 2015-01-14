package com.scimsoft.whatsnear.providers;

import java.util.Locale;

import com.scimsoft.whatsnear.MainActivity;

public class LocalesProvider extends Providers{

	public LocalesProvider(MainActivity mainActivity) {
		super(mainActivity);
		// TODO Auto-generated constructor stub
	}
	
	public Locale getLocale(){		
		Locale deviceLocale=Locale.getDefault();
		return deviceLocale;
	}

}
