package com.ivan.translateapp.ui.main.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.ivan.translateapp.R;
import com.ivan.translateapp.TranslateApplication;
import com.ivan.translateapp.dagger.MainModule;
import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.ui.main.presenter.IMainPresenter;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.List;

import javax.inject.Inject;


public class MainFragment extends Fragment implements IMainView {

    private EditText textToTranslate;
    private TextView translatedText;
    private Spinner fromLanguage;
    private Spinner toLanguage;
    private ImageButton addToFavourites;

    @Inject
    IMainPresenter iMainPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        TranslateApplication.get(getContext())
                .getApplicationComponent()
                .plus(new MainModule())
                .inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        textToTranslate = (EditText) view.findViewById(R.id.textToTranslate);
        translatedText = (TextView)view.findViewById(R.id.translatedText);
        fromLanguage = (Spinner)view.findViewById(R.id.fromLanguage);
        toLanguage = (Spinner)view.findViewById(R.id.toLanguage);
        addToFavourites = (ImageButton)view.findViewById(R.id.addToFavourites);



        textToTranslate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                iMainPresenter.listenText(s.toString(),"ru","en");
            }
        });

        iMainPresenter.bindView(this);
        iMainPresenter.loadLanguages();

        return view;
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
    public void setTranslatedText(String text) {
        translatedText.setText(text);
    }

    @Override
    public void setFromLanguage() {

    }

    @Override
    public void setToLanguage() {

    }

    @Override
    public void setText(String text) {

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
