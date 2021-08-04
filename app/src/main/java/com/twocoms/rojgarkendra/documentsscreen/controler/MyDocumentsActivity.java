package com.twocoms.rojgarkendra.documentsscreen.controler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.twocoms.rojgarkendra.R;
//import com.twocoms.rojgarkendra.documentsscreen.view.TabPagerAdapter;
import com.twocoms.rojgarkendra.global.model.AppConstant;
import com.twocoms.rojgarkendra.global.model.CommonMethod;
import com.twocoms.rojgarkendra.global.model.GlobalPreferenceManager;
import com.twocoms.rojgarkendra.global.model.ServiceHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class MyDocumentsActivity extends AppCompatActivity {

    ImageView menuIcon,backIcon,homeIcon,profileIcon;
    TextView titleToolbar;
    LinearLayout titleLnr;
    TabLayout tabLayout;
    ViewPager viewPager;
    RecyclerView docRecyclerView;
    FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_my_doc);
        initialization();
        setToolbarVisibility();
        onClick();
        getAllDocumentsToUpload();
    }

    void initialization(){
        menuIcon = (ImageView)findViewById(R.id.menu);
        backIcon = (ImageView)findViewById(R.id.backbutton);
        homeIcon = (ImageView)findViewById(R.id.home_img);
        profileIcon = (ImageView)findViewById(R.id.user_profile_img);
        titleToolbar =(TextView)findViewById(R.id.title);
        titleLnr =(LinearLayout)findViewById(R.id.title_lnr);
        titleToolbar.setText(AppConstant.NAME_MY_DOCUMENTS);
        docRecyclerView = (RecyclerView)findViewById(R.id.recycler_doc);
        addButton = (FloatingActionButton) findViewById(R.id.add_doc);

       /* tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.tab_pager);
        setTab();
        tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabPagerAdapter);
        //tabLayout.setOnTabSelectedListener(this);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/


    }

    void setTab(){
        tabLayout.addTab(tabLayout.newTab().setText("Pre Onbooarding Documents"));
        tabLayout.addTab(tabLayout.newTab().setText("Post Onbooarding Documents"));
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

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyDocumentsActivity.this,AddDocActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    public void getAllDocumentsToUpload() {
        JSONObject json = new JSONObject();
        String user_id = GlobalPreferenceManager.getStringForKey(MyDocumentsActivity.this, AppConstant.KEY_USER_ID, "");
        String url;
     //   if (currentPages == 1) {
         //   appliedAndUpcommingModels = new ArrayList<>();
            url = AppConstant.GET_ALL_DOCUMENTS_TO_UPLOADED;
//        } else {
//            url = AppConstant.GET_APPLIED_AND_UPCOMING_INTERVIEW + "/" + currentPages;
//        }
        try {
            json.put(AppConstant.KEY_APPLIED_AND_UPCOMING_INTEVIEW_USER_ID, user_id);
          //  json.put(AppConstant.KEY_APPLIED_AND_UPCOMING_INTEVIEW_STUDENT_STATUS, "offer_proposed");
//            json.put(AppConstant.KEY_PAGE, currentPages);
            Log.v("Request", json.toString());
            ServiceHandler serviceHandler = new ServiceHandler(MyDocumentsActivity.this);
            serviceHandler.StringRequest(Request.Method.POST, json.toString(), url, true, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {

                    Log.v("Response", result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
//                        message = jsonObject.getString(AppConstant.KEY_JOB_DATA_MESSAGE);
//                        if (jsonObject.getBoolean(AppConstant.KEY_JOB_DATA_SUCCESS)) {
//
//                            JSONObject object = jsonObject.getJSONObject(AppConstant.KEY_JOB_DATA_OBJ_DATA);
//                            JSONArray jsonArray = object.getJSONArray("records");
////                            numberofentries = object.getInt(AppConstant.KEY_JOB_DATA_NO_OF_ENTRIES);
////                            int perPageData = object.getInt(AppConstant.KEY_JOB_DATA_PER_PAGE);
////                            double numberofPages = ((double) numberofentries) / perPageData;
//                            numberOfPagesFromServer = object.getInt(AppConstant.KEY_JOB_DATA_NO_OF_ENTRIES);
//                            Log.e("numberOfPagesFromServer", "" + numberOfPagesFromServer);
//
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                AppliedAndUpcommingModel appliedAndUpcommingModel = new AppliedAndUpcommingModel();
//                                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
//                                appliedAndUpcommingModel.setAppliedId(jsonObject1.getInt(AppConstant.KEY_APPLIED_ID));
//                                appliedAndUpcommingModel.setScheduledDate(jsonObject1.getString(AppConstant.KEY_APPLIED_SCHEDULED_DATE));
//                                appliedAndUpcommingModel.setInterviewDate(jsonObject1.getString(AppConstant.KEY_APPLIED_INTERVIEW_DATE));
//                                appliedAndUpcommingModel.setStudentStatus(jsonObject1.getString(AppConstant.KEY_APPLIED_STUDENT_STATUS));
//                                appliedAndUpcommingModel.setClientRemark(jsonObject1.getString(AppConstant.KEY_APPLIED_CLIENT_REMARKS));
//                                appliedAndUpcommingModel.setStudentRemark(jsonObject1.getString(AppConstant.KEY_APPLIED_STUDENT_REMARKS));
//                                appliedAndUpcommingModel.setDateOFJoining(jsonObject1.getString(AppConstant.KEY_APPLIED_DATE_OF_JOINING));
//                                appliedAndUpcommingModel.setClientName(jsonObject1.getString(AppConstant.KEY_APPLIED_CLIENT_NAME));
//                                appliedAndUpcommingModel.setAppliedDate(jsonObject1.getString(AppConstant.KEY_APPLIED_DATE));
//                                appliedAndUpcommingModel.setJobType(jsonObject1.getString(AppConstant.KEY_JOB_DATA_JOB_TYPE));
//                                appliedAndUpcommingModel.setSalary(jsonObject1.getString(AppConstant.KEY_JOB_DATA_SALARY));
//                                appliedAndUpcommingModel.setVacancyTitle(jsonObject1.getString(AppConstant.KEY_VACANCY_DETAIL_VACANCY_TITLE));
//                                appliedAndUpcommingModel.setLocationOfWork(jsonObject1.getString(AppConstant.KEY_JOB_DATA_WORK_LOCATION));
//                                appliedAndUpcommingModel.setNumberOfOpenPositions(jsonObject1.getString(AppConstant.KEY_JOB_DATA_NO_OPEN_POSITION));
//
//                                appliedAndUpcommingModels.add(appliedAndUpcommingModel);
//
//                            }
//
//                            if (currentPages == 1) {
//                                setAdapter();
//                            } else {
//                                if (appliedInterviewAdapter != null) {
//                                    appliedInterviewAdapter.setData(appliedAndUpcommingModels);
//                                    appliedInterviewAdapter.notifyDataSetChanged();
//                                } else {
//                                    setAdapter();
//                                }
//                            }
//
//
//                        } else {
//                            CommonMethod.showToast(message, JobInHandActivity.this);
//                        }


                    } catch (JSONException e) {
                        CommonMethod.showToast(AppConstant.SOMETHING_WENT_WRONG, MyDocumentsActivity.this);
                        e.printStackTrace();
                    }


                }
            });

        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }

}