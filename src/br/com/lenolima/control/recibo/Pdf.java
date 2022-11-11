package br.com.lenolima.control.recibo;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Pdf {

    String texto;

    public Pdf(String texto) {
        this.texto = texto;
    }

    

    public void criaPDF(Integer fonte,String nomeArquivo,String Url) {

        Imagem  insImg = new Imagem();
        Document documentoPDF = new Document();


        try {

            //Criando uma fonte  
            Font font2 = FontFactory.getFont(FontFactory.COURIER, fonte);
            //define o tipo de pagina.   
            documentoPDF.setPageSize(PageSize.A4);
            //Modifica as margesn do documento. ORDEM: Direita, Esquerda,Cima,Baixo
            documentoPDF.setMargins(30, 53, 50, 0);
            //cria uma instancia do documento e da o nome ao pdf
            PdfWriter.getInstance(documentoPDF, new FileOutputStream( Url + "\\" +nomeArquivo +".pdf"));
      
            //abre o documento
            documentoPDF.open();
 
            
            //Primeira Imagem do logo da empresa.
            documentoPDF.add(insImg.criarImagem("/br/com/lenolima/img/recibos/tituloCds.png", 556, 400, 26, 745));
            //Segunda Imagem de uma listra abaixo do titulo e data
            documentoPDF.add(insImg.criarImagem("/br/com/lenolima/img/recibos/divisor01.jpg", 550, 400, 30, 720));
            //Assinatura taanho,tamnhao, (esquerda- direita) , (altura)
            documentoPDF.add(insImg.criarImagem("/br/com/lenolima/img/recibos/assinaturaRecibo.jpg", 200, 400, 200, 450));
            //Segunda Imagem de uma listra no final do documento
            documentoPDF.add(insImg.criarImagem("/br/com/lenolima/img/recibos/divisor01.jpg", 550, 400, 30, 400));
        
            
            
            //PARAGRAFO
            Paragraph a1 = new Paragraph(texto, font2);
            a1.setSpacingBefore(1);// Aumenta espaçao antes do paragrafo para alinhar abaixo da imagem
            //a1.setIndentationLeft(18);//Margem esquerda do paragrafo.
            documentoPDF.add(a1); //Adicona o paragrafo no documento criado

        } catch (DocumentException | IOException de) {

            JOptionPane.showMessageDialog(null, de);
        } finally {
            documentoPDF.close();
        }

    }

}
