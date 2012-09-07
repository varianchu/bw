package com.altostratus.bionicwheels.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionsWithinDate {

	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	Date initDate = new Date();

	private String date1 = df.format(initDate);
	private String date2 = df.format(initDate);

	public String getDate1() {
		return date1;
	}

	public void setDate1(String date1) {
		this.date1 = date1;
	}

	public String getDate2() {
		return date2;
	}

	public void setDate2(String date2) {
		this.date2 = date2;
	}

}