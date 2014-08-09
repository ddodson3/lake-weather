package com.dandodson.weather;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;

import org.junit.Test;

public class DataPageTest {

	@Test
	public void testDataPageLocalDateURLTest() throws IOException {
		URL url = new URL("http://lpo.dt.navy.mil/data/DM/2012/2012_01_08/Air_Temp");
		LocalDate date = LocalDate.of(2012, 1, 8);
		DataPage page = new DataPage(date);
		assertEquals(url, page.getURL());
	}
	
	@Test
	public void testDataPageLocalDateDataTest() throws IOException {
		ArrayList<Double> data = new ArrayList<Double>();
		Scanner input = new Scanner(new FileReader("testData/Air_Temp_2012_01_08.txt"));
		
		while(input.hasNext()) {
			if (input.hasNextDouble())
				data.add(input.nextDouble());
			else
				input.next();
		}
		input.close();
		
		LocalDate date = LocalDate.of(2012, 1, 8);
		DataPage page = new DataPage(date);
		assertEquals(data, page.getData());
	}

	@Test
	public void testDataPageLocalDateTimeURLTest() throws IOException {
		URL url = new URL("http://lpo.dt.navy.mil/data/DM/2012/2012_01_08/Air_Temp");
		LocalDate date = LocalDate.of(2012, 1, 8);
		DataPage page = new DataPage(date);
		assertEquals(url, page.getURL());
	}
	
	@Test
	public void testDataPageLocalDateTimeDataTest() throws IOException {
		ArrayList<Double> data = new ArrayList<Double>();
		Scanner input = new Scanner(new FileReader("testData/Air_Temp_2012_01_08-9to5.txt"));
		while (input.hasNext()) {
			data.add(input.nextDouble());
		}
		LocalDateTime date = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
		DataPage page = new DataPage(date);
		assertEquals(data, page.getData());
	}
}
