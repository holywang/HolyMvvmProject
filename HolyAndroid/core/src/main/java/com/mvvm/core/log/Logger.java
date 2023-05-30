package com.mvvm.core.log;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author rg wang
 * created on  2023/5/25
 */
public class Logger {
    private static final String TAG = "Logger";
    private static final String LOG_DIR = "logs";
    private static final String LOG_FILE_PREFIX = "log";
    private static final String LOG_FILE_SUFFIX = ".txt";
    private static final long MAX_LOG_FILE_SIZE = 1024 * 1024; // 1MB

    private static String sCurrentLogFileName;
    private static FileOutputStream sCurrentLogFileOutputStream;
    private static long sCurrentLogFileSize;

    public static void init(Context applicationContext) {
        File logDir = new File(
                applicationContext.getExternalFilesDir(null).getAbsolutePath() +
                        "/Android/data/" +
                        applicationContext.getPackageName());
        if (!logDir.exists()) {
            logDir.mkdirs();
        }

        File log2Dir = new File(logDir.getPath() + "/" +
                LOG_DIR);
        if (!log2Dir.exists()) {
            log2Dir.mkdirs();
        }

        String currentLogFileName = getCurrentLogFileName();
        File currentLogFile = new File(log2Dir, currentLogFileName);
        try {
            if (!currentLogFile.exists()) {
                FileWriter writer = new FileWriter(currentLogFile);
                writer.write(" ");
                writer.close();
            }
            sCurrentLogFileOutputStream = new FileOutputStream(currentLogFile, true);
            sCurrentLogFileSize = currentLogFile.length();
            sCurrentLogFileName = currentLogFileName;
        } catch (IOException e) {
            Log.e(TAG, "Failed to open log file: " + currentLogFileName, e);
        }
    }

    public static void d(String tag, String msg) {
        Log.d(tag, msg);
        writeLogToFile(tag, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
        writeLogToFile(tag, msg);
    }

    private static synchronized void writeLogToFile(String tag, String msg) {
        if (sCurrentLogFileOutputStream == null) {
            return;
        }

        String logLine = String.format(Locale.getDefault(), "[%s] %s: %s\n", getCurrentTime(), tag, msg);
        byte[] logLineBytes = logLine.getBytes();

        if (sCurrentLogFileSize + logLineBytes.length > MAX_LOG_FILE_SIZE) {
            rotateLogFile();
        }

        try {
            sCurrentLogFileOutputStream.write(logLineBytes);
            sCurrentLogFileOutputStream.flush();
            sCurrentLogFileSize += logLineBytes.length;
        } catch (IOException e) {
            Log.e(TAG, "Failed to write log line to file: " + logLine, e);
        }
    }

    private static synchronized void rotateLogFile() {
        try {
            sCurrentLogFileOutputStream.close();
        } catch (IOException e) {
            Log.e(TAG, "Failed to close log file: " + sCurrentLogFileName, e);
        }

        String newLogFileName = getCurrentLogFileName();
        File newLogFile = new File(Environment.getExternalStorageDirectory(), newLogFileName);
        try {
            sCurrentLogFileOutputStream = new FileOutputStream(newLogFile, true);
            sCurrentLogFileSize = 0;
            sCurrentLogFileName = newLogFileName;
        } catch (IOException e) {
            Log.e(TAG, "Failed to open log file: " + newLogFileName, e);
        }
    }

    private static String getCurrentLogFileName() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());
        return LOG_FILE_PREFIX + "_" + currentDate + LOG_FILE_SUFFIX;
    }

    private static String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(Calendar.getInstance().getTime());
    }
}
