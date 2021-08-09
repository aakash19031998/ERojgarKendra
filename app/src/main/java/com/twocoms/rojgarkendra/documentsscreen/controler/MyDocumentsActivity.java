package com.twocoms.rojgarkendra.documentsscreen.controler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonArray;
import com.twocoms.rojgarkendra.R;
//import com.twocoms.rojgarkendra.documentsscreen.view.TabPagerAdapter;
import com.twocoms.rojgarkendra.documentsscreen.model.DocumentsModel;
import com.twocoms.rojgarkendra.documentsscreen.view.DocumentListAdapter;
import com.twocoms.rojgarkendra.global.model.AppConstant;
import com.twocoms.rojgarkendra.global.model.CommonMethod;
import com.twocoms.rojgarkendra.global.model.GlobalPreferenceManager;
import com.twocoms.rojgarkendra.global.model.ServiceHandler;
import com.twocoms.rojgarkendra.interviewscreen.model.AppliedAndUpcommingModel;
import com.twocoms.rojgarkendra.jobboardscreen.controler.FrgAllJobs;
import com.twocoms.rojgarkendra.jobboardscreen.view.AllJobsAdapter;
import com.twocoms.rojgarkendra.registrationscreen.controler.RegisterUserDataActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyDocumentsActivity extends AppCompatActivity {

    ImageView menuIcon, backIcon, homeIcon, profileIcon;
    TextView titleToolbar;
    LinearLayout titleLnr;
    TabLayout tabLayout;
    //    ViewPager viewPager;
    RecyclerView docRecyclerView;
    FloatingActionButton addButton;
    ArrayList<DocumentsModel> documentsModule;
    DocumentListAdapter documentListAdapter;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_my_doc);
        initialization();
        setToolbarVisibility();
        onClick();
        getAllDocumentsToUpload();
        setAdapter();
    }

    void initialization() {
        menuIcon = (ImageView) findViewById(R.id.menu);
        backIcon = (ImageView) findViewById(R.id.backbutton);
        homeIcon = (ImageView) findViewById(R.id.home_img);
        profileIcon = (ImageView) findViewById(R.id.user_profile_img);
        titleToolbar = (TextView) findViewById(R.id.title);
        titleLnr = (LinearLayout) findViewById(R.id.title_lnr);
        titleToolbar.setText(AppConstant.NAME_MY_DOCUMENTS);
        docRecyclerView = (RecyclerView) findViewById(R.id.recycler_doc);
        addButton = (FloatingActionButton) findViewById(R.id.add_doc);
    }
//
//    void setTab(){
//        tabLayout.addTab(tabLayout.newTab().setText("Pre Onbooarding Documents"));
//        tabLayout.addTab(tabLayout.newTab().setText("Post Onbooarding Documents"));
//    }

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

    void setToolbarVisibility() {
        menuIcon.setVisibility(View.GONE);
        backIcon.setVisibility(View.VISIBLE);
        homeIcon.setVisibility(View.VISIBLE);
        profileIcon.setVisibility(View.GONE);
    }

    void onClick() {
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
                Intent intent = new Intent(MyDocumentsActivity.this, AddDocActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void getAllDocumentsToUpload() {
        documentsModule = new ArrayList<>();
        JSONObject json = new JSONObject();
        String user_id = GlobalPreferenceManager.getStringForKey(MyDocumentsActivity.this, AppConstant.KEY_USER_ID, "");

        String url = AppConstant.GET_ALL_DOCUMENTS_TO_UPLOADED;
        try {
            json.put(AppConstant.KEY_APPLIED_AND_UPCOMING_INTEVIEW_USER_ID, user_id);
            json.put(AppConstant.KEY_APPLIED_AND_UPCOMING_INTEVIEW_STUDENT_STATUS, "offer_accepected");

            Log.v("Request", json.toString());
            ServiceHandler serviceHandler = new ServiceHandler(MyDocumentsActivity.this);
            serviceHandler.StringRequest(Request.Method.POST, json.toString(), url, true, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {

                    Log.v("Response", result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String message = jsonObject.getString(AppConstant.KEY_JOB_DATA_MESSAGE);
                        if (jsonObject.getBoolean(AppConstant.KEY_JOB_DATA_SUCCESS)) {
                            JSONArray jsonArray1 = jsonObject.getJSONArray(AppConstant.KEY_JOB_DATA_OBJ_DATA);
                            if(jsonArray1.length() == 0){
                                CommonMethod.showToast("No documents to upload for now.", MyDocumentsActivity.this);
                                return;
                            }
                            JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
                            JSONArray jsonArray = jsonObject1.getJSONArray("months");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                DocumentsModel documentsModel = new DocumentsModel();
                                JSONObject documentJsonObject = (JSONObject) jsonArray.get(i);

                                if (documentJsonObject.has("id")) {
                                    documentsModel.id = documentJsonObject.getString("id");
                                }
                                if (documentJsonObject.has("candidate_id")) {
                                    documentsModel.candidateId = documentJsonObject.getString("candidate_id");
                                }
                                if (documentJsonObject.has("payslip_month")) {
                                    documentsModel.paySlipMonth = documentJsonObject.getString("payslip_month");
                                }
                                if (documentJsonObject.has("payslip")) {
                                    documentsModel.paySlipUrl = documentJsonObject.getString("payslip");
                                }
                                if (documentJsonObject.has("upload_date")) {
                                    documentsModel.uploadDate = documentJsonObject.getString("upload_date");
                                }
                                if (documentJsonObject.has("approved_status")) {
                                    documentsModel.approvedStatus = documentJsonObject.getString("approved_status");
                                }
                                if (documentJsonObject.has("verify_by")) {
                                    documentsModel.approvedStatus = documentJsonObject.getString("verify_by");
                                }
                                if (documentJsonObject.has("verify_date")) {
                                    documentsModel.verifiedDate = documentJsonObject.getString("verify_date");
                                }
                                if (documentJsonObject.has("payslip_year")) {
                                    documentsModel.paySlipYear = documentJsonObject.getString("payslip_year");
                                }
                                documentsModule.add(documentsModel);

                                setAdapter();

                            }
                        } else {
                            CommonMethod.showToast(message, MyDocumentsActivity.this);
                        }

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


    void setAdapter() {
        if (documentsModule.size() == 0) {
            docRecyclerView.setVisibility(View.GONE);
        } else {
            if (documentsModule.size() == 7) {
                documentsModule.remove(0);
            }
            documentListAdapter = new DocumentListAdapter(MyDocumentsActivity.this, documentsModule);
            docRecyclerView.setLayoutManager(new LinearLayoutManager(MyDocumentsActivity.this));
            docRecyclerView.setAdapter(documentListAdapter);
            documentListAdapter.notifyDataSetChanged();
            docRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    public void callAddDocActivity(String month, String year) {
        Intent intent = new Intent(MyDocumentsActivity.this, AddDocActivity.class);
        intent.putExtra("payMonth", month);
        intent.putExtra("payYear", year);
        startActivityForResult(intent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String documentuplodedSucess = data.getStringExtra("documentuplodedSucess");
            if (!documentuplodedSucess.equals("")) {
                getAllDocumentsToUpload();
            }
        }
    }


    public void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    public void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MyDocumentsActivity.this);
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

}