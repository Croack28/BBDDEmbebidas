package reto2Unidad2BDEmbebidas.Contadores;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ContadorBuggy {
    static final String SQL_CONSULTA = "SELECT cuenta FROM contadores WHERE nombre='contador1'";
    static final String SQL_ACTUALIZA = "UPDATE contadores SET cuenta=? WHERE nombre='contador1'";

    public static void main(String[] args) {
        String url = "jdbc:sqlite:/home/alumno/contadores";
        Connection con = null;

        try {
            //Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(url);
            System.out.println("Conectado exitosamente");

            // Crear la tabla si no existe
            try (Statement stm = con.createStatement()) {
                stm.executeUpdate("CREATE TABLE IF NOT EXISTS contadores(nombre TEXT PRIMARY KEY, cuenta INT);");
            }

            // Insertar una fila con nombre "contador1" si no existe
            String insertarSQL = "INSERT OR IGNORE INTO contadores(nombre, cuenta) VALUES (?, ?);";
            try (PreparedStatement preparedStatement = con.prepareStatement(insertarSQL)) {
                preparedStatement.setString(1, "contador1");
                preparedStatement.setInt(2, 0);
                preparedStatement.executeUpdate();
            }

            // Actualizar el contador
            //Lo he reducido a 10 porque si lo dejo en 1000 tarda muchisimo en cargar
            int cuenta = 0;
            for (int i = 1; i <= 1000; i++) {
                try (Statement consulta = con.createStatement();
                     ResultSet res = consulta.executeQuery(SQL_CONSULTA);
                     PreparedStatement actualiza = con.prepareStatement(SQL_ACTUALIZA)) {
                    
                    if (res.next()) cuenta = res.getInt(1) + 1;
                    
                    actualiza.setInt(1, cuenta);
                    actualiza.executeUpdate();
                }
            }
            System.out.println("Valor final: " + cuenta);
            
            //Prueba de lectura
           PreparedStatement leer = con.prepareStatement("Select * from contadores");
           ResultSet rs = leer.executeQuery();
           StringBuilder stringConsulta = new StringBuilder();
           while(rs.next()) {
        	   stringConsulta.append("Nombre: "+rs.getString(1)+"  Cuenta: "+rs.getString(2)+"\n");
           }
           
           System.out.println(stringConsulta);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
