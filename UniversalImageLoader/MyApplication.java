package com.example.imageloadertest.app;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by ggz on 2018/1/11.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(getApplicationContext());

        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);
    }
}
