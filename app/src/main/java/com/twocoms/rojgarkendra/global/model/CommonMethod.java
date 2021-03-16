package com.twocoms.rojgarkendra.global.model;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.twocoms.rojgarkendra.registrationscreen.controler.RegisterUserDataActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


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


//    public static Dialog progressDialogue(Context context, String message) {
//        Dialog pDialog = new Dialog(context, R.style.myDialogTheme);
//        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        pDialog.setContentView(R.layout.dialogue_progress);
//        ((TextView) pDialog.findViewById(R.id.progressMessageTxt)).setText(message);
//        Animation anim = AnimationUtils.loadAnimation(context, R.anim.loading_anim);
//        anim.setInterpolator(context, android.R.anim.linear_interpolator);
//        ProgressBar prog = pDialog.findViewById(R.id.progressBar);
//        prog.startAnimation(anim);
//        anim.setDuration(2000);
//        prog.startAnimation(anim);
//        //((Activity) prog).getWindow().new ColorDrawable(android.graphics.Color.TRANSPARENT)
//        pDialog.setCancelable(false);
//
//        pDialog.getWindow().setWindowAnimations(
//                R.style.dialog_animation_fade);
//
//        return pDialog;
//    }

//    public static void showInlineImage(Context context, String url) {
//        try {
//            final Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.setContentView(R.layout.dailogue_add_image);
//            TextView cancel =  dialog.findViewById(R.id.closebutton);
//            PhotoView addImage = dialog.findViewById(R.id.imageview);
//            Glide.with(context)
//                    .load(url)
//                    .into(addImage);
//            cancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });
//            dialog.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static int getPdfFileName(int position) {
//        int id = 0;
//        if (position == 0) {
//            id = R.raw.doc1;
//        } else if (position == 1) {
//            id = R.raw.doc2;
//        } else if (position == 2) {
//            id = R.raw.doc3;
//        } else if (position == 3) {
//            id = R.raw.doc4;
//        } else if (position == 4) {
//            id = R.raw.doc5;
//        } else if (position == 5) {
//            id = R.raw.doc6;
//        } else if (position == 6) {
//            id = R.raw.doc7;
//        } else if (position == 7) {
//            // id = R.raw.doc8;
//            id=0;
//        } else if (position == 8) {
//            // id = R.raw.doc9;
//            id = 0;
//
//        } else if (position == 9) {
//            id = R.raw.doc10;
//        } else if (position == 10) {
//            // id = R.raw.doc11;
//            id = 0;
//        } else if (position == 11) {
//            id = R.raw.doc12;
//        } else if (position == 12) {
//            id = R.raw.doc13;
//        } else if (position == 13) {
//            id = R.raw.doc14;
//        } else if (position == 14) {
//            // id = R.raw.doc15;
//            id = 0;
//        } else if (position == 15) {
//            id = R.raw.doc16;
//        } else if (position == 16) {
//            id = R.raw.doc17;
//        } else if (position == 17) {
//            id = R.raw.doc18;
//        } else if (position == 18) {
//            id = R.raw.doc19;
//        } else if (position == 19) {
//            id = R.raw.doc20;
//        } else if (position == 20) {
////            id = R.raw.doc21;
//            id = 0;
//        } else if (position == 21) {
//            id = R.raw.doc22;
//        } else if (position == 22) {
//            id = R.raw.doc23;
//        } else if (position == 23) {
//            id = R.raw.doc24;
//        } else if (position == 24) {
//            id = R.raw.doc25;
//        } else if (position == 25) {
//            id = R.raw.doc26;
//        } else if (position == 26) {
//            id = R.raw.doc27;
//        } else if (position == 27) {
//            id = R.raw.doc28;
//        } else if (position == 28) {
//            id = R.raw.doc29;
//        }
//        return id;
//    }
//
//
//    public static int getPdfFileNameDosAndDonts(int position) {
//        int id = 0;
//        if (position == 0) {
//            id = R.raw.dont1;
//        } else if (position == 1) {
//            id = R.raw.dont2;
//        } else if (position == 2) {
//            id = R.raw.dont3;
//        } else if (position == 3) {
//            id = R.raw.dont4;
//        } else if (position == 4) {
//            id = R.raw.dont5;
//        } else if (position == 5) {
//            id = R.raw.dont6;
//        } else if (position == 6) {
//            id = R.raw.dont7;
//        } else if (position == 7) {
//            id = R.raw.dont8;
//        } else if (position == 8) {
//            id = R.raw.dont9;
////            id = 0;
//
//        } else if (position == 9) {
//            id = R.raw.dont10;
//        } else if (position == 10) {
//            id = R.raw.dont11;
//            id = 0;
//        } else if (position == 11) {
//            id = R.raw.dont12;
//        } else if (position == 12) {
//            id = R.raw.dont13;
//        } else if (position == 13) {
//            id = R.raw.dont14;
//        } else if (position == 14) {
//            id = R.raw.dont15;
//
//        } else if (position == 15) {
//            id = R.raw.dont16;
//        } else if (position == 16) {
//            id = R.raw.dont17;
//        } else if (position == 17) {
//            id = R.raw.dont18;
//        } else if (position == 18) {
//            id = R.raw.dont19;
//        } else if (position == 19) {
//            id = R.raw.dont20;
//        } else if (position == 20) {
//            id = R.raw.dont21;
//        } else if (position == 21) {
//            id = R.raw.dont22;
//        } else if (position == 22) {
//            id = R.raw.dont23;
//        } else if (position == 23) {
//            // id = R.raw.dont24;
//            id=0;
//        } else if (position == 24) {
//            id = R.raw.dont25;
//        } else if (position == 25) {
//            id = R.raw.dont26;
//        } else if (position == 26) {
//            // id = R.raw.dont27;
//            id=0;
//        } else if (position == 27) {
//            id = R.raw.dont28;
//        } else if (position == 28) {
//            id = R.raw.dont29;
//        }
//        return id;
//    }
//
//
//    public static void openDocumentsFFiles(int pdf_name, String filename, Context context) //fileName is the pdf file name which is keep in assets folder. ex file.pdf
//    {
//
//        try {
//
//            copyFile(context.getResources().openRawResource(pdf_name),
//                    new FileOutputStream(new File(dirpicture, filename + ".docx")));
//
//            File pdfFile = new File(dirpicture, filename + ".docx");
//
//           /* Uri photoURI = FileProvider.getUriForFile(DiseasesActivity.this,
//                    BuildConfig.APPLICATION_ID + ".provider", pdfFile);*/
//
//
//            Uri photoURI;
//            if (Build.VERSION.SDK_INT >= 24) {
//                photoURI = FileProvider.getUriForFile(context,
//                        context.getApplicationContext().getPackageName() + ".provider",
//                        pdfFile);
//            } else {
//                photoURI = Uri.fromFile(pdfFile);
//            }
//
//
////            Uri path = Uri.fromFile(pdfFile);
//
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.setDataAndType(photoURI, "application/docx");
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            context.startActivity(intent);
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            CommonMethod.showToast("Cannot open due to some issues", context);
//        }
//    }
//
//
//    public static void openPDFFiles(int pdf_name, String filename, Context context) //fileName is the pdf file name which is keep in assets folder. ex file.pdf
//    {
//
//        try {
//
//            copyFile(context.getResources().openRawResource(pdf_name),
//                    new FileOutputStream(new File(dirpicture, filename + ".pdf")));
//
//            File pdfFile = new File(dirpicture, filename + ".pdf");
//
//           /* Uri photoURI = FileProvider.getUriForFile(DiseasesActivity.this,
//                    BuildConfig.APPLICATION_ID + ".provider", pdfFile);*/
//
//
//            Uri photoURI;
//            if (Build.VERSION.SDK_INT >= 24) {
//                photoURI = FileProvider.getUriForFile(context,
//                        context.getApplicationContext().getPackageName() + ".provider",
//                        pdfFile);
//            } else {
//                photoURI = Uri.fromFile(pdfFile);
//            }
//
//
////            Uri path = Uri.fromFile(pdfFile);
//
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.setDataAndType(photoURI, "application/pdf");
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            context.startActivity(intent);
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            CommonMethod.showToast("Cannot open due to some issues", context);
//        }
//    }
//
//    // public static String dirpicture = Environment.getExternalStorageDirectory() + "/DCIM/";
//
//    static File dirpicture = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//
//    //  public static String dirpicture =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Indoco/";
//
//
//    // public static String dirpicture = Environment.getExternalStorageDirectory() + "/DCIM/";
//
//    public static void copyFile(InputStream in, OutputStream out) throws IOException {
//        try {
//            byte[] buffer = new byte[1024];
//            int read;
//            while ((read = in.read(buffer)) != -1) {
//                out.write(buffer, 0, read);
//            }
//        } catch (Exception exp) {
//
//        }
//
//
//    }
//


    public static boolean isOnline(Context context)
    {
        boolean connected = false;

        try
        {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            return connected;


        }
        catch (Exception e)
        {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());
        }
        return connected;
    }


    void getFCMToken(final Context context){
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



}
