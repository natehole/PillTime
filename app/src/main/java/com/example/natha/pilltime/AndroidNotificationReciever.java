package com.example.natha.pilltime;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
/**
 * Created by DylansPc on 12/3/2017.
 */

public class AndroidNotificationReciever extends BroadcastReceiver{

    String medNote;
    @Override
    public void onReceive(Context context, Intent intent) {
        medNote = intent.getExtras().getString("meds");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL).setWhen(System.currentTimeMillis()).setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Take Medication!")
                .setContentText("Its time to take your: " + medNote).setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND).setContentInfo("Info");
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,builder.build());
    }
}