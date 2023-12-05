package ru.mobilap.localnotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.NotificationChannel;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import androidx.core.app.NotificationCompat;

import android.media.Image;
import android.util.Log;
import android.net.Uri;
import android.media.RingtoneManager;
import org.godotengine.godot.Godot;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

public class LocalNotificationReceiver extends BroadcastReceiver {
    private static final String TAG = "Notification";
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;

    @Override
    public void onReceive(Context context, Intent intent) {
        int notificationId = intent.getIntExtra("notification_id", 0);
        String message = intent.getStringExtra("message");
        String title = intent.getStringExtra("title");
        String smallIconName = intent.getStringExtra("smallIconName");
        String LargeIconName = intent.getStringExtra("LargeIconName");
        String textOfSummary = intent.getStringExtra("textOfSummary");
        String colorOfIcon = intent.getStringExtra("colorOfIcon");

        Log.i(TAG, "Receive notification: "+message);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String appName = "Game Test";
        SpannableStringBuilder spannableBuilder = new SpannableStringBuilder(appName);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.RED); // Set the desired color

        // Apply color to part of the text (in this case, the application name)
        spannableBuilder.setSpan(colorSpan, 0, appName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);



        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O ) {
            int importance = NotificationManager.IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            notificationChannel.setShowBadge(true);
            //builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
            manager.createNotificationChannel(notificationChannel) ;
        }

        Class appClass = null;
        try {
            appClass = Class.forName("com.godot.game.GodotApp");
        } catch (ClassNotFoundException e) {
            // app not found, do nothing
            return;
        }

        
        Intent intent2 = new Intent(context, appClass);
        intent2.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_IMMUTABLE);

        int iconID = context.getResources().getIdentifier(smallIconName, "mipmap", context.getPackageName());
        int notificationIconID = context.getResources().getIdentifier(LargeIconName, "mipmap", context.getPackageName());
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), notificationIconID);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        int colorID = context.getResources().getIdentifier("notification_color", "color", context.getPackageName());
        builder.setShowWhen(true);
        builder.setContentTitle(title);
        builder.setContentText(message);
        //builder.setContentText(message.substring(0, 80) + " ...");
        //builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message).setSummaryText(textOfSummary).setBigContentTitle(title));

        builder.setSmallIcon(iconID);
        builder.setLargeIcon(largeIcon);



        builder.setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE);
        builder.setTicker(message);
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setColorized(true);

        builder.setColor(Color.parseColor(colorOfIcon));

        builder.setContentIntent(pendingIntent);
        builder.setNumber(1);
        //builder.addAction();
        //builder.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.bomb3));
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        long[] pattern = {10,10};
        builder.setVibrate(pattern);

        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setCategory(NotificationCompat.CATEGORY_MESSAGE);
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        Notification notification = builder.build();
        notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        notification.vibrate = pattern;
        manager.notify(notificationId, notification);
    }

}
