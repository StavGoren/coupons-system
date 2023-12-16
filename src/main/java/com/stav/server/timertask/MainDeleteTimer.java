//package com.stav.server.timertask;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class MainDeleteTimer {
//    public static void main(String[] args) {
//
//
//        TimerTask timerTask = new MyTimerTask();
//        Timer timer = new Timer();
//
//        Calendar calendar = Calendar.getInstance(Calendar.getInstance().getTimeZone());
//        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
//        calendar.set(Calendar.HOUR_OF_DAY, 23);
//        calendar.set(Calendar.MINUTE, 59);
//        calendar.set(Calendar.SECOND, 59);
//
//        Date date = calendar.getTime();
//
//        timer.schedule(timerTask, date, 86400000);
//
//    }
//}
