package examples.com.structuredrequestsusingvolley;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by youssef2016 on 29/1/2018.
 */

public class AppClass extends Application {
    private static AppClass appClass;
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        appClass = this;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mRequestQueue.start();
    }

    public static synchronized AppClass getInstance() {
        return appClass;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
