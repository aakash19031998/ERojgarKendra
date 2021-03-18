package com.twocoms.rojgarkendra.registrationscreen.controler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.dashboardscreen.controler.DashboardActivity;
import com.twocoms.rojgarkendra.global.model.AppConstant;
import com.twocoms.rojgarkendra.global.model.GlobalPreferenceManager;
import com.twocoms.rojgarkendra.registrationscreen.model.SliderItemModel;
import com.twocoms.rojgarkendra.registrationscreen.view.SliderApdater;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GetStartedActivity extends AppCompatActivity {

    ViewPager viewPager1;
    CirclePageIndicator indicator1;
    List<SliderItemModel> sliderItemModels = new ArrayList<>();
    SliderApdater sliderApdater;
    TextView getStartBtn, skip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        viewPager1 = (ViewPager)findViewById(R.id.viewPager);
        indicator1 = (CirclePageIndicator)findViewById(R.id.indicator);
        getStartBtn =(TextView)findViewById(R.id.get_start_text);
        skip =(TextView)findViewById(R.id.skip_text);

        SliderItemModel item1 = new SliderItemModel();
        item1.setImg(R.drawable.icon_slide1);
        item1.setText("Get the Right Job");
        item1.setText1("");
        sliderItemModels.add(item1);


        SliderItemModel item2 = new SliderItemModel();
        item2.setImg(R.drawable.icon_slide2);
        item2.setText("Jobs according to");
        item2.setText1("Yous Skills");
        sliderItemModels.add(item2);

        SliderItemModel item3 = new SliderItemModel();
        item3.setImg(R.drawable.icon_slide3);
        item3.setText("Get placement opportunities");
        item3.setText1("locally and nationally");
        sliderItemModels.add(item3);

        SliderItemModel item4 = new SliderItemModel();
        item4.setImg(R.drawable.icon_slide4);
        item4.setText("Go ahead with PLACEME");
        item4.setText1("");
        sliderItemModels.add(item4);

        sliderApdater = new SliderApdater(GetStartedActivity.this, sliderItemModels);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer1(), 4000, 6000);

        viewPager1.setAdapter(sliderApdater);
        indicator1.setViewPager(viewPager1);

        getStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToActivity();
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToActivity();
            }
        });
    }

    private class SliderTimer1 extends TimerTask {

        @Override
        public void run() {
            GetStartedActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager1.getCurrentItem() < sliderItemModels.size() - 1) {
                        viewPager1.setCurrentItem(viewPager1.getCurrentItem() + 1);
                    } else {
                        viewPager1.setCurrentItem(0);
                    }
                }
            });
        }
    }

    void navigateToActivity(){

            Intent mainIntent = new Intent(GetStartedActivity.this, VerifyMobileNumberActivity.class);
            startActivity(mainIntent);
            finish();

    }
}