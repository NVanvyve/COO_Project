package com.lsinf1225.groupeo.uclove;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapterDatePreferences extends ArrayAdapter<String> {

    private final Context context;
    private final List date;
    private final List dateid;

    public CustomAdapterDatePreferences(Context context, List<String> date, List<Integer> dateid) {
        super(context, R.layout.custom_row_date_preferences , date);

        this.context=context;
        this.date=date;
        this.dateid=dateid;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View customView = myInflater.inflate(R.layout.custom_row_date_preferences, parent, false);

        String singleDate = (String)date.get(position);
        String singleDateId = String.valueOf(dateid.get(position));

        TextView RowDate = (TextView) customView.findViewById(R.id.custom_row_date_preferences_date);
        TextView RowDateId = (TextView) customView.findViewById(R.id.custom_row_date_preferences_cal_id);

        RowDate.setText(singleDate);
        RowDateId.setText(singleDateId);
        return customView;
    }
}
