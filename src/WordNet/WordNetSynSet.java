package WordNet;

/* The Synset Table The synsets table is one of the most important tables in the database. 
    * It is responsible for housing all the definitions within WordNet. 
    * Each row in the synset table has a synsetid, a definition, a pos (parts of speech field) 
    * and a lexdomainid (which links to the lexdomain table) There are 117373 synsets in the WordNet Database.
    * https://stackoverflow.com/questions/18278219/wordnet-sql-explanation
 */
public class WordNetSynSet {

    private int synsetno;
    private String definition;
    private int lexno;

    public WordNetSynSet(int synsetno, String definition, int lexno) {
        this.synsetno = synsetno;
        this.definition = definition;
        this.lexno = lexno;
    }

    public int getSynsetno() {
        return synsetno;
    }

    public void setSynsetno(int synsetno) {
        this.synsetno = synsetno;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public int getLexno() {
        return lexno;
    }

    public void setLexno(int lexno) {
        this.lexno = lexno;
    }

    @Override
    public String toString() {
        return "SynSet{" + "synsetno=" + synsetno + ", definition=" + definition + ", lexno=" + lexno + '}';
    }

}
