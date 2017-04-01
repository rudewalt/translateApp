package com.ivan.translateapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentContainer;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.ivan.translateapp.R;
import com.ivan.translateapp.ui.history.view.HistoryFragment;
import com.ivan.translateapp.ui.main.view.MainFragment;

public class MainActivity extends AppCompatActivity {

    private Fragment currentFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if(currentFragment.getClass()== MainFragment.class)
                        return false;

                    FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                    currentFragment = new MainFragment();
                    transaction1.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                    transaction1.replace(R.id.fragment_container, currentFragment);
                    transaction1.commit();
                    return true;
                case R.id.navigation_history:
                    if(currentFragment.getClass()== HistoryFragment.class)
                        return false;

                    FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                    transaction2.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                    currentFragment = new HistoryFragment();
                    transaction2.replace(R.id.fragment_container, currentFragment);
                    transaction2.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            currentFragment = new MainFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, currentFragment);
            transaction.commit();
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
