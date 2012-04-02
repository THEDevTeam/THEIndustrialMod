package com.thedevteam.theicport.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.URLConnection;
import java.net.URL;

public class Downloader {

    protected static int count, total, itemCount, itemTotal;
    protected static long lastModified;
    protected static String error;
    protected static boolean cancelled;

    public Downloader() { }

    public synchronized void cancel() {
        cancelled = true;
    }

    public static File fetch(String location, String filename) {
    	String destination = "plugins/THEIndustrialMod/Parts" + File.separator + filename;
        try {
            cancelled = false;
            count = total = itemCount = itemTotal = 0;
            if (cancelled) 
                return null;

            System.out.println("   + " + filename + " (" + getFileSize(location) + ")");
            download(location, filename);
            System.out.println("   - " + filename + " finished.");
            return new File(destination);
        } catch (IOException ex) {
            System.out.println("[iConomy] Error Downloading File: " + ex);
            return null;
        }
    }

    protected static synchronized void download(String location, String filename)throws IOException {
        URLConnection connection = new URL(location).openConnection();
        connection.setUseCaches(false);
        lastModified = connection.getLastModified();
        String destination = "plugins/THEIndustrialMod/Parts" + File.separator + filename;
        File parentDirectory = new File(destination).getParentFile();
        if (parentDirectory != null) 
            parentDirectory.mkdirs();

        InputStream in = connection.getInputStream();
        OutputStream out = new FileOutputStream(destination);
        byte[] buffer = new byte[65536];
        int currentCount = 0;

        for (;;) {
            if (cancelled)
                break;

            int count = in.read(buffer);
            if (count < 0)
                break;

            out.write(buffer, 0, count);
            currentCount += count;
        }

        in.close();
        out.close();
        
    }

    private static String getFileSize(String location) throws IOException {
        URLConnection connection = new URL(location).openConnection();
        return readableSize(connection.getContentLength());
    }
    
    public static String readableSize(long size) {
        String[] units = new String[] { "B", "KB", "MB", "GB", "TB", "PB" };
        int mod = 1024, i;

        for (i = 0; size > mod; i++)
            size /= mod;

        return Math.round(size) + " " + units[i];
    }

    public long getLastModified() {
        return lastModified;
    }
}
