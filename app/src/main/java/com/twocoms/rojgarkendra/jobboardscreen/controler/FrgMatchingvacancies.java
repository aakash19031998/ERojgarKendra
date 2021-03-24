package com.twocoms.rojgarkendra.jobboardscreen.controler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.global.model.AppConstant;
import com.twocoms.rojgarkendra.global.model.BottomSheetDialog;
import com.twocoms.rojgarkendra.global.model.CommonMethod;
import com.twocoms.rojgarkendra.global.model.GlobalPreferenceManager;
import com.twocoms.rojgarkendra.global.model.ServiceHandler;
import com.twocoms.rojgarkendra.jobboardscreen.model.ModelHotJobs;
import com.twocoms.rojgarkendra.jobboardscreen.view.AllJobsAdapter;
import com.twocoms.rojgarkendra.jobboardscreen.view.MatchingJobsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FrgMatchingvacancies extends Fragment {

    RecyclerView recyclerView;
    MatchingJobsAdapter matchingJobsAdapter;
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
        View rootView = inflater.inflate(R.layout.frg_matching_vacancies, container, false);
        CommonMethod.clearAllFilterDataMatchingVacancies(getActivity());
        String gender = GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_GENDER, "");
        if (gender.equals("M")) {
            GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_GENDER_MATCHING_JOBS, "male");
        } else if (gender.equals("F")) {
            GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_GENDER_MATCHING_JOBS, "female");

        } else {
            GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_GENDER_MATCHING_JOBS, "");
        }
        String qualificationType = GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_QUALIFICATION_TYPE, "");
        GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_QUALIFICATION_TYPE_MATCHING_JOBS, qualificationType);
        initialization(rootView);
        onclick();
        getAllJobsData();
        return rootView;
    }

    void initialization(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_matching_vacancies_job);
        filterBtn = (RelativeLayout) view.findViewById(R.id.filter_layout);
        noVacancyText = view.findViewById(R.id.novacancytext);
    }

    void onclick() {
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheet = BottomSheetDialog.newInstance(AppConstant.COMING_FROM_MATCHING_JOBS);
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
                        getAllJobsData();
                    }
                });
                bottomSheet.show(getActivity().getSupportFragmentManager(), "ModalBottomSheet");
            }
        });
    }

    public void getAllJobsData() {
        ServiceHandler serviceHandler = new ServiceHandler(getActivity());
        String url = "";
        url = AppConstant.GET_ALL_JOBS;
        String jSonRequest = getPostParameter();
        Log.v("Request", jSonRequest);
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
                        // nextPageUrl = object.getString("next_page_url");

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
                            if (matchingJobsAdapter != null) {
                                matchingJobsAdapter.setData(modelHotJobs);
                                matchingJobsAdapter.notifyDataSetChanged();
                            } else {
                                setAdapter();
                            }
                        }


                    } else {
                        CommonMethod.showToast(message, getActivity());
                    }


                } catch (JSONException e) {
                    CommonMethod.showToast(AppConstant.SOMETHING_WENT_WRONG, getActivity());
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
            matchingJobsAdapter = new MatchingJobsAdapter(getActivity(), modelHotJobs, FrgMatchingvacancies.this);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(matchingJobsAdapter);
            matchingJobsAdapter.notifyDataSetChanged();
            noVacancyText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

    }


    String getPostParameter() {
        String gender = GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_GENDER_MATCHING_JOBS, "");
        String city = GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_CITY_MATCHING_JOBS, "");
        String skills = GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_SKILLS_MATCHING_JOBS, "");
        String qualificationType = GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_QUALIFICATION_TYPE_MATCHING_JOBS, "");
        String languageKnown = GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_LANGUAGE_MATCHING_JOBS, "");
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

            jsonObject.put("page", currentPages);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

}