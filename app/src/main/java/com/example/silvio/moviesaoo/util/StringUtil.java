package com.example.silvio.moviesaoo.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by silvio on 25/12/2017.
 */

public class StringUtil {

    public static String formateDateFromServerResource(String originDate) {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat sdfWithoudHours = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");
        Date d = null;

        if(originDate.length() > 10) {
            try {
                d = sdf.parse(originDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else{
            try {
                d = sdfWithoudHours.parse(originDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return output.format(d);
    }

    public static Date formatStringToFullDate(String fulldate) {

        if (fulldate == null) return null;
        ParsePosition pos = new ParsePosition(0);
        Locale ptBr = new Locale("pt", "BR");
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyy-MM-dd", ptBr);
        return (Date)simpledateformat.parse(fulldate, pos);
    }

}
