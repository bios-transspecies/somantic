package WordNet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryWordNet {

    public RepositoryWordNet() {
        createSynSetList();
        createLexNameList();
        createLexRelList();
        createRelTypeList();
        createSemRelList();
        createSenseList();
        createWordList();
    }

    // Word
    private final Map<String, WordNetWord> word_byLemma = new HashMap<>();
    private final Map<Integer, WordNetWord> word_byId = new HashMap<>();
    // Sense
    private final Map<Integer, WordNetSense> sense_byWordno = new HashMap<>();
    private final Map<Integer, WordNetSense> sense_bySynsetno = new HashMap<>();
    private final Map<Integer, WordNetSense> sense_byTagcnt = new HashMap<>();
    // SynSet
    private final Map<Integer, WordNetSynSet> synSet_byLexNo = new HashMap<>();
    private final Map<Integer, WordNetSynSet> synSet_bySynSet = new HashMap<>();
    // LexRel
    private final Map<Integer, WordNetLexRel> lexRel_TypeNoMap = new HashMap<>();
    private final Map<Integer, WordNetLexRel> lexRel_byWord1 = new HashMap<>();
    private final Map<Integer, WordNetLexRel> lexRel_bySynsetno1 = new HashMap<>();
    // LexName
    private final Map<Integer, WordNetLexName> lexName_byLexno = new HashMap<>();
    private final Map<String, WordNetLexName> lexName_byLexName = new HashMap<>();
    //RelType
    private final Map<Integer, WordNetRelType> relType_byRelTypeno = new HashMap<>();
    private final Map<Character, WordNetRelType> relType_byTypeno = new HashMap<>();
    private final Map<String, WordNetRelType> relType_byDescription = new HashMap<>();
    //SemRel
    private final Map<Integer, WordNetSemRel> semRel_byRelTypeno = new HashMap<>();
    private final Map<Integer, WordNetSemRel> semRel_bySynsetno1 = new HashMap<>();
    private final Map<Integer, WordNetSemRel> semRel_bySynsetno2 = new HashMap<>();

    private void createLexNameList() {
        List<String> file = LoaderWordNetFile.load("lexname.mysql");
        file.stream().map((string) -> string.split("\t")).map((o) -> new WordNetLexName(Integer.parseInt(o[0]), o[1], o[2])).forEachOrdered((o) -> {
            lexName_byLexno.put(o.getLexno(), o);
            lexName_byLexName.put(o.getLexname(), o);
        });
    }

    private void createLexRelList() {
        List<String> file = LoaderWordNetFile.load("lexrel.mysql");
        file.stream().map((line) -> line.split("\t")).map((arr) -> new WordNetLexRel(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]), Integer.parseInt(arr[3]), Integer.parseInt(arr[4]))).forEachOrdered((o) -> {
            lexRel_TypeNoMap.put(o.getRelTypeno(), o);
            lexRel_byWord1.put(o.getWordno1(), o);
            lexRel_bySynsetno1.put(o.getSynsetno1(), o);
        });
    }

    private void createRelTypeList() {
        List<String> file = LoaderWordNetFile.load("reltype.mysql");
        file.stream().map((line) -> line.split("\t")).map((arr) -> new WordNetRelType(Integer.parseInt(arr[0]), arr[1].charAt(0), arr[2])).forEachOrdered((reltype) -> {
            relType_byRelTypeno.put(reltype.getRelTypeno(), reltype);
            relType_byTypeno.put(reltype.getTypeno(), reltype);
            relType_byDescription.put(reltype.getDescription(), reltype);
        });
    }

    private void createSemRelList() {
        List<String> file = LoaderWordNetFile.load("semrel.mysql");
        file.stream().map((line) -> line.split("\t")).map((arr) -> new WordNetSemRel(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]))).forEachOrdered((object) -> {
            semRel_byRelTypeno.put(object.getRelTypeno(), object);
            semRel_bySynsetno1.put(object.getSynsetno1(), object);
            semRel_bySynsetno2.put(object.getSynsetno2(), object);
        });
    }

    private void createSenseList() {
        List<String> file = LoaderWordNetFile.load("sense.mysql");
        file.stream().map((line) -> line.split("\t")).map((arr) -> new WordNetSense(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]))).forEachOrdered((object) -> {
            sense_byWordno.put(object.getWordno(), object);
            sense_bySynsetno.put(object.getSynsetno(), object);
            sense_byTagcnt.put(object.getTagcnt(), object);
        });
    }

    private void createSynSetList() {
        List<String> file = LoaderWordNetFile.load("synset.mysql");
        file.stream().map((line) -> line.split("\t")).map((arr) -> new WordNetSynSet(Integer.parseInt(arr[0]), arr[1], Integer.parseInt(arr[2]))).forEachOrdered((object) -> {
            Map<Integer, WordNetSynSet> element = new HashMap<>();
            synSet_byLexNo.put(object.getLexno(), object);
            synSet_bySynSet.put(object.getSynsetno(), object);
        });
    }

    private void createWordList() {
        List<String> file = LoaderWordNetFile.load("word.mysql");
        file.stream().map((line) -> line.split("\t")).map((arr) -> new WordNetWord(Integer.parseInt(arr[0]), arr[1])).forEachOrdered((object) -> {
            word_byLemma.put(object.getLemma(), object);
            word_byId.put(object.getId(), object);
        });
    }

    public Map<String, WordNetWord> getWord_byLemma() {
        return word_byLemma;
    }

    public Map<Integer, WordNetWord> getWord_byId() {
        return word_byId;
    }

    public Map<Integer, WordNetSense> getSense_byWordno() {
        return sense_byWordno;
    }

    public Map<Integer, WordNetSense> getSense_bySynsetno() {
        return sense_bySynsetno;
    }

    public Map<Integer, WordNetSense> getSense_byTagcnt() {
        return sense_byTagcnt;
    }

    public Map<Integer, WordNetSynSet> getSynSet_byLexNo() {
        return synSet_byLexNo;
    }

    public Map<Integer, WordNetSynSet> getSynSet_bySynSet() {
        return synSet_bySynSet;
    }

    public Map<Integer, WordNetLexRel> getLexRel_TypeNoMap() {
        return lexRel_TypeNoMap;
    }

    public Map<Integer, WordNetLexRel> getLexRel_byWord1() {
        return lexRel_byWord1;
    }

    public Map<Integer, WordNetLexRel> getLexRel_bySynsetno1() {
        return lexRel_bySynsetno1;
    }

    public Map<Integer, WordNetLexName> getLexName_byLexno() {
        return lexName_byLexno;
    }

    public Map<String, WordNetLexName> getLexName_byLexName() {
        return lexName_byLexName;
    }

    public Map<Integer, WordNetRelType> getRelType_byRelTypeno() {
        return relType_byRelTypeno;
    }

    public Map<Character, WordNetRelType> getRelType_byTypeno() {
        return relType_byTypeno;
    }

    public Map<String, WordNetRelType> getRelType_byDescription() {
        return relType_byDescription;
    }

    public Map<Integer, WordNetSemRel> getSemRel_byRelTypeno() {
        return semRel_byRelTypeno;
    }

    public Map<Integer, WordNetSemRel> getSemRel_bySynsetno1() {
        return semRel_bySynsetno1;
    }

    public Map<Integer, WordNetSemRel> getSemRel_bySynsetno2() {
        return semRel_bySynsetno2;
    }

}
