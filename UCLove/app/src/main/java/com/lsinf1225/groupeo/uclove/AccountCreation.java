package com.lsinf1225.groupeo.uclove;

import android.content.Intent;
import android.os.Environment;
import android.provider.MediaStore;
import android.net.Uri;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountCreation extends AppCompatActivity {

    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;
    Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_creation);
        //ImageView myImage = (ImageView) findViewById(R.id.imageView);
        //myImage.setImageResource(R.drawable.testpic);
        //Picasso.with(myImage.getContext()).load(R.drawable.testpic).fit().centerCrop().into(myImage);
        //Picasso.with(myImage.getContext()).load(new File(fileUri)).resize(600, 200).centerCrop().into(myImage);
    }

    public String mapLanguage(String language) {
        String returnedLanguage;
        switch (language) {
            case "Français":
                returnedLanguage = "French";
                break;
            case "Anglais":
                returnedLanguage = "English";
                break;
            default:
                returnedLanguage = "Invalid";
        }
        return returnedLanguage;
    }

    public String mapHairColor(String hairColor) {
        String returnedHairColor;
        switch (hairColor) {
            case "Blond":
                returnedHairColor = "Blond";
                break;
            case "Brun":
                returnedHairColor = "Brown";
                break;
            case "Noir":
                returnedHairColor = "Black";
                break;
            case "Roux":
                returnedHairColor = "Ginger";
                break;
            case "Gris/blanc":
                returnedHairColor = "Grey/white";
                break;
            case "Autre":
                returnedHairColor = "Other";
                break;
            default:
                returnedHairColor = "Invalid";
        }
        return returnedHairColor;
    }

    public String mapHairStyle(String hairStyle) {
        String returnedHairStyle;
        switch (hairStyle) {
            case "Court":
                returnedHairStyle = "Short";
                break;
            case "Medium":
                returnedHairStyle = "Moyen";
                break;
            case "Long":
                returnedHairStyle = "Long";
                break;
            default:
                returnedHairStyle = "Invalid";
        }
        return returnedHairStyle;
    }

    public String mapEyesColor(String eyesColor) {
        String returnedEyesColor;
        switch (eyesColor) {
            case "Bleu":
                returnedEyesColor = "Blue";
                break;
            case "Brun":
                returnedEyesColor = "Brown";
                break;
            case "Noir":
                returnedEyesColor = "Black";
                break;
            case "Vert":
                returnedEyesColor = "Green";
                break;
            case "Gris":
                returnedEyesColor = "Grey";
                break;
            default:
                returnedEyesColor = "Invalid";
        }
        return returnedEyesColor;
    }

    public String mapSex(String sex) {
        String returnedSex;
        switch (sex) {
            case "Homme":
                returnedSex = "Male";
                break;
            case "Femme":
                returnedSex = "Female";
                break;
            default:
                returnedSex = "Invalid";
        }
        return returnedSex;
    }

    public String mapSexuality(String sexuality) {
        String returnedSexuality;
        switch (sexuality) {
            case "Hétérosexuel":
                returnedSexuality = "Heterosexual";
                break;
            case "Homosexuel":
                returnedSexuality = "Homosexual";
                break;
            case "Bisexuel":
                returnedSexuality = "Bisexual";
                break;
            default:
                returnedSexuality = "Invalid";
        }
        return returnedSexuality;
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
        System.out.println("LOL1");
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

    public void createAccount(View button) {
        final EditText usernameField = (EditText) findViewById(R.id.account_creation_username);
        String username = usernameField.getText().toString();

        final EditText passwordField = (EditText) findViewById(R.id.account_creation_password);
        String password = passwordField.getText().toString();

        final EditText firstNameField = (EditText) findViewById(R.id.account_creation_first_name);
        String first_name = firstNameField.getText().toString();

        final EditText lastNameField = (EditText) findViewById(R.id.account_creation_last_name);
        String last_name = lastNameField.getText().toString();

        final EditText birthDateField = (EditText) findViewById(R.id.account_creation_birth_date);
        String birth_date = birthDateField.getText().toString();

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

        String position = "POSITION";

        UserManager m = new UserManager(this); // gestionnaire de la table "user"
        m.open();

        User newUser = new User(0, username, password, first_name, last_name, birth_date, city, language, hairColor, hairType, eyesColor, sex, sexuality, position, mCurrentPhotoPath);

        if (m.canCreateAccount(newUser)) {
            // Si l'utilisateur n'existe pas, on l'ajoute dans la BDD
            long userID = m.addUser(newUser);

            Intent Menu = new Intent(AccountCreation.this, Menu.class);
            Menu.putExtra("userID", userID);

            // Puis on lance l'intent !
            startActivity(Menu);
        } else {
            Context context = getApplicationContext();
            CharSequence text = "Cet utilisateur existe déjà!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        m.close();
    }
}