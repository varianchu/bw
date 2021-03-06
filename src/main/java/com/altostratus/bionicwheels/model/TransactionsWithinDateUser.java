package com.altostratus.bionicwheels.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionsWithinDateUser {
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	Date initDate = new Date();

	private String date1 = df.format(initDate);
	private String date2 = df.format(initDate);
	private Long userId;

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
