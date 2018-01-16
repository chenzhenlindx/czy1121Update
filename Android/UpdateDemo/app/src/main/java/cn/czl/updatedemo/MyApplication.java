package cn.czl.updatedemo;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * Created by LENOVO on 2018/1/16.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
