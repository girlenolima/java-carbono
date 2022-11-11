package br.com.lenolima.control.recibo;

import br.com.lenolima.model.recibos.Recibo;
import br.com.lenolima.sup.recibos.FormatoBRL;
import java.text.SimpleDateFormat;

public class Texto {

    Recibo emitente;

    public Texto(Recibo emitente) {
        this.emitente = emitente;
    }

    public String criarTextoBase() {

        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");

        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        builder.append("\n");
        builder.append("RECIBO N° ");
        builder.append(emitente.getCodigo());
        builder.append("     VALOR: ");
        builder.append(FormatoBRL.forBRL(emitente.getValor()));
        builder.append("     DATA: ");
        builder.append(emitente.getData());
        builder.append("\n");
        builder.append("\n");
        builder.append("RECEBI DE: ");
        builder.append(emitente.getOrigem());
        builder.append("\n");
        builder.append("ENDE.....: ");
        builder.append(emitente.getEnderecoEmpresa());
        builder.append("\n");
        builder.append("VALOR DE.: ");
        builder.append(FormatoBRL.forBRL(emitente.getValor()));
        builder.append("(");
        builder.append(Escrita.valorPorExtenso(emitente.getValor()));
        builder.append(")");
        builder.append("\n");
        builder.append("REF......: ");
        builder.append(emitente.getReferencia());
        builder.append("\n");
        builder.append(".................................................................");
        builder.append("\n");
        builder.append("POR SER VERDADE,FIRMO O PRESENTE RECIBO.");
        builder.append("\n");
        builder.append("EMITENTE.: ");
        builder.append(emitente.getNome());
        builder.append("\n");
        builder.append("CPF......: ");
        builder.append(emitente.getCPF());
        builder.append("\n");
        builder.append("ENDE.....: ");
        builder.append(emitente.getEndereco());
        builder.append("\n");

        return builder.toString();

    }

}
