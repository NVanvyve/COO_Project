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

public class CustomAdapterMessagesList extends ArrayAdapter<String> {

    private final Context context;
    private final List name;
    private final List message;
    private final List image;
    private final List userid;

    public CustomAdapterMessagesList(Context context, List<String> name, List<String> message, List<String> image, List<String> userid) {
        super(context, R.layout.custom_row_messages_list , name);

        this.context=context;
        this.name=name;
        this.message=message;
        this.image=image;
        this.userid=userid;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View customView = myInflater.inflate(R.layout.custom_row_messages_list, parent, false);

        String singleName = (String)name.get(position);
        String singleMessage = (String)message.get(position);
        String singleImage = (String)image.get(position);
        String singleUserId = (String)userid.get(position);

        TextView RowName = (TextView) customView.findViewById(R.id.custom_row_messages_list_name);
        TextView RowMessage = (TextView) customView.findViewById(R.id.custom_row_messages_list_last_message);
        ImageView RowImage = (ImageView) customView.findViewById(R.id.custom_row_messages_list_image);
        TextView RowUserId = (TextView) customView.findViewById(R.id.custom_row_messages_list_user_id);

        RowName.setText(singleName);
        RowMessage.setText(singleMessage);
        Picasso.with(RowImage.getContext()).load(singleImage).fit().centerCrop().into(RowImage);
        RowUserId.setText(singleUserId);

        return customView;
    }
}
