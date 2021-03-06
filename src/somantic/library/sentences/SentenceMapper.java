/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package somantic.library.sentences;

import somantic.library.SomanticWord;
import java.util.Comparator;
import java.util.Set;

/**
 *
 * @author michal
 */
public class SentenceMapper implements Comparator<SentenceMapper>, Comparable<SentenceMapper> {

    private int counter;
    private Set<SomanticWord> sentence;

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public Set<SomanticWord> getSentence() {
        return sentence;
    }

    public void setSentence(Set<SomanticWord> sentence) {
        this.sentence = sentence;
    }

    @Override
    public int compare(SentenceMapper o1, SentenceMapper o2) {
        return o1.getCounter() - o2.getCounter();
    }

    @Override
    public int compareTo(SentenceMapper o) {
        return compare(o, this);
    }

}
