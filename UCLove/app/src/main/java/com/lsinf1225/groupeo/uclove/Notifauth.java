/**
 * Classe créée par Jean-François GAZET, trouvée sur http://fr.jeffprod.com/blog/2015/utilisation-d-une-base-sqlite-sous-android.html
 * Elle a cependant été adaptée à notre base de données.
 */

package com.lsinf1225.groupeo.uclove;

public class Notifauth {

    private int notifauth_id;
    private int notifauth_user_id;
    private int notifauth_code;
    private int notifauth_bool;


    // Constructeur
    public Notifauth(int notifauth_id,
                     int notifauth_user_id,
                     int notifauth_code,
                     int notifauth_bool) {
        this.notifauth_id=notifauth_id;
        this.notifauth_user_id=notifauth_user_id;
        this.notifauth_code=notifauth_code;
        this.notifauth_bool=notifauth_bool;
        }


    // NotifAuth ID
    public int getNotifauthID() {
        return notifauth_id;
    }

    public void setNotifauthID(int notifauth_id) {
        this.notifauth_id = notifauth_id;
    }



    // NotifAuth User ID
    public int getNotifauthUserID() {
        return notifauth_user_id;
    }

    public void setNotifauthUserID(int notifauth_user_id) {
        this.notifauth_user_id = notifauth_user_id;
    }



    // NotifAuth Code
    public int getNotifauthCode() {
        return notifauth_code;
    }

    public void setNotifauthCode(int notifauth_code) {
        this.notifauth_code = notifauth_code;
    }



    // NotifAuth Bool
    public int getNotifauthBool() {
        return notifauth_bool;
    }

    public void setNotifauthBool(int notifauth_bool) {
        this.notifauth_bool = notifauth_bool;
    }

} // class NotifAuth

