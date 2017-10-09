package com.example.jsoup.jsoupdemo.base;

/**
 * User: Administrator
 * Date: 2016-12-26 {HOUR}:15
 */

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.jsoup.jsoupdemo.MyBaseApplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CrashHandler implements UncaughtExceptionHandler {

    private static CrashHandler instance;
    private Context mContext;
    private UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;

    public static CrashHandler getInstance() {
        if (instance == null) {
            instance = new CrashHandler();
        }
        return instance;
    }

    private CrashHandler() {
    }

    public void init(Context ctx) {
        this.mContext = ctx;
        mDefaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 核心方法，当程序crash 会回调此方法， Throwable中存放这错误日志
     */
    @Override
    public void uncaughtException(Thread arg0, Throwable arg1) {

        String logPath;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            logPath = mContext.getExternalFilesDir(null)
                    .getAbsolutePath()
                    + File.separator
                    + File.separator
                    + "log";

            File file = new File(logPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            try {
                FileWriter fw = new FileWriter(logPath + File.separator
                        + new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()) + ".log", true);
                fw.write(new Date() + "\n");
                // 错误信息
                // 这里还可以加上当前的系统版本，机型型号 等等信息
                StackTraceElement[] stackTrace = arg1.getStackTrace();
                fw.write(arg1.getMessage() + "\n");
                for (int i = 0; i < stackTrace.length; i++) {
                    fw.write("file:" + stackTrace[i].getFileName() + " class:"
                            + stackTrace[i].getClassName() + " method:"
                            + stackTrace[i].getMethodName() + " line:"
                            + stackTrace[i].getLineNumber() + "\n");
                }
                fw.write("\n");
                fw.close();
                // 上传错误信息到服务器
                // uploadToServer();
            } catch (IOException e) {
                Log.e("crash handler", "load file failed...", e.getCause());
            }
        }
        mDefaultUncaughtExceptionHandler.uncaughtException(arg0, arg1);
    }

}