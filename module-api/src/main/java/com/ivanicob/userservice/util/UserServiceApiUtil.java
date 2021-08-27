package com.ivanicob.userservice.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class UserServiceApiUtil {
	
	public static final String HEADER_USER_SERVICE_API_VERSION = "user-service-api-version";
	
	public static final String HEADER_API_KEY = "X-api-key";
	
	public static final String HEADER_LIMIT_REMAINING = "X-Rate-Limit-Remaining";
    
	public static final String HEADER_RETRY_AFTER = "X-Rate-Limit-Retry-After-Seconds";
	
	private UserServiceApiUtil() {}
	

	public static DateTimeFormatter getDateFormater() {
		return DateTimeFormatter.ofPattern("yyyy-MM-dd");
	}
	
	public static DateTimeFormatter getDateTimeFormater() {
		return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	}
	
	public static LocalDateTime getLocalDateTimeFromString(String dateAsString) throws ParseException{
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date dateISO8601 = inputFormat.parse(dateAsString);
        return dateISO8601.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
	
	public static LocalDateTime convertLocalDateToLocalDateTime(LocalDate date) {
		return date.atTime(0, 0, 0);
	}
	
}
