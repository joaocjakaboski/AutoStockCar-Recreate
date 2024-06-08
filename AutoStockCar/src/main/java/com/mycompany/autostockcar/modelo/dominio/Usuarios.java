package com.mycompany.autostockcar.modelo.dominio;


public class Usuarios {
    private int idUsuario;
    private String nomeUsuario;
    private String senhaUsuario;
    private Perfil admCategoria;

    public Usuarios() {
    }
    
    public Usuarios(int idUsuario, String nomeUsuario, String senhaUsuario, Perfil admCategoria) {
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.senhaUsuario = senhaUsuario;
        this.admCategoria = admCategoria;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenhaUsuario() {
        return senhaUsuario;
    }

    public void setSenhaUsuario(String senhaUsuario) {
        this.senhaUsuario = senhaUsuario;
    }

    public Perfil getAdmCategoria() {
        return admCategoria;
    }

    public void setAdmCategoria(Perfil admCategoria) {
        this.admCategoria = admCategoria;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.idUsuario;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuarios other = (Usuarios) obj;
        return this.idUsuario == other.idUsuario;
    }  
}
