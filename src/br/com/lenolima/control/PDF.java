package br.com.lenolima.control;

import br.com.lenolima.model.Converter;
import br.com.lenolima.sup.PathSave;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PDF implements Converter{
  

    @Override
    public void display(String url) {

        
        Desktop desktop = Desktop.getDesktop();
        
        File file = new File(url);      
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(PDF.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
    @Override
    public void create(List<String> list)  {

        try {
            List<InputStream> pdfs = new ArrayList<>();

            for (String arquivo : list) {
                pdfs.add(new FileInputStream(arquivo));
            }
            
            String url = PathSave.selectPath();          
            OutputStream urlSave = new FileOutputStream(url);
            
            merge(pdfs, urlSave, false);
            
            display(url);
            
        } catch (FileNotFoundException e) {

        }
    }

    @Override
    public void merge(List<InputStream> PDFlist,OutputStream saveUrl,boolean paginate) {

        Document document = new Document(); //Criando documento
        List<PdfReader> readers = new ArrayList<>(); //Variavel de armazenamento dos readers   
     
        try {
    
            int totalPages = 0;           
       
            //Criando os Readers dos PDFS, em resumo pegandos os arquivos que vao ser concatenados.   
            for (InputStream reader : PDFlist) {           
                PdfReader pdfReader = new PdfReader(reader);
                readers.add(pdfReader);
                totalPages += pdfReader.getNumberOfPages();
            }
            
            
            
            // Create a writer for the outputstream (Escritor de documentos)
            PdfWriter writer = PdfWriter.getInstance(document, saveUrl);

            document.open();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            PdfContentByte cb = writer.getDirectContent();

            PdfImportedPage page;
            int currentPageNumber = 0;
            int pageOfCurrentReaderPDF = 0;
            Iterator<PdfReader> iteratorPDFReader = readers.iterator();

            // Loop through the PDF files and add to the output.
            while (iteratorPDFReader.hasNext()) {
                PdfReader pdfReader = iteratorPDFReader.next();

                // Create a new page in the target for each source page.
                while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
                    document.newPage();
                    pageOfCurrentReaderPDF++;
                    currentPageNumber++;
                    page = writer.getImportedPage(pdfReader,
                            pageOfCurrentReaderPDF);
                    cb.addTemplate(page, 0, 0);

                    // Code for pagination.
                    if (paginate) {
                        cb.beginText();
                        cb.setFontAndSize(bf, 9);
                        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, ""
                                + currentPageNumber + " of " + totalPages, 520,
                                5, 0);
                        cb.endText();
                    }
                }
                pageOfCurrentReaderPDF = 0;
            }
            saveUrl.flush();
            document.close();
            saveUrl.close();
        } catch (DocumentException | IOException e) {
        } finally {
            if (document.isOpen()) {
                document.close();
            }
            try {
                if (saveUrl != null) {
                    saveUrl.close();
                }
            } catch (IOException ioe) {
            }
        }

    }

   
}
