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
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
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
import com.twocoms.rojgarkendra.global.model.GlobalPreferenceManager;
import com.twocoms.rojgarkendra.global.model.ServiceHandler;
import com.twocoms.rojgarkendra.global.model.Validation;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RegisterUserDataActivity extends AppCompatActivity {

    public ActivityRegisterUserDataBinding registerUserDataBinding;
    final Calendar myCalendar = Calendar.getInstance();
    private ArrayList<String> stateList = new ArrayList<>();
    private ArrayList<String> qualificationList = new ArrayList<>();
    private ArrayList<String> expList = new ArrayList<>();
    String mobile_no, radioBtnText, radioButtonSmallText, eduJobStr = "";
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private static final int REQUEST_CODE_DOC = 1;
    private AlertDialog dialog;
    private static String imageEncoded;
    private Bitmap bitmap;
    boolean isGallaryCalled = false;
    String img_user_profile_base_64 = "";
    String fileBAse64Str = "";
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
    String projectId = "", courseId = "", batchId = "", centreId = "";
    String[] states;
    String[] statesCode;
    ArrayList<String> listStates;
    String state_code = "";
    private Calendar mcalendar;

    //    String dobToServer;
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
        // getStates();
    }


    void initialization() {
//        Intent intent = getIntent();
        mobile_no = GlobalPreferenceManager.getStringForKey(this, AppConstant.KEY_CONTACT, "");
        eduJobStr = GlobalPreferenceManager.getStringForKey(this, AppConstant.KEY_IS_EDURP, "");
//        if (eduJobStr.equals("Y")){
//            registerUserDataBinding.coursenameHeader.setVisibility(View.VISIBLE);
//        }else{
//            registerUserDataBinding.coursenameHeader.setVisibility(View.GONE);
//
//        }
        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);
        listStates = new ArrayList<>();
        mcalendar = Calendar.getInstance();

//        qualificationList.add("5th");
//        qualificationList.add("6th");
//        qualificationList.add("7th");
//        qualificationList.add("8th");
//        qualificationList.add("9th");
        qualificationList.add("10th");
        qualificationList.add("11th");
        qualificationList.add("12th");
        qualificationList.add("Diploma");
        qualificationList.add("Advanced Diploma");
        qualificationList.add("Graduation");
        qualificationList.add("Post Graduation");
        qualificationList.add("Doctoral");
        qualificationList.add("ITI");
        qualificationList.add("ITI Dual");
        qualificationList.add("Others");

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
        setMobileDataCard();
        registerUserDataBinding.phoneNumberEditText.setFocusable(false);
        //Enable or Disable Save Botton
        enableOrDisableSaveBtn();

        //Device Id (Token Id)
        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.v("Device Id", android_id);

        registerUserDataBinding.skipbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonMethod.openDashBoardActivity(RegisterUserDataActivity.this);
            }
        });

        registerUserDataBinding.backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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

//                registerUserDataBinding.rollEditText.addTextChangedListener(watcher);
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

                registerUserDataBinding.dobKnownEditText.addTextChangedListener(watcher1);
                registerUserDataBinding.qualificationTypeEditText.addTextChangedListener(watcher1);
                registerUserDataBinding.experinaceEditText.addTextChangedListener(watcher1);
                registerUserDataBinding.experinaceYearsEditText.addTextChangedListener(watcher1);
                registerUserDataBinding.designationEditText.addTextChangedListener(watcher1);
//                registerUserDataBinding.rollEditText.addTextChangedListener(watcher1);
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
                registerUserDataBinding.coursenameHeader.setVisibility(View.VISIBLE);
                registerUserDataBinding.courseNameBottomLine.setVisibility(View.GONE);
                setDataOnEduErp();
            } else {
                registerUserDataBinding.centreNameEditTextLnr.setVisibility(View.GONE);
                registerUserDataBinding.projectNameEditTextLnr.setVisibility(View.GONE);
                registerUserDataBinding.batchNameEditTextLnr.setVisibility(View.GONE);
                registerUserDataBinding.courseNameEditTextLnr.setVisibility(View.GONE);
                registerUserDataBinding.coursenameHeader.setVisibility(View.GONE);
                registerUserDataBinding.courseNameBottomLine.setVisibility(View.GONE);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }


    void onClick() {

        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                mcalendar.set(Calendar.YEAR, year);
                mcalendar.set(Calendar.MONTH, monthOfYear);
                mcalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                registerUserDataBinding.dobKnownEditText.setText(sdf.format(mcalendar.getTime()));

                //check age of the user
            }

        };


        registerUserDataBinding.dobKnownEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                registerUserDataBinding.dobKnownEditText.setText("");
                /*new DatePickerDialog(RegisterUserDataActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();*/
                DatePickerDialog dialog = new DatePickerDialog(RegisterUserDataActivity.this, datePickerListener, mcalendar
                        .get(Calendar.YEAR), mcalendar.get(Calendar.MONTH), mcalendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMaxDate((long) (new Date().getTime() - 60 * 60 * 1000 * 24 * 30.41666666 * 12 * 18));//7 * 24 * 60 * 60 * 1000  604800000L
                dialog.show();
            }
        });


        registerUserDataBinding.stateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getStates();
            }


        });

        registerUserDataBinding.stateEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int j = listStates.indexOf(registerUserDataBinding.stateEditText.getText().toString());
                state_code = statesCode[j];
                Log.v("statecode", state_code);
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

        registerUserDataBinding.experinaceEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (registerUserDataBinding.experinaceEditText.getText().toString().equals("Yes")) {
                    registerUserDataBinding.experinaceYearsEditText.setVisibility(View.VISIBLE);
                    registerUserDataBinding.experinaceYearsEditText.setText("");
                    registerUserDataBinding.experinaceYearsEditTextLnr.setVisibility(View.VISIBLE);
                } else {
                    registerUserDataBinding.experinaceYearsEditText.setVisibility(View.GONE);
                    registerUserDataBinding.experinaceYearsEditText.setText("");
                    registerUserDataBinding.experinaceYearsEditTextLnr.setVisibility(View.GONE);
                }
            }
        });

        registerUserDataBinding.savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validation()) {
                    userRegistration(jsonUserRegistration());
//                    jsonUserRegistration();
                }
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

        // registerUserDataBinding.experinaceYearsEditText.setFilters(new InputFilter[]{ new InputFilterMin(10)});

        /*registerUserDataBinding.experinaceYearsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1 && s.toString().startsWith("0")){

                }
            }
        });*/


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

            radioBtnText = radioButtonGender.getTag().toString();
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
                Json.put(AppConstant.KEY_NAME, registerUserDataBinding.nameEditText.getText().toString());
                Json.put(AppConstant.KEY_CONTACT, mobile_no);
                Json.put(AppConstant.KEY_CONTACT_VERIFIED, "1");
                Json.put(AppConstant.KEY_EMAIL_ID, registerUserDataBinding.emailEditText.getText().toString());
                Json.put(AppConstant.STATE_ID, state_code);
                Json.put(AppConstant.KEY_CITY, registerUserDataBinding.cityEditText.getText().toString());
                Json.put(AppConstant.KEY_DOB, registerUserDataBinding.dobKnownEditText.getText().toString());
                Json.put(AppConstant.KEY_GENDER, radioButtonSmallText);
                Json.put(AppConstant.KEY_QUALIFICATION_TYPE, registerUserDataBinding.qualificationTypeEditText.getText().toString());
                Json.put(AppConstant.KEY_EXPERIANCE_YEARS, registerUserDataBinding.experinaceYearsEditText.getText().toString());
                Json.put(AppConstant.KEY_EXPERIANCE_MONTH, "0");
                Json.put(AppConstant.KEY_SALARY, registerUserDataBinding.salaryEditText.getText().toString());
                Json.put(AppConstant.KEY_INVITE_CODE, GlobalPreferenceManager.getStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_INVITE_CODE, ""));
                Json.put(AppConstant.KEY_DEVICE_ID, android_id);
                Json.put(AppConstant.KEY_OS_TYPE, "A");
                Json.put(AppConstant.KEY_IS_EDURP, "Y");
                Json.put(AppConstant.KEY_ROLE, registerUserDataBinding.designationEditText.getText().toString());
                Json.put(AppConstant.KEY_NOTIFICATION_ID, GlobalPreferenceManager.getStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_DEVICE_TOKEN, ""));
                Json.put(AppConstant.KEY_CENTRE_ID, centreId);
                Json.put(AppConstant.KEY_PROJECT_ID, projectId);
                Json.put(AppConstant.KEY_BATCH_ID, batchId);
                Json.put(AppConstant.KEY_COURSE_ID, courseId);
                Json.put(AppConstant.KEY_PROFILE_PHOTO, img_user_profile_base_64);
                Json.put(AppConstant.KEY_RESUME, fileBAse64Str);
                Json.put(AppConstant.KEY_LANGUAGE_KNOWN, registerUserDataBinding.languageKnownEditText.getText().toString());

                Log.v("json", Json.toString());
//                CommonMethod.showToast("Json",RegisterUserDataActivity.this);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            try {
                Json.put(AppConstant.KEY_NAME, registerUserDataBinding.nameEditText.getText().toString());
                Json.put(AppConstant.KEY_CONTACT, mobile_no);
                Json.put(AppConstant.KEY_CONTACT_VERIFIED, "1");
                Json.put(AppConstant.KEY_EMAIL_ID, registerUserDataBinding.emailEditText.getText().toString());
                Json.put(AppConstant.STATE_ID, state_code);
                Json.put(AppConstant.KEY_CITY, registerUserDataBinding.cityEditText.getText().toString());
                Json.put(AppConstant.KEY_DOB, registerUserDataBinding.dobKnownEditText.getText().toString());
                Json.put(AppConstant.KEY_GENDER, radioButtonSmallText);
                Json.put(AppConstant.KEY_QUALIFICATION_TYPE, registerUserDataBinding.qualificationTypeEditText.getText().toString());
                Json.put(AppConstant.KEY_EXPERIANCE_YEARS, registerUserDataBinding.experinaceYearsEditText.getText().toString());
                Json.put(AppConstant.KEY_EXPERIANCE_MONTH, "0");
                Json.put(AppConstant.KEY_SALARY, registerUserDataBinding.salaryEditText.getText().toString());
                Json.put(AppConstant.KEY_INVITE_CODE, GlobalPreferenceManager.getStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_INVITE_CODE, ""));
                Json.put(AppConstant.KEY_DEVICE_ID, android_id);
                Json.put(AppConstant.KEY_OS_TYPE, "A");
                Json.put(AppConstant.KEY_IS_EDURP, "N");
                Json.put(AppConstant.KEY_NOTIFICATION_ID, GlobalPreferenceManager.getStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_DEVICE_TOKEN, ""));
                Json.put("language_known", registerUserDataBinding.languageKnownEditText.getText().toString());
                Json.put("role", registerUserDataBinding.designationEditText.getText().toString());
                Json.put(AppConstant.KEY_PROFILE_PHOTO, img_user_profile_base_64);
                Json.put(AppConstant.KEY_RESUME, fileBAse64Str);
                Log.v("json", Json.toString());

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
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.phoneNumberEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Phone No", RegisterUserDataActivity.this);
                return false;
            } else if (registerUserDataBinding.emailEditText.getText().toString().isEmpty()) {
                CommonMethod.showToast("Please Enter Email Id", RegisterUserDataActivity.this);
                return false;
            } else if (!registerUserDataBinding.emailEditText.getText().toString().matches(emailPattern)) {
                CommonMethod.showToast("Please Enter Valid Email Id", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.dobKnownEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter DOB", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.languageKnownEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Language Known", RegisterUserDataActivity.this);
                return false;
            } else if (!checkGenderRadioGrp()) {
                CommonMethod.showToast("Please select Gender", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.stateEditText.getText().toString())) {
                CommonMethod.showToast("Please Select State", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.cityEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter City/District", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.qualificationTypeEditText.getText().toString())) {
                CommonMethod.showToast("Please Select Qualification", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.experinaceEditText.getText().toString())) {
                CommonMethod.showToast("Please Select Experience", RegisterUserDataActivity.this);
                return false;
            } else if (registerUserDataBinding.experinaceYearsEditText.getVisibility() == View.VISIBLE) {
                if (Validation.checkIfEmptyOrNot(registerUserDataBinding.experinaceYearsEditText.getText().toString())) {
                    CommonMethod.showToast("Please Enter Experience Year", RegisterUserDataActivity.this);
                    return false;
                }
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.designationEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Designation", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.salaryEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Salary", RegisterUserDataActivity.this);
                return false;
            }
        } else {
            if (Validation.checkIfEmptyOrNot(registerUserDataBinding.nameEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Name", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.phoneNumberEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Phone No", RegisterUserDataActivity.this);
                return false;
            } else if (registerUserDataBinding.emailEditText.getText().toString().isEmpty()) {
                CommonMethod.showToast("Please Enter Email Id", RegisterUserDataActivity.this);
                return false;
            } else if (!registerUserDataBinding.emailEditText.getText().toString().matches(emailPattern)) {
                CommonMethod.showToast("Please Enter Valid Email Id", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.dobKnownEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter DOB", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.languageKnownEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Language Known", RegisterUserDataActivity.this);
                return false;
            } else if (!checkGenderRadioGrp()) {
                CommonMethod.showToast("Please select Gender", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.stateEditText.getText().toString())) {
                CommonMethod.showToast("Please Select State", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.cityEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter City/District", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.qualificationTypeEditText.getText().toString())) {
                CommonMethod.showToast("Please Select Qualification", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.experinaceEditText.getText().toString())) {
                CommonMethod.showToast("Please Select Experience", RegisterUserDataActivity.this);
                return false;
            } else if (registerUserDataBinding.experinaceYearsEditText.getVisibility() == View.VISIBLE) {
                if (Validation.checkIfEmptyOrNot(registerUserDataBinding.experinaceYearsEditText.getText().toString())) {
                    CommonMethod.showToast("Please Enter Experience Year", RegisterUserDataActivity.this);
                    return false;
                }
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.designationEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Designation", RegisterUserDataActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(registerUserDataBinding.salaryEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Salary", RegisterUserDataActivity.this);
                return false;
            }
        }
        return true;
    }

    void userRegistration(JSONObject Json) {
        Log.v("JSON REQUEST", Json.toString());
        ServiceHandler serviceHandler = new ServiceHandler(RegisterUserDataActivity.this);
        serviceHandler.StringRequest(Request.Method.POST, Json.toString(), AppConstant.CREATE_USER, true, new ServiceHandler.VolleyCallback() {
            @Override
            public void onSuccess(String result) {

                Log.v("Response", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getBoolean("success")) {
                        JSONObject dataStr = jsonObject.getJSONObject("data");
                        dataStr.put(AppConstant.KEY_IS_REGISTER, "Y");
                        dataStr.put(AppConstant.KEY_IS_EDURP, eduJobStr);
//                        String msgStr = jsonObject.getString("message");
//                        if (dataStr.has("is_register") && dataStr.has("eduErp")) {
                        /*Data Save*/
                        GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_USER_ID, dataStr.getString(AppConstant.KEY_USER_ID));
                        GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_NAME, dataStr.getString(AppConstant.KEY_NAME));
                        GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_CONTACT, dataStr.getString(AppConstant.KEY_CONTACT));
                        GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_CONTACT_VERIFIED, dataStr.getString(AppConstant.KEY_CONTACT_VERIFIED));
                        GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_EMAIL_ID, dataStr.getString(AppConstant.KEY_EMAIL_ID));
                        GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.STATE_ID, dataStr.getString(AppConstant.STATE_ID));
                        GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_CITY, dataStr.getString(AppConstant.KEY_CITY));
                        GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_DOB, dataStr.getString(AppConstant.KEY_DOB));
                        GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_GENDER, dataStr.getString(AppConstant.KEY_GENDER));
                        GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_QUALIFICATION_TYPE, dataStr.getString(AppConstant.KEY_QUALIFICATION_TYPE));
                        GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_EXPERIANCE_YEARS, dataStr.getString(AppConstant.KEY_EXPERIANCE_YEARS));
                        GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_EXPERIANCE_MONTH, dataStr.getString(AppConstant.KEY_EXPERIANCE_MONTH));
                        GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_SALARY, dataStr.getString(AppConstant.KEY_SALARY));
                        GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_REFERAL_CODE, dataStr.getString(AppConstant.KEY_REFERAL_CODE));
                        //  GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_DEVICE_ID, dataStr.getString(AppConstant.KEY_DEVICE_ID));
                        // GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_OS_TYPE, dataStr.getString(AppConstant.KEY_OS_TYPE));
                        //   GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_NOTIFICATION_ID, dataStr.getString(AppConstant.KEY_NOTIFICATION_ID));
                        GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_IS_REGISTER, "Y");
//                        GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.thi);
//                            GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_IS_REGISTER, dataStr.getString(AppConstant.KEY_IS_REGISTER));
                        if (dataStr.has(AppConstant.KEY_PROFILE_URL)) {
                            GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_PROFILE_URL, dataStr.getString(AppConstant.KEY_PROFILE_URL));
                        } else {
                            GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_PROFILE_URL, "");

                        }
                        if (dataStr.has(AppConstant.KEY_RESUME_URL)) {
                            GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_RESUME_URL, dataStr.getString(AppConstant.KEY_RESUME_URL));
                        } else {
                            GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_RESUME_URL, "");

                        }

                        GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_WALLET_AMOUNT, dataStr.getString(AppConstant.KEY_WALLET_AMOUNT));
                        if (dataStr.getString("eduErp").equals("Y")) {
//                            GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_COURSE_NAME, dataStr.getString(AppConstant.KEY_COURSE_NAME));
//                            GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_PROJECT_NAME, dataStr.getString(AppConstant.KEY_PROJECT_NAME));
//                            GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_BATCH_NAME, dataStr.getString(AppConstant.KEY_BATCH_NAME));
//                            GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_CENTRE_NAME, dataStr.getString(AppConstant.KEY_CENTRE_NAME));
                            GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_COURSE_ID, dataStr.getString(AppConstant.KEY_COURSE_ID));
                            GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_PROJECT_ID, dataStr.getString(AppConstant.KEY_PROJECT_ID));
                            GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_BATCH_ID, dataStr.getString(AppConstant.KEY_BATCH_ID));
                            GlobalPreferenceManager.saveStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_CENTRE_ID, dataStr.getString(AppConstant.KEY_CENTRE_ID));
                        }
                        navigateToDashBoard();
//                        }
                    } else {
                        String msgStr = jsonObject.getString("message");
                        CommonMethod.showToast(msgStr, RegisterUserDataActivity.this);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    CommonMethod.showToast(AppConstant.SOMETHING_WENT_WRONG, RegisterUserDataActivity.this);

                }


            }
        });

    }


    void navigateToDashBoard() {
        Intent intent = new Intent(RegisterUserDataActivity.this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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

            if (!registerUserDataBinding.nameEditText.getText().toString().equals("")) {
                registerUserDataBinding.registationHeadre.nameVisitingCard.setText(registerUserDataBinding.nameEditText.getText().toString());
                registerUserDataBinding.registationHeadre.nameVisitingCard.setVisibility(View.VISIBLE);
            } else {
                registerUserDataBinding.registationHeadre.nameVisitingCard.setText(registerUserDataBinding.nameEditText.getText().toString());
                registerUserDataBinding.registationHeadre.nameVisitingCard.setVisibility(View.GONE);
            }

            if (!registerUserDataBinding.emailEditText.getText().toString().equals("")) {
                registerUserDataBinding.registationHeadre.emailIdVisitingcard.setText(registerUserDataBinding.emailEditText.getText().toString());
                registerUserDataBinding.registationHeadre.emailLayout.setVisibility(View.VISIBLE);
            } else {
                registerUserDataBinding.registationHeadre.emailIdVisitingcard.setText(registerUserDataBinding.emailEditText.getText().toString());
                registerUserDataBinding.registationHeadre.emailLayout.setVisibility(View.GONE);
            }

            if (!registerUserDataBinding.designationEditText.getText().toString().equals("")) {
                registerUserDataBinding.registationHeadre.designationVisitingcard.setText(registerUserDataBinding.designationEditText.getText().toString());
                registerUserDataBinding.registationHeadre.designationLayout.setVisibility(View.VISIBLE);
            } else {
                registerUserDataBinding.registationHeadre.designationVisitingcard.setText(registerUserDataBinding.designationEditText.getText().toString());
                registerUserDataBinding.registationHeadre.designationLayout.setVisibility(View.GONE);
            }

            if (!registerUserDataBinding.cityEditText.getText().toString().equals("")) {
                registerUserDataBinding.registationHeadre.locationVisitingcard.setText(registerUserDataBinding.cityEditText.getText().toString());
                registerUserDataBinding.registationHeadre.locationLayout.setVisibility(View.VISIBLE);
            } else {
                registerUserDataBinding.registationHeadre.locationVisitingcard.setText(registerUserDataBinding.cityEditText.getText().toString());
                registerUserDataBinding.registationHeadre.locationLayout.setVisibility(View.GONE);
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

            if (!registerUserDataBinding.nameEditText.getText().toString().equals("")) {
                registerUserDataBinding.registationHeadre.nameVisitingCard.setText(registerUserDataBinding.nameEditText.getText().toString());
                registerUserDataBinding.registationHeadre.nameVisitingCard.setVisibility(View.VISIBLE);
            } else {
                registerUserDataBinding.registationHeadre.nameVisitingCard.setText(registerUserDataBinding.nameEditText.getText().toString());
                registerUserDataBinding.registationHeadre.nameVisitingCard.setVisibility(View.GONE);
            }

            if (!registerUserDataBinding.emailEditText.getText().toString().equals("")) {
                registerUserDataBinding.registationHeadre.emailIdVisitingcard.setText(registerUserDataBinding.emailEditText.getText().toString());
                registerUserDataBinding.registationHeadre.emailLayout.setVisibility(View.VISIBLE);
            } else {
                registerUserDataBinding.registationHeadre.emailIdVisitingcard.setText(registerUserDataBinding.emailEditText.getText().toString());
                registerUserDataBinding.registationHeadre.emailLayout.setVisibility(View.GONE);
            }

            if (!registerUserDataBinding.designationEditText.getText().toString().equals("")) {
                registerUserDataBinding.registationHeadre.designationVisitingcard.setText(registerUserDataBinding.designationEditText.getText().toString());
                registerUserDataBinding.registationHeadre.designationLayout.setVisibility(View.VISIBLE);
            } else {
                registerUserDataBinding.registationHeadre.designationVisitingcard.setText(registerUserDataBinding.designationEditText.getText().toString());
                registerUserDataBinding.registationHeadre.designationLayout.setVisibility(View.GONE);
            }

            if (!registerUserDataBinding.cityEditText.getText().toString().equals("")) {
                registerUserDataBinding.registationHeadre.locationVisitingcard.setText(registerUserDataBinding.cityEditText.getText().toString());
                registerUserDataBinding.registationHeadre.locationLayout.setVisibility(View.VISIBLE);
            } else {
                registerUserDataBinding.registationHeadre.locationVisitingcard.setText(registerUserDataBinding.cityEditText.getText().toString());
                registerUserDataBinding.registationHeadre.locationLayout.setVisibility(View.GONE);
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
                String filename = docFilePath.substring(docFilePath.lastIndexOf("/") + 1);
                registerUserDataBinding.uploadCvTxt.setText(filename);
//                fileBAse64Str = fileBase64Convert(docFilePath);
                try {
                    fileBAse64Str = encodeFileToBase64Binary(loadFileAsBytesArray(docFilePath));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.v("base64FIle", fileBAse64Str);
//                String decodeStr = decodeBase64(fileBAse64Str);
//                Log.v("decode", decodeStr);

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


    public boolean checkExpVisibility() {
        if (registerUserDataBinding.experinaceYearsEditText.getVisibility() == View.VISIBLE) {
            if (Validation.checkIfEmptyOrNot(registerUserDataBinding.experinaceYearsEditText.getText().toString())) {
                CommonMethod.showToast("Please Select Experience", RegisterUserDataActivity.this);

            }
        }
        return true;
    }


    void setDataOnEduErp() {
        String name = GlobalPreferenceManager.getStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_NAME, "");
        String email = GlobalPreferenceManager.getStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_EMAIL_ID, "");
        String stateName = GlobalPreferenceManager.getStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_STATE_NAME, "");
        String stateId = GlobalPreferenceManager.getStringForKey(RegisterUserDataActivity.this, AppConstant.STATE_ID, "");
        String city = GlobalPreferenceManager.getStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_CITY, "");
        String languageKnown = GlobalPreferenceManager.getStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_LANGUAGE_KNOWN, "");
        String gender = GlobalPreferenceManager.getStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_GENDER, "");
        String dob = GlobalPreferenceManager.getStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_DOB, "");
        String phoneNumber = GlobalPreferenceManager.getStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_CONTACT, "");
        String qualificationType = GlobalPreferenceManager.getStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_QUALIFICATION_TYPE, "");
        String experiance = GlobalPreferenceManager.getStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_EXPERIANCE_YEARS, "");
        String role = GlobalPreferenceManager.getStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_ROLE, "");
        String salary = GlobalPreferenceManager.getStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_SALARY, "");
        String projectName = GlobalPreferenceManager.getStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_PROJECT_NAME, "");
        String courseName = GlobalPreferenceManager.getStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_COURSE_NAME, "");
        String batchName = GlobalPreferenceManager.getStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_BATCH_NAME, "");
        String centreName = GlobalPreferenceManager.getStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_CENTRE_NAME, "");
        projectId = GlobalPreferenceManager.getStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_PROJECT_ID, "");
        courseId = GlobalPreferenceManager.getStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_COURSE_ID, "");
        batchId = GlobalPreferenceManager.getStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_BATCH_ID, "");
        centreId = GlobalPreferenceManager.getStringForKey(RegisterUserDataActivity.this, AppConstant.KEY_CENTRE_ID, "");
        registerUserDataBinding.nameEditText.setText(name);
        registerUserDataBinding.emailEditText.setText(email);
        if (!stateName.equals("") && !stateName.equals("null")) {
            registerUserDataBinding.stateEditText.setText(stateName);
        }
        if (!stateName.equals("") && !stateName.equals("null")) {
            this.state_code = stateId;
        }
        if (!city.equals("") && !city.equals("null")) {
            registerUserDataBinding.cityEditText.setText(city);
        }

        if (!languageKnown.equals("") && !languageKnown.equals("null")) {
            registerUserDataBinding.languageKnownEditText.setText(city);
        }

        if (!phoneNumber.equals("") && !phoneNumber.equals("null")) {
            registerUserDataBinding.phoneNumberEditText.setText(phoneNumber);
        }

        if (!qualificationType.equals("") && !qualificationType.equals("null")) {
            registerUserDataBinding.qualificationTypeEditText.setText(qualificationType);
        }

        if (!experiance.equals("0") && !experiance.equals("") && !experiance.equals("null")) {
            registerUserDataBinding.experinaceYearsEditText.setVisibility(View.VISIBLE);
            registerUserDataBinding.experinaceYearsEditText.setText(experiance);
            registerUserDataBinding.experinaceYearsEditTextLnr.setVisibility(View.VISIBLE);
            registerUserDataBinding.experinaceEditText.setText("Yes");
        } else {
            registerUserDataBinding.experinaceYearsEditText.setVisibility(View.GONE);
            registerUserDataBinding.experinaceYearsEditText.setText("");
            registerUserDataBinding.experinaceYearsEditTextLnr.setVisibility(View.GONE);
            registerUserDataBinding.experinaceEditText.setText("No");
        }
        if (!role.equals("") && !role.equals("null")) {
            registerUserDataBinding.designationEditText.setText(role);
        }

        if (!salary.equals("") && !salary.equals("null") && !salary.equals("0")) {
            registerUserDataBinding.salaryEditText.setText(salary);
        }

        if (!centreName.equals("") && !centreName.equals("null")) {
            registerUserDataBinding.centreNameEditText.setText(centreName);
        } else {
            registerUserDataBinding.centreNameEditTextLnr.setVisibility(View.GONE);

        }

        if (!projectName.equals("") && !projectName.equals("null")) {
            registerUserDataBinding.projectNameEditText.setText(projectName);
        } else {
            registerUserDataBinding.projectNameEditTextLnr.setVisibility(View.GONE);
        }

        if (!batchName.equals("") && !batchName.equals("null")) {
            registerUserDataBinding.batchNameEditText.setText(projectName);
        } else {
            registerUserDataBinding.batchNameEditTextLnr.setVisibility(View.GONE);
        }

        if (!courseName.equals("") && !courseName.equals("null")) {
            registerUserDataBinding.courseNameEditText.setText(courseName);
        } else {
            registerUserDataBinding.courseNameEditTextLnr.setVisibility(View.GONE);
        }


        if (gender.equals("M")) {
            registerUserDataBinding.male.setChecked(true);
        }
        if (gender.equals("F")) {
            registerUserDataBinding.female.setChecked(true);
        }


    }

    void getStates() {
        ServiceHandler serviceHandler = new ServiceHandler(RegisterUserDataActivity.this);
        serviceHandler.StringRequest(Request.Method.GET, "", AppConstant.GET_STATE, true, new ServiceHandler.VolleyCallback() {
            @Override
            public void onSuccess(String result) {

                Log.v("Response", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getBoolean("success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        states = new String[jsonArray.length()];
                        statesCode = new String[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = (JSONObject) jsonArray.get(i);
                            String stateName = object.getString("StateName");
                            int stateCode = object.getInt("StCode");
                            if (!stateName.equals("empty")) {
                                listStates.add(stateName);
                                states[i] = stateName;
                                statesCode[i] = String.valueOf(stateCode);
                            }
                        }

                        ArrayAdapter adapter1 = new ArrayAdapter<String>(RegisterUserDataActivity.this, R.layout.drop_down_item, states);
                        registerUserDataBinding.stateEditText.setAdapter(adapter1);
                        registerUserDataBinding.stateEditText.showDropDown();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }


    private String encodeFileToBase64Binary(byte[] allData) {
        String encoded = Base64.encodeToString(allData, Base64.NO_WRAP);
        return encoded;
    }

    public static byte[] loadFileAsBytesArray(String fileName) throws Exception {
        File file = new File(fileName);
        int length = (int) file.length();
        BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
        byte[] bytes = new byte[length];
        reader.read(bytes, 0, length);
        reader.close();
        return bytes;

    }


    void setMobileDataCard() {
        if (!registerUserDataBinding.phoneNumberEditText.getText().toString().equals("")) {
            registerUserDataBinding.registationHeadre.mobileNumberVisitingcard.setText(registerUserDataBinding.phoneNumberEditText.getText().toString());
            registerUserDataBinding.registationHeadre.mobileLayout.setVisibility(View.VISIBLE);
        }
    }
}