package com.ogreenwood.discord_music;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);

        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("Music");
        arrayList.add("Add URLs");
        arrayList.add("Set up");
        arrayList.add("About");

        prepareViewPager(viewPager, arrayList);

        tabLayout.setupWithViewPager(viewPager);

    }

    private void prepareViewPager(ViewPager viewPager, ArrayList<String> arrayList) {
        MainAdapter adapter = new MainAdapter(getSupportFragmentManager());

        sound_buttons sb = new sound_buttons();
        adapter.addFragment(sb, arrayList.get(0));

        EditButtons eb = new EditButtons();
        adapter.addFragment(eb, arrayList.get(1));

        Settings settings = new Settings();
        adapter.addFragment(settings, arrayList.get(2));

        about ab = new about();
        adapter.addFragment(ab, arrayList.get(3));


        viewPager.setAdapter(adapter);
    }


    private class MainAdapter extends FragmentPagerAdapter {

        ArrayList<String> arrayList = new ArrayList<>();
        List<Fragment> fragmentList = new ArrayList<>();

        public void addFragment(Fragment fragment, String title) {
            arrayList.add(title);
            fragmentList.add(fragment);

        }

        public MainAdapter(@NonNull @org.jetbrains.annotations.NotNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @org.jetbrains.annotations.NotNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @org.jetbrains.annotations.Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return arrayList.get(position);
        }
    }
}