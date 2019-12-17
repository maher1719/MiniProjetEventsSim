package com.example.miniprojetevents.ui.fragmentsEvent;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniprojetevents.R;
import com.example.miniprojetevents.database.dao.IEvent;
import com.example.miniprojetevents.entities.Event;
import com.example.miniprojetevents.ui.event.EventListAdapter;
import com.example.miniprojetevents.viewModel.EventViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeEvents extends Fragment {


    private MaterialSearchBar searchBar;
    private String TAG = "EventD";

    public HomeEvents() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home_events, container, false);
        RecyclerView listEvents = root.findViewById(R.id.list_Events);
        EventViewModel mWordViewModel = new ViewModelProvider(this).get(EventViewModel.class);
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
                List<Event> ev = response.body();
                EventListAdapter adapter = new EventListAdapter(getContext(), ev);
                SearchView sv = root.findViewById(R.id.mSearch);
                listEvents.setLayoutManager(new LinearLayoutManager(root.getContext()));
                listEvents.setAdapter(adapter);
                searchBar = root.findViewById(R.id.searchBar);
                searchBar.inflateMenu(R.menu.search_menu_items);
                searchBar.addTextChangeListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        Log.d(TAG, "onTextChanged: " + "i " + i + " i2 " + i1 + " i3 " + i2);
                        adapter.getFilterWithCategorie("Titre").filter(charSequence.toString());
                        searchBar.getMenu().setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                //itemId=item.getOrder();
                                searchBar.setText("");
                                Log.d(TAG, "onMenuItemClick: " + item.toString() + " search" + charSequence.toString());
                                adapter.getFilterWithCategorie(item.toString()).filter(charSequence.toString());
                                return false;

                            }
                        });

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        //editable.toString();
                        Log.d(TAG, "afterTextChanged: " + editable.toString());

                    }
                });


            }


            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.d("failure", "onFailure3: " + t.getMessage());
            }
        });
        return root;
    }

}
