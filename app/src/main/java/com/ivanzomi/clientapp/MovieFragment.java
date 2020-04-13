package com.ivanzomi.clientapp;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import recycler.coverflow.RecyclerCoverFlow;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    static TextView txtallmovie;
    static RecyclerView allmovie;
    static RecyclerCoverFlow list;

    public MovieFragment() {
        // Required empty public constructor
    }
    View myView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       myView =  inflater.inflate(R.layout.fragment_movie, container, false);
       txtallmovie = myView.findViewById(R.id.txtallmovie);
       allmovie = myView.findViewById(R.id.allmovie);
       list = myView.findViewById(R.id.list);
       activity = getActivity();

       FirebaseConnect fConnect = new FirebaseConnect(getContext(),getFragmentManager());
       fConnect.getAllMoviesByMovieFragment();
       fConnect.getTopTenMovies();
       loadAds();
       return myView;
    }
    FrameLayout container1, container2;
    AdView  adView1, adView2;

    public  void loadAds()
    {
        MobileAds.initialize(getContext(),getString(R.string.app_id));
        adView1 =  new AdView(getContext());
        adView2 = new AdView(getContext());

        container1 = myView.findViewById(R.id.container1);
        container2 = myView.findViewById(R.id.container2);

        adView1.setAdUnitId(getString(R.string.banner_unit_id));
        adView2.setAdUnitId(getString(R.string.banner_unit_id));

        adView1.setAdSize(getAdSize());
        adView2.setAdSize(getAdSize());

        adView1.loadAd(new AdRequest.Builder().build());
        adView2.loadAd(new AdRequest.Builder().build());

        container1.addView(adView1);
        container2.addView(adView2);


    }
    private AdSize getAdSize() {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(getContext(), adWidth);
    }

    public static Activity activity;
    public static void setHeader(String header)
    {
        activity.setTitle(header);

    }
}
