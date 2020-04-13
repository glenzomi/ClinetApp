package com.ivanzomi.clientapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer;
     static Toolbar toolbar;
     static String currentFrag="";
     static  String preFrg = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        setContentView(R.layout.activity_main);
        drawer=findViewById(R.id.drawer);
         toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Home");
        currentFrag = getString(R.string.home_frag);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.openDrawer,
                R.string.closeDrawer);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        setFragment(new HomeFragment());
        NavigationView navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.home_menu)
                {
                    setFragment(new HomeFragment());
                    setTitle(getString(R.string.home_frag));
                    currentFrag = getString(R.string.home_frag);
                }
                else
                if (item.getItemId()==R.id.movie_menu)
                {
                    setFragment(new MovieFragment());
                    setTitle(getString(R.string.movie_frag));
                    currentFrag = getString(R.string.movie_frag);
                }
                else
                    if (item.getItemId()== R.id.series_menu)
                    {
                        setFragment(new SeriesFragment());
                        setTitle(getString(R.string.series_frag));
                        currentFrag = getString(R.string.series_frag);
                    }
                    else
                        if (item.getItemId()== R.id.share_menu)
                        {
                            currentFrag = getString(R.string.share_frag);
                            Intent shareintent = new Intent();
                            shareintent.setAction(Intent.ACTION_SEND);

                            // shareintent.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id="+BuildConfig.APPLICATION_ID);
                            shareintent.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=com.zomidictionary.tedim");
                            shareintent.setType("text/plain");
                           startActivity(shareintent);

                        }
                    else
                        if (item.getItemId()== R.id.request_menu)
                        {
                            setFragment(new RequestFragment());
                            setTitle(getString(R.string.request_str));
                            currentFrag = getString(R.string.reques_ref);
                        }
                        else
                            if (item.getItemId()== R.id.about_menu)
                            {
                                setFragment(new AboutFragment());
                                setTitle(getString(R.string.about_str));
                                currentFrag = getString(R.string.about_str);
                            }
                drawer.closeDrawer(Gravity.LEFT);
                return false;
            }
        });
    }

    public void setFragment(Fragment f)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.navContent,f);
        ft.commit();
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public void onBackPressed() {
        if (currentFrag.equals(getString(R.string.home_frag)))
        {
            if (drawer.isDrawerOpen(Gravity.LEFT))
            {
                drawer.closeDrawer(Gravity.LEFT);
            }
            else
            {
               AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Confirmation");
                alert.setMessage("Are you suar to Exit?");
                alert.setPositiveButton(Html.fromHtml("<font color='#212121'>Yes"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        finishAffinity();
                    }
                });
                alert.setNegativeButton(Html.fromHtml("<font color='#212121'>No"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });



                alert.show();
            }
        }
       else if (currentFrag.equals(getString(R.string.movie_frag)) ||
        currentFrag.equals(getString(R.string.series_frag))
         || currentFrag.equals(getString(R.string.reques_ref))
        || currentFrag.equals(getString(R.string.share_frag))
        || currentFrag.equals(getString(R.string.about_str)))
        {
            setFragment(new HomeFragment());
            currentFrag = getString(R.string.home_frag);
            setTitle(currentFrag);
        }
       else if (currentFrag.equals(getString(R.string.seies_det_frag)))
        {
            if (preFrg.equals(getString(R.string.home_frag)))
            {
                setFragment(new HomeFragment());
                currentFrag = getString(R.string.home_frag);
                setTitle(currentFrag);
            }
            else if (preFrg.equals(getString(R.string.series_frag)))
            {
                setFragment(new SeriesFragment());
                currentFrag = getString(R.string.series_frag);
                setTitle(currentFrag);

            }
            else  if (preFrg.equals(getString(R.string.movie_dat_frag)))
            {
                setFragment(new SeriesFragment());
                currentFrag = getString(R.string.series_frag);
                setTitle(currentFrag);
                preFrg = getString(R.string.seies_det_frag);
                VideoDetailFragment.player.stop();
            }
        }

       else  if (currentFrag.equals(getString(R.string.movie_dat_frag)))
        {
            if (preFrg.equals(getString(R.string.home_frag)))
            {
                setFragment(new HomeFragment());
                currentFrag = getString(R.string.home_frag);
                setTitle(currentFrag);
            }
            else  if (preFrg.equals(getString(R.string.movie_frag)))
            {
                setFragment(new MovieFragment());
                currentFrag = getString(R.string.movie_frag);
                setTitle(currentFrag);
            }
            else  if (preFrg.equals(getString(R.string.seies_det_frag)))
            {
                SeriesDetailFragment detfrag = new SeriesDetailFragment();
                setFragment(detfrag);
                currentFrag = getString(R.string.seies_det_frag);
                preFrg = getString(R.string.movie_dat_frag);
                setTitle(SeriesDetailFragment.model.seriesName);

            }
            else
            {
                setFragment(new SeriesFragment());
                currentFrag = getString(R.string.series_frag);
                setTitle(currentFrag);
            }
            VideoDetailFragment.player.stop();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            MainActivity.toolbar.setVisibility(View.VISIBLE);
        }
       else
        {
            super.onBackPressed();
        }
    }
}
