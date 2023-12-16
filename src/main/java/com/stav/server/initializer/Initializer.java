package com.stav.server.initializer;

import com.stav.server.timertask.MyTimerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

@Component
public class Initializer {
    private Timer timer;
    private MyTimerTask myTimerTask;

    @Autowired
    public Initializer(MyTimerTask myTimerTask) {
        this.timer = new Timer();
        this.myTimerTask = myTimerTask;
        removeExpiredCoupons();
    }

    private void removeExpiredCoupons() {
        Calendar calendar = Calendar.getInstance(Calendar.getInstance().getTimeZone());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        Date date = calendar.getTime();

        timer.schedule(myTimerTask, 0, 86400000);
    }
}
