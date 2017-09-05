package com.jsj.multithreaddemo;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @author jsj
 * @since 2017/9/5
 */

public class UploadImgService extends IntentService {

    private static final String ACTION_UPLOAD_IMG = "com.jsj.handlerthreaddemo.action.UPLOAD_IMAGE";
    public static final String EXTRA_IMG_PATH = "com.jsj.handlerthreaddemo.extra.IMG_PATH";


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     *             给线程设置名字
     */
    public UploadImgService(String name) {
        super(name);
    }

    public UploadImgService() {
        super("UploadImgService");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.d(this.getClass().getSimpleName(), "onStartCommand ==== ");
        return super.onStartCommand(intent, flags, startId);
    }

    public static void startUploadImg(Context context, String path) {
        Intent intent = new Intent(context, UploadImgService.class);
        intent.setAction(ACTION_UPLOAD_IMG);
        intent.putExtra(EXTRA_IMG_PATH, path);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPLOAD_IMG.equals(action)) {
                final String path = intent.getStringExtra(EXTRA_IMG_PATH);//获取要处理的数据
                handleUploadImg(path);
            }
        }
    }

    private void handleUploadImg(String path) {
        try {
            //模拟上传耗时
            Thread.sleep(3000);

            Intent intent = new Intent(IntentServiceActivity.UPLOAD_RESULT);
            intent.putExtra(EXTRA_IMG_PATH, path);
            sendBroadcast(intent);//将处理完的数据通过广播的形式发送出去

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TAG", "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "onDestroy");
    }
}
