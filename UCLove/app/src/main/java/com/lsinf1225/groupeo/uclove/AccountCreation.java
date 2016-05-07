package com.lsinf1225.groupeo.uclove;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Environment;
import android.provider.MediaStore;
import android.net.Uri;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.lsinf1225.groupeo.uclove.database.NotifAuth;
import com.lsinf1225.groupeo.uclove.database.NotifAuthManager;
import com.lsinf1225.groupeo.uclove.database.Preferences;
import com.lsinf1225.groupeo.uclove.database.PreferencesManager;
import com.lsinf1225.groupeo.uclove.database.User;
import com.lsinf1225.groupeo.uclove.database.UserManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AccountCreation extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;
    Uri fileUri;
    String birthDate = "0000-00-00";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_creation);
    }

    public void takePicture(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        // Create the File where the photo should go
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            fileUri = Uri.fromFile(photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            // Photo taken
        }
    }

    public void showDatePickerDialog(View v) {
        DatePickerFragmentAccountCreation newFragment = new DatePickerFragmentAccountCreation();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        //do some stuff for example write on log and update TextField on activity
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        birthDate = sdf.format(c.getTime());
        String birthDate2 = sdf2.format(c.getTime());
        ((Button) findViewById(R.id.account_creation_birth_date)).setText(birthDate2);
    }

    public void createAccount(View button) {

        // On récupère les données des champs
        final EditText usernameField = (EditText) findViewById(R.id.account_creation_username);
        String username = usernameField.getText().toString();

        final EditText passwordField = (EditText) findViewById(R.id.account_creation_password);
        String password = passwordField.getText().toString();

        final EditText firstNameField = (EditText) findViewById(R.id.account_creation_first_name);
        String first_name = firstNameField.getText().toString();

        final EditText lastNameField = (EditText) findViewById(R.id.account_creation_last_name);
        String last_name = lastNameField.getText().toString();

        final EditText cityField = (EditText) findViewById(R.id.account_creation_city);
        String city = cityField.getText().toString();

        final Spinner languageSpinner = (Spinner) findViewById(R.id.account_creation_language);
        String language = mapLanguage(languageSpinner.getSelectedItem().toString());

        final Spinner hairColorSpinner = (Spinner) findViewById(R.id.account_creation_hair_color);
        String hairColor = mapHairColor(hairColorSpinner.getSelectedItem().toString());

        final Spinner hairTypeSpinner = (Spinner) findViewById(R.id.account_creation_hair_type);
        String hairType = mapHairStyle(hairTypeSpinner.getSelectedItem().toString());

        final Spinner eyesColorSpinner = (Spinner) findViewById(R.id.account_creation_eyes_color);
        String eyesColor = mapEyesColor(eyesColorSpinner.getSelectedItem().toString());

        final Spinner sexSpinner = (Spinner) findViewById(R.id.account_creation_sex);
        String sex = mapSex(sexSpinner.getSelectedItem().toString());

        final Spinner sexualitySpinner = (Spinner) findViewById(R.id.account_creation_sexuality);
        String sexuality = mapSexuality(sexualitySpinner.getSelectedItem().toString());

        GPSTracker gps = new GPSTracker(this);
        String position = gps.getCoord();

        User newUser = new User(0, username, password, first_name, last_name, birthDate, city, language, hairColor, hairType, eyesColor, sex, sexuality, position, mCurrentPhotoPath);

        UserManager m = new UserManager(this); // gestionnaire de la table "user"
        m.open();

        if (m.canCreateAccount(newUser)) {
            // Si l'utilisateur n'existe pas encore, on l'ajoute dans la BDD
            long userID = m.addUser(newUser);

            //On initialise les préférences de recherche de l'utilisateur
            Preferences newPreferences = new Preferences(1, (int)userID, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 200, 0, 1000000);
            PreferencesManager pm = new PreferencesManager(this);
            pm.open();
                pm.addPreferences(newPreferences);
            pm.close();

            //On initialise les préférences de notifications de l'utilisateur
            NotifAuth na1 = new NotifAuth(1, (int)userID, 1, 1);
            NotifAuth na2 = new NotifAuth(1, (int)userID, 2, 1);
            NotifAuth na3 = new NotifAuth(1, (int)userID, 3, 1);
            NotifAuth na4 = new NotifAuth(1, (int)userID, 4, 1);
            NotifAuth na5 = new NotifAuth(1, (int)userID, 5, 1);
            NotifAuthManager nam = new NotifAuthManager(this);
            nam.open();
                nam.addNotifAuth(na1);
                nam.addNotifAuth(na2);
                nam.addNotifAuth(na3);
                nam.addNotifAuth(na4);
                nam.addNotifAuth(na5);
            nam.close();

            // On passe l'userID dans l'intent
            Intent DrawerActivity = new Intent(AccountCreation.this, DrawerMainActivity.class);
            DrawerActivity.putExtra("userID", userID);

            // Puis on lance l'intent !
            startActivity(DrawerActivity);
        } else {
            Context context = getApplicationContext();
            CharSequence text = getResources().getString(R.string.account_invalid);;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        m.close();
    }

    public String mapLanguage(String language) {
        String returnedLanguage;
        switch (language) {
            case "French":
            case "Français":
                returnedLanguage = "French";
                break;
            case "English":
            case "Anglais":
                returnedLanguage = "English";
                break;
            default:
                returnedLanguage = "Unspecified";
        }
        return returnedLanguage;
    }

    public String mapHairColor(String hairColor) {
        String returnedHairColor;
        switch (hairColor) {
            case "Blond":
                returnedHairColor = "Blond";
                break;
            case "Brown":
            case "Brun":
                returnedHairColor = "Brown";
                break;
            case "Black":
            case "Noir":
                returnedHairColor = "Black";
                break;
            case "Ginger":
            case "Roux":
                returnedHairColor = "Ginger";
                break;
            case "Grey/white":
            case "Gris/blanc":
                returnedHairColor = "Grey/white";
                break;
            case "Other":
            case "Autre":
                returnedHairColor = "Other";
                break;
            default:
                returnedHairColor = "Unspecified";
        }
        return returnedHairColor;
    }

    public String mapHairStyle(String hairStyle) {
        String returnedHairStyle;
        switch (hairStyle) {
            case "Short":
            case "Court":
                returnedHairStyle = "Short";
                break;
            case "Moyen":
            case "Medium":
                returnedHairStyle = "Medium";
                break;
            case "Long":
                returnedHairStyle = "Long";
                break;
            default:
                returnedHairStyle = "Unspecified";
        }
        return returnedHairStyle;
    }

    public String mapEyesColor(String eyesColor) {
        String returnedEyesColor;
        switch (eyesColor) {
            case "Blue":
            case "Bleu":
                returnedEyesColor = "Blue";
                break;
            case "Brown":
            case "Brun":
                returnedEyesColor = "Brown";
                break;
            case "Black":
            case "Noir":
                returnedEyesColor = "Black";
                break;
            case "Green":
            case "Vert":
                returnedEyesColor = "Green";
                break;
            case "Grey":
            case "Gris":
                returnedEyesColor = "Grey";
                break;
            default:
                returnedEyesColor = "Unspecified";
        }
        return returnedEyesColor;
    }

    public String mapSex(String sex) {
        String returnedSex;
        switch (sex) {
            case "Male":
            case "Homme":
                returnedSex = "Male";
                break;
            case "Female":
            case "Femme":
                returnedSex = "Female";
                break;
            default:
                returnedSex = "Unspecified";
        }
        return returnedSex;
    }

    public String mapSexuality(String sexuality) {
        String returnedSexuality;
        switch (sexuality) {
            case "Heterosexual":
            case "Hétérosexuel":
                returnedSexuality = "Heterosexual";
                break;
            case "Homosexual":
            case "Homosexuel":
                returnedSexuality = "Homosexual";
                break;
            case "Bisexual":
            case "Bisexuel":
                returnedSexuality = "Bisexual";
                break;
            default:
                returnedSexuality = "Unspecified";
        }
        return returnedSexuality;
    }
}