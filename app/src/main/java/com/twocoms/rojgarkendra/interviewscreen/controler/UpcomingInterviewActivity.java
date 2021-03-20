package com.twocoms.rojgarkendra.interviewscreen.controler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.global.model.AppConstant;

import org.w3c.dom.Text;

public class UpcomingInterviewActivity extends AppCompatActivity {

    ImageView menuIcon,backIcon,homeIcon,profileIcon;
    TextView titleToolbar;
    LinearLayout titleLnr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_interview);
        initialization();
        setToolbarVisibility();
        onClick();
    }

    void initialization(){
        menuIcon = (ImageView)findViewById(R.id.menu);
        backIcon = (ImageView)findViewById(R.id.backbutton);
        homeIcon = (ImageView)findViewById(R.id.home_img);
        profileIcon = (ImageView)findViewById(R.id.user_profile_img);
        titleToolbar =(TextView)findViewById(R.id.title);
        titleLnr =(LinearLayout)findViewById(R.id.title_lnr);
        titleToolbar.setText(AppConstant.NAME_UPCOMING_INTERVIEW);
    }

    void setToolbarVisibility(){
        menuIcon.setVisibility(View.GONE);
        backIcon.setVisibility(View.VISIBLE);
        homeIcon.setVisibility(View.VISIBLE);
        profileIcon.setVisibility(View.GONE);
    }

    void onClick(){
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        titleLnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}