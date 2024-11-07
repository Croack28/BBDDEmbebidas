import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Prueba {

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:/home/alumno/bbdd/h2/contadores");
        Statement statement = connection.createStatement();

        try {
        	statement.execute("CREATE TABLE IF NOT EXISTS contadores(nombre VARCHAR(10) PRIMARY KEY, cuenta INT)");
        	
            ResultSet rs = statement.executeQuery("Select * from contadores");
            System.out.println("Tablas en la base de datos:");

            //boolean tablesExist = false;
            while (rs.next()) {
                //tablesExist = true;
                String nombre = rs.getString("nombre");
                int cuenta = rs.getInt("cuenta");      
                System.out.println("Nombre: " + nombre + ", Cuenta: " + cuenta);
            }

            /*
            if (!tablesExist) {
                System.out.println("La tabla 'contadores' no existe. Creando...");
                statement.execute("CREATE TABLE contadores(nombre VARCHAR(10) PRIMARY KEY, cuenta INT)");
                System.out.println("Tabla 'contadores' creada exitosamente.");
            }*/

            rs.close(); 
        } finally {
            statement.close();
            connection.close();
        }
    }
}
