package RiTa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class RiTaWord implements Serializable {

    private Set<String> word = new HashSet<>();
    private String lemma = "";
    private String simpleTag = "";
    private String pennTag = "";
    private Set<RiWoContext> contexts = new HashSet<>();
    private Set<RiTaWord> previous = new HashSet<>();
    private Set<RiTaWord> next = new HashSet<>();
    private List<RiTaWord> synonyms = new ArrayList<>();
    private List<RiTaWord> antonyms = new ArrayList<>();
    private Set<List<RiTaWord>> sentence = new HashSet<>();
    private Set<List<Integer>> affects = new HashSet<>();

    public RiTaWord() {
    }

    public Set getWords() {
        return word;
    }

    public RiTaWord addWord(String word) {
        this.word.add(word);
        return this;
    }

    public String getSimpleTag() {
        return simpleTag;
    }

    public RiTaWord setSimpleTag(String simpleTag) {
        this.simpleTag = simpleTag;
        return this;
    }

    public String getPennTag() {
        return pennTag;
    }

    public RiTaWord setPennTag(String pennTag) {
        this.pennTag = pennTag;
        return this;
    }

    public String getLemma() {
        return lemma;
    }

    public RiTaWord setLemma(String lemma) {
        this.lemma = lemma;
        return this;
    }

    public Set<RiTaWord> getPrevious() {
        return previous;
    }

    public RiTaWord addPrevious(RiTaWord previous) {
        this.previous.add(previous);
        return this;
    }

    public Set<RiTaWord> getNext() {
        return next;
    }

    public RiTaWord addNext(RiTaWord next) {
        this.next.add(next);
        return this;
    }

    public List<RiTaWord> getSynonyms() {
        return synonyms;
    }

    public RiTaWord setSynonyms(List<RiTaWord> synonyms) {
        this.synonyms = synonyms;
        return this;
    }

    public List<RiTaWord> getAntonyms() {
        return antonyms;
    }

    public RiTaWord setAntonyms(List<RiTaWord> antonyms) {
        this.antonyms = antonyms;
        return this;
    }

    public RiTaWord setPrevious(Set<RiTaWord> previous) {
        this.previous = previous;
        return this;
    }

    public RiTaWord setNext(Set<RiTaWord> next) {
        this.next = next;
        return this;
    }

    @Override
    public String toString() {
        return lemma;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.lemma);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RiTaWord other = (RiTaWord) obj;
        if (!Objects.equals(this.lemma, other.lemma)) {
            return false;
        }
        return true;
    }

    public Set<String> getWord() {
        return word;
    }

    public void setWord(Set<String> word) {
        this.word = word;
    }

    public Set<RiWoContext> getContexts() {
        return contexts;
    }

    public void setContexts(Set<RiWoContext> contexts) {
        this.contexts = contexts;
    }

    public void addContext(RiWoContext context) {
        this.contexts.add(context);
    }

    public void addSentence(List<RiTaWord> sentence) {
        this.sentence.add(sentence);
    }

    public Set<List<RiTaWord>> getSentence() {
        return sentence;
    }

    public void print() {
        System.err.println("RiTaWord{" + "word=" + word.toString() + ", lemma=" + lemma + ", simpleTag=" + simpleTag + ", pennTag=" + pennTag + ", contexts=" + contexts.toString() + ", previous=" + previous.toString() + ", next=" + next.toString() + ", synonyms=" + synonyms + ", antonyms=" + antonyms + ", sentence=" + sentence.size() + '}');
    }

    public Set<List<Integer>> getAffects() {
        return affects;
    }

    public void setAffects(Set<List<Integer>> affects) {
        this.affects = affects;
    }

    void addAffect(List<Integer> affect) {
        this.affects.add(affect);
    }
}
