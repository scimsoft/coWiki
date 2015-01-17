package com.scimsoft.whatsnear.helpers;

import java.util.ArrayList;
import java.util.List;

import android.location.Location;

public class WikiEntries {
	private ArrayList<WikiEntry> entries = new ArrayList<WikiEntry>();
	
	public void addWikiEntry(WikiEntry wikientry){
		if(!getWikiEntrie(wikientry.getName()).getName().equals("Not Found"))
		entries.add(wikientry);
	}
	public void addWikiData(String name, Location location){
		entries.add(new WikiEntry(name));
	}
	public WikiEntry getWikiEntrie(String name){
		for(WikiEntry entry: entries){
			if(entry.getName().equalsIgnoreCase(name)){
				return entry;
			}
		}
		return new WikiEntry();
	}
	public List<String> getNameList() {
		ArrayList<String> nameList = new ArrayList<String>();
		for(WikiEntry res: entries){
			nameList.add(res.getName());
		}
		return nameList;
	}
	public WikiEntry getWikiEntry(String name) {
		for(WikiEntry res: entries){
			if(res.name.equalsIgnoreCase(name)){
				return res;
			}
		}
		return new WikiEntry();
	}
	

}
