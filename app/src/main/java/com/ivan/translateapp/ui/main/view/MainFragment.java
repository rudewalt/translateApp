package com.ivan.translateapp.ui.main.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.ivan.translateapp.R;
import com.ivan.translateapp.TranslateApplication;
import com.ivan.translateapp.dagger.MainModule;
import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.ui.main.presenter.IMainPresenter;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;


public class MainFragment extends Fragment implements IMainView {

    private final String TAG = MainFragment.class.toString();

    private EditText textToTranslate;
    private TextView translatedText;
    private Spinner fromLanguageSpinner;
    private Spinner toLanguageSpinner;
    private CheckBox isFavouriteCheckbox;
    private ImageButton clearButton;
    private ImageButton changeDirection;

    private Timer afterTextChangedTimer = new Timer();
    private final long DELAY = 500; //milliseconds

    private CompoundButton.OnCheckedChangeListener isFavouriteCheckedListener;

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
        translatedText = (TextView) view.findViewById(R.id.translatedText);
        fromLanguageSpinner = (Spinner) view.findViewById(R.id.fromLanguage);
        toLanguageSpinner = (Spinner) view.findViewById(R.id.toLanguage);
        isFavouriteCheckbox = (CheckBox) view.findViewById(R.id.isFavourite);
        clearButton = (ImageButton) view.findViewById(R.id.clearButton);
        changeDirection = (ImageButton) view.findViewById(R.id.changeDirection);

        isFavouriteCheckedListener = (buttonView, isChecked) -> {
            iMainPresenter.listenIsFavourite(isChecked);
        };

        isFavouriteCheckbox.setOnCheckedChangeListener(isFavouriteCheckedListener);

        textToTranslate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                afterTextChangedTimer.cancel();
                afterTextChangedTimer = new Timer();
                afterTextChangedTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        callTranslate();
                    }
                }, DELAY);

                if (s.toString().length() > 0) {
                    showClearButton();
                    showIsFavouriteCheckbox();
                } else {
                    hideClearButton();
                    hideIsFavouriteCheckbox();
                }
            }
        });

        textToTranslate.setOnFocusChangeListener((v, hasFocus)->{
            if(!hasFocus){
                iMainPresenter.saveTranslation();
            }
        });

        changeDirection.setOnClickListener(this::handleChangeDirectionClick);
        clearButton.setOnClickListener(this::handleClearButtonClick);

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
        ArrayAdapter<Language> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromLanguageSpinner.setAdapter(adapter);
        toLanguageSpinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener listener = getChangeLanguageListener();
        toLanguageSpinner.setOnItemSelectedListener(listener);
        fromLanguageSpinner.setOnItemSelectedListener(listener);
    }

    @Override
    public void setTranslatedText(String text) {
        translatedText.setText(text);
    }

    @Override
    public void setFromLanguage() {

    }

    private String getSelectedLanguage(Spinner spinner) {
        return ((Language) spinner.getSelectedItem()).getLanguage();
    }

    @Override
    public void setToLanguage() {

    }

    @Override
    public void setText(String text) {
        textToTranslate.setText(text);
    }

    @Override
    public void saveToFavourites() {

    }

    @Override
    public void showError(String text) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle(text);
        alert.setPositiveButton("Ok", (dialog, whichButton) -> {
            return;
        });
        alert.show();
    }

    @Override
    public void showClearButton() {
        getActivity().runOnUiThread(() -> {
            clearButton.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void hideClearButton() {
        getActivity().runOnUiThread(() -> {
            clearButton.setVisibility(View.INVISIBLE);
        });
    }

    @Override
    public void showIsFavouriteCheckbox() {
        isFavouriteCheckbox.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideIsFavouriteCheckbox() {
        isFavouriteCheckbox.setVisibility(View.INVISIBLE);
        setStateIsFavouriteCheckbox(false);
    }

    @Override
    public void setStateIsFavouriteCheckbox(boolean checked) {
        isFavouriteCheckbox.setOnCheckedChangeListener(null);
        isFavouriteCheckbox.setChecked(checked);
        isFavouriteCheckbox.setOnCheckedChangeListener(isFavouriteCheckedListener);
    }

    private void handleChangeDirectionClick(View view) {
        int idFromLanguage = fromLanguageSpinner.getSelectedItemPosition();
        int idToLanguage = toLanguageSpinner.getSelectedItemPosition();

        fromLanguageSpinner.setSelection(idToLanguage, true);
        toLanguageSpinner.setSelection(idFromLanguage, true);
        callTranslate();
    }

    private void handleClearButtonClick(View view) {
        textToTranslate.setText("");
        translatedText.setText("");
    }

    private void callTranslate() {
        String text = textToTranslate.getText().toString();
        if(text.equals(""))
            return;

        String fromLanguage = getSelectedLanguage(fromLanguageSpinner);
        String toLanguage = getSelectedLanguage(toLanguageSpinner);
        iMainPresenter.listenText(text, fromLanguage, toLanguage);
    }

    private AdapterView.OnItemSelectedListener getChangeLanguageListener() {
        return
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        callTranslate();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                };
    }

    @Override
    public void loadChanges() {

    }
}
