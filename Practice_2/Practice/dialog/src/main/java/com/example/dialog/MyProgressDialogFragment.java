package com.example.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class MyProgressDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Загрузка");
        dialog.setMessage("Пожалуйста, подождите...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        return dialog;
    }
}