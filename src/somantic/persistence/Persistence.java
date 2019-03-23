package somantic.persistence;

import somantic.state.State;
import somantic.library.SomanticRepository;
import somantic.library.SomanticWord;
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
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.neuroph.core.NeuralNetwork;

public class Persistence {

    private static boolean savingRepository;
    public static AtomicBoolean savingNN = new AtomicBoolean(false);
    private static final Object lock = new Object();
    private static final ExecutorService savingThread = Executors.newSingleThreadExecutor();

    public static void save(SomanticRepository repository) throws IOException, IllegalArgumentException {
        savingThread.execute(()
                -> {
            File f = new File(State.getLibraryFile());
            if (!f.exists()) {
                f.setWritable(true);
                try {
                    f.createNewFile();
                } catch (IOException ex) {
                    State.setMessage(ex.getMessage());
                }
            }
            if (!savingRepository) {
                synchronized (lock) {
                    try {
                        savingRepository = true;
                        Path tmpPath = Paths.get("temporary" + new Date().getTime());
                        FileOutputStream tmp = new FileOutputStream(tmpPath.toString());
                        ObjectOutputStream out = new ObjectOutputStream(tmp);
                        out.writeObject(repository.getRepositoryToSave());
                        out.close();
                        Files.deleteIfExists(Paths.get(State.getLibraryFile()));
                        Files.copy(tmpPath, Paths.get(State.getLibraryFile()));
                        Files.delete(tmpPath);
                        long filesize = Files.size(Paths.get(State.getLibraryFile()));
                        out.close();
                        State.setMessage("SAVED repo size " + filesize / 1024 + "kB with " + repository.size() + " words");
                    } catch (Exception e) {
                        State.setMessage(e.getMessage());
                    } finally {
                        savingRepository = false;
                    }
                }
            } else {
                State.setMessage(" writting file in progress ");
                throw new IllegalArgumentException(" writting file in progress ");
            }
        });
    }

    public static SomanticRepository loadRepository() throws FileNotFoundException, IOException, ClassNotFoundException {
        SomanticRepository somanticRepository = State.getSomanticFacade().getSomanticRepo();
        File f = new File(State.getLibraryFile());
        if (f.exists()) {
            State.setMessage(" loading SOMANTIC REPOSITORY ");
            FileInputStream fileIn = new FileInputStream(State.getLibraryFile());
            ObjectInputStream in = new ObjectInputStream(fileIn);
            if (in != null) {
                ConcurrentHashMap<String, SomanticWord> repo = (ConcurrentHashMap<String, SomanticWord>) in.readObject();
                if (repo != null && !repo.isEmpty()) {
                    somanticRepository.loadRepository(repo);
                }
            }
            in.close();
            fileIn.close();
            String message = "loaded repo - number of words: " + somanticRepository.size() + " successfully from file" + State.getLibraryFile();
            State.setMessage(message);
        }
        return somanticRepository;
    }

    public static String loadLiteraure(String location) throws IOException {
        File f = new File(location);
        if (f.exists()) {
            State.setMessage("loading file: " + f.getName().toLowerCase());
            if (f.getName().toLowerCase().contains(".pdf")) {
                PDFTextStripper pdfStripper = new PDFTextStripper();
                PDDocument pdDoc = PDDocument.load(f);
                pdfStripper.setStartPage(0);
                pdfStripper.setEndPage(pdDoc.getNumberOfPages());
                String parsedText = pdfStripper.getText(pdDoc);
                State.setMessage("ready...");
                return parsedText;
            } else {
                State.setMessage("ready...");
                return Files.lines(f.toPath()).collect(Collectors.joining(" "));
            }
        }
        return null;
    }

    public static void saveNewSentence(String line) {
        savingThread.execute(()
                -> {
            State.setMessage(" saving sentence to file ");
            Path pathText = null;
            Charset encoding = Charset.forName("UTF-8");
            try {
                pathText = Paths.get(State.getGeneratedSentencesFilePath());
            } catch (Exception e) {
                State.setMessage(e.getMessage());
            }
            if (pathText != null) {
                List<String> newline = new ArrayList<>();
                File file = pathText.toFile();
                if (!file.exists()) {
                    try {
                        file.setWritable(true);
                        file.createNewFile();
                    } catch (IOException ex) {
                        State.setMessage(ex.getMessage());
                    }
                } else {
                    try {
                        newline.addAll(Files.readAllLines(pathText, encoding));
                    } catch (IOException ex) {
                        State.setMessage(ex.getMessage());
                    }
                }
                newline.add(line);
                try {
                    Files.write(pathText, newline, encoding);
                } catch (IOException ex) {
                    State.setMessage(ex.getMessage());
                }
            }
        });
    }

    public static void saveNeuralNetwork(NeuralNetwork neuralNetwork, String PERCEPTRONNNET) {
        if (!savingNN.get()) {
            savingThread.execute(() -> {
                // State.setMessage("saving Neural Network");
                savingNN.set(true);
                try {
                    Path tmpPath = Paths.get(ZonedDateTime.now().toLocalDate() + "_" + PERCEPTRONNNET);
                    Files.deleteIfExists(tmpPath);
                    neuralNetwork.save(tmpPath.toString());
                    Files.deleteIfExists(Paths.get(PERCEPTRONNNET));
                    Files.copy(tmpPath, Paths.get(PERCEPTRONNNET));
                    Files.deleteIfExists(tmpPath);
                    //    State.setMessage("saved Neural Network successfully!");
                } catch (IOException ex) {
                    State.setMessage(ex.getMessage());
                } finally {
                    savingNN.set(false);
                }
            });
        }
    }
}
