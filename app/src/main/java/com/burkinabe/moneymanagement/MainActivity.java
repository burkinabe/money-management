package com.burkinabe.moneymanagement;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import com.burkinabe.database.DatabaseHandler;
import com.burkinabe.database.entities.Depense;
import com.burkinabe.database.entities.Depot;
import com.burkinabe.fragment.AddSpendingDialogFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import java.util.Date;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements SpendingFragment.OnFragmentInteractionListener, AddSpendingDialogFragment.AddSpendingDialogFragmentListener {
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

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

    @Override
    public void onFinishAddingDepense(Depense depense) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_deposit:
                showAddDepositDialog();
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    public void showAddDepositDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.new_deposit));
// I'm using fragment here so I'm using getView() to provide ViewGroup
// but you can provide here any other instance of ViewGroup from your Fragment / Activity
        View viewInflated = LayoutInflater.from(getApplicationContext()).inflate(R.layout.add_deposit_layout, null, false);
// Set up the input
        final EditText input = (EditText) viewInflated.findViewById(R.id.add_deposit_textview);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(viewInflated);

// Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String deposit = input.getText().toString();
                DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());
                if (databaseHandler.getCurrentMonthDepot() == null) {
                    Depot depot = new Depot();
                    Date date = new Date();
                    depot.setDateDepot(date);
                    depot.setMonthValue(date.getMonth());
                    depot.setYearValue(date.getYear());
                    depot.setMontantInitial(Double.parseDouble(deposit));
                    depot.setMontantRestant(Double.parseDouble(deposit));
                    databaseHandler.addDepot(depot);
                    SpendingFragment fragment = new SpendingFragment();
                    loadFragment(fragment);
                } else {
                    Depot depot = databaseHandler.getCurrentMonthDepot();
                    Depot temp = new Depot();
                    Date date = new Date();
                    temp.setId(depot.getId());
                    temp.setDateDepot(date);
                    temp.setMonthValue(date.getMonth());
                    temp.setYearValue(date.getYear());
                    temp.setMontantInitial(Double.parseDouble(deposit) + depot.getMontantRestant());
                    temp.setMontantRestant(Double.parseDouble(deposit) + depot.getMontantRestant());
                    databaseHandler.updateDepot(temp);
                    SpendingFragment fragment = new SpendingFragment();
                    loadFragment(fragment);
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
