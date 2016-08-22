package com.rarasnet.rnp.shared.util.managers;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.net.URI;

/**
 * Created by Farina on 25/6/2015.
 *
 * Classe abstrata de gerenciamento para o Download de PDFs no celular
 */
public abstract class PDFManager {

    private static final String FILESYSTEM_PREFIX = "file://";
    private static final String GOOGLE_DRIVE_PDF_READER_PREFIX = "http://drive.google.com/viewer?url=";
    private static final String HTML_MIME_TYPE = "text/html";
    private static final String PDF_MIME_TYPE = "application/pdf";
    private String WEBSERVICE_PREFIX = "http://rederaras.org/webservice" +
            "/app/webroot/files/";
    private Context context;
    private DownloadManager downloadManager;

    protected PDFManager(Context appContext, String filesFolder){
        WEBSERVICE_PREFIX += filesFolder;
        this.context = appContext;
    }

    public static String getPdfMimeType() {
        return PDF_MIME_TYPE;
    }

    public static String getGoogleDriveReaderPrefix() {
        return GOOGLE_DRIVE_PDF_READER_PREFIX;
    }

    public static String getHtmlMimeType() {
        return HTML_MIME_TYPE;
    }

    public static boolean hasPDFOpener( Context context ) {
        Intent i = new Intent( Intent.ACTION_VIEW );
        final File tempFile = new File( context.getExternalFilesDir( Environment.DIRECTORY_DOWNLOADS ), "test.pdf" );
        i.setDataAndType( Uri.fromFile(tempFile), PDF_MIME_TYPE );
        return context.getPackageManager().queryIntentActivities( i, PackageManager.MATCH_DEFAULT_ONLY ).size() > 0;
    }

    public static boolean hasFileInLocal(String protocolName) {
        String filePath = getLocalPDFPath(protocolName);
        File file = new File(URI.create(filePath));
        if(file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public void downloadPDF(String pdfName, String pdfFolder) {

        String pdfURL = buildPDFLink(pdfName, pdfFolder);
        Log.d("PDFURL:", pdfURL);
        downloadManager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
        Request request = new Request(Uri.parse(pdfURL));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, pdfName);

        long downloadID = downloadManager.enqueue(request);
    }

    public String buildPDFLink(String pdfName, String pdfFolder) {
        return WEBSERVICE_PREFIX + pdfFolder + "/" + pdfName;
    }

    public static String getLocalPDFPath(String pdfName) {
       return FILESYSTEM_PREFIX + Environment.getExternalStorageDirectory().getPath()
                + "/" + Environment.DIRECTORY_DOWNLOADS
                + "/" + pdfName;
    }
}
