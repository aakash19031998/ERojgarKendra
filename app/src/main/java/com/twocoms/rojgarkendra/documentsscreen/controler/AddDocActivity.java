package com.twocoms.rojgarkendra.documentsscreen.controler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.global.controler.VolleyMultipartRequest;
import com.twocoms.rojgarkendra.global.controler.VolleySingleton;
import com.twocoms.rojgarkendra.global.model.AppConstant;
import com.twocoms.rojgarkendra.global.model.CommonMethod;
import com.twocoms.rojgarkendra.global.model.GlobalPreferenceManager;
import com.twocoms.rojgarkendra.global.model.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;


public class AddDocActivity extends AppCompatActivity {

    ImageView menuIcon, backIcon, homeIcon, profileIcon;
    TextView titleToolbar, selectedDocText, uploadbutton;
    LinearLayout titleLnr;
    RelativeLayout selectDoc;
    private LoadingDialog loadingDialog;
    String filePath = "";
    Uri uri;
    String paySlipMonth = "";
    String paySlipYear = "";
    TextInputEditText doc_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doc);
        initialization();
        setToolbarVisibility();
        onClick();
        paySlipMonth = getIntent().getStringExtra("payMonth");
        paySlipYear = getIntent().getStringExtra("payYear");
        doc_type.setText(paySlipMonth + " " + paySlipYear);
    }

    void initialization() {
        menuIcon = (ImageView) findViewById(R.id.menu);
        backIcon = (ImageView) findViewById(R.id.backbutton);
        homeIcon = (ImageView) findViewById(R.id.home_img);
        profileIcon = (ImageView) findViewById(R.id.user_profile_img);
        titleToolbar = (TextView) findViewById(R.id.title);
        titleLnr = (LinearLayout) findViewById(R.id.title_lnr);
        titleToolbar.setText(AppConstant.NAME_MY_DOCUMENTS);
        selectDoc = (RelativeLayout) findViewById(R.id.upload_doc_lnr);
        selectedDocText = (TextView) findViewById(R.id.upload_doc_txt);
        uploadbutton = (TextView) findViewById(R.id.uploadbutton);
        doc_type = findViewById(R.id.doc_type);

    }

    void setToolbarVisibility() {
        menuIcon.setVisibility(View.GONE);
        backIcon.setVisibility(View.VISIBLE);
        homeIcon.setVisibility(View.VISIBLE);
        profileIcon.setVisibility(View.GONE);
    }

    void onClick() {
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
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
//                intent.setType("application/pdf");
//                startActivityForResult(Intent.createChooser(intent, "Select Pdf"), 1212);
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                intent.setType("application/pdf");
//                startActivityForResult(intent, 1);
                String[] mimeTypes = {"image/*", "application/pdf"};
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
                        if (mimeTypes.length > 0) {
                            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                        }
                    } else {
                        String mimeTypesStr = "";
                        for (String mimeType : mimeTypes) {
                            mimeTypesStr += mimeType + "|";
                        }
                        intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
                    }
                startActivityForResult(intent, 1);
            }
        });

        uploadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uri != null && !filePath.equals("")) {
                    uploadPDF(filePath, uri, upldocJson(paySlipMonth));
                } else {
                    CommonMethod.showToast("Please select document to upload", AddDocActivity.this);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // Get the Uri of the selected file
            Uri uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            String displayName = null;

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = this.getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        Log.d("nameeeee>>>>  ", displayName);
                        selectedDocText.setText(displayName);
                        filePath = displayName;
                        this.uri = uri;
                        //  uploadPDF(displayName,uri);
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
                filePath = displayName;
                Log.d("nameeeee>>>>  ", displayName);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private JSONObject upldocJson(String paySlipMonth) {
        JSONObject json = new JSONObject();
        try {
            json.put("user_id", GlobalPreferenceManager.getStringForKey(AddDocActivity.this, AppConstant.KEY_USER_ID, ""));
            json.put("payslip_month", paySlipMonth);
            Log.v("JSON", json.toString());
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        return json;
    }


    private void uploadPDF(final String pdfname, Uri pdffile, final JSONObject jsonObject) {
        loadingDialog = new LoadingDialog(AddDocActivity.this);
        loadingDialog.show();
        InputStream iStream = null;
        try {
            iStream = getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);
            Log.v("URL", AppConstant.UPLOAD_DOCUMENTS);
            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, AppConstant.UPLOAD_DOCUMENTS,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
                            try {
                                JSONObject jsonObject = new JSONObject(new String(response.data));
                                Log.v("Response", jsonObject.toString());
                                if (jsonObject.getBoolean("success")) {
                                    CommonMethod.showToast("Document Uploaded Successfully", AddDocActivity.this);
                                    loadingDialog.dismiss();
                                    Intent data = new Intent();
                                    data.putExtra("documentuplodedSucess","true");
                                    setResult(RESULT_OK,data);
                                    finish();

                                } else {
                                    //loadingDialog.dismiss();
                                    String msgStr = jsonObject.getString("message");
                                    CommonMethod.showToast(msgStr, AddDocActivity.this);
                                }

                            } catch (JSONException e ) {
                                loadingDialog.dismiss();
                                CommonMethod.showToast(AppConstant.SOMETHING_WENT_WRONG, AddDocActivity.this);
                                e.printStackTrace();
                            }
                            catch (Exception e ) {
                                loadingDialog.dismiss();
                                CommonMethod.showToast(AppConstant.SOMETHING_WENT_WRONG, AddDocActivity.this);
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loadingDialog.dismiss();
                            byte[] data = error.networkResponse.data;

//                    byte[] bytes = "hello world".getBytes();
//creates a string from the byte array without specifying character encoding
                            String s = new String(data);

                            Log.v("NetworkResponse", s);

                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }) {

                // @Override
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
                    String tokenMain = GlobalPreferenceManager.getStringForKey(AddDocActivity.this, AppConstant.KEY_TOKEN_MAIN, "");
                    headers.put("Authorization", "Bearer " + tokenMain);
                    return headers;
                }

                /*
                 *pass files using below method
                 * */
                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();
                    params.put("payslip", new DataPart(pdfname, inputData));
                    return params;
                }
            };


//            volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
//                    0,
//                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(volleyMultipartRequest);
        } catch (FileNotFoundException e) {
            loadingDialog.dismiss();
            CommonMethod.showToast(AppConstant.SOMETHING_WENT_WRONG, AddDocActivity.this);
            e.printStackTrace();
        } catch (IOException e) {
            loadingDialog.dismiss();
            CommonMethod.showToast(AppConstant.SOMETHING_WENT_WRONG, AddDocActivity.this);
            e.printStackTrace();
        }


    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        try {
            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
            return byteBuffer.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}