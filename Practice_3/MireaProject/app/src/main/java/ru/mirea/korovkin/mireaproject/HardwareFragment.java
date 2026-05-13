package ru.mirea.korovkin.mireaproject;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import java.io.File;
import java.io.IOException;

public class HardwareFragment extends Fragment implements
        SensorEventListener {
    private static final int REQUEST_CODE_PERMISSION = 100;
    private TextView textDirection;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private ImageView imageView;
    private Uri imageUri;
    private MediaRecorder recorder;
    private MediaPlayer player;
    private boolean isRecording = false;
    private boolean isPlaying = false;
    private String recordFilePath;
    private Button buttonRecord;
    private Button buttonPlay;
    private ActivityResultLauncher<Intent> cameraLauncher;
    public HardwareFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hardware, container,
                false);
        textDirection = view.findViewById(R.id.textDirection);
        imageView = view.findViewById(R.id.imageView);
        buttonRecord = view.findViewById(R.id.buttonRecord);
        buttonPlay = view.findViewById(R.id.buttonPlay);
        checkPermissions();
        sensorManager = (SensorManager) requireActivity()
                .getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        recordFilePath =
                (
                        new File(
                                requireActivity().getExternalFilesDir(
                                        Environment.DIRECTORY_MUSIC
                                ),
                                "record.3gp"
                        )
                ).getAbsolutePath();
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            imageView.setImageURI(imageUri);
                        }
                    }
                }
        );
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent =
                        new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    File photoFile = createImageFile();
                    String authorities = requireActivity()
                            .getPackageName() + ".fileprovider";
                    imageUri = FileProvider.getUriForFile(
                            requireContext(),
                            authorities,
                            photoFile
                    );
                    cameraIntent.putExtra(
                            MediaStore.EXTRA_OUTPUT,
                            imageUri
                    );
                    cameraLauncher.launch(cameraIntent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        buttonRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRecording) {
                    startRecording();
                    buttonRecord.setText("Stop recording");
                } else {
                    stopRecording();
                    buttonRecord.setText("Start recording");
                }
                isRecording = !isRecording;
            }
        });
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPlaying) {
                    startPlaying();
                    buttonPlay.setText("Stop playing");
                } else {
                    stopPlaying();
                    buttonPlay.setText("Start playing");
                }
                isPlaying = !isPlaying;
            }
        });
        return view;
    }
    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.RECORD_AUDIO
                ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    requireActivity(),
                    new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.RECORD_AUDIO
                    },
                    REQUEST_CODE_PERMISSION
            );
        }
    }
    private File createImageFile() throws IOException {
        File storageDirectory = requireActivity()
                .getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                "IMAGE_",
                ".jpg",
                storageDirectory
        );
    }
    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(recordFilePath);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }
    private void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(recordFilePath);
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void stopPlaying() {
        player.release();
        player = null;
    }
    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(
                this,
                accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }
    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        String direction;
        if (x > 5) {
            direction = "West";
        }
        else if (x < -5) {
            direction = "East";
        }
        else {
            direction = "North";
        }
        textDirection.setText("Direction: " + direction);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}