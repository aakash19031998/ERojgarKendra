package com.twocoms.rojgarkendra.myprofile.controler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.twocoms.rojgarkendra.R;

import com.twocoms.rojgarkendra.databinding.ActivityUserProfileBinding;
import com.twocoms.rojgarkendra.global.model.AppConstant;
import com.twocoms.rojgarkendra.global.model.CommonMethod;
import com.twocoms.rojgarkendra.global.model.GlobalPreferenceManager;
import com.twocoms.rojgarkendra.global.model.ServiceHandler;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDataBinding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        View view = userDataBinding.getRoot();
        setContentView(view);
        if (CommonMethod.isOnline(UserProfileActivity.this)) {
            initialization();
        }else {
            showDialog("No Internet Connection", UserProfileActivity.this);
            //CommonMethod.showToast("No Internet Connection",UserProfileActivity.this);
        }
    }

    void initialization(){
        userId = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this,AppConstant.KEY_USER_ID,"");
        eduJobStr = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this,AppConstant.KEY_IS_EDURP,"");
        listStates = new ArrayList<>();
        getProfileDetails();
        //getStates();
        setVisibilty();
        onCick();


    }

    void onCick(){
        userDataBinding.profileHeadre.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDataBinding.profileHeadre.nameNoLnr.setVisibility(View.GONE);
                userDataBinding.savebutton.setVisibility(View.VISIBLE);
            }
        });
    }


    void getProfileDetails() {
        ServiceHandler serviceHandler = new ServiceHandler(UserProfileActivity.this);
        serviceHandler.StringRequest(Request.Method.GET, "", AppConstant.GET_USER_DETAILS+userId, true, new ServiceHandler.VolleyCallback() {
            @Override
            public void onSuccess(String result) {

                Log.v("Response", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getBoolean("success")){
                        JSONObject object = jsonObject.getJSONObject("data");

                        responseMsg = jsonObject.getString("message");


                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_USER_ID,object.getString(AppConstant.KEY_USER_ID));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_NAME,object.getString(AppConstant.KEY_NAME));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_EMAIL_ID,object.getString(AppConstant.KEY_EMAIL_ID));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_CONTACT,object.getString(AppConstant.KEY_CONTACT));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_CONTACT_VERIFIED,object.getString(AppConstant.KEY_CONTACT_VERIFIED));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.STATE_ID,object.getString(AppConstant.STATE_ID));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_CITY,object.getString(AppConstant.KEY_CITY));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_DOB,object.getString(AppConstant.KEY_DOB));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_GENDER,object.getString(AppConstant.KEY_GENDER));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_QUALIFICATION_TYPE,object.getString(AppConstant.KEY_QUALIFICATION_TYPE));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_EXPERIANCE_YEARS,object.getString(AppConstant.KEY_EXPERIANCE_YEARS));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_EXPERIANCE_MONTH,object.getString(AppConstant.KEY_EXPERIANCE_MONTH));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_SALARY,object.getString(AppConstant.KEY_SALARY));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_REFERAL_CODE,object.getString(AppConstant.KEY_REFERAL_CODE));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_ROLE,object.getString(AppConstant.KEY_ROLE));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_IS_EDURP,object.getString(AppConstant.KEY_IS_EDURP));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_WALLET_AMOUNT,object.getString(AppConstant.KEY_WALLET_AMOUNT));
                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_LANGUAGE_KNOWN,object.getString(AppConstant.KEY_LANGUAGE_KNOWN));
//                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_PROFILE_URL,object.getString(AppConstant.KEY_PROFILE_URL));
//                        GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_RESUME_URL,object.getString(AppConstant.KEY_RESUME_URL));
                        if (object.getString("eduErp").equals("Y")){
                            GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_COURSE_ID,object.getString(AppConstant.KEY_COURSE_ID));
                            GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_CENTRE_ID,object.getString(AppConstant.KEY_CENTRE_ID));
                            GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_PROJECT_ID,object.getString(AppConstant.KEY_PROJECT_ID));
                            GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_BATCH_ID,object.getString(AppConstant.KEY_BATCH_ID));
//                            GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_COURSE_NAME,object.getString(AppConstant.KEY_COURSE_NAME));
//                            GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_CENTRE_NAME,object.getString(AppConstant.KEY_CENTRE_NAME));
//                            GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_PROJECT_NAME,object.getString(AppConstant.KEY_PROJECT_NAME));
//                            GlobalPreferenceManager.saveStringForKey(UserProfileActivity.this,AppConstant.KEY_BATCH_NAME,object.getString(AppConstant.KEY_BATCH_NAME));
                        }

                        setDataUserProfile();

                    }else {
                        CommonMethod.showToast(responseMsg,UserProfileActivity.this);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    public void showDialog(String msg, final Context context){
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
                            String stateName = object.getString("StateName");
                            int stateCode = object.getInt("StCode");
                            if(!stateName.equals("empty")) {
                                listStates.add(stateName);
                                states[i] = stateName;
                                statesCode[i] = String.valueOf(stateCode);
                            }
                        }

                       /* ArrayAdapter adapter1 = new ArrayAdapter<String>(UserProfileActivity.this, R.layout.drop_down_item, states);
                        userDataBinding.stateEditText.setAdapter(adapter1);
                        userDataBinding.stateEditText.showDropDown();*/

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    void setDataUserProfile(){
        String name = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_NAME, "");
        String email = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_EMAIL_ID, "");
        String stateName = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_STATE_NAME, "");
        String stateId = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.STATE_ID, "");
        String city = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_CITY, "");
        String languageKnown = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_LANGUAGE_KNOWN, "");
        String gender = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_GENDER, "");
        String dob = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_DOB, "");
        String phoneNumber = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_CONTACT, "");
        String qualificationType = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_QUALIFICATION_TYPE, "");
        String experiance = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_EXPERIANCE_YEARS, "");
        String role = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_ROLE, "");
        String salary = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_SALARY, "");
        String projectName = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_PROJECT_NAME, "");
        String courseName = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_COURSE_NAME, "");
        String batchName = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_BATCH_NAME, "");
        String centreName = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_CENTRE_NAME, "");
        projectId = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_PROJECT_ID, "");
        courseId = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_COURSE_ID, "");
        batchId = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_BATCH_ID, "");
        centreId = GlobalPreferenceManager.getStringForKey(UserProfileActivity.this, AppConstant.KEY_CENTRE_ID, "");
        userDataBinding.nameEditText.setText(name);
        userDataBinding.profileHeadre.nameText.setText(name);
        userDataBinding.emailEditText.setText(email);
        if (!stateName.equals("") && !stateName.equals("null")) {
            userDataBinding.stateEditText.setText(stateName);
        }
        if (!stateName.equals("") && !stateName.equals("null")) {
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
            userDataBinding.profileHeadre.noText.setText(phoneNumber);
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
            } else {
                userDataBinding.centreNameEditTextLnr.setVisibility(View.GONE);
                userDataBinding.projectNameEditTextLnr.setVisibility(View.GONE);
                userDataBinding.batchNameEditTextLnr.setVisibility(View.GONE);
                userDataBinding.courseNameEditTextLnr.setVisibility(View.GONE);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }



}