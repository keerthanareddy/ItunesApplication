package com.example.keerthana.itunesapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by keerthana on 2/23/2017.
 */

public class ItunesAppData extends AsyncTask<Object, Object, ArrayList<Itunes>> {
    MainActivity activity;
    public ItunesAppData(MainActivity activity){this.activity = activity;}


    @Override
    protected ArrayList<Itunes> doInBackground(Object... params) {

        BufferedReader reader = null;
        try {
            URL url = new URL((String) params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if(statusCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                //Log.d("demo",sb.toString());
                return ItunesDataParser.Parsing_itunes_class.parser(sb.toString());
            }



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }

        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        activity.progressDialog = new ProgressDialog(activity);
        activity.progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        activity.progressDialog.setCancelable(false);
        activity.progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Itunes> itunes) {

        activity.progressDialog.dismiss();
        activity.setData(itunes);

        super.onPostExecute(itunes);
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);
    }

    static interface ISetParsedData{
        public void setData(ArrayList<Itunes> itunes);
    }
}
