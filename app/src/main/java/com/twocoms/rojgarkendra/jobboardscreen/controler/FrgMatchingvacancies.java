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

import com.android.volley.Request;
import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.global.model.AppConstant;
import com.twocoms.rojgarkendra.global.model.CommonMethod;
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
    MatchingJobsAdapter allJobsAdapter;
    ArrayList<ModelHotJobs> modelHotJobs = new ArrayList<>();
    String message;
    public int numberofentries = 0;
    public int currentPages = 1;
    public String nextPageUrl = "";
    public int numberOfPagesFromServer = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frg_matching_vacancies, container, false);

        initialization(rootView);
        getHotJobs();
        return rootView;
    }

    void initialization(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_matching_vacancies_job);
    }

    public void getHotJobs() {
        ServiceHandler serviceHandler = new ServiceHandler(getActivity());
        String url = "";
        if (nextPageUrl.equals("")) {
            url = AppConstant.GET_ALL_JOBS;
        } else {
            url = nextPageUrl;
        }
        serviceHandler.StringRequest(Request.Method.GET, "", url, true, new ServiceHandler.VolleyCallback() {
            @Override
            public void onSuccess(String result) {

                Log.v("Response", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    message = jsonObject.getString("message");
                    if (jsonObject.getBoolean("success")) {

                        JSONObject object = jsonObject.getJSONObject("data");
                        JSONArray jsonArray = object.getJSONArray("data");

                        numberofentries = object.getInt("total");
                        int perPageData = object.getInt("per_page");
                        double numberofPages = ((double) numberofentries) / perPageData;
                        numberOfPagesFromServer = Integer.parseInt(CommonMethod.roundNumbertoNextPossibleValue(numberofPages + ""));
                        Log.e("numberOfPagesFromServer", "" + numberOfPagesFromServer);
                        nextPageUrl = object.getString("next_page_url");
//                        currentPages = jsonObject1.getInt("current_page");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            ModelHotJobs modelHotJob1 = new ModelHotJobs();
                            JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                            modelHotJob1.setId(jsonObject1.getInt("id"));
                            modelHotJob1.setSalary(jsonObject1.getString("salary"));
                            modelHotJob1.setJobTypes(jsonObject1.getString("job_type"));
                            modelHotJob1.setClientName(jsonObject1.getString("client_name"));
                            modelHotJob1.setNumberOpenings(jsonObject1.getString("number_of_open_positions"));
                            modelHotJob1.setLocation(jsonObject1.getString("region_of_work_location_in_india"));
                            modelHotJob1.setDates(jsonObject1.getString("created_on"));
                            modelHotJobs.add(modelHotJob1);

                        }

                        if (currentPages == 1) {
                            setAdapter();
                        } else {
                            if (allJobsAdapter != null) {
                                allJobsAdapter.setData(modelHotJobs);
                                allJobsAdapter.notifyDataSetChanged();
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
        allJobsAdapter = new MatchingJobsAdapter(getActivity(), modelHotJobs, FrgMatchingvacancies.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(allJobsAdapter);
        allJobsAdapter.notifyDataSetChanged();
    }

}