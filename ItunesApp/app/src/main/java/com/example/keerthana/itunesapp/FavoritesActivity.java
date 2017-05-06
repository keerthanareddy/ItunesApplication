package com.example.keerthana.itunesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class FavoritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ArrayList<Itunes> fav_itunes = (ArrayList<Itunes>)getIntent().getExtras().getSerializable("fav_list");
        ArrayList<Integer> fav_list_star = new ArrayList<Integer>(Collections.nCopies(fav_itunes.size(),1));
        create_list(fav_itunes);
        Button back_btn = (Button) findViewById(R.id.back_button);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Itunes> my_fav_itunes = FavListAdapter.my_itunes;
                Intent intent_back = new Intent();
                intent_back.putExtra("favorite_itunes",MainActivity.note_position_fav);
                setResult(RESULT_OK,intent_back);
                finish();
            }
        });

    }

    public void create_list(ArrayList<Itunes> itunes){
        ListView listView = (ListView) findViewById(R.id.mylist_fav);
        FavListAdapter adapter = new FavListAdapter(this,R.layout.root_item_layout,itunes);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);

    }
}
