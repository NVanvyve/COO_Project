/**
 * Classe créée par Jean-François GAZET, trouvée sur http://fr.jeffprod.com/blog/2015/utilisation-d-une-base-sqlite-sous-android.html
 * Elle a cependant été adaptée à notre base de données.
 */

package com.lsinf1225.groupeo.uclove;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NotifauthManager {

    private static final String TABLE_NAME = "NotifAuth";
    public static final String KEY_NOTIFAUTH_ID="notifauth_id";
    public static final String KEY_USER_ID ="notifauth_user_id";
    public static final String KEY_NOTIF_CODE ="notifauth_code";
    public static final String KEY_BOOL ="notifauth_bool";
    public static final String CREATE_TABLE_RELATION = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_NOTIFAUTH_ID+" INTEGER not null primary key," +
            " "+KEY_USER_ID+" INTEGER not null references User," +
            " "+KEY_NOTIF_CODE+" INTEGER not null," +
            " "+KEY_BOOL+" INTEGER not null," +
            " unique("+KEY_USER_ID+", "+KEY_NOTIF_CODE+", "+KEY_BOOL+") " +
            ");";
    private MySQLite maBaseSQLite; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;


    // Constructeur
    public NotifauthManager(Context context)
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

    public long addRelation(Notifauth notifauth) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_NOTIFAUTH_ID,notifauth.getNotifauthID() );
        values.put(KEY_USER_ID,notifauth.getNotifauthUserID());
        values.put(KEY_NOTIF_CODE,notifauth.getNotifauthCode() );
        values.put(KEY_BOOL, notifauth.getNotifauthBool());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int modRelation(Notifauth notifauth) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_NOTIFAUTH_ID,notifauth.getNotifauthID() );
        values.put(KEY_USER_ID,notifauth.getNotifauthUserID());
        values.put(KEY_NOTIF_CODE,notifauth.getNotifauthCode() );
        values.put(KEY_BOOL, notifauth.getNotifauthBool());


        String where = KEY_NOTIFAUTH_ID+" = ?";
        String[] whereArgs = {notifauth.getNotifauthID()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int supUser(Notifauth notifauth) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_NOTIFAUTH_ID+" = ?";
        String[] whereArgs = {notifauth.getNotifauthID()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Notifauth getNotifauth(long notifauth_id) {
        // Retourne la Notifauth dont l'id est passé en paramètre
        Notifauth a = new Notifauth(0,0,0,0);

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_NOTIFAUTH_ID+"="+notifauth_id, null);
        if (c.moveToFirst()) {
            a.setNotifauthID(c.getInt(c.getColumnIndex(KEY_NOTIFAUTH_ID)));
            a.setNotifauthUserID(c.getInt(c.getColumnIndex(KEY_USER_ID)));
            a.setNotifauthCode(c.getInt(c.getColumnIndex(KEY_NOTIF_CODE)));
            a.setNotifauthBool(c.getInt(c.getColumnIndex(KEY_BOOL)));
            c.close();
        }

        return a;
    }

    public Cursor getNotifauths() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }

} // class RelationManager
