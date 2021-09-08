package com.twocoms.rojgarkendra.myprofile.controler;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.databinding.ActivityUserProfileBinding;
import com.twocoms.rojgarkendra.global.controler.VolleyMultipartRequest;
import com.twocoms.rojgarkendra.global.controler.VolleySingleton;
import com.twocoms.rojgarkendra.global.model.AppConstant;
import com.twocoms.rojgarkendra.global.model.CommonMethod;
import com.twocoms.rojgarkendra.global.model.GlobalPreferenceManager;
import com.twocoms.rojgarkendra.global.model.LoadingDialog;
import com.twocoms.rojgarkendra.global.model.ServiceHandler;
import com.twocoms.rojgarkendra.global.model.Validation;
import com.twocoms.rojgarkendra.registrationscreen.controler.ImagePickerActivity;
import com.twocoms.rojgarkendra.registrationscreen.controler.RegisterUserDataActivity;

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
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class UserProfileActivity extends AppCompatActivity {
    public ActivityUserProfileBinding userDataBinding;
    String userId;
    String[] states;
    String[] statesCode;
    ArrayList<String> listStates;
    String state_code = "";
    String responseMsg;
    String eduJobStr;
    String projectId = "", courseId = "", batchId = "", centreId = "";
    String userProfileImgStr = "";
    String userResumeUrlStr = "";
    public static final int REQUEST_IMAGE = 100;
    private AlertDialog dialog;
    String fileBAse64Str = "";
    String docFilePath;
    String android_id;
    private ArrayList<String> qualificationList = new ArrayList<>();
    private ArrayList<String> expList = new ArrayList<>();
    String mobile_no, radioBtnText, radioButtonSmallText;
    String name = "", email = "", stateName = "", stateId = "", city = "", languageKnown = "", gender = "", dob = "", phoneNumber = "",
            qualificationType = "", experiance = "", role = "", salary = "", projectName = "", courseName = "", batchName = "", centreName = "";

    String name1 = "", email1 = "", stateName1 = "", stateId1 = "", city1 = "", languageKnown1 = "", gender1 = "", dob1 = "", phoneNumber1 = "",
            qualificationType1 = "", experiance1 = "", role1 = "", salary1 = "", projectName1 = "", courseName1 = "", batchName1 = "", centreName1 = "";
    String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Calendar mcalendar;
    boolean isradioBtnChecked = false;
    RadioButton radioButtonGender;
    private static String imageEncoded;
    boolean isProfileImgUpdated = false;
    boolean isCVUpadted = false;
    Uri mInvitationUrl;
    private LoadingDialog loadingDialog;
    public Bitmap bitmap;

    String[] cityMain;
    String[] cityCode;
    ArrayList<String> listCity;
    String city_code = "";
    String fileName = "";
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDataBinding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        View view = userDataBinding.getRoot();
        setContentView(view);
        if (CommonMethod.isOnline(UserProfileActivity.this)) {
            initialization();
        } else {
            showDialog("No Internet Connection", UserProfileActivity.this);
            //CommonMethod.showToast("No Internet Connection",UserProfileActivity.this);
        }
    }

    void initialization() {
        mobile_no = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_CONTACT, "");
        userDataBinding.profileHeader.mobileLayout.setVisibility(View.VISIBLE);
        userDataBinding.profileHeader.mobileNumberVisitingcard.setText(mobile_no);
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
        mcalendar = Calendar.getInstance();
        userId = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_USER_ID, "");
        eduJobStr = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_IS_EDURP, "");

        listStates = new ArrayList<>();
        getProfileDetails();
//        createReferralLink();

        //getStates();
        setVisibilty();
        onCick();
        setdisable();
        enableOrDisableSaveBtn();
    }

    void onCick() {
        userDataBinding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDataBinding.savebutton.setVisibility(View.VISIBLE);
                userDataBinding.cancel.setVisibility(View.VISIBLE);
                userDataBinding.edit.setVisibility(View.GONE);

                //userDataBinding.mainLnr.setAlpha((float) 1.0);
                setEnable();
            }
        });

        userDataBinding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDataBinding.savebutton.setVisibility(View.GONE);
                userDataBinding.edit.setVisibility(View.VISIBLE);
                userDataBinding.cancel.setVisibility(View.GONE);
                //userDataBinding.mainLnr.setAlpha((float) 0.5);
                setdisable();
            }
        });

        userDataBinding.profileHeader.userImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onProfileImageClick();
            }
        });

        userDataBinding.uploadCvTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDocument();
            }
        });

        userDataBinding.uploadCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDocument();
            }
        });

        userDataBinding.savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validation()) {
//                    updateUserDetails(jsonUpdateUser());
                    updateUserData(UserProfileActivity.this,jsonUpdateUser());
                }
            }
        });

        userDataBinding.backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        userDataBinding.qualificationTypeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayAdapter adapter = new ArrayAdapter<String>(UserProfileActivity.this, R.layout.drop_down_item, qualificationList);
                userDataBinding.qualificationTypeEditText.setAdapter(adapter);
                {
                    userDataBinding.qualificationTypeEditText.showDropDown();
                }
            }
        });

        userDataBinding.experinaceEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayAdapter adapter = new ArrayAdapter<String>(UserProfileActivity.this, R.layout.drop_down_item, expList);
                userDataBinding.experinaceEditText.setAdapter(adapter);
                {
                    userDataBinding.experinaceEditText.showDropDown();
                }
            }
        });

        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                mcalendar.set(Calendar.YEAR, year);
                mcalendar.set(Calendar.MONTH, monthOfYear);
                mcalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                userDataBinding.dobKnownEditText.setText(sdf.format(mcalendar.getTime()));


            }

        };

        userDataBinding.dobKnownEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //userDataBinding.dobKnownEditText.setText("");
                DatePickerDialog dialog = new DatePickerDialog(UserProfileActivity.this, datePickerListener, mcalendar
                        .get(Calendar.YEAR), mcalendar.get(Calendar.MONTH), mcalendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMaxDate((long) (new Date().getTime() - 60 * 60 * 1000 * 24 * 30.41666666 * 12 * 18));//7 * 24 * 60 * 60 * 1000  604800000L
                dialog.show();
            }
        });

        userDataBinding.stateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStates();
            }
        });

        userDataBinding.stateEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int j = listStates.indexOf(userDataBinding.stateEditText.getText().toString());
                state_code = statesCode[j];
                Log.v("statecode", state_code);
            }
        });
        userDataBinding.experinaceEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (userDataBinding.experinaceEditText.getText().toString().equals("Yes")) {
                    userDataBinding.experinaceYearsEditText.setVisibility(View.VISIBLE);
                    //userDataBinding.experinaceYearsEditText.setText("");
                    userDataBinding.experinaceYearsEditTextLnr.setVisibility(View.VISIBLE);
                } else {
                    userDataBinding.experinaceYearsEditText.setVisibility(View.GONE);
                    //userDataBinding.experinaceYearsEditText.setText("");
                    userDataBinding.experinaceYearsEditTextLnr.setVisibility(View.GONE);
                }
            }
        });

        userDataBinding.downloadCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userResumeUrlStr.equals("") && !userResumeUrlStr.equals("null")) {
                    String fullPath = String.format(Locale.ENGLISH, userResumeUrlStr, "PDF_URL_HERE");
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fullPath));
                    startActivity(browserIntent);
                }
            }
        });


//        userDataBinding.cityEditText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(!userDataBinding.stateEditText.getText().toString().equals("")){
//                    getCity(state_code);
//                }
//                else {
//                    CommonMethod.showToast("Please select state first.",UserProfileActivity.this);
//                }
//
//
//            }
//        });
//
//        userDataBinding.cityEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                int j = listCity.indexOf(userDataBinding.cityEditText.getText().toString());
//                city_code = cityCode[j];
//                Log.v("city_code", city_code);
//            }
//        });

    }

    void getCity(String city) {
        Log.v("URL City",AppConstant.GET_CITY+city);
        ServiceHandler serviceHandler = new ServiceHandler(UserProfileActivity.this);
        serviceHandler.StringRequest(Request.Method.GET, "", AppConstant.GET_CITY+cityMain, true, new ServiceHandler.VolleyCallback() {
            @Override
            public void onSuccess(String result) {

                Log.v("Response", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getBoolean("success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        cityMain = new String[jsonArray.length()];
                        cityCode = new String[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = (JSONObject) jsonArray.get(i);
                            String stateName = object.getString("city");
                            int stateCode = object.getInt("id");
//                            if (!stateName.equals("empty")) {
                            listCity.add(stateName);
                            cityMain[i] = stateName;
                            cityCode[i] = String.valueOf(stateCode);
//                            }
                        }

                        ArrayAdapter adapter1 = new ArrayAdapter<String>(UserProfileActivity.this, R.layout.drop_down_item, cityMain);
                        userDataBinding.cityEditText.setAdapter(adapter1);
                        userDataBinding.cityEditText.showDropDown();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }


    void setdisable() {
        userDataBinding.profileHeader.userImg.setFocusable(false);
        userDataBinding.profileHeader.userImg.setEnabled(false);

        userDataBinding.nameEditText.setFocusable(false);
        userDataBinding.nameEditText.setEnabled(false);
        userDataBinding.emailEditText.setFocusable(false);
        userDataBinding.emailEditText.setEnabled(false);
        userDataBinding.stateEditText.setFocusable(false);
        userDataBinding.stateEditText.setEnabled(false);
        userDataBinding.cityEditText.setFocusable(false);
        userDataBinding.cityEditText.setEnabled(false);
        userDataBinding.languageKnownEditText.setFocusable(false);
        userDataBinding.languageKnownEditText.setEnabled(false);
        userDataBinding.genderGrp.setFocusable(false);
        userDataBinding.genderGrp.setEnabled(false);
        userDataBinding.male.setFocusable(false);
        userDataBinding.male.setEnabled(false);
        userDataBinding.female.setFocusable(false);
        userDataBinding.female.setEnabled(false);
        userDataBinding.dobKnownEditText.setFocusable(false);
        userDataBinding.dobKnownEditText.setEnabled(false);
        userDataBinding.phoneNumberEditText.setFocusable(false);
        userDataBinding.phoneNumberEditText.setEnabled(false);
        userDataBinding.qualificationTypeEditText.setFocusable(false);
        userDataBinding.qualificationTypeEditText.setEnabled(false);
        userDataBinding.experinaceEditText.setFocusable(false);
        userDataBinding.experinaceEditText.setEnabled(false);
        userDataBinding.experinaceYearsEditText.setFocusable(false);
        userDataBinding.experinaceYearsEditText.setEnabled(false);
        userDataBinding.designationEditText.setFocusable(false);
        userDataBinding.designationEditText.setEnabled(false);
        userDataBinding.salaryEditText.setFocusable(false);
        userDataBinding.salaryEditText.setEnabled(false);
        userDataBinding.uploadCvLnr.setFocusable(false);
        userDataBinding.uploadCvLnr.setEnabled(false);
        userDataBinding.uploadCv.setFocusable(false);
        userDataBinding.uploadCv.setEnabled(false);
        userDataBinding.uploadCvTxt.setFocusable(false);
        userDataBinding.uploadCvTxt.setEnabled(false);
        if (eduJobStr.equals("Y")) {
            userDataBinding.centreNameEditText.setFocusable(false);
            userDataBinding.centreNameEditText.setEnabled(false);
            userDataBinding.batchNameEditText.setFocusable(false);
            userDataBinding.batchNameEditText.setEnabled(false);
            userDataBinding.projectNameEditText.setFocusable(false);
            userDataBinding.projectNameEditText.setEnabled(false);
            userDataBinding.courseNameEditText.setFocusable(false);
            userDataBinding.courseNameEditText.setEnabled(false);
        }
    }

    void setEnable() {
        userDataBinding.profileHeader.userImg.setFocusable(true);
        userDataBinding.profileHeader.userImg.setEnabled(true);
        userDataBinding.nameEditText.setFocusable(true);
        userDataBinding.nameEditText.setEnabled(true);
        userDataBinding.nameEditText.setFocusableInTouchMode(true);
        userDataBinding.emailEditText.setFocusable(true);
        userDataBinding.emailEditText.setEnabled(true);
        userDataBinding.emailEditText.setFocusableInTouchMode(true);
        userDataBinding.stateEditText.setFocusable(false);
        userDataBinding.stateEditText.setEnabled(true);
        userDataBinding.cityEditText.setFocusable(true);
        userDataBinding.cityEditText.setEnabled(true);
        userDataBinding.cityEditText.setFocusableInTouchMode(true);
        userDataBinding.languageKnownEditText.setFocusable(true);
        userDataBinding.languageKnownEditText.setEnabled(true);
        userDataBinding.languageKnownEditText.setFocusableInTouchMode(true);
        userDataBinding.genderGrp.setFocusable(true);
        userDataBinding.genderGrp.setEnabled(true);
        userDataBinding.male.setFocusable(true);
        userDataBinding.male.setEnabled(true);
        userDataBinding.female.setFocusable(true);
        userDataBinding.female.setEnabled(true);
        userDataBinding.dobKnownEditText.setFocusable(false);
        userDataBinding.dobKnownEditText.setEnabled(true);
        userDataBinding.dobKnownEditText.setFocusableInTouchMode(false);
        userDataBinding.phoneNumberEditText.setFocusable(false);
        userDataBinding.phoneNumberEditText.setEnabled(true);
        userDataBinding.qualificationTypeEditText.setFocusable(false);
        userDataBinding.qualificationTypeEditText.setEnabled(true);
        userDataBinding.experinaceEditText.setFocusable(false);
        userDataBinding.experinaceEditText.setEnabled(true);
        userDataBinding.experinaceYearsEditText.setFocusable(true);
        userDataBinding.experinaceYearsEditText.setEnabled(true);
        userDataBinding.experinaceYearsEditText.setFocusableInTouchMode(true);
        userDataBinding.designationEditText.setFocusable(true);
        userDataBinding.designationEditText.setEnabled(true);
        userDataBinding.designationEditText.setFocusableInTouchMode(true);
        userDataBinding.salaryEditText.setFocusable(true);
        userDataBinding.salaryEditText.setEnabled(true);
        userDataBinding.salaryEditText.setFocusableInTouchMode(true);
        userDataBinding.uploadCvLnr.setFocusable(true);
        userDataBinding.uploadCvLnr.setEnabled(true);
        userDataBinding.uploadCv.setFocusable(true);
        userDataBinding.uploadCv.setEnabled(true);
        userDataBinding.uploadCv.setFocusableInTouchMode(true);
        userDataBinding.uploadCvTxt.setFocusable(true);
        userDataBinding.uploadCvTxt.setEnabled(true);
        userDataBinding.uploadCvTxt.setFocusableInTouchMode(true);
        if (eduJobStr.equals("Y")) {
            userDataBinding.centreNameEditText.setFocusable(false);
            userDataBinding.centreNameEditText.setEnabled(true);
            userDataBinding.batchNameEditText.setFocusable(false);
            userDataBinding.batchNameEditText.setEnabled(true);
            userDataBinding.projectNameEditText.setFocusable(false);
            userDataBinding.projectNameEditText.setEnabled(true);
            userDataBinding.courseNameEditText.setFocusable(false);
            userDataBinding.courseNameEditText.setEnabled(true);
        }
    }


    public boolean validation() {
        if (eduJobStr.equals("Y")) {
            if (Validation.checkIfEmptyOrNot(userDataBinding.nameEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Name", UserProfileActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(userDataBinding.phoneNumberEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Phone No", UserProfileActivity.this);
                return false;
            } else if (userDataBinding.emailEditText.getText().toString().isEmpty()) {
                CommonMethod.showToast("Please Enter Email Id", UserProfileActivity.this);
                return false;
            } else if (!userDataBinding.emailEditText.getText().toString().matches(emailPattern)) {
                CommonMethod.showToast("Please Enter Valid Email Id", UserProfileActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(userDataBinding.dobKnownEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter DOB", UserProfileActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(userDataBinding.languageKnownEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Language Known", UserProfileActivity.this);
                return false;
            } else if (!checkGenderRadioGrp()) {
                CommonMethod.showToast("Please select Gender", UserProfileActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(userDataBinding.stateEditText.getText().toString())) {
                CommonMethod.showToast("Please Select State", UserProfileActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(userDataBinding.cityEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter City/District", UserProfileActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(userDataBinding.qualificationTypeEditText.getText().toString())) {
                CommonMethod.showToast("Please Select Qualification", UserProfileActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(userDataBinding.experinaceEditText.getText().toString())) {
                CommonMethod.showToast("Please Select Experience", UserProfileActivity.this);
                return false;
            } else if (userDataBinding.experinaceYearsEditText.getVisibility() == View.VISIBLE) {
                if (Validation.checkIfEmptyOrNot(userDataBinding.experinaceYearsEditText.getText().toString())) {
                    CommonMethod.showToast("Please Enter Experience Year", UserProfileActivity.this);
                    return false;
                }
            } else if (Validation.checkIfEmptyOrNot(userDataBinding.designationEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Designation", UserProfileActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(userDataBinding.salaryEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Salary", UserProfileActivity.this);
                return false;
            }
        } else {
            if (Validation.checkIfEmptyOrNot(userDataBinding.nameEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Name", UserProfileActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(userDataBinding.phoneNumberEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Phone No", UserProfileActivity.this);
                return false;
            } else if (userDataBinding.emailEditText.getText().toString().isEmpty()) {
                CommonMethod.showToast("Please Enter Email Id", UserProfileActivity.this);
                return false;
            } else if (!userDataBinding.emailEditText.getText().toString().matches(emailPattern)) {
                CommonMethod.showToast("Please Enter Valid Email Id", UserProfileActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(userDataBinding.dobKnownEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter DOB", UserProfileActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(userDataBinding.languageKnownEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Language Known", UserProfileActivity.this);
                return false;
            } else if (!checkGenderRadioGrp()) {
                CommonMethod.showToast("Please select Gender", UserProfileActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(userDataBinding.stateEditText.getText().toString())) {
                CommonMethod.showToast("Please Select State", UserProfileActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(userDataBinding.cityEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter City/District", UserProfileActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(userDataBinding.qualificationTypeEditText.getText().toString())) {
                CommonMethod.showToast("Please Select Qualification", UserProfileActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(userDataBinding.experinaceEditText.getText().toString())) {
                CommonMethod.showToast("Please Select Experience", UserProfileActivity.this);
                return false;
            } else if (userDataBinding.experinaceYearsEditText.getVisibility() == View.VISIBLE) {
                if (Validation.checkIfEmptyOrNot(userDataBinding.experinaceYearsEditText.getText().toString())) {
                    CommonMethod.showToast("Please Enter Experience Year", UserProfileActivity.this);
                    return false;
                }
            } else if (Validation.checkIfEmptyOrNot(userDataBinding.designationEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Designation", UserProfileActivity.this);
                return false;
            } else if (Validation.checkIfEmptyOrNot(userDataBinding.salaryEditText.getText().toString())) {
                CommonMethod.showToast("Please Enter Salary", UserProfileActivity.this);
                return false;
            }
        }
        return true;
    }

    public boolean checkGenderRadioGrp() {
        if (userDataBinding.genderGrp.getCheckedRadioButtonId() == -1) {
            isradioBtnChecked = false;
            //CommonMethod.showToast("Please select Gender", RegisterUserDataActivity.this);
            // no radio buttons are checked
            return false;
        } else {
            // one of the radio buttons is checked

            radioButtonGender = (RadioButton) findViewById(userDataBinding.genderGrp.getCheckedRadioButtonId());

            radioBtnText = radioButtonGender.getTag().toString();
            if (radioBtnText.equals("Male")) {
                radioButtonSmallText = "M";
            } else {
                radioButtonSmallText = "F";
            }
            return true;
        }

    }


    void getProfileDetails() {
        ServiceHandler serviceHandler = new ServiceHandler(UserProfileActivity.this);
        serviceHandler.StringRequest(Request.Method.GET, "", AppConstant.GET_USER_DETAILS + userId, true, new ServiceHandler.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                Log.v("Response", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getBoolean("success")) {
                        JSONObject object = jsonObject.getJSONObject("data");
                        responseMsg = jsonObject.getString("message");
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this, AppConstant.KEY_USER_ID, object.getString(AppConstant.KEY_USER_ID));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this, AppConstant.KEY_NAME, object.getString(AppConstant.KEY_NAME));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this, AppConstant.KEY_EMAIL_ID, object.getString(AppConstant.KEY_EMAIL_ID));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this, AppConstant.KEY_CONTACT, object.getString(AppConstant.KEY_CONTACT));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this, AppConstant.KEY_CONTACT_VERIFIED, object.getString(AppConstant.KEY_CONTACT_VERIFIED));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this, AppConstant.KEY_STATE_NAME, object.getString(AppConstant.KEY_STATE_NAME));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this, AppConstant.STATE_ID, object.getString(AppConstant.STATE_ID));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this, AppConstant.KEY_CITY, object.getString(AppConstant.KEY_CITY));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this, AppConstant.KEY_DOB, object.getString(AppConstant.KEY_DOB));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this, AppConstant.KEY_GENDER, object.getString(AppConstant.KEY_GENDER));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this, AppConstant.KEY_QUALIFICATION_TYPE, object.getString(AppConstant.KEY_QUALIFICATION_TYPE));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this, AppConstant.KEY_EXPERIANCE_YEARS, object.getString(AppConstant.KEY_EXPERIANCE_YEARS));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this, AppConstant.KEY_EXPERIANCE_MONTH, object.getString(AppConstant.KEY_EXPERIANCE_MONTH));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this, AppConstant.KEY_SALARY, object.getString(AppConstant.KEY_SALARY));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this, AppConstant.KEY_REFERAL_CODE, object.getString(AppConstant.KEY_REFERAL_CODE));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this, AppConstant.KEY_ROLE, object.getString(AppConstant.KEY_ROLE));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this, AppConstant.KEY_IS_EDURP, object.getString(AppConstant.KEY_IS_EDURP));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this, AppConstant.KEY_WALLET_AMOUNT, object.getString(AppConstant.KEY_WALLET_AMOUNT));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this, AppConstant.KEY_LANGUAGE_KNOWN, object.getString(AppConstant.KEY_LANGUAGE_KNOWN));
                        if (object.has(AppConstant.KEY_PROFILE_URL)) {
                            GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this, AppConstant.KEY_PROFILE_URL, object.getString(AppConstant.KEY_PROFILE_URL));
                        } else {
                            GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this, AppConstant.KEY_PROFILE_URL, "");
                        }
                        if (object.has(AppConstant.KEY_RESUME_URL)) {
                            GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this, AppConstant.KEY_RESUME_URL, object.getString(AppConstant.KEY_RESUME_URL));
                        } else {
                            GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this, AppConstant.KEY_RESUME_URL, "");
                        }
                        if (object.getString("eduErp").equals("Y")) {
                            GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this, AppConstant.KEY_COURSE_ID, object.getString(AppConstant.KEY_COURSE_ID));
                            GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this, AppConstant.KEY_CENTRE_ID, object.getString(AppConstant.KEY_CENTRE_ID));
                            GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this, AppConstant.KEY_PROJECT_ID, object.getString(AppConstant.KEY_PROJECT_ID));
                            GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this, AppConstant.KEY_BATCH_ID, object.getString(AppConstant.KEY_BATCH_ID));
//                            GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_COURSE_NAME,object.getString(AppConstant.KEY_COURSE_NAME));
//                            GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_CENTRE_NAME,object.getString(AppConstant.KEY_CENTRE_NAME));
//                            GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_PROJECT_NAME,object.getString(AppConstant.KEY_PROJECT_NAME));
//                            GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_BATCH_NAME,object.getString(AppConstant.KEY_BATCH_NAME));
                        }

                        setDataUserProfile();

                    } else {
                        CommonMethod.showToast(responseMsg, UserProfileActivity.this);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    public void showDialog(String msg, final Context context) {
        AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
        alertbox.setTitle(R.string.app_name);
        alertbox.setMessage(msg);
        alertbox.setCancelable(false);
        alertbox.setPositiveButton("OK", new
                DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                });
        alertbox.show();
    }

    void getStates() {
        ServiceHandler serviceHandler = new ServiceHandler(UserProfileActivity.this);
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
                            String stateName = object.getString("name");
                            int stateCode = object.getInt("id");
                            //if(!stateName.equals("empty")) {
                            listStates.add(stateName);
                            states[i] = stateName;
                            statesCode[i] = String.valueOf(stateCode);
                            // }
                        }

                        ArrayAdapter adapter1 = new ArrayAdapter<String>(UserProfileActivity.this, R.layout.drop_down_item, states);
                        userDataBinding.stateEditText.setAdapter(adapter1);
                        userDataBinding.stateEditText.showDropDown();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    void setDataUserProfile() {
        name = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_NAME, "");
        email = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_EMAIL_ID, "");
        stateName = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_STATE_NAME, "");
        stateId = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.STATE_ID, "");
        city = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_CITY, "");
        languageKnown = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_LANGUAGE_KNOWN, "");
        gender = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_GENDER, "");
        dob = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_DOB, "");
        phoneNumber = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_CONTACT, "");
        qualificationType = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_QUALIFICATION_TYPE, "");
        experiance = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_EXPERIANCE_YEARS, "");
        role = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_ROLE, "");
        salary = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_SALARY, "");
        projectName = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_PROJECT_NAME, "");
        courseName = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_COURSE_NAME, "");
        batchName = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_BATCH_NAME, "");
        centreName = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_CENTRE_NAME, "");
        projectId = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_PROJECT_ID, "");
        courseId = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_COURSE_ID, "");
        batchId = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_BATCH_ID, "");
        centreId = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_CENTRE_ID, "");
        userProfileImgStr = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_PROFILE_URL, "");
        userResumeUrlStr = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_RESUME_URL, "");


        Glide.with(this)
                .load(userProfileImgStr)
                .into(userDataBinding.profileHeader.userImg);

        if (!userResumeUrlStr.equals("") && !userResumeUrlStr.equals("null")) {
            userDataBinding.uploadCvTxt.setText("My Resume");
            userDataBinding.downloadCV.setVisibility(View.VISIBLE);
        } else {
            userDataBinding.uploadCvTxt.setText("Upload Resume");
            userDataBinding.downloadCV.setVisibility(View.GONE);
        }

        userDataBinding.nameEditText.setText(name);
        userDataBinding.emailEditText.setText(email);
        if (!stateName.equals("") && !stateName.equals("null")) {
            userDataBinding.stateEditText.setText(stateName);
        }
        if (!stateId.equals("") && !stateId.equals("null")) {
            this.state_code = stateId;

        }
        if (!city.equals("") && !city.equals("null")) {
            userDataBinding.cityEditText.setText(city);
        }

        if (!languageKnown.equals("") && !languageKnown.equals("null")) {
            userDataBinding.languageKnownEditText.setText(languageKnown);
        }

        if (!phoneNumber.equals("") && !phoneNumber.equals("null")) {
            userDataBinding.phoneNumberEditText.setText(phoneNumber);

        }

        if (!qualificationType.equals("") && !qualificationType.equals("null")) {
            userDataBinding.qualificationTypeEditText.setText(qualificationType);
        }

        if (!experiance.equals("0") && !experiance.equals("") && !experiance.equals("null")) {
            userDataBinding.experinaceYearsEditText.setVisibility(View.VISIBLE);
            userDataBinding.experinaceYearsEditText.setText(experiance);
            userDataBinding.experinaceYearsEditTextLnr.setVisibility(View.VISIBLE);
            userDataBinding.experinaceEditText.setText("Yes");
        } else {
            userDataBinding.experinaceYearsEditText.setVisibility(View.GONE);
            userDataBinding.experinaceYearsEditText.setText("");
            userDataBinding.experinaceYearsEditTextLnr.setVisibility(View.GONE);
            userDataBinding.experinaceEditText.setText("No");
        }
        if (!dob.equals("") && !dob.equals("null")) {
            userDataBinding.dobKnownEditText.setText(dob);
        }
        if (!role.equals("") && !role.equals("null")) {
            userDataBinding.designationEditText.setText(role);
        }

        if (!salary.equals("") && !salary.equals("null") && !salary.equals("0")) {
            userDataBinding.salaryEditText.setText(salary);
        }

        if (eduJobStr.equals("Y")) {
            if (!centreName.equals("") && !centreName.equals("null")) {
                userDataBinding.centreNameEditText.setText(centreName);
            } else {
                userDataBinding.centreNameEditTextLnr.setVisibility(View.GONE);

            }

            if (!projectName.equals("") && !projectName.equals("null")) {
                userDataBinding.projectNameEditText.setText(projectName);
            } else {
                userDataBinding.projectNameEditTextLnr.setVisibility(View.GONE);
            }

            if (!batchName.equals("") && !batchName.equals("null")) {
                userDataBinding.batchNameEditText.setText(projectName);
            } else {
                userDataBinding.batchNameEditTextLnr.setVisibility(View.GONE);
            }

            if (!courseName.equals("") && !courseName.equals("null")) {
                userDataBinding.courseNameEditText.setText(courseName);
            } else {
                userDataBinding.courseNameEditTextLnr.setVisibility(View.GONE);
            }
        }


        if (gender.equals("M")) {
            userDataBinding.male.setChecked(true);
        }
        if (gender.equals("F")) {
            userDataBinding.female.setChecked(true);
        }
    }

    void setVisibilty() {
        try {
            if (eduJobStr.equals("Y")) {
                userDataBinding.centreNameEditTextLnr.setVisibility(View.VISIBLE);
                userDataBinding.projectNameEditTextLnr.setVisibility(View.VISIBLE);
                userDataBinding.batchNameEditTextLnr.setVisibility(View.VISIBLE);
                userDataBinding.courseNameEditTextLnr.setVisibility(View.VISIBLE);
                userDataBinding.coursenameHeader.setVisibility(View.VISIBLE);
                userDataBinding.courseNameBottomLine.setVisibility(View.GONE);

            } else {
                userDataBinding.centreNameEditTextLnr.setVisibility(View.GONE);
                userDataBinding.projectNameEditTextLnr.setVisibility(View.GONE);
                userDataBinding.batchNameEditTextLnr.setVisibility(View.GONE);
                userDataBinding.courseNameEditTextLnr.setVisibility(View.GONE);
                userDataBinding.coursenameHeader.setVisibility(View.GONE);
                userDataBinding.courseNameBottomLine.setVisibility(View.GONE);

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }

    void getUpadtedFields() {
        name1 = userDataBinding.nameEditText.getText().toString().trim();
        email1 = userDataBinding.emailEditText.getText().toString().trim();
        stateName1 = userDataBinding.stateEditText.getText().toString().trim();
        stateName1 = userDataBinding.stateEditText.getText().toString().trim();
        city1 = userDataBinding.cityEditText.getText().toString().trim();
        languageKnown1 = userDataBinding.languageKnownEditText.getText().toString().trim();
        dob1 = userDataBinding.dobKnownEditText.getText().toString().trim();
        phoneNumber1 = userDataBinding.phoneNumberEditText.getText().toString().trim();
        qualificationType1 = userDataBinding.qualificationTypeEditText.getText().toString().trim();
        experiance1 = userDataBinding.experinaceYearsEditText.getText().toString().trim();
        role1 = userDataBinding.designationEditText.getText().toString().trim();
        salary1 = userDataBinding.salaryEditText.getText().toString().trim();
    }


    JSONObject jsonUpdateUser() {
        getUpadtedFields();
        JSONObject Json = new JSONObject();
        if (eduJobStr.equals("Y")) {
            try {
                if (!name1.equals(name)) {
                    Json.put(AppConstant.KEY_NAME, name1);
                }
                if (!email1.equals(email)) {
                    Json.put(AppConstant.KEY_EMAIL_ID, email1);
                }
                if (!state_code.equals(GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.STATE_ID, ""))) {
                    Json.put(AppConstant.STATE_ID, state_code);
                }
                if (!city1.equals(city)) {
                    Json.put(AppConstant.KEY_CITY, city1);
                }
                if (!languageKnown1.equals(languageKnown)) {
                    Json.put(AppConstant.KEY_LANGUAGE_KNOWN, languageKnown1);
                }
                if (!dob1.equals(dob)) {
                    Json.put(AppConstant.KEY_DOB, dob1);
                }

                Json.put(AppConstant.KEY_CONTACT, phoneNumber1);
                Json.put(AppConstant.KEY_USER_ID, userId);

                if (!radioButtonSmallText.equals(GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_GENDER, ""))) {
                    Json.put(AppConstant.KEY_GENDER, radioButtonSmallText);
                }
                if (!qualificationType1.equals(qualificationType)) {
                    Json.put(AppConstant.KEY_QUALIFICATION_TYPE, qualificationType1);
                }
                if (!experiance1.equals(experiance)) {
                    Json.put(AppConstant.KEY_EXPERIANCE_YEARS, experiance1);
                    Json.put(AppConstant.KEY_EXPERIANCE_MONTH, "0");
                }
                if (!role1.equals(role)) {
                    Json.put(AppConstant.KEY_ROLE, role);
                }
                if (!salary1.equals(salary)) {
                    Json.put(AppConstant.KEY_SALARY, salary);
                }
                Json.put(AppConstant.KEY_DEVICE_ID, android_id);
                Json.put(AppConstant.KEY_OS_TYPE, "A");
                Json.put(AppConstant.KEY_ROLE, userDataBinding.designationEditText.getText().toString());
                Json.put(AppConstant.KEY_NOTIFICATION_ID, GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_DEVICE_TOKEN, ""));
                Json.put(AppConstant.KEY_CENTRE_ID, centreId);
                Json.put(AppConstant.KEY_PROJECT_ID, projectId);
                Json.put(AppConstant.KEY_BATCH_ID, batchId);
                Json.put(AppConstant.KEY_COURSE_ID, courseId);
//                if (isProfileImgUpdated) {
//                    Json.put(AppConstant.KEY_PROFILE_PHOTO, img_user_profile_base_64);
//                }
//                if (isCVUpadted) {
//                    Json.put(AppConstant.KEY_RESUME, fileBAse64Str);
//                }
                Log.v("json", Json.toString());
//                CommonMethod.showToast("Json",UserProfileActivity.this);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            try {
                if (!name1.equals(name)) {
                    Json.put(AppConstant.KEY_NAME, name1);
                }
                if (!email1.equals(email)) {
                    Json.put(AppConstant.KEY_EMAIL_ID, email1);
                }
                if (!state_code.equals(GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.STATE_ID, ""))) {
                    Json.put(AppConstant.STATE_ID, state_code);
                }
                if (!city1.equals(city)) {
                    Json.put(AppConstant.KEY_CITY, city1);
                }
                if (!languageKnown1.equals(languageKnown)) {
                    Json.put(AppConstant.KEY_LANGUAGE_KNOWN, languageKnown1);
                }
                if (!dob1.equals(dob)) {
                    Json.put(AppConstant.KEY_DOB, dob1);
                }

                Json.put(AppConstant.KEY_CONTACT, phoneNumber1);
                Json.put(AppConstant.KEY_USER_ID, userId);

                if (!radioButtonSmallText.equals(GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_GENDER, ""))) {
                    Json.put(AppConstant.KEY_GENDER, radioButtonSmallText);
                }
                if (!qualificationType1.equals(qualificationType)) {
                    Json.put(AppConstant.KEY_QUALIFICATION_TYPE, qualificationType1);
                }
                if (!experiance1.equals(experiance)) {
                    Json.put(AppConstant.KEY_EXPERIANCE_YEARS, experiance1);
                    Json.put(AppConstant.KEY_EXPERIANCE_MONTH, "0");
                }
                if (!role1.equals(role)) {
                    Json.put(AppConstant.KEY_ROLE, role);
                }
                if (!salary1.equals(salary)) {
                    Json.put(AppConstant.KEY_SALARY, salary);
                }
                Json.put(AppConstant.KEY_DEVICE_ID, android_id);
                Json.put(AppConstant.KEY_OS_TYPE, "A");
                Json.put(AppConstant.KEY_ROLE, userDataBinding.designationEditText.getText().toString());
                Json.put(AppConstant.KEY_NOTIFICATION_ID, GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_DEVICE_TOKEN, ""));
//                if (isProfileImgUpdated) {
//                    Json.put(AppConstant.KEY_PROFILE_PHOTO, img_user_profile_base_64);
//                }
//                if (isCVUpadted) {
//                    Json.put(AppConstant.KEY_RESUME, fileBAse64Str);
//                }
                Log.v("json", Json.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return Json;
    }


    void updateUserDetails(JSONObject Json) {

        Log.v("JSONURL", Json.toString());
        ServiceHandler serviceHandler = new ServiceHandler(UserProfileActivity.this);
        serviceHandler.StringRequest(Request.Method.POST, Json.toString(), AppConstant.UPDATE_USER_DETAILS + userId, true, new ServiceHandler.VolleyCallback() {
            @Override
            public void onSuccess(String result) {

                Log.v("Response1", result);

                getProfileDetails();
                userDataBinding.savebutton.setVisibility(View.GONE);
                userDataBinding.edit.setVisibility(View.VISIBLE);
                userDataBinding.cancel.setVisibility(View.GONE);

                //Auto Scroll the Scroll view to top
                userDataBinding.scroll.post(new Runnable() {
                    public void run() {
                        userDataBinding.scroll.fullScroll(userDataBinding.scroll.FOCUS_UP);
                    }
                });
                //userDataBinding.mainLnr.setAlpha((float) 0.5);
                setdisable();

            }
        });


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
        Intent intent = new Intent(UserProfileActivity.this, ImagePickerActivity.class);
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
        Intent intent = new Intent(UserProfileActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));

        builder.setPositiveButton(getString(R.string.go_to_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                openSettings();
            }
        });

        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alert11 = builder.create();
        alert11.show();
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void getDocument() {
       /* Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/msword,application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // Only the system receives the ACTION_OPEN_DOCUMENT, so no need to test.
        startActivityForResult(intent, REQUEST_CODE_DOC);*/
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
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


//    public static String fileBase64Convert(String string) {
//
//        byte[] data;
//        String base64 = "";
//
//        try {
//
//            data=
//
//
//
////            File pdfFile = new File(filePath);
////            byte[] encoded = Files.readAllBytes(Paths.get(pdfFile.getAbsolutePath()));
////            Base64.Encoder enc = Base64.getEncoder();
////            byte[] strenc = enc.encode(encoded);
////            String encode = new String(strenc, "UTF-8");
////            Base64.Decoder dec = Base64.getDecoder();
////            byte[] strdec = dec.decode(encode);
////            OutputStream out = new FileOutputStream("/home/user/out.pdf");
////            out.write(strdec);
////            out.close();
////
////            data = string.getBytes("UTF-8");
//
//            base64 = Base64.encodeToString(data, Base64.DEFAULT);
//
//            Log.i("Base 64 ", base64);
//
//        } catch (UnsupportedEncodingException e) {
//
//            e.printStackTrace();
//
//        }
//
//        return base64;
//    }

//    private String decodeBase64(String coded) {
//        byte[] valueDecoded = new byte[0];
//        try {
//            valueDecoded = Base64.decode(coded.getBytes("UTF-8"), Base64.DEFAULT);
//        } catch (UnsupportedEncodingException e) {
//        }
//        return new String(valueDecoded);
//    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                     bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    Glide.with(this)
                            .load(bitmap)
                            .into(userDataBinding.profileHeader.userImg);
                    isProfileImgUpdated = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
//                Uri uri = data.getData();
//                String uriString = uri.toString();
//                File myFile = new File(uriString);
//                String path = myFile.getAbsolutePath();
//                String displayName = null;
//                Cursor cursor = null;
//                docFilePath = getFileNameByUri(this, uri);
//                cursor = getContentResolver().query(uri, null, null, null, null);
//                if (cursor != null && cursor.moveToFirst()) {
//                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
//                    userDataBinding.uploadCvTxt.setText(displayName);
//                }
//                isCVUpadted = true;

                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    this.uri = uri;
                    String uriString = uri.toString();
                    File myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();
                    String displayName = null;

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = this.getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                Log.d("nameeeee>>>>  ", displayName);
                                userDataBinding.uploadCvTxt.setText(displayName);
                                fileName = displayName;
                                //  uploadPDF(displayName,uri);
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                        userDataBinding.uploadCvTxt.setText(displayName);
                        fileName = displayName;
                        Log.d("nameeeee>>>>  ", displayName);
                    }
                }

            }
        }
    }


    public static String encodeTobase64(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] b = baos.toByteArray();
        imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log Encoded:", imageEncoded);
        return imageEncoded;

    }

    private String encodeFileToBase64Binary(byte[] allData) {
        String encoded = Base64.encodeToString(allData, Base64.NO_WRAP);
        return encoded;
    }

    public static byte[] loadFileAsBytesArray(String fileName) {
        try {
            File file = new File(fileName);
            int length = (int) file.length();
            BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
            byte[] bytes = new byte[length];
            reader.read(bytes, 0, length);
            reader.close();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.v("Device Id", android_id);

    }

    void enableOrDisableSaveBtn() {
        try {
            if (eduJobStr.equals("Y")) {
                userDataBinding.nameEditText.addTextChangedListener(watcher);
                userDataBinding.emailEditText.addTextChangedListener(watcher);
                userDataBinding.stateEditText.addTextChangedListener(watcher);
                userDataBinding.cityEditText.addTextChangedListener(watcher);
                userDataBinding.languageKnownEditText.addTextChangedListener(watcher);
                userDataBinding.designationEditText.addTextChangedListener(watcher);
                userDataBinding.dobKnownEditText.addTextChangedListener(watcher);
                userDataBinding.qualificationTypeEditText.addTextChangedListener(watcher);
                userDataBinding.experinaceEditText.addTextChangedListener(watcher);
                userDataBinding.experinaceYearsEditText.addTextChangedListener(watcher);
                userDataBinding.salaryEditText.addTextChangedListener(watcher);
                userDataBinding.centreNameEditText.addTextChangedListener(watcher);
                userDataBinding.projectNameEditText.addTextChangedListener(watcher);
                userDataBinding.batchNameEditText.addTextChangedListener(watcher);
                userDataBinding.courseNameEditText.addTextChangedListener(watcher);
            } else {
                userDataBinding.nameEditText.addTextChangedListener(watcher1);
                userDataBinding.emailEditText.addTextChangedListener(watcher1);
                userDataBinding.stateEditText.addTextChangedListener(watcher1);
                userDataBinding.cityEditText.addTextChangedListener(watcher1);
                userDataBinding.languageKnownEditText.addTextChangedListener(watcher1);
                userDataBinding.dobKnownEditText.addTextChangedListener(watcher1);
                userDataBinding.qualificationTypeEditText.addTextChangedListener(watcher1);
                userDataBinding.experinaceEditText.addTextChangedListener(watcher1);
                userDataBinding.experinaceYearsEditText.addTextChangedListener(watcher1);
                userDataBinding.designationEditText.addTextChangedListener(watcher1);
//                userDataBinding.rollEditText.addTextChangedListener(watcher1);
                userDataBinding.salaryEditText.addTextChangedListener(watcher1);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
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

            if (!userDataBinding.nameEditText.getText().toString().equals("")) {
                userDataBinding.profileHeader.nameVisitingCard.setText(userDataBinding.nameEditText.getText().toString());
                userDataBinding.profileHeader.nameVisitingCard.setVisibility(View.VISIBLE);
            } else {
                userDataBinding.profileHeader.nameVisitingCard.setText(userDataBinding.nameEditText.getText().toString());
                userDataBinding.profileHeader.nameVisitingCard.setVisibility(View.GONE);
            }

            if (!userDataBinding.emailEditText.getText().toString().equals("")) {
                userDataBinding.profileHeader.emailIdVisitingcard.setText(userDataBinding.emailEditText.getText().toString());
                userDataBinding.profileHeader.emailLayout.setVisibility(View.VISIBLE);
            } else {
                userDataBinding.profileHeader.emailIdVisitingcard.setText(userDataBinding.emailEditText.getText().toString());
                userDataBinding.profileHeader.emailLayout.setVisibility(View.GONE);
            }

            if (!userDataBinding.designationEditText.getText().toString().equals("")) {
                userDataBinding.profileHeader.designationVisitingcard.setText(userDataBinding.designationEditText.getText().toString());
                userDataBinding.profileHeader.designationLayout.setVisibility(View.VISIBLE);
            } else {
                userDataBinding.profileHeader.designationVisitingcard.setText(userDataBinding.designationEditText.getText().toString());
                userDataBinding.profileHeader.designationLayout.setVisibility(View.GONE);
            }

            if (!userDataBinding.cityEditText.getText().toString().equals("")) {
                userDataBinding.profileHeader.locationVisitingcard.setText(userDataBinding.cityEditText.getText().toString());
                userDataBinding.profileHeader.locationLayout.setVisibility(View.VISIBLE);
            } else {
                userDataBinding.profileHeader.locationVisitingcard.setText(userDataBinding.cityEditText.getText().toString());
                userDataBinding.profileHeader.locationLayout.setVisibility(View.GONE);
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

            if (!userDataBinding.nameEditText.getText().toString().equals("")) {
                userDataBinding.profileHeader.nameVisitingCard.setText(userDataBinding.nameEditText.getText().toString());
                userDataBinding.profileHeader.nameVisitingCard.setVisibility(View.VISIBLE);
            } else {
                userDataBinding.profileHeader.nameVisitingCard.setText(userDataBinding.nameEditText.getText().toString());
                userDataBinding.profileHeader.nameVisitingCard.setVisibility(View.GONE);
            }

            if (!userDataBinding.emailEditText.getText().toString().equals("")) {
                userDataBinding.profileHeader.emailIdVisitingcard.setText(userDataBinding.emailEditText.getText().toString());
                userDataBinding.profileHeader.emailLayout.setVisibility(View.VISIBLE);
            } else {
                userDataBinding.profileHeader.emailIdVisitingcard.setText(userDataBinding.emailEditText.getText().toString());
                userDataBinding.profileHeader.emailLayout.setVisibility(View.GONE);
            }

            if (!userDataBinding.designationEditText.getText().toString().equals("")) {
                userDataBinding.profileHeader.designationVisitingcard.setText(userDataBinding.designationEditText.getText().toString());
                userDataBinding.profileHeader.designationLayout.setVisibility(View.VISIBLE);
            } else {
                userDataBinding.profileHeader.designationVisitingcard.setText(userDataBinding.designationEditText.getText().toString());
                userDataBinding.profileHeader.designationLayout.setVisibility(View.GONE);
            }

            if (!userDataBinding.cityEditText.getText().toString().equals("")) {
                userDataBinding.profileHeader.locationVisitingcard.setText(userDataBinding.cityEditText.getText().toString());
                userDataBinding.profileHeader.locationLayout.setVisibility(View.VISIBLE);
            } else {
                userDataBinding.profileHeader.locationVisitingcard.setText(userDataBinding.cityEditText.getText().toString());
                userDataBinding.profileHeader.locationLayout.setVisibility(View.GONE);
            }


        }
    };

    public void createReferralLink() {
        String uid = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_REFERAL_CODE, "");
        String link = "https://mygame.example.com/?invitedby=" + uid;
        FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(link))
                .setDomainUriPrefix("https://rojgarkendra.page.link")
                .setAndroidParameters(
                        new DynamicLink.AndroidParameters.Builder("com.twocoms.rojgarkendra")
                                .setMinimumVersion(125)
                                .build())

                .buildShortDynamicLink()
                .addOnSuccessListener(new OnSuccessListener<ShortDynamicLink>() {
                    @Override
                    public void onSuccess(ShortDynamicLink shortDynamicLink) {
                        mInvitationUrl = shortDynamicLink.getShortLink();
                        Log.v("Referral LInk", mInvitationUrl.toString());
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_TEXT, mInvitationUrl.toString());
                        intent.setType("text/plain");
                        startActivity(intent);
                    }
                });
    }


    private void updateUserData(final Context context, final JSONObject jsonObject) {
        loadingDialog = new LoadingDialog(context);
        loadingDialog.show();
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, AppConstant.UPDATE_USER_DETAILS + userId, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                loadingDialog.dismiss();
                getProfileDetails();
                userDataBinding.savebutton.setVisibility(View.GONE);
                userDataBinding.edit.setVisibility(View.VISIBLE);
                userDataBinding.cancel.setVisibility(View.GONE);

                //Auto Scroll the Scroll view to top
                userDataBinding.scroll.post(new Runnable() {
                    public void run() {
                        userDataBinding.scroll.fullScroll(userDataBinding.scroll.FOCUS_UP);
                    }
                });
                //userDataBinding.mainLnr.setAlpha((float) 0.5);
                setdisable();
//               try {
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");

                        Log.e("Error Status", status);
                        Log.e("Error Message", message);

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message + " Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message + " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message + " Something is getting wrong";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Error", errorMessage);
                loadingDialog.dismiss();
                CommonMethod.showToast(errorMessage, UserProfileActivity.this);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Gson gson = new Gson();
                Type type = new TypeToken<Map<String, String>>() {}.getType();
                Map<String, String> myMap = gson.fromJson(jsonObject.toString(), type);
                return myMap;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String tokenMain = GlobalPreferenceManager.getStringForKey(context, AppConstant.KEY_TOKEN_MAIN, "");
                headers.put("Authorization", "Bearer " + tokenMain);
                return headers;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                if (bitmap != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream);
                    byte[] byteArray = stream.toByteArray();
                    params.put("profile_photo", new DataPart("profile_image.jpg", byteArray));

                }
                if (uri != null && !fileName.equals("")) {
                    byte [] data = getBytes();
                    if(data != null){
                        if (data != null) {
                            params.put("resume", new DataPart(fileName, data));
                        }
                    }
                }

                return params;
            }
        };
        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);
    }


    public byte[] getBytes() {
        try {
            InputStream iStream = getContentResolver().openInputStream(uri);
            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int len = 0;
            while ((len = iStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
            return byteBuffer.toByteArray();
        } catch (IOException ex){
            ex.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}