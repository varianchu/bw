package com.altostratus.core.util;

import java.util.Comparator;

import com.altostratus.bionicwheels.model.Inventory;

public class CustomComparatorAscending implements Comparator<Inventory> {
	@Override
	public int compare(Inventory o1, Inventory o2) {
		return o1.getDate().compareTo(o2.getDate());
	}
}