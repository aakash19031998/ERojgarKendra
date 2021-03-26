package com.twocoms.rojgarkendra.jobboardscreen.controler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.dashboardscreen.controler.DashboardActivity;
import com.twocoms.rojgarkendra.global.model.AppConstant;
import com.twocoms.rojgarkendra.global.model.BottomSheetDialog;
import com.twocoms.rojgarkendra.global.model.CommonMethod;
import com.twocoms.rojgarkendra.global.model.GlobalPreferenceManager;
import com.twocoms.rojgarkendra.global.model.ServiceHandler;
import com.twocoms.rojgarkendra.jobboardscreen.model.ModelHotJobs;
import com.twocoms.rojgarkendra.jobboardscreen.view.AllJobsAdapter;
import com.twocoms.rojgarkendra.jobboardscreen.view.HotJobsAdapter;
import com.twocoms.rojgarkendra.myprofile.controler.UserProfileActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FrgHotJob extends Fragment {

    RecyclerView recyclerView;
    HotJobsAdapter hotJobsAdapter;
    ArrayList<ModelHotJobs> modelHotJobs = new ArrayList<>();
    String message;
    public int numberofentries = 0;
    public int currentPages = 1;
    public String nextPageUrl = "";
    public int numberOfPagesFromServer = 0;
    RelativeLayout filterBtn;
    TextView noVacancyText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frg_hot_job, container, false);
        CommonMethod.clearAllFilterDataHotJobs(getActivity());
        initialization(rootView);
        onclick();
        getHotJobs();
        return rootView;
    }

    void initialization(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyler_hot_job);
        filterBtn = (RelativeLayout) view.findViewById(R.id.filter_layout);
        noVacancyText = view.findViewById(R.id.novacancytext);

    }

    void onclick() {
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheet = BottomSheetDialog.newInstance(AppConstant.COMING_FROM_HOT_JOBS);
                bottomSheet.setListener(new BottomSheetDialog.applyButtonListener() {
                    @Override
                    public void applyButtonPressed() {
                        Log.e("applyButtonPressed", "true");
                        modelHotJobs.clear();
                        modelHotJobs = new ArrayList<>();
                        numberofentries = 0;
                        currentPages = 1;
                        nextPageUrl = "";
                        numberOfPagesFromServer = 0;
                        getHotJobs();
                    }
                });
                bottomSheet.show(getActivity().getSupportFragmentManager(), "ModalBottomSheet");
            }
        });
    }

    public void getHotJobs() {

//
        String url = "";
        url = AppConstant.GET_HOT_JOBS;
        String jSonRequest = getPostParameter();
        Log.v("Request", jSonRequest);
        ServiceHandler serviceHandler = new ServiceHandler(getActivity());
        serviceHandler.StringRequest(Request.Method.POST, jSonRequest, url, true, new ServiceHandler.VolleyCallback() {
            @Override
            public void onSuccess(String result) {

                Log.v("Response", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    message = jsonObject.getString(AppConstant.KEY_JOB_DATA_MESSAGE);
                    if (jsonObject.getBoolean(AppConstant.KEY_JOB_DATA_SUCCESS)) {
                        JSONObject object = jsonObject.getJSONObject(AppConstant.KEY_JOB_DATA_OBJ_DATA);
                        JSONArray jsonArray = object.getJSONArray(AppConstant.KEY_JOB_DATA_ARRAY_DATA);
                        numberofentries = object.getInt(AppConstant.KEY_JOB_DATA_NO_OF_ENTRIES);
                        int perPageData = object.getInt(AppConstant.KEY_JOB_DATA_PER_PAGE);
                        double numberofPages = ((double) numberofentries) / perPageData;
                        numberOfPagesFromServer = Integer.parseInt(CommonMethod.roundNumbertoNextPossibleValue(numberofPages + ""));
                        Log.e("numberOfPagesFromServer", "" + numberOfPagesFromServer);
                        nextPageUrl = object.getString(AppConstant.KEY_JOB_DATA_NEXT_PAGE_URL);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            ModelHotJobs modelHotJob1 = new ModelHotJobs();
                            JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                            modelHotJob1.setId(jsonObject1.getInt(AppConstant.KEY_JOB_DATA_ID));
                            modelHotJob1.setSalary(jsonObject1.getString(AppConstant.KEY_JOB_DATA_SALARY));
                            modelHotJob1.setJobTypes(jsonObject1.getString(AppConstant.KEY_JOB_DATA_JOB_TYPE));
                            modelHotJob1.setClientName(jsonObject1.getString(AppConstant.KEY_JOB_DATA_CLIENT_NAME));
                            modelHotJob1.setNumberOpenings(jsonObject1.getString(AppConstant.KEY_JOB_DATA_NO_OPEN_POSITION));
                            modelHotJob1.setLocation(jsonObject1.getString(AppConstant.KEY_JOB_DATA_WORK_LOCATION));
                            modelHotJob1.setDates(jsonObject1.getString(AppConstant.KEY_JOB_DATA_CREATED_ON));
                            modelHotJobs.add(modelHotJob1);
                        }


                        if (currentPages == 1) {
                            setAdapter();
                        } else {
                            if (hotJobsAdapter != null) {
                                hotJobsAdapter.setData(modelHotJobs);
                                hotJobsAdapter.notifyDataSetChanged();
                            } else {
                                setAdapter();
                            }
                        }


                    } else {
                        CommonMethod.showToast(message, getActivity());
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }


        void setAdapter() {
            if (modelHotJobs.size() == 0) {
                noVacancyText.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                hotJobsAdapter = new HotJobsAdapter(getActivity(), modelHotJobs, FrgHotJob.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(hotJobsAdapter);
                hotJobsAdapter.notifyDataSetChanged();
                noVacancyText.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

        }


    String getPostParameter() {
        String gender = GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_GENDER_HOT_JOBS, "");
        String city = GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_CITY_HOT_JOBS, "");
        String skills = GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_SKILLS_HOT_JOBS, "");
        String qualificationType = GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_QUALIFICATION_TYPE_HOT_JOBS, "");
        String languageKnown = GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_LANGUAGE_HOT_JOBS, "");
        JSONObject jsonObject = new JSONObject();
        try {

            if (!gender.equals("")) {
                jsonObject.put("gender", gender);
            }
            if (!city.equals("")) {
                jsonObject.put("city", city);
            }
            if (!skills.equals("")) {
                jsonObject.put("skills", skills);
            }

            if (!languageKnown.equals("")) {
                jsonObject.put("language", languageKnown);
            }
            if (!qualificationType.equals("")) {
                jsonObject.put("qualification_type", qualificationType);
            }
            jsonObject.put("hotjobs","yes");
            jsonObject.put("page", currentPages);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public void applyHotJobs(Context context, String user_id, int vacancy_id){
        JSONObject Json = new JSONObject();
        try {
            Json.put(AppConstant.KEY_APPLY_JOB_USER_ID,user_id);
            Json.put(AppConstant.KEY_APPLY_JOB_VACANCY_ID,vacancy_id);
            Log.v("request",Json.toString());
           ServiceHandler serviceHandler = new ServiceHandler(context);
           serviceHandler.StringRequest(Request.Method.POST, Json.toString(),AppConstant.APPLY_HOT_JOBS ,true, new ServiceHandler.VolleyCallback() {
            @Override
            public void onSuccess(String result) {

                Log.v("Response", result);

            }
        });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}