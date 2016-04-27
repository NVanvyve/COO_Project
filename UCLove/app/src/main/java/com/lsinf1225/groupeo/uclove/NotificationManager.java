/**
 * Classe créée par Jean-François GAZET, trouvée sur http://fr.jeffprod.com/blog/2015/utilisation-d-une-base-sqlite-sous-android.html
 * Elle a cependant été adaptée à notre base de données.
 */

package com.lsinf1225.groupeo.uclove;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NotificationManager {

    private static final String TABLE_NAME = "Notification";
    public static final String KEY_NOTIF_ID="notif_id";
    public static final String KEY_NOTIF_USER_ID="notif_user_id";
    public static final String KEY_NOTIF_DATE="notif_date";
    public static final String KEY_NOTIF_TEXT="notif_text";
    public static final String KEY_NOTIF_STATUS="notif_status";
    public static final String KEY_NOTIF_CODE="notif_code";
    public static final String CREATE_TABLE_NOTIFICATION = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_NOTIF_ID+" INTEGER not null primary key," +
            " "+KEY_NOTIF_USER_ID+" INTEGER not null references User," +
            " "+KEY_NOTIF_DATE+" TEXT not null," +
            " "+KEY_NOTIF_TEXT+" TEXT not null," +
            " "+KEY_NOTIF_STATUS+" TEXT not null," +
            " "+KEY_NOTIF_CODE+" INTEGER not null," +
            " unique("+KEY_NOTIF_USER_ID+", "+KEY_NOTIF_DATE+", "+KEY_NOTIF_TEXT+") " +
            ");";
    private MySQLite maBaseSQLite; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;

    // Constructeur
    public NotificationManager(Context context)
    {
        maBaseSQLite = MySQLite.getInstance(context);
    }

    public void open()
    {
        //on ouvre la table en lecture/écriture
        db = maBaseSQLite.getWritableDatabase();
    }

    public void close()
    {
        //on ferme l'accès à la BDD
        db.close();
    }

    public long addNotification(Notification notification) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_NOTIF_ID, notification.getNotifID());
        values.put(KEY_NOTIF_USER_ID, notification.getNotifUserID());
        values.put(KEY_NOTIF_DATE, notification.getNotifDate());
        values.put(KEY_NOTIF_TEXT, notification.getNotifText());
        values.put(KEY_NOTIF_STATUS, notification.getNotifStatus());
        values.put(KEY_NOTIF_CODE, notification.getNotifCode());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int modNotification(Notification notification) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_NOTIF_ID, notification.getNotifID());
        values.put(KEY_NOTIF_USER_ID, notification.getNotifUserID());
        values.put(KEY_NOTIF_DATE, notification.getNotifDate());
        values.put(KEY_NOTIF_TEXT, notification.getNotifText());
        values.put(KEY_NOTIF_STATUS, notification.getNotifStatus());
        values.put(KEY_NOTIF_CODE, notification.getNotifCode());

        String where = KEY_NOTIF_ID+" = ?";
        String[] whereArgs = {notification.getNotifID()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int supNotification(Notification notification) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_NOTIF_ID+" = ?";
        String[] whereArgs = {notification.getNotifID()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Notification getNotification(long notif_id) {
        // Retourne la notification dont l'id est passé en paramètre
        Notification a = new Notification(0, 0, "", "", "", 0);

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_NOTIF_ID+"="+notif_id, null);
        if (c.moveToFirst()) {
            a.setNotifID(c.getInt(c.getColumnIndex(KEY_NOTIF_ID)));
            a.setNotifUserID(c.getInt(c.getColumnIndex(KEY_NOTIF_USER_ID)));
            a.setNotifDate(c.getString(c.getColumnIndex(KEY_NOTIF_DATE)));
            a.setNotifText(c.getString(c.getColumnIndex(KEY_NOTIF_TEXT)));
            a.setNotifStatus(c.getString(c.getColumnIndex(KEY_NOTIF_STATUS)));
            a.setNotifCode(c.getInt(c.getColumnIndex(KEY_NOTIF_CODE)));
            c.close();
        }

        return a;
    }

    public Cursor getNotifications() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }

} // class NotificationManager
