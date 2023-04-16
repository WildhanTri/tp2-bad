package com.example.model;

import java.sql.Date;

public class Pasien {
    private int id;
    private int no;
    private String namaPasien;
    private String nik;
    private Date tanggalLahir;
    private String alamat;

    public Pasien(int id, int no, String namaPasien, String nik, Date tanggalLahir, String alamat) {
        this.id = id;
        this.no = no;
        this.namaPasien = namaPasien;
        this.nik = nik;
        this.tanggalLahir = tanggalLahir;
        this.alamat = alamat;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }
    public String getNamaPasien() {
        return namaPasien;
    }
    public void setNamaPasien(String namaPasien) {
        this.namaPasien = namaPasien;
    }
    public String getNik() {
        return nik;
    }
    public void setNik(String nik) {
        this.nik = nik;
    }
    public Date getTanggalLahir() {
        return tanggalLahir;
    }
    public void setTanggalLahir(Date tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }
    public String getAlamat() {
        return alamat;
    }
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
