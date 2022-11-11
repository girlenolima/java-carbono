package br.com.lenolima.sup;


import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PathSave {

    public static String selectPath() {

        JFileChooser jFileChooser = new JFileChooser();

        jFileChooser.setAcceptAllFileFilterUsed(false);
        jFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.pdf", "pdf"));
          
        
        jFileChooser.showSaveDialog(null);
        String url = jFileChooser.getSelectedFile().getAbsolutePath() + ".pdf";

        return url;

       

    }

}
