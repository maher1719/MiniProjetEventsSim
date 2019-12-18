package com.example.miniprojetevents.map;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.miniprojetevents.AddEvent;
import com.example.miniprojetevents.R;
import com.example.miniprojetevents.database.dao.IEvent;
import com.example.miniprojetevents.entities.Event;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.TransitionOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

    private MapView mapView;


    public BlankFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_blank, container, false);
        mapView = root.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        final String BASE_URL = "http://10.0.2.2:81";
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        IEvent user = retrofit.create(IEvent.class);
        Call<List<Event>> call = user.getEvent();
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                List<Event> events = response.body();
                mapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(@NonNull MapboxMap mapboxMap) {
                        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                            @Override
                            public void onStyleLoaded(@NonNull Style style) {
                                style.setTransition(new TransitionOptions(0, 0, false));
                                List<MarkerOptions> markers = new ArrayList<>();
                                for (Event event : events) {
                                    MarkerOptions m = new MarkerOptions().setTitle(event.getTitle())
                                            .position(new LatLng(event.getLatitude(), event.getLongtitude()));
                                    mapboxMap.addMarker(m);
                                }
                                /*
                                try {
                                    style.addSource(
                                            new GeoJsonSource("EventsMap",
                                                    new URI("http://10.0.2.2:81/mesWebSErvices/geojson.php"),
                                                    new GeoJsonOptions()
                                                            .withCluster(true)
                                                            .withClusterMaxZoom(15)
                                                            .withClusterRadius(50)
                                            )
                                    );
                                } catch (URISyntaxException exception) {
                                    Log.e("errorMap", "Check the URL " + exception.getMessage());
                                }
                                SymbolLayer unclustered = new SymbolLayer("unclustered-points", "EventsMap");
                                unclustered.setFilter(has("titre"));
                                style.addLayer(unclustered);
                                SymbolLayer countLayer = new SymbolLayer("SYMBOL_LAYER_COUNT_LAYER_ID", "EventsMap");
                                countLayer.setProperties(
                                        textField(Expression.toString(get("point_count"))),
                                        textSize(12f),
                                        textColor(getResources().getColor(R.color.white)),
                                        textIgnorePlacement(true),
                                        textAllowOverlap(true)
                                );
                                style.addLayer(countLayer);
                                int[][] layers = new int[][]{
                                        new int[]{10, ContextCompat.getColor(root.getContext(), R.color.colorPrimaryDark)},
                                        new int[]{5, ContextCompat.getColor(root.getContext(), R.color.colorPrimary)},
                                        new int[]{0, ContextCompat.getColor(root.getContext(), R.color.mapbox_blue)}
                                };
                                for (int i = 0; i < layers.length; i++) {
//Add clusters' circles
                                    CircleLayer circles = new CircleLayer("cluster-" + i, "earthquakes");
                                    circles.setProperties(
                                            circleColor(layers[i][1]),
                                            circleRadius(18f)
                                    );
                                    Expression pointCount = toNumber(get("point_count"));
                                    circles.setFilter(
                                            i == 0
                                                    ? all(has("point_count"),
                                                    gte(pointCount, literal(layers[i][0]))
                                            ) : all(has("point_count"),
                                                    gte(pointCount, literal(layers[i][0])),
                                                    lt(pointCount, literal(layers[i - 1][0]))
                                            )
                                    );
                                    style.addLayer(circles);
                                }*/
                                /*mapboxMap.addOnMapClickListener(new MapboxMap.OnMapClickListener() {
                                    @Override
                                    public boolean onMapClick(@NonNull LatLng point) {

                                        return true;
                                    }
                                });*/
                                mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
                                    @Override
                                    public boolean onMarkerClick(@NonNull Marker marker) {
                                        String titleMarker = marker.getTitle();
                                        for (Event event : events) {
                                            if (event.getTitle().equals(titleMarker)) {
                                                Intent EventClicked = new Intent(getContext(), AddEvent.class);
                                                EventClicked.putExtra("event", event);
                                                root.getContext().startActivity(EventClicked);
                                            }

                                        }
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
            public void onFailure(Call<List<Event>> call, Throwable t) {
            }
        });
        /*mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        List<MarkerOptions> markers = new ArrayList<>();
                        MarkerOptions m = new MarkerOptions().setTitle("hello")
                                .position(new LatLng(33.7932, 9.5608));
                        mapboxMap.addMarker(m);
                        mapboxMap.addOnMapClickListener(new MapboxMap.OnMapClickListener() {
                            @Override
                            public boolean onMapClick(@NonNull LatLng point) {
                                mapboxMap.addMarker(new MarkerOptions().setPosition(point));
                                return true;
                            }
                        });
                        mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(@NonNull Marker marker) {
                                mapboxMap.removeMarker(marker);
                                return false;
                            }
                        });
                        // Map is set up and the style has loaded. Now you can add data or make other map adjustments
                    }
                });

            }
        });*/
        return root;
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();

    }


}
