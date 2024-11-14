package reto2Unidad2BDEmbebidas.ContadoresConSQLite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ContadorEnBDUpdatable {

	public static void main(String[] args) {
		final String claveContador = "contador1";
		final String sqlConsulta = "SELECT nombre,cuenta FROM contadores WHERE nombre=?";
		
		 String url = "jdbc:sqlite:/home/alumno/contadores";
	        Connection connection = null;

	        try {
	            //Class.forName("org.sqlite.JDBC");
	        	connection = DriverManager.getConnection(url);
	            System.out.println("Conectado exitosamente");
			 //Statement consulta = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE); 
	            //para este error: SQLite only supports TYPE_FORWARD_ONLY cursors, quitamos del preparedStatement el ResultSet.TYPE_SCROLL_INSENSITIVE Y EL ResultSet.CONCUR_UPDATABLE

			 PreparedStatement consulta = connection.prepareStatement(sqlConsulta);
			 consulta.setString(1, claveContador);
			 int cuenta = 0;
			 
			 for (int i=0; i<1000;i++) {
				 //ResultSet res = consulta.executeQuery("SELECT nombre,cuenta FROM contador WHERE nombre='" + claveContador + "';");
				 //ResultSet res = consulta.executeQuery();
				 if (consulta.execute()) {  // por ver para qué sirve esto del boolean devuelvo por el execute ¿?
					 ResultSet res = consulta.getResultSet();
					 if (res.next()) {
						 cuenta = res.getInt(2)+1;
						 
						 /* Poner comentario aqui porque el resultSet no puede actualizar valores asi
						 res.updateInt(2, cuenta);
						 res.updateRow();
						 */
					 }
					 //else break;
					 else System.out.println("Error");
				 }
				 //if (i%10==0) System.out.println(i/10 + "%");
			 } //
			 System.out.println("Valor final: " + cuenta);
			 
		 } // try
		 catch (SQLException e) {
			 System.out.println(e.getMessage());
		 }
		 catch (Exception e) {
			 e.printStackTrace();
		 }
	} // main

} // class
