package li.zhuoyuan.cizhi.application;

import android.app.Application;

import cn.smssdk.SMSSDK;

/**
 * Created by 卓原 on 2017/3/1.
 * email: zhuoyuan93@gmail.com
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SMSSDK.initSDK(this, "1bb687ecb067b", "f65c038558f0ca2cd455e655ae893cb5");
    }
}
