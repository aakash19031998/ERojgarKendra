package com.twocoms.rojgarkendra.global.model;//package com.glenmark.dermaapp.global.model;
//
//import android.content.Context;
//import android.content.Intent;
//import android.media.Ringtone;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.support.v4.content.WakefulBroadcastReceiver;
//
///**
// * Created by Uttam on 4/11/2018.
// */
//
//public class NotificationReceiver extends WakefulBroadcastReceiver {
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        playNotificationSound(context);
//    }
//
//    public void playNotificationSound(Context context) {
//        try {
//            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//            Ringtone r = RingtoneManager.getRingtone(context, notification);
//            r.play();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}