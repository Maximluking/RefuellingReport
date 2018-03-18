package service;


import ServiceDAO.ServiceDAO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class DateService {
    private DateFormat simpleDateFormat;
    private Calendar calendar;


    public DateService() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Helsinki"));
        calendar = Calendar.getInstance();
    }

    public boolean ifEndOfSearch(String date) throws ParseException {
        if ((simpleDateFormat.parse(date)).getTime() >= (simpleDateFormat.parse(ServiceDAO.endPoint)).getTime()) {
            return false;
        } else return true;
    }

    public String nextDay(String date) throws ParseException {
        calendar.setTime(simpleDateFormat.parse(date));
        calendar.add(Calendar.HOUR, 6);
        return simpleDateFormat.format(calendar.getTime());
    }
}
//add commit