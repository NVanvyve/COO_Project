package com.lsinf1225.groupeo.uclove;

        import android.app.Notification;
        import android.app.NotificationManager;
        import android.app.PendingIntent;
        import android.content.Context;
        import android.content.Intent;


public class NotificationSender{

    private String Message;
    private final Context mContext;
    private int id;


    public NotificationSender(String Message, int id, Context context){
        this.Message=Message;
        this.mContext=context;
        this.id = id;
    }

    public void send(){

        Notification myNotication;

        NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent("com.lsinf1225.groupeo.uclove");

        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 1, intent, 0);

        Notification.Builder builder = new Notification.Builder(mContext);

        builder.setAutoCancel(true);
        builder.setTicker(mContext.getResources().getString(R.string.app_name));
        builder.setContentTitle(mContext.getResources().getString(R.string.app_name));
        builder.setContentText(Message);
        builder.setSmallIcon(R.drawable.ic_stat_name); // icône de l'app
        //builder.setLargeIcon(/*bitmap*/);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(false);

        myNotication = builder.getNotification();
        manager.notify(id, myNotication);
    }

    public void cancelNotification() {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) mContext.getSystemService(ns);
        nMgr.cancel(id);
    }

    public void cancelAllNotifications() {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) mContext.getSystemService(ns);
        nMgr.cancelAll();
    }
}