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

public class CalenderManager {

    private static final String TABLE_NAME = "Calender";
    public static final String KEY_CAL_ID="cal_id";
    public static final String KEY_CAL_USER_ID="cal_user_id";
    public static final String KEY_CAL_DATE="cal_date";
    public static final String KEY_CAL_STATUS="cal_status";
    public static final String CREATE_TABLE_CALENDER = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_USER_ID+" INTEGER not null primary key," +
            " "+KEY_USER_USERNAME+" TEXT not null," +
            " "+KEY_USER_PASSWORD+" TEXT not null," +
            " "+KEY_USER_FIRST_NAME+" TEXT not null," +
            " "+KEY_USER_LAST_NAME+" TEXT not null," +
            " "+KEY_USER_BIRTH_DATE+" TEXT not null," +
            " "+KEY_USER_CITY+" TEXT not null," +
            " "+KEY_USER_LANGUAGE+" TEXT not null," +
            " "+KEY_USER_HAIR_COLOR+" TEXT," +
            " "+KEY_USER_HAIR_TYPE+" TEXT," +
            " "+KEY_USER_EYES_COLOR+" TEXT," +
            " "+KEY_USER_SEX+" TEXT not null," +
            " "+KEY_USER_SEXUALITY+" TEXT not null," +
            " "+KEY_USER_POSITION+" TEXT not null," +
            " "+KEY_USER_PROFILE_PICTURE+" BLOB," +
            " unique("+KEY_USER_FIRST_NAME+", "+KEY_USER_LAST_NAME+", "+KEY_USER_BIRTH_DATE+") " +
            ");";
    private MySQLite maBaseSQLite; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;

    // Constructeur
    public UserManager(Context context)
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

    public long addCalender(Calender calender) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_CAL_ID, calender.getCalID());
        values.put(KEY_CAL_USER_ID, calender.getCalUserID());
        values.put(KEY_CAL_DATE, calender.getCalDate());
        values.put(KEY_CAL_STATUS, calender.getCalStatus());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int modCalender(Calender calender) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_CAL_ID, calender.getCalID());
        values.put(KEY_CAL_USER_ID, calender.getCalUserID());
        values.put(KEY_CAL_DATE, calender.getCalDate());
        values.put(KEY_CAL_STATUS, calender.getCalStatus());

        String where = KEY_CAL_ID+" = ?";
        String[] whereArgs = {calender.getCalID()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int supCalender(Calender calender) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_CAL_ID+" = ?";
        String[] whereArgs = {calender.getCalID()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Calender getCalender(long cal_id) {
        // Retourne le calendrier dont l'id est passé en paramètre
        Calender a = new Calender(0, 0, "", "");

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_CAL_ID+"="+cal_id, null);
        if (c.moveToFirst()) {
            a.setCalID(c.getInt(c.getColumnIndex(KEY_USER_ID)));
            a.setUserUsername(c.getString(c.getColumnIndex(KEY_USER_USERNAME)));
            a.setUserPassword(c.getString(c.getColumnIndex(KEY_USER_PASSWORD)));
            a.setUserFirstName(c.getString(c.getColumnIndex(KEY_USER_FIRST_NAME)));
            a.setUserLastName(c.getString(c.getColumnIndex(KEY_USER_LAST_NAME)));
            a.setUserBirthDate(c.getString(c.getColumnIndex(KEY_USER_BIRTH_DATE)));
            a.setUserCity(c.getString(c.getColumnIndex(KEY_USER_CITY)));
            a.setUserLanguage(c.getString(c.getColumnIndex(KEY_USER_LANGUAGE)));
            a.setUserHairColor(c.getString(c.getColumnIndex(KEY_USER_HAIR_COLOR)));
            a.setUserHairType(c.getString(c.getColumnIndex(KEY_USER_HAIR_TYPE)));
            a.setUserEyesColor(c.getString(c.getColumnIndex(KEY_USER_EYES_COLOR)));
            a.setUserSex(c.getString(c.getColumnIndex(KEY_USER_SEX)));
            a.setUserSexuality(c.getString(c.getColumnIndex(KEY_USER_SEXUALITY)));
            a.setUserPosition(c.getString(c.getColumnIndex(KEY_USER_POSITION)));
            a.setUserProfilePic(c.getBlob(c.getColumnIndex(KEY_USER_PROFILE_PICTURE)));
            c.close();
        }

        return a;
    }

    public Cursor getUsers() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }

} // class UserManager
