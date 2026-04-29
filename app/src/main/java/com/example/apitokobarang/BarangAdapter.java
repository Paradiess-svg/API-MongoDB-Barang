package com.example.apitokobarang;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.BarangViewHolder> {

    private List<Barang> barangList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(Barang barang);
        void onDeleteClick(Barang barang);
    }

    public BarangAdapter(List<Barang> barangList, OnItemClickListener listener) {
        this.barangList = barangList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang, parent, false);
        return new BarangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BarangViewHolder holder, int position) {
        Barang barang = barangList.get(position);
        holder.tvNama.setText(barang.getNama());
        holder.tvHarga.setText("Harga: Rp " + barang.getHargaSatuan());
        holder.tvStok.setText("Stok: " + barang.getStok());

        holder.btnEdit.setOnClickListener(v -> listener.onEditClick(barang));
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(barang));
    }

    @Override
    public int getItemCount() {
        return barangList != null ? barangList.size() : 0;
    }

    public void setData(List<Barang> newList) {
        this.barangList = newList;
        notifyDataSetChanged();
    }

    static class BarangViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvHarga, tvStok;
        ImageButton btnEdit, btnDelete;

        public BarangViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvHarga = itemView.findViewById(R.id.tv_harga);
            tvStok = itemView.findViewById(R.id.tv_stok);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
