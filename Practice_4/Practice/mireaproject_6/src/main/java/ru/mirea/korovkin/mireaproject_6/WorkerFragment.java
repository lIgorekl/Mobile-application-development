package ru.mirea.korovkin.mireaproject_6;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import ru.mirea.korovkin.mireaproject_6.databinding.FragmentWorkerBinding;

public class WorkerFragment extends Fragment {

    private FragmentWorkerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentWorkerBinding.inflate(inflater, container, false);

        binding.buttonStartWork.setOnClickListener(v -> {

            // Показываем, что началась работа
            binding.textStatus.setText("Выполняется...");

            // Создаём задачу
            OneTimeWorkRequest request =
                    new OneTimeWorkRequest.Builder(MyWorker.class)
                            .build();

            // Запускаем
            WorkManager.getInstance(requireContext())
                    .enqueue(request);

            // Подписываемся на результат
            WorkManager.getInstance(requireContext())
                    .getWorkInfoByIdLiveData(request.getId())
                    .observe(getViewLifecycleOwner(), workInfo -> {

                        if (workInfo != null &&
                                workInfo.getState().isFinished()) {

                            binding.textStatus.setText("Готово");
                        }
                    });
        });

        return binding.getRoot();
    }
}