package br.com.lenolima.sup;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class UtilitarioFormatarData {

    public static String getDataAtualFormatadaPTBR() {

        // data/hora atual
        LocalDateTime agora = LocalDateTime.now();

        // formatar a data
        DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm:ss");
        String dataFormatada = formatterData.format(agora);

        // formatar a hora
        //DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");
        //String horaFormatada = formatterHora.format(agora);
        return dataFormatada;

    }

    public static LocalDate converterStringParaDate(String entrada) {

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(entrada, formato);

        return data;

    }

}
