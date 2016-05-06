package com.lsinf1225.groupeo.uclove.drawer_fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.lsinf1225.groupeo.uclove.DrawerMainActivity;
import com.lsinf1225.groupeo.uclove.R;
import com.lsinf1225.groupeo.uclove.database.User;
import com.lsinf1225.groupeo.uclove.database.UserManager;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditProfileFragment extends Fragment {

    private View myFragmentView;
    String mCurrentPhotoPath = "";
    User currentUser;
    UserManager m;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int RESULT_OK = -1;
    Uri fileUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        // On récupère l'userID
        long user_id = ((DrawerMainActivity) getActivity()).returnUserID();
        m = new UserManager(getActivity()); // gestionnaire de la table "user"
        m.open();
        currentUser = m.getUser(user_id);


        Button button1= (Button) myFragmentView.findViewById(R.id.edit_profile_upload_picture);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

        Button button2= (Button) myFragmentView.findViewById(R.id.edit_profile_send_info);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText usernameField = (EditText) myFragmentView.findViewById(R.id.edit_profile_username);
                String username = usernameField.getText().toString();

                final EditText passwordField = (EditText) myFragmentView.findViewById(R.id.edit_profile_password);
                String password = passwordField.getText().toString();

                final EditText cityField = (EditText) myFragmentView.findViewById(R.id.edit_profile_city);
                String city = cityField.getText().toString();

                final Spinner languageSpinner = (Spinner) myFragmentView.findViewById(R.id.edit_profile_language);
                String language = mapLanguage(languageSpinner.getSelectedItem().toString());

                final Spinner hairColorSpinner = (Spinner) myFragmentView.findViewById(R.id.edit_profile_hair_color);
                String hairColor = mapLanguage(hairColorSpinner.getSelectedItem().toString());

                final Spinner hairTypeSpinner = (Spinner) myFragmentView.findViewById(R.id.edit_profile_hair_type);
                String hairType = mapLanguage(hairTypeSpinner.getSelectedItem().toString());

                if (mCurrentPhotoPath.equals("")){
                    mCurrentPhotoPath = currentUser.getUserProfilePic();
                }

                User editedUser = new User(currentUser.getUserID(), username, password, currentUser.getUserFirstName(), currentUser.getUserLastName(), currentUser.getUserBirthDate(), city, language, hairColor, hairType, currentUser.getUserEyesColor(), currentUser.getUserSex(), currentUser.getUserSexuality(), currentUser.getUserPosition(), mCurrentPhotoPath);

                if (m.canEditProfile(editedUser)){
                    m.modUser(editedUser);
                }
            }
        });

        return myFragmentView;
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
                returnedHairStyle = "Moyen";
                break;
            case "Long":
                returnedHairStyle = "Long";
                break;
            default:
                returnedHairStyle = "Unspecified";
        }
        return returnedHairStyle;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            // Photo taken
        }
    }

}