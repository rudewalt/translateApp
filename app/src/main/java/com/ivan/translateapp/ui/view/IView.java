package com.ivan.translateapp.ui.view;

/**
 * Базовый интерфейс View
 */

public interface IView {


    /**
     * Показать пользователю сообщение обошибке.
     * Текст и сообщение локализовано и берется из ресурсов
     * @param titleResourceName
     * @param descriptionResourceName
     */
    void showError(String titleResourceName, String descriptionResourceName);

    /**
     * Показать пользователю сообщение об ошибке.
     * @param errorMessage
     */
    void showError(String errorMessage);


    /**
     * Показать пользователю, сообщение, об отсутствии интернета
     */
    void showInternetConnectionError();


    void onShowView();


    void onHideView();
}
