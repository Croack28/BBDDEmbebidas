package reto2Unidad2BDEmbebidas.ContadoresConSQLite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ContadorEnBDsoloUpdateSql {

	public static void main(String[] args) {
		final String claveContador = "contador1";
		// La actualización en el propio SQL sí es atómica:
		final String sqlActualización = "UPDATE contadores SET cuenta=cuenta+1 WHERE nombre='" + claveContador + "';";
		String url = "jdbc:sqlite:/home/alumno/contadores";
        Connection con = null;
	
		 try{
			 //Class.forName("org.sqlite.JDBC");
	         con = DriverManager.getConnection(url); 
			 PreparedStatement actualización = con.prepareStatement(sqlActualización);
			 for (int i=0; i<1000;i++) {
				 if (actualización.executeUpdate() != 1) break;
				if (i%10==0) System.out.println(i/10 + "%");
			 }
			 
			 
		 } // try
		 catch (SQLException e) {
			 System.out.println(e.getMessage());

			 e.printStackTrace();
		 }
		 catch (Exception e) {
			 e.printStackTrace();
		 }
	} // main

} // class
