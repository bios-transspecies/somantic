package WordNet;

/**
 * The WordNetSense Table The sense table is responsible for linking together
 * words (in the words table), with definitions (in the synset table). The
 * entries in the sense table are referred as “word-sense pairs” - because each
 * pairing of a wordid with a synset is one complete meaning of a word - a
 * “sense of the word”.
 */
public class WordNetSense {

    private int wordno;
    private int synsetno;
    private int tagcnt;

    public WordNetSense(int wordno, int synsetno, int tagcnt) {
        this.wordno = wordno;
        this.synsetno = synsetno;
        this.tagcnt = tagcnt;
    }

    public int getWordno() {
        return wordno;
    }

    public void setWordno(int wordno) {
        this.wordno = wordno;
    }

    public int getSynsetno() {
        return synsetno;
    }

    public void setSynsetno(int synsetno) {
        this.synsetno = synsetno;
    }

    public int getTagcnt() {
        return tagcnt;
    }

    public void setTagcnt(int tagcnt) {
        this.tagcnt = tagcnt;
    }

    @Override
    public String toString() {
        return "Sense{" + "wordno=" + wordno + ", synsetno=" + synsetno + ", tagcnt=" + tagcnt + '}';
    }

}
