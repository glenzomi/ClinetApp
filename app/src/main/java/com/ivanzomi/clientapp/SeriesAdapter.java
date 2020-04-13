package com.ivanzomi.clientapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.MovieHolder> {
    ArrayList<SeriesModel> seriesModels = new ArrayList<>();
    Context context;
    FragmentManager fm;
    private InterstitialAd interstitialAd;

    ArrayList<String> seriesIds = new ArrayList<>();
    public SeriesAdapter(ArrayList<SeriesModel> SeriesModels, final Context context, FragmentManager fm) {
        this.seriesModels = seriesModels;
        this.context = context;
        this.fm = fm;
        MobileAds.initialize(context,context.getString(R.string.app_id));
        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(context.getString(R.string.interstitial_unit_id));
        interstitialAd.loadAd(new AdRequest.Builder().build());
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
            }
        });
    }


    public SeriesAdapter(ArrayList<SeriesModel> seriesModels, final Context context, FragmentManager fm, ArrayList<String> seriesIds) {
        this.seriesModels = seriesModels;
        this.context = context;
        this.fm = fm;
        this.seriesIds = seriesIds;
        MobileAds.initialize(context,context.getString(R.string.app_id));
        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(context.getString(R.string.interstitial_unit_id));
        interstitialAd.loadAd(new AdRequest.Builder().build());
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.

            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                interstitialAd.loadAd(new AdRequest.Builder().build());
                // Code to be executed when the interstitial ad is closed.
            }
        });

    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movieitem,parent,false);
        return new MovieHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, final int position) {
        Glide.with(context)
                .load(seriesModels.get(position).seriesImage)
                .into(holder.movieImage);

        holder.movieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interstitialAd.isLoaded())
                {

                    interstitialAd.show();
                    goToNext(position);
                }
                else
                {
                    goToNext(position);

                }


            }
        });

    }
    public void goToNext(int position)
    {
        MainActivity.preFrg = MainActivity.currentFrag;
        MainActivity.currentFrag = context.getString(R.string.seies_det_frag);
        SeriesDetailFragment detfrag = new SeriesDetailFragment();
        detfrag.model = seriesModels.get(position);
        detfrag.seriesId = seriesIds.get(position);
        setFragment(detfrag);
        SeriesFragment.setHeader(seriesModels.get(position).seriesName);
    }
    public void  setFragment(Fragment detfrag)
    {
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.navContent,detfrag);
        ft.commit();

    }

    @Override
    public int getItemCount() {
        return seriesModels.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder{
        ImageView movieImage;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            movieImage=itemView.findViewById(R.id.image);
        }
    }
}
