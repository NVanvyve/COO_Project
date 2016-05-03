/**
 * Classe créée par Jean-François GAZET, trouvée sur http://fr.jeffprod.com/blog/2015/utilisation-d-une-base-sqlite-sous-android.html
 * Elle a cependant été adaptée à notre base de données.
 */

package com.lsinf1225.groupeo.uclove;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lsinf1225.groupeo.uclove.database.CalendarManager;
import com.lsinf1225.groupeo.uclove.database.MessageManager;
import com.lsinf1225.groupeo.uclove.database.NotifAuthManager;
import com.lsinf1225.groupeo.uclove.database.NotificationManager;
import com.lsinf1225.groupeo.uclove.database.PreferencesManager;
import com.lsinf1225.groupeo.uclove.database.RDVManager;
import com.lsinf1225.groupeo.uclove.database.RelationManager;
import com.lsinf1225.groupeo.uclove.database.UserManager;

public class MySQLite extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db.sqlite";
    private static final int DATABASE_VERSION = 8;
    private static MySQLite sInstance;

    public static synchronized MySQLite getInstance(Context context) {
        if (sInstance == null) { sInstance = new MySQLite(context); }
        return sInstance;
    }

    private MySQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Création de la base de données
        // on exécute ici les requêtes de création des tables
        sqLiteDatabase.execSQL("PRAGMA foreign_keys = ON;");
        sqLiteDatabase.execSQL(UserManager.CREATE_TABLE_USER); // création table "User"
        sqLiteDatabase.execSQL(RDVManager.CREATE_TABLE_RDV); // création table "RDV"
        sqLiteDatabase.execSQL(MessageManager.CREATE_TABLE_MESSAGE); // création table "RDV"
        sqLiteDatabase.execSQL(NotificationManager.CREATE_TABLE_NOTIFICATION); // création table "Notification"
        sqLiteDatabase.execSQL(NotifAuthManager.CREATE_TABLE_NOTIFAUTH); // création table "NotifAuth"
        sqLiteDatabase.execSQL(RelationManager.CREATE_TABLE_RELATION); // création table "Relation"
        sqLiteDatabase.execSQL(RelationManager.TABLE_RELATION_TRIGGER); // trigger
        sqLiteDatabase.execSQL(CalendarManager.CREATE_TABLE_CALENDAR); // création table "Calendar"
        sqLiteDatabase.execSQL(PreferencesManager.CREATE_TABLE_PREFERENCES); // création table "Preferences"
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        // Mise à jour de la base de données
        // méthode appelée sur incrémentation de DATABASE_VERSION
        // on peut faire ce qu'on veut ici, comme recréer la base :
        onCreate(sqLiteDatabase);
    }

} // class MySQLite