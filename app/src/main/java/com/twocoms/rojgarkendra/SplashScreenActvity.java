package com.twocoms.rojgarkendra;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SubscriptionPlan;

import androidx.appcompat.app.AppCompatActivity;

import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.global.model.AppConstant;
import com.twocoms.rojgarkendra.global.model.GlobalPreferenceManager;
import com.twocoms.rojgarkendra.registrationscreen.controler.RegisterUserDataActivity;
import com.twocoms.rojgarkendra.registrationscreen.controler.VerifyMobileNumberActivity;

public class SplashScreenActvity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splashScreenDisplay(this);
    }

    void splashScreenDisplay(final Context context) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

//                if (GlobalPreferenceManager.getBooleanForKey(context, AppConstant.IS_VERIFIED_MAIN, false)) {
//                    if (getIntent().getStringExtra("comingFrom") != null && getIntent().getStringExtra("comingFrom").equals("notification")) {
//                        Intent mainIntent = new Intent(context, DermatologyConferenceCalendarActivity.class);
//                        startActivity(mainIntent);
//                        finish();
//                    } else {
//                        Intent mainIntent = new Intent(SplashScreenActivity.this, DashBoardActivity.class);
//                        startActivity(mainIntent);
//                        finish();
//                    }
//                } else {
                    Intent mainIntent = new Intent(SplashScreenActvity.this, RegisterUserDataActivity.class);
                    startActivity(mainIntent);
                    finish();
//                }


            }
        }, 3000);
    }



    ////Digit hash code
//    7cvbP7dQKy2

}