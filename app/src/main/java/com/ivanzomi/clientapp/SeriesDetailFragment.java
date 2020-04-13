package com.ivanzomi.clientapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class SeriesDetailFragment extends Fragment {

    public SeriesDetailFragment() {
        // Required empty public constructor
    }
    public static String seriesId;
    public static SeriesModel model;
    static RecyclerView list;
    static ImageView image;
    static TextView seriesName, viewCount;
    static FloatingActionButton epCount;
    View myView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         myView = inflater.inflate(R.layout.fragment_series_detail, container, false);
        MobileAds.initialize(getContext(), getContext().getString(R.string.app_id));
        InterstitialAd mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId(getContext().getString(R.string.interstitial_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.show();
        list = myView.findViewById(R.id.ep_list);
        image = myView.findViewById(R.id.image);
        seriesName = myView.findViewById(R.id.series_name);
        viewCount = myView.findViewById(R.id.txtviewcount);
        epCount = myView.findViewById(R.id.txtepcount);
        loadAds();
        FirebaseConnect firebaseConnect = new FirebaseConnect(getContext(),getFragmentManager());
        firebaseConnect.updateSeriesCount(model,seriesId);


        if (model!= null)
        {
            Glide.with(getContext())
                    .load(model.seriesImage)
                    .into(image);
            seriesName.setText(model.seriesName);
            viewCount.setText(model.seriesCount+"");
        }
        FirebaseConnect fconect = new FirebaseConnect(getContext(),getFragmentManager());
        fconect.getEpBySeriesName(model.seriesName);
        return myView;
    }

    public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }

    FrameLayout adContainer1, adContainer2;
    AdView adView1,adView2;
    AdRequest request1, request2;
    public void loadAds()
    {
        MobileAds.initialize(getContext(),getString(R.string.app_id));
        adContainer1 = myView.findViewById(R.id.adcontainer1);
        adContainer2 = myView.findViewById(R.id.adcontainer2);


        adView1 = new AdView(getContext());
        adView2 = new AdView(getContext());


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


        AdLoader adLoader = new AdLoader.Builder(getContext(), getContext().getString(R.string.native_unit_id))
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        ColorDrawable background = new ColorDrawable(Color.WHITE);
                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();

                        TemplateView template = myView.findViewById(R.id.smalltemplate);
                        template.setStyles(styles);
                        template.setNativeAd(unifiedNativeAd);

                    }
                })
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());

        AdLoader adLoader2 = new AdLoader.Builder(getContext(), getContext().getString(R.string.native_unit_id))
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        ColorDrawable background = new ColorDrawable(Color.WHITE);
                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();

                        TemplateView template = myView.findViewById(R.id.mediumtemplate);
                        template.setStyles(styles);
                        template.setNativeAd(unifiedNativeAd);

                    }
                })
                .build();

        adLoader2.loadAd(new AdRequest.Builder().build());


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

}
