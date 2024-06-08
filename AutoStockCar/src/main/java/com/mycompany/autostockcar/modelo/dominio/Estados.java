
package com.mycompany.autostockcar.modelo.dominio;


public class Estados {
    private int idEstado;
    private String nomeEstado;
    private String ufEstado;

    public Estados() {
    }

    public Estados(int idEstado, String nomeEstado, String ufEstado) {
        this.idEstado = idEstado;
        this.nomeEstado = nomeEstado;
        this.ufEstado = ufEstado;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String getNomeEstado() {
        return nomeEstado;
    }

    public void setNomeEstado(String nomeEstado) {
        this.nomeEstado = nomeEstado;
    }

    public String getUfEstado() {
        return ufEstado;
    }

    public void setUfEstado(String ufEstado) {
        this.ufEstado = ufEstado;
    }
}
