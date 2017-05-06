package com.example.keerthana.itunesapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItunesAppData.ISetParsedData,ItunesListAdapter.IGetCheckList {
    ProgressDialog progressDialog = null;
    ArrayList<Itunes> itunes;
    public static final int REQ_CODE = 150;
    public static ArrayList<Integer> note_position_fav;
    public static ArrayList<Integer> original_check_list = new ArrayList<Integer>(Collections.nCopies(25,0));





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new ItunesAppData(this).execute("https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.my_menu_list,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 150){
            if (resultCode == RESULT_OK){
                SharedPreferences sharedPreferences = getSharedPreferences("my_fav_itunes",MODE_PRIVATE);
                final SharedPreferences.Editor editor = sharedPreferences.edit();
                ArrayList<Integer> a=data.getExtras().getIntegerArrayList("favorite_itunes");
                ItunesListAdapter.check_list = new ArrayList<Integer>(Collections.nCopies(25,0));
                for (int c=0;c<a.size();c++){

                    if (sharedPreferences.contains(itunes.get(c).getTitle())) {
                        ItunesListAdapter.check_list.set(a.get(c), 1);
                    }
                }
                create_list(itunes);
                //for (int c=0;c<)
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){

            case R.id.ref_list:

                SharedPreferences sharedPreferences = getSharedPreferences("my_fav_itunes",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear().apply();

                ItunesListAdapter.check_list = new ArrayList<Integer>(Collections.nCopies(25,0));
                //create_list(itunes);

                new ItunesAppData(this).execute("https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");
                break;
            case R.id.fav_list:
                ArrayList<Integer> fav_list = ItunesListAdapter.getCheck_list();
                original_check_list = fav_list;
                ArrayList<Itunes> itunes_fav = new ArrayList<Itunes>();
                note_position_fav = new ArrayList<Integer>();

                for (int i =0; i<fav_list.size();i++ ){
                    if (fav_list.get(i) == 1){
                        itunes_fav.add(itunes.get(i));
                        note_position_fav.add(i);

                    }
                }
                Intent intent = new Intent(MainActivity.this,FavoritesActivity.class);
                intent.putExtra("fav_list",itunes_fav);
                startActivityForResult(intent, REQ_CODE);


                for (int i = 0;i<itunes_fav.size();i++) {
                   //ItunesListAdapter.fav_check_list.set(i,1);
                }
                //create_list(itunes_fav);

                break;
            case R.id.sort_inc:

                int it =0;
                for (Itunes itunes1 : itunes) {
                    if (ItunesListAdapter.check_list.get(it)==0) {
                        itunes1.setChecked_val(0);
                    }
                    else if (ItunesListAdapter.check_list.get(it)==1){
                        itunes1.setChecked_val(1);
                    }
                    it++;
                }
                Collections.sort(itunes, new Comparator<Itunes>() {
                    @Override
                    public int compare(Itunes o1, Itunes o2) {
                        Float f_o1 = Float.valueOf(o1.getPrice());
                        Float f_o2 = Float.valueOf(o2.getPrice());
                        return f_o1.compareTo(f_o2);
                    }
                });

                for (int j=0;j<itunes.size();j++){
                    if (itunes.get(j).getChecked_val() == 0){
                        ItunesListAdapter.check_list.set(j,0);
                    }
                    else if (itunes.get(j).getChecked_val()==1){
                        ItunesListAdapter.check_list.set(j,1);
                    }
                }

                create_list(itunes);
                break;
            case R.id.sort_dec:

                int it_d =0;
                for (Itunes itunes2 : itunes) {
                    if (ItunesListAdapter.check_list.get(it_d)==0) {
                        itunes2.setChecked_val(0);
                    }
                    else if (ItunesListAdapter.check_list.get(it_d)==1){
                        itunes2.setChecked_val(1);
                    }
                    it_d++;
                }
                original_check_list = ItunesListAdapter.getCheck_list();
                Collections.sort(itunes, new Comparator<Itunes>() {
                    @Override
                    public int compare(Itunes o1, Itunes o2) {
                        Float f_o1 = Float.valueOf(o1.getPrice());
                        Float f_o2 = Float.valueOf(o2.getPrice());
                        return f_o2.compareTo(f_o1);
                    }
                });
                for (int j_d=0;j_d<itunes.size();j_d++){
                    if (itunes.get(j_d).getChecked_val() == 0){
                        ItunesListAdapter.check_list.set(j_d,0);
                    }
                    else if (itunes.get(j_d).getChecked_val()==1){
                        ItunesListAdapter.check_list.set(j_d,1);
                    }
                }
                create_list(itunes);

                break;
        }

        return false;
    }

    @Override
    public void setData(ArrayList<Itunes> itunes) {
        this.itunes = itunes;
        create_list(itunes);





    }

    public void create_list(ArrayList<Itunes> itunes){
        ListView listView = (ListView) findViewById(R.id.mylist);
        ItunesListAdapter adapter = new ItunesListAdapter(this,R.layout.root_item_layout,itunes);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);

    }


    @Override
    public void get_check_list(ArrayList<Integer> check_list) {
        Log.d("gettCheck",""+check_list);
    }
}
