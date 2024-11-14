package reto2Unidad2BDEmbebidas.BancoTransaccionesSQLite;

public class Banco {
    public final Cuenta[] cuentas;
    private final int saldoInicial;
    private final int númeroDeCuentas;
    private boolean abierto;

    public Banco(int numCuentas, int saldoInicial) {
        this.abierto = true;
    	this.saldoInicial = saldoInicial;
        this.númeroDeCuentas = numCuentas;
        cuentas = new Cuenta[numCuentas];
        for (int i = 0; i < cuentas.length; i++) {
            cuentas[i] = new Cuenta(i, saldoInicial);
        }
    }

    public void transfiere(int origen, int destino, int cantidad) {
        if (cuentas[origen].retiraDinero(cantidad)) {
        	cuentas[destino].meteDinero(cantidad);
        	
        	
        }
        else {
        	System.err.printf("No puedo tranferir %d de %d a %d por falta de fondos\n",
        			cantidad,origen,destino);
        }
    }

    public void comprueba() {
            int saldoTotal = 0;
            for (Cuenta cuenta : cuentas) {
            	saldoTotal += cuenta.getSaldo();
            	System.out.printf("Cuenta %d , saldo %d, parcial %d\n",cuenta.getId(),cuenta.getSaldo(),saldoTotal);
            }
            if (saldoTotal != (númeroDeCuentas * saldoInicial)) {
                System.out.println("¡¡¡¡¡No cuadran las cuentas!!!!");
            } else {
                System.out.println("Balance correcto");
            }
    } // comprueba

    public int getNúmeroDeCuentas() {
        return númeroDeCuentas;
    }

    boolean abierto() {
        return abierto;
    }

    void cierraBanco() {
    	abierto = false;
    }
}
