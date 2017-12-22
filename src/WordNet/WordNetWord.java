package WordNet;

/**
 * The Words Table WordNet also has a “words” table, that only has two fields: a
 * wordid, and a “lemma”. The words table is responsible for housing all the
 * lemmas (base words) within the Wordnet Database. There are 146625 entries in
 * this table
 */
public class WordNetWord {

    private int id;
    private String lemma;

    public WordNetWord(int id, String lemma) {
        this.id = id;
        this.lemma = lemma;
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Word{" + "id=" + id + ", lemma=" + lemma + '}';
    }

}
