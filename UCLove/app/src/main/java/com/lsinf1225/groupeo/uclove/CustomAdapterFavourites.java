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

public class CustomAdapterFavourites extends ArrayAdapter<String> {

    private final Context context;
    private final List name;
    private final List city;
    private final List image;
    private final List userid;

    public CustomAdapterFavourites(Context context, List<String> name, List<String> city, List<String> image, List<String> userid) {
        super(context, R.layout.custom_row_favourites , name);

        this.context=context;
        this.name=name;
        this.city=city;
        this.image=image;
        this.userid=userid;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View customView = myInflater.inflate(R.layout.custom_row_favourites, parent, false);

        String singleName = (String)name.get(position);
        String singleCity = (String)city.get(position);
        String singleImage = (String)image.get(position);
        String singleUserId = (String)userid.get(position);

        TextView RowName = (TextView) customView.findViewById(R.id.custom_row_favourites_name);
        TextView RowCity = (TextView) customView.findViewById(R.id.custom_row_favourites_city);
        ImageView RowImage = (ImageView) customView.findViewById(R.id.custom_row_favourites_image);
        TextView RowUserId = (TextView) customView.findViewById(R.id.custom_row_favourites_user_id);

        RowName.setText(singleName);
        RowCity.setText(singleCity);
        Picasso.with(RowImage.getContext()).load(singleImage).fit().centerCrop().into(RowImage);
        RowUserId.setText(singleUserId);

        return customView;
    }
}
