package br.com.lenolima.control.recibo;

import br.com.lenolima.control.interfaceBusines;
import br.com.lenolima.model.recibos.Recibo;
import java.util.ArrayList;
import java.util.List;



public class Busines implements interfaceBusines {

    @Override
    public void CriarPDF(String origemPlanilha, String destinoPDF) {

 
        List<Recibo> emitentes = new ArrayList<>();
        Excel c = new Excel();

        emitentes = c.lerExcel(origemPlanilha);

        for (int i = 0; i < emitentes.size(); i++) {

            Texto texto = new Texto(emitentes.get(i));
            String tx = texto.criarTextoBase();

            Pdf pdf = new Pdf(tx);
            pdf.criaPDF(13, emitentes.get(i).getNome(), destinoPDF);

        }
  
    }

}
