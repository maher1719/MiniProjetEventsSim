package com.example.miniprojetevents;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniprojetevents.entities.Event;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

public class AddEvent extends AppCompatActivity {

    private MapView mapView;

    private SlidrConfig mConfig;
    private ImageButton backActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoibWFoZXIxNzE3IiwiYSI6ImNqcGI3em54bjA2N2gza2x4enp1aGZzaDAifQ.FuZ8LLu_2I_5I9pVKVmGZA");
        setContentView(R.layout.activity_add_event);
        int primary = getResources().getColor(R.color.colorPrimary);
        int secondary = getResources().getColor(R.color.colorPrimaryDark);
        mConfig = new SlidrConfig.Builder()
                .primaryColor(primary)
                .secondaryColor(secondary)
                .position(SlidrPosition.LEFT)
                .scrimStartAlpha(0.8f)
                .scrimEndAlpha(0f)
                .distanceThreshold(.25f)
                .edge(true)
                .build();
        // Attach the Slidr Mechanism to this activity
        Slidr.attach(this, mConfig);
        Intent thisActivity = getIntent();
        backActivity = findViewById(R.id.returnButton);
        backActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView titre = findViewById(R.id.TitreEventDetail);
        mapView = findViewById(R.id.mapEventDetail);
        Event event = (Event) thisActivity.getExtras().getSerializable("event");
        titre.setText(event.getTitle());
        mapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        LatLng positionEvent = new LatLng(event.getLatitude(), event.getLongtitude());
                        CameraPosition positionCamera = new CameraPosition.Builder()
                                .target(positionEvent) // Sets the new camera position
                                .zoom(10) // Sets the zoom
                                // Rotate the camera
                                .tilt(30) // Set the camera tilt
                                .build();
                        mapboxMap.setCameraPosition(positionCamera);
                        MarkerOptions m = new MarkerOptions().setTitle(event.getTitle())
                                .position(positionEvent);
                        mapboxMap.addMarker(m);
                        mapboxMap.addOnMapClickListener(new MapboxMap.OnMapClickListener() {
                            @Override
                            public boolean onMapClick(@NonNull LatLng point) {
                                //mapboxMap.addMarker(new MarkerOptions().setPosition(point));
                                return true;
                            }
                        });
                        mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(@NonNull Marker marker) {
                                //mapboxMap.removeMarker(marker);
                                return false;
                            }
                        });
                        // Map is set up and the style has loaded. Now you can add data or make other map adjustments
                    }
                });
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
