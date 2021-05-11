import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;
import org.sqlite.SQLiteDataSource;

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
            con.setAutoCommit(false);
            if (con.isValid(5)) ;
            {
                System.out.println("Conexion realizada.");
            }
            // cambiarEstado(con, "perex llanos ", "BLOQUEADO");
            try (Statement st = con.createStatement()) {
                try (ResultSet rs = st.executeQuery("SELECT * FROM Usuarios;")) {
                   // exportarAFichero(rs);
                }

                System.out.println(obtenerUltimoId(con));
              //  consultarUsuario(con);
/*    String tablaUsuarios = "CREATE TABLE Usuarios (\n" +
            "id INTEGER CONSTRAINT usuarios_pk PRIMARY KEY,\n" +
            "usarname NVARCHAR(100),\n" +
            "email NVARCHAR(100),\n" +
            "password NVARCHAR(100),\n" +
            "estado NVARCHAR(100),\n" +
            " CONSTRAINT usuarios_estado_ck CHECK (estado in ('ACTIVO', 'PENDIENTE', 'BLOQUEADO')));";
st.executeUpdate(tablaUsuarios); */
                // añadirRegistro(con);

            } catch (SQLException e) {
                if (con != null) {
                    try {
                        System.err.println("Transaction is being revert back");
                        con.rollback();
                    } catch (SQLException excep) {
                        excep.printStackTrace();
                    }
                }
            }
        } catch (SQLException sqlE) {
            System.out.println("Excepcion al establecer la conexion :" + sqlE.getSQLState() + sqlE.getMessage());
        }
    }

    //void añadirRegistro(Connection con): pide por teclado los datos
    //necesarios para rellenar un registro de App.db, y los inserta en
    //la base de datos. Comprueba su funcionamiento añadiendo tres
    //usuarios desde el método main. Una vez hayas implementado, más
    //adelante, el método obtenerÚltimoId(Statement st), implementa su
    //utilización en este método para generar el Id de usuario
    //automáticamente. Utilizar PreparedStatement
    public static void añadirRegistro(Connection con) throws SQLException {
        boolean salida = true;
        do {
            System.out.println("¿Que nombre quiere darle al usuario?: ");
            String userName = sc.nextLine();
            System.out.println("¿Que email quiere darle al usuario?: ");
            String email = sc.nextLine();
            System.out.println("¿Que contraseña quiere darle al usuario?: ");
            String contraseña = sc.nextLine();
            System.out.println("¿Con que estado quiere crear el usuario?: ");
            String estado = sc.nextLine();
            String insert = "INSERT INTO Usuarios (id, usarname, email, password, estado) VALUES (?, ?, ?, ?, ?);";
            try (PreparedStatement st = con.prepareStatement(insert)) {
                st.setInt(1, (obtenerUltimoId(con) + 5));
                st.setString(2, userName);
                st.setString(3, email);
                st.setString(4, contraseña);
                st.setString(5, estado);
                st.executeUpdate();
                con.commit();
            } catch (SQLException e) {
                if (con != null) {
                    try {
                        System.err.println("Transaction is being revert back");
                        con.rollback();
                    } catch (SQLException excep) {
                        System.out.println(excep.getMessage());
                    }
                }
            }
            boolean salida2 = false;
            do {
                System.out.println("¿Quiere añadir otro usuario a la base de datos? s/n");
                String sON = sc.nextLine();
                if (sON.equalsIgnoreCase("s")) {
                    salida2 = false;
                } else if (sON.equalsIgnoreCase("n")) {
                    System.out.println("Esta bien, gracias.");
                    salida = false;
                    salida2 = false;
                } else {
                    System.out.println("Opcion incorrecta, vuelva a responder s (si) o n (no)");
                    salida2 = true;
                }
            } while (salida2);
        } while (salida);
    }
    //void cambiarEstado(Connection con, String username, String
//estado): actualiza el registro correspondiente al username con
//el estado que se recibe por parámetro. Comprueba su
//funcionamiento cambiando el estado de los usuarios añadidos al
//comprobar el método anterior. Utiliza Statement
    public static void cambiarEstado(Connection con, String username, String estado) {
        String cambiarEstado = "UPDATE Usuarios SET estado = \"" + estado + "\" WHERE usarname = \"" + username + "\";";
        try (Statement st = con.createStatement()) {
            st.executeUpdate(cambiarEstado);
            con.commit();
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            if (con != null) {
                try {
                    System.err.println("Transaction is being revert back");
                    con.rollback();
                } catch (SQLException excep) {
                    System.out.println(excep.getMessage());
                }
            }
        }
    }
    //void mostrarDatos(ResultSet rs): muestra el total de registros
    //que contiene el ResultSet. Comprueba su funcionamiento mostrando
    //los datos que hay contenidos en la tabla Usuarios. (Para ello
    //deberás hacer la consulta desde el método main)
    public static void mostrarDatos(ResultSet rs) throws SQLException {
        System.out.println("ALUMNOS:");
        System.out.println("id:  username:  \t      email:   \t     password:        \testado:");
        System.out.println("*******************************************************");
        while (rs.next()) {
            int id = rs.getInt("id");
            String username = rs.getString("usarname");
            String email = rs.getString("email");
            String password = rs.getString("password");
            String estado = rs.getString("estado");
            System.out.println(id + "|\t" + username + "|\t" + email + "|\t" + password + "|\t" + estado);
        }
    }
    //void exportarAFichero(ResultSet rs): crea un fichero que
    //contiene los datos del ResultSet pasado como parámetro.
    //Comprueba su funcionamiento aprovechando la consulta que
    //realizaste para probar el método anterior. El fichero puede ser
    //binario o de texto.
    public static void exportarAFichero(ResultSet rs) throws SQLException {
        String ruta = "D:\\Educa\\daw1_a\\conexionBBDD\\App-Practica\\Alumnos.txt";
        try (FileWriter escribir = new FileWriter(ruta, true)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("usarname");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String estado = rs.getString("estado");
                String escribirArchivo = "id: " + id + " \tusername: " + username + " \temail: " + email + " \tcontraseña: " + password + " \testado: " + estado + "\n";
                escribir.write(escribirArchivo);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //ResultSet consultarUsuarios(Connection con): presenta un menú
    //con cuatro opciones, según se quiera mostrar a los usuarios
    //Activos, Pendientes, Bloqueados o a Todos los usuarios. A
    //continuación hace la consulta correspondiente y, antes de
    //devolver el ResultSet, se lo pasa al método
    //mostrarDatos(ResultSet rs) y pregunta si se desea exportar los
    //datos a un fichero. Si la respuesta es afirmativa, llama al
    //método exportarAFichero(ResultSet rs). Comprueba su
    //funcionamiento.
    public static void consultarUsuario(Connection con) {
        String busqueda;
        String siONo;
        System.out.println("¿Qué usuarios quiere consultar?:\n1.Activos\n2.Pendientes\n3.Bloqueados\n4.Todos");
        int caso = sc.nextInt();
        sc.nextLine();
        try (Statement st = con.createStatement()) {
            switch (caso) {
                case 1:
                    busqueda = "SELECT * FROM Usuarios WHERE estado = 'ACTIVO';";
                    try (ResultSet rs = st.executeQuery(busqueda)) {
                        mostrarDatos(rs);
                    }
                        System.out.println("¿Quiere exportarlo a un fichero?: (s/n)");
                        siONo = sc.nextLine();
                        if (siONo.equalsIgnoreCase("s")){
                            try (ResultSet rs2 = st.executeQuery(busqueda)) {
                                exportarAFichero(rs2);
                            }
                        }
                    break;
                case 2:
                    busqueda = "SELECT * FROM Usuarios WHERE estado = 'PENDIENTE';";
                    try (ResultSet rs = st.executeQuery(busqueda)) {
                        mostrarDatos(rs);
                    }
                    System.out.println("¿Quiere exportarlo a un fichero?: (s/n)");
                    siONo = sc.nextLine();
                    if (siONo.equalsIgnoreCase("s")){
                        try (ResultSet rs2 = st.executeQuery(busqueda)) {
                            exportarAFichero(rs2);
                        }
                    }
                    break;
                case 3:
                    busqueda = "SELECT * FROM Usuarios WHERE estado = 'BLOQUEADO';";
                    try (ResultSet rs = st.executeQuery(busqueda)) {
                        mostrarDatos(rs);
                    }
                    System.out.println("¿Quiere exportarlo a un fichero?: (s/n)");
                    siONo = sc.nextLine();
                    if (siONo.equalsIgnoreCase("s")){
                        try (ResultSet rs2 = st.executeQuery(busqueda)) {
                            exportarAFichero(rs2);
                        }
                    }
                    break;
                case 4:
                    busqueda = "SELECT * FROM Usuarios;";
                    try (ResultSet rs = st.executeQuery(busqueda)) {
                        mostrarDatos(rs);
                    }
                    System.out.println("¿Quiere exportarlo a un fichero?: (s/n)");
                    siONo = sc.nextLine();
                    if (siONo.equalsIgnoreCase("s")){
                        try (ResultSet rs2 = st.executeQuery(busqueda)) {
                            exportarAFichero(rs2);
                        }
                    }
                    break;
            }
        } catch (SQLException e) {
            if (con != null) {
                try {
                    System.err.println("Transaction is being revert back");
                    con.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
        }
    }
//int obtenerÚltimoId(Connection con): realiza una consulta para
//obtener el último Id (el más alto) contenido en la tabla
//Usuarios. Comprueba su funcionamiento realizando una llamada a
//este método desde main e imprimiendo lo que devuelve.
    public static int obtenerUltimoId (Connection con){
        int ultimoId = 0;
        try (Statement st = con.createStatement()) {
            String busqueda = "SELECT id FROM Usuarios;";
            try (ResultSet rs = st.executeQuery(busqueda)) {
                while (rs.next()){
                     int ids = rs.getInt("id");
                ultimoId = ids;
                }
            }
        } catch (SQLException e) {
            if (con != null) {
                try {
                    System.err.println("Transaction is being revert back");
                    con.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
        }
    return ultimoId;
    }

    public static void cargarFichero (Connection con, String nombreFichero){


    }
}