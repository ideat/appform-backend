package com.mindware.appform.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static String formatDate(Date date, String sourceFormat){

        SimpleDateFormat sm = new SimpleDateFormat(sourceFormat);

        String strDate = date!=null?sm.format(date):"";

        return strDate;
    }
}
