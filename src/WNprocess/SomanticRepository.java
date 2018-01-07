package WNprocess;

import java.io.Serializable;
import java.util.HashMap;

public class SomanticRepository extends HashMap<String, SomanticWord> implements Serializable {

    private static SomanticRepository instance = null;

    protected SomanticRepository() {
    }

    public static SomanticRepository getInstance() {
        if (instance == null) {
            instance = new SomanticRepository();
        }
        return instance;
    }
}
