package com.ivan.translateapp.ui.view;

/**
 * Created by Ivan on 02.04.2017.
 */

public interface IView {


    /**
     * Show error message to user
     */
    void showError(String title, String description);


    void showInternetConnectionError();

    /**
     * Called when view become visible to user
     */
    void onShowView();


    /**
     * Called when view become hide to user
     */
    void onHideView();
}
