package com.twocoms.rojgarkendra.global.model;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.registrationscreen.controler.RegisterUserDataActivity;

import java.util.ArrayList;

public class BottomSheetDialog extends BottomSheetDialogFragment {

    TextInputEditText cityEditText, skillEditText, langEditText;
    AutoCompleteTextView qualificationText;
    TextView applyBtn;
    RadioGroup genderGrp;
    RadioButton male, female;
    String mobile_no, radioBtnText, radioButtonSmallText, eduJobStr = "";
    ImageView refreshBtn;
    private ArrayList<String> qualificationList = new ArrayList<>();
    String comingFrom;


    public interface applyButtonListener {
        public void applyButtonPressed();

//        public void backButtonPressed();
    }

    private applyButtonListener listener;

    public applyButtonListener getListener() {
        return listener;
    }

    public void setListener(applyButtonListener listener) {
        this.listener = listener;
    }

    public BottomSheetDialog() {
        // Required empty public constructor
    }

    public static BottomSheetDialog newInstance(String comingFrom) {
        BottomSheetDialog bottomSheetDialogFragment = new BottomSheetDialog();
        Bundle bundle = new Bundle();
        bottomSheetDialogFragment.setArguments(bundle);
        bundle.putString("comingFrom", comingFrom);
        return bottomSheetDialogFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout, container, false);
        comingFrom = getArguments().getString("comingFrom");
        initialization(v);
        qualificationArrayInit();
        onClick();
        Log.e("comingFrom", comingFrom);

        genderGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                refreshBtn.setVisibility(View.VISIBLE);
            }
        });
        return v;
    }

    void initialization(View view) {
        cityEditText = view.findViewById(R.id.city_edit_text);
        skillEditText = view.findViewById(R.id.skill_edit_text);
        langEditText = view.findViewById(R.id.language_edit_text);
        male = view.findViewById(R.id.male);
        female = view.findViewById(R.id.female);
        genderGrp = view.findViewById(R.id.gender_grp);
        qualificationText = view.findViewById(R.id.qualification_type_edit_text);
        refreshBtn = view.findViewById(R.id.refreshBtn);
        applyBtn = view.findViewById(R.id.apply_filter);
        qualificationText.setFocusable(false);
        cityEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0) {
                    refreshBtn.setVisibility(View.VISIBLE);
                } else {
                    refreshBtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        qualificationText.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0) {
                    refreshBtn.setVisibility(View.VISIBLE);
                } else {
                    refreshBtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        skillEditText.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0) {
                    refreshBtn.setVisibility(View.VISIBLE);
                } else {
                    refreshBtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        langEditText.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0) {
                    refreshBtn.setVisibility(View.VISIBLE);
                } else {
                    refreshBtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        setData();
    }

    void qualificationArrayInit() {
//        qualificationList.add("5th");
//        qualificationList.add("6th");
//        qualificationList.add("7th");
//        qualificationList.add("8th");
//        qualificationList.add("9th");
        qualificationList.add("10th");
        qualificationList.add("11th");
        qualificationList.add("12th");
        qualificationList.add("Diploma");
        qualificationList.add("Advanced Diploma");
        qualificationList.add("Graduation");
        qualificationList.add("Post Graduation");
        qualificationList.add("Doctoral");
        qualificationList.add("ITI");
        qualificationList.add("ITI Dual");
        qualificationList.add("Others");

    }

    void onClick() {
        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (comingFrom.equals(AppConstant.COMING_FROM_ALL_JOBS)) {
                    GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_CITY_ALL_JOBS, cityEditText.getText().toString());
                    GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_QUALIFICATION_TYPE_ALL_JOBS, qualificationText.getText().toString());
                    GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_LANGUAGE_ALL_JOBS, langEditText.getText().toString());
                    GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_SKILLS_ALL_JOBS, skillEditText.getText().toString());
                    if (male.isChecked()) {
                        GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_GENDER_ALL_JOBS, "male");
                    } else if (female.isChecked()) {
                        GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_GENDER_ALL_JOBS, "female");
                    } else {
                        GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_GENDER_ALL_JOBS, "");
                    }
                }

                else if (comingFrom.equals(AppConstant.COMING_FROM_HOT_JOBS)) {
                    GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_CITY_HOT_JOBS, cityEditText.getText().toString());
                    GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_QUALIFICATION_TYPE_HOT_JOBS, qualificationText.getText().toString());
                    GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_LANGUAGE_HOT_JOBS, langEditText.getText().toString());
                    GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_SKILLS_HOT_JOBS, skillEditText.getText().toString());
                    if (male.isChecked()) {
                        GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_GENDER_HOT_JOBS, "male");
                    } else if (female.isChecked()) {
                        GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_GENDER_HOT_JOBS, "female");
                    } else {
                        GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_GENDER_HOT_JOBS, "");
                    }
                }

                else if (comingFrom.equals(AppConstant.COMING_FROM_MATCHING_JOBS)) {
                    GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_CITY_MATCHING_JOBS, cityEditText.getText().toString());
                    GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_QUALIFICATION_TYPE_MATCHING_JOBS, qualificationText.getText().toString());
                    GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_LANGUAGE_MATCHING_JOBS, langEditText.getText().toString());
                    GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_SKILLS_MATCHING_JOBS, skillEditText.getText().toString());
                    if (male.isChecked()) {
                        GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_GENDER_MATCHING_JOBS, "male");
                    } else if (female.isChecked()) {
                        GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_GENDER_MATCHING_JOBS, "female");
                    } else {
                        GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_GENDER_MATCHING_JOBS, "");
                    }
                }

                else if (comingFrom.equals(AppConstant.COMING_FROM_POPULAR_JOBS)) {
                    GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_CITY_POPULAR_JOBS, cityEditText.getText().toString());
                    GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_QUALIFICATION_TYPE_POPULAR_JOBS, qualificationText.getText().toString());
                    GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_LANGUAGE_POPULAR_JOBS, langEditText.getText().toString());
                    GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_SKILLS_POPULAR_JOBS, skillEditText.getText().toString());
                    if (male.isChecked()) {
                        GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_GENDER_POPULAR_JOBS, "male");
                    } else if (female.isChecked()) {
                        GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_GENDER_POPULAR_JOBS, "female");
                    } else {
                        GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_GENDER_POPULAR_JOBS, "");
                    }
                }

                else if (comingFrom.equals(AppConstant.COMING_FROM_BATCH_MATES_JOBS)) {
                    GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_CITY_BATCH_MATES_JOBS, cityEditText.getText().toString());
                    GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_QUALIFICATION_TYPE_BATCH_MATES_JOBS, qualificationText.getText().toString());
                    GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_LANGUAGE_BATCH_MATES_JOBS, langEditText.getText().toString());
                    GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_SKILLS_BATCH_MATES_JOBS, skillEditText.getText().toString());
                    if (male.isChecked()) {
                        GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_GENDER_BATCH_MATES_JOBS, "male");
                    } else if (female.isChecked()) {
                        GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_GENDER_BATCH_MATES_JOBS, "female");
                    } else {
                        GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_GENDER_BATCH_MATES_JOBS, "");
                    }
                }
                dismiss();
                if (listener != null) {
                    listener.applyButtonPressed();
                }
            }
        });

        qualificationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.drop_down_item, qualificationList);
                qualificationText.setAdapter(adapter);
                {
                    qualificationText.showDropDown();

                }
            }
        });

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityEditText.setText("");
                qualificationText.setText("");
                skillEditText.setText("");
                langEditText.setText("");
                male.setChecked(false);
                female.setChecked(false);
            }
        });
    }


    void setData() {
        if (comingFrom.equals(AppConstant.COMING_FROM_ALL_JOBS)) {
            cityEditText.setText(GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_CITY_ALL_JOBS, ""));
            qualificationText.setText(GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_QUALIFICATION_TYPE_ALL_JOBS, ""));
            skillEditText.setText(GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_SKILLS_ALL_JOBS, ""));
            langEditText.setText(GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_LANGUAGE_ALL_JOBS, ""));
            if (GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_GENDER_ALL_JOBS, "").equals("male")) {
                male.setChecked(true);
            } else if (GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_GENDER_ALL_JOBS, "").equals("female")) {
                female.setChecked(true);
            } else {
                male.setChecked(false);
                female.setChecked(false);
            }


        }

        else  if (comingFrom.equals(AppConstant.COMING_FROM_HOT_JOBS)) {
            cityEditText.setText(GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_CITY_HOT_JOBS, ""));
            qualificationText.setText(GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_QUALIFICATION_TYPE_HOT_JOBS, ""));
            skillEditText.setText(GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_SKILLS_HOT_JOBS, ""));
            langEditText.setText(GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_LANGUAGE_HOT_JOBS, ""));
            if (GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_GENDER_HOT_JOBS, "").equals("male")) {
                male.setChecked(true);
            } else if (GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_GENDER_HOT_JOBS, "").equals("female")) {
                female.setChecked(true);
            } else {
                male.setChecked(false);
                female.setChecked(false);
            }


        }

        else  if (comingFrom.equals(AppConstant.COMING_FROM_MATCHING_JOBS)) {
            cityEditText.setText(GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_CITY_MATCHING_JOBS, ""));
            qualificationText.setText(GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_QUALIFICATION_TYPE_MATCHING_JOBS, ""));
            skillEditText.setText(GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_SKILLS_MATCHING_JOBS, ""));
            langEditText.setText(GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_LANGUAGE_MATCHING_JOBS, ""));
            if (GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_GENDER_MATCHING_JOBS, "").equals("male")) {
                male.setChecked(true);
            } else if (GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_GENDER_MATCHING_JOBS, "").equals("female")) {
                female.setChecked(true);
            } else {
                male.setChecked(false);
                female.setChecked(false);
            }


        }

        else  if (comingFrom.equals(AppConstant.COMING_FROM_POPULAR_JOBS)) {
            cityEditText.setText(GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_CITY_POPULAR_JOBS, ""));
            qualificationText.setText(GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_QUALIFICATION_TYPE_POPULAR_JOBS, ""));
            skillEditText.setText(GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_SKILLS_POPULAR_JOBS, ""));
            langEditText.setText(GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_LANGUAGE_POPULAR_JOBS, ""));
            if (GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_GENDER_POPULAR_JOBS, "").equals("male")) {
                male.setChecked(true);
            } else if (GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_GENDER_POPULAR_JOBS, "").equals("female")) {
                female.setChecked(true);
            } else {
                male.setChecked(false);
                female.setChecked(false);
            }


        }

        else  if (comingFrom.equals(AppConstant.COMING_FROM_BATCH_MATES_JOBS)) {
            cityEditText.setText(GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_CITY_BATCH_MATES_JOBS, ""));
            qualificationText.setText(GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_QUALIFICATION_TYPE_BATCH_MATES_JOBS, ""));
            skillEditText.setText(GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_SKILLS_BATCH_MATES_JOBS, ""));
            langEditText.setText(GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_LANGUAGE_BATCH_MATES_JOBS, ""));
            if (GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_GENDER_BATCH_MATES_JOBS, "").equals("male")) {
                male.setChecked(true);
            } else if (GlobalPreferenceManager.getStringForKey(getActivity(), AppConstant.KEY_FILTER_GENDER_BATCH_MATES_JOBS, "").equals("female")) {
                female.setChecked(true);
            } else {
                male.setChecked(false);
                female.setChecked(false);
            }


        }

        if(!cityEditText.getText().toString().isEmpty() || !cityEditText.getText().toString().isEmpty() || !cityEditText.getText().toString().isEmpty() ||
                !cityEditText.getText().toString().isEmpty() || !cityEditText.getText().toString().isEmpty() || male.isChecked() || female.isChecked() ){
            refreshBtn.setVisibility(View.VISIBLE);
        }else {
            refreshBtn.setVisibility(View.GONE);
        }
    }


}