package com.example.linki.androidmusicplayerproject;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class PlayActivity extends AppCompatActivity {

    Button btn_next,btn_previous,btn_pause;
    TextView songTextLabe;
    SeekBar songSeekbar;


    static MediaPlayer myMediaPlayerl;
    int position;
    String sname;
    ArrayList<File> mySongs;
    Thread updateseelBar;




    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        btn_next=(Button) findViewById(R.id.next);
        btn_pause=(Button) findViewById(R.id.pause);
        btn_previous=(Button) findViewById(R.id.previous);

        songTextLabe= (TextView) findViewById(R.id.songlabel);
        songSeekbar= (SeekBar) findViewById(R.id.seekBar);
        getSupportActionBar().setTitle("Now Playing ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



       


        if(myMediaPlayerl!=null){
            myMediaPlayerl.stop();
            myMediaPlayerl.release();
        }
        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        mySongs=(ArrayList) bundle.getParcelableArrayList("songs");
        sname=mySongs.get(position).getName().toString();

        String songName = i.getStringExtra("songname");
        songTextLabe.setText(songName);
        songTextLabe.setSelected(true);

        position= bundle.getInt("pos",0);
        Uri u = Uri.parse(mySongs.get(position).toString());
        myMediaPlayerl = MediaPlayer.create(getApplicationContext(),u);

        myMediaPlayerl.start();
        songSeekbar.setMax(myMediaPlayerl.getDuration());


       


        songSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                myMediaPlayerl.seekTo(seekBar.getProgress());
            }
        });

        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songSeekbar.setMax((myMediaPlayerl.getDuration()));
                if(myMediaPlayerl.isPlaying()){
                    btn_pause.setBackgroundResource(R.drawable.icon_play);
                    myMediaPlayerl.pause();
                }
                else
                {
                    btn_pause.setBackgroundResource(R.drawable.icon_pause);
                    myMediaPlayerl.start();
                }
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myMediaPlayerl.stop();
                myMediaPlayerl.release();
                position=((position+1)%mySongs.size());

                Uri u = Uri.parse(mySongs.get(position).toString());
                myMediaPlayerl = MediaPlayer.create(getApplicationContext(),u);
                sname=mySongs.get(position).getName().toString();
                songTextLabe.setText(sname);
                myMediaPlayerl.start();


            }
        });
        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myMediaPlayerl.stop();
                myMediaPlayerl.release();
                position=(((position-1)<0)?(mySongs.size()-1):(position-1));

                Uri u = Uri.parse(mySongs.get(position).toString());
                myMediaPlayerl = MediaPlayer.create(getApplicationContext(),u);
                sname=mySongs.get(position).getName().toString();
                songTextLabe.setText(sname);
                myMediaPlayerl.start();

            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
