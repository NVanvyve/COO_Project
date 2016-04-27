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

public class PreferencesManager {

    private static final String TABLE_NAME = "Preferences";
    public static final int KEY_PREF_ID="pref_id";
    public static final int KEY_PREF_USER_ID="pref_user_id";
    public static final int KEY_PREF_LANGUAGE_FR="pref_language_FR";
    public static final int KEY_PREF_LANGUAGE_EN="pref_language_EN";
    public static final int KEY_PREF_HAIR_COLOR_BLOND="pref_hair_color_blond";
    public static final int KEY_PREF_HAIR_COLOR_BLACK="pref_hair_color_black";
    public static final int KEY_PREF_HAIR_COLOR_BROWN="pref_hair_color_brown";
    public static final int KEY_PREF_HAIR_COLOR_GINGER="pref_hair_color_ginger";
    public static final int KEY_PREF_HAIR_COLOR_GREYWHITE="pref_hair_color_greywhite";
    public static final int KEY_PREF_HAIR_COLOR_OTHER="pref_hair_color_other";
    public static final int KEY_PREF_HAIR_TYPE_SHORT="pref_hair_type_short";
    public static final int KEY_PREF_HAIR_TYPE_MEDIUM="pref_hair_type_medium";
    public static final int KEY_PREF_HAIR_TYPE_LONG="pref_hair_type_long";
    public static final int KEY_PREF_EYES_COLOR_BLUE="pref_eyes_color_blue";
    public static final int KEY_PREF_EYES_COLOR_BROWN="pref_eyes_color_brown";
    public static final int KEY_PREF_EYES_COLOR_BLACK="pref_eyes_color_black";
    public static final int KEY_PREF_EYES_COLOR_GREEN="pref_eyes_color_green";
    public static final int KEY_PREF_EYES_COLOR_GREY="pref_eyes_color_grey";
    public static final int KEY_PREF_AGE_MAX="pref_age_max";
    public static final int KEY_PREF_AGE_MIN="pref_age_min";
    public static final int KEY_PREF_DISTANCE_MAX="pref_distance_max";
    public static final int CREATE_TABLE_USER = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_USER_ID+" INTEGER not null primary key," +
            " "+KEY_PREF_USER_ID+" TEXT not null," +
            " "+KEY_PREF_LANGUAGE_FR+" TEXT not null," +
            " "+KEY_PREF_LANGUAGE_EN+" TEXT not null," +
            " "+KEY_PREF_HAIR_COLOR_BLOND+" TEXT not null," +
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
            " unique("+KEY_PREF_LANGUAGE_EN+", "+KEY_PREF_HAIR_COLOR_BLOND+", "+KEY_USER_BIRTH_DATE+") " +
            ");";
    private MySQLite maBaseSQLite; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;

    // Constructeur
    public PreferencesManager(Context context)
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

    public long addPreferences(Preferences pref) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_PREF_USER_ID, pref.getPrefUserID());
        values.put(KEY_PREF_LANGUAGE_FR, pref.getPrefLanguageFR());
        values.put(KEY_PREF_LANGUAGE_EN, pref.getPrefLanguageEN());
        values.put(KEY_PREF_HAIR_COLOR_BLOND, pref.getPrefHairColorBlond());
        values.put(KEY_PREF_HAIR_COLOR_BLACK, pref.getPrefHairColorBlack());
        values.put(KEY_PREF_HAIR_COLOR_BROWN, pref.getPrefHairColorBrown());
        values.put(KEY_PREF_HAIR_COLOR_GINGER, pref.getPrefHairColorGinger());
        values.put(KEY_PREF_HAIR_COLOR_GREYWHITE, pref.getPrefHairColorGreywhite());
        values.put(KEY_PREF_HAIR_COLOR_OTHER, pref.getPrefHairColorOther());
        values.put(KEY_PREF_HAIR_TYPE_LONG, pref.getPrefHairTypeLong());
        values.put(KEY_PREF_HAIR_TYPE_MEDIUM, pref.getPrefHairTypeMedium());
        values.put(KEY_PREF_HAIR_TYPE_SHORT, pref.getPrefHairTypeShort());
        values.put(KEY_PREF_EYES_COLOR_BLACK, pref.getPrefEyesColorBlack());
        values.put(KEY_PREF_EYES_COLOR_BLUE, pref.getPrefEyesColorBlue());
        values.put(KEY_PREF_EYES_COLOR_BROWN, pref.getPrefEyesColorBrown());
        values.put(KEY_PREF_EYES_COLOR_GREEN, pref.getPrefEyesColorGreen());
        values.put(KEY_PREF_EYES_COLOR_GREY, pref.getPrefEyesColorGrey());
        values.put(KEY_PREF_AGE_MAX, pref.getPrefAgeMax());
        values.put(KEY_PREF_AGE_MIN, pref.getPrefAgeMin());
        values.put(KEY_PREF_DISTANCE_MAX, pref.getPrefDistanceMax());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int modPreferences(Preferences pref) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_PREF_USER_ID, pref.getPrefUserID());
        values.put(KEY_PREF_LANGUAGE_FR, pref.getPrefLanguageFR());
        values.put(KEY_PREF_LANGUAGE_EN, pref.getPrefLanguageEN());
        values.put(KEY_PREF_HAIR_COLOR_BLOND, pref.getPrefHairColorBlond());
        values.put(KEY_PREF_HAIR_COLOR_BLACK, pref.getPrefHairColorBlack());
        values.put(KEY_PREF_HAIR_COLOR_BROWN, pref.getPrefHairColorBrown());
        values.put(KEY_PREF_HAIR_COLOR_GINGER, pref.getPrefHairColorGinger());
        values.put(KEY_PREF_HAIR_COLOR_GREYWHITE, pref.getPrefHairColorGreywhite());
        values.put(KEY_PREF_HAIR_COLOR_OTHER, pref.getPrefHairColorOther());
        values.put(KEY_PREF_HAIR_TYPE_LONG, pref.getPrefHairTypeLong());
        values.put(KEY_PREF_HAIR_TYPE_MEDIUM, pref.getPrefHairTypeMedium());
        values.put(KEY_PREF_HAIR_TYPE_SHORT, pref.getPrefHairTypeShort());
        values.put(KEY_PREF_EYES_COLOR_BLACK, pref.getPrefEyesColorBlack());
        values.put(KEY_PREF_EYES_COLOR_BLUE, pref.getPrefEyesColorBlue());
        values.put(KEY_PREF_EYES_COLOR_BROWN, pref.getPrefEyesColorBrown());
        values.put(KEY_PREF_EYES_COLOR_GREEN, pref.getPrefEyesColorGreen());
        values.put(KEY_PREF_EYES_COLOR_GREY, pref.getPrefEyesColorGrey());
        values.put(KEY_PREF_AGE_MAX, pref.getPrefAgeMax());
        values.put(KEY_PREF_AGE_MIN, pref.getPrefAgeMin());
        values.put(KEY_PREF_DISTANCE_MAX, pref.getPrefDistanceMax());

        String where = KEY_PREF_ID+" = ?";
        String[] whereArgs = {pref.getPrefID()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int supPreferences(Preferences pref) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_PREF_ID+" = ?";
        String[] whereArgs = {pref.getPrefID()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Preferences getPreferences(long pref_id) {
        // Retourne la preference dont l'id est passé en paramètre
        Preferences a = new Preferences(0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0,0,0,0,0);

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_PREF_ID+"="+pref_id, null);
        if (c.moveToFirst()) {
            a.setPrefID(c.getInt(c.getColumnIndex(KEY_PREF_ID)));
            a.setPrefUserID(c.getString(c.getColumnIndex(KEY_PREF_USER_ID)));
            a.setPrefLanguageFR(c.getString(c.getColumnIndex(KEY_PREF_LANGUAGE_FR)));
            a.setPrefLanguageEN(c.getString(c.getColumnIndex(KEY_PREF_LANGUAGE_EN)));
            a.setPrefHairColorBlond(c.getString(c.getColumnIndex(KEY_PREF_HAIR_COLOR_BLOND)));
            a.setPrefHairColorBrown(c.getString(c.getColumnIndex(KEY_PREF_HAIR_COLOR_BROWN)));
            a.setPrefHairColorBlack(c.getString(c.getColumnIndex(KEY_PREF_HAIR_COLOR_BLACK)));
            a.setPrefHairColorGinger(c.getString(c.getColumnIndex(KEY_PREF_HAIR_COLOR_GINGER)));
            a.setPrefHairColorGreywhite(c.getString(c.getColumnIndex(KEY_PREF_HAIR_COLOR_GREYWHITE)));
            a.setPrefHairColorOther(c.getString(c.getColumnIndex(KEY_PREF_HAIR_COLOR_OTHER)));
            a.setPrefHairTypeLong(c.getString(c.getColumnIndex(KEY_PREF_HAIR_TYPE_LONG)));
            a.setPrefHairTypeMedium(c.getString(c.getColumnIndex(KEY_PREF_HAIR_TYPE_MEDIUM)));
            a.setPrefHairTypeShort(c.getString(c.getColumnIndex(KEY_PREF_HAIR_TYPE_SHORT)));
            a.setPrefEyesColorBlack(c.getString(c.getColumnIndex(KEY_PREF_EYES_COLOR_BLACK)));
            a.setPrefEyesColorBlue(c.getBlob(c.getColumnIndex(KEY_PREF_EYES_COLOR_BLUE)));
            a.setPrefEyesColorBrown();c.getBlob(c.getColumnIndex(KEY_PREF_EYES_COLOR_BROWN));
            a.setPrefEyesColorGreen(c.getBlob(c.getColumnIndex(KEY_PREF_EYES_COLOR_GREEN)));
            a.setPrefEyesColorGrey(c.getBlob(c.getColumnIndex(KEY_PREF_EYES_COLOR_GREY)));
            a.setPrefAgeMax(c.getBlob(c.getColumnIndex(KEY_PREF_AGE_MAX)));
            a.setPrefAgeMin(c.getBlob(c.getColumnIndex(KEY_PREF_AGE_MIN)));
            a.setPrefDistanceMax(c.getBlob(c.getColumnIndex(KEY_PREF_DISTANCE_MAX)));

            c.close();
        }

        return a;
    }

    public Cursor getUsers() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }

} // class UserManager