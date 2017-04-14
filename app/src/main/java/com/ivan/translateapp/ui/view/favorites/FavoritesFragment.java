package com.ivan.translateapp.ui.view.favorites;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ivan.translateapp.R;
import com.ivan.translateapp.TranslateApplication;
import com.ivan.translateapp.dagger.MainModule;
import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.ui.adapter.TranslationAdapter;
import com.ivan.translateapp.ui.presenter.IFavoritesPresenterPresenter;
import com.ivan.translateapp.ui.view.MainActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FavoritesFragment extends Fragment implements IFavoritesView {

    @Inject
    IFavoritesPresenterPresenter iFavoritesPresenter;

    @BindView(R.id.favoritesRecyclerView)
    RecyclerView favoritesRecyclerView;

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

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        favoritesRecyclerView.setLayoutManager(layoutManager);

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
        TranslationAdapter adapter = new TranslationAdapter(
                translations, iFavoritesPresenter, R.layout.history_list_item);
        favoritesRecyclerView.setAdapter(adapter);
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
    public void loadData() {
            iFavoritesPresenter.loadFavorites();
    }
}
