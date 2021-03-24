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
                    String message = jsonObject.getString(AppConstant.KEY_VACANCY_DETAIL_MESSAGE);
                    if (jsonObject.getBoolean(AppConstant.KEY_VACANCY_DETAIL_SUCCESS)) {
                        JSONObject jsonObjectData = jsonObject.getJSONObject(AppConstant.KEY_VACANCY_DETAIL_OBJ_DATA);
                        VacancyDetailModel vacancyDetailModel = new VacancyDetailModel();
                        vacancyDetailModel.setId(jsonObjectData.getInt(AppConstant.KEY_VACANCY_DETAIL_ID));
                        vacancyDetailModel.setVacancy_master_id(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_VACANCY_MASTER_ID));
                        vacancyDetailModel.setNaps_opportunity_id(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_NAPS_OPPORTUNITY_ID));
                        vacancyDetailModel.setZoho_recruit_id(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_ZOHO_RECRUIT_ID));
                        vacancyDetailModel.setZoho_sourcing_id(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_ZOHO_SOURCING_ID));
                        vacancyDetailModel.setVacancy_title(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_VACANCY_TITLE));
                        vacancyDetailModel.setClient_name(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_CLIENT_NAME));
                        vacancyDetailModel.setIndustry(jsonObjectData.getInt(AppConstant.KEY_VACANCY_DETAIL_INDUSTRY));
                        vacancyDetailModel.setContact_name(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_CONTACT_NAME));
                        vacancyDetailModel.setNumber_of_open_positions(jsonObjectData.getInt(AppConstant.KEY_VACANCY_DETAIL_NO_OPEN_POSITION));
                        vacancyDetailModel.setYears_of_exp_required(jsonObjectData.getInt(AppConstant.KEY_VACANCY_DETAIL_YEARS_EXP_REQUIRED));
                        vacancyDetailModel.setMonths_of_exp_required(jsonObjectData.getInt(AppConstant.KEY_VACANCY_DETAIL_MONTHS_EXP_REQUIRED));
                        vacancyDetailModel.setJob_responsibilities(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_JOB_RESPONSIBILITY));
                        vacancyDetailModel.setGender_preferences(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_GENDER_PREFERENCFE));
                        vacancyDetailModel.setJob_description(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_JOB_DESCRIPTION));
                        vacancyDetailModel.setSkills(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_SKILLS));
                        vacancyDetailModel.setLanguage_preference(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_LANG_PREFERENCE));
                        vacancyDetailModel.setMinimum_education(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_MIN_EDU));
                        vacancyDetailModel.setOther_eligibility_criterea(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_OTHER_ELIGIBILITY_CRITEREA));
                        vacancyDetailModel.setHeight(jsonObjectData.getInt(AppConstant.KEY_VACANCY_DETAIL_HEIGHT));
                        vacancyDetailModel.setWeight(jsonObjectData.getInt(AppConstant.KEY_VACANCY_DETAIL_WEIGHT));
                        vacancyDetailModel.setShift_type(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_SHIFT_TYPE));
                        vacancyDetailModel.setShift_timing(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_SHIFT_TIMIMNG));
                        vacancyDetailModel.setFooding(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_FOODING));
                        vacancyDetailModel.setLodging(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_LOODING));
                        vacancyDetailModel.setOther_benefits_for_employees(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_OTHER_BENEFITS_FOR_EMP));
                        vacancyDetailModel.setIn_hand_salary(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_IN_HAND_SALARY));
                        vacancyDetailModel.setCtc(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_CTC));
                        vacancyDetailModel.setCurrency(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_CURRENCY));
                        vacancyDetailModel.setMinimum_salary_stipend(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_MIN_SALARY_STIPED));
                        vacancyDetailModel.setMaximum_salary_stipend(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_MAX_SALARY_STIPED));
                        vacancyDetailModel.setOvertime_per_day_hr(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_OVERTIME_PER_DAY_HR));
                        vacancyDetailModel.setOt_amount(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_OT_AMMOUNT));
                        vacancyDetailModel.setJob_opening_status(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_JOB_OPENING_STATUS));
                        vacancyDetailModel.setDate_opened(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_DATE_OPENED));
                        vacancyDetailModel.setJob_type(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_JOB_TYPE));
                        vacancyDetailModel.setIs_hot_job_opening(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_IS_HOT_JOB_OPENING));
                        vacancyDetailModel.setAssigned_recruiter(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_ASSIGNED_RECRUITER));
                        vacancyDetailModel.setCity(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_CITY));
                        vacancyDetailModel.setState_province(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_STATE_PROVINCE));
                        vacancyDetailModel.setCountry(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_COUNTRY));
                        vacancyDetailModel.setPostal_code(jsonObjectData.getInt(AppConstant.KEY_VACANCY_DETAIL_POSTAL_CODE));
                        vacancyDetailModel.setRegion_of_work_location_in_india(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_WORK_LOCATION));
                        vacancyDetailModel.setAdded_by(jsonObjectData.getDouble(AppConstant.KEY_VACANCY_DETAIL_ADDED_BY));
                        vacancyDetailModel.setCreated_on(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_CREATED_ON));
                        vacancyDetailModel.setEdited_by(jsonObjectData.getDouble(AppConstant.KEY_VACANCY_DETAIL_EDITED_BY));
                        vacancyDetailModel.setUpdated_on(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_UPDATED_ON));
                        vacancyDetailModel.setPublished(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_PUBLISHED));
                        vacancyDetailModel.setRevenue_per_position(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_REVENUE_PER_POSITION));
                        vacancyDetailModel.setIndsId(jsonObjectData.getDouble(AppConstant.KEY_VACANCY_DETAIL_INDSID));
                        vacancyDetailModel.setInds_name(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_INDS_NAME));
                        vacancyDetailModel.setInds_desc(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_INDS_DESC));
                        vacancyDetailModel.setInds_logo(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_INDS_LOGO));
                        vacancyDetailModel.setInds_status(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_INDS_STATUS));
                        vacancyDetailModel.setCreated_on(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_CREAETED_AT));
                        vacancyDetailModel.setStCode(jsonObjectData.getDouble(AppConstant.KEY_VACANCY_DETAIL_STCODE));
                        vacancyDetailModel.setStateName(jsonObjectData.getString(AppConstant.KEY_VACANCY_DETAIL_STATENAME));
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