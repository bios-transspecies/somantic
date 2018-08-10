package WNprocess;

import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class SomanticRepository extends ConcurrentHashMap<String, SomanticWord> implements Serializable {

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
