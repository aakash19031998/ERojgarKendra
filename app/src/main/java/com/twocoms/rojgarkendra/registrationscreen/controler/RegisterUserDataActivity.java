package com.twocoms.rojgarkendra.registrationscreen.controler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.twocoms.rojgarkendra.R;

import com.twocoms.rojgarkendra.dashboardscreen.controler.DashboardActivity;
import com.twocoms.rojgarkendra.databinding.ActivityRegisterUserDataBinding;
import com.twocoms.rojgarkendra.global.model.AppConstant;
import com.twocoms.rojgarkendra.global.model.CommonMethod;
import com.twocoms.rojgarkendra.global.model.ServiceHandler;
import com.twocoms.rojgarkendra.global.model.Validation;
//import com.twocoms.rojgarkendra.squarecamera.CameraActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RegisterUserDataActivity extends AppCompatActivity {

    public ActivityRegisterUserDataBinding registerUserDataBinding;
    final Calendar myCalendar = Calendar.getInstance();
    private ArrayList<String> stateList = new ArrayList<>();
    private ArrayList<String> qualificationList = new ArrayList<>();
    private ArrayList<String> expList = new ArrayList<>();
    String mobile_no, radioBtnText, radioButtonSmallText, eduJobStr;
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private static final int REQUEST_CODE_DOC = 1;
    private AlertDialog dialog;
    private static String imageEncoded;
    private Bitmap bitmap;
    boolean isGallaryCalled = false;
    String img_user_profile_base_64 = "";
    String fileBAse64Str="";
    int radioButtonId;
    RadioButton radioButtonGender;
    private SharedPreferences permissionStatus;
    private boolean sentToSettings = false;
    String[] permissionsRequired = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    String android_id;
    String docFilePath;
    String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    boolean isradioBtnChecked = false;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;
    private Bitmap bitmap1;
    private File destination = null;
    private InputStream inputStreamImg;
    private String imgPath = null;
    boolean isFile = false;
    private static final String TAG = RegisterUserDataActivity.class.getSimpleName();
    public static final int REQUEST_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_register_user_data);
        registerUserDataBinding = ActivityRegisterUserDataBinding.inflate(getLayoutInflater());
        View view = registerUserDataBinding.getRoot();
        setContentView(view);
        initialization();
        checkPermission();
        onClick();

    }


    void initialization() {

        Intent intent = getIntent();
        mobile_no = intent.getStringExtra("mobile_no");
        eduJobStr = intent.getStringExtra("eduJob");

        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);
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
        Log.v("Device Id", android_id);


        setVisibilty();
    }

    void enableOrDisableSaveBtn() {
        try {
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
            } else {
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
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    void setVisibilty() {
        try {


            if (eduJobStr.equals("Y")) {
                registerUserDataBinding.centreNameEditTextLnr.setVisibility(View.VISIBLE);
                registerUserDataBinding.projectNameEditTextLnr.setVisibility(View.VISIBLE);
                registerUserDataBinding.batchNameEditTextLnr.setVisibility(View.VISIBLE);
                registerUserDataBinding.courseNameEditTextLnr.setVisibility(View.VISIBLE);
            } else {
                registerUserDataBinding.centreNameEditTextLnr.setVisibility(View.GONE);
                registerUserDataBinding.projectNameEditTextLnr.setVisibility(View.GONE);
                registerUserDataBinding.batchNameEditTextLnr.setVisibility(View.GONE);
                registerUserDataBinding.courseNameEditTextLnr.setVisibility(View.GONE);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }


    void onClick() {

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

//                if (validation()) {
//                    userRegistration(jsonUserRegistration());
//                }
                Intent intent = new Intent(RegisterUserDataActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });


        registerUserDataBinding.registationHeadre.userImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checkPermission();
                onProfileImageClick();
            }
        });

        registerUserDataBinding.uploadCvLnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFile = true;
                getDocument();
            }
        });


    }


    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        registerUserDataBinding.dobKnownEditText.setText(sdf.format(myCalendar.getTime()));
    }

    public boolean checkGenderRadioGrp() {
        if (registerUserDataBinding.genderGrp.getCheckedRadioButtonId() == -1) {
            isradioBtnChecked = false;
            //CommonMethod.showToast("Please select Gender", RegisterUserDataActivity.this);
            // no radio buttons are checked
            return false;
        } else {
            // one of the radio buttons is checked

            radioButtonGender = (RadioButton) findViewById(registerUserDataBinding.genderGrp.getCheckedRadioButtonId());
            radioBtnText = radioButtonGender.getText().toString();
            if (radioBtnText.equals("Male")) {
                radioButtonSmallText = "M";
            } else {
                radioButtonSmallText = "F";
            }
            return true;
        }

    }

    JSONObject jsonUserRegistration() {
        JSONObject Json = new JSONObject();
        if (eduJobStr.equals("Y")) {
            try {
                Json.put("name", registerUserDataBinding.nameEditText.getText().toString());
                Json.put("contact", mobile_no);
                Json.put("contact_verification", "1");
                Json.put("email", registerUserDataBinding.emailEditText.getText().toString());
                Json.put("state", "11");
                Json.put("city", registerUserDataBinding.cityEditText.getText().toString());
                Json.put("dob", registerUserDataBinding.dobKnownEditText.getText().toString());
                Json.put("gender", radioButtonSmallText);
                Json.put("qualification_type", registerUserDataBinding.qualificationTypeEditText.getText().toString());
                Json.put("years_experience", registerUserDataBinding.experinaceYearsEditText.getText().toString());
                Json.put("months_experience", "0");
                Json.put("salary", registerUserDataBinding.salaryEditText.getText().toString());
                Json.put("invite_code", "");
                Json.put("device_id", android_id);
                Json.put("os_type", "A");
                Json.put("notification_id", android_id);
                Json.put("center_name", registerUserDataBinding.centreNameEditText.getText().toString());
                Json.put("project_name", registerUserDataBinding.projectNameEditText.getText().toString());
                Json.put("batch_number", registerUserDataBinding.batchNameEditText.getText().toString());
                Json.put("course_name", registerUserDataBinding.courseNameEditText.getText().toString());
                Json.put("profile_photo", img_user_profile_base_64);
                Json.put("resume", fileBAse64Str);
                Log.v("json", Json.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            try {
//                Json = new JSONObject();
                Json.put("name", registerUserDataBinding.nameEditText.getText().toString());
                Json.put("contact", "9699101091");
                Json.put("contact_verification", "1");
                Json.put("email", registerUserDataBinding.emailEditText.getText().toString());
                Json.put("state", "11");
                Json.put("city", registerUserDataBinding.cityEditText.getText().toString());
                Json.put("dob", registerUserDataBinding.dobKnownEditText.getText().toString());
                Json.put("gender", radioButtonSmallText);
                Json.put("qualification_type", registerUserDataBinding.qualificationTypeEditText.getText().toString());
                Json.put("years_experience", registerUserDataBinding.experinaceYearsEditText.getText().toString());
                Json.put("months_experience", "0");
                Json.put("salary", registerUserDataBinding.salaryEditText.getText().toString());
                Json.put("invite_code", "");
                Json.put("device_id", "");
                Json.put("os_type", "A");
                Json.put("notification_id", "Satyam Vishwakarma");
                Json.put("profile_photo", img_user_profile_base_64);
                Json.put("resume", fileBAse64Str);
                Json.put("center_name", "");
                Json.put("project_name", "");
                Json.put("batch_number", "");
                Json.put("course_name", "");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return Json;
    }

    public boolean validation() {
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
        } else {
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

    void userRegistration(JSONObject Json) {


        //Json.put("Messege","HAVELLS APP VERIFICATION");
        Log.v("JSONURL", Json.toString());
        ServiceHandler serviceHandler = new ServiceHandler(RegisterUserDataActivity.this);
        serviceHandler.StringRequest(Request.Method.POST, Json.toString(), AppConstant.CREATE_USER, true, new ServiceHandler.VolleyCallback() {
            @Override
            public void onSuccess(String result) {

                Log.v("Response", result);
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
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }


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
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }


        @Override
        public void afterTextChanged(Editable s) {
            if (registerUserDataBinding.nameEditText.getText().toString().length() == 0 || registerUserDataBinding.emailEditText.getText().toString().length() == 0 ||
                    registerUserDataBinding.stateEditText.getText().toString().length() == 0 || registerUserDataBinding.cityEditText.getText().toString().length() == 0 ||
                    registerUserDataBinding.languageKnownEditText.toString().trim().length() == 0 || registerUserDataBinding.dobKnownEditText.getText().toString().length() == 0 ||
                    registerUserDataBinding.phoneNumberEditText.getText().toString().length() == 0 || registerUserDataBinding.qualificationTypeEditText.getText().toString().length() == 0 ||
                    registerUserDataBinding.experinaceEditText.getText().toString().length() == 0 || registerUserDataBinding.experinaceYearsEditText.getText().toString().length() == 0 ||
                    registerUserDataBinding.designationEditText.getText().toString().length() == 0 || registerUserDataBinding.rollEditText.getText().toString().length() == 0 ||
                    registerUserDataBinding.salaryEditText.getText().toString().length() == 0) {
                registerUserDataBinding.savebutton.setBackground(getResources().getDrawable(R.drawable.verify_disble));
                //ButtonScore.setEnabled(false);
            } else {
                registerUserDataBinding.savebutton.setBackground(getResources().getDrawable(R.drawable.verify_enable));
                //ButtonScore.setEnabled(true);
            }
        }
    };

    void getFCMToken() {
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

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(RegisterUserDataActivity.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(RegisterUserDataActivity.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(RegisterUserDataActivity.this, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterUserDataActivity.this, permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(RegisterUserDataActivity.this, permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(RegisterUserDataActivity.this, permissionsRequired[2])) {
                //Show Information about why you need the permission

                ActivityCompat.requestPermissions(RegisterUserDataActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);

            } else if (permissionStatus.getBoolean(permissionsRequired[0], false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission

                sentToSettings = true;
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                Toast.makeText(getBaseContext(), "Go to Permissions to Grant  Camera and Storage", Toast.LENGTH_LONG).show();

            } else {
                //just request the permission
                ActivityCompat.requestPermissions(RegisterUserDataActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
            }


            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(permissionsRequired[0], true);
            editor.commit();
        } else {
            //You already have the permission, just go ahead.
            //selectImage(RegistrationNew.this);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {
            //check if all permissions are granted
            boolean allgranted = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if (allgranted) {

            } else if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterUserDataActivity.this, permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(RegisterUserDataActivity.this, permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(RegisterUserDataActivity.this, permissionsRequired[2])) {


                ActivityCompat.requestPermissions(RegisterUserDataActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);

            } else {
                Toast.makeText(getBaseContext(), "Unable to get Permission", Toast.LENGTH_LONG).show();
            }
        }


    }









    private void showPermissionRationaleDialog(final String message, final String permission) {
        new androidx.appcompat.app.AlertDialog.Builder(RegisterUserDataActivity.this)
                .setMessage(message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestForPermission(permission);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();
    }

    private void requestForPermission(final String permission) {
        ActivityCompat.requestPermissions(RegisterUserDataActivity.this, new String[]{permission}, REQUEST_CAMERA_PERMISSION);
    }


    public static String encodeTobase64(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] b = baos.toByteArray();
        imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log Encoded:", imageEncoded);
        return imageEncoded;

    }


    private void getDocument() {
       /* Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/msword,application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // Only the system receives the ACTION_OPEN_DOCUMENT, so no need to test.
        startActivityForResult(intent, REQUEST_CODE_DOC);*/
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), 1);
    }


    private String getFileNameByUri(Context context, Uri uri) {
        String filepath = "";//default fileName
        //Uri filePathUri = uri;
        File file;
        if (uri.getScheme().toString().compareTo("content") == 0) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{android.provider.MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.ORIENTATION}, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            cursor.moveToFirst();

            String mImagePath = cursor.getString(column_index);
            cursor.close();
            filepath = mImagePath;

        } else if (uri.getScheme().compareTo("file") == 0) {
            try {
                file = new File(new URI(uri.toString()));
                if (file.exists())
                    filepath = file.getAbsolutePath();

            } catch (URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            filepath = uri.getPath();
        }
        return filepath;
    }

    public static String fileBase64Convert(String string) {

        byte[] data;
        String base64 = "";

        try {

            data = string.getBytes("UTF-8");

            base64 = Base64.encodeToString(data, Base64.DEFAULT);

            Log.i("Base 64 ", base64);

        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();

        }

        return base64;
    }

    private String decodeBase64(String coded) {
        byte[] valueDecoded = new byte[0];
        try {
            valueDecoded = Base64.decode(coded.getBytes("UTF-8"), Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
        }
        return new String(valueDecoded);
    }


    void onProfileImageClick() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(RegisterUserDataActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(RegisterUserDataActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    img_user_profile_base_64 = encodeTobase64(bitmap);
                    registerUserDataBinding.registationHeadre.userImg.setImageBitmap(bitmap);
                    // loading profile image from local cache
//                    loadProfile(uri.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Uri fileuri = data.getData();
                docFilePath = getFileNameByUri(this, fileuri);
                Log.v("document", docFilePath);
                String filename=docFilePath.substring(docFilePath.lastIndexOf("/")+1);
                registerUserDataBinding.uploadCvTxt.setText(filename);
                fileBAse64Str = fileBase64Convert(docFilePath);
                Log.v("base64FIle", fileBAse64Str);
                String decodeStr = decodeBase64(fileBAse64Str);
                Log.v("decode", decodeStr);


            }
        }
    }

    private void loadProfile(String url) {
//        Log.d(TAG, "Image cache path: " + url);

//        GlideApp.with(this).load(url)
//                .into(imgProfile);
//        imgProfile.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterUserDataActivity.this);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));

        builder.setPositiveButton(getString(R.string.go_to_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.cancel();
                openSettings();
            }
        });

        builder.setPositiveButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

}