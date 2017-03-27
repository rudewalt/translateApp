package com.ivan.translateapp.main.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivan.translateapp.R;
import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.main.presenter.IMainPresenter;
import com.ivan.translateapp.main.view.IMainView;

import java.util.List;

import javax.inject.Inject;


public class MainFragment extends Fragment implements IMainView {

    @Inject
    IMainPresenter iMainPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onDestroyView() {
        iMainPresenter.unbindVIew();
        super.onDestroyView();
    }

    @Override
    public void loadLanguages(List<Language> languages) {

    }

    @Override
    public void setTranslatedText() {

    }

    @Override
    public void setFromLanguage() {

    }

    @Override
    public void setToLanguage() {

    }

    @Override
    public void setText() {

    }

    @Override
    public void saveToFavourites() {

    }

    @Override
    public void showError(String text) {

    }

    @Override
    public void showHistoryScreen() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        //TODO historyScreen
        //transaction.replace(R.id.fmt_container, new TransferFragment());
        transaction.commit();
    }
}
