package com.ivanzomi.clientapp;

import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class GoogleAds {

    public static InterstitialAd loadInterstitialAds(Context context)
    {
        MobileAds.initialize(context,context.getString(R.string.app_id));
        InterstitialAd mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(context.getString(R.string.interstitial_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        return mInterstitialAd;

    }
}
