package reto2Unidad2BDEmbebidas.ContadoresConSQLite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ContadorEnBD {

	public static void main(String[] args) {
		final String sqlConsulta = "SELECT cuenta FROM contadores WHERE nombre=?;";
		final String sqlActualización = "UPDATE contadores SET cuenta=? WHERE nombre=?;";
		final String claveContador = "contador1";
		String url = "jdbc:sqlite:/home/alumno/contadores";
		Connection connection=null;

		
		try {
			Class.forName("org.sqlite.JDBC");
			connection =DriverManager.getConnection(url);
			System.out.println("Conexion exitosa");
			PreparedStatement consulta = connection.prepareStatement(sqlConsulta);
			PreparedStatement actualización = connection.prepareStatement(sqlActualización);
			int cuenta = 0;

			consulta.setString(1, claveContador);
			actualización.setString(2, claveContador);
			for (int i = 0; i < 100; i++) {
				ResultSet res = consulta.executeQuery();
				if (res.next()) {
					cuenta = res.getInt(1) + 1;
					actualización.setInt(1, cuenta);
					//aqui tenemos que cambiar el executeQuery por el execute porque un update no devuelve nada
					actualización.execute();
				}
				// else break;
				else
					System.out.println("Error");
				// if (i%10==0) System.out.println(i/10 + "%");
			}
			connection.close();
			System.out.println("Valor final: " + cuenta);

		} // try
		catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // main

} // class
