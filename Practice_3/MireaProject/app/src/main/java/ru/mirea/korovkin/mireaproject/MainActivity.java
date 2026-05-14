package ru.mirea.korovkin.mireaproject;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {

    NavigationView navView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        navView = findViewById(R.id.nav_view);

        // первый фрагмент при запуске
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new DataFragment())
                .commit();

        // обработка нажатий меню
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.nav_data) {
                    selectedFragment = new DataFragment();
                } else if (item.getItemId() == R.id.nav_web) {
                    selectedFragment = new WebViewFragment();
                }
                else if (item.getItemId() == R.id.nav_hardware) {
                    selectedFragment = new HardwareFragment();
                }
                else if (item.getItemId() == R.id.nav_profile) {
                    selectedFragment = new ProfileFragment();
                }
                else if (item.getItemId() == R.id.nav_notes) {
                    selectedFragment = new NotesFragment();
                }
                else if (item.getItemId() == R.id.nav_network) {
                    selectedFragment = new NetworkFragment();
                }
                else if (item.getItemId() == R.id.nav_places) {
                    selectedFragment = new PlacesFragment();
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                }

                return true;
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(
                R.menu.main_menu,
                menu
        );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_logout) {

            mAuth.signOut();

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            LoginActivity.class
                    );

            startActivity(intent);

            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}