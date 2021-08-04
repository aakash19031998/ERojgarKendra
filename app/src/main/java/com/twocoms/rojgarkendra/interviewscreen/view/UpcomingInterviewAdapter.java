package com.twocoms.rojgarkendra.interviewscreen.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.google.android.material.textfield.TextInputEditText;
import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.global.model.AppConstant;
import com.twocoms.rojgarkendra.global.model.CommonMethod;
import com.twocoms.rojgarkendra.global.model.GlobalPreferenceManager;
import com.twocoms.rojgarkendra.global.model.ServiceHandler;
import com.twocoms.rojgarkendra.interviewscreen.controler.AppliedApplicationActivity;
import com.twocoms.rojgarkendra.interviewscreen.controler.UpcomingInterviewActivity;
import com.twocoms.rojgarkendra.interviewscreen.model.AppliedAndUpcommingModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class UpcomingInterviewAdapter extends RecyclerView.Adapter<UpcomingInterviewAdapter.ViewHolder> {

    Context context;
    List<AppliedAndUpcommingModel> appliedAndUpcommingModels;
    String studentRemarkStr = "";
    int applied_id;
    String responseMsg;

    public UpcomingInterviewAdapter(Context context, List<AppliedAndUpcommingModel> appliedAndUpcommingModels) {
        this.context = context;
        this.appliedAndUpcommingModels = appliedAndUpcommingModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View normalView = LayoutInflater.from(context).inflate(R.layout.item_upcoming_interview, null);
        return new ViewHolder(normalView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.clientNameText.setText(appliedAndUpcommingModels.get(position).getClientName());
        holder.postedDateText.setText("Applied on : " + CommonMethod.parseDateToFormat(appliedAndUpcommingModels.get(position).getAppliedDate()));
        holder.jobType.setText(appliedAndUpcommingModels.get(position).getJobType());
        holder.location.setText(appliedAndUpcommingModels.get(position).getLocationOfWork());
        holder.numberOfOpenings.setText(appliedAndUpcommingModels.get(position).getNumberOfOpenPositions() + " openings");
        holder.salary.setText(context.getResources().getString(R.string.Rs) + CommonMethod.roundNumbertoNextPossibleValue(appliedAndUpcommingModels.get(position).getSalary()));
        holder.addRemarkImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applied_id = appliedAndUpcommingModels.get(position).getAppliedId();
                showRemarkDialog(holder.studentRemark,holder.studentHeader,applied_id);
            }
        });

        if (!appliedAndUpcommingModels.get(position).getStudentRemark().equals("null") && !appliedAndUpcommingModels.get(position).getStudentRemark().equals("")){
            holder.remarkView.setVisibility(View.VISIBLE);
            holder.studentHeader.setVisibility(View.VISIBLE);
            holder.studentRemark.setVisibility(View.VISIBLE);
         //   holder.addRemarkImg.setVisibility(View.VISIBLE);
            holder.studentRemark.setText(appliedAndUpcommingModels.get(position).getStudentRemark());
        }else {
           holder.remarkView.setVisibility(View.VISIBLE);
          //  holder.addRemarkImg.setVisibility(View.VISIBLE);
        }

        holder.vacancy_title_text.setText(appliedAndUpcommingModels.get(position).getVacancyTitle());

        if (!appliedAndUpcommingModels.get(position).getClientRemark().equals("null") && !appliedAndUpcommingModels.get(position).getClientRemark().equals("")) {

            holder.remarkView.setVisibility(View.VISIBLE);
            holder.clientRemarkLnr.setVisibility(View.VISIBLE);
            holder.clientRemark.setText(appliedAndUpcommingModels.get(position).getClientRemark());
        }
        if (position == appliedAndUpcommingModels.size() - 1) {
            if (context instanceof UpcomingInterviewActivity) {
                if (UpcomingInterviewActivity.currentPages == UpcomingInterviewActivity.numberOfPagesFromServer) {
                    Log.e("AllDataLoaded", "true");
                } else {

                    UpcomingInterviewActivity.currentPages = UpcomingInterviewActivity.currentPages + 1;
                    ((UpcomingInterviewActivity) context).getUpcomingInterview();
                }

            }

        }

    }

    @Override
    public int getItemCount() {
        return appliedAndUpcommingModels.size();
    }

    public void setData(List<AppliedAndUpcommingModel> appliedAndUpcommingModel) {
        this.appliedAndUpcommingModels = appliedAndUpcommingModel;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView postedDateText, clientNameText, salary, jobType, numberOfOpenings, location, studentRemark, clientRemark,studentHeader,vacancy_title_text;
        View remarkView;
        RelativeLayout studentRemarkLnr;
        RelativeLayout clientRemarkLnr;
        ImageView addRemarkImg;



        ViewHolder(View itemView) {
            super(itemView);
            postedDateText = itemView.findViewById(R.id.date_job_posted);
            clientNameText = itemView.findViewById(R.id.client_text);
            salary = itemView.findViewById(R.id.salary_text);
            jobType = itemView.findViewById(R.id.job_type_text);
            numberOfOpenings = itemView.findViewById(R.id.vacancy_text);
            studentRemark = itemView.findViewById(R.id.student_remark_text);
            clientRemark = itemView.findViewById(R.id.client_remark_text);
            remarkView = itemView.findViewById(R.id.remark_line);
            studentRemarkLnr = itemView.findViewById(R.id.student_remark_lnr);
            clientRemarkLnr = itemView.findViewById(R.id.client_remark_lnr);
            addRemarkImg = itemView.findViewById(R.id.add_remark);
            studentHeader = itemView.findViewById(R.id.student_remark_header);
            location = itemView.findViewById(R.id.location_text);
            vacancy_title_text = itemView.findViewById(R.id.vacancy_title_text);


        }

    }

    public void showRemarkDialog(final TextView remarkTextview, final TextView studentRemarkHeader, final int applied_id) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_remark_dialog);
        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        final TextInputEditText remartText = (TextInputEditText) dialog.findViewById(R.id.add_remark_text);
        Button addRemarkBtn = (Button) dialog.findViewById(R.id.addRemarkBtn);
        // if button is clicked, close the custom dialog

        addRemarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                studentRemarkStr = remartText.getText().toString().trim();
                if (!studentRemarkStr.equals("null") && !studentRemarkStr.equals("") &&!studentRemarkStr.isEmpty()){
                    addStudentRemark(applied_id,studentRemarkStr,remarkTextview,studentRemarkHeader);
//                    remarkTextview.setText(studentRemarkStr);
//                    remarkTextview.setVisibility(View.VISIBLE);
//                    studentRemarkHeader.setVisibility(View.VISIBLE);
                }

            }
        });
        dialog.show();
    }


    public void addStudentRemark(int applied_id, String studentMsg,final TextView remarkTextview, final TextView studentRemarkHeader) {
        JSONObject json = new JSONObject();

        try {
            json.put(AppConstant.KEY_APPLIED_ID_REMARK,applied_id);
            json.put(AppConstant.KEY_APPLIED_STUDENT_REMARKS,studentMsg);
            Log.v("Request", json.toString());

            ServiceHandler serviceHandler = new ServiceHandler(context);
            serviceHandler.StringRequest(Request.Method.POST, json.toString(), AppConstant.ADD_STUDENT_REMARK, true, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    Log.v("Response", result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        responseMsg = jsonObject.getString("message");

                        if (jsonObject.getBoolean("success")){
                             CommonMethod.showToast(responseMsg,context);
                             remarkTextview.setText(studentRemarkStr);
                             remarkTextview.setVisibility(View.VISIBLE);
                             studentRemarkHeader.setVisibility(View.VISIBLE);
                        }else {
                            CommonMethod.showToast(responseMsg,context);
                        }

                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }



                }
            });

        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }

}
