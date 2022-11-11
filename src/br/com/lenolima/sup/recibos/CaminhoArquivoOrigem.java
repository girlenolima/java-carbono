
package br.com.lenolima.sup.recibos;

import javax.swing.JFileChooser;


public class CaminhoArquivoOrigem {
    
    public static String selecionarPath(Boolean item) {

        JFileChooser jFileChooser = new JFileChooser();
        
        if (item == true) {
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        } else {
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        }
        
        jFileChooser.setAcceptAllFileFilterUsed(false);
        jFileChooser.showSaveDialog(null);
        String url = jFileChooser.getSelectedFile().getAbsolutePath();
        
        return url;
        
    }
    
}
