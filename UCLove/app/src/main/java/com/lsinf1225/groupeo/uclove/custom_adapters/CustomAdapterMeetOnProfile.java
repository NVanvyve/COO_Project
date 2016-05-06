package com.lsinf1225.groupeo.uclove.custom_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lsinf1225.groupeo.uclove.R;

import java.util.List;

public class CustomAdapterMeetOnProfile extends ArrayAdapter<String> {

    private final Context context;
    private final List date;
    private final List dateid;

    public CustomAdapterMeetOnProfile(Context context, List<String> date, List<Integer> dateid) {
        super(context, R.layout.custom_row_meet_on_profile , date);

        this.context=context;
        this.date=date;
        this.dateid=dateid;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View customView = myInflater.inflate(R.layout.custom_row_meet_on_profile, parent, false);

        String singleDate = (String)date.get(position);
        String singleDateId = String.valueOf(dateid.get(position));

        TextView RowDate = (TextView) customView.findViewById(R.id.custom_row_meet_on_profile_date);
        TextView RowDateId = (TextView) customView.findViewById(R.id.custom_row_meet_on_profile_cal_id);

        RowDate.setText(singleDate);
        RowDateId.setText(singleDateId);
        return customView;
    }
}
