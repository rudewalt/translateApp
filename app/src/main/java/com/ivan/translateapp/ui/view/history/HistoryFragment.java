package com.ivan.translateapp.ui.view.history;


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
import com.ivan.translateapp.ui.adapter.TranslationAdapter;
import com.ivan.translateapp.ui.presenter.IHistoryPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements IHistoryView {

    @Inject
    IHistoryPresenter iHistoryPresenter;

    @BindView(R.id.historyListView) ListView historyListView;

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
        ButterKnife.bind(this, view);

        iHistoryPresenter.bindView(this);
        loadData();
        return view;
    }

    @Override
    public void onDestroyView() {
        iHistoryPresenter.unbindView();
        super.onDestroyView();
    }


    @Override
    public void showTranslations(List<Translation> translations) {
        TranslationAdapter historyAdapter = new TranslationAdapter(getActivity(),
                R.layout.history_list_item, translations, iHistoryPresenter);
        historyListView.setAdapter(historyAdapter);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void loadData() {
        if (iHistoryPresenter != null)
            iHistoryPresenter.loadHistory();
    }
}
