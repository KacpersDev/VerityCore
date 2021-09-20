package xyz.cronu.veritycore.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Date {

	public static String longToDate(long milis) {
		java.util.Date d = new java.util.Date(milis);
		DateFormat df = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		return df.format(d);
	}

}
