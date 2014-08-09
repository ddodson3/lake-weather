package com.dandodson.weather;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class DataPage {
	private URL page;
	private ArrayList<Double> data;
	private LocalDate date;
	private LocalDateTime dateTime;
	
	
	public DataPage(LocalDate date) throws IOException {
		page = new URL(parseURL(date));
		Scanner in = new Scanner(page.openStream());
		data = new ArrayList<Double>();
		while (in.hasNext()) {
			if(in.hasNextDouble())
				data.add(in.nextDouble());
			else
				in.next();
		}
		in.close();
		
	}
	public DataPage(LocalDateTime dateTime) throws MalformedURLException {
		LocalDate date = dateTime.toLocalDate();
		LocalTime time = dateTime.toLocalTime();
		page = new URL(parseURL(date)) ;
		
	}
	
	public URL getURL() {
		return page;
	}
	
	public ArrayList<Double> getData() {
		return data;
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
