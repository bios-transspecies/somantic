package Persistence;

import MainProgram.Interface;
import WNprocess.SomanticRepository;
import WNprocess.SomanticWord;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.neuroph.core.NeuralNetwork;

public class Persistence {

    private static boolean saving;
    private static final Object lock = new Object();
    private static final ExecutorService neuralNetworkSavingThread = Executors.newSingleThreadExecutor();

    public static void save(SomanticRepository repository) throws IOException, IllegalArgumentException {
        File f = new File(Interface.getLibraryFile());
        if (!f.exists()) {
            f.setWritable(true);
            f.createNewFile();
        }
        if (!saving) {
            synchronized (lock) {
                try {
                    saving = true;
                    Path tmpPath = Paths.get("temporary" + new Date().getTime());
                    FileOutputStream tmp = new FileOutputStream(tmpPath.toString());
                    ObjectOutputStream out = new ObjectOutputStream(tmp);
                    out.writeObject(repository.getRepositoryToSave());
                    out.close();
                    Files.deleteIfExists(Paths.get(Interface.getLibraryFile()));
                    Files.copy(tmpPath, Paths.get(Interface.getLibraryFile()));
                    Files.delete(tmpPath);
                    long filesize = Files.size(Paths.get(Interface.getLibraryFile()));
                    out.close();
                    System.err.println("SAVED repo size " + filesize / 1024 + "kB with " + repository.size() + " words");
                } catch (Exception e) {
                } finally {
                    saving = false;
                }
            }
        } else {
            throw new IllegalArgumentException(" writting file in progress ");
        }
    }

    public static SomanticRepository loadRepository() throws FileNotFoundException, IOException, ClassNotFoundException {
        SomanticRepository somanticRepository = Interface.getSomanticFacade().getSomanticRepo();
        File f = new File(Interface.getLibraryFile());
        if (f.exists()) {
            FileInputStream fileIn = new FileInputStream(Interface.getLibraryFile());
            ObjectInputStream in = new ObjectInputStream(fileIn);
            if (in != null) {
                ConcurrentHashMap<String, SomanticWord> repo = (ConcurrentHashMap<String, SomanticWord>) in.readObject();
                if (repo != null && !repo.isEmpty()) {
                    somanticRepository.loadRepository(repo);
                }
            }
            in.close();
            fileIn.close();
            System.out.println("loaded repo - number of words: " + somanticRepository.size() + " successfully from file" + Interface.getLibraryFile());
        }
        return somanticRepository;
    }

    public static String loadLiteraure(String location) throws IOException {
        File f = new File(location);
        if (f.exists()) {
            if (f.getName().toLowerCase().contains(".pdf")) {
                PDFTextStripper pdfStripper = new PDFTextStripper();
                PDDocument pdDoc = PDDocument.load(f);
                pdfStripper.setStartPage(0);
                pdfStripper.setEndPage(pdDoc.getNumberOfPages());
                String parsedText = pdfStripper.getText(pdDoc);
                return parsedText;
            } else {
                return Files.lines(f.toPath()).collect(Collectors.joining(" "));
            }
        }
        return null;
    }

    public static void saveNewSentence(String line) {
        Path pathText = null;
        Charset encoding = Charset.forName("UTF-8");
        try {
            pathText = Paths.get(Interface.getGeneratedSentencesFilePath());
        } catch (Exception e) {
        }
        if (pathText != null) {
            List<String> newline = new ArrayList<>();
            File file = pathText.toFile();
            if (!file.exists()) {
                try {
                    file.setWritable(true);
                    file.createNewFile();
                } catch (IOException ex) {
                }
            } else {
                try {
                    newline.addAll(Files.readAllLines(pathText, encoding));
                } catch (IOException ex) {
                }
            }
            newline.add(line);
            try {
                Files.write(pathText, newline, encoding);
            } catch (IOException ex) {
            }
        }
    }

    public static void saveNeuralNetwork(NeuralNetwork neuralNetwork, String PERCEPTRONNNET) {
        neuralNetworkSavingThread.execute(() -> neuralNetwork.save(PERCEPTRONNNET));
    }
}
