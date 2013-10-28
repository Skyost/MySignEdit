package com.skyost.mysignedit.utils;

public class SignUtils {
	
	public static final String colourize(String in) {
		return (" " + in).replaceAll("([^\\\\](\\\\\\\\)*)&(.)", "$1§$3").replaceAll("([^\\\\](\\\\\\\\)*)&(.)", "$1§$3").replaceAll("(([^\\\\])\\\\((\\\\\\\\)*))&(.)", "$2$3&$5").replaceAll("\\\\\\\\", "\\\\").trim();
	}
	
	public static final String decolourize(String in) {
		return (" " + in).replaceAll("\\\\", "\\\\\\\\").replaceAll("&", "\\\\&").replaceAll("§", "&").trim();
	}
	
	public static final boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
		}
		catch(NumberFormatException ex) {
			return false;
		}
		return true;
	}
	
	public static final String[] toArray(String str) {
		return str.split("§à§");
	}

}
