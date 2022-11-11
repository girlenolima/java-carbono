package br.com.lenolima.control.recibo;

import br.com.lenolima.model.recibos.Recibo;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Excel {

    protected File file;
    protected Workbook workbook;

    public List lerExcel(String url) {

        List<Recibo> listaEmissores = new ArrayList();

        try {

            workbook = Workbook.getWorkbook(new File(url));

            //pega a primeira haba da planilha
            Sheet sheet = workbook.getSheet(0);
            //qauntidade de linhas da planilha
            int linhas = sheet.getRows();

            for (int i = 0; i < linhas; i++) {
                Recibo emitente = new Recibo();

                Cell codigo = sheet.getCell(0, i);
                Cell valor = sheet.getCell(1, i);
                Cell empresa = sheet.getCell(2, i);
                Cell referencia = sheet.getCell(3, i);
                Cell data = sheet.getCell(4, i);
                Cell nome = sheet.getCell(5, i);
                Cell cpf = sheet.getCell(6, i);
                Cell endereco = sheet.getCell(7, i);
                Cell enderecoEmpresa = sheet.getCell(8, i);

                emitente.setCodigo(codigo.getContents());
                emitente.setValor(Float.parseFloat(valor.getContents()));
                emitente.setOrigem(empresa.getContents());
                emitente.setReferencia(referencia.getContents());
                emitente.setData(data.getContents());
                emitente.setNome(nome.getContents());
                emitente.setCPF(cpf.getContents());
                emitente.setEndereco(endereco.getContents());
                emitente.setEnderecoEmpresa(enderecoEmpresa.getContents());

                listaEmissores.add(emitente);

            }
            workbook.close();

        } catch (IOException | IndexOutOfBoundsException | NumberFormatException | BiffException ex) {

            JOptionPane.showMessageDialog(null, "PLANILHA FORA DOS PADROES");
        }

        return listaEmissores;

    }

}
