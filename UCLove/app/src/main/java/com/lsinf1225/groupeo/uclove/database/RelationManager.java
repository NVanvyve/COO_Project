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

public class RelationManager {

    private static final String TABLE_NAME = "Relation";
    public static final String KEY_RELATION_ID="rel_id";
    public static final String KEY_RELATION_USER_ID_A ="rel_user_id_a";
    public static final String KEY_RELATION_USER_ID_B ="rel_user_id_b";
    public static final String KEY_RELATION_STATUS ="rel_status";
    public static final String KEY_RELATION_FAV_A = "rel_fav_a";
    public static final String KEY_RELATION_FAV_B = "rel_fav_b";
    public static final String CREATE_TABLE_RELATION = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_RELATION_ID+" INTEGER not null primary key," +
            " "+KEY_RELATION_USER_ID_A+" INTEGER not null references User," +
            " "+KEY_RELATION_USER_ID_B+" INTEGER not null references User," +
            " "+KEY_RELATION_STATUS+" TEXT not null," +
            " "+KEY_RELATION_FAV_A+" INTEGER not null default 0," +
            " "+KEY_RELATION_FAV_B+" INTEGER not null default 0," +
            " unique("+KEY_RELATION_USER_ID_A+", "+KEY_RELATION_USER_ID_A+") " +
            ");";
    private MySQLite maBaseSQLite; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;

    // Constructeur
    public RelationManager(Context context)
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

    public long addRelation(Relation relation) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_RELATION_USER_ID_A, relation.getRelUserIDA());
        values.put(KEY_RELATION_USER_ID_B, relation.getRelUserIDB());
        values.put(KEY_RELATION_STATUS, relation.getRelSatus());
        values.put(KEY_RELATION_FAV_A, relation.getRelFavA());
        values.put(KEY_RELATION_FAV_B, relation.getRelFavB());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int modRelation(Relation relation) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_RELATION_USER_ID_A, relation.getRelUserIDA());
        values.put(KEY_RELATION_USER_ID_B, relation.getRelUserIDB());
        values.put(KEY_RELATION_STATUS, relation.getRelSatus());
        values.put(KEY_RELATION_FAV_A, relation.getRelFavA());
        values.put(KEY_RELATION_FAV_B, relation.getRelFavB());

        String where = KEY_RELATION_ID+" = ?";
        String[] whereArgs = {relation.getRelID()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int supRelation(Relation relation) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_RELATION_ID+" = ?";
        String[] whereArgs = {relation.getRelID()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Relation getRelation(long rel_id) {
        // Retourne la relation dont l'id est passé en paramètre
        Relation a = new Relation(0, 0, 0, "", 0, 0);

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_RELATION_ID+"="+rel_id, null);
        if (c.moveToFirst()) {
            a.setRelID(c.getInt(c.getColumnIndex(KEY_RELATION_ID)));
            a.setRelUserIDA(c.getInt(c.getColumnIndex(KEY_RELATION_USER_ID_A)));
            a.setRelUserIDB(c.getInt(c.getColumnIndex(KEY_RELATION_USER_ID_B)));
            a.setRelStatus(c.getString(c.getColumnIndex(KEY_RELATION_STATUS)));
            a.setRelFavA(c.getInt(c.getColumnIndex(KEY_RELATION_FAV_A)));
            a.setRelFavB(c.getInt(c.getColumnIndex(KEY_RELATION_FAV_B)));
            c.close();
        }

        return a;
    }

    public Cursor getRelations() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }

} // class RelationManager