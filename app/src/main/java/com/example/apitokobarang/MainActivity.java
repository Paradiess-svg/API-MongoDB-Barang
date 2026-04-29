package com.example.apitokobarang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvBarang;
    private BarangAdapter adapter;
    private ApiService apiService;
    private List<Barang> barangList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvBarang = findViewById(R.id.rv_barang);
        FloatingActionButton fabAdd = findViewById(R.id.fab_add);

        rvBarang.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BarangAdapter(barangList, new BarangAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(Barang barang) {
                showBarangDialog(barang);
            }

            @Override
            public void onDeleteClick(Barang barang) {
                deleteBarang(barang.getId());
            }
        });
        rvBarang.setAdapter(adapter);

        apiService = RetrofitClient.getApiService();
        loadBarang();

        fabAdd.setOnClickListener(v -> showBarangDialog(null));
    }

    private void loadBarang() {
        apiService.getAllBarang().enqueue(new Callback<List<Barang>>() {
            @Override
            public void onResponse(Call<List<Barang>> call, Response<List<Barang>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    barangList = response.body();
                    adapter.setData(barangList);
                }
            }

            @Override
            public void onFailure(Call<List<Barang>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal memuat data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showBarangDialog(Barang barang) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_barang, null);
        builder.setView(view);

        EditText etNama = view.findViewById(R.id.et_nama);
        EditText etHarga = view.findViewById(R.id.et_harga);
        EditText etStok = view.findViewById(R.id.et_stok);
        EditText etDeskripsi = view.findViewById(R.id.et_deskripsi);

        if (barang != null) {
            etNama.setText(barang.getNama());
            etHarga.setText(String.valueOf(barang.getHargaSatuan()));
            etStok.setText(String.valueOf(barang.getStok()));
            etDeskripsi.setText(barang.getDeskripsi());
            builder.setTitle("Edit Barang");
        } else {
            builder.setTitle("Tambah Barang");
        }

        builder.setPositiveButton("Simpan", (dialog, which) -> {
            String nama = etNama.getText().toString();
            int harga = Integer.parseInt(etHarga.getText().toString());
            int stok = Integer.parseInt(etStok.getText().toString());
            String deskripsi = etDeskripsi.getText().toString();

            Barang newBarang = new Barang(nama, harga, stok, deskripsi);
            if (barang == null) {
                createBarang(newBarang);
            } else {
                updateBarang(barang.getId(), newBarang);
            }
        });

        builder.setNegativeButton("Batal", null);
        builder.show();
    }

    private void createBarang(Barang barang) {
        apiService.createBarang(barang).enqueue(new Callback<Barang>() {
            @Override
            public void onResponse(Call<Barang> call, Response<Barang> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Berhasil menambah barang", Toast.LENGTH_SHORT).show();
                    loadBarang();
                }
            }

            @Override
            public void onFailure(Call<Barang> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateBarang(String id, Barang barang) {
        apiService.updateBarang(id, barang).enqueue(new Callback<Barang>() {
            @Override
            public void onResponse(Call<Barang> call, Response<Barang> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Berhasil update barang", Toast.LENGTH_SHORT).show();
                    loadBarang();
                }
            }

            @Override
            public void onFailure(Call<Barang> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteBarang(String id) {
        apiService.deleteBarang(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Berhasil menghapus barang", Toast.LENGTH_SHORT).show();
                    loadBarang();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
