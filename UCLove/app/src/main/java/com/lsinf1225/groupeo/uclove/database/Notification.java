/**
 * Classe créée par Jean-François GAZET, trouvée sur http://fr.jeffprod.com/blog/2015/utilisation-d-une-base-sqlite-sous-android.html
 * Elle a cependant été adaptée à notre base de données.
 */

package com.lsinf1225.groupeo.uclove.database;

public class Notification {

    private int notif_id;
    private int notif_user_id;
    private String notif_date;
    private String notif_text;
    private String notif_status;
    private int notif_code;

    // Constructeur
    public Notification(int notif_id,
                        int notif_user_id,
                        String notif_date,
                        String notif_text,
                        String notif_status,
                        int notif_code) {
        this.notif_id=notif_id;
        this.notif_user_id=notif_user_id;
        this.notif_date=notif_date;
        this.notif_text=notif_text;
        this.notif_status=notif_status;
        this.notif_code=notif_code;

    }

    //NOTIF_ID
    public int getNotifID() {
        return notif_id;
    }

    public void setNotifID(int notif_id) {
        this.notif_id = notif_id;
    }

    //NOTIF_USER_ID
    public int getNotifUserID() {
        return notif_user_id;
    }

    public void setNotifUserID(int notif_user_id) {
        this.notif_user_id = notif_user_id;
    }

    //NOTIF_DATE
    public String getNotifDate() {
        return notif_date;
    }

    public void setNotifDate(String notif_date) {
        this.notif_date = notif_date;
    }

    //NOTIF_TEXT
    public String getNotifText() {
        return notif_text;
    }

    public void setNotifText(String notif_text) {
        this.notif_text = notif_text;
    }

    //NOTIF_STATUS
    public String getNotifStatus() {
        return notif_status;
    }

    public void setNotifStatus(String notif_status) {
        this.notif_status = notif_status;
    }

    //NOTIF_CODE
    public int getNotifCode() {
        return notif_code;
    }

    public void setNotifCode(int notif_code) {
        this.notif_code = notif_code;
    }

} // class Notification
