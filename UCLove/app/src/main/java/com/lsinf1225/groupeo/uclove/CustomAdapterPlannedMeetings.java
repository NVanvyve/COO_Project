package com.lsinf1225.groupeo.uclove;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapterPlannedMeetings extends ArrayAdapter<String> {

    private final Context context;
    private final List name;
    private final List date;
    private final List rdvid;

    public CustomAdapterPlannedMeetings(Context context, List<String> name, List<String> date, List<String> rdvid) {
        super(context, R.layout.custom_row_planned_meetings , name);

        this.context=context;
        this.name=name;
        this.date=date;
        this.rdvid=rdvid;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View customView = myInflater.inflate(R.layout.custom_row_planned_meetings, parent, false);

        String singleName = (String)name.get(position);
        String singleDate = (String)date.get(position);
        String singleRDVId = (String)rdvid.get(position);

        TextView RowName = (TextView) customView.findViewById(R.id.custom_row_planned_meetings_name);
        TextView RowDate = (TextView) customView.findViewById(R.id.custom_row_planned_meetings_date);
        TextView RowUserId = (TextView) customView.findViewById(R.id.custom_row_planned_meetings_rdv_id);

        RowName.setText(singleName);
        RowDate.setText(singleDate);
        RowUserId.setText(singleRDVId);

        return customView;
    }
}
