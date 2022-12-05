package com.example.a470group.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.a470group.R;

/**
 * The type Custom help dialog fragment.
 */
public class CustomHelpDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.home_menu_option1))
                .setMessage(getString(R.string.dialog_option2))
                .setPositiveButton(getString(R.string.ok), (dialog, which) -> {
                })
                .create();
    }

    /**
     * The constant TAG.
     */
    public static String TAG = "CustomHelpDialog";

}
