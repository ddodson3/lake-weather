package com.dandodson.weather;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

import org.junit.Test;

public class DataPageTest {

	@Test
	public void testDataPageLocalDate() throws MalformedURLException {
		URL url = new URL("http://lpo.dt.navy.mil/data/DM/2012/2012_01_08/Air_Temp");
		LocalDate date = LocalDate.of(2012, 1, 8);
		DataPage page = new DataPage(date);
		assertEquals(url, page.getURL());
	}

	@Test
	public void testDataPageLocalDateTime() throws MalformedURLException {
		URL url = new URL("http://lpo.dt.navy.mil/data/DM/2012/2012_01_08/Air_Temp");
		LocalDate date = LocalDate.of(2012, 1, 8);
		DataPage page = new DataPage(date);
		assertEquals(url, page.getURL());
	}
}
