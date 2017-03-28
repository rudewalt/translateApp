package com.ivan.translateapp.ui.history.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivan.translateapp.R;
import com.ivan.translateapp.TranslateApplication;
import com.ivan.translateapp.dagger.MainModule;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements IHistoryView {


    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        TranslateApplication.get(getContext())
                .getApplicationComponent()
                .plus(new MainModule())
                .inject(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void showHistory() {

    }

    @Override
    public void showMainSceen() {

    }

    @Override
    public void clear() {

    }

    @Override
    public void delete() {

    }
}
