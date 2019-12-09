package com.example.miniprojetevents.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniprojetevents.R;
import com.example.miniprojetevents.database.dao.IEvent;
import com.example.miniprojetevents.entities.Event;
import com.example.miniprojetevents.ui.event.EventListAdapter;
import com.example.miniprojetevents.viewModel.EventViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private String TAG = "EventD";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Executor myExecutor = Executors.newSingleThreadExecutor();


        //runner.execute(evv);

        BottomNavigationView bottomNavigationView = root.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_recents:
                        Toast.makeText(root.getContext(), "Recents", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_favorites:
                        Toast.makeText(root.getContext(), "Favorites", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_nearby:
                        Toast.makeText(root.getContext(), "Nearby", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });


        final TextView textView = root.findViewById(R.id.text_home);
        RecyclerView listEvents = root.findViewById(R.id.list_Events);

        Spinner spinnerCategories = root.findViewById(R.id.spinnerCategories);
        String[] arraySpinner = new String[]{
                "Titre", "Categorie", "type", "lieu Event"
        };
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, arraySpinner);

        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategories.setAdapter(adapterSpinner);


        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
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


                sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {


                        adapter.getFilterWithCategorie(spinnerCategories.getSelectedItem().toString()).filter(s);
                        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                Object item = parent.getItemAtPosition(pos);

                                Log.d(TAG, "onItemSelected: " + item.toString());
                                sv.setQueryHint("Chercher par " + item.toString());
                                adapter.getFilterWithCategorie(item.toString()).filter(s);

                            }

                            public void onNothingSelected(AdapterView<?> parent) {

                                Object item = parent.getItemAtPosition(parent.getFirstVisiblePosition());
                                Log.d(TAG, "onItemSelected: " + item.toString());

                                adapter.getFilterWithCategorie(item.toString()).filter(s);
                            }
                        });

                        //Spinner Ends


                        return false;
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