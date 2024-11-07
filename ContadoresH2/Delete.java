import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Delete {

	public static void main(String[] args) throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:h2:/home/alumno/bbdd/h2/contadores");
        Statement statement = connection.createStatement();

        try {
            statement.execute("CREATE TABLE IF NOT EXISTS contadores(nombre VARCHAR(10) PRIMARY KEY, cuenta INT)");

            String insertSQL = "Delete from contadores";
            PreparedStatement insertStatement = connection.prepareStatement(insertSQL);
    
            int rowsInserted = insertStatement.executeUpdate();
            System.out.println("Filas insertadas: " + rowsInserted);

            insertStatement.close(); 

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            statement.close();
            connection.close();
        }

	}

}
