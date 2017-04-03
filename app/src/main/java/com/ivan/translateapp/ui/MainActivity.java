package com.ivan.translateapp.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ivan.translateapp.R;
import com.ivan.translateapp.ui.history.view.HistoryFragment;
import com.ivan.translateapp.ui.main.view.MainFragment;

public class MainActivity extends AppCompatActivity {

    private Fragment currentFragment;
    private TabsPagerAdapter adapterViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        TabsPagerAdapter adapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public static class TabsPagerAdapter extends FragmentPagerAdapter{
        private static final int PAGE_COUNT = 2;

        public TabsPagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new MainFragment();
                case 1:
                    return new HistoryFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "Главная";
                case 1:
                    return "История";
                default:
                    return "";
            }
        }

        /*public Fragment getFragment(int position) {
            String tag = mFragmentTags.get(position);
            if (tag == null)
                return null;
            return mFragmentManager.findFragmentByTag(tag);
        }*/
    }


}
