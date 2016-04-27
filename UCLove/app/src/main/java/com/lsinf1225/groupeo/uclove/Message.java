/**
 * Classe créée par Jean-François GAZET, trouvée sur http://fr.jeffprod.com/blog/2015/utilisation-d-une-base-sqlite-sous-android.html
 * Elle a cependant été adaptée à notre base de données.
 */

package com.lsinf1225.groupeo.uclove;

public class Message {

    private int message_id;
    private int message_rel_id;
    private int message_user_id;
    private String message_date;
    private String message_text;


    // Constructeur
    public Message(int message_id,
                int message_rel_id,
                int message_user_id,
                String message_date,
                String message_text) {
        this.message_id=message_id;
        this.message_rel_id=message_rel_id;
        this.message_user_id=message_user_id;
        this.message_date=message_date;
        this.message_text=message_text;

    }

    // USERID
    public int getMessageID() {
        return message_id;
    }

    public void setMessageID(int message_id) {
        this.message_id = message_id;
    }

    //USERNAME
    public String getMessageRelID() {
        return message_rel_id;
    }

    public void setMessageRelID(int message_rel_id) {
        this.message_rel_id = message_rel_id;
    }

    //PASSWORD
    public String getMessageUserID() {
        return message_user_id;
    }

    public void setMessageUserID(int message_user_id) {
        this.message_user_id = message_user_id;
    }

    //FIRSTNAME
    public String getMessageDate() {
        return message_date;
    }

    public void setMessageDate(String message_date) {
        this.message_date = message_date;
    }

    //LASTNAME
    public String getMessageText() {
        return message_text;
    }

    public void setMessageText(String message_text) {
        this.message_text = message_text;
    }


} // class User