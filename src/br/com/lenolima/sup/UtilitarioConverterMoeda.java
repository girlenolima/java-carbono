
package br.com.lenolima.sup;

import java.text.NumberFormat;
import java.util.Locale;

public class UtilitarioConverterMoeda {

    public static String formatarParaBRL(Double valor) {

        Locale localeBR = new Locale("pt", "BR");
        NumberFormat dinheiroBR = NumberFormat.getCurrencyInstance(localeBR);
        return dinheiroBR.format(valor);

    }

}
