package RiTa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RiWoContext implements Serializable {

    private RiTaWord word;
    private List<String> prePennTags = new ArrayList<>();
    private List<String> preSimpleTags = new ArrayList<>();
    private List<String> postPennTags = new ArrayList<>();
    private List<String> postSimpleTags = new ArrayList<>();

    public RiWoContext() {
    }

    public List<String> getPrePennTags() {
        return prePennTags;
    }

    public List<String> getPreSimpleTags() {
        return preSimpleTags;
    }

    public void setPrePennTags(List<String> preTags) {
        this.prePennTags = preTags;
    }

    public void setPreSimpleTags(List<String> preTags) {
        this.preSimpleTags = preTags;
    }

    public RiTaWord getWord() {
        return word;
    }

    public void setWord(RiTaWord word) {
        this.word = word;
    }

    public List<String> getPostPennTags() {
        return postPennTags;
    }

    public void setPostPennTags(List<String> postTags) {
        this.postPennTags = postTags;
    }

    public List<String> getPostSimpleTags() {
        return postSimpleTags;
    }

    public void setPostSimpleTags(List<String> postTags) {
        this.postSimpleTags = postTags;
    }
}
