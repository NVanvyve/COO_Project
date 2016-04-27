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

public class UserManager {

    private static final String TABLE_NAME = "User";
    public static final String KEY_USER_ID="user_id";
    public static final String KEY_USER_USERNAME="user_username";
    public static final String KEY_USER_PASSWORD="user_password";
    public static final String KEY_USER_FIRST_NAME="user_first_name";
    public static final String KEY_USER_LAST_NAME="user_last_name";
    public static final String KEY_USER_BIRTH_DATE="user_birth_date";
    public static final String KEY_USER_CITY="user_city";
    public static final String KEY_USER_LANGUAGE="user_language";
    public static final String KEY_USER_HAIR_COLOR="user_hair_color";
    public static final String KEY_USER_HAIR_TYPE="user_hair_type";
    public static final String KEY_USER_EYES_COLOR="user_eyes_color";
    public static final String KEY_USER_SEX="user_sex";
    public static final String KEY_USER_SEXUALITY="user_sexuality";
    public static final String KEY_USER_POSITION="user_position";
    public static final String KEY_USER_PROFILE_PICTURE="user_profile_picture";
    public static final String CREATE_TABLE_USER = "CREATE TABLE "+TABLE_NAME+
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

    public long addUser(User user) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_USER_USERNAME, user.getUserUsername());
        values.put(KEY_USER_PASSWORD, user.getUserPassword());
        values.put(KEY_USER_FIRST_NAME, user.getUserFirstName());
        values.put(KEY_USER_LAST_NAME, user.getUserLastName());
        values.put(KEY_USER_BIRTH_DATE, user.getUserBirthDate());
        values.put(KEY_USER_CITY, user.getUserCity());
        values.put(KEY_USER_LANGUAGE, user.getUserLanguage());
        values.put(KEY_USER_HAIR_COLOR, user.getUserHairColor());
        values.put(KEY_USER_HAIR_TYPE, user.getUserHairType());
        values.put(KEY_USER_EYES_COLOR, user.getUserEyesColor());
        values.put(KEY_USER_SEX, user.getUserSex());
        values.put(KEY_USER_SEXUALITY, user.getUserSexuality());
        values.put(KEY_USER_POSITION, user.getUserPosition());
        values.put(KEY_USER_PROFILE_PICTURE, user.getUserProfilePic());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int modUser(User user) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_USER_USERNAME, user.getUserUsername());
        values.put(KEY_USER_PASSWORD, user.getUserPassword());
        values.put(KEY_USER_FIRST_NAME, user.getUserFirstName());
        values.put(KEY_USER_LAST_NAME, user.getUserLastName());
        values.put(KEY_USER_BIRTH_DATE, user.getUserBirthDate());
        values.put(KEY_USER_CITY, user.getUserCity());
        values.put(KEY_USER_LANGUAGE, user.getUserLanguage());
        values.put(KEY_USER_HAIR_COLOR, user.getUserHairColor());
        values.put(KEY_USER_HAIR_TYPE, user.getUserHairType());
        values.put(KEY_USER_EYES_COLOR, user.getUserEyesColor());
        values.put(KEY_USER_SEX, user.getUserSex());
        values.put(KEY_USER_SEXUALITY, user.getUserSexuality());
        values.put(KEY_USER_POSITION, user.getUserPosition());
        values.put(KEY_USER_PROFILE_PICTURE, user.getUserProfilePic());

        String where = KEY_USER_ID+" = ?";
        String[] whereArgs = {user.getUserID()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int supUser(User user) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_USER_ID+" = ?";
        String[] whereArgs = {user.getUserID()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public User getUser(long user_id) {
        // Retourne l'user dont l'id est passé en paramètre
        byte[] blob = {0};
        User a = new User(0,"", "", "", "", "", "", "", "", "", "", "", "", "", blob);

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_USER_ID+"="+user_id, null);
        if (c.moveToFirst()) {
            a.setUserID(c.getInt(c.getColumnIndex(KEY_USER_ID)));
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