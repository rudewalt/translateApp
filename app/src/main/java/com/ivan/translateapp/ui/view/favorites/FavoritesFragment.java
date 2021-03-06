package com.ivan.translateapp.ui.view.favorites;


import android.os.Bundle;
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
import com.ivan.translateapp.ui.presenter.ITranslationListViewPresenter;
import com.ivan.translateapp.ui.view.BaseFragment;
import com.ivan.translateapp.ui.view.ITranslationListView;
import com.ivan.translateapp.ui.view.MainActivity;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class FavoritesFragment extends BaseFragment implements ITranslationListView {

    @Inject
    @Named("favorites")
    ITranslationListViewPresenter iFavoritesPresenter;

    @BindView(R.id.favoritesRecyclerView)
    RecyclerView favoritesRecyclerView;

    @BindView(R.id.empty_view)
    View emptyView;

    private Unbinder unbinder;

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
        unbinder = ButterKnife.bind(this, view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        favoritesRecyclerView.setLayoutManager(layoutManager);

        iFavoritesPresenter.bindView(this);
        onShowView();
        return view;
    }

    @Override
    public void onDestroyView() {
        iFavoritesPresenter.unbindView();
        if (unbinder != null)
            unbinder.unbind();

        super.onDestroyView();
    }

    @Override
    public void showTranslations(List<Translation> translations) {
        TranslationAdapter adapter = new TranslationAdapter(
                translations, iFavoritesPresenter, R.layout.translation_list_item);
        favoritesRecyclerView.setAdapter(adapter);

        getActivity().runOnUiThread(() ->
                emptyView.setVisibility(translations.size() == 0 ? View.VISIBLE : View.INVISIBLE));
    }

    @Override
    public void openMainView(Translation translation) {
        MainActivity activity = (MainActivity) getActivity();
        if (activity == null)
            return;

        activity.openMainFragment(translation);
    }

    @Override
    public void onShowView() {
        iFavoritesPresenter.loadTranslations();
    }

    @Override
    public void onHideView() {
        //no implementation
    }
}
