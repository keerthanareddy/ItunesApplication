package com.example.keerthana.itunesapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractList;
import java.util.ArrayList;

/**
 * Created by keerthana on 2/23/2017.
 */

public class ItunesDataParser {

    static public class Parsing_itunes_class{
        static ArrayList<Itunes> parser(String in) throws JSONException{
            ArrayList<Itunes> itunesArrayList = new ArrayList<Itunes>();
            JSONObject root = new JSONObject(in);
            JSONObject feed_obj = root.getJSONObject("feed");
            //JSONObject author_object = feed_obj.getJSONObject("author");
            JSONArray entry_array = feed_obj.getJSONArray("entry");
            for(int i=0; i< entry_array.length(); i++){
                JSONObject itunes_name_obj = entry_array.getJSONObject(i);
                Itunes it = new Itunes();
                JSONObject label_obj = itunes_name_obj.getJSONObject("im:name");
                it.setTitle(String.valueOf(label_obj.get("label")));
                JSONObject price_obj = itunes_name_obj.getJSONObject("im:price");
                String price_value = String.valueOf(price_obj.get("label"));
                price_value = price_value.replace("$"," ");
                it.setPrice(price_value);
                JSONArray img_array = itunes_name_obj.getJSONArray("im:image");
                JSONObject img_label_obj = img_array.getJSONObject(1);
                it.setLogo(img_label_obj.getString("label"));
                itunesArrayList.add(it);
            }

            return itunesArrayList;
        }

    }
}
