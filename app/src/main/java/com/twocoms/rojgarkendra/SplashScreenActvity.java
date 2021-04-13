package com.twocoms.rojgarkendra;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SubscriptionPlan;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
//import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
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
        splashScreenDisplay(this);
       Log.v( "FCMTOKEN",GlobalPreferenceManager.getStringForKey(SplashScreenActvity.this,AppConstant.KEY_DEVICE_TOKEN,""));
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                        }
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user == null
                                && deepLink != null
                                && deepLink.getBooleanQueryParameter("invitedby", false)) {
                            String referrerUid = deepLink.getQueryParameter("invitedby");
                            GlobalPreferenceManager.saveStringForKey(SplashScreenActvity.this,AppConstant.KEY_INVITE_CODE,referrerUid);
                            Log.v("ReferereId Main :",referrerUid);
                          //  createAnonymousAccountWithReferrerInfo(referrerUid);
                        }
                    }
                });

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


//    V1M6BXL8


    ////Digit hash code
//    7cvbP7dQKy2

}