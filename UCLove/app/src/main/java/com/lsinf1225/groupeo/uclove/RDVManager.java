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

public class RDV {

    private static final int KEY_RDV_ID = "rdv_id";
    public static final int KEY_RDV_USER_ID="rdv_user_id";
    public static final int KEY_RDV_REL_ID="rdv_rel_id";
    public static final String KEY_RDV_DATE="rdv_date";
    public static final String KEY_RDV_STATUS="rdv_status";
    public static final String RendezVous = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_RDV_ID+" INTEGER not null primary key," +
            " "+KEY_RDV_USER_ID+" INTEGER not null references Utilisateur," +
            " "+KEY_RDV_REL_ID+" INTEGER not null references Relation," +
            " "+KEY_RDV_DATE+" TEXT not null," +
            " "+KEY_RDV_STATUS+" TEXT not null," +
            " unique("+KEY_RDV_ID+", "+KEY_RDV_REL_ID+", "+KEY_RDV_DATE+") " +
            ");";
    private MySQLite maBaseSQLite; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;

    // Constructeur
    public RDVManager(Context context)
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

    public long addRDV(RDV rdv) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_RDV_ID, rdv.getRDVID());
        values.put(KEY_RDV_USER_ID, rdv.getRDVUserID());
        values.put(KEY_RDV_REL_ID, rdv.getRDVRelID());
        values.put(KEY_RDV_DATE, rdv.getRDVDate());
        values.put(KEY_RDV_STATUS, rdv.getRDVStatus());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int modRDV(RDV rdv) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_RDV_ID, rdv.getRDVID());
        values.put(KEY_RDV_USER_ID, rdv.getRDVUserID());
        values.put(KEY_RDV_REL_ID, rdv.getRDVRelID());
        values.put(KEY_RDV_DATE, rdv.getRDVDate());
        values.put(KEY_RDV_STATUS, rdv.getRDVStatus());

        String where = KEY_RDV_ID+" = ?";
        String[] whereArgs = {rdv.getRDVID()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int supRDV(RDV rdv) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_RDV_ID+" = ?";
        String[] whereArgs = {rdv.getRDVID()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public RDV getRDV(long rdv_id) {
        // Retourne le rdv dont l'id est passé en paramètre
        RDV a = new RDV(0, 0, 0, "", "");

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_RDV_ID+"="+rdv_id, null);
        if (c.moveToFirst()) {
            a.setRDVID(c.getInt(c.getColumnIndex(KEY_RDV_ID)));
            a.setRDVUserID(c.getInt(c.getColumnIndex(KEY_RDV_USER_ID)));
            a.setRDVRelID(c.getInt(c.getColumnIndex(KEY_RDV_REL_ID)));
            a.setRDVDate(c.getString(c.getColumnIndex(KEY_RDV_DATE)));
            a.setRDVStatus(c.getString(c.getColumnIndex(KEY_RDV_STATUS)));
            c.close();
        }

        return a;
    }

    public Cursor getRDVs() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }

} // class RDVManager       
