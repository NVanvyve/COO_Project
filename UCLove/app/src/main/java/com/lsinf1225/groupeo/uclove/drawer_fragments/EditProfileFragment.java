package com.lsinf1225.groupeo.uclove.drawer_fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    String language_code;
    long user_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        // On récupère l'userID
        user_id = ((DrawerMainActivity) getActivity()).returnUserID();
        m = new UserManager(getActivity()); // gestionnaire de la table "user"
        m.open();
        currentUser = m.getUser(user_id);

        language_code = Locale.getDefault().getLanguage();

        String username = currentUser.getUserUsername();
        String password = currentUser.getUserPassword();
        String city = currentUser.getUserCity();
        String language = mapLanguageFR(currentUser.getUserLanguage());
        String hairColor = mapHairColorFR(currentUser.getUserHairColor());
        String hairType = mapHairStyleFR(currentUser.getUserHairType());

        final EditText usernameField = (EditText) myFragmentView.findViewById(R.id.edit_profile_username);
        usernameField.setText(username);

        final EditText passwordField = (EditText) myFragmentView.findViewById(R.id.edit_profile_password);
        passwordField.setText(password);

        final EditText cityField = (EditText) myFragmentView.findViewById(R.id.edit_profile_city);
        cityField.setText(city);

        final Spinner languageSpinner = (Spinner) myFragmentView.findViewById(R.id.edit_profile_language);
        languageSpinner.setSelection(((ArrayAdapter)languageSpinner.getAdapter()).getPosition(language));

        final Spinner hairColorSpinner = (Spinner) myFragmentView.findViewById(R.id.edit_profile_hair_color);
        hairColorSpinner.setSelection(((ArrayAdapter)hairColorSpinner.getAdapter()).getPosition(hairColor));

        final Spinner hairTypeSpinner = (Spinner) myFragmentView.findViewById(R.id.edit_profile_hair_type);
        hairTypeSpinner.setSelection(((ArrayAdapter)hairTypeSpinner.getAdapter()).getPosition(hairType));

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
                String hairColor = mapHairColor(hairColorSpinner.getSelectedItem().toString());

                final Spinner hairTypeSpinner = (Spinner) myFragmentView.findViewById(R.id.edit_profile_hair_type);
                String hairType = mapHairStyle(hairTypeSpinner.getSelectedItem().toString());

                if (mCurrentPhotoPath.equals("")){
                    mCurrentPhotoPath = currentUser.getUserProfilePic();
                }

                User editedUser = new User(currentUser.getUserID(), username, password, currentUser.getUserFirstName(), currentUser.getUserLastName(), currentUser.getUserBirthDate(), city, language, hairColor, hairType, currentUser.getUserEyesColor(), currentUser.getUserSex(), currentUser.getUserSexuality(), currentUser.getUserPosition(), mCurrentPhotoPath);

                if (m.canEditProfile(editedUser)){
                    m.modUser(editedUser);

                    Context context = getActivity();
                    CharSequence text = getResources().getString(R.string.edit_profile_ok);;
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    Intent DrawerActivity = new Intent(getActivity(), DrawerMainActivity.class);
                    DrawerActivity.putExtra("userID", user_id);

                    // Puis on lance l'intent !
                    startActivity(DrawerActivity);
                } else {
                    Context context = getActivity();
                    CharSequence text = getResources().getString(R.string.edit_profile_failed);;
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
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

    private String mapLanguageFR(String language) {
        if (language_code.equals("fr")) {
            switch (language) {
                case "French":
                    language = "Français";
                    break;
                case "English":
                    language = "Anglais";
                    break;
                default:
                    language = "Non spécifié";
                    break;
            }
        }
        return language;
    }

    private String mapHairColorFR(String haircolor) {
        if (language_code.equals("fr")) {
            switch (haircolor) {
                case "Blond":
                    haircolor = "Blond";
                    break;
                case "Brown":
                    haircolor = "Brun";
                    break;
                case "Black":
                    haircolor = "Noir";
                    break;
                case "Ginger":
                    haircolor = "Roux";
                    break;
                case "Grey/white":
                    haircolor = "Gris/blanc";
                    break;
                case "Other":
                    haircolor = "Autre";
                    break;
                default:
                    haircolor = "Non spécifié";
                    break;
            }
        }
        return haircolor;
    }

    private String mapHairStyleFR(String hairstyle) {
        if (language_code.equals("fr")) {
            switch (hairstyle) {
                case "Short":
                    hairstyle = "Court";
                    break;
                case "Medium":
                    hairstyle = "Moyen";
                    break;
                case "Long":
                    hairstyle = "Long";
                    break;
                default:
                    hairstyle = "Non spécifié";
                    break;
            }
        }
        return hairstyle;
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