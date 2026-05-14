package ru.mirea.korovkin.mireaproject;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class NotesFragment extends Fragment {

    private ListView listView;

    private ArrayList<String> notes;

    private ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view =
                inflater.inflate(R.layout.fragment_notes,
                        container,
                        false);

        listView = view.findViewById(R.id.listViewNotes);

        FloatingActionButton fabAdd =
                view.findViewById(R.id.fabAdd);

        notes = new ArrayList<>();

        adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                notes
        );

        listView.setAdapter(adapter);

        loadNotes();

        fabAdd.setOnClickListener(v -> showDialog());

        return view;
    }

    private void showDialog() {

        EditText editText =
                new EditText(requireContext());

        new AlertDialog.Builder(requireContext())
                .setTitle("Новая заметка")
                .setMessage("Введите текст")
                .setView(editText)

                .setPositiveButton("Сохранить",
                        (dialog, which) -> {

                            String text =
                                    editText.getText().toString();

                            saveNote(text);

                            notes.add(text);

                            adapter.notifyDataSetChanged();
                        })

                .setNegativeButton("Отмена", null)
                .show();
    }

    private void saveNote(String text) {

        try {

            String encoded =
                    Base64.encodeToString(
                            text.getBytes(StandardCharsets.UTF_8),
                            Base64.DEFAULT
                    );

            String fileName =
                    "note_" + System.currentTimeMillis() + ".txt";

            File file =
                    new File(
                            requireContext().getFilesDir(),
                            fileName
                    );

            FileOutputStream stream =
                    new FileOutputStream(file);

            stream.write(encoded.getBytes());

            stream.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void loadNotes() {

        File directory =
                requireContext().getFilesDir();

        File[] files =
                directory.listFiles();

        if (files == null) return;

        for (File file : files) {

            if (file.getName().startsWith("note_")) {

                try {

                    java.io.FileInputStream stream =
                            new java.io.FileInputStream(file);

                    byte[] bytes =
                            new byte[(int) file.length()];

                    stream.read(bytes);

                    stream.close();

                    String encoded =
                            new String(bytes);

                    byte[] decodedBytes =
                            Base64.decode(encoded, Base64.DEFAULT);

                    String decodedText =
                            new String(decodedBytes);

                    notes.add(decodedText);

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }
    }
}