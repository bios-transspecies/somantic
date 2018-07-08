package WNprocess;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class SomanticWord implements Serializable {

    private String lemma = "";
    private Set<String> POS = new HashSet<>();
    private Set<String> words = new HashSet<>();
    private Set<SomanticWord> previous = new HashSet<>();
    private Set<SomanticWord> next = new HashSet<>();
    private Set<List<SomanticWord>> sentences = new HashSet<>();
    private Set<List<Integer>> affects = new CopyOnWriteArraySet<>();
    private String description;
    private Set<String> tags = new HashSet<>();

    public SomanticWord() {
    }

    synchronized public Set<String> getPOS() {
        return POS;
    }

    synchronized public void setPOS(Set<String> POS) {
        this.POS = POS;
    }

    synchronized public void addPOS(String POS) {
        this.POS.add(POS);
    }

    synchronized public String getLemma() {
        return lemma;
    }

    synchronized public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    @Override
    public String toString() {
        return lemma;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.lemma);
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
        final SomanticWord other = (SomanticWord) obj;
        if (!Objects.equals(this.lemma, other.lemma)) {
            return false;
        }
        return true;
    }

    synchronized public Set<List<Integer>> getAffects() {
        return affects;
    }

    synchronized public void setAffects(Set<List<Integer>> affects) {
        this.affects = affects;
    }

    synchronized public void addAffect(List<Integer> affect) {
        this.affects.add(affect);
    }

    synchronized public void setDescription(String description) {
        this.description = description;
    }

    synchronized public String getDescription() {
        return description;
    }

    synchronized public Set<String> getWords() {
        return words;
    }

    synchronized public void setWords(Set<String> words) {
        this.words = words;
    }

    synchronized public void addWord(String word) {
        this.words.add(word);
    }

    synchronized public Set<SomanticWord> getPrevious() {
        return previous;
    }

    synchronized public void setPrevious(Set<SomanticWord> previous) {
        this.previous = previous;
    }

    synchronized public void addPrevious(SomanticWord previous) {
        this.previous.add(previous);
    }

    synchronized public Set<SomanticWord> getNext() {
        return next;
    }

    synchronized public void setNext(Set<SomanticWord> next) {
        this.next = next;
    }

    synchronized public void addNext(SomanticWord next) {
        this.next.add(next);
    }

    synchronized public Set<List<SomanticWord>> getSentences() {
        return this.sentences;
    }

    synchronized public void setSentences(Set<List<SomanticWord>> sentence) {
        this.sentences = sentence;
    }

    synchronized public void addSentence(List<SomanticWord> sentence) {
        this.sentences.add(sentence);
    }

    synchronized void addTag(String tag) {
        this.tags.add(tag);
    }

    synchronized Set<String> getTags() {
        return tags;
    }

    synchronized void setTags(Set<String> tags) {
        this.tags = tags;
    }
}