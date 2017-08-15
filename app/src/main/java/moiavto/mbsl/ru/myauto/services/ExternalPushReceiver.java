package moiavto.mbsl.ru.myauto.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.ui.activities.MainActivity;
import moiavto.mbsl.ru.myauto.ui.fragments.BookingsFragment;

/**
 * Created by Fedor on 30.05.2017.
 */

public class ExternalPushReceiver extends BroadcastReceiver {

    private Context context;

    public void onReceive(Context context, Intent intent) {
        this.context = context;

        if (intent != null) {
            Bundle extras = intent.getExtras();

            String appId = extras.getString("appId");
            String type = extras.getString("type");
            String message = extras.getString("message");
            Integer bookingId = extras.getInt("bookingId");
            Integer companyId = extras.getInt("companyId");
            //            if (!TextUtils.isEmpty(type)){
            //
            //            } else {
            //                sendNotification(message);
            //            }

            sendNotification(message);
        }
    }


    //    private void sendMessage(String message, final int userId) {
    //        int endNameIndex = message.indexOf(':');
    //        String userName = message.substring(0, endNameIndex);
    //
    //        Intent notifyIntent = MainActivity.getIntent(context, userId, userName, ChatFragment.TAG);
    //        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
    //                Intent.FLAG_ACTIVITY_CLEAR_TASK);
    //        PendingIntent pendingIntent =
    //                PendingIntent.getActivity(
    //                        context,
    //                        0,
    //                        notifyIntent,
    //                        PendingIntent.FLAG_UPDATE_CURRENT
    //                );
    //
    //        String title = context.getString(R.string.app_name);
    //        showNotification(title, message, pendingIntent);
    //    }


    private void sendNotification(String messageBody) {
        Intent intent = MainActivity.newInstancePush(context, BookingsFragment.TAG_FACTORY, null);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        showNotification(context.getString(R.string.app_name), messageBody, pendingIntent);
    }

    private void showNotification(String title, String text, PendingIntent pendingIntent) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(defaultSoundUri);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
