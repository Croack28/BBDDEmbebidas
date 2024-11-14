package reto2Unidad2BDEmbebidas.BancoTransacciones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SimulaBancoSQL {
	
	private static Connection CON =null;

	public static void main(String[] args) {
		String url = "jdbc:sqlite:/home/alumno/contadores";
		BancoSQL banco = new BancoSQL(10, 10000);

        
        try {
        	CON = DriverManager.getConnection(url);
        	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        for(int i=0;i<10;i++) {
        	String insertarSQL = "INSERT INTO contadores(nombre, cuenta) VALUES (?, ?);";
            try (PreparedStatement preparedStatement = CON.prepareStatement(insertarSQL)) {
                preparedStatement.setString(1, "contador"+i);
                preparedStatement.setInt(2, 10000);
                preparedStatement.executeUpdate();
                
            } catch (SQLException e) {
            	String updateSQL = "Update contadores set cuenta=10000 where nombre='contador"+i+"';";
            	
            	try {
            		PreparedStatement pstUpdate = CON.prepareStatement(updateSQL);
					pstUpdate.executeUpdate();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

        }
        System.out.println("_________________________________SALDO PRINCIPAL_________________________________\n\n");
        verInformacion();
        ejecutaTransferencias(banco,100,10000);
        
        System.out.println("_________________________________SALDO FINAL_________________________________\n\n");
        verInformacion();
        
	}
	
	public static void verInformacion() {
		String verSQL = "select * from contadores";
		try( PreparedStatement pst = CON.prepareStatement(verSQL)){
			ResultSet rs = pst.executeQuery();
			StringBuilder resultado = new StringBuilder();
			int sumaTotal=0;
			while(rs.next()) {
				resultado.append("Cuenta: "+rs.getString(1)+"  Saldo: "+rs.getInt(2)+"\n");
				sumaTotal+=rs.getInt(2);
			}
			System.out.println(resultado);
			
			if(sumaTotal==100000) {
				System.out.println("Balance total correcto: "+sumaTotal);
			}
			else {
				System.err.println("Balance total Erroneo: "+sumaTotal);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void ejecutaTransferencias(BancoSQL banco, int numTransferencias, int cantidadMáxima ) {
		for (int i = 0; i < numTransferencias; i++) {
			int cuentaOrigen =  (int) (banco.getNúmeroDeCuentas() * Math.random());
			int cuentaDestino = (int) (banco.getNúmeroDeCuentas() * Math.random());
			int cantidad = (int) (cantidadMáxima * Math.random());
			
			boolean puedeHacerseTransaccion=banco.transfiere(cuentaOrigen, cuentaDestino, cantidad);
			try(PreparedStatement pstOrigen = CON.prepareStatement("Update contadores set cuenta=cuenta-"+cantidad+" where nombre= 'contador"+cuentaOrigen+"';");
					PreparedStatement pstDestino = CON.prepareStatement("Update contadores set cuenta=cuenta+"+cantidad+" where nombre= 'contador"+cuentaDestino+"';");) {
				if(puedeHacerseTransaccion) {
					pstOrigen.execute();
					pstDestino.execute();
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(i%100==0) {
				System.out.println("_________________________________SALDO DESPUES DE "+(i+1)+" TRANSACCIONES_________________________________\n\n");
				verInformacion();
				
			}
		}
		
	}
}


















