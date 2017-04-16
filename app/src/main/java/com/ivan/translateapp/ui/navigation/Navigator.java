package com.ivan.translateapp.ui.navigation;

import com.ivan.translateapp.domain.Translation;

/**
 * Created by Ivan on 15.04.2017.
 */

//TODO delete or use
public class Navigator {

    public interface ISupportNavigation{
        void openMainFragment(Translation translation);
    }

    ISupportNavigation iSupportNavigation;

    public Navigator(ISupportNavigation iSupportNavigation){
        this.iSupportNavigation = iSupportNavigation;
    }

    public void navigateToMainFragment(Translation translation){
        iSupportNavigation.openMainFragment(translation);
    }
}
