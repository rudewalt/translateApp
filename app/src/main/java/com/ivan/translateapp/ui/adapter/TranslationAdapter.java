package com.ivan.translateapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ivan.translateapp.R;
import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.ui.presenter.ISupportIsFavoriteCheckbox;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ivan on 01.04.2017.
 */

public class TranslationAdapter extends ArrayAdapter<Translation> {

    private LayoutInflater inflater;
    private int layout;
    private List<Translation> translationList;
    private ISupportIsFavoriteCheckbox iSupportIsFavoriteCheckbox;

    public TranslationAdapter(Context context, int resource, List<Translation> translationList, ISupportIsFavoriteCheckbox iSupportIsFavoriteCheckbox) {
        super(context, resource, translationList);
        this.translationList = translationList;
        this.iSupportIsFavoriteCheckbox = iSupportIsFavoriteCheckbox;
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

        viewHolder.isFavoriteCheckbox.setOnCheckedChangeListener(null);
        viewHolder.isFavoriteCheckbox.setChecked(translation.isFavourite());
        viewHolder.isFavoriteCheckbox.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
                    translation.setFavourite(isChecked);
                    translationList.set(position, translation);
                    iSupportIsFavoriteCheckbox.isFavoriteCheckboxStateChanged(translation);
                });

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.template_addToFavorites)
        CheckBox isFavoriteCheckbox;
        @BindView(R.id.template_textView)
        TextView text;
        @BindView(R.id.template_translatedTextView)
        TextView translated;
        @BindView(R.id.template_directionView)
        TextView direction;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
