package com.company;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class prepareST {
    //Sentencias ST
    public static void main(String[] args) {
        //si la base de datos no existiera la crearia, es un archivo db, no sql, por que un archivo sql
        //es un archivo quehay que cargar
        String url = "jdbc:sqlite:D:\\Educa\\daw1_a\\sqlite\\BaseDeDatos.db";
        SQLiteDataSource dataSource = new SQLiteDataSource();
    dataSource.setUrl(url);

    try (Connection con = dataSource.getConnection()){
        //ESTo es opcional
        if (con.isValid(5)) {
            System.out.printf("Conexión establecida");
        }
        // con este objeto podemos usar algunos metodos como executeUpdate para insert, delete, update y create
        // si queremos hacer una consulta usaremos el metodo executeQuery, este metodo devuelve un objeto de tipo ResultSet
        // para obtener los datos hace falta recorrer el objeto ResultSet re.next en un while para esto
        // next mandara registro hasta que no haya ningun registro (cada una de las lineas recuperadas de la base de datos)
        // mas.
        try (Statement st = con.createStatement()) {
            String createTable  = "CREATE TABLE IF NOT EXISTS Album (albumid INTEGER NOT NULL, titulo Nvarchar NOT null," +
                    "idartista INTEGER NOT NULL);";
        }
String y = "insert into album (albumid, titulo, idartista) VALUES (?, ?, ?);";
try (PreparedStatement pst = con.prepareStatement(y)){
pst.setInt(1, 2);
pst.setString(2, "Abbey Road");
pst.setInt(3,3);
int x = pst.executeUpdate();
    System.out.println("Se han insertado " + x + " Registros");
}

    } catch (SQLException throwables){
        System.out.println("Excepcion SQL al conectar con la base de datos.");
        throwables.printStackTrace();
    }

    }
}
