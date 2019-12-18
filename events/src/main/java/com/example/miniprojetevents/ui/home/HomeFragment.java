package com.example.miniprojetevents.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.miniprojetevents.R;
import com.example.miniprojetevents.ui.fragmentsEvent.HomeEvents;
import com.example.miniprojetevents.ui.gallery.GalleryFragment;
import com.example.miniprojetevents.ui.send.SendFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private String TAG = "EventD";
    private MaterialSearchBar searchBar;
    private Integer itemId;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //runner.execute(evv);
        ViewPager pager = root.findViewById(R.id.view_pager);
        setupViewPager(pager);
        BottomNavigationView bottomNavigationView = root.findViewById(R.id.bottom_navigation);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.action_recents);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.action_map);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.action_favorites);
                        break;
                    case 3:
                        bottomNavigationView.setSelectedItemId(R.id.action_nearby);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_recents:
                        pager.setCurrentItem(0);

                        Toast.makeText(root.getContext(), "Recents", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_map:
                        pager.setCurrentItem(1);
                        Toast.makeText(root.getContext(), "Nearby", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_favorites:
                        pager.setCurrentItem(2);

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
        return root;
    }

    private void setupViewPager(ViewPager pager) {
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragment(new HomeEvents());
        pagerAdapter.addFragment(new SendFragment());
        pagerAdapter.addFragment(new GalleryFragment());
        pager.setAdapter(pagerAdapter);
    }


    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();


        public ViewPagerAdapter(FragmentManager childFragmentManager) {
            super(childFragmentManager);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);

        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

    }

}

