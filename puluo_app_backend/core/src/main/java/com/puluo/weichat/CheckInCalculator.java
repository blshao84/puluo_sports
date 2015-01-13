package com.puluo.weichat;

public class CheckInCalculator {
	public static double calculateCustomerPoints(int checkinDays) {
		double points = 1.0;
		if (checkinDays >= 30) {
			points *= 3.0;
		} else if (checkinDays >= 21) {
			points *= 2.0;
		} else if (checkinDays >= 14) {
			points *= 1.5;
		} else if (checkinDays >= 7) {
			points *= 1.3;
		} else if (checkinDays >= 3) {
			points *= 1.1;
		} else {
			
		}
		return points;
	}
}
