package com.example.apitokobarang;

import com.google.gson.annotations.SerializedName;

public class Barang {
    @SerializedName("_id")
    private String id;
    
    private String nama;
    
    @SerializedName("harga_satuan")
    private int hargaSatuan;
    
    private int stok;
    
    private String deskripsi;

    public Barang(String nama, int hargaSatuan, int stok, String deskripsi) {
        this.nama = nama;
        this.hargaSatuan = hargaSatuan;
        this.stok = stok;
        this.deskripsi = deskripsi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getHargaSatuan() {
        return hargaSatuan;
    }

    public void setHargaSatuan(int hargaSatuan) {
        this.hargaSatuan = hargaSatuan;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
