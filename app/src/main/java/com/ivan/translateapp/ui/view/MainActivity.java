package com.ivan.translateapp.ui.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

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

    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private TabsPagerAdapter tabsPagerAdapter;
    private IView currentView;

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

        viewPager.addOnPageChangeListener(createPageChangeListener());
    }

    @Override
    protected void onStart(){
        super.onStart();
        currentView = (IView) tabsPagerAdapter.getFragment(viewPager.getCurrentItem());
    }

    public void openMainFragment(Translation translation) {
        final int mainFragmentPosition = 0;

        MainFragment fragment = (MainFragment) tabsPagerAdapter.getFragment(mainFragmentPosition);
        if (fragment == null)
            return;

        fragment.setTranslation(translation);
        viewPager.setCurrentItem(mainFragmentPosition);
    }

    private ViewPager.OnPageChangeListener createPageChangeListener(){
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //no implementation
            }

            @Override
            public void onPageSelected(int position) {
                if(currentView !=null)
                    currentView.onHideView();

                IView view = (IView) tabsPagerAdapter.getFragment(position);
                if (view != null) {
                    view.onShowView();
                }

                currentView = view;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //no implementation
            }
        };
    }

    private void setupTabIcons() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
        }
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
            switch (position) {
                case 0:
                    return new MainFragment();
                case 1:
                    return new HistoryFragment();
                case 2:
                    return new FavoritesFragment();
            }

            return null;
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
