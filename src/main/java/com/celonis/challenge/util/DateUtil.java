package com.celonis.challenge.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {

    public static boolean isOlderThan(int numOfDays, Date newDate){
        LocalDate newLocalDate = newDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return newLocalDate.isBefore(LocalDate.now().minusDays(numOfDays));
    }
}
