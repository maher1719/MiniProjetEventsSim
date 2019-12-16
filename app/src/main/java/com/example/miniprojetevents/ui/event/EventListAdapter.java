package com.example.miniprojetevents.ui.event;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniprojetevents.AddEvent;
import com.example.miniprojetevents.R;
import com.example.miniprojetevents.database.EventDatabase;
import com.example.miniprojetevents.database.dao.EventDao;
import com.example.miniprojetevents.entities.Event;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder> implements Filterable {


    private final LayoutInflater mInflater;
    public List<Event> mEvents; // Cached copy of words

    CustomeFilter filter;

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomeFilter(mEvents, this);
        }

        return filter;
    }

    public Filter getFilterWithCategorie(String categorie) {
        if (filter == null) {
            filter = new CustomeFilter(mEvents, this, categorie);
        }

        return filter;
    }

    public EventListAdapter(Context context, List<Event> getDatabaseEvents) {
        mInflater = LayoutInflater.from(context);
        mEvents = getDatabaseEvents;

    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FoldingCell) view).toggle(false);
                Toast.makeText(view.getContext(), position + "", Toast.LENGTH_SHORT).show();
                TextView moreinfo=view.findViewById(R.id.content_request_btn);
                TextView t=view.findViewById(R.id.title_price);
                TextView intersted=view.findViewById(R.id.content_add_favorite);

                Event current = mEvents.get(position);
                moreinfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent add = new Intent(view.getContext(), AddEvent.class);
                        add.putExtra("event", current);
                        view.getContext().startActivity(add);






                    }
                });

                final EventDao events = EventDatabase.getDatabase(view.getContext()).eventDao();

                class insertData extends AsyncTask<Void,Void,Boolean> {


                    @Override
                    protected Boolean doInBackground(Void... voids) {
                        Integer e=null;
                        e=events.selectEventId(current.getId());


                        return e != null;


                    }
                }


                insertData runner = new insertData();
                try {
                    Boolean check=runner.execute().get();
                    if(check){
                        holder.addFavorite.setText("Delete");
                    }else{
                        holder.addFavorite.setText("Interstsed");
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                intersted.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final EventDao events = EventDatabase.getDatabase(view.getContext()).eventDao();
                            class insertData extends AsyncTask<Void,Void,Boolean> {


                                @Override
                                protected Boolean doInBackground(Void... voids) {
                                    Integer e=null;
                                    e=events.selectEventId(current.getId());


                                    if(e!=null){
                                        events.deleteEventFromFavorit(current.getId());

                                        Log.d("EventG", "doInBackground: "+e);


                                        return false;
                                    }else{
                                        events.insert(current);
                                        return true;

                                    }


                                }
                            }


                            insertData runner = new insertData();
                            try {
                                Boolean check=runner.execute().get();
                                if(check){
                                    Toast.makeText(view.getContext(), "Event added to favorites", Toast.LENGTH_SHORT).show();
                                    holder.addFavorite.setText("Delete");

                                }else{
                                    if (holder.addFavorite.getText().toString() == "Delete") {
                                        mEvents.remove(current);
                                        holder.mAdapter.notifyDataSetChanged();
                                    }

                                    Toast.makeText(view.getContext(), "Event deleted from favorites", Toast.LENGTH_SHORT).show();
                                    holder.addFavorite.setText("Interstsed");

                                }


                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }


                });



            }
        });

        if (mEvents != null) {
            Event current = mEvents.get(position);
            holder.wordItemView.setText(current.getMontant());
            holder.capacite.setText(current.getCapacite());
            holder.lieu.setText(current.getLieuEvent());
            holder.capaciteContent.setText(current.getCapacite());
            holder.content_name.setText(current.getUserMail());
            holder.title.setText(current.getTitle());
            holder.category.setText(current.getCategorie());
            //holder.mapFragement.

            holder.mapView.getMapAsync(new OnMapReadyCallback() {

                @Override
                public void onMapReady(@NonNull MapboxMap mapboxMap) {
                    mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {
                            List<MarkerOptions> markers = new ArrayList<>();
                            LatLng positionEvent = new LatLng(current.getLatitude(), current.getLongtitude());
                            CameraPosition positionCamera = new CameraPosition.Builder()
                                    .target(positionEvent) // Sets the new camera position
                                    .zoom(10) // Sets the zoom
                                    // Rotate the camera
                                    .tilt(30) // Set the camera tilt
                                    .build();
                            mapboxMap.setCameraPosition(positionCamera);
                            MarkerOptions m = new MarkerOptions().setTitle(current.getTitle())
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



        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Word");
            holder.capacite.setText("no data");
        }

    }

    @NonNull
    @Override
    public EventListAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.cell, parent, false);


        return new EventViewHolder(itemView, this);
    }

    class EventViewHolder extends RecyclerView.ViewHolder {
        final EventListAdapter mAdapter;
        private final TextView wordItemView;
        private final TextView capacite;
        private final TextView lieu;
        private final TextView capaciteContent;
        private final TextView lieuContent;
        private final TextView addFavorite;
        private final TextView content_name;
        private final MapView mapView;
        private final TextView title;
        private final TextView category;

        private EventViewHolder(View itemView, EventListAdapter adapter) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.title_price);
            this.mAdapter = adapter;
            this.capacite = itemView.findViewById(R.id.title_requests_count);
            this.lieu = itemView.findViewById(R.id.title_weight);
            this.capaciteContent = itemView.findViewById(R.id.head_image_left_text);
            this.lieuContent = itemView.findViewById(R.id.head_image_right_text);
            this.addFavorite = itemView.findViewById(R.id.content_add_favorite);
            content_name = itemView.findViewById(R.id.content_name_view);
            this.title = itemView.findViewById(R.id.title_from_address);
            this.category = itemView.findViewById(R.id.title_to_address);

            mapView = itemView.findViewById(R.id.mapViewEvent);
        }
    }


    void setWords(List<Event> words) {
        mEvents = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mEvents != null)
            return mEvents.size();
        else return 0;
    }


}
