package br.com.lenolima.model.adiantamento;

import br.com.lenolima.sup.UtilitarioConverterMoeda;
import br.com.lenolima.sup.UtilitarioFormatarData;
import org.apache.commons.lang3.StringUtils;

public class AdiantamentoTexto {

    public static String criarTextoBase(Adiantamento adiantamento) {

        StringBuilder builder = new StringBuilder();
        builder.append("-------------------------------------------------------------------\n");
        builder.append("Chamado : ").append(adiantamento.getReferencia());
        builder.append("     ");
        builder.append("Previsao : ").append(adiantamento.getVencimento());
        builder.append("\n");
        builder.append("-------------------------------------------------------------------\n");
        builder.append("\n");
        builder.append(limit("Cafe :")).append(UtilitarioConverterMoeda.formatarParaBRL(adiantamento.getCafeDaManha()));
        builder.append("\n");
        builder.append(limit("Almoco :")).append(UtilitarioConverterMoeda.formatarParaBRL(adiantamento.getAlmoco()));
        builder.append("\n");
        builder.append(limit("Jantar :")).append(UtilitarioConverterMoeda.formatarParaBRL(adiantamento.getJantar()));
        builder.append("\n");
        builder.append(limit("Passagens :")).append(UtilitarioConverterMoeda.formatarParaBRL(adiantamento.getPassagens()));
        builder.append("\n");
        builder.append(limit("Deslocamento :")).append(UtilitarioConverterMoeda.formatarParaBRL(adiantamento.getDeslocamento()));
        builder.append("\n");
        builder.append(limit("Ajuda KM :")).append(UtilitarioConverterMoeda.formatarParaBRL(adiantamento.getAjudaKm()));
        builder.append("\n");
        builder.append(limit("Hospedagem :")).append(UtilitarioConverterMoeda.formatarParaBRL(adiantamento.getHospedagem()));
        builder.append("\n");
        builder.append(limit("Extras :")).append(UtilitarioConverterMoeda.formatarParaBRL(adiantamento.getExtras()));
        builder.append("\n");
        builder.append("\n");
        builder.append(limit("Total :"));
        builder.append(UtilitarioConverterMoeda.formatarParaBRL(adiantamento.somarAdiantamento()));
        builder.append("\n");
        builder.append("\n");
        builder.append("-------------------------------------------------------------------\n");
        builder.append("Registrado em : ");
        builder.append(UtilitarioFormatarData.getDataAtualFormatadaPTBR());
        builder.append("\n");
        builder.append("-------------------------------------------------------------------\n");
       
  

        builder.append("\n");

        return builder.toString();

    }

    private static String limit(String dado) {

        int tamanhoFixo = 30;
        return StringUtils.rightPad(dado, tamanhoFixo, ".");

    }
    
    
    



}
