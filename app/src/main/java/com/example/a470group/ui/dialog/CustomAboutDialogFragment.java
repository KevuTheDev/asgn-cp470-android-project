package com.example.a470group.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.a470group.R;

public class CustomAboutDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.home_menu_option2))
                .setMessage(getString(R.string.dialog_option1))
                .setPositiveButton(getString(R.string.ok), (dialog, which) -> {} )
                .create();
    }

    public static String TAG = "CustomAboutDialog";

}

