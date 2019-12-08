package com.example.miniprojetevents.ui.event;

import android.widget.Filter;

import com.example.miniprojetevents.entities.Event;

import java.util.ArrayList;
import java.util.List;

public class CustomeFilter extends Filter {

    EventListAdapter adapter;
    List<Event> filterList;

    public CustomeFilter(List<Event> list, EventListAdapter adapter) {
        this.adapter = adapter;
        this.filterList = list;
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
                //CHECK
                if (filterList.get(i).getTitle().toUpperCase().contains(constraint)) {
                    //ADD PLAYER TO FILTERED PLAYERS

                    events.add(filterList.get(i));
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
