package ru.mirea.korovkin.mireaproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;

import com.yandex.runtime.image.ImageProvider;

public class PlacesFragment extends Fragment
        implements UserLocationObjectListener {

    private MapView mapView;

    private UserLocationLayer userLocationLayer;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {

        View view = inflater.inflate(
                R.layout.fragment_places,
                container,
                false
        );

        MapKitFactory.initialize(requireContext());

        mapView = view.findViewById(R.id.mapview);

        mapView.getMap().move(
                new CameraPosition(
                        new Point(55.670005, 37.479894),
                        11.0f,
                        0.0f,
                        0.0f
                )
        );

        addPlaces();

        checkPermissions();

        return view;
    }

    private void addPlaces() {

        addMarker(
                new Point(55.670005, 37.479894),
                "РТУ МИРЭА",
                "Москва, проспект Вернадского"
        );

        addMarker(
                new Point(55.755864, 37.617698),
                "Красная площадь",
                "Главная площадь Москвы"
        );

        addMarker(
                new Point(55.760186, 37.618711),
                "ЦУМ",
                "Торговый центр"
        );
    }

    private void addMarker(
            Point point,
            String title,
            String description
    ) {

        PlacemarkMapObject marker =
                mapView.getMap().getMapObjects().addPlacemark(
                        point,
                        ImageProvider.fromResource(
                                requireContext(),
                                android.R.drawable.star_big_on
                        )
                );

        marker.setText(title);

        marker.setIconStyle(
                new IconStyle()
                        .setScale(2.5f)
                        .setAnchor(new PointF(0.5f, 1.0f))
        );

        marker.addTapListener(new MapObjectTapListener() {

            @Override
            public boolean onMapObjectTap(
                    @NonNull MapObject mapObject,
                    @NonNull Point point
            ) {

                Toast.makeText(
                        requireContext(),
                        title + "\n" + description,
                        Toast.LENGTH_LONG
                ).show();

                return true;
            }
        });
    }

    private void checkPermissions() {

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    requireActivity(),
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    300
            );

        } else {

            loadUserLocationLayer();
        }
    }

    private void loadUserLocationLayer() {

        userLocationLayer = MapKitFactory.getInstance()
                .createUserLocationLayer(
                        mapView.getMapWindow()
                );

        userLocationLayer.setVisible(true);

        userLocationLayer.setHeadingEnabled(true);

        userLocationLayer.setObjectListener(this);
    }

    @Override
    public void onObjectAdded(
            @NonNull UserLocationView userLocationView
    ) {

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
    public void onObjectRemoved(
            @NonNull UserLocationView userLocationView
    ) {

    }

    @Override
    public void onObjectUpdated(
            @NonNull UserLocationView userLocationView,
            @NonNull ObjectEvent objectEvent
    ) {

    }

    @Override
    public void onStart() {
        super.onStart();

        MapKitFactory.getInstance().onStart();

        mapView.onStart();
    }

    @Override
    public void onStop() {

        mapView.onStop();

        MapKitFactory.getInstance().onStop();

        super.onStop();
    }
}