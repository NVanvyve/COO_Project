/**
 * Classe créée par Jean-François GAZET, trouvée sur http://fr.jeffprod.com/blog/2015/utilisation-d-une-base-sqlite-sous-android.html
 * Elle a cependant été adaptée à notre base de données.
 */

package com.lsinf1225.groupeo.uclove;

public class Relation {

    private int rel_id;
    private int rel_user_id_a;
    private int rel_user_id_b;
    private String rel_status;
    private int rel_fav_a;
    private int rel_fav_b;

    // Constructeur
    public Relation(int rel_id,
                    int rel_user_id_a,
                    int rel_user_id_b,
                    String rel_status,
                    int rel_fav_a,
                    int rel_fav_b) {
        this.rel_id=rel_id;
        this.rel_user_id_a=rel_user_id_a;
        this.rel_user_id_b=rel_user_id_b;
        this.rel_status=rel_status;
        this.rel_fav_a;
        this.rel_fav_b;

    }

    // REL_ID
    public int getRelID() {

        return rel_id;
    }

    public void setRelID(int rel_id) {

        this.rel_id = rel_id;
    }

    // REL_USER_ID_A
    public int getRelUserIDA() {

        return rel_user_id_a;
    }

    public void setRelUserIDA(int rel_user_id_a) {

        this.rel_user_id_a = rel_user_id_a;
    }

    // REL_USER_ID_B
    public int getRelUserIDB() {

        return rel_user_id_b;
    }

    public void setRelUserIDB(int rel_user_id_b) {

        this.rel_user_id_b = rel_user_id_b;
    }

    // REL_STATUS
    public String getSatus(){

        return rel_status;
    }
    public void setRelStatus(String rel_status) {

        this.rel_status=rel_status;
    }

    //REL_FAV_A
    public int getRelFavA(){

        return rel_fav_a;
    }
    public void setRelFavA(int rel_fav_a){

        this.rel_fav_a=rel_fav_a;
    }

    //REL_FAV_B
    public int getRelFavB(){

        return rel_fav_b;
    }
    public void setRelFavB(int rel_fav_b){

        this.rel_fav_b=rel_fav_b;
    }

} // class Relation
