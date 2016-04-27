import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lsinf1225.groupeo.uclove.MySQLite;
import com.lsinf1225.groupeo.uclove.User;

public class MessageManager {

    private static final String TABLE_NAME = "Message";
    public static final String KEY_MESSAGE_ID = "message_id";
    public static final String KEY_MESSAGE_REL_ID = "rel_id";
    public static final String KEY_MESSAGE_USER_ID = "user_id";
    public static final String KEY_MESSAGE_DATE = "date";
    public static final String KEY_MESSAGE_TEXT_ = "message_text";
    public static final String CREATE_TABLE_MESSAGE = "CREATE TABLE " + TABLE_MESSAGE +
            " (" +
            " " + KEY_MESSAGE_ID + " INTEGER not null primary key," +
            " " + KEY_MESSAGE_REL_ID + " INTEGER not null," +
            " " + KEY_MESSAGE_USER_ID + " INTEGER not null," +
            " " + KEY_MESSAGE_DATE + " TEXT not null," +
            " " + KEY_MESSAGE_DATE + "D TEXT not null," +
            " unique(" + KEY_USER_FIRST_NAME + ", " + KEY_USER_LAST_NAME + ", " + KEY_USER_BIRTH_DATE + ") " +
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

    public long addUser(Message message) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_MESSAGE_ID, message.getMessageID());
        values.put(KEY_MESSAGE_REL_ID, message.getMessageRelID());
        values.put(KEY_MESSAGE_USER_ID, message.getMessageUserID());
        values.put(KEY_MESSAGE_DATE, message.getMessageDate());
        values.put(KEY_MESSAGE_TEXT_, message.getMessage_Date());


        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME, null, values);
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

        String where = KEY_USER_ID + " = ?";
        String[] whereArgs = {user.getUserID() + ""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int supUser(User user) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_USER_ID + " = ?";
        String[] whereArgs = {user.getUserID() + ""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public User getUser(long user_id) {
        // Retourne l'user dont l'id est passé en paramètre
        byte[] blob = {0};
        User a = new User(0, "", "", "", "", "", "", "", "", "", "", "", "", "", blob);

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_USER_ID + "=" + user_id, null);
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
        }
    }
}