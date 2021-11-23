package com.twocoms.rojgarkendra.successstoryscreen.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.global.model.CommonMethod;
import com.twocoms.rojgarkendra.interviewscreen.controler.AppliedApplicationActivity;
import com.twocoms.rojgarkendra.interviewscreen.model.AppliedAndUpcommingModel;
import com.twocoms.rojgarkendra.successstoryscreen.controler.SuccessStoriesActivity;
import com.twocoms.rojgarkendra.successstoryscreen.model.TestimonialsModel;

import java.util.List;

public class TestimonialsAdapter extends RecyclerView.Adapter<TestimonialsAdapter.ViewHolder> {

    Context context;
    List<TestimonialsModel> testimonialsModels;

    public TestimonialsAdapter(Context context, List<TestimonialsModel> testimonialsModels) {
        this.context = context;
        this.testimonialsModels = testimonialsModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View normalView = LayoutInflater.from(context).inflate(R.layout.item_testimonial, null);
        return new ViewHolder(normalView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.date_testimonials_date.setText("Posted on : "+CommonMethod.parseDateToFormat((testimonialsModels.get(position).getPostedOn())));
        holder.userName.setText(testimonialsModels.get(position).getUserName());
        holder.message.setText(testimonialsModels.get(position).getPostedMessage());

        if (!testimonialsModels.get(position).getProfileUrl().isEmpty()) {
            String profileUrl = testimonialsModels.get(position).getProfileUrl().trim();
            Glide.with(context)
                    .load(profileUrl)
                    .apply(new RequestOptions().placeholder(R.drawable.profile_main).error(R.drawable.profile_main))
                    .into(holder.userImage);

        }

        if (position == testimonialsModels.size() - 1) {
            if (context instanceof SuccessStoriesActivity) {
                if (SuccessStoriesActivity.currentPages == SuccessStoriesActivity.numberOfPagesFromServer) {
                    Log.e("AllDataLoaded", "true");
                } else {
                    SuccessStoriesActivity.currentPages = SuccessStoriesActivity.currentPages + 1;
                    ((SuccessStoriesActivity) context).getAllTestimonials();
                }

            }
        }




    }

    @Override
    public int getItemCount() {
        return testimonialsModels.size();
    }

    public void setData(List<TestimonialsModel> testimonialsModels) {
        this.testimonialsModels = testimonialsModels;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date_testimonials_date, message, userName;
        CircularImageView userImage;


        ViewHolder(View itemView) {
            super(itemView);
            date_testimonials_date = itemView.findViewById(R.id.date_testimonials_date);
            message = itemView.findViewById(R.id.message);
            userName = itemView.findViewById(R.id.userName);
            userImage = itemView.findViewById(R.id.userImage);
        }

    }


}
