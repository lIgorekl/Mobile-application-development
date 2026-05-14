package ru.mirea.korovkin.mireaproject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkFragment extends Fragment {

    private TextView ipTextView;
    private TextView cityTextView;
    private TextView regionTextView;
    private TextView countryTextView;
    private TextView coordinatesTextView;
    private TextView temperatureTextView;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {

        View view = inflater.inflate(
                R.layout.fragment_network,
                container,
                false
        );

        Button loadButton =
                view.findViewById(R.id.loadButton);

        ipTextView =
                view.findViewById(R.id.ipTextView);

        cityTextView =
                view.findViewById(R.id.cityTextView);

        regionTextView =
                view.findViewById(R.id.regionTextView);

        countryTextView =
                view.findViewById(R.id.countryTextView);

        coordinatesTextView =
                view.findViewById(R.id.coordinatesTextView);

        temperatureTextView =
                view.findViewById(R.id.temperatureTextView);

        loadButton.setOnClickListener(v -> {
            checkInternetAndLoad();
        });

        return view;
    }

    @SuppressWarnings("MissingPermission")
    private void checkInternetAndLoad() {

        ConnectivityManager connectivityManager =
                (ConnectivityManager)
                        requireContext().getSystemService(
                                Context.CONNECTIVITY_SERVICE
                        );

        NetworkInfo networkInfo =
                connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            new IpInfoTask().execute();

        } else {

            Toast.makeText(
                    requireContext(),
                    "No internet connection",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private class IpInfoTask
            extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {

            try {

                URL url =
                        new URL("https://ipinfo.io/json");

                HttpURLConnection connection =
                        (HttpURLConnection)
                                url.openConnection();

                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(
                                        connection.getInputStream()
                                )
                        );

                StringBuilder result =
                        new StringBuilder();

                String line;

                while ((line = reader.readLine()) != null) {

                    result.append(line);
                }

                reader.close();

                return result.toString();

            } catch (Exception e) {

                e.printStackTrace();

                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result == null) {

                Toast.makeText(
                        requireContext(),
                        "Error loading IP info",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            try {

                JSONObject jsonObject =
                        new JSONObject(result);

                String ip =
                        jsonObject.getString("ip");

                String city =
                        jsonObject.getString("city");

                String region =
                        jsonObject.getString("region");

                String country =
                        jsonObject.getString("country");

                String loc =
                        jsonObject.getString("loc");

                ipTextView.setText(ip);

                cityTextView.setText(city);

                regionTextView.setText(region);

                countryTextView.setText(country);

                coordinatesTextView.setText(loc);

                String[] coordinates =
                        loc.split(",");

                new WeatherTask(
                        coordinates[0],
                        coordinates[1]
                ).execute();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    private class WeatherTask
            extends AsyncTask<Void, Void, String> {

        private final String latitude;
        private final String longitude;

        public WeatherTask(
                String latitude,
                String longitude
        ) {

            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {

                String urlString =
                        "https://api.open-meteo.com/v1/forecast"
                                + "?latitude=" + latitude
                                + "&longitude=" + longitude
                                + "&current_weather=true";

                URL url = new URL(urlString);

                HttpURLConnection connection =
                        (HttpURLConnection)
                                url.openConnection();

                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(
                                        connection.getInputStream()
                                )
                        );

                StringBuilder result =
                        new StringBuilder();

                String line;

                while ((line = reader.readLine()) != null) {

                    result.append(line);
                }

                reader.close();

                return result.toString();

            } catch (Exception e) {

                e.printStackTrace();

                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result == null) {

                Toast.makeText(
                        requireContext(),
                        "Weather loading error",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            try {

                JSONObject jsonObject =
                        new JSONObject(result);

                JSONObject currentWeather =
                        jsonObject.getJSONObject(
                                "current_weather"
                        );

                double temperature =
                        currentWeather.getDouble(
                                "temperature"
                        );

                temperatureTextView.setText(
                        temperature + " °C"
                );

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }
}