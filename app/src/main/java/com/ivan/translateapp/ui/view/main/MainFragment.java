package com.ivan.translateapp.ui.view.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.ui.adapter.LanguageAdapter;
import com.ivan.translateapp.ui.presenter.IMainPresenter;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainFragment extends Fragment implements IMainView {

    private final String TAG = MainFragment.class.toString();
    private final static String EMPTY_STRING = "";
    private final long DELAY = 500; //milliseconds

    @BindView(R.id.textToTranslate)
    EditText textToTranslate;
    @BindView(R.id.translatedText)
    TextView translatedText;
    @BindView(R.id.fromLanguage)
    Spinner fromLanguageSpinner;
    @BindView(R.id.toLanguage)
    Spinner toLanguageSpinner;
    @BindView(R.id.isFavorite)
    CheckBox isFavoriteCheckbox;
    @BindView(R.id.clearButton)
    ImageButton clearButton;
    @BindView(R.id.changeDirection)
    ImageButton changeDirection;
    @BindView(R.id.apiRequirementsTextView)
    TextView apiRequirementsTextView;

    private Timer afterTextChangedTimer = new Timer();
    private CompoundButton.OnCheckedChangeListener isFavouriteCheckedListener;
    private LanguageAdapter languageAdapter;

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
        ButterKnife.bind(this, view);
        setApiRequirementsText();

        isFavouriteCheckedListener = (buttonView, isChecked) -> {
            Translation translation = getTranslation();
            iMainPresenter.clickIsFavouriteCheckbox(translation);
        };

        isFavoriteCheckbox.setOnCheckedChangeListener(isFavouriteCheckedListener);

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
                    showIsFavoriteCheckbox();
                } else {
                    hideClearButton();
                    hideIsFavoriteCheckbox();
                }
            }
        });

        textToTranslate.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                iMainPresenter.textToTranslateLostFocus(getTranslation());
            }
        });

        changeDirection.setOnClickListener(this::handleChangeDirectionClick);
        clearButton.setOnClickListener(this::handleClearButtonClick);

        //TODO нужно что-то придумать с языками, в таком виде не годится
        //TODO сделать првоерку на наличие сети
        //TODO сделать обработку и вывод понятных ошибок пользователю
        //TODO написать тесты
        iMainPresenter.bindView(this);
        iMainPresenter.loadLanguages();

        return view;
    }

    @Override
    public void onDestroyView() {
        iMainPresenter.unbindView();
        super.onDestroyView();
    }


    @Override
    public void setLanguages(List<Language> languages) {
        languageAdapter = new LanguageAdapter(getActivity(), android.R.layout.simple_spinner_item, languages);
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromLanguageSpinner.setAdapter(languageAdapter);
        toLanguageSpinner.setAdapter(languageAdapter);

        AdapterView.OnItemSelectedListener listener = getChangeLanguageListener();
        toLanguageSpinner.setOnItemSelectedListener(listener);
        fromLanguageSpinner.setOnItemSelectedListener(listener);
    }

    @Override
    public void setTranslatedText(String text) {
        translatedText.setText(text);
    }

    @Override
    public void setFromLanguage(String fromLanguage) {
        selectLanguage(fromLanguage, fromLanguageSpinner);
    }

    @Override
    public void setToLanguage(String toLanguage) {
        selectLanguage(toLanguage, toLanguageSpinner);
    }


    @Override
    public void setText(String text) {
        textToTranslate.setText(text);
    }


    @Override
    public void showError(String text) {
        final String errorTitle = "Ошибка";
        final String okButtonTitle = "Ok";

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity())
        .setTitle(errorTitle)
        .setMessage(text)
        .setPositiveButton(okButtonTitle, (dialog, whichButton) -> {
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
    public void showIsFavoriteCheckbox() {
        isFavoriteCheckbox.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideIsFavoriteCheckbox() {
        isFavoriteCheckbox.setVisibility(View.INVISIBLE);
        setStateIsFavoriteCheckbox(false);
    }

    @Override
    public void setStateIsFavoriteCheckbox(boolean checked) {
        isFavoriteCheckbox.setOnCheckedChangeListener(null);
        isFavoriteCheckbox.setChecked(checked);
        isFavoriteCheckbox.setOnCheckedChangeListener(isFavouriteCheckedListener);
    }

    @Override
    public void loadData() {
        //no impl
    }

    private void selectLanguage(String language, Spinner languageSpinner){
        int position = languageAdapter.getPosition(language);
        if(position < 0)
            return;

        AdapterView.OnItemSelectedListener listener = languageSpinner.getOnItemSelectedListener();
        languageSpinner.setOnItemSelectedListener(null);
        languageSpinner.setSelection(position);
        languageSpinner.setOnItemSelectedListener(listener);
    }

    private String getSelectedLanguage(Spinner spinner) {
        return ((Language) spinner.getSelectedItem()).getLanguage();
    }

    private void handleChangeDirectionClick(View view) {
        int idFromLanguage = fromLanguageSpinner.getSelectedItemPosition();
        int idToLanguage = toLanguageSpinner.getSelectedItemPosition();

        fromLanguageSpinner.setSelection(idToLanguage, true);
        toLanguageSpinner.setSelection(idFromLanguage, true);
        callTranslate();
    }

    private void handleClearButtonClick(View view) {
        textToTranslate.setText(EMPTY_STRING);
        translatedText.setText(EMPTY_STRING);
    }

    private void callTranslate() {
        String text = textToTranslate.getText().toString();
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

    private Translation getTranslation() {
        return new Translation(textToTranslate.getText().toString(), translatedText.getText().toString(),
                getSelectedLanguage(fromLanguageSpinner), getSelectedLanguage(toLanguageSpinner), true, isFavoriteCheckbox.isChecked());
    }

    private void setApiRequirementsText(){
        final String text="<a href='http://translate.yandex.ru/'>Переведено сервисом «Яндекс.Переводчик»</a>";
        apiRequirementsTextView.setText(Html.fromHtml(text));
        apiRequirementsTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
