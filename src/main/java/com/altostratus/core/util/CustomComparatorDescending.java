package com.altostratus.core.util;

import java.util.Comparator;

import com.altostratus.bionicwheels.model.Inventory;

public class CustomComparatorDescending implements Comparator<Inventory> {
	@Override
	public int compare(Inventory o1, Inventory o2) {
		// TODO Auto-generated method stub
		return o2.getDate().compareTo(o1.getDate());
	}

}
