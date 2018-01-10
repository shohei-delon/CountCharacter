package com.delon.user.countcharacternumber;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener,DialogInterface.OnClickListener {

    private ListView listView;

    private SentenceData sentenceData;
    private ListAdapter listAdapter;
    private ListItem listItem1;
    private List<ListItem> list  = new ArrayList<>();

    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,com.delon.user.countcharacternumber.CountActivity.class);
                startActivity(intent);
            }
        });

        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner)parent;
                String item = (String)spinner.getSelectedItem();
                switch(item) {
                    case "UPDATE":
                        loadList(sentenceData.getAll());
                        break;
                    case "COUNT":
                        loadList(sentenceData.sortCountNumber());
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sentenceData = new SentenceData(this);

        //紐づけ
        listView = (ListView)findViewById(R.id.main_listView);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

        //adapter へ設定
        listAdapter = new ListAdapter(this,android.R.layout.simple_list_item_1,list);

        listView.setAdapter(listAdapter);

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this,"ca-app-pub-9589073381069791~8898760064");

        // Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
        // values/strings.xml.
        mAdView = (AdView) findViewById(R.id.ad_view);

        // Create an ad request. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder().build();

        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListItem listItem = list.get(position);

        Intent intent = new Intent(this,CountActivity.class);
        intent.putExtra("title",listItem.getTitle());
        intent.putExtra("contents",listItem.getContents());
        startActivity(intent);

        sentenceData.delete(listItem.getId());
    }



    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        listItem1 = list.get(position);

        new AlertDialog.Builder(this)
                .setTitle("delete")
                .setPositiveButton("OK", this)
                .setNegativeButton("NO", null)
                .show();

        return false;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        sentenceData.delete(listItem1.getId());
    }

    private void loadList(Cursor cursor){
        list.clear();

        if (cursor.moveToFirst()){
            do {
                ListItem listItem = new ListItem();
                listItem.setId(cursor.getInt(cursor.getColumnIndex("id")));
                listItem.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                listItem.setContents(cursor.getString(cursor.getColumnIndex("contents")));
                listItem.setNumber(cursor.getInt(cursor.getColumnIndex("number")));
                list.add(listItem);
            }while (cursor.moveToNext());
        }

        listAdapter.notifyDataSetChanged();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        loadList(sentenceData.getAll());
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    /** Called when leaving the activity */
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }


}
