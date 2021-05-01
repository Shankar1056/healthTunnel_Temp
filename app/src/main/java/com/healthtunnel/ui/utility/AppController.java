package com.healthtunnel.ui.utility;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


public class AppController extends Application implements Application.ActivityLifecycleCallbacks {
    private static AppController ourInstance = null;
    public NetworkStateMonitor networkStateMonitor;
    private Activity activity;

    public static AppController getInstance() {
        return ourInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ourInstance = this;
        networkStateMonitor = new NetworkStateMonitor(getApplicationContext());

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

       /* new Instabug.Builder(this, "6e23078d36d69c2dc4142cfc284f53c4")
                .setInvocationEvents(
                        InstabugInvocationEvent.SHAKE,
                        InstabugInvocationEvent.FLOATING_BUTTON)
                .build();*/
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        this.activity = null;
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    public Activity getActivity() {
        return activity;
    }
}
