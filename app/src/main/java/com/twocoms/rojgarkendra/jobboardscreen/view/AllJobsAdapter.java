package com.twocoms.rojgarkendra.jobboardscreen.view;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.dashboardscreen.controler.DashboardActivity;
import com.twocoms.rojgarkendra.global.model.AppConstant;
import com.twocoms.rojgarkendra.global.model.CommonMethod;
import com.twocoms.rojgarkendra.global.model.GlobalPreferenceManager;
import com.twocoms.rojgarkendra.jobboardscreen.controler.FrgAllJobs;
import com.twocoms.rojgarkendra.jobboardscreen.controler.JobDetailActivity;
import com.twocoms.rojgarkendra.jobboardscreen.model.ModelHotJobs;

import java.util.ArrayList;

public class AllJobsAdapter extends RecyclerView.Adapter<AllJobsAdapter.ViewHolder> {

    public Context context;
    public ArrayList<ModelHotJobs> modelHotJobs;
    FrgAllJobs frgAllJobs;


    public AllJobsAdapter(Context context, ArrayList<ModelHotJobs> modelHotJobs, FrgAllJobs frgAllJobs) {
        this.context = context;
        this.modelHotJobs = modelHotJobs;
        this.frgAllJobs = frgAllJobs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View normalView = LayoutInflater.from(context).inflate(R.layout.items_hot_jobs, null);
        return new ViewHolder(normalView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        ModelHotJobs hotJobsModels = modelHotJobs.get(position);
        holder.salaryText.setText(context.getResources().getString(R.string.Rs) + " " + CommonMethod.roundNumbertoNextPossibleValue(hotJobsModels.getSalary()));
        holder.jobText.setText(hotJobsModels.getJobTypes());
        holder.clientText.setText(hotJobsModels.getClientName());
        holder.locationText.setText(hotJobsModels.getLocation());
        holder.vacancyText.setText(hotJobsModels.getNumberOpenings() + " openings");
        if (CommonMethod.checkTodaysDate(hotJobsModels.getDates())) {
            holder.dateText.setText("Posted Today");
        } else {
            holder.dateText.setText("Posted on : " + CommonMethod.parseDateToFormat(hotJobsModels.getDates()));
        }


        holder.viewJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, JobDetailActivity.class);
                intent.putExtra("jobId", modelHotJobs.get(position).getId() + "");
                context.startActivity(intent);
            }
        });

        holder.applyJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CommonMethod.checkUserLoggedInOrRegister(context)) {
//                    CommonMethod.showToast("Job Applied", context);
                    String userIdSTr = GlobalPreferenceManager.getStringForKey(context, AppConstant.KEY_USER_ID, "");
                    int vacancyId = modelHotJobs.get(position).getId();
                    frgAllJobs.applyAllJobs(userIdSTr, vacancyId);

                } else {
                    CommonMethod.showDialogueForLoginSignUp(frgAllJobs.getActivity(), AppConstant.SIGN_UP_LOGIN_TEXT);
                }
            }
        });

        if (position == modelHotJobs.size() - 1) {
            if (frgAllJobs.currentPages == frgAllJobs.numberOfPagesFromServer) {
                Log.e("AllDataLoaded", "true");
//                paginationProgress.setVisibility(View.GONE);
            } else {
//                paginationProgress.setVisibility(View.VISIBLE);
                // newsAdapter.notifyDataSetChanged();
                frgAllJobs.currentPages = frgAllJobs.currentPages + 1;
                frgAllJobs.getAllJobsData();
//                getNews(currentPages + 1);
                // searchGetNews(currentPages + 1);
            }
        }


    }


    @Override
    public int getItemCount() {
        return modelHotJobs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView salaryText, jobText, clientText, locationText, vacancyText, dateText;
        Button viewJobBtn, applyJobBtn;

        ViewHolder(View itemView) {
            super(itemView);
            salaryText = itemView.findViewById(R.id.salary_text);
            jobText = itemView.findViewById(R.id.job_type_text);
            clientText = itemView.findViewById(R.id.client_text);
            locationText = itemView.findViewById(R.id.location_text);
            vacancyText = itemView.findViewById(R.id.vacancy_text);
            dateText = itemView.findViewById(R.id.date_job_posted);
            viewJobBtn = itemView.findViewById(R.id.view_jobs);
            applyJobBtn = itemView.findViewById(R.id.apply_jobs);

        }

    }

    public void setData(ArrayList<ModelHotJobs> allHotJobs) {
        this.modelHotJobs = allHotJobs;
    }
}
