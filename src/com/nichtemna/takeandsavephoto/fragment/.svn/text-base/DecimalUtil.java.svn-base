package com.nichtemna.takeandsavephoto.fragment;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DecimalUtil {

	public static String toString(double number){
		String result="";
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(6);
		df.setMaximumFractionDigits(6);
		df.setRoundingMode(RoundingMode.UP);
		result = df.format(number);
		return result;
	}
	
	public static String toString1Digit(double number){
		String result="";
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(2);
		df.setMaximumFractionDigits(2);
		df.setRoundingMode(RoundingMode.UP);
		result = df.format(number);
		return result;
	}
	
}
