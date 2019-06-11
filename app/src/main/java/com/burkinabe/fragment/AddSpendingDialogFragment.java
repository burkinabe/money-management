package com.burkinabe.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.burkinabe.database.DatabaseHandler;
import com.burkinabe.database.entities.Depense;
import com.burkinabe.moneymanagement.R;

import java.util.Date;


public class AddSpendingDialogFragment extends DialogFragment {


    private EditText montantEditText;
    private EditText motifEditText;

    private Button saveButton;
    private Button cancelButton;

    public interface AddSpendingDialogFragmentListener {
        void onFinishAddingDepense(Depense depense);
    }

    public AddSpendingDialogFragment() {}


    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle saveInstanceState){

        View view = inflater.inflate(
                R.layout.add_spending_dialog_layout, container);

        //---get the EditText and Button views
        motifEditText = (EditText) view.findViewById(R.id.motif_depense_edittext);
        montantEditText = (EditText) view.findViewById(R.id.montant_depense_edittext);
        saveButton = (Button) view.findViewById(R.id.button_save);
        cancelButton = (Button) view.findViewById(R.id.button_cancel);

        //---event handler for the button
        saveButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view) {

                sendBackResult();

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //---show the keyboard automatically
        motifEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return view;
    }

    public void sendBackResult() {
        //---gets the calling activity
        AddSpendingDialogFragmentListener activity = (AddSpendingDialogFragmentListener) getTargetFragment();
        Depense depense = new Depense();
        depense.setMotif(motifEditText.getText().toString());
        depense.setMontantDepense(Double.parseDouble(montantEditText.getText().toString()));
        depense.setDateDepense(new Date());
        DatabaseHandler databaseHandler = new DatabaseHandler(getContext());
//                databaseHandler.addDepense(depense);
        assert activity != null;
        activity.onFinishAddingDepense(depense);

        //---dismiss the alert
        dismiss();
    }
}
