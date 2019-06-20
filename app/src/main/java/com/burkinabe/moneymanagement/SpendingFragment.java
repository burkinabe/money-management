package com.burkinabe.moneymanagement;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.burkinabe.adapter.DepenseAdapter;
import com.burkinabe.database.DatabaseHandler;
import com.burkinabe.database.entities.Depense;
import com.burkinabe.database.entities.Depot;
import com.burkinabe.fragment.AddSpendingDialogFragment;
import com.burkinabe.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SpendingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SpendingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpendingFragment extends Fragment implements AddSpendingDialogFragment.AddSpendingDialogFragmentListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView spendingRecyclerView;
    private List<Depense> depenses;
    private TextView depotMensuelTextview;
    private TextView totalDepenseTexitview;

    private OnFragmentInteractionListener mListener;

    final Calendar dateRendezVousCalendar = Calendar.getInstance();

    public SpendingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SpendingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SpendingFragment newInstance(String param1, String param2) {
        SpendingFragment fragment = new SpendingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_spending, container, false);
        spendingRecyclerView = view.findViewById(R.id.spending_recyclerview);
        spendingRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        depenses = new ArrayList<>();
        final DepenseAdapter depenseAdapter = new DepenseAdapter(depenses);
        spendingRecyclerView.setAdapter(depenseAdapter);

        depotMensuelTextview = view.findViewById(R.id.depot_mensuel_textview);
        totalDepenseTexitview = view.findViewById(R.id.total_depense_textview);



        FloatingActionButton floatingActionButton = view.findViewById(R.id.spending_fab);
        final DatabaseHandler databaseHandler = new DatabaseHandler(view.getContext());

        if (databaseHandler.getCurrentMonthDepot() != null) {
            depotMensuelTextview.append(""+databaseHandler.getCurrentMonthDepot().getMontantInitial());
        }

        depenses.addAll(databaseHandler.getAllDepenses());

        double totalDepense = 0.0;
        for (Depense depense : depenses) {
            totalDepense += depense.getMontantDepense();
        }

        totalDepenseTexitview.setText(""+totalDepense);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (databaseHandler.getCurrentMonthDepot() != null) {
                    showInputNameDialog();
                } else {
                    Utils.showAlertDialog(getContext(), "Information", "Vous n'avez pas éffectué de depôt");
                }
            }
        });
        return view;
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onFinishAddingDepense(Depense depense) {
        DatabaseHandler databaseHandler = new DatabaseHandler(getContext());
        if (databaseHandler.getCurrentMonthDepot() != null) {
            if (databaseHandler.getCurrentMonthDepot().getMontantRestant() < depense.getMontantDepense()) {
                Utils.showAlertDialog(getContext(), "Information", "Votre solde n'est pas suffisant pour effectuer ce retrait");
            } else {
                Date date = new Date();
                depense.setDayValue(date.getDay());
                depense.setMonthValue(date.getMonth());
                depense.setYearValue(date.getYear());
                depense.setYearValue(Long.parseLong(date.getYear() + ""));
                databaseHandler.addDepense(depense);
                Depot depot = databaseHandler.getCurrentMonthDepot();
                Depot temp = new Depot();
                temp.setId(depot.getId());
                temp.setMontantInitial(depot.getMontantInitial());
                temp.setMonthValue(depot.getMonthValue());
                temp.setYearValue(depot.getYearValue());
                temp.setDateDepot(depot.getDateDepot());
                temp.setMontantRestant((depot.getMontantRestant() - depense.getMontantDepense()));
                databaseHandler.updateDepot(temp);
                depenses.add(depense);
                double totalDepense = 0.0;
                for (Depense d : depenses) {
                    totalDepense += d.getMontantDepense();
                }
                totalDepenseTexitview.setText("" + totalDepense);
            }
        } else {
            Toast.makeText(getContext(), "Aucune depense enregistrée", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    private void showInputNameDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        AddSpendingDialogFragment addSpendingDialogFragment = new AddSpendingDialogFragment();
        addSpendingDialogFragment.setCancelable(false);
        addSpendingDialogFragment.setTargetFragment(SpendingFragment.this, 300);
//        addSpendingDialogFragment.setDialogTitle("Enter Name");
        addSpendingDialogFragment.show(fragmentManager, "Input Dialog");
        DatabaseHandler databaseHandler = new DatabaseHandler(getContext());
        depenses.clear();
        depenses.addAll(databaseHandler.getAllDepenses());
        double totalDepense = 0.0;
        for (Depense depense : depenses) {
            totalDepense += depense.getMontantDepense();
        }

        totalDepenseTexitview.setText(""+totalDepense);
    }



    private void updateLabel(TextView textView) {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        textView.setText(sdf.format(dateRendezVousCalendar.getTime()));
    }

    public void updateDepotInitialTextView(String s) {
        depotMensuelTextview.setText(s);
    }

}
