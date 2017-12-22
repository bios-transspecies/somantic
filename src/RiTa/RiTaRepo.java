package RiTa;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;

public class RiTaRepo extends HashSet<RiTaWord> implements Serializable {

    public RiTaWord get(String o) {
        RiTaWord res = null;
        Optional<RiTaWord> r = this.stream()
                .filter(w->w.getWord().stream()
                        .filter(a->a==o)
                        .findAny()
                        .isPresent() ||
                        w.getLemma()==o)
                .findAny();
        if(r.isPresent())
            res = r.get();
        return res;
    }

    public RiTaWord get(RiTaWord o) {
        RiTaWord res = null;
        for (Object thisObject : this) {
            RiTaWord thisElement = (RiTaWord) thisObject;
            RiTaWord lookingForElement = (RiTaWord) o;
            if (thisElement.getLemma() == null ? false : thisElement.getLemma().equals(lookingForElement.getLemma())) {
                res = thisElement;
            }
        }
        return res;
    }
}
