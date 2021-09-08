package com.twocoms.rojgarkendra.successstoryscreen.controler;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.global.model.AppConstant;
import com.twocoms.rojgarkendra.global.model.CommonMethod;
import com.twocoms.rojgarkendra.global.model.GlobalPreferenceManager;
import com.twocoms.rojgarkendra.global.model.ServiceHandler;
import com.twocoms.rojgarkendra.interviewscreen.view.AppliedInterviewAdapter;
import com.twocoms.rojgarkendra.successstoryscreen.model.TestimonialsModel;
import com.twocoms.rojgarkendra.successstoryscreen.view.TestimonialsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SuccessStoriesActivity extends AppCompatActivity {

    ImageView menuIcon, backIcon, homeIcon, profileIcon;
    TextView titleToolbar;
    LinearLayout titleLnr;
    RecyclerView recyclerView;
    public static int numberofentries = 0;
    public static int currentPages = 1;
    public static String nextPageUrl = "";
    public static int numberOfPagesFromServer = 0;
    List<TestimonialsModel> allTestimonials = new ArrayList<>();
    TestimonialsAdapter testimonialsAdapter;
    TextView noappliedJobText;
    String message;
    private SwipeRefreshLayout swipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_stories);
        initialization();
        setToolbarVisibility();
        onClick();
        getAllTestimonials();
        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(false);
                allTestimonials.clear();
                allTestimonials = new ArrayList<>();
                currentPages = 1;
                getAllTestimonials();
            }
        });

    }


    void initialization() {
        menuIcon = (ImageView) findViewById(R.id.menu);
        backIcon = (ImageView) findViewById(R.id.backbutton);
        homeIcon = (ImageView) findViewById(R.id.home_img);
        profileIcon = (ImageView) findViewById(R.id.user_profile_img);
        titleToolbar = (TextView) findViewById(R.id.title);
        titleLnr = (LinearLayout) findViewById(R.id.title_lnr);
        titleToolbar.setText(AppConstant.NAME_SUCCESS_STORIES);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_success_stories);
        noappliedJobText = (TextView) findViewById(R.id.notestimoinals);
    }

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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void getAllTestimonials() {
        ServiceHandler serviceHandler = new ServiceHandler(SuccessStoriesActivity.this);
        String url;
        if (currentPages == 1) {
            url = AppConstant.GET_ALL_TESTIMONIALS;
        } else {
            url = AppConstant.GET_ALL_TESTIMONIALS + "/" + currentPages;
        }

        serviceHandler.StringRequest(Request.Method.POST, "", url, true, new ServiceHandler.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                Log.v("Response", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    message = jsonObject.getString(AppConstant.KEY_JOB_DATA_MESSAGE);
                    if (jsonObject.getBoolean(AppConstant.KEY_JOB_DATA_SUCCESS)) {

                        JSONObject object = jsonObject.getJSONObject(AppConstant.KEY_JOB_DATA_OBJ_DATA);
                        JSONArray jsonArray = object.getJSONArray("records");
//                        numberofentries = object.getInt(AppConstant.KEY_JOB_DATA_NO_OF_ENTRIES);
//                        int perPageData = object.getInt(AppConstant.KEY_JOB_DATA_PER_PAGE);
//                        double numberofPages = ((double) numberofentries) / perPageData;
                        numberOfPagesFromServer = object.getInt(AppConstant.KEY_JOB_DATA_NO_OF_ENTRIES);
                        //  Log.e("numberOfPagesFromServer", "" + numberOfPagesFromServer);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            TestimonialsModel testimonialsModel = new TestimonialsModel();
                            JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                            testimonialsModel.setPostedBy(jsonObject1.getInt(AppConstant.KEY_POSTED_BY));
                            testimonialsModel.setPostedOn(jsonObject1.getString(AppConstant.KEY_POSTED_DATE_TIME));
                            testimonialsModel.setUserName(jsonObject1.getString(AppConstant.KEY_NAME));
                            testimonialsModel.setUserContact(jsonObject1.getString(AppConstant.KEY_CONTACT));
                            testimonialsModel.setUserEmail(jsonObject1.getString(AppConstant.KEY_EMAIL_ID));
                            testimonialsModel.setPostedMessage(jsonObject1.getString(AppConstant.KEY_JOB_DATA_MESSAGE));
                            if (jsonObject1.has(AppConstant.KEY_PROFILE_URL)) {
                                testimonialsModel.setProfileUrl(jsonObject1.getString(AppConstant.KEY_PROFILE_URL));
                            }

                            allTestimonials.add(testimonialsModel);

                        }

                        if (currentPages == 1) {
                            setAdapter();
                        } else {
                            if (testimonialsAdapter != null) {
                                testimonialsAdapter.setData(allTestimonials);
                                testimonialsAdapter.notifyDataSetChanged();
                            } else {
                                setAdapter();
                            }
                        }


                    } else {
                        CommonMethod.showToast(message, SuccessStoriesActivity.this);
                    }


                } catch (JSONException e) {
                    CommonMethod.showToast(AppConstant.SOMETHING_WENT_WRONG, SuccessStoriesActivity.this);
                    e.printStackTrace();
                }


            }
        });

    }


    void setAdapter() {
        if (allTestimonials.size() == 0) {
            noappliedJobText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            testimonialsAdapter = new TestimonialsAdapter(SuccessStoriesActivity.this, allTestimonials);
            recyclerView.setLayoutManager(new LinearLayoutManager(SuccessStoriesActivity.this));
            recyclerView.setAdapter(testimonialsAdapter);
            testimonialsAdapter.notifyDataSetChanged();
            noappliedJobText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

    }

}