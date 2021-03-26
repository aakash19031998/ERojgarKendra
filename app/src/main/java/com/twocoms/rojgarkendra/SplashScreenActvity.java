package com.twocoms.rojgarkendra;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SubscriptionPlan;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
//import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.dashboardscreen.controler.DashboardActivity;
import com.twocoms.rojgarkendra.global.model.AppConstant;
import com.twocoms.rojgarkendra.global.model.GlobalPreferenceManager;
import com.twocoms.rojgarkendra.registrationscreen.controler.GetStartedActivity;
import com.twocoms.rojgarkendra.registrationscreen.controler.RegisterUserDataActivity;
import com.twocoms.rojgarkendra.registrationscreen.controler.VerifyMobileNumberActivity;

public class SplashScreenActvity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        FirebaseApp.initializeApp(this);

        //FirebaseApp.initializeApp(this);
//        FirebaseAnalytics.getInstance().get
        splashScreenDisplay(this);
       Log.v( "FCMTOKEN",GlobalPreferenceManager.getStringForKey(SplashScreenActvity.this,AppConstant.KEY_DEVICE_TOKEN,""));
        //throw new RuntimeException("Test Crash");
    }

    void splashScreenDisplay(final Context context) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    if (GlobalPreferenceManager.getStringForKey(context, AppConstant.KEY_CONTACT_VERIFIED, "").equals("1") &&
                            GlobalPreferenceManager.getStringForKey(context, AppConstant.KEY_IS_REGISTER, "").equals("Y")) {
                        Intent mainIntent = new Intent(SplashScreenActvity.this, DashboardActivity.class);
                        startActivity(mainIntent);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashScreenActvity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    }
            }
        }, 3000);
    }


    ////Digit hash code
//    7cvbP7dQKy2

}