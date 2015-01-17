package com.scimsoft.whatsnear.helpers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class WikiEntry extends NearLocation {
	private StringBuffer extract;
	private URI mainInmageUri;
	
	public WikiEntry(String name) {
		extract= new StringBuffer("");
		super.name=name;
	}
	public WikiEntry() {
		this.name="Not Found";
		extract= new StringBuffer("");
	}
	/**
	 * @return the extract
	 */
	public StringBuffer getExtract() {
		return extract;
	}
	/**
	 * @param extract the extract to set
	 */
	public void setExtract(StringBuffer extract) {
		this.extract = extract;
	}
	/**
	 * @return the mainInmageUri
	 */
	public URI getMainInmageUri() {
		return mainInmageUri;
	}
	/**
	 * @param mainInmageUri the mainInmageUri to set
	 */
	public void setMainInmageUri(URI mainInmageUri) {
		this.mainInmageUri = mainInmageUri;
	}
	public void addExtract(String string) {
		this.extract.append(string);		
	}
	public List<String> getExtracts(){
		ArrayList<String> extracts = new ArrayList<String>();
		extracts.add(extract.toString());
		return extracts;
	}
	public List<String> getDetailTexts(){
		
		return getExtracts();
	}
	
	
	

}
