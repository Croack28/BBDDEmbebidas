package reto2Unidad2BDEmbebidas.Contadores;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ContadorBuggy {
    static final String SQL_CONSULTA = "SELECT cuenta FROM contadores WHERE nombre='contador1'";
    static final String SQL_ACTUALIZA = "UPDATE contadores SET cuenta=? WHERE nombre='contador1'";

    public static void main(String[] args) {
        String url = "";
        Connection con = null;

        //Esta clase coge lo que haya dentro del archivo config.ini con un fileinputstream
        Properties propiedades = new Properties();
        
        try(FileInputStream input = new FileInputStream("config.ini")) {
        	//Aqui lo carga y la clase properties tiene todo lo que tenga el archivo config.ini
        	propiedades.load(input);
        	//Aqui pilla el contenido de una variable que se llamada db.url que contiene la direccion de la base de datos
        	url=propiedades.getProperty("db.url");
        	
        } catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        
        try {
        	
        	
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
            for (int i = 1; i <= 100; i++) {
                try (Statement consulta = con.createStatement();
                     ResultSet res = consulta.executeQuery(SQL_CONSULTA);
                     PreparedStatement actualiza = con.prepareStatement(SQL_ACTUALIZA)) {
                    
                    if (res.next()) cuenta = res.getInt(1) + 1;
                    
                    actualiza.setInt(1, cuenta);
                    actualiza.executeUpdate();
                }
            }
            System.out.println("Valor final: " + cuenta);
            

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
