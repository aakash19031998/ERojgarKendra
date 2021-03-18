
package com.twocoms.rojgarkendra.registrationscreen.controler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.messaging.FirebaseMessaging;
import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.global.model.AppConstant;
import com.twocoms.rojgarkendra.global.model.CommonMethod;
import com.twocoms.rojgarkendra.global.model.GlobalPreferenceManager;
import com.twocoms.rojgarkendra.global.model.ServiceHandler;
import com.twocoms.rojgarkendra.global.model.Validation;

import org.json.JSONException;
import org.json.JSONObject;
//import com.twocoms.rojgarkendra.global.model.AppSignatureHelper;

public class VerifyMobileNumberActivity extends AppCompatActivity {

    TextView tcmaintext;
    TextInputEditText mobile_edit_text;
    RelativeLayout proceddbuttonlayout;
    TextView signupbutton;
    boolean isProceedEnabld = false;
    int RESOLVE_HINT = 1001;
    GoogleApiClient googleApiClient;
    String mobile_no;
    String otpStr;
    String android_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_mobile_number);
        init();
        getFCMToken();
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.CREDENTIALS_API)
                .build();
        customTextView(tcmaintext);
        mobile_edit_text.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() == 10) {
                    signupbutton.setBackground(getResources().getDrawable(R.drawable.sign_up_enable));
                    isProceedEnabld = true;
                } else {
                    signupbutton.setBackground(getResources().getDrawable(R.drawable.sign_up_disble));
                    isProceedEnabld = false;
                }

            }
        });
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               mobile_no = mobile_edit_text.getText().toString();
//                AppSignatureHelper appSignatureHelper = new AppSignatureHelper(VerifyMobileNumberActivity.this);
//                appSignatureHelper.getAppSignatures();
                if (Validation.checkIfEmptyOrNot(mobile_no)){
                    CommonMethod.showToast("Please Enter Mobile Number", VerifyMobileNumberActivity.this);
                }else if(!Validation.isValidMobileNumber(mobile_no)) {
                    CommonMethod.showToast("Enter Valid Mobile Number", VerifyMobileNumberActivity.this);
                } else {
                    proceedButtonClicked();
                }


            }
        });

        requestPhoneNumber();

        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.v("Device Id",android_id);

    }

    void proceedButtonClicked() {
        if (isProceedEnabld) {
            callVerifyOtpScreen();
            startSMSretreiver();
        }
    }

    void init() {
        tcmaintext = findViewById(R.id.tcmaintext);
        mobile_edit_text = findViewById(R.id.mobile_edit_text);
        proceddbuttonlayout = findViewById(R.id.proceddbuttonlayout);
        signupbutton = findViewById(R.id.signupbutton);

    }

    void getFCMToken(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Token", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        GlobalPreferenceManager.saveStringForKey(VerifyMobileNumberActivity.this, "firebasekey", token);

                        // Log and toast
                        //String msg = getString(R.string.token_id, token);
                        Log.d("Token", token);
//                        Toast.makeText(VerifyMobileNumberActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void customTextView(TextView view) {
        SpannableStringBuilder spanTxt = new SpannableStringBuilder(
                "By signing up you agree to 2COMS ");
        spanTxt.append("T&C");
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(getApplicationContext(), "Terms of services Clicked",
                        Toast.LENGTH_SHORT).show();
            }
        }, spanTxt.length() - "T&C".length(), spanTxt.length(), 0);
        spanTxt.append(" and ");
        spanTxt.setSpan(new ForegroundColorSpan(Color.BLACK), 41, spanTxt.length(), 0);
        spanTxt.append("Privacy Policy ");
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(getApplicationContext(), "Privacy Policy Clicked",
                        Toast.LENGTH_SHORT).show();
            }
        }, spanTxt.length() - " Privacy Policy".length(), spanTxt.length(), 0);
        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }

    void callVerifyOtpScreen() {
//        Intent intent = new Intent(this, VerifyOtpActivity.class);
//        startActivity(intent);
        verifyMobileNo();
    }


    public void requestPhoneNumber() {
        HintRequest hintRequest = new HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)
                .build();

        PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(
                googleApiClient, hintRequest);
        try {
            startIntentSenderForResult(intent.getIntentSender(),
                    RESOLVE_HINT, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    // Obtain the phone number from the result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESOLVE_HINT) {
            if (resultCode == RESULT_OK) {
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                // credential.getId();  <-- will need to process phone number string
//                Log.e("Mobile Number", credential.getId());
                String phoneNumber = credential.getId();
                if (phoneNumber.startsWith("+")) {
                    if (phoneNumber.length() == 13) {
                        String str_getMOBILE = phoneNumber.substring(3);
                        mobile_edit_text.setText(str_getMOBILE);
                    } else if (phoneNumber.length() == 14) {
                        String str_getMOBILE = phoneNumber.substring(4);
                        mobile_edit_text.setText(str_getMOBILE);
                    }
                } else {
                    mobile_edit_text.setText(phoneNumber);
                }
            }
        }
    }



    void startSMSretreiver(){
        SmsRetrieverClient client = SmsRetriever.getClient(this /* context */);
// Starts SmsRetriever, which waits for ONE matching SMS message until timeout
// (5 minutes). The matching SMS message will be sent via a Broadcast Intent with
// action SmsRetriever#SMS_RETRIEVED_ACTION.
        Task<Void> task = client.startSmsRetriever();

// Listen for success/failure of the start Task. If in a background thread, this
// can be made blocking using Tasks.await(task, [timeout]);
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e("Succesfully Started SMS","TRUE");
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Succesfully Started SMS","FALSE");
            }
        });
    }

//    void callApiForRegistration(){
//
//    }

    void verifyMobileNo() {

        final JSONObject Json = new JSONObject();

        try {
            Json.put(AppConstant.KEY_CONTACT, mobile_edit_text.getText().toString());
            //Json.put("Messege","HAVELLS APP VERIFICATION");
            Log.v("JSONURL", Json.toString());
            ServiceHandler serviceHandler = new ServiceHandler(VerifyMobileNumberActivity.this);
            serviceHandler.StringRequest(Request.Method.POST, Json.toString(), AppConstant.VERIFY_MOB_NO, true, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {

                    Log.v("Response",result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.getBoolean("success")){
                            JSONObject dataStr = jsonObject.getJSONObject("data");
                            otpStr = dataStr.getString("otp");
                            Intent intent = new Intent(VerifyMobileNumberActivity.this, VerifyOtpActivity.class);
                            intent.putExtra("mobile_no", mobile_no);
                            intent.putExtra("otp",otpStr);
                            startActivity(intent);
                        }
                        else {
                            CommonMethod.showToast(jsonObject.getString("message"), VerifyMobileNumberActivity.this);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        CommonMethod.showToast(AppConstant.SOMETHING_WENT_WRONG, VerifyMobileNumberActivity.this);
                    }


                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}