package com.jorocha.coopervote.resources.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Data {
	
    public static Date stringToDate(String dateInString) throws ParseException{	
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");		
		return dateFormatter.parse(dateInString);
    }
    
}
