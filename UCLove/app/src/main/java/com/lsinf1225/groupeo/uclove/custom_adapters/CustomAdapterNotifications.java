package com.lsinf1225.groupeo.uclove.custom_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lsinf1225.groupeo.uclove.R;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CustomAdapterNotifications extends ArrayAdapter<String> {

    private final Context context;
    private final List text;
    private final List date;
    private final List code;
    private final List status;
    private final List id;

    public CustomAdapterNotifications(Context context, List<String> text, List<String> date, List<String> status, List<String> code, List<String> id) {
        super(context, R.layout.custom_row_notifications , text);

        this.context=context;
        this.text=text;
        this.date=date;
        this.code=code;
        this.status=status;
        this.id=id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View customView = myInflater.inflate(R.layout.custom_row_notifications, parent, false);

        String singleText = (String)text.get(position);
        String singleDateTime = (String)date.get(position);
        String singleCode = (String)code.get(position);
        String singleStatus = (String)status.get(position);
        String singleId = (String)id.get(position);
        String date = "";
        String time = "";

        // On formate la date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dateObject = format.parse(singleDateTime);
            Calendar cal = DateToCalendar(dateObject);
            date = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1);
            time = cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        TextView RowText = (TextView) customView.findViewById(R.id.notifications_text);
        TextView RowDate = (TextView) customView.findViewById(R.id.notifications_date);
        TextView RowTime = (TextView) customView.findViewById(R.id.notifications_time);
        TextView RowCode = (TextView) customView.findViewById(R.id.notifications_code);
        TextView RowId = (TextView) customView.findViewById(R.id.notifications_id);
        RelativeLayout RowBackground = (RelativeLayout) customView.findViewById(R.id.notifications_relative_layout);

        RowText.setText(singleText);
        RowDate.setText(date);
        RowTime.setText(time);
        RowCode.setText(singleCode);
        RowId.setText(singleId);

        if (singleStatus.equals("Unread")) {
            RowBackground.setBackgroundColor(customView.getResources().getColor(R.color.lightRose));
        }

        return customView;
    }

    public static Calendar DateToCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}