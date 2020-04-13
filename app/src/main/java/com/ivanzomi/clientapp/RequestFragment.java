package com.ivanzomi.clientapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.telephony.TelephonyScanManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import es.dmoral.toasty.Toasty;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestFragment extends Fragment {

    public RequestFragment() {
        // Required empty public constructor
    }
 View myView;
    Button btnsave,btncancle;
    EditText edtname,edtimge;

    FirebaseFirestore db;
    CollectionReference ref;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView =  inflater.inflate(R.layout.fragment_request, container, false);
        db = FirebaseFirestore.getInstance();
        final CollectionReference ref = db.collection(getString(R.string.reques_ref));

        edtname = myView.findViewById(R.id.edtname);
        edtimge = myView.findViewById(R.id.edtimage);
        btnsave = myView.findViewById(R.id.btnsave);
        btncancle = myView.findViewById(R.id.btncancle);

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtimge.getText().toString().trim().equals("")
                    || !edtname.getText().toString().trim().equals(""))
                {
                    RequestModel model = new RequestModel();
                    model.name = edtname.getText().toString().trim();
                    model.imagelink = edtimge.getText().toString().trim();
                    ref.add(model);
                    edtname.setText("");
                    edtimge.setText("");
                    Toasty.success(getContext(),"Request Send",Toasty.LENGTH_LONG).show();
                }
              else  {
                  Toasty.error(getContext(),"Please Fill Data",Toasty.LENGTH_LONG).show();

                }

            }
        });
        loadAds();
        return myView;
    }
    FrameLayout container1, container2;
    AdView adView1, adView2;

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
}
