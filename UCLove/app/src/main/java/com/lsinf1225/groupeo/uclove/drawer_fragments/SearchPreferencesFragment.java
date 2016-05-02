package com.lsinf1225.groupeo.uclove.drawer_fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.lsinf1225.groupeo.uclove.DrawerMainActivity;
import com.lsinf1225.groupeo.uclove.R;
import com.lsinf1225.groupeo.uclove.database.Preferences;
import com.lsinf1225.groupeo.uclove.database.PreferencesManager;
import com.lsinf1225.groupeo.uclove.database.User;
import com.lsinf1225.groupeo.uclove.database.UserManager;

public class SearchPreferencesFragment extends Fragment {

    private View myFragmentView;
    User currentUser;
    Preferences currentPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_search_preferences, container, false);

        // On récupère l'userID
        long user_id = ((DrawerMainActivity) getActivity()).returnUserID();
        UserManager m = new UserManager(getActivity()); // gestionnaire de la table "user"
        m.open();
        currentUser = m.getUser(user_id);
        m.close();

        PreferencesManager pm = new PreferencesManager(getActivity());
        pm.open();
        currentPreferences = pm.getPreferences(currentUser.getUserID());
        pm.close();

        // Lit les préférences et mets les switches dans la bonne position
        setFields();


        Switch sw;

        sw = (Switch) myFragmentView.findViewById(R.id.pref_search_language_fr);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    currentPreferences.setPrefLanguageFR(1);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                } else {
                    currentPreferences.setPrefLanguageFR(0);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                }
            }
        });

        sw = (Switch) myFragmentView.findViewById(R.id.pref_search_language_en);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    currentPreferences.setPrefLanguageEN(1);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                } else {
                    currentPreferences.setPrefLanguageEN(0);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                }
            }
        });

        sw = (Switch) myFragmentView.findViewById(R.id.pref_search_hair_color_blond);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    currentPreferences.setPrefHairColorBlond(1);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                } else {
                    currentPreferences.setPrefHairColorBlond(0);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                }
            }
        });

        sw = (Switch) myFragmentView.findViewById(R.id.pref_search_hair_color_brown);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    currentPreferences.setPrefHairColorBrown(1);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                } else {
                    currentPreferences.setPrefHairColorBrown(0);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                }
            }
        });

        sw = (Switch) myFragmentView.findViewById(R.id.pref_search_hair_color_black);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    currentPreferences.setPrefHairColorBlack(1);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                } else {
                    currentPreferences.setPrefHairColorBlack(0);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                }
            }
        });

        sw = (Switch) myFragmentView.findViewById(R.id.pref_search_hair_color_ginger);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    currentPreferences.setPrefHairColorGinger(1);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                } else {
                    currentPreferences.setPrefHairColorGinger(0);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                }
            }
        });

        sw = (Switch) myFragmentView.findViewById(R.id.pref_search_hair_color_greywhite);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    currentPreferences.setPrefHairColorGreywhite(1);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                } else {
                    currentPreferences.setPrefHairColorGreywhite(0);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                }
            }
        });

        sw = (Switch) myFragmentView.findViewById(R.id.pref_search_hair_color_other);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    currentPreferences.setPrefHairColorOther(1);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                } else {
                    currentPreferences.setPrefHairColorOther(0);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                }
            }
        });

        sw = (Switch) myFragmentView.findViewById(R.id.pref_search_hair_type_short);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    currentPreferences.setPrefHairTypeShort(1);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                } else {
                    currentPreferences.setPrefHairTypeShort(0);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                }
            }
        });

        sw = (Switch) myFragmentView.findViewById(R.id.pref_search_hair_type_medium);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    currentPreferences.setPrefHairTypeMedium(1);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                } else {
                    currentPreferences.setPrefHairTypeMedium(0);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                }
            }
        });

        sw = (Switch) myFragmentView.findViewById(R.id.pref_search_hair_type_long);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    currentPreferences.setPrefHairTypeLong(1);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                } else {
                    currentPreferences.setPrefHairTypeLong(0);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                }
            }
        });

        sw = (Switch) myFragmentView.findViewById(R.id.pref_search_eyes_color_blue);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    currentPreferences.setPrefEyesColorBlue(1);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                } else {
                    currentPreferences.setPrefEyesColorBlue(0);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                }
            }
        });

        sw = (Switch) myFragmentView.findViewById(R.id.pref_search_eyes_color_brown);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    currentPreferences.setPrefEyesColorBrown(1);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                } else {
                    currentPreferences.setPrefEyesColorBrown(0);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                }
            }
        });

        sw = (Switch) myFragmentView.findViewById(R.id.pref_search_eyes_color_black);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    currentPreferences.setPrefEyesColorBlack(1);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                } else {
                    currentPreferences.setPrefEyesColorBlack(0);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                }
            }
        });

        sw = (Switch) myFragmentView.findViewById(R.id.pref_search_eyes_color_green);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    currentPreferences.setPrefEyesColorGreen(1);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                } else {
                    currentPreferences.setPrefEyesColorGreen(0);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                }
            }
        });

        sw = (Switch) myFragmentView.findViewById(R.id.pref_search_eyes_color_grey);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    currentPreferences.setPrefEyesColorGrey(1);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                } else {
                    currentPreferences.setPrefEyesColorGrey(0);
                    PreferencesManager pm = new PreferencesManager(getActivity());
                    pm.open();
                    pm.modPreferences(currentPreferences);
                    pm.close();
                }
            }
        });

        // Enregistre les modifications du champ de texte dans la base de données
        EditText editText1 = (EditText) myFragmentView.findViewById(R.id.pref_search_age_max);
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if (str.equals("")) {
                    str = "0";
                }
                currentPreferences.setPrefAgeMax(Integer.parseInt(str));
                PreferencesManager pm = new PreferencesManager(getActivity());
                pm.open();
                pm.modPreferences(currentPreferences);
                pm.close();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        // Enregistre les modifications du champ de texte dans la base de données
        EditText editText2 = (EditText) myFragmentView.findViewById(R.id.pref_search_age_min);
        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if (str.equals("")) {
                    str = "0";
                }
                currentPreferences.setPrefAgeMin(Integer.parseInt(str));
                PreferencesManager pm = new PreferencesManager(getActivity());
                pm.open();
                pm.modPreferences(currentPreferences);
                pm.close();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        // Enregistre les modifications du champ de texte dans la base de données
        EditText editText3 = (EditText) myFragmentView.findViewById(R.id.pref_search_distance_max);
        editText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if (str.equals("")) {
                    str = "0";
                }
                currentPreferences.setPrefDistanceMax(Integer.parseInt(str));
                PreferencesManager pm = new PreferencesManager(getActivity());
                pm.open();
                pm.modPreferences(currentPreferences);
                pm.close();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        // Inflate the layout for this fragment
        return myFragmentView;
    }

    public void setFields() {
        if (currentPreferences.getPrefLanguageFR() == 0) {
            Switch sw = (Switch) myFragmentView.findViewById(R.id.pref_search_language_fr);
            sw.setChecked(false);
        }

        if (currentPreferences.getPrefLanguageEN() == 0) {
            Switch sw = (Switch) myFragmentView.findViewById(R.id.pref_search_language_en);
            sw.setChecked(false);
        }

        if (currentPreferences.getPrefHairColorBlond() == 0) {
            Switch sw = (Switch) myFragmentView.findViewById(R.id.pref_search_hair_color_blond);
            sw.setChecked(false);
        }

        if (currentPreferences.getPrefHairColorBrown() == 0) {
            Switch sw = (Switch) myFragmentView.findViewById(R.id.pref_search_hair_color_brown);
            sw.setChecked(false);
        }

        if (currentPreferences.getPrefHairColorBlack() == 0) {
            Switch sw = (Switch) myFragmentView.findViewById(R.id.pref_search_hair_color_black);
            sw.setChecked(false);
        }

        if (currentPreferences.getPrefHairColorGinger() == 0) {
            Switch sw = (Switch) myFragmentView.findViewById(R.id.pref_search_hair_color_ginger);
            sw.setChecked(false);
        }

        if (currentPreferences.getPrefHairColorGreywhite() == 0) {
            Switch sw = (Switch) myFragmentView.findViewById(R.id.pref_search_hair_color_greywhite);
            sw.setChecked(false);
        }

        if (currentPreferences.getPrefHairColorOther() == 0) {
            Switch sw = (Switch) myFragmentView.findViewById(R.id.pref_search_hair_color_other);
            sw.setChecked(false);
        }

        if (currentPreferences.getPrefHairTypeShort() == 0) {
            Switch sw = (Switch) myFragmentView.findViewById(R.id.pref_search_hair_type_short);
            sw.setChecked(false);
        }

        if (currentPreferences.getPrefHairTypeMedium() == 0) {
            Switch sw = (Switch) myFragmentView.findViewById(R.id.pref_search_hair_type_medium);
            sw.setChecked(false);
        }

        if (currentPreferences.getPrefHairTypeLong() == 0) {
            Switch sw = (Switch) myFragmentView.findViewById(R.id.pref_search_hair_type_long);
            sw.setChecked(false);
        }

        if (currentPreferences.getPrefEyesColorBlue() == 0) {
            Switch sw = (Switch) myFragmentView.findViewById(R.id.pref_search_eyes_color_blue);
            sw.setChecked(false);
        }

        if (currentPreferences.getPrefEyesColorBrown() == 0) {
            Switch sw = (Switch) myFragmentView.findViewById(R.id.pref_search_eyes_color_brown);
            sw.setChecked(false);
        }

        if (currentPreferences.getPrefEyesColorBlack() == 0) {
            Switch sw = (Switch) myFragmentView.findViewById(R.id.pref_search_eyes_color_black);
            sw.setChecked(false);
        }

        if (currentPreferences.getPrefEyesColorGreen() == 0) {
            Switch sw = (Switch) myFragmentView.findViewById(R.id.pref_search_eyes_color_green);
            sw.setChecked(false);
        }

        if (currentPreferences.getPrefEyesColorGrey() == 0) {
            Switch sw = (Switch) myFragmentView.findViewById(R.id.pref_search_eyes_color_grey);
            sw.setChecked(false);
        }

        TextView tvagemax = (TextView) myFragmentView.findViewById(R.id.pref_search_age_max);
        tvagemax.setText(String.valueOf(currentPreferences.getPrefAgeMax()));

        TextView tvagemin = (TextView) myFragmentView.findViewById(R.id.pref_search_age_min);
        tvagemin.setText(String.valueOf(currentPreferences.getPrefAgeMin()));

        TextView tvdistmax = (TextView) myFragmentView.findViewById(R.id.pref_search_distance_max);
        tvdistmax.setText(String.valueOf(currentPreferences.getPrefDistanceMax()));
    }
}