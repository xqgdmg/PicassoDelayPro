package com.example.qhsj.picassodelaypro;

import android.app.Application;
import android.content.Context;

/**
 * Created by Chris on 2017/5/11.
 */
public class MyApplication extends Application{
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext =getApplicationContext();
    }

    public static Context getContext(){
        return mContext;
    }
}
