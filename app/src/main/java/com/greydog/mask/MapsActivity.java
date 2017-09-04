package com.greydog.mask;

import android.app.Activity;
import android.app.Application;
import android.graphics.Point;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.R.attr.width;
import static android.widget.RelativeLayout.ALIGN_PARENT_BOTTOM;
import static android.widget.RelativeLayout.ALIGN_PARENT_TOP;
import static android.widget.RelativeLayout.CENTER_IN_PARENT;
import static com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap _map;
    private MaskView _maskView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        _map = googleMap;

        // Add a marker in London and move the camera
        LatLng london = new LatLng(51.5085, -0.1249);
        _map.addMarker(new MarkerOptions().position(london).title("Marker in London"));
        _map.moveCamera(CameraUpdateFactory.newLatLngZoom(london, 14f));

        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        int height = displaymetrics.heightPixels;

        _maskView = new MaskView(this);
        _maskView.init(new Point(width / 2, height / 2), "Dismiss mask view", 1f);

        _maskView.getMaskButton().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                _maskView.setVisibility(View.GONE);
            }
        });

        addContentView(_maskView, lParams);
    }
}
