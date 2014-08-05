package com.dandodson.weather;


import java.io.IOException;
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

public class MeanMedian {

	
	public static void main(String[] args) {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("M-d-yyyy");
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("h:m a");
		try (Scanner keyboard = new Scanner (System.in)) {
			System.out.print("Enter Start Date (mm-dd-yyyy): ");
			LocalDate startDate = LocalDate.parse(keyboard.nextLine(), dateFormat);
			System.out.print("Enter Start Time (hh:mm am/pm): ");
			LocalTime startTime = LocalTime.parse(keyboard.nextLine().toUpperCase(),timeFormat);
			LocalDateTime start = startDate.atTime(startTime);
			System.out.print("Enter End Date (mm-dd-yyyy): ");
			LocalDate endDate = LocalDate.parse(keyboard.nextLine(), dateFormat);
			System.out.print("Enter End Time (hh:mm am/pm): ");
			LocalTime endTime = LocalTime.parse(keyboard.nextLine().toUpperCase(),timeFormat);
			LocalDateTime end = endDate.atTime(endTime);
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
		} catch (DateTimeException e) {
			System.err.println("Cannot recognize entry.");
		}
	}
	
	public static ArrayList<Double> getData(LocalDateTime start, LocalDateTime end) throws IOException {
		ArrayList<Double> results = new ArrayList<Double>();
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
					//check time only if date is at start or end of range
					if (date.isEqual(start.toLocalDate()) || date.isEqual(end.toLocalDate())) {
						String lineDate = in.next().replace("_", "-");
						String lineTime = in.next();
						LocalDateTime time = LocalDate.parse(lineDate).atTime(LocalTime.parse(lineTime));
						if (time.isAfter(start) && time.isBefore(end)) {
							results.add(in.nextDouble());
						} else {
							in.next();
						}
					} else {
						in.next();
						in.next();
						results.add(in.nextDouble());
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