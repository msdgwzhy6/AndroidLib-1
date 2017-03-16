package com.soubu.androidlib.util;

import android.content.Context;
import android.os.Environment;

import java.io.IOException;

/**
 * 异常处理工具
 * Created by dingsigang on 16-8-2.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    private static final boolean DEBUG = true;

    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/CrashTest/log/";
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFFIX = ".trace";

    private static CrashHandler sInstance = new CrashHandler();
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private Context mContext;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return sInstance;
    }

    public void init(Context context) {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }

    /**
     * 这个是最关键的函数，当程序中有未被捕获的异常，系统将会自动调用#uncaughtException方法
     * thread为出现未捕获异常的线程，ex为未捕获的异常，有了这个ex，我们就可以得到异常信息。
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            //导出异常信息到SD卡中
            dumpExceptionToSDCard(ex);
            uploadExceptionToServer();
            //这里可以通过网络上传异常信息到服务器，便于开发人员分析日志从而解决bug
        } catch (IOException e) {
            e.printStackTrace();
        }

        ex.printStackTrace();

        //如果系统提供了默认的异常处理器，则交给系统去结束我们的程序，否则就由我们自己结束自己
        if (mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(thread, ex);
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    private void dumpExceptionToSDCard(Throwable ex) throws IOException {
        //伪代码 本方法用于实现将错误信息存储到SD卡中
    }



    private void uploadExceptionToServer() {
        //伪代码 本方法用于将错误信息上传至服务器
    }




//    private final String TAG = CrashHandler.class.getSimpleName();
//
//    private static Thread.UncaughtExceptionHandler defaultHandler = null;
//
//    private Context context = null;
//
//    public CrashHandler(Context context) {
//        this.context = context;
//    }
//
//    /**
//     * 初始化,设置该CrashHandler为程序的默认处理器
//     */
//    public static void init(CrashHandler crashHandler) {
//        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
//        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
//    }
//
//    @Override
//    public void uncaughtException(Thread thread, Throwable ex) {
//        WriteLogUtil.e(TAG, ex.toString());
//        WriteLogUtil.e(TAG, getCrashDeviceInfo());
//        WriteLogUtil.e(TAG, getCrashInfo(ex));
//        // 调用系统错误机制
//        defaultHandler.uncaughtException(thread, ex);
//    }
//
//    /**
//     * 获取应用崩溃的详细信息
//     * @param ex
//     * @return
//     */
//    public String getCrashInfo(Throwable ex){
//        Writer writer = new StringWriter();
//        PrintWriter printWriter = new PrintWriter(writer);
//        ex.setStackTrace(ex.getStackTrace());
//        ex.printStackTrace(printWriter);
//        return writer.toString();
//    }
//    public String getCrashDeviceInfo(){
//        String versionName = AppUtil.getVersionName(context);
//        String model = android.os.Build.MODEL;
//        String androidVersion = android.os.Build.VERSION.RELEASE;
//        String manufacturer = android.os.Build.MANUFACTURER;
//        return versionName + "  " +
//                model + "  " +
//                androidVersion + "  " +
//                manufacturer;
//    }

}