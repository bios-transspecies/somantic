package somantic.library;

import java.util.ArrayList;

/**
 *
 * @author michal
 */
public class SomanticSentence extends ArrayList<SomanticWord> implements Comparable<SomanticSentence> {

    @Override
    public int compareTo(SomanticSentence o) {
        if (containsAll(o)) {
            return 0;
        }
        return size() > o.size() ? 1 : -1;
    }

}
