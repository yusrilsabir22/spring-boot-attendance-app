package com.test.absensi.models;

public enum TypeJenisKelamin {
    PRIA,
    WANITA;

    public int getOrdinal() {
        return ordinal()+1;
    }
}
