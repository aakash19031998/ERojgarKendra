package com.twocoms.rojgarkendra.registrationscreen.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.registrationscreen.model.SliderItemModel;

import java.util.List;

public class SliderApdater extends PagerAdapter {

    Context context;
    List<SliderItemModel> sliderBottomModels;

    public SliderApdater(Context context, List<SliderItemModel> sliderBottomModels) {
        this.context = context;
        this.sliderBottomModels = sliderBottomModels;
    }

    @Override
    public int getCount() {
        return sliderBottomModels.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.frg_common_slider, null);

        ImageView img = (ImageView) view.findViewById(R.id.image);
        TextView textView = (TextView) view.findViewById(R.id.text1);
        TextView textView2 = (TextView) view.findViewById(R.id.text2);
        img.setImageResource(sliderBottomModels.get(position).getImg());
        if (sliderBottomModels.get(position).getText1().equals("") || sliderBottomModels.get(position).getText1() == null){
            textView2.setVisibility(View.GONE);
        }
        textView.setText(sliderBottomModels.get(position).getText());
        textView2.setText(sliderBottomModels.get(position).getText1());
        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}
