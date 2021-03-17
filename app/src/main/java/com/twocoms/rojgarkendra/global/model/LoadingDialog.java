package com.twocoms.rojgarkendra.global.model;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.twocoms.rojgarkendra.R;


public class LoadingDialog extends Dialog {

    String message;
    Context context;

    /*  ProgressBar customProgressBar;*/

    public LoadingDialog(Context context) {
        super(context);
        this.context = context;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        setContentView(R.layout.transparent_progress_layout);
        TextView textView = findViewById(R.id.progressMessageTxt);
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.loading_anim);
        anim.setInterpolator(context, android.R.anim.linear_interpolator);
        ProgressBar prog = findViewById(R.id.progressBar);
        prog.startAnimation(anim);
        anim.setDuration(2000);
        prog.startAnimation(anim);
        //((Activity) prog).getWindow().new ColorDrawable(android.graphics.Color.TRANSPARENT)
        setCancelable(false);
        getWindow().setWindowAnimations(
                R.style.dialog_animation_fade);
        setCanceledOnTouchOutside(false);
    }

  /*  public void SetTitleMessage(String MessageTitle){
        ((TextView)findViewById(R.id.customProgressBar_message)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.customProgressBar_message)).setText(MessageTitle);
    }*/

    /*private void MapWidgetIds() {
        customProgressBar = (ProgressBar) findViewById(R.id.customProgressBar);
    }*/


}