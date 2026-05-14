package ru.mirea.korovkin.mireaproject;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class ProfileFragment extends Fragment {

    private EditText editName;
    private EditText editAge;
    private EditText editLanguage;

    private SeekBar seekLevel;

    private TextView textLevel;

    private Switch switchKotlin;

    private SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view =
                inflater.inflate(R.layout.fragment_profile,
                        container,
                        false);

        editName = view.findViewById(R.id.editName);
        editAge = view.findViewById(R.id.editAge);
        editLanguage = view.findViewById(R.id.editLanguage);

        seekLevel = view.findViewById(R.id.seekLevel);

        textLevel = view.findViewById(R.id.textLevel);

        switchKotlin = view.findViewById(R.id.switchKotlin);

        Button buttonSave =
                view.findViewById(R.id.buttonSave);

        preferences = requireActivity()
                .getSharedPreferences(
                        "profile_settings",
                        requireActivity().MODE_PRIVATE
                );

        loadProfile();

        seekLevel.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(
                            SeekBar seekBar,
                            int progress,
                            boolean fromUser) {

                        textLevel.setText(
                                "Уровень Android: " + progress);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });

        buttonSave.setOnClickListener(v -> saveProfile());

        return view;
    }

    private void saveProfile() {

        SharedPreferences.Editor editor =
                preferences.edit();

        editor.putString(
                "NAME",
                editName.getText().toString());

        editor.putString(
                "AGE",
                editAge.getText().toString());

        editor.putString(
                "LANGUAGE",
                editLanguage.getText().toString());

        editor.putInt(
                "LEVEL",
                seekLevel.getProgress());

        editor.putBoolean(
                "KOTLIN",
                switchKotlin.isChecked());

        editor.apply();
    }

    private void loadProfile() {

        editName.setText(
                preferences.getString("NAME", ""));

        editAge.setText(
                preferences.getString("AGE", ""));

        editLanguage.setText(
                preferences.getString("LANGUAGE", ""));

        int level =
                preferences.getInt("LEVEL", 0);

        seekLevel.setProgress(level);

        textLevel.setText(
                "Уровень Android: " + level);

        switchKotlin.setChecked(
                preferences.getBoolean("KOTLIN", false));
    }
}