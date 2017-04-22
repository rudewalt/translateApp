package com.ivan.translateapp.ui.view;

/**
 * Базовый интерфейс View
 */

public interface IView {


    /**
     * Показать пользователю сообщение обошибке.
     * Текст и сообщение локализовано и берется из ресурсов
     *
     * @param titleResourceName       - название ресурса с заголовком
     * @param descriptionResourceName - название ресурса с описанием
     */
    void showError(String titleResourceName, String descriptionResourceName);

    /**
     * Показать пользователю сообщение об ошибке.
     *
     * @param errorMessage - текст сообщения
     */
    void showError(String errorMessage);

    void showInternetConnectionError();

    void onShowView();

    void onHideView();
}
