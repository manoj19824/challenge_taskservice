package com.celonis.challenge.util;

public class TaskUtil {

    public static String getCompletedPercentage(int completed, int total){
        double percentage = completed * 100 / total;
        return percentage + "%";
    }
}
