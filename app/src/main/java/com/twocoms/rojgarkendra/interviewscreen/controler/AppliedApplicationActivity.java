package com.twocoms.rojgarkendra.interviewscreen.controler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.global.model.AppConstant;
import com.twocoms.rojgarkendra.global.model.CommonMethod;
import com.twocoms.rojgarkendra.global.model.GlobalPreferenceManager;
import com.twocoms.rojgarkendra.global.model.ServiceHandler;
import com.twocoms.rojgarkendra.interviewscreen.model.AppliedAndUpcommingModel;
import com.twocoms.rojgarkendra.interviewscreen.view.AppliedInterviewAdapter;
import com.twocoms.rojgarkendra.jobboardscreen.controler.FrgAllJobs;
import com.twocoms.rojgarkendra.jobboardscreen.model.ModelHotJobs;
import com.twocoms.rojgarkendra.jobboardscreen.view.AllJobsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AppliedApplicationActivity extends AppCompatActivity {

    ImageView menuIcon,backIcon,homeIcon,profileIcon;
    TextView titleToolbar;
    LinearLayout titleLnr;
    RecyclerView recyclerView;
    public static int numberofentries = 0;
    public static int currentPages = 1;
    public static String nextPageUrl = "";
    public static int numberOfPagesFromServer = 0;
    List<AppliedAndUpcommingModel> appliedAndUpcommingModels = new ArrayList<>();
    AppliedInterviewAdapter appliedInterviewAdapter;
    TextView noappliedJobText;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_application);
        initialization();
        setToolbarVisibility();
        onClick();
        getAppliedInterview();
    }

    void initialization(){
        menuIcon = (ImageView)findViewById(R.id.menu);
        backIcon = (ImageView)findViewById(R.id.backbutton);
        homeIcon = (ImageView)findViewById(R.id.home_img);
        profileIcon = (ImageView)findViewById(R.id.user_profile_img);
        titleToolbar =(TextView)findViewById(R.id.title);
        titleLnr =(LinearLayout)findViewById(R.id.title_lnr);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_applied);
        noappliedJobText = (TextView)findViewById(R.id.noAppliedJob);
        titleToolbar.setText(AppConstant.NAME_ALL_APPLIED_APPLICATION);
    }

    void setToolbarVisibility(){
        menuIcon.setVisibility(View.GONE);
        backIcon.setVisibility(View.VISIBLE);
        homeIcon.setVisibility(View.VISIBLE);
        profileIcon.setVisibility(View.GONE);
    }

    void onClick(){
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        titleLnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void getAppliedInterview() {
        JSONObject json = new JSONObject();

        String user_id = GlobalPreferenceManager.getStringForKey(AppliedApplicationActivity.this,AppConstant.KEY_USER_ID,"");

        try {
            json.put(AppConstant.KEY_APPLIED_AND_UPCOMING_INTEVIEW_USER_ID,user_id);
            json.put(AppConstant.KEY_APPLIED_AND_UPCOMING_INTEVIEW_STUDENT_STATUS,"applied");
            json.put(AppConstant.KEY_PAGE,currentPages);
            Log.v("Request", json.toString());

        ServiceHandler serviceHandler = new ServiceHandler(AppliedApplicationActivity.this);
        serviceHandler.StringRequest(Request.Method.POST, json.toString(), AppConstant.GET_APPLIED_AND_UPCOMING_INTERVIEW, true, new ServiceHandler.VolleyCallback() {
            @Override
            public void onSuccess(String result) {

                Log.v("Response", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    message = jsonObject.getString(AppConstant.KEY_JOB_DATA_MESSAGE);
                    if (jsonObject.getBoolean(AppConstant.KEY_JOB_DATA_SUCCESS)) {

                        JSONObject object = jsonObject.getJSONObject(AppConstant.KEY_JOB_DATA_OBJ_DATA);
                        JSONArray jsonArray = object.getJSONArray("records");

                        numberofentries = object.getInt(AppConstant.KEY_JOB_DATA_NO_OF_ENTRIES);
                        int perPageData = object.getInt(AppConstant.KEY_JOB_DATA_PER_PAGE);
                        double numberofPages = ((double) numberofentries) / perPageData;
                        numberOfPagesFromServer = Integer.parseInt(CommonMethod.roundNumbertoNextPossibleValue(numberofPages + ""));
                        Log.e("numberOfPagesFromServer", "" + numberOfPagesFromServer);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            AppliedAndUpcommingModel appliedAndUpcommingModel = new AppliedAndUpcommingModel();
                            JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                            appliedAndUpcommingModel.setAppliedId(jsonObject1.getInt(AppConstant.KEY_APPLIED_ID));
                            appliedAndUpcommingModel.setScheduledDate(jsonObject1.getString(AppConstant.KEY_APPLIED_SCHEDULED_DATE));
                            appliedAndUpcommingModel.setInterviewDate(jsonObject1.getString(AppConstant.KEY_APPLIED_INTERVIEW_DATE));
                            appliedAndUpcommingModel.setStudentStatus(jsonObject1.getString(AppConstant.KEY_APPLIED_STUDENT_STATUS));
                            appliedAndUpcommingModel.setClientRemark(jsonObject1.getString(AppConstant.KEY_APPLIED_CLIENT_REMARKS));
                            appliedAndUpcommingModel.setStudentRemark(jsonObject1.getString(AppConstant.KEY_APPLIED_STUDENT_REMARKS));
                            appliedAndUpcommingModel.setDateOFJoining(jsonObject1.getString(AppConstant.KEY_APPLIED_DATE_OF_JOINING));
                            appliedAndUpcommingModel.setClientName(jsonObject1.getString(AppConstant.KEY_APPLIED_CLIENT_NAME));
                            appliedAndUpcommingModel.setAppliedDate(jsonObject1.getString(AppConstant.KEY_APPLIED_DATE));
                            appliedAndUpcommingModel.setJobType(jsonObject1.getString(AppConstant.KEY_JOB_DATA_JOB_TYPE));
                            appliedAndUpcommingModel.setSalary(jsonObject1.getString(AppConstant.KEY_JOB_DATA_SALARY));
                            appliedAndUpcommingModel.setLocationOfWork(jsonObject1.getString(AppConstant.KEY_JOB_DATA_WORK_LOCATION));
                            appliedAndUpcommingModel.setNumberOfOpenPositions(jsonObject1.getString(AppConstant.KEY_JOB_DATA_NO_OPEN_POSITION));

                            appliedAndUpcommingModels.add(appliedAndUpcommingModel);

                        }

                        if (currentPages == 1) {
                            setAdapter();
                        } else {
                            if (appliedInterviewAdapter != null) {
                                appliedInterviewAdapter.setData(appliedAndUpcommingModels);
                                appliedInterviewAdapter.notifyDataSetChanged();
                            } else {
                                setAdapter();
                            }
                        }


                    } else {
                        CommonMethod.showToast(message, AppliedApplicationActivity.this);
                    }


                } catch (JSONException e) {
                    CommonMethod.showToast(AppConstant.SOMETHING_WENT_WRONG, AppliedApplicationActivity.this);
                    e.printStackTrace();
                }


            }
        });

        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }



    void setAdapter() {
        if (appliedAndUpcommingModels.size() == 0) {
            noappliedJobText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            appliedInterviewAdapter = new AppliedInterviewAdapter(AppliedApplicationActivity.this, appliedAndUpcommingModels);
            recyclerView.setLayoutManager(new LinearLayoutManager(AppliedApplicationActivity.this));
            recyclerView.setAdapter(appliedInterviewAdapter);
            appliedInterviewAdapter.notifyDataSetChanged();
            noappliedJobText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

    }


}