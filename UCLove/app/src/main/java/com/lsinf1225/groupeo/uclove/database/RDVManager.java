/**
 * Classe créée par Jean-François GAZET, trouvée sur http://fr.jeffprod.com/blog/2015/utilisation-d-une-base-sqlite-sous-android.html
 * Elle a cependant été adaptée à notre base de données.
 */

package com.lsinf1225.groupeo.uclove.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lsinf1225.groupeo.uclove.MySQLite;


public class RDVManager {

    private static final String TABLE_NAME = "RDV";
    private static final String KEY_RDV_ID = "rdv_id";
    public static final String KEY_RDV_REL_ID="rdv_rel_id";
    public static final String KEY_RDV_USER_ID_A="rdv_user_id_a";
    public static final String KEY_RDV_USER_ID_B="rdv_user_id_b";
    public static final String KEY_RDV_DATE="rdv_date";
    public static final String KEY_RDV_STATUS="rdv_status";
    public static final String CREATE_TABLE_RDV = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_RDV_ID+" INTEGER not null primary key," +
            " "+KEY_RDV_REL_ID+" INTEGER not null references Relation," +
            " "+KEY_RDV_USER_ID_A+" INTEGER not null references User," +
            " "+KEY_RDV_USER_ID_B+" INTEGER not null references User," +
            " "+KEY_RDV_DATE+" TEXT not null," +
            " "+KEY_RDV_STATUS+" TEXT not null," +
            " unique("+KEY_RDV_USER_ID_A+", "+KEY_RDV_REL_ID+", "+KEY_RDV_DATE+") " +
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
        values.put(KEY_RDV_USER_ID_A, rdv.getRDVUserIDA());
        values.put(KEY_RDV_USER_ID_B, rdv.getRDVUserIDB());
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
        values.put(KEY_RDV_USER_ID_A, rdv.getRDVUserIDA());
        values.put(KEY_RDV_USER_ID_B, rdv.getRDVUserIDB());
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
        RDV a = new RDV(0, 0, 0, 0, "", "");

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_RDV_ID+"="+rdv_id, null);
        if (c.moveToFirst()) {
            a.setRDVID(c.getInt(c.getColumnIndex(KEY_RDV_ID)));
            a.setRDVUserIDA(c.getInt(c.getColumnIndex(KEY_RDV_USER_ID_A)));
            a.setRDVUserIDB(c.getInt(c.getColumnIndex(KEY_RDV_USER_ID_B)));
            a.setRDVRelID(c.getInt(c.getColumnIndex(KEY_RDV_REL_ID)));
            a.setRDVDate(c.getString(c.getColumnIndex(KEY_RDV_DATE)));
            a.setRDVStatus(c.getString(c.getColumnIndex(KEY_RDV_STATUS)));
            c.close();
        }

        return a;
    }

    public RDV getRDVRequest(long user_id){
        // Retourne une demande de rdv
        RDV a = new RDV(-1, -1, -1, -1, "", "");
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_RDV_USER_ID_B+"="+user_id+" AND "+KEY_RDV_STATUS+"='Request'";
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            a.setRDVID(c.getInt(c.getColumnIndex(KEY_RDV_ID)));
            a.setRDVUserIDA(c.getInt(c.getColumnIndex(KEY_RDV_USER_ID_A)));
            a.setRDVUserIDB(c.getInt(c.getColumnIndex(KEY_RDV_USER_ID_B)));
            a.setRDVRelID(c.getInt(c.getColumnIndex(KEY_RDV_REL_ID)));
            a.setRDVDate(c.getString(c.getColumnIndex(KEY_RDV_DATE)));
            a.setRDVStatus(c.getString(c.getColumnIndex(KEY_RDV_STATUS)));
        } else {
            a.setRDVID(-1);
        }
        c.close();

        return a;
    }

    public RDV getRDVAccepted(long user_id, int number){
        //Retourne le n-ieme rendez-vous accepté
        int loop = 0;
        RDV a = new RDV(-1, -1, -1, -1, "", "");

        String query = "SELECT * FROM "+TABLE_NAME+" WHERE ("+KEY_RDV_USER_ID_A+"="+user_id+" OR "+KEY_RDV_USER_ID_B+"="+user_id+") AND "+KEY_RDV_STATUS+"='Accepted'";

        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do{
                a.setRDVID(c.getInt(c.getColumnIndex(KEY_RDV_ID)));
                a.setRDVUserIDA(c.getInt(c.getColumnIndex(KEY_RDV_USER_ID_A)));
                a.setRDVUserIDB(c.getInt(c.getColumnIndex(KEY_RDV_USER_ID_B)));
                a.setRDVRelID(c.getInt(c.getColumnIndex(KEY_RDV_REL_ID)));
                a.setRDVDate(c.getString(c.getColumnIndex(KEY_RDV_DATE)));
                a.setRDVStatus(c.getString(c.getColumnIndex(KEY_RDV_STATUS)));
                loop++;
            }
            while(c.moveToNext() && (loop < number));
        }
        c.close();
        if (loop < number) {
            a.setRDVID(-1);
        }
        return a;
    }

    public Cursor getRDVs() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }

} // class RDVManager       
