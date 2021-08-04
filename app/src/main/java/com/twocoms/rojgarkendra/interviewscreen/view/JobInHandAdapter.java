package com.twocoms.rojgarkendra.interviewscreen.view;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.google.android.material.textfield.TextInputEditText;
import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.global.model.AppConstant;
import com.twocoms.rojgarkendra.global.model.CommonMethod;
import com.twocoms.rojgarkendra.global.model.ServiceHandler;
import com.twocoms.rojgarkendra.interviewscreen.controler.AppliedApplicationActivity;
import com.twocoms.rojgarkendra.interviewscreen.controler.JobInHandActivity;
import com.twocoms.rojgarkendra.interviewscreen.controler.UpcomingInterviewActivity;
import com.twocoms.rojgarkendra.interviewscreen.model.AppliedAndUpcommingModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JobInHandAdapter extends RecyclerView.Adapter<JobInHandAdapter.ViewHolder> {

    Context context;
    List<AppliedAndUpcommingModel> appliedAndUpcommingModels;

    public JobInHandAdapter(Context context, List<AppliedAndUpcommingModel> appliedAndUpcommingModels) {
        this.context = context;
        this.appliedAndUpcommingModels = appliedAndUpcommingModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View normalView = LayoutInflater.from(context).inflate(R.layout.item_job_in_hand, null);
        return new ViewHolder(normalView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.clientNameText.setText(appliedAndUpcommingModels.get(position).getClientName());
        holder.postedDateText.setText("Applied on : " + CommonMethod.parseDateToFormat(appliedAndUpcommingModels.get(position).getAppliedDate()));
        holder.jobType.setText(appliedAndUpcommingModels.get(position).getJobType());
        holder.location.setText(appliedAndUpcommingModels.get(position).getLocationOfWork());
        holder.numberOfOpenings.setText(appliedAndUpcommingModels.get(position).getNumberOfOpenPositions() + " openings");
        if (appliedAndUpcommingModels.get(position).getSalary().equals("null")) {
            holder.salary.setText("000");
        } else {
            holder.salary.setText(context.getResources().getString(R.string.Rs) + CommonMethod.roundNumbertoNextPossibleValue(appliedAndUpcommingModels.get(position).getSalary()));
        }
        if (position == appliedAndUpcommingModels.size() - 1) {
            if (context instanceof JobInHandActivity) {
                if (JobInHandActivity.currentPages == JobInHandActivity.numberOfPagesFromServer) {
                    Log.e("AllDataLoaded", "true");
                } else {
                    JobInHandActivity.currentPages = JobInHandActivity.currentPages + 1;
                    ((JobInHandActivity) context).getJobsInHands();
                }

            }

        }

        holder.vacancy_title_text.setText(appliedAndUpcommingModels.get(position).getVacancyTitle());

        holder.accept_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int applied_id = appliedAndUpcommingModels.get(position).getAppliedId();
                showAcceptrejectDialog(applied_id, true);
            }
        });

        holder.reject_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int applied_id = appliedAndUpcommingModels.get(position).getAppliedId();
                showAcceptrejectDialog(applied_id, false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appliedAndUpcommingModels.size();
    }

    public void setData(List<AppliedAndUpcommingModel> appliedAndUpcommingModels) {
        this.appliedAndUpcommingModels = appliedAndUpcommingModels;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView postedDateText, clientNameText, salary, jobType, numberOfOpenings, location, reject_job, accept_job,vacancy_title_text;

        ViewHolder(View itemView) {
            super(itemView);
            postedDateText = itemView.findViewById(R.id.date_job_posted);
            clientNameText = itemView.findViewById(R.id.client_text);
            salary = itemView.findViewById(R.id.salary_text);
            jobType = itemView.findViewById(R.id.job_type_text);
            numberOfOpenings = itemView.findViewById(R.id.vacancy_text);
            location = itemView.findViewById(R.id.location_text);
            reject_job = itemView.findViewById(R.id.reject_job);
            accept_job = itemView.findViewById(R.id.accept_job);
            vacancy_title_text = itemView.findViewById(R.id.vacancy_title_text);

        }

    }


    public void showAcceptrejectDialog(final int applied_id, final boolean acceptJob) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.accept_reject_offer_dialog);
        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        final TextInputEditText remartText = (TextInputEditText) dialog.findViewById(R.id.add_remark_text);
        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        Button ok = (Button) dialog.findViewById(R.id.ok);
        TextView headertext = (TextView) dialog.findViewById(R.id.headertext);
        if (acceptJob) {
            headertext.setText("Are you sure you want to accept offer?");
        } else {
            headertext.setText("Are you sure you want to reject offer?");
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (remartText.getText().toString().equals("")) {
                    remartText.setError("Please enter remarks.");
                } else {
                    if (acceptJob) {
                        acceptOrRejectoffer(applied_id, remartText.getText().toString(), "offer_accepected");
                    } else {
                        acceptOrRejectoffer(applied_id, remartText.getText().toString(), "offer_rejected");
                    }
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }


    public void acceptOrRejectoffer(int applied_id, String studentMsg, String status) {
        JSONObject json = new JSONObject();
        try {
            json.put(AppConstant.KEY_APPLIED_ID_REMARK, applied_id);
            json.put(AppConstant.KEY_APPLIED_STUDENT_REMARKS, studentMsg);
            json.put(AppConstant.KEY_APPLIED_AND_UPCOMING_INTEVIEW_STUDENT_STATUS, status);
            Log.v("Request", json.toString());

            ServiceHandler serviceHandler = new ServiceHandler(context);
            serviceHandler.StringRequest(Request.Method.POST, json.toString(), AppConstant.ADD_STUDENT_REMARK, true, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    Log.v("Response", result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String responseMsg = jsonObject.getString("message");

                        if (jsonObject.getBoolean("success")) {
                            if (context instanceof JobInHandActivity) {
                                JobInHandActivity.currentPages = 1;
                                JobInHandActivity.numberOfPagesFromServer = 0;
                                ((JobInHandActivity) context).getJobsInHands();
                            }
                        } else {
                            CommonMethod.showToast(responseMsg, context);
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
