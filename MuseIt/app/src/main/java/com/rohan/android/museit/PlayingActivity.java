package com.rohan.android.museit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class PlayingActivity extends AppCompatActivity {
    private static final String TAG = PlayingActivity.class.getName();
    SeekBar position_bar;
    ImageView album;
    TextView name;
    TextView artist;
    Button playBtn;
    Button backbtn;

    private int imageUrl;
    private String names;
    private String artists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);
        album = (ImageView) findViewById(R.id.album_playing);

        name = (TextView) findViewById(R.id.name_playing);
        artist = (TextView) findViewById(R.id.descrip_playing);
        position_bar = (SeekBar) findViewById(R.id.positionBar);

        //PlayButton ClickListner Setup
        playBtn = (Button) findViewById(R.id.playBtn);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playBtnClick(v);
            }
        });

        //BackButton ClickListner Setup
        backbtn = (Button) findViewById(R.id.back_btn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMain(v);
            }
        });

        // Getting Data Through Intent
        getIncomingIntent();

        //Set Data
        album.setImageResource(imageUrl);
        name.setText(names);
        artist.setText(artists);

        //Round Album Image
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageUrl);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);
        album.setImageDrawable(roundedBitmapDrawable);
    }

    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");
        if (getIntent().hasExtra("image_url") && getIntent().hasExtra("name") && getIntent().hasExtra("artist")) {
            Log.d(TAG, "getIncomingIntent: found intent extras.");
            imageUrl = getIntent().getIntExtra("image_url", 2131165279);
            names = getIntent().getStringExtra("name");
            artists = getIntent().getStringExtra("artist");
        }
    }


    public void playBtnClick(View view) {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.music);
        mp.seekTo(0);
        mp.setLooping(true);
        if (!mp.isPlaying()) {
            // Stopping
            Log.i(TAG, "MP Started");
            playBtn.setBackgroundResource(R.drawable.stop);
        } else {
            // Playing
            playBtn.setBackgroundResource(R.drawable.play);
        }

    }

    public void goMain(View view) {
        Intent intent_main = new Intent(PlayingActivity.this, MainActivity.class);
        startActivity(intent_main);
    }
}
