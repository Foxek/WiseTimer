package com.foxek.simpletimer.utils;

public class IntervalUtils {

    public static int convertToSeconds(String minute, String seconds) {
        return Integer.valueOf(minute) * 60 + Integer.valueOf(seconds);
    }

    public static String formatEditTextData(int time) {
        if (time < 10)
            return "0" + String.valueOf(time);
        else
            return String.valueOf(time);
    }

    public static String formatIntervalData(int time) {
        int minutes, seconds;
        minutes = time / 60;
        seconds = time % 60;

        if ((minutes < 10) && (seconds < 10)) {
            if ((minutes == 0) && (seconds < 0))
                return "00" + ":" + "00";
            else
                return "0" + String.valueOf(minutes) + ":" + "0" + String.valueOf(seconds);
        } else if ((minutes < 10) && (seconds >= 10))
            return "0" + String.valueOf(minutes) + ":" + String.valueOf(seconds);
        else if ((minutes >= 10) && (seconds < 10))
            return String.valueOf(minutes) + ":" + "0" + String.valueOf(seconds);
        else
            return String.valueOf(minutes) + ":" + String.valueOf(seconds);
    }

    public static String formatIntervalNumber(int number) {
        String rawString = String.valueOf(number);
        char lastNumber = rawString.charAt(rawString.length() - 1);
        switch (lastNumber) {
            case '2':
            case '6':
            case '7':
            case '8':
                return rawString + "-ой подход";
            case '3':
                return rawString + "-ий подход";
            default:
                return rawString + "-ый подход";
        }
    }
}
