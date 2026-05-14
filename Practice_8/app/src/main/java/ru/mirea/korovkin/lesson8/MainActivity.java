package ru.mirea.korovkin.lesson8;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;

public class MainActivity extends AppCompatActivity
        implements UserLocationObjectListener {

    private MapView mapView;
    private UserLocationLayer userLocationLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapKitFactory.initialize(this);

        setContentView(R.layout.activity_main);

        mapView = findViewById(R.id.mapview);

        mapView.getMap().move(
                new CameraPosition(
                        new Point(55.751574, 37.573856),
                        11.0f,
                        0.0f,
                        0.0f
                )
        );

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    100
            );

        } else {
            loadUserLocationLayer();
        }
    }

    private void loadUserLocationLayer() {

        userLocationLayer = MapKitFactory.getInstance()
                .createUserLocationLayer(mapView.getMapWindow());

        userLocationLayer.setVisible(true);

        userLocationLayer.setHeadingEnabled(true);

        userLocationLayer.setObjectListener(this);
    }

    @Override
    public void onObjectAdded(@NonNull UserLocationView userLocationView) {

        userLocationLayer.setAnchor(
                new PointF(
                        (float) (mapView.getWidth() * 0.5),
                        (float) (mapView.getHeight() * 0.5)
                ),
                new PointF(
                        (float) (mapView.getWidth() * 0.5),
                        (float) (mapView.getHeight() * 0.83)
                )
        );

        userLocationView.getAccuracyCircle()
                .setFillColor(Color.BLUE & 0x99ffffff);
    }

    @Override
    public void onObjectRemoved(@NonNull UserLocationView userLocationView) {

    }

    @Override
    public void onObjectUpdated(
            @NonNull UserLocationView userLocationView,
            @NonNull ObjectEvent objectEvent
    ) {

    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
        );

        if (requestCode == 100) {

            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                loadUserLocationLayer();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {

        mapView.onStop();
        MapKitFactory.getInstance().onStop();

        super.onStop();
    }
}