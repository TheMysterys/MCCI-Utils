package com.themysterys.mcciutils.util.api;

import com.themysterys.mcciutils.McciUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateAPI {

    public static boolean isMCCIBirthday() {
        Calendar birthday = Calendar.getInstance();
        birthday.set(2022, Calendar.AUGUST, 21, 12,58);
        birthday.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar now = Calendar.getInstance();
        now.setTimeZone(TimeZone.getTimeZone("UTC"));
        return now.get(Calendar.DAY_OF_YEAR) == birthday.get(Calendar.DAY_OF_YEAR);
    }

    public static String getMCCIAge() {
        Calendar birthday = Calendar.getInstance();
        birthday.set(2022, Calendar.AUGUST, 21, 12,58);
        birthday.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar now = Calendar.getInstance();
        now.setTimeZone(TimeZone.getTimeZone("UTC"));
        int age = now.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
        if (age >= 11 && age <= 13) {
            return "th";
        }
        return age + switch (age % 10) {
            case 1 -> "st";
            case 2 -> "nd";
            case 3 -> "rd";
            default -> "th";
        };
    }

    public static boolean isMCCDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        return calendar.get(Calendar.DAY_OF_YEAR) == McciUtils.mccAPI.mccDate.get(Calendar.DAY_OF_YEAR);
    }
}
