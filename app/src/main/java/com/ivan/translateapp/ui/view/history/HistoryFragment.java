package com.ivan.translateapp.ui.view.history;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivan.translateapp.R;
import com.ivan.translateapp.TranslateApplication;
import com.ivan.translateapp.dagger.MainModule;
import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.ui.adapter.TranslationAdapter;
import com.ivan.translateapp.ui.presenter.IHistoryPresenterPresenter;
import com.ivan.translateapp.ui.view.MainActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HistoryFragment extends Fragment implements IHistoryView {

    @Inject
    IHistoryPresenterPresenter iHistoryPresenter;

    @BindView(R.id.historyRecyclerView)
    RecyclerView historyRecyclerView;

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

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        historyRecyclerView.setLayoutManager(layoutManager);

        iHistoryPresenter.bindView(this);
        onShowView();
        return view;
    }

    @Override
    public void onDestroyView() {
        iHistoryPresenter.unbindView();
        super.onDestroyView();
    }


    @Override
    public void showTranslations(List<Translation> translations) {
        TranslationAdapter adapter = new TranslationAdapter(
                translations, iHistoryPresenter, R.layout.translation_list_item);
        historyRecyclerView.setAdapter(adapter);
    }

    @Override
    public void openMainView(Translation translation) {
        MainActivity activity = (MainActivity) getActivity();
        if(activity == null)
            return;

        activity.openMainFragment(translation);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void onShowView() {
            iHistoryPresenter.loadHistory();
    }

    @Override
    public void onHideView() {
        //no implementation
    }
}
