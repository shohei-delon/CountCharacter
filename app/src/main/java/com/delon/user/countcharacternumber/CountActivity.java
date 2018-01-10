package com.delon.user.countcharacternumber;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Calendar;

public class CountActivity extends AppCompatActivity {

    private TextView numberTextView;
    private EditText titleEditText;
    private EditText contentsEditText;

    private SentenceData sentenceData;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        Intent intent10 = getIntent();

        sentenceData = new SentenceData(this);

        titleEditText = new EditText(this);
        titleEditText = (EditText)findViewById(R.id.title_editText);
        if (!(intent10.getStringExtra("title") == null)){
            titleEditText.setText(intent10.getStringExtra("title"));
        }

        contentsEditText = new EditText(this);
        contentsEditText = (EditText)findViewById(R.id.contents_editText);
        if (!(intent10.getStringExtra("contents") == null)){
            contentsEditText.setText(intent10.getStringExtra("contents"));
        }
        contentsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 入力文字数の表示
                int txtLength = s.length();
                numberTextView.setText(Integer.toString(txtLength));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        numberTextView = new TextView(this);
        numberTextView = (TextView)findViewById(R.id.count_number_text);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleEditText.getText().toString().length() == 0){
                    new AlertDialog.Builder(CountActivity.this)
                            .setTitle("error")
                            .setMessage("NO TITLE")
                            .setPositiveButton("OK",null)
                            .show();
                }else {
                    saveData();
                    goMain();
                }
            }
        });
    }

    private void saveData(){
        //SQLiteに書き込み
        sentenceData.insert(titleEditText.getText().toString(),
                contentsEditText.getText().toString(),
                contentsEditText.getText().toString().length());
    }

    private void goMain(){
        //mainへ移動
        Intent intent = new Intent(CountActivity.this, com.delon.user.countcharacternumber.MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            // 戻るボタンの処理
            if(!(contentsEditText.getText().toString().length() == 0)) {
                // 編集しているときに戻るボタンを押したときは警告をする
                new AlertDialog.Builder(this)
                        .setTitle("Editing now")
                        .setMessage("continue?")
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                saveData();
                                goMain();
                            }
                        })
                        .setNegativeButton("Don't save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                goMain();
                            }
                        })
                        .setNeutralButton("Cancel", null)
                        .show();
                return false;
            }
            return super.onKeyDown(keyCode, event);
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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
