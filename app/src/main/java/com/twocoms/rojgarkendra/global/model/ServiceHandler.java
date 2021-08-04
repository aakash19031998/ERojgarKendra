package com.twocoms.rojgarkendra.global.model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.internal.service.Common;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.dashboardscreen.controler.DashboardActivity;
import com.twocoms.rojgarkendra.global.controler.AppHelper;
import com.twocoms.rojgarkendra.global.controler.VolleyMultipartRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.CheckedOutputStream;

public class ServiceHandler {

    private final Context context;
    private String strResponse;
    private RequestQueue requestQueue;
    private JSONObject jsonResponse;
    private JSONArray jsonArray;
    private LoadingDialog loadingDialog;
    private VolleyError volleyError;
    SharedPreferences sharedpreferences;


    public ServiceHandler(Context context) {
        this.context = context;


        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(this.context);
        }
    }

    public void jsonRequest(int requestType, JSONObject parameters, final String web_url, boolean showDialog, final VolleyJsonCallback callback) {
        if (showDialog) {
            loadingDialog = new LoadingDialog(context);
            loadingDialog.show();
        }

        Log.d("JsonRequest", parameters.toString());


        JsonObjectRequest jsonObjectRequest;
        jsonObjectRequest = new JsonObjectRequest(requestType, web_url, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String status = "j";
                        try {

                            Log.d("Status", web_url + "::" + status);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                       /* if(jwtToken.decryptJWTToken(strJwtToken))
                        {
                        }*/

                        if (loadingDialog != null && loadingDialog.isShowing())
                            loadingDialog.dismiss();


                        jsonResponse = response;
                        callback.onSuccess(jsonResponse);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (loadingDialog != null && loadingDialog.isShowing())
                            loadingDialog.dismiss();

                        Log.d("Error", error.toString());
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params1 = new HashMap<>();


//                params1.put("Clientid",Application_Constants.CLIENT_ID);
//                params1.put("SecretId",Application_Constants.SECRET_ID);


                return params1;
            }

        };
        jsonObjectRequest.setShouldCache(false);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);


    }


    public void JsonArrayRequest1(final String web_url, Response.Listener<JSONArray> listener, boolean showDialog
            , Response.ErrorListener listener1) {
        if (showDialog) {
            loadingDialog = new LoadingDialog(context);
            loadingDialog.show();
        }


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(web_url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                if (loadingDialog != null && loadingDialog.isShowing())
                    loadingDialog.dismiss();


                jsonArray = response;


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (loadingDialog != null && loadingDialog.isShowing())
                    loadingDialog.dismiss();

                Log.d("Error", error.toString());
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();

            }
        }) {


            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params1 = new HashMap<>();


//                params1.put("Clientid",Application_Constants.CLIENT_ID);
//                params1.put("SecretId",Application_Constants.SECRET_ID);


                return params1;

            }


        };


        jsonArrayRequest.setShouldCache(false);
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);


    }


    public void StringRequestjsonArry(int requestType, final JSONArray parameters, final String web_url, boolean showDialog, final VolleyCallback volleyCallback) {
        if (CommonMethod.isOnline(context)) {

            if (showDialog) {
                loadingDialog = new LoadingDialog(context);
                loadingDialog.show();
            }

            StringRequest stringRequest = new StringRequest(requestType, web_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (loadingDialog != null && loadingDialog.isShowing())
                                loadingDialog.dismiss();

                            strResponse = response;
                            volleyCallback.onSuccess(strResponse);


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    if (loadingDialog != null && loadingDialog.isShowing())
                        loadingDialog.dismiss();

                    if (error instanceof TimeoutError) {
                        Toast.makeText(context, "timeout", Toast.LENGTH_SHORT).show();
                    }

                    Log.d("Error", error.toString());

                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params1 = new HashMap<>();

//                    params1.put("Content-Type", "application/json");
//                    params1.put("Clientid",Application_Constants.CLIENT_ID);
//                    params1.put("SecretId",Application_Constants.SECRET_ID);
                    return params1;
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    return parameters.toString().getBytes();
                }
            };

            stringRequest.setShouldCache(false);


            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    60000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);

        } else {
            Toast.makeText(context, "CHECK INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
        }

    }



    /*public void StringRequest(int requestType, final String parameter, final String web_url, boolean showDialog, final VolleyCallback volleyCallback)
    {
        if(CommonMethod.isOnline(context))
        {

            if(showDialog)
            {
                loadingDialog =  new LoadingDialog(context);
                loadingDialog.show();
            }

            StringRequest stringRequest = new StringRequest(requestType, web_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (loadingDialog != null && loadingDialog.isShowing())
                                loadingDialog.dismiss();

                            strResponse = response;
                            volleyCallback.onSuccess(strResponse);


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    if (loadingDialog != null && loadingDialog.isShowing())
                        loadingDialog.dismiss();

                    if(error instanceof TimeoutError)
                    {
                        Toast.makeText(context, "timeout", Toast.LENGTH_SHORT).show();
                    }

                    Log.d("Error",error.toString());

                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }){
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params1 = new HashMap<>();

//                    params1.put("Content-Type", "application/json");
//                    params1.put("Clientid",Application_Constants.CLIENT_ID);
//                    params1.put("SecretId",Application_Constants.SECRET_ID);
                    return params1;
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    return parameter.getBytes();
                }
            };

            stringRequest.setShouldCache(false);


            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    60000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);

        }
        else
        {
            Toast.makeText(context, "CHECK INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
        }

    }*/


    public void StringRequest(int requestType, final String parameters, final String web_url, boolean showDialog, final VolleyCallback volleyCallback) {
        if (CommonMethod.isOnline(context)) {

            if (showDialog) {
                loadingDialog = new LoadingDialog(context);
                loadingDialog.setCancelable(false);
                loadingDialog.show();

            }

            StringRequest stringRequest = new StringRequest(requestType, web_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (loadingDialog != null && loadingDialog.isShowing())
                                loadingDialog.dismiss();

                            strResponse = response;
                            volleyCallback.onSuccess(strResponse);


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (loadingDialog != null && loadingDialog.isShowing())
                        loadingDialog.dismiss();

                    try {

                        byte[] data = error.networkResponse.data;

//                    byte[] bytes = "hello world".getBytes();
//creates a string from the byte array without specifying character encoding
                        String s = new String(data);

                        Log.v("NetworkResponse", s);
                        if (error.getMessage() == null) {
                            Toast.makeText(context, "Something went wrong please try again!", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof TimeoutError) {
                            Toast.makeText(context, "Time out error", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        Log.d("Error", error.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Something went wrong please try again!", Toast.LENGTH_SHORT).show();

                    }
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/x-www-form-urlencoded; charset=UTF-8";
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Gson gson = new Gson();
                    Type type = new TypeToken<Map<String, String>>() {
                    }.getType();
                    Map<String, String> myMap = gson.fromJson(parameters, type);
                    return myMap;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    String tokenMain = GlobalPreferenceManager.getStringForKey(context, AppConstant.KEY_TOKEN_MAIN, "");
                    headers.put("Authorization", "Bearer " + tokenMain);
                    return headers;
                }
            };

            stringRequest.setShouldCache(false);


            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    60000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);

        } else {
            Toast.makeText(context, "CHECK INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
        }

    }


    public void StringRequest(int requestType, final JSONObject parameters, final String web_url, boolean showDialog, final VolleyCallback volleyCallback) {
        if (CommonMethod.isOnline(context)) {

            if (showDialog) {
                loadingDialog = new LoadingDialog(context);
                loadingDialog.show();
            }

            StringRequest stringRequest = new StringRequest(requestType, web_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (loadingDialog != null && loadingDialog.isShowing())
                                loadingDialog.dismiss();

                            strResponse = response;
                            volleyCallback.onSuccess(strResponse);


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    if (loadingDialog != null && loadingDialog.isShowing())
                        loadingDialog.dismiss();

                    if (error instanceof TimeoutError) {
                        Toast.makeText(context, "timeout", Toast.LENGTH_SHORT).show();
                    }

                    Log.d("Error", error.toString());

                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params1 = new HashMap<>();

//                    params1.put("Content-Type", "application/json");
//                    params1.put("Clientid",Application_Constants.CLIENT_ID);
//                    params1.put("SecretId",Application_Constants.SECRET_ID);
                    return params1;
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    return parameters.toString().getBytes();
                }
            };

            stringRequest.setShouldCache(false);


            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    60000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);

        } else {
            Toast.makeText(context, "CHECK INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
        }

    }

    public void StringRequestScheme(int requestType, final JSONObject parameters, final String web_url, boolean showDialog, final VolleyCallback volleyCallback) {
        if (CommonMethod.isOnline(context)) {

            if (showDialog) {
                loadingDialog = new LoadingDialog(context);
                loadingDialog.show();
            }

            StringRequest stringRequest = new StringRequest(requestType, web_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (loadingDialog != null && loadingDialog.isShowing())
                                loadingDialog.dismiss();

                            strResponse = response.toString();
                            volleyCallback.onSuccess(strResponse);


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    if (loadingDialog != null && loadingDialog.isShowing())
                        loadingDialog.dismiss();

                    if (error instanceof TimeoutError) {

                        showAlert1("WEAK INTERNET CONNECTION, KINDLY TRY AGAIN");
                    }


                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params1 = new HashMap<>();

//                    params1.put("Content-Type", "application/json");
//                    params1.put("Clientid",Application_Constants.CLIENT_ID);
//                    params1.put("SecretId",Application_Constants.SECRET_ID);
                    return params1;
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    return parameters.toString().getBytes();
                }
            };

            stringRequest.setShouldCache(false);


            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    15000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);

        } else {
            Toast.makeText(context, "CHECK INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
        }

    }

    public void StringRequestGet(int requestType, final String web_url, boolean showDialog, final VolleyCallback volleyCallback) {
        if (CommonMethod.isOnline(context)) {

            if (showDialog) {
                loadingDialog = new LoadingDialog(context);
                loadingDialog.show();
            }

            StringRequest stringRequest = new StringRequest(requestType, web_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (loadingDialog != null && loadingDialog.isShowing())
                                loadingDialog.dismiss();

                            strResponse = response;
                            volleyCallback.onSuccess(strResponse);


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    if (loadingDialog != null && loadingDialog.isShowing())
                        loadingDialog.dismiss();

                    if (error instanceof TimeoutError) {
                        Toast.makeText(context, "timeout", Toast.LENGTH_SHORT).show();
                    }

                    Log.d("Error", error.toString());

                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params1 = new HashMap<>();

//                    params1.put("Content-Type", "application/json");
//                    params1.put("Clientid",Application_Constants.CLIENT_ID);
//                    params1.put("SecretId",Application_Constants.SECRET_ID);
                    return params1;
                }


            };

            stringRequest.setShouldCache(false);


            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    60000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);

        } else {
            Toast.makeText(context, "CHECK INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
        }

    }


    public void StringRequestGet1(int requestType, final String web_url, final boolean showDialog, final VolleyCallback1 volleyCallback) {
        if (CommonMethod.isOnline(context)) {

            if (showDialog) {
                loadingDialog = new LoadingDialog(context);
                loadingDialog.show();
            }

            StringRequest stringRequest = new StringRequest(requestType, web_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (loadingDialog != null && loadingDialog.isShowing())
                                loadingDialog.dismiss();

                            strResponse = response;
                            volleyCallback.onSuccess(strResponse);


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                    volleyCallback.onError(error);

//
                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params1 = new HashMap<>();

//                    params1.put("Content-Type", "application/json");
//                    params1.put("Clientid",Application_Constants.CLIENT_ID);
//                    params1.put("SecretId",Application_Constants.SECRET_ID);
                    return params1;
                }


            };

            stringRequest.setShouldCache(false);


            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    15000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


            requestQueue.add(stringRequest);

        } else {
            Toast.makeText(context, "CHECK INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
        }

    }


    public interface VolleyJsonCallback {
        void onSuccess(JSONObject result);
    }

    public interface VolleyJsonArrayCallback {
        void onSuccess(JSONArray result);
    }

    public interface VolleyCallback {
        void onSuccess(String result);

    }


    public interface VolleyCallback1 {
        void onSuccess(String result);

        void onError(VolleyError error);

    }


    public void showAlert(String Message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Error Message");

        // set dialog message
        alertDialogBuilder
                .setMessage(Message)
                .setCancelable(false)
                .setNegativeButton(context.getString(R.string.exit), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        //  finish();

                        dialog.dismiss();


                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


    public void showAlert1(String Message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Error Message");

        // set dialog message
        alertDialogBuilder
                .setMessage(Message)
                .setCancelable(false)
                .setNegativeButton(context.getString(R.string.exit), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        //  finish();

                        dialog.dismiss();

//                        Intent i = new Intent(context, DashboardActivity.class);
//                        context.startActivity(i);


                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
