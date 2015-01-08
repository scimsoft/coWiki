package com.scimsoft.cowiki.providers;

import java.util.Locale;

import com.scimsoft.cowiki.MainActivity;

public class LocalesProvider extends Providers{

	LocalesProvider(MainActivity mainActivity) {
		super(mainActivity);
		// TODO Auto-generated constructor stub
	}
	
	public String getLocale(){		
		String deviceLocale=Locale.getDefault().getLanguage();
		return deviceLocale;
	}

}
