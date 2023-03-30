package com.kfxlabs.smartsociety.Activity;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.Utils;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class MyXAxisValueFormatter implements IAxisValueFormatter {
//public class MyXAxisValueFormatter extends IndexAxisValueFormatter {
    /*private String[] mValues;

    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd:hh:mm:ss");
    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");

    public MyXAxisValueFormatter(String[] values) {
        this.mValues = values; }
    @Override
    public String getFormattedValue(float value, AxisBase axisBase) {
        //Date d = new Date(Float.valueOf(value).longValue());
        //String date = new SimpleDateFormat("dd-MM",Locale.ENGLISH).format(d);
        return sdf.format(new Date((long) value));
        //return date;
    }*/
    @Override
    public String getFormattedValue(float value, AxisBase axisBase) {
        Date date = new Date((long)value);
        SimpleDateFormat stf = new SimpleDateFormat("hh:mm");
        return stf.format(date);
    }

}
/*public class MyXAxisValueFormatter extends IndexAxisValueFormatter {

    List<String> datesList;

    public MyXAxisValueFormatter(List<String> arrayOfDates) {
        this.datesList = arrayOfDates;
    }


    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        Integer position = Math.round(value);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");

        if (value > 1 && value < 2) {
            position = 0;
        } else if (value > 2 && value < 3) {
            position = 1;
        } else if (value > 3 && value < 4) {
            position = 2;
        } else if (value > 4 && value <= 5) {
            position = 3;
        }
        if (position < datesList.size())
            return sdf.format(new Date((Utils.getDateInMilliSeconds(datesList.get(position), "yyyy-MM-dd"))));
        return "";
    }
}*/