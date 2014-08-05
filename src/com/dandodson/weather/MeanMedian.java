package com.dandodson.weather;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.TreeSet;

public class MeanMedian {
	
	private static double median;
	private static double mean;
	
	public static void main(String[] args) {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("M-d-yyyy");
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("h:m a");
		Scanner keyboard = new Scanner (System.in);
		System.out.print("Enter Start Date (mm-dd-yyyy): ");
		LocalDate startDate = LocalDate.parse(keyboard.nextLine(), dateFormat);
		System.out.print("Enter Start Time (hh:mm am/pm): ");
		LocalTime startTime = LocalTime.parse(keyboard.nextLine().toUpperCase(),timeFormat);
		LocalDateTime start = startDate.atTime(startTime);
//		TODO: Error handling
		System.out.print("Enter End Date (mm-dd-yyyy): ");
		LocalDate endDate = LocalDate.parse(keyboard.nextLine(), dateFormat);
		System.out.print("Enter End Time (hh:mm am/pm): ");
		LocalTime endTime = LocalTime.parse(keyboard.nextLine().toUpperCase(),timeFormat);
		LocalDateTime end = endDate.atTime(endTime);
		keyboard.close();
		

		try {
			if (end.isAfter(start)) {
				ArrayList<Double> results = getData(start, end);
				System.out.printf("Mean: %.1f\n", mean(results));
				System.out.printf("Median: %.1f\n", median(results));
			} else {
				System.out.println("End is before start date.");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static ArrayList<Double> getData(LocalDateTime start, LocalDateTime end) throws IOException {
		ArrayList<Double> results = new ArrayList<Double>();
//			TODO: check start date and end date differently
			for (LocalDate date = start.toLocalDate(); 
					date.isBefore(end.toLocalDate()) || date.isEqual(end.toLocalDate()); 
					date = date.plusDays(1)) {
				String url = "http://lpo.dt.navy.mil/data/DM/";
				url += date.getYear() + "/";
				url += date.getYear() + "_"; 
				url += String.format("%02d", date.getMonthValue()) + "_";
				url += String.format("%02d", date.getDayOfMonth())+ "/";
				url += "Air_Temp";
				URL dayOfData = new URL(url);
				Scanner in = new Scanner(dayOfData.openStream()); 
				while (in.hasNext()) {
					String lineDate = in.next().replace("_", "-");
					LocalDateTime time = LocalDate.parse(lineDate).atTime(LocalTime.parse(in.next()));
					if (time.isAfter(start) && time.isBefore(end)) {
						results.add(in.nextDouble());
					} else {
						in.next();
					}
				}
				in.close();
			}
		return (results);
	}
	
	public static double mean(ArrayList<Double> data) {
		double sum = 0;
		for (double i : data) {
			sum += i;
		}
		return (sum/data.size());
	}
	public static double median(ArrayList<Double> data) {
		Collections.sort(data);
		if (data.size() % 2 == 0) {
			return ((data.get(data.size()/2) + data.get(data.size()/2-1)) / 2);
		} else {
			return (data.get(data.size()/2));
		}
	}
}