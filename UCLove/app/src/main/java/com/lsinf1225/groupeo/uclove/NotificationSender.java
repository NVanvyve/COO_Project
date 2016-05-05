package com.lsinf1225.groupeo.uclove;

        import android.app.Notification;
        import android.app.NotificationManager;
        import android.app.PendingIntent;
        import android.app.Service;
        import android.content.Context;
        import android.content.Intent;

        import java.util.Random;

public class NotificationSender{

    private String Message;
    private final Context mContext;


    public NotificationSender(String Message,Context context){
        this.Message=Message;
        this.mContext=context;
    }

    public void send(){
        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;

        Notification myNotication;

        NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        //NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //NotificationManager manager = (NotificationManager)getSystemService(getApplicationContext().NOTIFICATION_SERVICE);


        Intent intent = new Intent("vanvyve.nicolas.notification");

        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 1, intent, 0);

        Notification.Builder builder = new Notification.Builder(mContext);

        builder.setAutoCancel(true);
        builder.setTicker(mContext.getResources().getString(R.string.app_name));
        builder.setContentTitle(mContext.getResources().getString(R.string.app_name));
        builder.setContentText(Message);
        builder.setSmallIcon(R.drawable.ic_stat_name); // Icone de l'App Obligatoire sinon pas de notif
        //builder.setLargeIcon(/*bitmap*/);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(false);
        builder.setVibrate(new long[]{0, 1000, 1000, 1000, 1000}); // Need : <uses-permission android:name="android.permission.VIBRATE" />

        myNotication = builder.getNotification();
        manager.notify(m, myNotication);
    }
}