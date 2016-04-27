/**
 * Classe créée par Jean-François GAZET, trouvée sur http://fr.jeffprod.com/blog/2015/utilisation-d-une-base-sqlite-sous-android.html
 * Elle a cependant été adaptée à notre base de données.
 */

package com.lsinf1225.groupeo.uclove;

public class RDV {

    private int rdv_id;
    private int rdv_user_id;
    private int rdv_rel_id;
    private String rdv_date;
    private String rdv_status;

    // Constructeur
    public RDV(int rdv_id,
                int rdv_user_id,
                int rdv_rel_id,
                String rdv_date,
                String rdv_status)
        this.rdv_user_id=rdv_user_id;
        this.rdv_rel_id=rdv_rel_id;
        this.rdv_date=rdv_date;
        this.rdv_status=rdv_status;

    }

    // RDV_ID
    public int getRDVID() {
        return rdv_id;
    }

    public void setRDVID(int rdv_id) {this.rdv_id = rdv_id;}

    //RDV_User_ID
    public int getRDVUserID() {
        return rdv_user_id;
    }

    public void setRDVUserID(int rdv_user_id) {
        this.rdv_user_id = rdv_user_id;
    }

    //RDV_Rel_ID
    public int getRDVRelID() {
        return rdv_rel_id;
    }

    public void setRDVRelID(int rdv_rel_id) {
        this.rdv_rel_id = rdv_rel_id;
    }

    //RDV_Date
    public String getRDVDate() {
        return rdv_date;
    }

    public void setRDVDate(String rdv_date) {
        this.rdv_date = rdv_date;
    }

    //RDV_Status
    public String getRDVStatus() {
        return rdv_status;
    }

    public void setRDVStatus(String rdv_status) {
        this.rdv_status = rdv_status;
    }

} // class RDV
