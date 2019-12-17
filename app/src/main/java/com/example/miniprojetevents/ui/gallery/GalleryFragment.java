package com.example.miniprojetevents.ui.gallery;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniprojetevents.R;
import com.example.miniprojetevents.database.EventDatabase;
import com.example.miniprojetevents.database.dao.EventDao;
import com.example.miniprojetevents.entities.Event;
import com.example.miniprojetevents.ui.event.EventListAdapter;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private String TAG = "EventG";
    List<Event> ev;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        RecyclerView listEvents = root.findViewById(R.id.list_Events_gallery);
        final EventDao events = EventDatabase.getDatabase(root.getContext()).eventDao();
        class insertData extends AsyncTask<Void, Void, List<Event>> {


            @Override
            protected List<Event> doInBackground(Void... voids) {
                ev = events.getAllEvents();
                Log.d(TAG, "doInBackground: " + ev.toString());
                return ev;
            }

        }
        insertData runner = new insertData();
        try {
            List<Event> eventsLocal = runner.execute().get();
            EventListAdapter adapter = new EventListAdapter(getContext(), eventsLocal);
            listEvents.setLayoutManager(new LinearLayoutManager(root.getContext()));
            listEvents.setAdapter(adapter);


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return root;
    }
}