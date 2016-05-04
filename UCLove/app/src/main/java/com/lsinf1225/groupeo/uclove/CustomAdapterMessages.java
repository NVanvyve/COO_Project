package com.lsinf1225.groupeo.uclove;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapterMessages extends ArrayAdapter<String> {

    private final Context context;
    private final List message;
    private final List image;
    private final List messageright;

    public CustomAdapterMessages(Context context, List<String> message, List<String> image, List<Boolean> messageright) {
        super(context, R.layout.custom_row_message , message);

        this.context=context;
        this.message=message;
        this.image=image;
        this.messageright=messageright;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View customView = myInflater.inflate(R.layout.custom_row_message, parent, false);

        String singleMessage = (String)message.get(position);
        String singleImage = (String)image.get(position);
        Boolean singleMessageRight = (Boolean)messageright.get(position);

        TextView RowMessage = (TextView) customView.findViewById(R.id.custom_row_message_message);
        ImageView RowImage1 = (ImageView) customView.findViewById(R.id.custom_row_message_image1);
        ImageView RowImage2 = (ImageView) customView.findViewById(R.id.custom_row_message_image2);
        RelativeLayout RowBackground = (RelativeLayout) customView.findViewById(R.id.custom_row_message_background);

        RowMessage.setText(singleMessage);
        if (singleMessageRight) {
            Picasso.with(RowImage2.getContext()).load(singleImage).fit().centerCrop().into(RowImage2);
            RowImage1.setVisibility(View.GONE);
            RowMessage.setGravity(Gravity.END);
            RowBackground.setBackgroundColor(customView.getResources().getColor(R.color.lightRose));
        } else {
            Picasso.with(RowImage2.getContext()).load(singleImage).fit().centerCrop().into(RowImage1);
            RowImage2.setVisibility(View.GONE);
            RowMessage.setGravity(Gravity.START);
        }

        return customView;
    }
}
