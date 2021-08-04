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
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.interviewscreen.controler.AppliedApplicationActivity;
import com.twocoms.rojgarkendra.registrationscreen.controler.RegisterUserDataActivity;

import java.util.ArrayList;

public class AppliedJobBottomSheetDialog extends BottomSheetDialogFragment {
    AutoCompleteTextView statusText;
    TextView applyJobFilterBtn;
    ImageView refreshBtn;
    private ArrayList<String> statusList = new ArrayList<>();
    String statusStr;
    String statusAppliedStr;




    public interface appliedJobApplyBtnPressed{
        public void appliedJobApplyBtnPressed();
    }

    private appliedJobApplyBtnPressed listener;

    public appliedJobApplyBtnPressed getListener() {
        return listener;
    }

    public void setListener(appliedJobApplyBtnPressed listener) {
        this.listener = listener;
    }

    public static AppliedJobBottomSheetDialog newInstance() {
        return new AppliedJobBottomSheetDialog();
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.applied_job_bottom_sheet, container, false);
        initialization(v);
        onClick();
        statusText.setText(getStatus());
        return v;
    }

    private void initialization(View v){

        statusList.add("Interview Scheduled");
        statusList.add("Applied");
        statusList.add("Offer Proposed");
        statusList.add("Offer Accepted");
        statusList.add("Offer Rejected");
        statusList.add("Rejected By Client");
        statusText =  (AutoCompleteTextView)v.findViewById(R.id.status_edit_text);
        applyJobFilterBtn = (TextView)v.findViewById(R.id.apply_job_filter);
        refreshBtn = (ImageView)v.findViewById(R.id.refreshBtn);

        statusText.addTextChangedListener(new TextWatcher() {


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
    }

    private void onClick(){

        statusText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.drop_down_item, statusList);
                statusText.setAdapter(adapter);
                {
                    statusText.showDropDown();
                }
            }
        });

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statusText.setText("");

            }
        });

        applyJobFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statusStr = statusText.getText().toString();
                Log.e("Status",statusStr);
                if (statusStr.equals("Interview Scheduled")){
                    GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_STATUS_APPLIED_JOB, AppConstant.VALUE_STATUS_SCHEDULED);
                }else if(statusStr.equals("Applied")){
                    GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_STATUS_APPLIED_JOB, AppConstant.VALUE_STATUS_APPLIED);
                }else if (statusStr.equals("Offer Proposed")){
                    GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_STATUS_APPLIED_JOB, AppConstant.VALUE_STATUS_PROPOSED);
                }else if (statusStr.equals("Offer Rejected")){
                    GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_STATUS_APPLIED_JOB, AppConstant.VALUE_STATUS_REJECTED);
                }else if (statusStr.equals("Offer Accepted")){
                    GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_STATUS_APPLIED_JOB, AppConstant.VALUE_STATUS_ACCEPTED);
                }else if (statusStr.equals("Rejected By Client")){
                    GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_STATUS_APPLIED_JOB, AppConstant.VALUE_STATUS_REJECTED_BY_CLIENT);
                }else if (statusStr.equals("")){
                    GlobalPreferenceManager.saveStringForKey(getActivity(), AppConstant.KEY_FILTER_STATUS_APPLIED_JOB, AppConstant.VALUE_STATUS_NULL);
                }
                dismiss();
                if (listener != null) {
                    listener.appliedJobApplyBtnPressed();
                }
            }
        });

    }

    String getStatus(){
      String statusData =  GlobalPreferenceManager.getStringForKey(getActivity(),AppConstant.KEY_FILTER_STATUS_APPLIED_JOB,"");

      if(statusData.equalsIgnoreCase(AppConstant.VALUE_STATUS_SCHEDULED)){
          statusAppliedStr = "Interview Scheduled";
      } else if(statusData.equalsIgnoreCase(AppConstant.VALUE_STATUS_APPLIED)){
            statusAppliedStr = "Applied";
      }
      else if(statusData.equalsIgnoreCase(AppConstant.VALUE_STATUS_PROPOSED)){
          statusAppliedStr = "Offer Proposed";
      }
      else if(statusData.equalsIgnoreCase(AppConstant.VALUE_STATUS_ACCEPTED)){
          statusAppliedStr = "Offer Accepted";
      }
      else if(statusData.equalsIgnoreCase(AppConstant.VALUE_STATUS_REJECTED)){
          statusAppliedStr = "Offer Rejected";
      }
      else if(statusData.equalsIgnoreCase(AppConstant.VALUE_STATUS_REJECTED_BY_CLIENT)){
          statusAppliedStr = "Rejected By Client";
      }else{
          statusAppliedStr = "";
      }
        return statusAppliedStr;
    }



}
