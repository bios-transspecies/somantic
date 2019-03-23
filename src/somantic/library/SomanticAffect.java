package somantic.library;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author michal
 */
public class SomanticAffect extends ArrayList<Integer> implements Comparable<SomanticAffect> {

    public SomanticAffect() {
    }

    public SomanticAffect(Collection<? extends Integer> c) {
        super(c);
    }

    @Override
    public int compareTo(SomanticAffect o) {
        int accumulator = 0;
        int same = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < o.size(); j++) {
                int r = get(i) - o.get(j);
                if (r == 0) {
                    same++;
                } else {
                    accumulator += get(i) - o.get(j) > 0 ? 1 : -1;
                }
            }
        }
        return accumulator > 0 ? 1 : -1;
    }
}
