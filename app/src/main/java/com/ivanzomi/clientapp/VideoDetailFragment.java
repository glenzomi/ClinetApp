package com.ivanzomi.clientapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import es.dmoral.toasty.Toasty;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideoDetailFragment extends Fragment {

    public VideoDetailFragment() {
        // Required empty public constructor
    }
    View myView;

    boolean isfullscreen;
    ImageView fullscreenButton;
    SimpleExoPlayerView playerView;
    public static SimpleExoPlayer player;
    MovieModel movieModel;
    ProgressBar loading;
    FloatingActionButton download;
    RelativeLayout playerContent;
    String link;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_video_detail, container, false);
        loadAds();
        fullscreenButton = myView.findViewById(R.id.fullscreen);
        playerContent = myView.findViewById(R.id.playerContent);
        fullscreenButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SourceLockedOrientationActivity")
            @Override
            public void onClick(View v) {
                if (isfullscreen)
                {
                    getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
                    DisplayMetrics displayMetrics =  new DisplayMetrics();
                    getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int width = (int) (displayMetrics.widthPixels/getActivity().getResources().getDisplayMetrics().density);
                    int height = (int) (300*getContext().getResources().getDisplayMetrics().density);

                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = height;
                    playerView.setLayoutParams(params);
                    isfullscreen = false;
                    largetemplate.setVisibility(View.VISIBLE);
                    smalltemplate.setVisibility(View.VISIBLE);
                    MainActivity.toolbar.setVisibility(View.VISIBLE);
                }
                else
                {
                    getActivity().getWindow().getDecorView().setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_FULLSCREEN |
                                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    );
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    DisplayMetrics displayMetrics =  new DisplayMetrics();
                    getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int width = (int) (displayMetrics.widthPixels/getActivity().getResources().getDisplayMetrics().density);
                   int height = 0;
                   if(Build.VERSION.SDK_INT< Build.VERSION_CODES.P)
                   {

                       height = displayMetrics.heightPixels;
                   }
                   else {
                       height = (int) (displayMetrics.heightPixels / getContext().getResources().getDisplayMetrics().density);
                   }

                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)playerView.getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = height;

                    playerContent.setLayoutParams(params);
                    playerView.setLayoutParams(params);
//                    largetemplate.setVisibility(View.GONE);
//                    smalltemplate.setVisibility(View.GONE);
                    MainActivity.toolbar.setVisibility(View.GONE);
                    isfullscreen = true;
                }
            }
        });
        playerView = myView.findViewById(R.id.player_view);
        loading = myView.findViewById(R.id.loading);
        player = ExoPlayerFactory.newSimpleInstance(getContext(),new DefaultTrackSelector());

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        String url = null;
        try {
            url = "https://media-fire-api.herokuapp.com/?url="+ URLEncoder.encode(movieModel.movieVideo,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            JSONObject object = response.getJSONObject(0);
                            DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer");
                            ExtractorsFactory factory = new DefaultExtractorsFactory();
                            try {
                                 String url = object.getString("file");
                                String [] arr = url.split(":");

                                if(arr[0].equals("http")) {

                                    url = arr[0] + "s:" + arr[1];
                                }
                                MediaSource source = new ExtractorMediaSource(Uri.parse(url),httpDataSourceFactory,factory,null,null);
                                player.prepare(source);
                                link = url;
                                playerView.setPlayer(player);
                                player.setPlayWhenReady(true);
                                SimpleExoPlayer.EventListener listener = new ExoPlayer.EventListener() {
                                    @Override
                                    public void onTimelineChanged(Timeline timeline, Object manifest) {

                                    }

                                    @Override
                                    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                                    }

                                    @Override
                                    public void onLoadingChanged(boolean isLoading) {

                                    }

                                    @Override
                                    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                                        if (playbackState == SimpleExoPlayer.STATE_BUFFERING)
                                        {
                                            loading.setVisibility(View.VISIBLE);
                                        }
                                        else
                                        {
                                            loading.setVisibility(View.GONE);
                                        }

                                    }

                                    @Override
                                    public void onRepeatModeChanged(int repeatMode) {

                                    }

                                    @Override
                                    public void onPlayerError(ExoPlaybackException error) {

                                    }

                                    @Override
                                    public void onPositionDiscontinuity() {

                                    }

                                    @Override
                                    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                                    }
                                };
                                player.addListener(listener);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        catch (Exception ex) {
                            Toasty.error(getContext(),"JSON Exceptiion",Toasty.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(request);
        RetryPolicy retryPolicy = new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 10000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 5;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        };
        request.setRetryPolicy(retryPolicy);


    download = myView.findViewById(R.id.btndownload);
    download.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M)
            {
                if ( getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                {
                    downloadFile(link);

                }
                else
                {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
                    && shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                    {
                        Toasty.error(getContext(),"Please Accept Permission",Toasty.LENGTH_LONG).show();
                    }
                    String [] permissions =  new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    requestPermissions(permissions,123);

                }


            }
            else
            {
                downloadFile(link);
            }

        }
    });



        return myView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       if (requestCode == 123)
       {
           if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] ==  PackageManager.PERMISSION_GRANTED)
           {
               downloadFile(link);

           }
       }
    }
    public void downloadFile(String filelink)
    {
        DownloadManager.Request request  = new DownloadManager.Request(Uri.parse(link));
        //Newtwork Allow
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle(movieModel.movieName+".mp4");
        request.setDescription("Download File....");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION);
        String filename = movieModel.movieName+System.currentTimeMillis()+".mp4";
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,filename);
        DownloadManager manager = (DownloadManager)getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
        Toasty.success(getContext(),"Download Started",Toasty.LENGTH_LONG).show();


    }
    TemplateView largetemplate, smalltemplate;

    public void loadAds() {
        MobileAds.initialize(getContext(),getString(R.string.app_id));
        AdLoader adLoader = new AdLoader.Builder(getContext(), getContext().getString(R.string.native_unit_id))
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        ColorDrawable background = new ColorDrawable(Color.WHITE);
                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();

                        smalltemplate = myView.findViewById(R.id.smalltemplate);
                        smalltemplate.setStyles(styles);
                        smalltemplate.setNativeAd(unifiedNativeAd);

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

                        largetemplate = myView.findViewById(R.id.mediumtemplate);
                        largetemplate.setStyles(styles);
                        largetemplate.setNativeAd(unifiedNativeAd);

                    }
                })
                .build();

        adLoader2.loadAd(new AdRequest.Builder().build());
    }


}
