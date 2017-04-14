package com.ivan.translateapp.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.ivan.translateapp.R;
import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.ui.view.favorites.FavoritesFragment;
import com.ivan.translateapp.ui.view.history.HistoryFragment;
import com.ivan.translateapp.ui.view.main.MainFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final int PAGE_COUNT = 3;
    private static final int[] tabIcons = {
            R.drawable.selector_ic_language,
            R.drawable.selector_ic_history,
            R.drawable.selector_ic_star
    };

    private TabsPagerAdapter tabsPagerAdapter;

    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        tabsPagerAdapter = new TabsPagerAdapter(fragmentManager);

        viewPager.setAdapter(tabsPagerAdapter);
        viewPager.setOffscreenPageLimit(PAGE_COUNT);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                hideSoftInput();
                IView view = (IView) tabsPagerAdapter.getFragment(position);
                if (view != null) {
                    view.loadData();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupTabIcons() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
        }
    }

    private void hideSoftInput(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    public void openMainFragment(Translation translation){
        final int mainFragmentPosition = 0;

        MainFragment fragment = (MainFragment) tabsPagerAdapter.getFragment(mainFragmentPosition);
        if(fragment==null)
            return;

        fragment.setTranslation(translation);
        viewPager.setCurrentItem(mainFragmentPosition);
    }

    private static class TabsPagerAdapter extends FragmentPagerAdapter {
        private Map<Integer, String> fragmentTags;
        private FragmentManager fragmentManager;


        TabsPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            this.fragmentManager = fragmentManager;
            this.fragmentTags = new HashMap<>(PAGE_COUNT);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object obj = super.instantiateItem(container, position);
            if (obj instanceof Fragment) {
                Fragment f = (Fragment) obj;
                String tag = f.getTag();
                fragmentTags.put(position, tag);
            }
            return obj;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            switch (position) {
                case 0:
                    fragment = new MainFragment();
                    break;
                case 1:
                    fragment = new HistoryFragment();
                    break;
                case 2:
                    fragment = new FavoritesFragment();
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        public Fragment getFragment(int position) {
            String tag = fragmentTags.get(position);
            if (tag == null)
                return null;

            return fragmentManager.findFragmentByTag(tag);
        }
    }
}
