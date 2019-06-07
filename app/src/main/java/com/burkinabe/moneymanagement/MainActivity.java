package com.burkinabe.moneymanagement;

import android.net.Uri;
import android.os.Bundle;

import com.burkinabe.fragment.AddSpendingDialogFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SpendingFragment.OnFragmentInteractionListener, AddSpendingDialogFragment.OnFragmentInteractionListener {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_spendings:
                    SpendingFragment fragment = new SpendingFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_dashboard:
                    Toast.makeText(getApplicationContext(), "En cours de développement", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.navigation_saves:
                    Toast.makeText(getApplicationContext(), "En cours de développement", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.navigation_schedules:
                    Toast.makeText(getApplicationContext(), "En cours de développement", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.navigation_profil:
                    Toast.makeText(getApplicationContext(), "En cours de développement", Toast.LENGTH_LONG).show();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new SpendingFragment());
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_activity_framelayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
