package com.fltprep.dttp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class DttpBulk {
    public static final String FAA_CYCLE_REF_DATE_STRING = "2020-01-02";
    public static final Date FAA_CYCLE_REF_DATE = getFAARefDate();

    private static Date getFAARefDate() {
        Date refDate = null;
        try {
            SimpleDateFormat df;
            df = new SimpleDateFormat("yyyy-MM-dd");
            refDate = df.parse(FAA_CYCLE_REF_DATE_STRING);
        }
        catch (Exception e) {
            System.err.println(e);
        }
        return refDate;
    }

    public static void dloadMetaFile(String dest_dir) {
        //**todo**//
    }

    /**
     * @return String returns cycle string used in url for faa dttp zip files;
     */
    public static String getCurrentCycle() {
        Calendar resultTimeCal = Calendar.getInstance();
        int month;
        int year;
        int day;
        String currentCycleString = "";

        // loops from reference date until
        while (resultTimeCal.getTime().compareTo(FAA_CYCLE_REF_DATE) <= 0)
            resultTimeCal.add(Calendar.DATE, 28);

        if (resultTimeCal.getTime().compareTo(FAA_CYCLE_REF_DATE) != 0)
            resultTimeCal.add(Calendar.DATE, -28);

        month = resultTimeCal.get(Calendar.MONTH);
        year = resultTimeCal.get(Calendar.YEAR);
        day = resultTimeCal.get(Calendar.DAY_OF_MONTH);

        currentCycleString = String.valueOf(year).substring(2);
        currentCycleString += day < 10 ? "0" + String.valueOf(day) : String.valueOf(day);
        currentCycleString += month < 10 ? "0" + String.valueOf(month) : String.valueOf(month);

        return currentCycleString;
    }

}
