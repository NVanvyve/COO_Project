/**
 * Classe créée par Jean-François GAZET, trouvée sur http://fr.jeffprod.com/blog/2015/utilisation-d-une-base-sqlite-sous-android.html
 * Elle a cependant été adaptée à notre base de données.
 */

package com.lsinf1225.groupeo.uclove;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Blob;

public class CalendarManager {

    private static final String TABLE_NAME = "Calendar";
    public static final String KEY_CAL_ID="cal_id";
    public static final String KEY_CAL_USER_ID="cal_user_id";
    public static final String KEY_CAL_DATE="cal_date";
    public static final String KEY_CAL_STATUS="cal_status";
    public static final String CREATE_TABLE_CALENDAR = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_CAL_ID+" INTEGER not null primary key," +
            " "+KEY_CAL_USER_ID+" INTEGER not null references User," +
            " "+KEY_CAL_DATE+" TEXT not null," +
            " "+KEY_CAL_STATUS+" TEXT not null default 'Libre'," +
            " unique("+KEY_CAL_USER_ID+", "+KEY_CAL_DATE+") " +
            ");";
    private MySQLite maBaseSQLite; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;

    // Constructeur
    public CalendarManager(Context context)
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

    public long addCalendar(Calendar calendar) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_CAL_USER_ID, calendar.getCalUserID());
        values.put(KEY_CAL_DATE, calendar.getCalDate());
        values.put(KEY_CAL_STATUS, calendar.getCalStatus());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int modCalendar(Calendar calendar) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_CAL_USER_ID, calendar.getCalUserID());
        values.put(KEY_CAL_DATE, calendar.getCalDate());
        values.put(KEY_CAL_STATUS, calendar.getCalStatus());

        String where = KEY_CAL_ID+" = ?";
        String[] whereArgs = {calendar.getCalID()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int supCalendar(Calendar calendar) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_CAL_ID+" = ?";
        String[] whereArgs = {calendar.getCalID()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Calendar getCalendar(long cal_id) {
        // Retourne le calendrier dont l'id est passé en paramètre
        Calendar a = new Calendar(0, 0, "", "");

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_CAL_ID+"="+cal_id, null);
        if (c.moveToFirst()) {
            a.setCalID(c.getInt(c.getColumnIndex(KEY_CAL_ID)));
            a.setCalUserID(c.getInt(c.getColumnIndex(KEY_CAL_USER_ID)));
            a.setCalDate(c.getString(c.getColumnIndex(KEY_CAL_DATE)));
            a.setCalStatus(c.getString(c.getColumnIndex(KEY_CAL_STATUS)));
            c.close();
        }

        return a;
    }

    public Cursor getCalendars() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }

} // class CalendarManager