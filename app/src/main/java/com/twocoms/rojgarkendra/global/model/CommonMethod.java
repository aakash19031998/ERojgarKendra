package com.twocoms.rojgarkendra.global.model;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.dashboardscreen.controler.DashboardActivity;
import com.twocoms.rojgarkendra.registrationscreen.controler.GetStartedActivity;
import com.twocoms.rojgarkendra.registrationscreen.controler.RegisterUserDataActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * Created by S.V. on 7/30/2017.
 */

public class CommonMethod {


    public static void showToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static ProgressDialog showProgressDialog(Context context, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }


    public static Typeface createTypefaceFromAssets(Context context, String typefacename) {
        return Typeface.createFromAsset(context.getAssets(), typefacename);
    }


    public static boolean isOnline(Context context) {
        boolean connected = false;

        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            return connected;


        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());
        }
        return connected;
    }


    void getFCMToken(final Context context) {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Token", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        //String msg = getString(R.string.token_id, token);
                        Log.d("Token", token);
                        Toast.makeText(context, token, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {

                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }

            }
        }

        return degree;
    }

    public static String saveimagetosdcard(Context ctx, Bitmap photo) {


        FileOutputStream output;
        File file;
        file = getOutputMediaFile();
        try {

            output = new FileOutputStream(getOutputMediaFile());


            photo.compress(Bitmap.CompressFormat.PNG, 100, output);
            output.flush();
            output.close();
            /*
             * Toast.makeText(ctx,
             * "Image Saved to "+getOutputMediaFile().getAbsolutePath(),
             * Toast.LENGTH_SHORT) .show()
             */
            ;
        } catch (Exception e) {
            Toast.makeText(ctx, "Try Again.", Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    public static File getOutputMediaFile() {


        File mediaStorageDir = Environment.getExternalStorageDirectory();


        File dir = new File(mediaStorageDir.getAbsolutePath() + "/Purlieu/");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());

        File mediaFile;

        mediaFile = new File(dir.getPath() + File.separator + "IMG_"
                + timeStamp + ".png");

        return mediaFile;
    }


    public static void showDialogueForLoginSignUp(final Activity context, String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(message);
        builder1.setTitle(context.getString(R.string.app_name));
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        if (CommonMethod.checkUserVerifiedOrNot(context)) {
                            Intent intent = new Intent(context, RegisterUserDataActivity.class);
                            context.startActivity(intent);
                        } else {
                            CommonMethod.openGetStartedActivity(context);
                        }
                        context.finish();
                    }
                });

        builder1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }




    public static boolean checkUserLoggedInOrRegister(Context context) {
        boolean isRegUser = false;
        if (GlobalPreferenceManager.getStringForKey(context, AppConstant.KEY_CONTACT_VERIFIED, "").equals("1") &&
                GlobalPreferenceManager.getStringForKey(context, AppConstant.KEY_IS_REGISTER, "").equals("Y")) {
            isRegUser = true;
        }
        return isRegUser;
    }


    public static boolean checkUserVerifiedOrNot(Context context) {
        boolean isVerified = false;
        if (GlobalPreferenceManager.getStringForKey(context, AppConstant.KEY_CONTACT_VERIFIED, "").equals("1")) {
            isVerified = true;
        }
        return isVerified;
    }


    public static void openGetStartedActivity(Context context) {
        Intent intent = new Intent(context, GetStartedActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }


    public static void openDashBoardActivity(Context context) {
        Intent intent = new Intent(context, DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }


    public static String roundNumbertoNextPossibleValue(String value) {
        double valueToBeRoundedOff = Double.parseDouble(value);
        int a = (int) Math.ceil(valueToBeRoundedOff);
        return "" + a;

    }


    public static String parseDateToFormat(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "MMMM d, yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = "";

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static boolean checkTodaysDate(String time) {
        boolean checkTodaysDate = false;
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        Date date = null;

        try {
            date = inputFormat.parse(time);
            checkTodaysDate = DateUtils.isToday(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return checkTodaysDate;
    }


    public static void clearAllFilterData(Context context) {
        GlobalPreferenceManager.saveStringForKey(context, AppConstant.KEY_FILTER_LANGUAGE_ALL_JOBS, "");
        GlobalPreferenceManager.saveStringForKey(context, AppConstant.KEY_FILTER_QUALIFICATION_TYPE_ALL_JOBS, "");
        GlobalPreferenceManager.saveStringForKey(context, AppConstant.KEY_FILTER_CITY_ALL_JOBS, "");
        GlobalPreferenceManager.saveStringForKey(context, AppConstant.KEY_FILTER_GENDER_ALL_JOBS, "");
        GlobalPreferenceManager.saveStringForKey(context, AppConstant.KEY_FILTER_SKILLS_ALL_JOBS, "");
    }

    public static void clearAllFilterDataHotJobs(Context context) {
        GlobalPreferenceManager.saveStringForKey(context, AppConstant.KEY_FILTER_LANGUAGE_HOT_JOBS, "");
        GlobalPreferenceManager.saveStringForKey(context, AppConstant.KEY_FILTER_QUALIFICATION_TYPE_HOT_JOBS, "");
        GlobalPreferenceManager.saveStringForKey(context, AppConstant.KEY_FILTER_CITY_HOT_JOBS, "");
        GlobalPreferenceManager.saveStringForKey(context, AppConstant.KEY_FILTER_GENDER_HOT_JOBS, "");
        GlobalPreferenceManager.saveStringForKey(context, AppConstant.KEY_FILTER_SKILLS_HOT_JOBS, "");
    }

    public static void clearAllFilterDataPopularJobs(Context context) {
        GlobalPreferenceManager.saveStringForKey(context, AppConstant.KEY_FILTER_LANGUAGE_POPULAR_JOBS, "");
        GlobalPreferenceManager.saveStringForKey(context, AppConstant.KEY_FILTER_QUALIFICATION_TYPE_POPULAR_JOBS, "");
        GlobalPreferenceManager.saveStringForKey(context, AppConstant.KEY_FILTER_CITY_POPULAR_JOBS, "");
        GlobalPreferenceManager.saveStringForKey(context, AppConstant.KEY_FILTER_GENDER_POPULAR_JOBS, "");
        GlobalPreferenceManager.saveStringForKey(context, AppConstant.KEY_FILTER_SKILLS_POPULAR_JOBS, "");
    }

    public static void clearAllFilterDataMatchingVacancies(Context context) {
        GlobalPreferenceManager.saveStringForKey(context, AppConstant.KEY_FILTER_LANGUAGE_MATCHING_JOBS, "");
        GlobalPreferenceManager.saveStringForKey(context, AppConstant.KEY_FILTER_QUALIFICATION_TYPE_MATCHING_JOBS, "");
        GlobalPreferenceManager.saveStringForKey(context, AppConstant.KEY_FILTER_CITY_MATCHING_JOBS, "");
        GlobalPreferenceManager.saveStringForKey(context, AppConstant.KEY_FILTER_GENDER_MATCHING_JOBS, "");
        GlobalPreferenceManager.saveStringForKey(context, AppConstant.KEY_FILTER_SKILLS_MATCHING_JOBS, "");
    }

    public static void clearAllFilterDataJonAppliedByYourBatchMates(Context context) {
        GlobalPreferenceManager.saveStringForKey(context, AppConstant.KEY_FILTER_LANGUAGE_BATCH_MATES_JOBS, "");
        GlobalPreferenceManager.saveStringForKey(context, AppConstant.KEY_FILTER_QUALIFICATION_TYPE_BATCH_MATES_JOBS, "");
        GlobalPreferenceManager.saveStringForKey(context, AppConstant.KEY_FILTER_CITY_BATCH_MATES_JOBS, "");
        GlobalPreferenceManager.saveStringForKey(context, AppConstant.KEY_FILTER_GENDER_BATCH_MATES_JOBS, "");
        GlobalPreferenceManager.saveStringForKey(context, AppConstant.KEY_FILTER_SKILLS_BATCH_MATES_JOBS, "");
    }

    public static void getTokenForMobile(final Context context) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, AppConstant.GET_TOKEN_CREATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                               String token = jsonObject.getString("data");
                                GlobalPreferenceManager.saveStringForKey(context, AppConstant.KEY_TOKEN_MAIN, token);
                            }
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
                    }
                }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", "api@eduerp.com");
                params.put("password", "admin@1234");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjRequest);
    }


}
