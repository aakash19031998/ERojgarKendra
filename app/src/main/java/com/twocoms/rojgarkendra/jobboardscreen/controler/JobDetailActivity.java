package com.twocoms.rojgarkendra.jobboardscreen.controler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.databinding.ActivityJobDetailBinding;
import com.twocoms.rojgarkendra.global.model.AppConstant;
import com.twocoms.rojgarkendra.global.model.CommonMethod;
import com.twocoms.rojgarkendra.global.model.ServiceHandler;
import com.twocoms.rojgarkendra.jobboardscreen.model.ModelHotJobs;
import com.twocoms.rojgarkendra.jobboardscreen.model.VacancyDetailModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JobDetailActivity extends AppCompatActivity {

    private ActivityJobDetailBinding jobDetailBinding;

    ImageView menuIcon, backIcon, homeIcon, profileIcon;
    TextView titleToolbar;
    LinearLayout titleLnr;
    String jobId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jobDetailBinding = ActivityJobDetailBinding.inflate(getLayoutInflater());
        View view = jobDetailBinding.getRoot();
        setContentView(view);
        initialization();
        setToolbarVisibility();
        onClick();
    }

    void initialization() {
//        jobDetailBinding.successStoriesToolbar.menu.setVisibility(View.GONE);
//        menuIcon = (ImageView) findViewById(R.id.menu);
//        backIcon = (ImageView) findViewById(R.id.backbutton);
//        homeIcon = (ImageView) findViewById(R.id.home_img);
//        profileIcon = (ImageView) findViewById(R.id.user_profile_img);
//        titleToolbar = (TextView) findViewById(R.id.title);
//        titleLnr = (LinearLayout) findViewById(R.id.title_lnr);
        jobDetailBinding.successStoriesToolbar.title.setText("Vacancy Detail");
        jobId = getIntent().getStringExtra("jobId");
        getJobDetail();
    }

    void setToolbarVisibility() {
        jobDetailBinding.successStoriesToolbar.menu.setVisibility(View.GONE);
        jobDetailBinding.successStoriesToolbar.userProfileImg.setVisibility(View.GONE);
        jobDetailBinding.successStoriesToolbar.backbutton.setVisibility(View.VISIBLE);
        jobDetailBinding.successStoriesToolbar.homeImg.setVisibility(View.VISIBLE);
//        menuIcon.setVisibility(View.GONE);
//        backIcon.setVisibility(View.VISIBLE);
//        homeIcon.setVisibility(View.VISIBLE);
//        profileIcon.setVisibility(View.GONE);
    }

    void onClick() {

        jobDetailBinding.successStoriesToolbar.backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        jobDetailBinding.successStoriesToolbar.homeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        jobDetailBinding.successStoriesToolbar.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        backIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
//
//        homeIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
//
//        titleLnr.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
    }


    public void getJobDetail() {
        ServiceHandler serviceHandler = new ServiceHandler(JobDetailActivity.this);
        serviceHandler.StringRequest(Request.Method.GET, "", AppConstant.GET_JOBS_DETAIL + jobId, true, new ServiceHandler.VolleyCallback() {
            @Override
            public void onSuccess(String result) {

                Log.v("Response", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String message = jsonObject.getString("message");
                    if (jsonObject.getBoolean("success")) {
                        JSONObject jsonObjectData = jsonObject.getJSONObject("data");
                        VacancyDetailModel vacancyDetailModel = new VacancyDetailModel();
                        vacancyDetailModel.setId(jsonObjectData.getInt("id"));
                        vacancyDetailModel.setVacancy_master_id(jsonObjectData.getString("vacancy_master_id"));
                        vacancyDetailModel.setNaps_opportunity_id(jsonObjectData.getString("naps_opportunity_id"));
                        vacancyDetailModel.setZoho_recruit_id(jsonObjectData.getString("zoho_recruit_id"));
                        vacancyDetailModel.setZoho_sourcing_id(jsonObjectData.getString("zoho_sourcing_id"));
                        vacancyDetailModel.setVacancy_title(jsonObjectData.getString("vacancy_title"));
                        vacancyDetailModel.setClient_name(jsonObjectData.getString("client_name"));
                        vacancyDetailModel.setIndustry(jsonObjectData.getInt("industry"));
                        vacancyDetailModel.setContact_name(jsonObjectData.getString("contact_name"));
                        vacancyDetailModel.setNumber_of_open_positions(jsonObjectData.getInt("number_of_open_positions"));
                        vacancyDetailModel.setYears_of_exp_required(jsonObjectData.getInt("years_of_exp_required"));
                        vacancyDetailModel.setMonths_of_exp_required(jsonObjectData.getInt("months_of_exp_required"));
                        vacancyDetailModel.setJob_responsibilities(jsonObjectData.getString("job_responsibilities"));
                        vacancyDetailModel.setGender_preferences(jsonObjectData.getString("gender_preferences"));
                        vacancyDetailModel.setJob_description(jsonObjectData.getString("job_description"));
                        vacancyDetailModel.setSkills(jsonObjectData.getString("skills"));
                        vacancyDetailModel.setLanguage_preference(jsonObjectData.getString("language_preference"));
                        vacancyDetailModel.setMinimum_education(jsonObjectData.getString("minimum_education"));
                        vacancyDetailModel.setOther_eligibility_criterea(jsonObjectData.getString("other_eligibility_criterea"));
                        vacancyDetailModel.setHeight(jsonObjectData.getInt("height"));
                        vacancyDetailModel.setWeight(jsonObjectData.getInt("weight"));
                        vacancyDetailModel.setShift_type(jsonObjectData.getString("shift_type"));
                        vacancyDetailModel.setShift_timing(jsonObjectData.getString("shift_timing"));
                        vacancyDetailModel.setFooding(jsonObjectData.getString("fooding"));
                        vacancyDetailModel.setLodging(jsonObjectData.getString("lodging"));
                        vacancyDetailModel.setOther_benefits_for_employees(jsonObjectData.getString("other_benefits_for_employees"));
                        vacancyDetailModel.setIn_hand_salary(jsonObjectData.getString("in_hand_salary"));
                        vacancyDetailModel.setCtc(jsonObjectData.getString("ctc"));
                        vacancyDetailModel.setCurrency(jsonObjectData.getString("currency"));
                        vacancyDetailModel.setMinimum_salary_stipend(jsonObjectData.getString("minimum_salary_stipend"));
                        vacancyDetailModel.setMaximum_salary_stipend(jsonObjectData.getString("maximum_salary_stipend"));
                        vacancyDetailModel.setOvertime_per_day_hr(jsonObjectData.getString("overtime_per_day_hr"));
                        vacancyDetailModel.setOt_amount(jsonObjectData.getString("ot_amount"));
                        vacancyDetailModel.setJob_opening_status(jsonObjectData.getString("job_opening_status"));
                        vacancyDetailModel.setDate_opened(jsonObjectData.getString("date_opened"));
                        vacancyDetailModel.setJob_type(jsonObjectData.getString("job_type"));
                        vacancyDetailModel.setIs_hot_job_opening(jsonObjectData.getString("is_hot_job_opening"));
                        vacancyDetailModel.setAssigned_recruiter(jsonObjectData.getString("assigned_recruiter"));
                        vacancyDetailModel.setCity(jsonObjectData.getString("city"));
                        vacancyDetailModel.setState_province(jsonObjectData.getString("state_province"));
                        vacancyDetailModel.setCountry(jsonObjectData.getString("country"));
                        vacancyDetailModel.setPostal_code(jsonObjectData.getInt("postal_code"));
                        vacancyDetailModel.setRegion_of_work_location_in_india(jsonObjectData.getString("region_of_work_location_in_india"));
                        vacancyDetailModel.setAdded_by(jsonObjectData.getDouble("added_by"));
                        vacancyDetailModel.setCreated_on(jsonObjectData.getString("created_on"));
                        vacancyDetailModel.setEdited_by(jsonObjectData.getDouble("edited_by"));
                        vacancyDetailModel.setUpdated_on(jsonObjectData.getString("updated_on"));
                        vacancyDetailModel.setPublished(jsonObjectData.getString("published"));
                        vacancyDetailModel.setRevenue_per_position(jsonObjectData.getString("revenue_per_position"));
                        vacancyDetailModel.setIndsId(jsonObjectData.getDouble("indsId"));
                        vacancyDetailModel.setInds_name(jsonObjectData.getString("inds_name"));
                        vacancyDetailModel.setInds_desc(jsonObjectData.getString("inds_desc"));
                        vacancyDetailModel.setInds_logo(jsonObjectData.getString("inds_logo"));
                        vacancyDetailModel.setInds_status(jsonObjectData.getString("inds_status"));
                        vacancyDetailModel.setCreated_on(jsonObjectData.getString("created_at"));
                        vacancyDetailModel.setStCode(jsonObjectData.getDouble("StCode"));
                        vacancyDetailModel.setStateName(jsonObjectData.getString("StateName"));
                        setDataonView(vacancyDetailModel);

                    } else {
                        CommonMethod.showToast(message, JobDetailActivity.this);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }


    void setDataonView(VacancyDetailModel vacancyDetailModel) {
        Log.e("vacancyDetailModel", "fecthed");
        jobDetailBinding.salaryText.setText(getResources().getString(R.string.Rs) + CommonMethod.roundNumbertoNextPossibleValue(vacancyDetailModel.getCtc()));
        jobDetailBinding.clientText.setText(vacancyDetailModel.getClient_name());
        jobDetailBinding.jobTypeText.setText(vacancyDetailModel.getJob_type());
        jobDetailBinding.locationText.setText(vacancyDetailModel.getCity());
        jobDetailBinding.vacancyText.setText(CommonMethod.roundNumbertoNextPossibleValue(vacancyDetailModel.getNumber_of_open_positions()+"") + " open position");
        jobDetailBinding.jobSkills.setText(vacancyDetailModel.getSkills());
        if (vacancyDetailModel.getGender_preferences().equalsIgnoreCase("male")) {
            jobDetailBinding.jobPreferredGender.setText("Male");
        } else {
            jobDetailBinding.jobPreferredGender.setText("Female");
        }
        jobDetailBinding.jobExperinaceRequired.setText(CommonMethod.roundNumbertoNextPossibleValue(vacancyDetailModel.getYears_of_exp_required() + "") + " Years");
        jobDetailBinding.jobMinimumQualificationRequired.setText(vacancyDetailModel.getMinimum_education());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            jobDetailBinding.jobDesciption.setText(Html.fromHtml(vacancyDetailModel.getJob_description(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            jobDetailBinding.jobDesciption.setText(Html.fromHtml(vacancyDetailModel.getJob_description()));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            jobDetailBinding.jobResponsiblity.setText(Html.fromHtml(vacancyDetailModel.getJob_responsibilities(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            jobDetailBinding.jobResponsiblity.setText(Html.fromHtml(vacancyDetailModel.getJob_responsibilities()));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            jobDetailBinding.jobBenefit.setText(Html.fromHtml(vacancyDetailModel.getOther_benefits_for_employees(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            jobDetailBinding.jobBenefit.setText(Html.fromHtml(vacancyDetailModel.getOther_benefits_for_employees()));
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}