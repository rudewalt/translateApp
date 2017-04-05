package com.ivan.translateapp.ui.history.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ivan.translateapp.R;
import com.ivan.translateapp.TranslateApplication;
import com.ivan.translateapp.dagger.MainModule;
import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.ui.history.adapter.HistoryAdapter;
import com.ivan.translateapp.ui.history.presenter.IHistoryPresenter;

import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements IHistoryView {

    @Inject
    IHistoryPresenter iHistoryPresenter;

    private ListView historyListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TranslateApplication.get(getContext())
                .getApplicationComponent()
                .plus(new MainModule())
                .inject(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);

        historyListView = (ListView) view.findViewById(R.id.historyListView);

        iHistoryPresenter.bindView(this);
        loadChanges();
        return view;
    }

    @Override
    public void onDestroyView() {
        iHistoryPresenter.unbindView();
        super.onDestroyView();
    }


    @Override
    public void showHistory(List<Translation> translations) {
        HistoryAdapter historyAdapter = new HistoryAdapter(getActivity(),
                R.layout.history_list_item, translations, iHistoryPresenter);
        historyListView.setAdapter(historyAdapter);
    }

    @Override
    public void showFavourites(List<Translation> translations) {
        HistoryAdapter historyAdapter = new HistoryAdapter(getActivity(),
                R.layout.history_list_item, translations, iHistoryPresenter);
    }

    @Override
    public void clearButtonClicked() {
    }

    @Override
    public void deleteButtonClicked() {
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void loadChanges() {
        if (iHistoryPresenter != null)
            iHistoryPresenter.loadHistory();
    }
}
