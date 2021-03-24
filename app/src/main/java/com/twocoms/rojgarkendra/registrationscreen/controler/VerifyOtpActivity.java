package com.twocoms.rojgarkendra.registrationscreen.controler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.dashboardscreen.controler.DashboardActivity;
import com.twocoms.rojgarkendra.global.model.AppConstant;
import com.twocoms.rojgarkendra.global.model.CommonMethod;
import com.twocoms.rojgarkendra.global.model.GlobalPreferenceManager;
import com.twocoms.rojgarkendra.global.model.ServiceHandler;
import com.twocoms.rojgarkendra.global.model.Validation;
import com.twocoms.rojgarkendra.myprofile.controler.UserProfileActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class VerifyOtpActivity extends AppCompatActivity {

    EditText et1, et2, et3, et4;
    boolean isOtpExpire = false;
    CountDownTimer countDownTimer;
    TextView otp_timer, didnotgetotptext, resendotpbutton;
    TextView verifybutton;
    ImageView backbutton;
    String otpStr, enteredOtpStr, mobile_no;
    boolean isVerifiedEnabled = false;
    TextView explainableText;
    String android_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        init();
        startTimer();
    }

    void init() {
        Intent intent = getIntent();
        otpStr = intent.getStringExtra("otp");
        mobile_no = intent.getStringExtra("mobile_no");
        explainableText = (TextView) findViewById(R.id.explainabletext);
        explainableText.setText("Please wait.\nWe will auto verify the OTP sent to +91" + mobile_no);
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
                resendOtp();
            }
        });
        verifybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isVerifiedEnabled) {
                    callVerifyOTP();
                }
            }
        });

        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
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


    void resendOtpClicked() {

    }

    void startTimer() {
        otp_timer.setVisibility(View.VISIBLE);
        isOtpExpire = false;
        long timeInMilliseconds;
        timeInMilliseconds = 5 * 60000;
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

            verifybutton.setBackground(getResources().getDrawable(R.drawable.sign_up_enable));
            isVerifiedEnabled = true;


        } else {
            verifybutton.setBackground(getResources().getDrawable(R.drawable.sign_up_disble));
            isVerifiedEnabled = false;
        }
    }


//    void verifyOtpClicked(){
//
//    }


    private BroadcastReceiver mServiceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String[] splitted = intent.getStringExtra("OTP").split("(?<=.)");
                et1.setText(splitted[0]);
                et2.setText(splitted[1]);
                et3.setText(splitted[2]);
                et4.setText(splitted[3]);
                et4.requestFocus();
                callVerifyOTP();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();

        try {
            if (mServiceReceiver != null) {
                unregisterReceiver(mServiceReceiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void callVerifyOTP() {
        enteredOtpStr = et1.getText().toString() + et2.getText().toString() + et3.getText().toString() + et4.getText().toString();
        Log.v("entered Otp", enteredOtpStr);

        if (Validation.checkIfEmptyOrNot(et1.getText().toString()) || Validation.checkIfEmptyOrNot(et2.getText().toString()) || Validation.checkIfEmptyOrNot(et3.getText().toString()) || Validation.checkIfEmptyOrNot(et4.getText().toString())) {
            CommonMethod.showToast("Please Enter Valid Otp", VerifyOtpActivity.this);
        } else {
//            Intent intent = new Intent(this, RegisterUserDataActivity.class);
//            startActivity(intent);
            verifyOtp();
        }
    }


    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.SmsReceiver");
        registerReceiver(mServiceReceiver, filter);
    }

    void verifyOtp() {
        final JSONObject Json = new JSONObject();
        try {
            Json.put(AppConstant.KEY_CONTACT, mobile_no);
            Json.put(AppConstant.KEY_OTP, enteredOtpStr);
            Json.put(AppConstant.KEY_DEVICE_ID, android_id);
            Json.put(AppConstant.KEY_OS_TYPE, "A");
            Json.put(AppConstant.KEY_NOTIFICATION_ID, GlobalPreferenceManager.getStringForKey(VerifyOtpActivity.this, AppConstant.KEY_DEVICE_TOKEN, ""));
            //Json.put("Messege","HAVELLS APP VERIFICATION");
            Log.v("JSONURL", Json.toString());
            ServiceHandler serviceHandler = new ServiceHandler(VerifyOtpActivity.this);
            serviceHandler.StringRequest(Request.Method.POST, Json.toString(), AppConstant.VERIFY_OTP, true, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {

                    Log.v("Response", result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.getBoolean("success")) {
                            JSONObject dataStr = jsonObject.getJSONObject("data");
                            JSONObject otpJson = dataStr.getJSONObject("otpInfo");
                            String contactInfo = otpJson.getString("contact");
                            otpStr = otpJson.getString("otp");
                            GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_CONTACT_VERIFIED, "1");
                            // String msgStr = jsonObject.getString("message");
                            if (dataStr.has("is_register") && dataStr.has("eduErp")) {
                                if (dataStr.getString("is_register").equals("N")) {
                                    if (dataStr.getString("eduErp").equals("Y")) {
                                        /*Save data in SharePref*/
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_NAME, dataStr.getString(AppConstant.KEY_NAME));
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_CONTACT, dataStr.getString(AppConstant.KEY_CONTACT));
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_EMAIL_ID, dataStr.getString(AppConstant.KEY_EMAIL_ID));
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.STATE_ID, dataStr.getString(AppConstant.STATE_ID));
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_STATE_NAME, dataStr.getString(AppConstant.KEY_STATE_NAME));
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_CITY, dataStr.getString(AppConstant.KEY_CITY));
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_DOB, dataStr.getString(AppConstant.KEY_DOB));
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_GENDER, dataStr.getString(AppConstant.KEY_GENDER));
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_QUALIFICATION_TYPE, dataStr.getString(AppConstant.KEY_QUALIFICATION_TYPE));
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_EXPERIANCE_YEARS, dataStr.getString(AppConstant.KEY_EXPERIANCE_YEARS));
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_EXPERIANCE_MONTH, dataStr.getString(AppConstant.KEY_EXPERIANCE_MONTH));
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_SALARY, dataStr.getString(AppConstant.KEY_SALARY));
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_COURSE_NAME, dataStr.getString(AppConstant.KEY_COURSE_NAME));
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_PROJECT_NAME, dataStr.getString(AppConstant.KEY_PROJECT_NAME));
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_BATCH_NAME, dataStr.getString(AppConstant.KEY_BATCH_NAME));
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_CENTRE_NAME, dataStr.getString(AppConstant.KEY_CENTRE_NAME));
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_COURSE_ID, dataStr.getString(AppConstant.KEY_COURSE_ID));
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_PROJECT_ID, dataStr.getString(AppConstant.KEY_PROJECT_ID));
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_BATCH_ID, dataStr.getString(AppConstant.KEY_BATCH_ID));
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_CENTRE_ID, dataStr.getString(AppConstant.KEY_CENTRE_ID));
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_IS_REGISTER, dataStr.getString(AppConstant.KEY_IS_REGISTER));
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_LANGUAGE_KNOWN, dataStr.getString(AppConstant.KEY_LANGUAGE_KNOWN));
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_ROLE, dataStr.getString(AppConstant.KEY_ROLE));
                                    }
                                    navigateToRegister(contactInfo, dataStr.getString("eduErp"));
                                } else {
                                    /*Data Save*/
                                    GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_USER_ID, dataStr.getString(AppConstant.KEY_USER_ID));
                                    GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_NAME, dataStr.getString(AppConstant.KEY_NAME));
                                    GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_CONTACT, dataStr.getString(AppConstant.KEY_CONTACT));
                                    GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_CONTACT_VERIFIED, dataStr.getString(AppConstant.KEY_CONTACT_VERIFIED));
                                    GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_EMAIL_ID, dataStr.getString(AppConstant.KEY_EMAIL_ID));
                                    GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.STATE_ID, dataStr.getString(AppConstant.STATE_ID));
                                    GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_CITY, dataStr.getString(AppConstant.KEY_CITY));
                                    GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_DOB, dataStr.getString(AppConstant.KEY_DOB));
                                    GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_GENDER, dataStr.getString(AppConstant.KEY_GENDER));
                                    GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_QUALIFICATION_TYPE, dataStr.getString(AppConstant.KEY_QUALIFICATION_TYPE));
                                    GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_EXPERIANCE_YEARS, dataStr.getString(AppConstant.KEY_EXPERIANCE_YEARS));
                                    GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_EXPERIANCE_MONTH, dataStr.getString(AppConstant.KEY_EXPERIANCE_MONTH));
                                    GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_SALARY, dataStr.getString(AppConstant.KEY_SALARY));
//                                    GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_PROFILE_URL, dataStr.getString(AppConstant.KEY_PROFILE_URL));
//                                    GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_RESUME_URL, dataStr.getString(AppConstant.KEY_RESUME_URL));
                                    if (dataStr.has(AppConstant.KEY_PROFILE_URL)) {
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_PROFILE_URL, dataStr.getString(AppConstant.KEY_PROFILE_URL));
                                    }
                                    else {
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_PROFILE_URL, "");
                                    }
                                    if (dataStr.has(AppConstant.KEY_RESUME_URL)) {
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_RESUME_URL, dataStr.getString(AppConstant.KEY_RESUME_URL));
                                    }
                                    else {
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_RESUME_URL, "");
                                    }
                                    GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_REFERAL_CODE, dataStr.getString(AppConstant.KEY_REFERAL_CODE));
                                    GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_REFERAL_CODE, dataStr.getString(AppConstant.KEY_REFERAL_CODE));
                                    GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_IS_REGISTER, dataStr.getString(AppConstant.KEY_IS_REGISTER));
                                    // GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_DEVICE_ID, dataStr.getString(AppConstant.KEY_DEVICE_ID));
                                    //   GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_OS_TYPE, dataStr.getString(AppConstant.KEY_OS_TYPE));
                                    //    GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_NOTIFICATION_ID, dataStr.getString(AppConstant.KEY_NOTIFICATION_ID));
                                    GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_IS_EDURP, dataStr.getString(AppConstant.KEY_IS_EDURP));
                                    if (dataStr.getString("eduErp").equals("Y")) {
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_COURSE_NAME, dataStr.getString(AppConstant.KEY_COURSE_NAME));
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_PROJECT_NAME, dataStr.getString(AppConstant.KEY_PROJECT_NAME));
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_BATCH_NAME, dataStr.getString(AppConstant.KEY_BATCH_NAME));
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_CENTRE_NAME, dataStr.getString(AppConstant.KEY_CENTRE_NAME));
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_COURSE_ID, dataStr.getString(AppConstant.KEY_COURSE_ID));
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_PROJECT_ID, dataStr.getString(AppConstant.KEY_PROJECT_ID));
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_BATCH_ID, dataStr.getString(AppConstant.KEY_BATCH_ID));
                                        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this, AppConstant.KEY_CENTRE_ID, dataStr.getString(AppConstant.KEY_CENTRE_ID));
                                    }
                                    navigateToDashBoard();
                                }

                            } else {
                                navigateToRegister(contactInfo, "N");
                            }
                        } else {
                            String msgStr = jsonObject.getString("message");
                            CommonMethod.showToast(msgStr, VerifyOtpActivity.this);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        CommonMethod.showToast(AppConstant.SOMETHING_WENT_WRONG, VerifyOtpActivity.this);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    void navigateToRegister(String mobileNumber, String isEduRp) {
        Intent intent = new Intent(VerifyOtpActivity.this, RegisterUserDataActivity.class);
        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this,AppConstant.KEY_IS_EDURP,isEduRp);
        GlobalPreferenceManager.saveStringForKey(VerifyOtpActivity.this,AppConstant.KEY_CONTACT,mobileNumber);
        startActivity(intent);
        finish();
    }

    void navigateToDashBoard() {
        Intent intent = new Intent(VerifyOtpActivity.this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    void resendOtp() {
        final JSONObject Json = new JSONObject();

        try {
            Json.put("contact", mobile_no);
            Json.put("resend", 1);
            //Json.put("Messege","HAVELLS APP VERIFICATION");
            Log.v("JSONURL", Json.toString());
            ServiceHandler serviceHandler = new ServiceHandler(VerifyOtpActivity.this);
            serviceHandler.StringRequest(Request.Method.POST, Json.toString(), AppConstant.RESEND_OTP, true, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {

                    Log.v("Response", result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.getBoolean("success")) {
                            JSONObject dataStr = jsonObject.getJSONObject("data");
                            otpStr = dataStr.getString("otp");
                            String msgStr = jsonObject.getString("message");
                            setDataEmpty();
                            startTimer();
                        } else {
                            String msgStr = jsonObject.getString("message");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    void setDataEmpty() {
        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");
        et1.requestFocus();
    }


}