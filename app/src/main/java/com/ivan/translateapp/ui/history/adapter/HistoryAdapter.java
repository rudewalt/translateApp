package com.ivan.translateapp.ui.history.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ivan.translateapp.R;
import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.ui.history.presenter.IHistoryPresenter;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;

import java.util.List;
import java.util.Observable;

/**
 * Created by Ivan on 01.04.2017.
 */

public class HistoryAdapter extends ArrayAdapter<Translation> {

    private LayoutInflater inflater;
    private int layout;
    private List<Translation> translationList;
    private IHistoryPresenter iHistoryPresenter;

    public HistoryAdapter(Context context, int resource, List<Translation> translationList, IHistoryPresenter iHistoryPresenter) {
        super(context, resource, translationList);
        this.translationList = translationList;
        this.iHistoryPresenter = iHistoryPresenter;
        layout = resource;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Translation translation = translationList.get(position);
        viewHolder.text.setText(translation.getText());
        viewHolder.translated.setText(translation.getTranslated());
        viewHolder.direction.setText(translation.getDirection());

        viewHolder.addToFavourites.setOnCheckedChangeListener(null);
        viewHolder.addToFavourites.setChecked(translation.isFavourite());
        viewHolder.addToFavourites.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
                    translation.setFavourite(isChecked);
                    translationList.set(position, translation);
                    iHistoryPresenter.saveChanges(translation);
                });

        return convertView;
    }

    private class ViewHolder {
        final CheckBox addToFavourites;
        final TextView text, translated, direction;

        ViewHolder(View view) {
            addToFavourites = (CheckBox) view.findViewById(R.id.template_addToFavourites);
            text = (TextView) view.findViewById(R.id.template_textView);
            translated = (TextView) view.findViewById(R.id.template_translatedTextView);
            direction = (TextView) view.findViewById(R.id.template_directionView);
        }
    }
}
