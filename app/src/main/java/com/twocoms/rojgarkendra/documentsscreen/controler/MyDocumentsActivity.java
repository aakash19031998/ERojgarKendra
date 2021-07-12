package com.twocoms.rojgarkendra.documentsscreen.controler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.twocoms.rojgarkendra.R;
//import com.twocoms.rojgarkendra.documentsscreen.view.TabPagerAdapter;
import com.twocoms.rojgarkendra.global.model.AppConstant;

public class MyDocumentsActivity extends AppCompatActivity {

    ImageView menuIcon,backIcon,homeIcon,profileIcon;
    TextView titleToolbar;
    LinearLayout titleLnr;
    TabLayout tabLayout;
    ViewPager viewPager;
//    TabPagerAdapter tabPagerAdapter;
    RecyclerView docRecyclerView;
    FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_my_doc);
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
        titleToolbar.setText(AppConstant.NAME_MY_DOCUMENTS);
        docRecyclerView = (RecyclerView)findViewById(R.id.recycler_doc);
        addButton = (FloatingActionButton) findViewById(R.id.add_doc);

       /* tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.tab_pager);
        setTab();
        tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabPagerAdapter);
        //tabLayout.setOnTabSelectedListener(this);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/


    }

    void setTab(){
        tabLayout.addTab(tabLayout.newTab().setText("Pre Onbooarding Documents"));
        tabLayout.addTab(tabLayout.newTab().setText("Post Onbooarding Documents"));
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

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyDocumentsActivity.this,AddDocActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}