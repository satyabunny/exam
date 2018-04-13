package com.exam.portal.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
	
	private static Pattern emailPattern = Pattern
			.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

	public static boolean isValidEmail(String email) {
		/*String emailPattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(emailPattern);
         java.util.regex.Matcher matcher = p.matcher(email);*/
		Matcher matcher = emailPattern.matcher(email);
		return matcher.matches();
	}

}
