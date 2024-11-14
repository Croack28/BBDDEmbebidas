package reto2Unidad2BDEmbebidas.ContadoresConSQLite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Prueba {

	public static void main(String[] args) {
		prueba1();
		// prueba2();
	}

	private static void prueba2() {
		String url = "jdbc:sqlite:/home/alumno/contadores";
	        Connection con = null;

	        try {
	            //Class.forName("org.sqlite.JDBC");
	            con = DriverManager.getConnection(url);
	            System.out.println("Conectado exitosamente");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void prueba1() {
		final String claveContador = "contador1";
		// La actualización en el propio SQL sí es atómica:
		final String sqlActualización = "UPDATE contadores SET cuenta=0 WHERE nombre='" + claveContador + "';";
		String url = "jdbc:sqlite:/home/alumno/contadores";
        Connection con = null;
	
		 try{
			 Class.forName("org.sqlite.JDBC");
	         con = DriverManager.getConnection(url); 
			 PreparedStatement actualización = con.prepareStatement(sqlActualización);
			 for (int i=0; i<1000;i++) {
				 if (actualización.executeUpdate() != 1) break;
				if (i%10==0) System.out.println(i/10 + "%");
			 }
			 
			 
		 } // try
		 catch (SQLException e) {
			 System.out.println(e.getMessage());
		 }
		 catch (Exception e) {
			 e.printStackTrace();
		 }
	}

}
