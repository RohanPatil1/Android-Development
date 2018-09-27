package com.rohan.android.assignments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMenuActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;

    @BindView(R.id.menuToolbar)
    Toolbar menuToolbar;
    @BindView(R.id.sem1Btn)
    ImageView sem1Btn;
    @BindView(R.id.sem2Btn)
    ImageView sem2Btn;

    Boolean prefBoolValue = false;
    SharedPreferences preferences;

    @Override
    protected void onStart() {
        super.onStart();
        if (prefBoolValue) {
            Intent intentSem1 = new Intent(MainMenuActivity.this, SemesterActivity.class);
            startActivity(intentSem1);
        }
    }

    private AdView mAdView;
    ProgressBar progressBar;
    TextView mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ButterKnife.bind(this);

        progressBar = findViewById(R.id.loading_spinner);
        mEmptyView = findViewById(R.id.empty_state);
        progressBar.setVisibility(View.VISIBLE);


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //Connection successfull

            //Ad_Setup
            MobileAds.initialize(this, getString(R.string.app_id));
            mAdView = findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId(getString(R.string.interstitial_id));
            mInterstitialAd.loadAd(new AdRequest.Builder().build());

            progressBar.setVisibility(View.GONE);

        } else {
            //Connection Failed !

            //Hide progress bar in order to display error message
            progressBar.setVisibility(View.GONE);
            //Update UI to show error message
            mEmptyView.setVisibility(View.VISIBLE);
            mEmptyView.setText(R.string.no_internet);
        }
        preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        prefBoolValue = preferences.getBoolean("boolValue", false);
        // by default phone item if you want to make checked then make it true

        setSupportActionBar(menuToolbar);
        menuToolbar.setTitle(getString(R.string.assignments));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_setting) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
            return true;
        } else if (id == R.id.action_admin) {
            Intent settingsIntent = new Intent(this, LoginActivty.class);
            startActivity(settingsIntent);
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick({R.id.sem1Btn, R.id.sem2Btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sem1Btn:
                Intent intentSem1 = new Intent(MainMenuActivity.this, SemesterActivity.class);
                startActivity(intentSem1);
                break;

            case R.id.sem2Btn:
                Toast.makeText(this, R.string.coming_soon, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
