package com.burkinabe.adapter;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.burkinabe.database.entities.Depense;
import com.burkinabe.moneymanagement.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class DepenseAdapter extends RecyclerView.Adapter<DepenseAdapter.DepenseViewHolder> {

    List<Depense> depenses;
    Locale locale = Locale.FRANCE;

    public DepenseAdapter(List<Depense> depenses) {
        this.depenses = depenses;
    }

    @NonNull
    @Override
    public DepenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.spending_recycler_view_item, parent, false);
        DepenseViewHolder holder = new DepenseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DepenseViewHolder holder, int position) {

        ((TextView) holder.itemView.findViewById(R.id.montant_depense_textview)).setText(depenses.get(position).getMontantDepense() + "");
        ((TextView) holder.itemView.findViewById(R.id.date_depense_textview)).setText(SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG, locale).format(
                depenses.get(position).getDateDepense()
        ));
        ((TextView) holder.itemView.findViewById(R.id.motif_depense_textview)).setText(depenses.get(position).getMotif());

    }

    @Override
    public int getItemCount() {
        return depenses.size();
    }

    class DepenseViewHolder extends RecyclerView.ViewHolder {
        DepenseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
