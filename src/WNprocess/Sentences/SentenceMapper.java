/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WNprocess.Sentences;

import WNprocess.SomanticWord;
import java.util.Comparator;
import java.util.Set;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

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
//
//    @Override
//    public int compareTo(SentenceMapper o) {
//        return o.getCounter().compareTo(o.getCounter());
//    }

    @Override
    public int compare(SentenceMapper o1, SentenceMapper o2) {
        return o1.getCounter() - o2.getCounter();
    }

    @Override
    public int compareTo(SentenceMapper o) {
        return compare(o, this);
    }

}
