package com.dandodson.weather;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DataPage {
	private URL page;
	private ArrayList<Double> data;
	private LocalDate date;
	private LocalDateTime dateTime;
	
	
	public DataPage(LocalDate date) throws MalformedURLException {
		page = new URL(parseURL(date));
		
	}
	public DataPage(LocalDateTime dateTime) throws MalformedURLException {
		LocalDate date = dateTime.toLocalDate();
		page = new URL(parseURL(date)) ;
		
	}
	
	public URL getURL() {
		return page;
	}

	private String parseURL(LocalDate date) {
		String url = "http://lpo.dt.navy.mil/data/DM/";
		url += date.getYear() + "/";
		url += date.getYear() + "_"; 
		url += String.format("%02d", date.getMonthValue()) + "_";
		url += String.format("%02d", date.getDayOfMonth())+ "/";
		url += "Air_Temp";
		return url;
	}
}
