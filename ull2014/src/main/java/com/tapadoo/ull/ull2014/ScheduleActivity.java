package com.tapadoo.ull.ull2014;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tapadoo.ull.ull2014.models.Schedule;
import com.tapadoo.ull.ull2014.models.ScheduleDay;
import com.tapadoo.ull.ull2014.models.ScheduleItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ScheduleActivity extends Activity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this)
            );
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private int sectionNumber;
        private String title;
        private List<ScheduleItem> scheduleItems = new ArrayList<ScheduleItem>();

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER, 0);


            switch (sectionNumber) {
                case 0:
                    title = getString(R.string.title_section1);
                    break;
                case 1:
                    title = getString(R.string.title_section2);
                    break;
                case 2:
                    title = getString(R.string.title_section3);
                    break;
            }

            String jsonString = "";
            try {
                InputStream is = getActivity().getAssets().open("schedule.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                jsonString = new String(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Gson gson = new Gson();
            Schedule schedule = gson.fromJson(jsonString, Schedule.class);
            for (ScheduleDay scheduleDay : schedule.days) {
                if (scheduleDay.name.toLowerCase().equals(title.toLowerCase())) {
                    if (scheduleDay.schedule != null)
                        scheduleItems = scheduleDay.schedule;
                }
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            LinearLayout rootView = (LinearLayout) inflater.inflate(R.layout.fragment_schedule, container, false);
            for (ScheduleItem scheduleItem : scheduleItems) {
                View scheduleItemView = inflater.inflate(R.layout.schedule_item, rootView, false);
                switch (sectionNumber) {
                    case 0: {
                        scheduleItemView.setBackgroundDrawable(getResources().getDrawable(R.drawable.red_background));
                        break;
                    }
                    case 1: {
                        scheduleItemView.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_background));
                        break;
                    }
                    case 2: {
                        scheduleItemView.setBackgroundDrawable(getResources().getDrawable(R.drawable.yellow_background));
                        break;
                    }
                }

                scheduleItemView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, scheduleItem.endTime - scheduleItem.startTime));
                if (scheduleItem.title.toLowerCase().equals("chillout")) {
                    scheduleItemView.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_background));
                }
                ((TextView) scheduleItemView.findViewById(android.R.id.title)).setText(scheduleItem.title);

                ((TextView) scheduleItemView.findViewById(R.id.timeRange)).setText(String.format("%02d", scheduleItem.startTime.intValue()) + ":" + String.format("%02d", (int) ((scheduleItem.startTime - scheduleItem.startTime.intValue()) * 60)) + " - " + String.format("%02d", scheduleItem.endTime.intValue()) + ":" + String.format("%02d", (int) ((scheduleItem.endTime - scheduleItem.endTime.intValue()) * 60)));
                rootView.addView(scheduleItemView);
            }
            return rootView;
        }


    }
}
