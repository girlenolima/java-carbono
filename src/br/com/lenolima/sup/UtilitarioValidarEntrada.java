package br.com.lenolima.sup;


public class UtilitarioValidarEntrada {


    public static Double validarSeEumNumero(String entrada) {

        if (!entrada.matches("[0-9-.]+")) {
            return 0d;
        } else {
            return Double.parseDouble(entrada);
        }
    }
    
    
}
