package top.zhujm.searchapp;

import android.app.Application;

import com.dbflow5.config.FlowManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }
}
