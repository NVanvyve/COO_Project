package com.lsinf1225.groupeo.uclove;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.app.DialogFragment ;
import java.util.Calendar;

public class DatePickerFragmentAccountCreation extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new DatePickerDialog(getActivity(), R.style.style_date_picker_dialog, (AccountCreation)getActivity(), year, month, day);
        } else {
            return new DatePickerDialog(getActivity(), (AccountCreation)getActivity(), year, month, day);
        }
    }
}