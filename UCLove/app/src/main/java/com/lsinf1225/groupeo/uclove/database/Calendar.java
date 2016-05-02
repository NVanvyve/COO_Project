/**
 * Classe créée par Jean-François GAZET, trouvée sur http://fr.jeffprod.com/blog/2015/utilisation-d-une-base-sqlite-sous-android.html
 * Elle a cependant été adaptée à notre base de données.
 */

package com.lsinf1225.groupeo.uclove.database;

public class Calendar {

    private int cal_id;
    private int cal_user_id;
    private String cal_date;
    private String cal_status;

    // Constructeur
    public Calendar(int cal_id,
                    int cal_user_id,
                    String cal_date,
                    String cal_status) {
        this.cal_id=cal_id;
        this.cal_user_id=cal_user_id;
        this.cal_date=cal_date;
        this.cal_status=cal_status;

    }

    //CAL_ID
    public int getCalID() {
        return cal_id;
    }

    public void setCalID(int cal_id) {
        this.cal_id = cal_id;
    }

    //CAL_USER_ID
    public int getCalUserID() {
        return cal_user_id;
    }

    public void setCalUserID(int cal_user_id) {
        this.cal_user_id = cal_user_id;
    }

    //CAL_DATE
    public String getCalDate() {
        return cal_date;
    }

    public void setCalDate(String cal_date) {
        this.cal_date = cal_date;
    }

    //CAL_STATUS
    public String getCalStatus() {
        return cal_status;
    }

    public void setCalStatus(String cal_status) {
        this.cal_status = cal_status;
    }

} // class Calendar