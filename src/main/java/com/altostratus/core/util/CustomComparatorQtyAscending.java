package com.altostratus.core.util;

import java.util.Comparator;

import com.altostratus.bionicwheels.model.LineItemProductReport;

public class CustomComparatorQtyAscending implements Comparator<LineItemProductReport>{

	@Override
	public int compare(LineItemProductReport o1, LineItemProductReport o2) {
		// TODO Auto-generated method stub
		return o1.getQtyOut().compareTo(o2.getQtyOut());
	}

}
