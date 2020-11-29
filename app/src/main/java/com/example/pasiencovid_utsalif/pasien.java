package com.example.pasiencovid_utsalif;

public class pasien {
    String id_pasien, nama_pasien, alamat_pasien, jk_pasien, umur_pasien, telp_pasien, status;

    public pasien(String id_pasien, String nama_pasien, String alamat_pasien , String jk_pasien, String umur_pasien, String telp_pasien, String status) {
        this.id_pasien = id_pasien;
        this.nama_pasien = nama_pasien;
        this.alamat_pasien =alamat_pasien;
        this.jk_pasien =jk_pasien;
        this.umur_pasien =umur_pasien;
        this.telp_pasien =telp_pasien;
        this.status =status;
    }

    public String getIdPasien() {
        return id_pasien;
    }

    public String getNamaPasien() {
        return nama_pasien;
    }

    public String getAlamatPasien() {
        return alamat_pasien;
    }

    public String getJkPasien() {
        return jk_pasien;
    }

    public String getUmurPasien() {
        return umur_pasien;
    }

    public String getTelpPasien() {
        return telp_pasien;
    }

    public String getStatus() {
        return status;
    }
}
