package com.example.miniprojetevents.ui.event;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniprojetevents.R;
import com.example.miniprojetevents.database.EventDatabase;
import com.example.miniprojetevents.database.dao.EventDao;
import com.example.miniprojetevents.entities.Event;
import com.ramotion.foldingcell.FoldingCell;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder>  {


    class EventViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;
        private final TextView capacite;
        private final TextView lieu;
        private final TextView capaciteContent;
        private final TextView lieuContent;
        final EventListAdapter mAdapter;
        private final TextView addFavorite;
        private final TextView content_name;

        private EventViewHolder(View itemView, EventListAdapter adapter) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.title_price);
            this.mAdapter=adapter;
            this.capacite=itemView.findViewById(R.id.title_requests_count);
            this.lieu=itemView.findViewById(R.id.title_weight);
            this.capaciteContent=itemView.findViewById(R.id.head_image_left_text);
            this.lieuContent=itemView.findViewById(R.id.head_image_right_text);
            this.addFavorite=itemView.findViewById(R.id.content_add_favorite);
            content_name = itemView.findViewById(R.id.content_name_view);
        }
    }


    private final LayoutInflater mInflater;
    private List<Event> mEvents; // Cached copy of words

    public EventListAdapter(Context context, List<Event> getDatabaseEvents) {
        mInflater = LayoutInflater.from(context);
        mEvents=getDatabaseEvents;

    }

    @NonNull
    @Override
    public EventListAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.cell, parent, false);



        return new EventViewHolder(itemView,this);
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

                        String ta=t.getText().toString();
                        Toast.makeText(view.getContext(), position + " "+ta, Toast.LENGTH_SHORT).show();


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
                                    Toast.makeText(view.getContext(), "Event deleted to favorites", Toast.LENGTH_SHORT).show();
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

        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Word");
            holder.capacite.setText("no data");
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
