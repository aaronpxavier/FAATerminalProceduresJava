package com.fltprep.dttp;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;


public class Dttp {
    protected static final int FAA_CYCLE_INTERVAL = 28;
    public static final String FAA_CYCLE_REF_DATE_STRING = "2020-01-02";
    public static final Date FAA_CYCLE_REF_DATE = getFAARefDate();
    protected static final String META_FILE_URL = String.format("http://aeronav.faa.gov/d-tpp/%s/xml_data/d-tpp_Metafile.xml", getFourDigitCycle());
    protected static final String META_FILE_NAME = "d-tpp_Metafile.xml";

    private static Date getFAARefDate() {
        Date refDate = null;
        try {
            SimpleDateFormat df;
            df = new SimpleDateFormat("yyyy-MM-dd");
            refDate = df.parse(FAA_CYCLE_REF_DATE_STRING);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return refDate;
    }

    public static Calendar getCycleCalendar() {
        Calendar currentTime = Calendar.getInstance();
        Calendar resultTimeCal = Calendar.getInstance();

        currentTime.setTime(new Date(System.currentTimeMillis()));
        resultTimeCal.setTime(FAA_CYCLE_REF_DATE);

        while (resultTimeCal.getTime().compareTo(currentTime.getTime()) <= 0)
            resultTimeCal.add(Calendar.DAY_OF_MONTH, FAA_CYCLE_INTERVAL);

        if (resultTimeCal.getTime().compareTo(FAA_CYCLE_REF_DATE) != 0)
            resultTimeCal.add(Calendar.DAY_OF_MONTH, -FAA_CYCLE_INTERVAL);

        return resultTimeCal;
    }

    /**
     * @return String - 6 digit cycle string used in url for faa dttp zip files;
     */
    public static String getCurrentCycle() {
        Calendar resultTimeCal = (GregorianCalendar) getCycleCalendar();
        int month;
        int year;
        int day;
        String currentCycleString = "";

        day = resultTimeCal.get(resultTimeCal.DAY_OF_MONTH);
        month = resultTimeCal.get(resultTimeCal.MONTH) + 1;
        year = resultTimeCal.get(resultTimeCal.YEAR);

        currentCycleString = String.valueOf(year).substring(2);
        currentCycleString += day < 10 ? "0" + String.valueOf(day) : String.valueOf(day);
        currentCycleString += month < 10 ? "0" + String.valueOf(month) : String.valueOf(month);

        return currentCycleString;
    }

    /**
     * @return String - 4 digit faa cycle string used to get metafile;
     */
    public static String getFourDigitCycle() {
        GregorianCalendar resultTimeCal = (GregorianCalendar) getCycleCalendar();
        int month;
        int year;
        String currentCycleString = "";

        month = resultTimeCal.get(resultTimeCal.MONTH) + 1;
        year = resultTimeCal.get(resultTimeCal.YEAR);

        currentCycleString = String.valueOf(year).substring(2);
        currentCycleString += month < 10 ? "0" + String.valueOf(month) : String.valueOf(month);

        return currentCycleString;
    }

    public static void main(String args[]) {
        ParseMetaFile.parseMetafile(new File("./d-tpp_Metafile.xml"));
        //DttpDownloads.dloadMetaFile(".");
    }

}
