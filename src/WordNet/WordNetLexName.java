package WordNet;

/**
 * The Lexdomains table The Lexdomains table is referenced by the sense table,
 * and is used to define what lexical domain a word-sense pair belongs to. There
 * are 45 lexical domains in the lexdomains table. The lexdomain table
 * therefore, is WordNet’s way of “tagging” a word-sense pair. However, it is
 * quite limited, because a word-sense pair can only belong to ONE lexical
 * domain. The 45 lexical domains include:
 */
public class WordNetLexName {

    private int lexno;
    private String lexname;

    @Override
    public String toString() {
        return "LexName{" + "lexno=" + lexno + ", lexname=" + lexname + ", description=" + description + '}';
    }
    private String description;

    public WordNetLexName(int lexno, String lexname, String description) {
        this.lexno = lexno;
        this.lexname = lexname;
        this.description = description;
    }

    public int getLexno() {
        return lexno;
    }

    public void setLexno(int lexno) {
        this.lexno = lexno;
    }

    public String getLexname() {
        return lexname;
    }

    public void setLexname(String lexname) {
        this.lexname = lexname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
