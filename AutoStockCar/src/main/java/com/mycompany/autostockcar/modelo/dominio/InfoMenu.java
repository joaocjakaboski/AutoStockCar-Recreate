
package com.mycompany.autostockcar.modelo.dominio;


public class InfoMenu {
    private String nomeUsuario;
    private Perfil perfil;

    public InfoMenu() {
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
    
}
