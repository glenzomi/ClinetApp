package com.ivanzomi.clientapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    public static CarouselView carouselView;
    View myView;
    static Context context;
    static RecyclerView allMovie, allSeries,allCategory;
    static TextView txtallmovie, txtallseries,txtallcategory;

    public static ArrayList<String> sampleImages = new ArrayList<>();

    static ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            Glide.with(context)
                    .load(sampleImages.get(position))
                    .into(imageView);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_home, container, false);
        carouselView = myView.findViewById(R.id.carouselView);
        allMovie = myView.findViewById(R.id.allmovie);
        txtallmovie = myView.findViewById(R.id.txtmovie);
        allSeries = myView.findViewById(R.id.allseries);
        txtallseries = myView.findViewById(R.id.txtseries);
        txtallcategory = myView.findViewById(R.id.txtcategory);
        allCategory = myView.findViewById(R.id.allcategory);
        SeriesFragment.activity = getActivity();
        MovieFragment.activity = getActivity();
        loadAds();



        context = getContext();
        HomeFragment.carouselView.setPageCount(sampleImages.size());
        HomeFragment.carouselView.setImageListener(imageListener);

        FirebaseConnect fConnect = new FirebaseConnect(getContext(),getFragmentManager());
        fConnect.getAllCategory();
        fConnect.showSlide();
        fConnect.getAllMovies();
        fConnect.getAllSeries();



        return myView;
    }
    FrameLayout adContainer1, adContainer2, adContainer3;
    AdView adView1,adView2,adView3;
    AdRequest request1, request2, request3;
    public void loadAds()
    {
        MobileAds.initialize(getContext(),getString(R.string.app_id));
        adContainer1 = myView.findViewById(R.id.adcontainer);
        adContainer2 = myView.findViewById(R.id.adcontainer2);
        adContainer3 = myView.findViewById(R.id.adcontainer3);

        adView1 = new AdView(getContext());
        adView2 = new AdView(getContext());
        adView3 = new AdView(getContext());

        request1 = new AdRequest.Builder().build();
        adView1.setAdUnitId(getString(R.string.banner_unit_id));
        adView1.setAdSize(getAdSize());
        adContainer1.addView(adView1);
        adView1.loadAd(request1);

        request2 = new AdRequest.Builder().build();
        adView2.setAdUnitId(getString(R.string.banner_unit_id));
        adView2.setAdSize(getAdSize());
        adView2.loadAd(request2);
        adContainer2.addView(adView2);

        request3 = new AdRequest.Builder().build();
        adView3.setAdUnitId(getString(R.string.banner_unit_id));
        adView3.setAdSize(getAdSize());
        adView3.loadAd(request3);
        adContainer3.addView(adView3);


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