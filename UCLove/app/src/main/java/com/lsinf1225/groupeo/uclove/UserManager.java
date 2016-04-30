/**
 * Classe créée par Jean-François GAZET, trouvée sur http://fr.jeffprod.com/blog/2015/utilisation-d-une-base-sqlite-sous-android.html
 * Elle a cependant été adaptée à notre base de données.
 */

package com.lsinf1225.groupeo.uclove;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.provider.Settings;

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

        User a = new User(0,"", "", "", "", "", "", "", "", "", "", "", "", "", "");

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
            a.setUserProfilePic(c.getString(c.getColumnIndex(KEY_USER_PROFILE_PICTURE)));
            c.close();
        }

        return a;
    }

    public Cursor getUsers() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }

    public boolean canCreateAccount(User user) {
        // Vérifie si l'utilisateur est déjà dans la base de données au moment de l'inscription.

        String userName = user.getUserUsername();
        String firstName = user.getUserFirstName();
        String lastName = user.getUserLastName();
        String birthDate = user.getUserBirthDate();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_USER_FIRST_NAME + "='" + firstName +
                "' AND " + KEY_USER_LAST_NAME + "='" + lastName +
                "' AND " + KEY_USER_BIRTH_DATE + "='" + birthDate +
                "' AND " + KEY_USER_USERNAME + "='" + userName +
                "'";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            c.close();
            return true;
        } else {
            c.close();
            return true;
        }
    }

    public long isAlreadyInDatabase(String userName, String password) {
        // retourne le user_id de l'utilisateur s'il est déjà inscrit, sinon retourne -1

        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_USER_USERNAME+"="+userName+
                "' AND "+KEY_USER_PASSWORD+"='"+password+
                "'";
        Cursor c = db.rawQuery(query, null);
        if(c.moveToFirst()){
            long userID = c.getLong(c.getColumnIndex(KEY_USER_ID));
            c.close();
            return userID;
        } else {
            c.close();
            return -1;
        }
    }
    public String formatquery(String str[]){
        if(str[0]==null){
            return(null);
        }
        else{
            String acc="AND ( ";
            int i=0;
            while(str[i]!=null){
                if(str[i+1]==null){
                    acc+=str[i]+" )";
                    return(acc);
                }
                else{
                    acc+=str[i]+"  OR";
                }
            }
        }
        return(null);
    }

    public long userSearcher(User user){

     //retourne un utilisateur qui satisfait les critères de recherche de user et avec lequel il n'a pas encore interragis

        PreferencesManager m = new PreferencesManager(this);
        m.open();
        Preferences newPrefs = m.getPreferences(user.getUserID());
        m.close();

        String query="select user_id FROM User U Relation R WHERE ";
        String haircolorpref[]=new String[7] ;
        int hc=0;
        String hairTypepref[]=new String[4];
        int ht=0;
        String eyecolorpref[]=new String[6];
        int ec=0;
        if(newPrefs.getPrefHairColorBlond()==1) {

            haircolorpref[hc]="U.user_hair_color = 'Blond'";
            hc+=1;
        }
        if(newPrefs.getPrefHairColorBrown()==1) {
            haircolorpref[hc]="U.user_hair_color = 'Brown'";
            hc+=1;
        }
        if(newPrefs.getPrefHairColorBlack()==1) {
            haircolorpref[hc]="U.user_hair_color = 'Black'";
            hc+=1;
        }
        if(newPrefs.getPrefHairColorGinger()==1) {
            haircolorpref[hc]="U.user_hair_color = 'Ginger'";
            hc+=1;
        }
        if(newPrefs.getPrefHairColorGreywhite()==1) {
            haircolorpref[hc]="U.user_hair_color = 'Grey/white'";
            hc+=1;
        }
        if(newPrefs.getPrefHairColorOther()==1) {
            haircolorpref[hc]="U.user_hair_color = 'Other'";
            hc+=1;
        }
        if(newPrefs.getPrefHairTypeShort()==1) {
            hairTypepref[ht]="U.user_hair_type = 'Short'";
            ht+=1;
        }
        if(newPrefs.getPrefHairTypeMedium()== 1) {
            hairTypepref[ht]="U.user_hair_type = 'Medium'";
            ht+=1;
        }
        if(newPrefs.getPrefHairTypeLong()== 1) {
            hairTypepref[ht]="U.user_hair_type = 'Long'";
            ht+=1;
        }
        if(newPrefs.getPrefEyesColorBlue()==1) {
            eyecolorpref[ec]="U.user_eyes_color = 'Blue'";
            ec+=1;
        }
        if(newPrefs.getPrefEyesColorBrown()==1) {
            eyecolorpref[ec]="U.user_eyes_color = 'Brown'";
            ec+=1;
        }
        if(newPrefs.getPrefEyesColorBlack()==1) {
            eyecolorpref[ec]="U.user_eyes_color = 'Black'";
            ec+=1;
        }
        if(newPrefs.getPrefEyesColorGreen()==1) {
            eyecolorpref[ec]="U.user_eyes_color = 'green'";
            ec+=1;
        }
        if(newPrefs.getPrefEyesColorGrey()==1) {
            eyecolorpref[ec]="U.user_eyes_color = 'Grey'";
            ec+=1;
        }
        query+="( U.user_id <>'"+String.valueOf(user.getUserID())+"' AND cast(strftime('%Y.%m%d', 'now') - strftime('%Y.%m%d', U.user_birth_date) as int) <='"+String.valueOf(newPrefs.getPrefAgeMax())+"' AND cast(strftime('%Y.%m%d', 'now') - strftime('%Y.%m%d', U.user_birth_date) as int) >='"+String.valueOf(newPrefs.getPrefAgeMin())+"' )";
        query+=formatquery(haircolorpref);
        query+=formatquery(hairTypepref);
        query+=formatquery(eyecolorpref);
        if(user.getUserSexuality().equals("heterosexual")){
            query+=" AND ( U.user_sex <>'"+user.getUserSex()+"' AND (U.user_sexuality ='heterosexual' OR U.user_sexuality='bisexual' )";
        }
        else if(user.getUserSexuality().equals("homosexual")){
            query+=" AND ( U.user_sex ='"+user.getUserSex()+"' AND (U.user_sexuality ='homosexual' OR U.user_sexuality='bisexual'  )";
        }
        else{
            query+=" AND ( (U.user_sexuality ='homosexual' AND U.user_sexuality <>'"+user.getUserSex()+"') OR (U.user_sexuality='bisexual') OR (U.user_sexuality ='heterosexual' AND U.user_sexuality ='"+user.getUserSex()+"')  )";
        }
        query+=" AND ( U.user_id = P.user_id_a AND P.user_id_b='"+String.valueOf(user.getUserID())+"' AND P.rel_status='request'";
        Cursor d = db.rawQuery(query,null);
        if(d.moveToFirst()){
            long userID = d.getLong(d.getColumnIndex(KEY_USER_ID));
            d.close();
            return(userID);

        }
        else {
            d.close();
            return -1;
        }





    }


} // class UserManager