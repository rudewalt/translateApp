package com.ivan.translateapp.ui.view;

import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.ivan.translateapp.R;
import com.ivan.translateapp.utils.ResourceUtils;


public class BaseFragment extends Fragment {

    public void showError(String titleResName, String descriptionResName) {
        String title = getResources().getString(ResourceUtils.getResId(titleResName, R.string.class));
        String description = getResources().getString(ResourceUtils.getResId(descriptionResName, R.string.class));

        openErrorDialog(title, description);
    }

    public void showError(String errorMessage) {
        String title = getResources().getString(R.string.title_error);
        openErrorDialog(title, errorMessage);
    }

    public void showInternetConnectionError() {
        int duration = Toast.LENGTH_SHORT;
        String text = getResources().getString(R.string.error_description_no_internet);

        Toast toast = Toast.makeText(getActivity(), text, duration);
        toast.show();
    }

    private void openErrorDialog(String title, String description) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(description)
                .setPositiveButton(getString(R.string.title_button_ok), (dialog, whichButton) -> {
                });

        alert.show();
    }
}
