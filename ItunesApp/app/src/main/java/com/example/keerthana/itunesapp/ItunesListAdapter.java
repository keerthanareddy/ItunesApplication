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
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by keerthana on 2/23/2017.
 */

public class ItunesListAdapter extends ArrayAdapter<Itunes> {

    List<Itunes> mdata;
    Context context;
    int mresource;
    public static ArrayList<Integer> check_list = new ArrayList<Integer>(Collections.nCopies(25,0));



    MainActivity activity;

    public static ArrayList<Integer> getCheck_list() {
        return check_list;
    }

    public void setCheck_list(ArrayList<Integer> check_list) {
        this.check_list = check_list;
    }

    public ItunesListAdapter(Context context, int resource, List<Itunes> objects) {
        super(context, resource, objects);
        //Here we can ascend and descend
        this.mdata = objects;
        this.context = context;
        this.mresource = resource;

    }



    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("my_fav_itunes",getContext().MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(mresource,parent,false);
        }
        final Itunes itunes = mdata.get(position);
        ImageView app_icon = (ImageView) convertView.findViewById(R.id.app_logo);
        Picasso.with(getContext())
                .load(itunes.getLogo())
                .into(app_icon);
        TextView title_text_view = (TextView) convertView.findViewById(R.id.title_app);
        title_text_view.setText(itunes.getTitle());
        TextView price_text_view = (TextView) convertView.findViewById(R.id.price_id);
        String only_price = itunes.getPrice();
        price_text_view.setText("Price: USD"+only_price);
        final ImageView imageView_star = (ImageView) convertView.findViewById(R.id.star_img);
        if (check_list.get(position).equals(1) || sharedPreferences.contains(itunes.getTitle())) {
            imageView_star.setImageResource(R.drawable.blackstar);
            check_list.set(position,1);

        }
        else {
            imageView_star.setImageResource(R.drawable.whitestar);
        }

        imageView_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (check_list.get(position).equals(0)) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Add to Favourites")
                            .setMessage("Are you sure that you want to add this App to favourites?")
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d("negative", "neg");
                                }
                            })
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    imageView_star.setImageResource(R.drawable.blackstar);
                                    check_list.set(position, 1);

                                    editor.putBoolean(itunes.getTitle(),true);
                                    editor.apply();



                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else if(check_list.get(position).equals(1)){

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

                                    imageView_star.setImageResource(R.drawable.whitestar);
                                    check_list.set(position, 0);
                                    editor.remove(itunes.getTitle());
                                    editor.apply();


                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();

                }

            }
        });


        return convertView;
    }

    static interface IGetCheckList{
        public void get_check_list(ArrayList<Integer> check_list);
    }
}
