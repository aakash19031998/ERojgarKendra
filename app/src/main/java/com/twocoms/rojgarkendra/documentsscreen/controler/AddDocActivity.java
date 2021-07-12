package com.twocoms.rojgarkendra.documentsscreen.controler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.global.model.AppConstant;
import com.twocoms.rojgarkendra.global.model.CommonMethod;

import java.io.File;

public class AddDocActivity extends AppCompatActivity {

    ImageView menuIcon,backIcon,homeIcon,profileIcon;
    TextView titleToolbar,selectedDocText;
    LinearLayout titleLnr;
    RelativeLayout selectDoc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doc);
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
        selectDoc = (RelativeLayout)findViewById(R.id.upload_doc_lnr);
        selectedDocText = (TextView)findViewById(R.id.upload_doc_txt);
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

        selectDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                intent.setType("application/pdf");
                startActivityForResult(Intent.createChooser(intent, "Select Pdf"), 1212);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1212:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String uriString = uri.toString();
                    File myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();
                    String displayName = null;

                    if (uriString.startsWith("content://")) {
                        if (uri.toString().contains(".pdf")) {
                            Cursor cursor = null;
                            try {
                                cursor = getContentResolver().query(uri, null, null, null, null);
                                if (cursor != null && cursor.moveToFirst()) {
                                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                    selectedDocText.setText(displayName);
                                }
                            } finally {
                                cursor.close();
                            }
                        }else {
                            CommonMethod.showToast("Please Select PDF",AddDocActivity.this);
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                        selectedDocText.setText(displayName);
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}