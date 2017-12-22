package MainProgram;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

class Library {

    List<String> lines;
    OpenOption[] options;
    Path pathLibrary;
    Charset encoding;
    File file;

    public Library() {
    }

    public void load() {
        encoding = StandardCharsets.UTF_8;
        pathLibrary = Paths.get(Interface.getLibraryFile());
        lines = new ArrayList<>();
        try {
            file = pathLibrary.toFile();
        } catch (Exception e) {

        }
        if (!file.exists()) {
            try {
                file.setWritable(true);
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            lines.addAll(Files.readAllLines(Paths.get(Interface.getLibraryFile()), encoding));
        } catch (IOException ex) {
            Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void zapisz(String word, String matrix) {
        word = clear(word);
        if (word.length() > 0) {
            lines.add(word + ":" + matrix);
            try {
                Files.write(pathLibrary, lines, Charset.forName("UTF-8"));
                file = pathLibrary.toFile();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

    public void zapiszPlik(String line) {
        Path pathText = null;
        try {
            pathText = Paths.get(Interface.getGeneratedSentencesFilePath());
        } catch (Exception e) {
            System.err.println(e);
        }
        if (pathText != null) {
            List<String> newline = new ArrayList<>();
            file = pathText.toFile();
            if (!file.exists()) {
                try {
                    file.setWritable(true);
                    file.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    newline.addAll(Files.readAllLines(pathText, encoding));
                } catch (IOException ex) {
                    Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            newline.add(clear(line));
            try {
                Files.write(pathText, newline, Charset.forName("UTF-8"));
            } catch (IOException ex) {
                Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String clear(String word) {
        String[] words = word.split(" ");
        int i = 0;
        for (String wordPart : words) {
            words[i++] = wordPart.replaceAll("[^a-zA-Z]", "").toLowerCase();
        }
        return String.join(" ", words);
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    void postOnBlog(String text) {
        Interface.setWords("");
    }

}
