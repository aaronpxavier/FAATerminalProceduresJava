package com.fltprep.dttp;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DttpDownloads extends Dttp {
    private static final String[] ZIP_FILE_PRE = new String[]{"DDTPPA_", "DDTPPB_", "DDTPPC_", "DDTPPD_"};
    private static final String CHART_FILES_URL = "https://aeronav.faa.gov/upload_313-d/terminal";

    private static void downloadFile(String url, File file) throws Exception {
        HttpGet request = new HttpGet(url);

        // add request headers
        request.addHeader("custom-key", "mkyong");
        request.addHeader(HttpHeaders.USER_AGENT, "JAVA");
        System.out.println("Downloading " + file.getName() + " from " + url);
        try (final CloseableHttpClient httpClient = HttpClients.createDefault()) {
            File downloaded = httpClient.execute(request, new FileDownloadResponseHandler(file));
            System.out.println("Dowloaded to: " + downloaded.getAbsolutePath());
        }

    }

    private static class FileDownloadResponseHandler implements ResponseHandler<File> {

        private final File target;

        public FileDownloadResponseHandler(File target) {
            this.target = target;
        }

        @Override
        public File handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
            InputStream source = response.getEntity().getContent();
            FileUtils.copyInputStreamToFile(source, this.target);
            return this.target;
        }

    }

    /**
     *
     * @param destDir Directory where you want to save the Metafile.xml from FAA d-tpp
     **/
    public static void dloadMetaFile(String destDir) {
        checkDirectory(destDir);
        File metaFile = new File(destDir + "/" + META_FILE_NAME);
        try {
            downloadFile(META_FILE_URL, metaFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean checkDirectory(String dir) {
        final Path DEST_DIR_PATH = Paths.get(dir);
        if (!Files.exists(DEST_DIR_PATH)) {
            try {
                Files.createDirectories(DEST_DIR_PATH);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return true;
        }
    }

    public static void dloadPdfFiles(String destDir) {
        checkDirectory(destDir);
        for (String filePre : ZIP_FILE_PRE) {
            String fileName = filePre + getCurrentCycle() + ".zip";
            File file = new File(destDir + "/" + fileName);
            try {
                downloadFile(CHART_FILES_URL + "/" + fileName, file);
                unzipPdfFiles(destDir, fileName);
                new File(fileName).delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void cleanDirectory(String dir) throws IOException {
        FileUtils.cleanDirectory(new File(dir));
    }

    private static void unzipPdfFiles(String DEST_FOLDER, String fileName) throws IOException, FileNotFoundException{
        System.out.println("Unziping file " + fileName + "To folder" + DEST_FOLDER);
        String fileZip = DEST_FOLDER + "/" + fileName;
        if(!checkDirectory(DEST_FOLDER)) {
            throw new FileNotFoundException("Unable to create directory: " + DEST_FOLDER);
        }
        System.out.println("Unziping to Directory: " + DEST_FOLDER);
        File destDir = new File(DEST_FOLDER);
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = newFile(destDir, zipEntry);
            if (newFile.isDirectory()) {
                checkDirectory(newFile.getAbsolutePath());
            }
            FileOutputStream fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
        new File(fileZip).delete();
    }

    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

}
