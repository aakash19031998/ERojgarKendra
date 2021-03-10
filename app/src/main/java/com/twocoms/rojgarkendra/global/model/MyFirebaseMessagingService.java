//package com.twocoms.rojgarkendra.global.model;
//
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.TaskStackBuilder;
//import android.content.Context;
//import android.content.Intent;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.os.Build;
//import android.util.Log;
//
//import androidx.annotation.RequiresApi;
//import androidx.core.app.NotificationCompat;
//import androidx.core.app.NotificationManagerCompat;
//
//import com.glenmarkmain.dermaapp.R;
//import com.glenmarkmain.dermaapp.SplashScreenActivity;
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.Map;
//import java.util.Random;
//
///**
// * Created by Satyam on 06-May-17.
// */
//
//public class MyFirebaseMessagingService extends FirebaseMessagingService {
//
//   // SharedPreferences sf;
//
//    private static final String TAG = com.glenmarkmain.dermaapp.global.model.MyFirebaseMessagingService.class.getSimpleName();
//
//    private Uri defaultSoundUri;
//
//    private PendingIntent pendingIntent;
//
//    String title = "", body = "";
//
//
//    @Override
//    public void onNewToken(String fcmToken) {
//        super.onNewToken(fcmToken);
//        GlobalPreferenceManager.saveStringForKey(this, "firebasekey", fcmToken);
//        Log.e(TAG, "onNewToken: " + fcmToken);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);
//        /*if (remoteMessage.getNotification().getTitle() != null) {
//
//            title = remoteMessage.getNotification().getTitle();
//
//            body = remoteMessage.getNotification().getBody();
//
//        }*/
//
//        Map<String, String> data = remoteMessage.getData();
//
//        JSONObject object = new JSONObject(data);
//
//        String message = object.toString();
//
//        Log.e("JSON_OBJECT", object.toString());
//
//        defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////x
////            setupChannels();
////
////        }
//
//        parseMessage(object);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void parseMessage(JSONObject message) {
//        try {
//            title = message.has("title") ? message.getString("title") : "";
//            body = message.has("message") ? message.getString("message") : "";
//            Intent resultIntent;
//            resultIntent = new Intent(getApplicationContext(), SplashScreenActivity.class);
//           // resultIntent.putExtra("title", title);
//            //resultIntent.putExtra("body", body);
//            resultIntent.putExtra("comingFrom", "notification");
//            showNotificationWithStack(getApplicationContext(), resultIntent);
//        } catch (JSONException e) {
//
//            e.printStackTrace();
//
//        }
//
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void showNotificationWithStack(Context applicationContext, Intent resultIntent) {
//
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//
//        stackBuilder.addNextIntentWithParentStack(resultIntent);
//
//        pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//
////        pendingIntent = PendingIntent.getActivity(applicationContext, 0, resultIntent, 0);
//
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(applicationContext, getResources().getString(R.string.channel_id));
//
//        defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        showSmallNotification(applicationContext, mBuilder, pendingIntent, defaultSoundUri);
//
//      //  showNotification(applicationContext, mBuilder, pendingIntent, defaultSoundUri);
//
//
//    }
//
//    private void showNotification(Context applicationContext, Intent resultIntent) {
//        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//        pendingIntent = PendingIntent.getActivity(applicationContext, 0, resultIntent, 0);
//
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(applicationContext, getResources().getString(R.string.channel_id));
//
//        defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        //  showSmallNotification(applicationContext, mBuilder, pendingIntent, defaultSoundUri);
//
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void showSmallNotification(Context mContext, NotificationCompat.Builder mBuilder, PendingIntent pendingIntent, Uri defaultSoundUri) {
//
//        try {
//
//            String CHANNEL_ID = mContext.getResources().getString(R.string.channel_id);// The id of the channel.
//
//            int notificationId = new Random().nextInt(60000);
//
////            setupChannels();
//
//
//            mBuilder.setSmallIcon(R.mipmap.applogo)
//
//                    .setContentTitle(title)
//
//                    .setContentText(body)
//
//                    .setAutoCancel(true)
//
//                    .setSound(defaultSoundUri)
//
//                    .setChannelId(CHANNEL_ID)
//
//                    .setContentIntent(pendingIntent)
//
//                    .setPriority(NotificationCompat.PRIORITY_MAX)
//
//                    .setCategory(NotificationCompat.CATEGORY_MESSAGE);
//
//            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//
//            notificationManager.notify(notificationId, mBuilder.build());
//
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//        }
//
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void setupChannels() {
//
//        String channelId = getResources().getString(R.string.channel_id);
//
//        // Create the NotificationChannel, but only on API 26+ because
//
//        // the NotificationChannel class is new and not in the support library
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//
//            CharSequence name = getString(R.string.channel_name);
//
//            String description = getString(R.string.notifications_admin_channel_description);
//
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//
//            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
//
//            channel.setDescription(description);
//
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//
//            notificationManager.createNotificationChannel(channel);
//
//        }
//
//    }
//
//}
//
