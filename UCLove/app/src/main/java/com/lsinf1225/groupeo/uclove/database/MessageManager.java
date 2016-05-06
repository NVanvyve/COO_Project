package com.lsinf1225.groupeo.uclove.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lsinf1225.groupeo.uclove.MySQLite;

public class MessageManager {

    private static final String TABLE_NAME = "Message";
    public static final String KEY_MESSAGE_ID = "message_id";
    public static final String KEY_MESSAGE_REL_ID = "message_rel_id";
    public static final String KEY_MESSAGE_USER_ID = "message_user_id";
    public static final String KEY_MESSAGE_DATE = "message_date";
    public static final String KEY_MESSAGE_TEXT = "message_text";
    public static final String CREATE_TABLE_MESSAGE = "CREATE TABLE " + TABLE_NAME +
            " (" +
            " " + KEY_MESSAGE_ID + " INTEGER not null primary key," +
            " " + KEY_MESSAGE_REL_ID + " INTEGER not null references Relation," +
            " " + KEY_MESSAGE_USER_ID + " INTEGER not null references User," +
            " " + KEY_MESSAGE_DATE + " TEXT not null," +
            " " + KEY_MESSAGE_TEXT + " TEXT not null," +
            " unique(" + KEY_MESSAGE_USER_ID + ", " + KEY_MESSAGE_DATE + ") " +
            ");";
    private MySQLite maBaseSQLite; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;

    // Constructeur
    public MessageManager(Context context) {
        maBaseSQLite = MySQLite.getInstance(context);
    }

    public void open() {
        //on ouvre la table en lecture/écriture
        db = maBaseSQLite.getWritableDatabase();
    }

    public void close() {
        //on ferme l'accès à la BDD
        db.close();
    }

    public long addMessage(Message message) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_MESSAGE_REL_ID, message.getMessageRelID());
        values.put(KEY_MESSAGE_USER_ID, message.getMessageUserID());
        values.put(KEY_MESSAGE_DATE, message.getMessageDate());
        values.put(KEY_MESSAGE_TEXT, message.getMessageText());


        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME, null, values);
    }

    public int modMessage(Message message) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_MESSAGE_REL_ID, message.getMessageRelID());
        values.put(KEY_MESSAGE_USER_ID, message.getMessageUserID());
        values.put(KEY_MESSAGE_DATE, message.getMessageDate());
        values.put(KEY_MESSAGE_TEXT, message.getMessageText());

        String where = KEY_MESSAGE_ID + " = ?";
        String[] whereArgs = {message.getMessageID() + ""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int supMessage(Message message) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_MESSAGE_ID + " = ?";
        String[] whereArgs = {message.getMessageID() + ""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Message getMessage(long message_id) {
        // Retourne le message dont l'id est passé en paramètre

        Message a = new Message(0, 0, 0, "", "");

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_MESSAGE_ID + "=" + message_id, null);
        if (c.moveToFirst()) {
            a.setMessageID(c.getInt(c.getColumnIndex(KEY_MESSAGE_ID)));
            a.setMessageUserID(c.getInt(c.getColumnIndex(KEY_MESSAGE_USER_ID)));
            a.setMessageRelID(c.getInt(c.getColumnIndex(KEY_MESSAGE_REL_ID)));
            a.setMessageDate(c.getString(c.getColumnIndex(KEY_MESSAGE_DATE)));
            a.setMessageText(c.getString(c.getColumnIndex(KEY_MESSAGE_TEXT)));
            c.close();
        }
        return a;
    }

    public Message getRecentMessage(long rel_id, int recent_number){
        // retourne le n-ieme message le plus récent

        int loop = 0;

        Message a = new Message(-1, -1, -1, "", "");

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_MESSAGE_REL_ID + "=" + rel_id + " ORDER BY "+ KEY_MESSAGE_ID, null);

        if(c.moveToFirst()){
            do{
                a.setMessageID(c.getInt(c.getColumnIndex(KEY_MESSAGE_ID)));
                a.setMessageRelID(c.getInt(c.getColumnIndex(KEY_MESSAGE_REL_ID)));
                a.setMessageUserID(c.getInt(c.getColumnIndex(KEY_MESSAGE_USER_ID)));
                a.setMessageDate(c.getString(c.getColumnIndex(KEY_MESSAGE_DATE)));
                a.setMessageText(c.getString(c.getColumnIndex(KEY_MESSAGE_TEXT)));
                loop++;
            }
            while(c.moveToNext() && (loop < recent_number));
            c.close();
        }

        if (loop < recent_number) {
            a.setMessageID(-1);
        }

        return a;

    }

    public Message getMostRecentMessage(long rel_id){
        // retourne le message le plus récent

        Message a = new Message(-1, -1, -1, "", "");

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_MESSAGE_REL_ID + "=" + rel_id + " ORDER BY "+KEY_MESSAGE_ID+" DESC";

        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            a.setMessageID(c.getInt(c.getColumnIndex(KEY_MESSAGE_ID)));
            a.setMessageRelID(c.getInt(c.getColumnIndex(KEY_MESSAGE_REL_ID)));
            a.setMessageUserID(c.getInt(c.getColumnIndex(KEY_MESSAGE_USER_ID)));
            a.setMessageDate(c.getString(c.getColumnIndex(KEY_MESSAGE_DATE)));
            a.setMessageText(c.getString(c.getColumnIndex(KEY_MESSAGE_TEXT)));
            c.close();
        }

        return a;

    }

    public Cursor getMessages() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }

}
