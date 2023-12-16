package com.stav.server.timertask;

import com.stav.server.logic.CouponsLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

@Component
public class MyTimerTask extends TimerTask {

    private CouponsLogic couponsLogic;

    @Autowired
    public MyTimerTask(CouponsLogic couponsLogic) {
        this.couponsLogic = couponsLogic;
    }

    @Override
    public void run() {
//        Date date = new Date();
//        long dateInMillis = date.getTime();
//        Date todayDate = new Date(dateInMillis);
//        Date todayDate = Calendar.getInstance(Calendar.getInstance().getTimeZone()).getTime();
        try {
            couponsLogic.deleteExpiredCoupon();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
