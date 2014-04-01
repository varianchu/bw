package com.altostratus.core.util;

import org.springframework.stereotype.Component;

@Component
public class ConvertCode {

	public String getCostCode(Double cost) {

		String stringCost = cost.toString();
		String costCode = "";

		String[] code = stringCost.split("\\.");

		if (code[1].equals("0")) {
			stringCost = code[0];
		}

		for (int i = 0; i < stringCost.length(); i++) {

			if (stringCost.charAt(i) == '0') {
				costCode = costCode + "Q";
			} else if (stringCost.charAt(i) == '1') {
				costCode = costCode + "A";
			} else if (stringCost.charAt(i) == '2') {
				costCode = costCode + "T";
			} else if (stringCost.charAt(i) == '3') {
				costCode = costCode + "E";
			} else if (stringCost.charAt(i) == '4') {
				costCode = costCode + "L";
			} else if (stringCost.charAt(i) == '5') {
				costCode = costCode + "S";
			} else if (stringCost.charAt(i) == '6') {
				costCode = costCode + "G";
			} else if (stringCost.charAt(i) == '7') {
				costCode = costCode + "F";
			} else if (stringCost.charAt(i) == '8') {
				costCode = costCode + "B";
			} else if (stringCost.charAt(i) == '9') {
				costCode = costCode + "P";
			} else if (stringCost.charAt(i) == '.') {
				costCode = costCode + ".";
			}
		}

		return costCode;
	}

}
