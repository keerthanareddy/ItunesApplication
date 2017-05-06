package com.example.keerthana.itunesapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by keerthana on 2/25/2017.
 */

public class FavListAdapter extends ArrayAdapter {
    List<Itunes> mdata;
    Context context;
    int mresource;
    static ArrayList<Itunes> my_itunes;
    ArrayList<Integer> note_position_fav;
    static ArrayList<Integer> removed_pos = new ArrayList<Integer>();



    public FavListAdapter(Context context, int resource, List<Itunes> objects) {
        super(context, resource, objects);
        this.mdata = objects;
        this.context = context;
        this.mresource = resource;
        my_itunes = (ArrayList<Itunes>) objects;

    }






    ArrayList<Integer> fav_check_list = new ArrayList<Integer>(Collections.nCopies(25,1));

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(mresource,parent,false);
        }
        final Itunes itunes = mdata.get(position);
        TextView title_text_view = (TextView) convertView.findViewById(R.id.title_app);
        title_text_view.setText(itunes.getTitle());
        TextView price_text_view = (TextView) convertView.findViewById(R.id.price_id);
        String only_price = itunes.getPrice();
        price_text_view.setText("Price: USD"+only_price);
        ImageView app_icon = (ImageView) convertView.findViewById(R.id.app_logo);
        Picasso.with(getContext())
                .load(itunes.getLogo())
                .into(app_icon);
        final ImageView imageView_star = (ImageView) convertView.findViewById(R.id.star_img);
        if (fav_check_list.get(position).equals(1)) {
            imageView_star.setImageResource(R.drawable.blackstar);

        }
        else {
            imageView_star.setImageResource(R.drawable.whitestar);
        }

        imageView_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fav_check_list.get(position).equals(1)){

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Add to Favourites")
                            .setMessage("Are you sure that you want to remove this App from favourites?")
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d("b_negative", "neg");
                                }
                            })
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("my_fav_itunes",Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.remove(itunes.getTitle());
                                    editor.apply();
                                    MainActivity.note_position_fav.remove(position);
                                    my_itunes.remove(position);
                                    removed_pos.add(position);
                                    remove(itunes);






                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.show();

                }

            }
        });


        return convertView;
    }
}
