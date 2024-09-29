package com.mycompany.autostockcar.modelo.conexao;

import java.sql.Connection;
import java.sql.SQLException;


public interface Conexao {
    public Connection obterConexao() throws SQLException;
}
