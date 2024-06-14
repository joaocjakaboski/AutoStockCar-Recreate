package com.mycompany.autostockcar;

import com.mycompany.autostockcar.view.formulario.Login;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        new Login().setVisible(true);
    }
}