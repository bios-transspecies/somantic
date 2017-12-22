/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WordNet;

/**
 *
 * @author micha
 */
public class WordNetLexRel {

    private int wordno1;
    private int synsetno1;
    private int wordno2;
    private int synsetno2;
    private int reltypeno;

    public WordNetLexRel(int wordno1, int synsetno1, int wordno2, int synsetno2, int reltypeno) {
        this.wordno1 = wordno1;
        this.synsetno1 = synsetno1;
        this.wordno2 = wordno2;
        this.synsetno2 = synsetno2;
        this.reltypeno = reltypeno;
    }

    public int getWordno1() {
        return wordno1;
    }

    public void setWordno1(int wordno1) {
        this.wordno1 = wordno1;
    }

    public int getSynsetno1() {
        return synsetno1;
    }

    public void setSynsetno1(int synsetno1) {
        this.synsetno1 = synsetno1;
    }

    public int getWordno2() {
        return wordno2;
    }

    public void setWordno2(int wordno2) {
        this.wordno2 = wordno2;
    }

    public int getSynsetno2() {
        return synsetno2;
    }

    public void setSynsetno2(int synsetno2) {
        this.synsetno2 = synsetno2;
    }

    public int getRelTypeno() {
        return reltypeno;
    }

    public void setReltypeno(int reltypeno) {
        this.reltypeno = reltypeno;
    }

    @Override
    public String toString() {
        return "LexRel{" + "wordno1=" + wordno1 + ", synsetno1=" + synsetno1 + ", wordno2=" + wordno2 + ", synsetno2=" + synsetno2 + ", reltypeno=" + reltypeno + '}';
    }

}
