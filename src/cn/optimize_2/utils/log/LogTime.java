package cn.optimize_2.utils.log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogTime {
    public static String getTime() {
        DateFormat df = new SimpleDateFormat("[HH:mm:ss]");
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }
}
