package com.scimsoft.cowiki;

import java.util.Locale;

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
