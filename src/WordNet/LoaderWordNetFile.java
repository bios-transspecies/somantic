package WordNet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import MainProgram.Interface;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.FileSystems;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class designet to load WordNet structure.
 */
public class LoaderWordNetFile {

    public static List<String> load(String filename) {
        filename = Interface.wordnetDir() + FileSystems.getDefault().getSeparator() + filename;
        Path path = Paths.get(filename);
        List<String> lines = new ArrayList<>();
        File file = new File(path.toUri());
        if (file != null && file.exists()) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(file));
                br.lines().forEach((t) -> {
                    lines.add(t);
                });
            } catch (FileNotFoundException ex) {
                Logger.getLogger(LoaderWordNetFile.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            System.err.println("cant find " + filename);
            try {
                file.setWritable(true);
                file.createNewFile();
            } catch (IOException ex) {
                System.err.println("cant make file " + ex);
            }
        }
        System.out.println("WordNet.WordnetFileOperations.load(" + filename + ") returned size: " + lines.size());
        return lines;
    }
}
