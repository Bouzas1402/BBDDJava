package com.company;

import org.sqlite.SQLiteDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class conexion {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:D:\\Educa\\daw1_a\\sqlite\\chinook.db";
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (
                Connection con = dataSource.getConnection()) {
            if (con.isValid(5)) {
                System.out.printf("Conexión establecida");
            }
            //
            try (Statement st = con.createStatement()){
                String sql = "CREATE TABLE quevedofest (id INT CONSTRAINT quevedofest_pk PRIMARY KEY, nombre VARCHAR(15), ubicacion VARCHAR(10), capacidad INT, web VARCHAR(255)";


            }


            //
        } catch (
                SQLException throwables) {
            System.out.println("Excepción al establecer la conexión " + throwables.getSQLState());
        }
    }
}
