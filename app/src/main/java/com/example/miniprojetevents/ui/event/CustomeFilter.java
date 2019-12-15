package com.example.miniprojetevents.ui.event;

import android.util.Log;
import android.widget.Filter;

import com.example.miniprojetevents.entities.Event;

import java.util.ArrayList;
import java.util.List;

public class CustomeFilter extends Filter {

    EventListAdapter adapter;
    List<Event> filterList;
    String categorieFilter;

    public CustomeFilter(List<Event> list, EventListAdapter adapter) {
        this.filterList = list;

    }


    public CustomeFilter(List<Event> list, EventListAdapter adapter, String categorie) {
        this.adapter = adapter;
        this.filterList = list;
        this.categorieFilter = categorie;
    }

    @Override
    public FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if (constraint != null && constraint.length() > 0) {
            //CHANGE TO UPPER
            constraint = constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            List<Event> events = new ArrayList<>();

            for (int i = 0; i < filterList.size(); i++) {
                //CHECK "Titre", "Categorie", "type", "lieu Event"
                Log.d("switchCat", "performFiltering: " + categorieFilter);
                switch (categorieFilter) {
                    case "Titre":
                        if (filterList.get(i).getTitle().toUpperCase().contains(constraint)) {
                            //ADD PLAYER TO FILTERED PLAYERS

                            events.add(filterList.get(i));
                        }

                    case "Categorie":
                        if (filterList.get(i).getCategorie().toUpperCase().contains(constraint)) {
                            //ADD PLAYER TO FILTERED PLAYERS

                            events.add(filterList.get(i));
                        }
                    case "Type":
                        if (filterList.get(i).getType().toUpperCase().contains(constraint)) {
                            //ADD PLAYER TO FILTERED PLAYERS

                            events.add(filterList.get(i));
                        }
                    case "Lieu":
                        if (filterList.get(i).getLieuEvent().toUpperCase().contains(constraint)) {
                            //ADD PLAYER TO FILTERED PLAYERS

                            events.add(filterList.get(i));
                        }
                    default:
                        if (filterList.get(i).getTitle().toUpperCase().contains(constraint)) {
                            //ADD PLAYER TO FILTERED PLAYERS

                            events.add(filterList.get(i));
                        }


                }

            }

            results.count = events.size();
            results.values = events;
        } else {
            results.count = filterList.size();
            results.values = filterList;

        }

        return results;

    }

    @Override
    public void publishResults(CharSequence charSequence, FilterResults filterResults) {

        adapter.mEvents = (List<Event>) filterResults.values;

        //REFRESH
        adapter.notifyDataSetChanged();

    }
}
