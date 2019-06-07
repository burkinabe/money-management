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
import android.widget.EditText;

import com.burkinabe.database.entities.Depense;
import com.burkinabe.moneymanagement.R;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddSpendingDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddSpendingDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddSpendingDialogFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText motifTextView;
    private EditText montantDepense;

    public interface AddSpendingDialogFragmentListner {
        public void onSaveButtonClick(DialogFragment dialogFragment);
        public void onCancelButtonClick (DialogFragment dialogFragment);
        void onFinishEditDialog(Depense depense);
    }

    public void sendBackResult() {
        AddSpendingDialogFragmentListner listner = (AddSpendingDialogFragmentListner) getTargetFragment();
        Depense depense = new Depense();
        depense.setMontantDepense(Double.parseDouble(montantDepense.getText().toString()));
        depense.setMotif(motifTextView.getText().toString());
        depense.setDateDepense(new Date());
        listner.onFinishEditDialog(depense);
        dismiss();
    }

    private OnFragmentInteractionListener mListener;
    private AddSpendingDialogFragmentListner addSpendingDialogFragmentListner;

    public AddSpendingDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddSpendingDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddSpendingDialogFragment newInstance(String param1, String param2) {
        AddSpendingDialogFragment fragment = new AddSpendingDialogFragment();
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

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage("Are you sure?");
        alertDialogBuilder.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // on success
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }

        });

        return alertDialogBuilder.create();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_spending_dialog, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        motifTextView = view.findViewById(R.id.motif_depense_textview);
        montantDepense = view.findViewById(R.id.montant_depense_textview);
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
}
