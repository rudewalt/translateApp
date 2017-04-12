package com.ivan.translateapp.ui.view.favorites;


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
import com.ivan.translateapp.ui.presenter.IFavoritesPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FavoritesFragment extends Fragment implements IFavoritesView {

    @Inject
    IFavoritesPresenter iFavoritesPresenter;

    @BindView(R.id.favoritesListView)
    ListView favoritesListView;

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
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.bind(this, view);

        iFavoritesPresenter.bindView(this);
        loadData();
        return view;
    }

    @Override
    public void onDestroyView() {
        iFavoritesPresenter.unbindView();
        super.onDestroyView();
    }

    @Override
    public void showFavorites(List<Translation> translations) {
        TranslationAdapter translationAdapter =
                new TranslationAdapter(getActivity(),R.layout.history_list_item, translations, iFavoritesPresenter);
        favoritesListView.setAdapter(translationAdapter);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void loadData() {
        if (iFavoritesPresenter != null)
            iFavoritesPresenter.loadFavorites();
    }
}
