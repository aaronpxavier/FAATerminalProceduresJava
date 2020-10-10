package com.fltprep.dttp;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class DttpDownloads extends Dttp {

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
}
