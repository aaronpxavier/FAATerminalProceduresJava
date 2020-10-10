package com.fltprep.dttp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


public class DttpBulk {
    private static final int FAA_CYCLE_INTERVAL = 28;
    public static final String FAA_CYCLE_REF_DATE_STRING = "2020-01-02";
    public static final Date FAA_CYCLE_REF_DATE = getFAARefDate();
    private static final String META_FILE_URL = String.format("http://aeronav.faa.gov/d-tpp/%s/xml_data/d-tpp_Metafile.xml", getFourDigitCycle());
    private static final String META_FILE_NAME = "d-tpp_Metafile.xml";

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

    private static void downloadFile(String url, File file) throws Exception {
        HttpGet request = new HttpGet(url);
        final CloseableHttpClient httpClient = HttpClients.createDefault();
        // add request headers
        request.addHeader("custom-key", "mkyong");
        request.addHeader(HttpHeaders.USER_AGENT, "JAVA");
        System.out.println("Downloading " + file.getName() + "from " + url);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
            System.out.println(headers);

            if (entity != null) {
                InputStream is = entity.getContent();
                FileOutputStream fos = new FileOutputStream(file);
                int inByte;
                while ((inByte = is.read()) != -1) {
                    fos.write(inByte);
                }
                is.close();
                fos.close();
            }
        }
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
     *
     * @param dest_dir Directory where you want to save the Metafile.xml from FAA d-tpp
     **/
    public static void dloadMetaFile(String dest_dir) {
        File metaFile = new File(dest_dir + "/" + META_FILE_NAME);
        try {
            downloadFile(META_FILE_URL, metaFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        System.out.println(getFourDigitCycle());
        //dloadMetaFile(".");
    }

}
