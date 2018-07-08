package Persistence;

import MainProgram.Interface;
import WNprocess.SomanticRepository;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Persistence {

    private static boolean saving = false;

    public static void save(SomanticRepository repository) throws IOException, IllegalArgumentException {
        File f = new File(Interface.getLibraryFile());
        if (!f.exists()) {
            f.setWritable(true);
            f.createNewFile();
        }
        if (!saving) {
            saving = true;
            Path tmpPath = Paths.get("temporary" + new Date().getTime());
            FileOutputStream tmp = new FileOutputStream(tmpPath.toString());
            ObjectOutputStream out = new ObjectOutputStream(tmp);
            out.writeObject(repository);
            out.close();
            Files.deleteIfExists(Paths.get(Interface.getLibraryFile()));
            Files.copy(tmpPath, Paths.get(Interface.getLibraryFile()));
            Files.delete(tmpPath);
            out.close();
            saving = false;
        } else {
            saving = false;
            throw new IllegalArgumentException(" writting file in progress ");
        }
    }

    public static SomanticRepository load() throws FileNotFoundException, IOException, ClassNotFoundException {
        SomanticRepository riTaRepo = null;
        File f = new File(Interface.getLibraryFile());
        if (f.exists()) {
            FileInputStream fileIn = new FileInputStream(Interface.getLibraryFile());
            ObjectInputStream in = new ObjectInputStream(fileIn);
            riTaRepo = (SomanticRepository) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("loaded repo successfully from file" + Interface.getLibraryFile());
        }
        return riTaRepo;
    }

    public static void saveNewLineInFile(String line) {
        Path pathText = null;
        Charset encoding = Charset.forName("UTF-8");
        try {
            pathText = Paths.get(Interface.getGeneratedSentencesFilePath());
        } catch (Exception e) {
            System.err.println(e);
        }
        if (pathText != null) {
            List<String> newline = new ArrayList<>();
            File file = pathText.toFile();
            if (!file.exists()) {
                try {
                    file.setWritable(true);
                    file.createNewFile();
                } catch (IOException ex) {
                    System.err.println(ex);
                }
            } else {
                try {
                    newline.addAll(Files.readAllLines(pathText, encoding));
                } catch (IOException ex) {
                    System.err.println(ex);
                }
            }
            newline.add(line);
            try {
                Files.write(pathText, newline, encoding);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
}
