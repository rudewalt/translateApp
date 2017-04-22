package com.ivan.translateapp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ivan.translateapp.R;
import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.ui.presenter.ISupportFavoritesPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TranslationAdapter extends RecyclerView.Adapter<TranslationAdapter.ViewHolder> {

    private final List<Translation> translations;
    private final ISupportFavoritesPresenter iSupportFavoritesPresenter;
    private final int layout;

    public TranslationAdapter(@NonNull List<Translation> translations,
                              @NonNull ISupportFavoritesPresenter iSupportFavoritesPresenter,
                              int layout) {
        this.translations = translations;
        this.iSupportFavoritesPresenter = iSupportFavoritesPresenter;
        this.layout = layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Translation translation = translations.get(position);

        holder.bindTranslation(translation);
        holder.text.setText(translation.getText());
        holder.translated.setText(translation.getTranslated());
        holder.direction.setText(translation.getDirection());

        holder.isFavoriteCheckbox.setOnCheckedChangeListener(null);
        holder.isFavoriteCheckbox.setChecked(translation.isFavorite());
        holder.isFavoriteCheckbox.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
                    translation.setFavorite(isChecked);
                    translations.set(position, translation);
                    iSupportFavoritesPresenter.isFavoriteCheckboxStateChanged(translation);
                });

        holder.itemView.setOnClickListener(view ->
                iSupportFavoritesPresenter.clickOnTranslation(holder.getTranslation()));
    }

    @Override
    public int getItemCount() {
        return translations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.template_addToFavorites)
        CheckBox isFavoriteCheckbox;
        @BindView(R.id.template_textView)
        TextView text;
        @BindView(R.id.template_translatedTextView)
        TextView translated;
        @BindView(R.id.template_directionView)
        TextView direction;
        private Translation translation;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindTranslation(Translation translation) {
            this.translation = translation;

        }

        public Translation getTranslation() {
            return translation;
        }
    }
}
