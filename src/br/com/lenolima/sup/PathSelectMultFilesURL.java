package br.com.lenolima.sup;

import java.io.File;
import javax.swing.JFileChooser;

public class PathSelectMultFilesURL {

    public static File[] selectFiles() {

        JFileChooser jFileChooser = new JFileChooser();

        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
       // jFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.pdf", "pdf"));
        jFileChooser.setAcceptAllFileFilterUsed(false);
        jFileChooser.setMultiSelectionEnabled(true);
        jFileChooser.showSaveDialog(null);
        
        File[] url = jFileChooser.getSelectedFiles();

        return url;

    }

}
