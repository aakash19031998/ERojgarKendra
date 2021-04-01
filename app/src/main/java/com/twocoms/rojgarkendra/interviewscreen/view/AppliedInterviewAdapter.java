package com.twocoms.rojgarkendra.interviewscreen.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.global.model.CommonMethod;
import com.twocoms.rojgarkendra.interviewscreen.controler.AppliedApplicationActivity;
import com.twocoms.rojgarkendra.interviewscreen.model.AppliedAndUpcommingModel;
import com.twocoms.rojgarkendra.jobboardscreen.view.AllJobsAdapter;

import java.util.List;

public class AppliedInterviewAdapter extends RecyclerView.Adapter<AppliedInterviewAdapter.ViewHolder> {

    Context context;
    List<AppliedAndUpcommingModel> appliedAndUpcommingModels;

    public AppliedInterviewAdapter(Context context, List<AppliedAndUpcommingModel> appliedAndUpcommingModels) {
        this.context = context;
        this.appliedAndUpcommingModels = appliedAndUpcommingModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View normalView = LayoutInflater.from(context).inflate(R.layout.item_applied_interview, null);
        return new ViewHolder(normalView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.clientNameText.setText(appliedAndUpcommingModels.get(position).getClientName());
        holder.postedDateText.setText("Applied on : " + CommonMethod.parseDateToFormat(appliedAndUpcommingModels.get(position).getAppliedDate()));
        holder.jobType.setText(appliedAndUpcommingModels.get(position).getJobType());
        holder.location.setText(appliedAndUpcommingModels.get(position).getLocationOfWork());
        holder.numberOfOpenings.setText(appliedAndUpcommingModels.get(position).getNumberOfOpenPositions() + " openings");
        holder.salary.setText(context.getResources().getString(R.string.Rs) + CommonMethod.roundNumbertoNextPossibleValue(appliedAndUpcommingModels.get(position).getSalary()));
        if (position == appliedAndUpcommingModels.size() - 1) {
            if (context instanceof AppliedApplicationActivity) {
                if (AppliedApplicationActivity.currentPages == AppliedApplicationActivity.numberOfPagesFromServer) {
                    Log.e("AllDataLoaded", "true");
                } else {

                    AppliedApplicationActivity.currentPages = AppliedApplicationActivity.currentPages + 1;
                    ((AppliedApplicationActivity) context).getAppliedInterview();
                }

            }

        }
    }

    @Override
    public int getItemCount() {
        return appliedAndUpcommingModels.size();
    }

    public void setData(List<AppliedAndUpcommingModel> appliedAndUpcommingModels) {
        this.appliedAndUpcommingModels = appliedAndUpcommingModels;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView postedDateText, clientNameText, salary, jobType, numberOfOpenings, location;


        ViewHolder(View itemView) {
            super(itemView);
            postedDateText = itemView.findViewById(R.id.date_job_posted);
            clientNameText = itemView.findViewById(R.id.client_text);
            salary = itemView.findViewById(R.id.salary_text);
            jobType = itemView.findViewById(R.id.job_type_text);
            numberOfOpenings = itemView.findViewById(R.id.vacancy_text);
            location = itemView.findViewById(R.id.location_text);
        }

    }


}
