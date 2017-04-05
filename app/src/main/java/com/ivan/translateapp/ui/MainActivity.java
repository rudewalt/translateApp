package com.ivan.translateapp.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.ivan.translateapp.R;
import com.ivan.translateapp.ui.history.view.HistoryFragment;
import com.ivan.translateapp.ui.main.view.MainFragment;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Fragment currentFragment;
    private TabsPagerAdapter adapterViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        TabsPagerAdapter adapter = new TabsPagerAdapter(fragmentManager);
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                IBaseView view = ((IBaseView)adapter.getFragment(position));
                if(view!=null) {
                    view.loadChanges();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public static class TabsPagerAdapter extends FragmentPagerAdapter{
        private Map<Integer, String> fragmentTags;
        private FragmentManager fragmentManager;
        private static final int PAGE_COUNT = 2;

        public TabsPagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
            this.fragmentManager = fragmentManager;
            this.fragmentTags = new HashMap<>(PAGE_COUNT);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object obj = super.instantiateItem(container, position);
            if (obj instanceof Fragment) {
                // record the fragment tag here.
                Fragment f = (Fragment) obj;
                String tag = f.getTag();
                fragmentTags.put(position, tag);
            }
            return obj;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            switch (position){
                case 0:
                    fragment = new MainFragment();
                    break;
                case 1:
                    fragment = new HistoryFragment();
                    break;
            }

            if(fragment!=null) {
                fragmentTags.put(position, fragment.getTag());
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        //TODO переделать
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

        public Fragment getFragment(int position) {
            String tag = fragmentTags.get(position);
            if (tag == null)
                return null;

            return fragmentManager.findFragmentByTag(tag);
        }
    }


}
