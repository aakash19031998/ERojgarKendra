package com.twocoms.rojgarkendra.documentsscreen.controler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.global.controler.VolleyMultipartRequest;
import com.twocoms.rojgarkendra.global.controler.VolleySingleton;
import com.twocoms.rojgarkendra.global.model.AppConstant;
import com.twocoms.rojgarkendra.global.model.CommonMethod;
import com.twocoms.rojgarkendra.global.model.GlobalPreferenceManager;
import com.twocoms.rojgarkendra.global.model.LoadingDialog;
import com.twocoms.rojgarkendra.global.model.ServiceHandler;
import com.twocoms.rojgarkendra.jobboardscreen.controler.JobDetailActivity;
import com.twocoms.rojgarkendra.jobboardscreen.model.VacancyDetailModel;
import com.twocoms.rojgarkendra.registrationscreen.controler.RegisterUserDataActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class AddDocActivity extends AppCompatActivity {

    ImageView menuIcon,backIcon,homeIcon,profileIcon;
    TextView titleToolbar,selectedDocText;
    LinearLayout titleLnr;
    RelativeLayout selectDoc;
    private LoadingDialog loadingDialog;

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


    private void uploadDocument(final Context context, final JSONObject jsonObject) {

        loadingDialog = new LoadingDialog(context);
        loadingDialog.show();
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, AppConstant.CREATE_USER, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    Log.v("response", resultResponse);
                    JSONObject jsonObject = new JSONObject(resultResponse);
                    //   JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getBoolean("success")) {
                        JSONObject dataStr = jsonObject.getJSONObject("data");
                        dataStr.put(AppConstant.KEY_IS_REGISTER, "Y");
                        loadingDialog.dismiss();
                    } else {
                        loadingDialog.dismiss();
                        String msgStr = jsonObject.getString("message");
                        CommonMethod.showToast(msgStr, AddDocActivity.this);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");

                        Log.e("Error Status", status);
                        Log.e("Error Message", message);

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message + " Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message + " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message + " Something is getting wrong";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Error", errorMessage);
                loadingDialog.dismiss();
                CommonMethod.showToast(errorMessage, AddDocActivity.this);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Gson gson = new Gson();
                Type type = new TypeToken<Map<String, String>>() {
                }.getType();
                Map<String, String> myMap = gson.fromJson(jsonObject.toString(), type);
                return myMap;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String tokenMain = GlobalPreferenceManager.getStringForKey(context, AppConstant.KEY_TOKEN_MAIN, "");
                headers.put("Authorization", "Bearer " + tokenMain);
                return headers;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                return params;
            }
        };
        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);
    }

}