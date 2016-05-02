/**
 * Classe créée par Jean-François GAZET, trouvée sur http://fr.jeffprod.com/blog/2015/utilisation-d-une-base-sqlite-sous-android.html
 * Elle a cependant été adaptée à notre base de données.
 */

package com.lsinf1225.groupeo.uclove.database;

public class NotifAuth {

    private int notifauth_id;
    private int notifauth_user_id;
    private int notifauth_code;
    private int notifauth_bool;


    // Constructeur
    public NotifAuth(int notifauth_id,
                     int notifauth_user_id,
                     int notifauth_code,
                     int notifauth_bool) {
        this.notifauth_id=notifauth_id;
        this.notifauth_user_id=notifauth_user_id;
        this.notifauth_code=notifauth_code;
        this.notifauth_bool=notifauth_bool;
    }


    // NotifAuth ID
    public int getNotifAuthID() {
        return notifauth_id;
    }

    public void setNotifAuthID(int notifauth_id) {
        this.notifauth_id = notifauth_id;
    }

    // NotifAuth User ID
    public int getNotifAuthUserID() {
        return notifauth_user_id;
    }

    public void setNotifAuthUserID(int notifauth_user_id) {
        this.notifauth_user_id = notifauth_user_id;
    }

    // NotifAuth Code
    public int getNotifAuthNotifCode() {
        return notifauth_code;
    }

    public void setNotifAuthNotifCode(int notifauth_code) {
        this.notifauth_code = notifauth_code;
    }



    // NotifAuth Bool
    public int getNotifAuthBool() {
        return notifauth_bool;
    }

    public void setNotifAuthBool(int notifauth_bool) {
        this.notifauth_bool = notifauth_bool;
    }

} // class NotifAuth
