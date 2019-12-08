package somantic.library;

import java.util.ArrayList;

/**
 *
 * @author michal
 */
public class SomanticSentence extends ArrayList<SomanticWord> implements Comparable<SomanticSentence> {

    @Override
    public int compareTo(SomanticSentence o) {
        System.out.println(o);
        if (containsAll(o) && size() == o.size()) {
            return 0;
        }
        return size() > o.size() ? 1 : -1;
    }

}
