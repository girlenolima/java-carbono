package br.com.lenolima.control.recibo;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import java.io.IOException;
import java.net.URL;

public class Imagem {

    public Image criarImagem(String url, int... cordenatas) throws BadElementException, IOException {

        
        URL endereco = this.getClass().getResource(url);
        //Passa o caminho para o PDF.
        Image img = Image.getInstance(endereco);
        //setar o tamanho da imagem
        img.scaleToFit(cordenatas[0], cordenatas[1]);
        //Seta a posiçao da imagem
        img.setAbsolutePosition(cordenatas[2], cordenatas[3]);
        //adicionar a imagem ao pdf
        return img;
               

    }

}
