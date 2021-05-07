import java.sql.PreparedStatement;
import java.util.Scanner;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    public static enum estado {ACTIVO, PENDIENTE, BLOQUEDO}
public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
//Crear la base de datos App.db si no existe. Si existe, conectar con ella cada vez que se
//ejecute el programa. La base de datos contiene una única tabla Usuarios, con los
//campos Id (numérico, clave primaria; Username, Email, Password y Estado (posibles
//valores: ACTIVO, PENDIENTE, BLOQUEADO), todos ellos de texto y máximo 100
//caracteres.
        String url = "jdbc:sqlite:D:\\Educa\\daw1_a\\conexionBBDD\\App-Practica\\App.db";
SQLiteDataSource sqlData = new SQLiteDataSource();
sqlData.setUrl(url);
        try (Connection con = sqlData.getConnection()) {
        if (con.isValid(5));{
            System.out.println("Conexion realizada.");}
try (Statement st = con.createStatement()){
    String tablaUsuarios = "CREATE TABLE Usuarios (\n" +
            "id INTEGER CONSTRAINT usuarios_pk PRIMARY KEY,\n" +
            "usarname NVARCHAR(100),\n" +
            "email NVARCHAR(100),\n" +
            "password NVARCHAR(100),\n" +
            "estado NVARCHAR(100),\n" +
            " CONSTRAINT usuarios_estado_ck CHECK (estado in ('ACTIVO', 'PENDIENTE', 'BLOQUEADO')));";
st.executeUpdate(tablaUsuarios);
}
        } catch (SQLException sqlE) {
            System.out.println("Excepcion al establecer la conexion :" + sqlE.getSQLState());
        }


    }
    //void añadirRegistro(Connection con): pide por teclado los datos
    //necesarios para rellenar un registro de App.db, y los inserta en
    //la base de datos. Comprueba su funcionamiento añadiendo tres
    //usuarios desde el método main. Una vez hayas implementado, más
    //adelante, el método obtenerÚltimoId(Statement st), implementa su
    //utilización en este método para generar el Id de usuario
    //automáticamente. Utilizar PreparedStatement
public void añadirRegistro (Connection con) throws SQLException {
    System.out.println("¿Que id quiere darle al usuario?: ");
    int id = sc.nextInt();
    sc.nextLine();
    System.out.println("¿Que nombre quiere darle al usuario?: ");
    String usarName = sc.nextLine();
    System.out.println("¿Que email quiere darle al usuario?: ");
    String email = sc.nextLine();
    System.out.println("¿Que contraseña quiere darle al usuario?: ");
    String contraseña = sc.nextLine();
    System.out.println("¿Con que estado quiere crear el usuario?: ");
    String estado = sc.nextLine();
    String insert = "INSERT INTO Usuarios VALUES (?, ?, ?, ?, ?);";
        try (PreparedStatement st = con.prepareStatement(insert)) {
            st.setInt(1, id);
            st.setString(2, "Carlos Bouzas Álvaro");
            st.setString();
        }

}

}

