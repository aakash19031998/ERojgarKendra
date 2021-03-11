package com.twocoms.rojgarkendra.registrationscreen.controler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.twocoms.rojgarkendra.R;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class VerifyOtpActivity extends AppCompatActivity {

    EditText et1, et2, et3, et4;
    boolean isOtpExpire = false;
    CountDownTimer countDownTimer;
    TextView otp_timer, didnotgetotptext, resendotpbutton;
    TextView verifybutton;
    ImageView backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        init();
        startTimer();
    }

    void init() {
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);
        et1.addTextChangedListener(new GenericTextWatcher(et1));
        et2.addTextChangedListener(new GenericTextWatcher(et2));
        et3.addTextChangedListener(new GenericTextWatcher(et3));
        et4.addTextChangedListener(new GenericTextWatcher(et4));
        otp_timer = findViewById(R.id.otp_timer);
        didnotgetotptext = findViewById(R.id.didnotgetotptext);
        resendotpbutton = findViewById(R.id.resendotpbutton);
        verifybutton = findViewById(R.id.verifybutton);
        verifybutton.setBackground(getResources().getDrawable(R.drawable.verify_disble));
        backbutton = findViewById(R.id.backbutton);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        resendotpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer();
            }
        });

        verifybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callVerifyOTP();
            }
        });

        String OTP ="123456";
        et1.setText(String.valueOf(OTP.charAt(1)));
    }

    void showOtpTimer() {
        resendotpbutton.setVisibility(View.GONE);
        didnotgetotptext.setVisibility(View.INVISIBLE);
        otp_timer.setVisibility(View.VISIBLE);
    }

    void showDidnotgetOtp() {
        resendotpbutton.setVisibility(View.VISIBLE);
        didnotgetotptext.setVisibility(View.VISIBLE);
        otp_timer.setVisibility(View.INVISIBLE);
    }


    void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        showDidnotgetOtp();
    }


    void resendOtpClicked(){

    }

    void startTimer() {
        otp_timer.setVisibility(View.VISIBLE);
        isOtpExpire = false;
        long timeInMilliseconds;
        timeInMilliseconds = 1 * 60000;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(timeInMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                String text = String.format(Locale.getDefault(), "Your OTP will expire in %02d: %02d min",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);

                otp_timer.setText(text);
            }

            @Override
            public void onFinish() {
//                otp_timer.setText("OTP has expired!");
                isOtpExpire = true;
                showDidnotgetOtp();
            }
        }.start();
        showOtpTimer();
    }


    public class GenericTextWatcher implements TextWatcher {
        private View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String text = editable.toString();
            switch (view.getId()) {

                case R.id.et1:
                    if (text.length() == 1)
                        et2.requestFocus();

                    break;
                case R.id.et2:
                    if (text.length() == 1)
                        et3.requestFocus();
                    else if (text.length() == 0)
                        et1.requestFocus();
                    break;
                case R.id.et3:
                    if (text.length() == 1)
                        et4.requestFocus();
                    else if (text.length() == 0)
                        et2.requestFocus();
                    break;
                case R.id.et4:
                    if (text.length() == 0)
                        et3.requestFocus();
                    break;


            }

            enableDisableButton();
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }
    }


    void enableDisableButton() {
        if (!et1.getText().toString().equals("") &&
                !et2.getText().toString().equals("") &&
                !et3.getText().toString().equals("") &&
                !et4.getText().toString().equals("")) {

            verifybutton.setBackground(getResources().getDrawable(R.drawable.verify_enable));

        } else {
            verifybutton.setBackground(getResources().getDrawable(R.drawable.verify_disble));
        }
    }


    void verifyOtpClicked(){

    }


    private BroadcastReceiver mServiceReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent)
        {
            try {


                //Extract your data - better to use constants...
                String incomingSms = intent.getStringExtra("incomingOTP");
                Log.e("OTP sended to Activity", incomingSms);
                et1.setText(intent.getStringExtra("OTP1"));
//            et1.setText(intent.getStringExtra("OTP2"));
//            et1.setText(intent.getStringExtra("OTP3"));
//            et1.setText(intent.getStringExtra("OTP4"));
                callVerifyOTP();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();

        try {
            if(mServiceReceiver != null){
                unregisterReceiver(mServiceReceiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void callVerifyOTP(){
        Intent intent = new Intent(this,RegisterUserDataActivity.class);
        startActivity(intent);
    }


    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.SmsReceiver");
        registerReceiver(mServiceReceiver , filter);
    }
}