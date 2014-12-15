package com.scimsoft.cowiki;

import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.scimsoft.cowiki.helpers.Coordinates;

@SuppressLint("ServiceCast")
public class LocationProvider {
	private static final int TWO_MINUTES = 1000 * 60 * 2;
	LocationManager locationManager;
	private Location currentLocation;
	private MainActivity mainActivity;
	public LocationProvider(Activity mainActivity){
		
		this.mainActivity = (MainActivity) mainActivity;
		// Acquire a reference to the system Location Manager
		locationManager = (LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE);

		// Define a listener that responds to location updates
		LocationListener locationListener = new LocationListener() {
		    public void onLocationChanged(Location location) {
		      // Called when a new location is found by the network location provider.
		      setNewLocation(location);
		      
		    }

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				
				
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}

		    
		  };
		 currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		  locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		  
	}
	private void setNewLocation(Location location) {
		Location lastLocation = getLastKnownLocation();
		if (isBetterLocation(lastLocation,location )){
			currentLocation = location;			
			mainActivity.noticeNewResults();
		}
	}
		
	public Coordinates getCurrentLocationCoordinates(){
		Coordinates coordinates;
		if (currentLocation==null){
			coordinates= new Coordinates();
		}else{
		coordinates = new Coordinates(currentLocation.getLatitude(),currentLocation.getLongitude());
				}
		return coordinates;
	}
	
	protected Location getLastKnownLocation(){
		String locationProvider = LocationManager.NETWORK_PROVIDER;
		Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
		return lastKnownLocation;
	}
	/** Determines whether one Location reading is better than the current Location fix
	  * @param location  The new Location that you want to evaluate
	  * @param currentBestLocation  The current Location fix, to which you want to compare the new one
	  */
	protected boolean isBetterLocation(Location location, Location currentBestLocation) {
	    if (currentBestLocation == null) {
	        // A new location is always better than no location
	        return true;
	    }

	    // Check whether the new location fix is newer or older
	    long timeDelta = location.getTime() - currentBestLocation.getTime();
	    boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
	    boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
	    boolean isNewer = timeDelta > 0;

	    // If it's been more than two minutes since the current location, use the new location
	    // because the user has likely moved
	    if (isSignificantlyNewer) {
	        return true;
	    // If the new location is more than two minutes older, it must be worse
	    } else if (isSignificantlyOlder) {
	        return false;
	    }

	    // Check whether the new location fix is more or less accurate
	    int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
	    boolean isLessAccurate = accuracyDelta > 0;
	    boolean isMoreAccurate = accuracyDelta < 0;
	    boolean isSignificantlyLessAccurate = accuracyDelta > 200;

	    // Check if the old and new location are from the same provider
	    boolean isFromSameProvider = isSameProvider(location.getProvider(),
	            currentBestLocation.getProvider());

	    // Determine location quality using a combination of timeliness and accuracy
	    if (isMoreAccurate) {
	        return true;
	    } else if (isNewer && !isLessAccurate) {
	        return true;
	    } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
	        return true;
	    }
	    return false;
	}

	/** Checks whether two providers are the same */
	private boolean isSameProvider(String provider1, String provider2) {
	    if (provider1 == null) {
	      return provider2 == null;
	    }
	    return provider1.equals(provider2);
	}
	public void updateLocation() {
		// TODO Auto-generated method stub
		
		
	}
	public String getDeviceLocale(){
		String locale = mainActivity.getResources().getConfiguration().locale.getCountry(); 
		return locale;
	}
	public String getLocationLocale(){		
		Locale locale = Locale.getDefault();
		return locale.getCountry();
	}
	
}
