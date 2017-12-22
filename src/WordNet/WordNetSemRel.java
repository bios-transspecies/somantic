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
public class WordNetSemRel {

    private int synsetno1;
    private int synsetno2;
    private int reltypeno;

    public WordNetSemRel(int synsetno1, int synsetno2, int reltypeno) {
        this.synsetno1 = synsetno1;
        this.synsetno2 = synsetno2;
        this.reltypeno = reltypeno;
    }

    public int getSynsetno1() {
        return synsetno1;
    }

    public void setSynsetno1(int synsetno1) {
        this.synsetno1 = synsetno1;
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
        return "SemRel{" + "synsetno1=" + synsetno1 + ", synsetno2=" + synsetno2 + ", reltypeno=" + reltypeno + '}';
    }

}
