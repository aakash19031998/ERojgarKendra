package com.twocoms.rojgarkendra.registrationscreen.controler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.ims.RegistrationManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.dashboardscreen.controler.DashboardActivity;
import com.twocoms.rojgarkendra.databinding.ActivityRegisterUserDataBinding;
import com.twocoms.rojgarkendra.global.model.AppConstant;
import com.twocoms.rojgarkendra.global.model.CommonMethod;
import com.twocoms.rojgarkendra.global.model.GlobalPreferenceManager;
import com.twocoms.rojgarkendra.global.model.ServiceHandler;
import com.twocoms.rojgarkendra.global.model.Validation;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class RegisterUserDataActivity extends AppCompatActivity {

    public ActivityRegisterUserDataBinding registerUserDataBinding;
    final Calendar myCalendar = Calendar.getInstance();

    private ArrayList<String> stateList = new ArrayList<>();
    private ArrayList<String> qualificationList = new ArrayList<>();
    private ArrayList<String> expList = new ArrayList<>();
    String mobile_no, radioBtnText, radioButtonSmallText, eduJobStr;
    int radioButtonId;
    RadioButton radioButtonGender;
    String android_id;
    String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    boolean isradioBtnChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_register_user_data);
        registerUserDataBinding = ActivityRegisterUserDataBinding.inflate(getLayoutInflater());
        View view = registerUserDataBinding.getRoot();
        setContentView(view);
        initialization();
        onClick();

    }


    void initialization(){

        Intent intent= getIntent();
        mobile_no = intent.getStringExtra("mobile_no");
        eduJobStr= intent.getStringExtra("eduJob");

        stateList.add("Andhra Pradesh");
        stateList.add("Arunachal Pradesh");
        stateList.add("Assam");
        stateList.add("Bihar");
        stateList.add("Chandigarh");
        stateList.add("Chhattisgarh");
        stateList.add("Delhi");
        stateList.add("Goa");
        stateList.add("Gujarat");
        stateList.add("Haryana");
        stateList.add("Himachal Pradesh");
        stateList.add("Jammu and Kashmir");
        stateList.add("Jharkhand");
        stateList.add("Karnataka");
        stateList.add("Kerala");
        stateList.add("Ladakh");
        stateList.add("Madhya Pradesh");
        stateList.add("Maharashtra");
        stateList.add("Manipur");
        stateList.add("Meghalaya");
        stateList.add("Mizoram");
        stateList.add("Nagaland");
        stateList.add("Odisha");
        stateList.add("Punjab");
        stateList.add("Rajasthan");
        stateList.add("Sikkim");
        stateList.add("Tamil Nadu");
        stateList.add("Telangana");
        stateList.add("Tripura");
        stateList.add("Uttar Pradesh");
        stateList.add("West Bengal");

        qualificationList.add("10");
        qualificationList.add("10 + 2 ");
        qualificationList.add("Diploma");
        qualificationList.add("Graduation");
        qualificationList.add("Post Graduation");

        expList.add("Yes");
        expList.add("No");


        registerUserDataBinding.dobKnownEditTextLnr.setFocusable(false);
        registerUserDataBinding.dobKnownEditText.setFocusable(false);
        registerUserDataBinding.stateEditTextLnr.setFocusable(false);
        registerUserDataBinding.stateEditText.setFocusable(false);
        registerUserDataBinding.qualificationTypeEditTextLnr.setFocusable(false);
        registerUserDataBinding.qualificationTypeEditText.setFocusable(false);
        registerUserDataBinding.experinaceEditTextLnr.setFocusable(false);
        registerUserDataBinding.experinaceEditText.setFocusable(false);

        registerUserDataBinding.phoneNumberEditText.setText(mobile_no);
        registerUserDataBinding.phoneNumberEditText.setEnabled(false);
        //registerUserDataBinding.phoneNumberEditText.setFocusable(false);



        //Enable or Disable Save Botton
        enableOrDisableSaveBtn();

        //Device Id (Token Id)
        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.v("Device Id",android_id);


        setVisibilty();
    }

    void enableOrDisableSaveBtn(){
        if (eduJobStr.equals("Y")) {
            registerUserDataBinding.nameEditText.addTextChangedListener(watcher);
            registerUserDataBinding.emailEditText.addTextChangedListener(watcher);
            registerUserDataBinding.stateEditText.addTextChangedListener(watcher);
            registerUserDataBinding.cityEditText.addTextChangedListener(watcher);
            registerUserDataBinding.languageKnownEditText.addTextChangedListener(watcher);
            registerUserDataBinding.designationEditText.addTextChangedListener(watcher);
            registerUserDataBinding.dobKnownEditText.addTextChangedListener(watcher);
            registerUserDataBinding.qualificationTypeEditText.addTextChangedListener(watcher);
            registerUserDataBinding.experinaceEditText.addTextChangedListener(watcher);
            registerUserDataBinding.experinaceYearsEditText.addTextChangedListener(watcher);
            registerUserDataBinding.designationEditText.addTextChangedListener(watcher);
            registerUserDataBinding.rollEditText.addTextChangedListener(watcher);
            registerUserDataBinding.salaryEditText.addTextChangedListener(watcher);
            registerUserDataBinding.centreNameEditText.addTextChangedListener(watcher);
            registerUserDataBinding.projectNameEditText.addTextChangedListener(watcher);
            registerUserDataBinding.batchNameEditText.addTextChangedListener(watcher);
            registerUserDataBinding.courseNameEditText.addTextChangedListener(watcher);
        }else {
            registerUserDataBinding.nameEditText.addTextChangedListener(watcher1);
            registerUserDataBinding.emailEditText.addTextChangedListener(watcher1);
            registerUserDataBinding.stateEditText.addTextChangedListener(watcher1);
            registerUserDataBinding.cityEditText.addTextChangedListener(watcher1);
            registerUserDataBinding.languageKnownEditText.addTextChangedListener(watcher1);
            registerUserDataBinding.designationEditText.addTextChangedListener(watcher1);
            registerUserDataBinding.dobKnownEditText.addTextChangedListener(watcher1);
            registerUserDataBinding.qualificationTypeEditText.addTextChangedListener(watcher1);
            registerUserDataBinding.experinaceEditText.addTextChangedListener(watcher1);
            registerUserDataBinding.experinaceYearsEditText.addTextChangedListener(watcher1);
            registerUserDataBinding.designationEditText.addTextChangedListener(watcher1);
            registerUserDataBinding.rollEditText.addTextChangedListener(watcher1);
            registerUserDataBinding.salaryEditText.addTextChangedListener(watcher1);
        }
    }

    void setVisibilty(){
        if(eduJobStr.equals("Y")){
            registerUserDataBinding.centreNameEditTextLnr.setVisibility(View.VISIBLE);
            registerUserDataBinding.projectNameEditTextLnr.setVisibility(View.VISIBLE);
            registerUserDataBinding.batchNameEditTextLnr.setVisibility(View.VISIBLE);
            registerUserDataBinding.courseNameEditTextLnr.setVisibility(View.VISIBLE);
        }else{
            registerUserDataBinding.centreNameEditTextLnr.setVisibility(View.GONE);
            registerUserDataBinding.projectNameEditTextLnr.setVisibility(View.GONE);
            registerUserDataBinding.batchNameEditTextLnr.setVisibility(View.GONE);
            registerUserDataBinding.courseNameEditTextLnr.setVisibility(View.GONE);
        }


    }


    void onClick(){








        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {



            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        registerUserDataBinding.dobKnownEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                registerUserDataBinding.dobKnownEditText.setText("");
                new DatePickerDialog(RegisterUserDataActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        registerUserDataBinding.stateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayAdapter adapter1 = new ArrayAdapter<String>(RegisterUserDataActivity.this, R.layout.drop_down_item, stateList);
                registerUserDataBinding.stateEditText.setAdapter(adapter1);
                {
                    registerUserDataBinding.stateEditText.showDropDown();
                }
            }
        });

        registerUserDataBinding.qualificationTypeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayAdapter adapter = new ArrayAdapter<String>(RegisterUserDataActivity.this, R.layout.drop_down_item, qualificationList);
                registerUserDataBinding.qualificationTypeEditText.setAdapter(adapter);
                {
                    registerUserDataBinding.qualificationTypeEditText.showDropDown();
                }
            }
        });

        registerUserDataBinding.experinaceEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayAdapter adapter = new ArrayAdapter<String>(RegisterUserDataActivity.this, R.layout.drop_down_item, expList);
                registerUserDataBinding.experinaceEditText.setAdapter(adapter);
                {
                    registerUserDataBinding.experinaceEditText.showDropDown();
                }
            }
        });

        registerUserDataBinding.savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validation()){

                    userRegistration(jsonUserRegistration());
                }
//                Intent intent = new Intent(RegisterUserDataActivity.this, DashboardActivity.class);
//                startActivity(intent);
            }
        });


    }


    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        registerUserDataBinding.dobKnownEditText.setText(sdf.format(myCalendar.getTime()));
    }

    public boolean checkGenderRadioGrp(){
        if (registerUserDataBinding.genderGrp.getCheckedRadioButtonId() == -1)
        {
            isradioBtnChecked =false;
            //CommonMethod.showToast("Please select Gender", RegisterUserDataActivity.this);
            // no radio buttons are checked
            return false;
        }
        else
        {
            // one of the radio buttons is checked

            radioButtonGender = (RadioButton)findViewById(registerUserDataBinding.genderGrp.getCheckedRadioButtonId());
            radioBtnText = radioButtonGender.getText().toString();
            if (radioBtnText.equals("Male")){
                radioButtonSmallText = "M";
            }else {
                radioButtonSmallText = "F";
            }
            return true;
        }

    }

    JSONObject jsonUserRegistration(){
        JSONObject Json = new JSONObject();
        if (eduJobStr.equals("Y")){
            try {
//                Json = new JSONObject();
                Json.put("name", registerUserDataBinding.nameEditText.getText().toString());
                Json.put("contact",mobile_no);
                Json.put("contact_verification","0");
                Json.put("email",registerUserDataBinding.emailEditText.getText().toString());
                Json.put("state","11");
                Json.put("city",registerUserDataBinding.cityEditText.getText().toString());
                Json.put("dob",registerUserDataBinding.dobKnownEditText.getText().toString());
                Json.put("gender",radioButtonSmallText);
                Json.put("qualification_type",registerUserDataBinding.qualificationTypeEditText.getText().toString());
                Json.put("years_experience",registerUserDataBinding.experinaceYearsEditText.getText().toString());
                Json.put("months_experience","0");
                Json.put("salary",registerUserDataBinding.salaryEditText.getText().toString());
                Json.put("invite_code","");
                Json.put("device_id",android_id);
                Json.put("os_type","A");
                Json.put("notification_id",android_id);
                Json.put("center_name",registerUserDataBinding.centreNameEditText.getText().toString());
                Json.put("project_name",registerUserDataBinding.projectNameEditText.getText().toString());
                Json.put("batch_number",registerUserDataBinding.batchNameEditText.getText().toString());
                Json.put("course_name",registerUserDataBinding.courseNameEditText.getText().toString());
                Log.v("json",Json.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else {
            try {
//                Json = new JSONObject();
                Json.put("name", registerUserDataBinding.nameEditText.getText().toString());
                Json.put("contact",mobile_no);
                Json.put("contact_verification","0");
                Json.put("email",registerUserDataBinding.emailEditText.getText().toString());
                Json.put("state","11");
                Json.put("city",registerUserDataBinding.cityEditText.getText().toString());
                Json.put("dob",registerUserDataBinding.dobKnownEditText.getText().toString());
                Json.put("gender",radioButtonSmallText);
                Json.put("qualification_type",registerUserDataBinding.qualificationTypeEditText.getText().toString());
                Json.put("years_experience",registerUserDataBinding.experinaceYearsEditText.getText().toString());
                Json.put("months_experience","7");
                Json.put("salary",registerUserDataBinding.salaryEditText.getText().toString());
                Json.put("invite_code","");
                Json.put("device_id",android_id);
                Json.put("os_type","A");
                Json.put("notification_id", GlobalPreferenceManager.getStringForKey(RegisterUserDataActivity.this,"firebasekey","abcdefg"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return Json;
    }

    public boolean validation(){
        if (eduJobStr.equals("Y")) {
            if (Validation.checkIfEmptyOrNot(registerUserDataBinding.nameEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Name", RegisterUserDataActivity.this);
                return false;
            } else if (registerUserDataBinding.emailEditText.getText().toString().isEmpty()) {
                CommonMethod.showToast("Please Enter Email Id", RegisterUserDataActivity.this);
                return false;
            } else if (!registerUserDataBinding.emailEditText.getText().toString().matches(emailPattern)) {
                CommonMethod.showToast("Please Enter Valid Email Id", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.stateEditText.getText().toString())) {
                CommonMethod.showToast("Please Select State", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.cityEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter City/District", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.languageKnownEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Language Known", RegisterUserDataActivity.this);
                return false;
            } else if (!checkGenderRadioGrp()) {
                CommonMethod.showToast("Please select Gender", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.dobKnownEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter DOB", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.phoneNumberEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Phone", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.qualificationTypeEditText.getText().toString())) {
                CommonMethod.showToast("Please Select Qualification", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.experinaceEditText.getText().toString())) {
                CommonMethod.showToast("Please Select Experience", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.experinaceYearsEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Years of Experience  ", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.designationEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Designation", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.rollEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Roll", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.salaryEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Salary", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.centreNameEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Center Name", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.projectNameEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Project Name", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.batchNameEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Batch Name", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.courseNameEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Course Name", RegisterUserDataActivity.this);
                return false;
            }
        }else {
            if (Validation.checkIfEmptyOrNot(registerUserDataBinding.nameEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Name", RegisterUserDataActivity.this);
                return false;
            } else if (registerUserDataBinding.emailEditText.getText().toString().isEmpty()) {
                CommonMethod.showToast("Please Enter Email Id", RegisterUserDataActivity.this);
                return false;
            } else if (!registerUserDataBinding.emailEditText.getText().toString().matches(emailPattern)) {
                CommonMethod.showToast("Please Enter Valid Email Id", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.stateEditText.getText().toString())) {
                CommonMethod.showToast("Please Select State", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.cityEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter City/District", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.languageKnownEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Language Known", RegisterUserDataActivity.this);
                return false;
            } else if (!checkGenderRadioGrp()) {
                CommonMethod.showToast("Please select Gender", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.dobKnownEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter DOB", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.phoneNumberEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Phone", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.qualificationTypeEditText.getText().toString())) {
                CommonMethod.showToast("Please Select Qualification", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.experinaceEditText.getText().toString())) {
                CommonMethod.showToast("Please Select Experience", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.experinaceYearsEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Years of Experience  ", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.designationEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Designation", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.rollEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Roll", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.salaryEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Salary", RegisterUserDataActivity.this);
                return false;
            }

        }
        return true;
    }

    void userRegistration(JSONObject Json){



            //Json.put("Messege","HAVELLS APP VERIFICATION");
            Log.v("JSONURL", Json.toString());
            ServiceHandler serviceHandler = new ServiceHandler(RegisterUserDataActivity.this);
            serviceHandler.StringRequest(Request.Method.POST, Json.toString(), AppConstant.CREATE_USER, true, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {

                    Log.v("Response",result);
                    /*try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.getBoolean("success")){
                            JSONObject dataStr = jsonObject.getJSONObject("data");
                            otpStr = dataStr.getString("otp");
                            String msgStr = jsonObject.getString("message");
                            Intent intent = new Intent(VerifyOtpActivity.this, RegisterUserDataActivity.class);
                            intent.putExtra("mobile_no",mobile_no);
                            startActivity(intent);
                            CommonMethod.showToast(msgStr,VerifyOtpActivity.this);
                            finish();
                        }else {
                            String msgStr = jsonObject.getString("message");
                            CommonMethod.showToast(msgStr,VerifyOtpActivity.this);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/

//                    Intent intent = new Intent(VerifyOtpActivity.this, VerifyOtpActivity.class);
//                    intent.putExtra("otp",otpStr);
//                    startActivity(intent);
                }
            });

    }


    private final TextWatcher watcher = new TextWatcher() {


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        { }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {}



        @Override
        public void afterTextChanged(Editable s) {
            if (registerUserDataBinding.nameEditText.getText().toString().length() == 0 || registerUserDataBinding.emailEditText.getText().toString().length() == 0 ||
                    registerUserDataBinding.stateEditText.getText().toString().length() == 0 || registerUserDataBinding.cityEditText.getText().toString().length() == 0 ||
                    registerUserDataBinding.languageKnownEditText.toString().trim().length() == 0 || registerUserDataBinding.dobKnownEditText.getText().toString().length() == 0 ||
                    registerUserDataBinding.phoneNumberEditText.getText().toString().length() == 0 || registerUserDataBinding.qualificationTypeEditText.getText().toString().length() == 0 ||
                    registerUserDataBinding.experinaceEditText.getText().toString().length() == 0 || registerUserDataBinding.experinaceYearsEditText.getText().toString().length() == 0 ||
                    registerUserDataBinding.designationEditText.getText().toString().length() == 0 || registerUserDataBinding.rollEditText.getText().toString().length() == 0 ||
                    registerUserDataBinding.salaryEditText.getText().toString().length() == 0 || registerUserDataBinding.centreNameEditText.getText().toString().length() == 0 ||
                    registerUserDataBinding.projectNameEditText.getText().toString().length() == 0 || registerUserDataBinding.batchNameEditText.getText().toString().length() == 0 ||
                    registerUserDataBinding.courseNameEditText.getText().toString().length() == 0) {
                registerUserDataBinding.savebutton.setBackground(getResources().getDrawable(R.drawable.verify_disble));
                //ButtonScore.setEnabled(false);
            } else {
                registerUserDataBinding.savebutton.setBackground(getResources().getDrawable(R.drawable.verify_enable));
                //ButtonScore.setEnabled(true);
            }
        }
    };

    private final TextWatcher watcher1 = new TextWatcher() {


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        { }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {}



        @Override
        public void afterTextChanged(Editable s) {
            if (registerUserDataBinding.nameEditText.getText().toString().length() == 0 || registerUserDataBinding.emailEditText.getText().toString().length() == 0 ||
                    registerUserDataBinding.stateEditText.getText().toString().length() == 0 || registerUserDataBinding.cityEditText.getText().toString().length() == 0 ||
                    registerUserDataBinding.languageKnownEditText.toString().trim().length() == 0 || registerUserDataBinding.dobKnownEditText.getText().toString().length() == 0 ||
                    registerUserDataBinding.phoneNumberEditText.getText().toString().length() == 0 || registerUserDataBinding.qualificationTypeEditText.getText().toString().length() == 0 ||
                    registerUserDataBinding.experinaceEditText.getText().toString().length() == 0 || registerUserDataBinding.experinaceYearsEditText.getText().toString().length() == 0 ||
                    registerUserDataBinding.designationEditText.getText().toString().length() == 0 || registerUserDataBinding.rollEditText.getText().toString().length() == 0 ||
                    registerUserDataBinding.salaryEditText.getText().toString().length() == 0 ) {
                registerUserDataBinding.savebutton.setBackground(getResources().getDrawable(R.drawable.verify_disble));
                //ButtonScore.setEnabled(false);
            } else {
                registerUserDataBinding.savebutton.setBackground(getResources().getDrawable(R.drawable.verify_enable));
                //ButtonScore.setEnabled(true);
            }
        }
    };

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

                        // Log and toast
                        //String msg = getString(R.string.token_id, token);
                        Log.d("Token", token);
                        Toast.makeText(RegisterUserDataActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

    }
}