package WNprocess;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SomanticRepository implements Serializable {

    private static ConcurrentHashMap<String, SomanticWord> repository = new ConcurrentHashMap<>();
    private static SomanticRepository instance;
    private final static Object lock = new Object();

    private SomanticRepository() {
    }

    public static SomanticRepository getInstance() {
        if (instance == null) {
            instance = new SomanticRepository();
            initialiseKeystore();
        }
        return instance;
    }

    private static void initialiseKeystore() {
        for (Map.Entry<String, SomanticWord> entry : repository.entrySet()) {
            String key = entry.getKey();
            SomanticWord value = entry.getValue();
            SomanticWord.idStore.put(value.getId(), key);
        }
    }

    public SomanticWord put(String key, SomanticWord value) {
        synchronized (lock) {
            if (key != null
                    && !key.isEmpty()
                    && value.getLemma() != null
                    && !value.getLemma().isEmpty()
                    && value.getWords().size() > 0) {
                return repository.put(key, value);
            }
            return null;
        }
    }

    public SomanticWord get(String k) {
        synchronized (lock) {
            return repository.get(k);
        }
    }

    public Set<Map.Entry<String, SomanticWord>> entrySet() {
        return repository.entrySet();
    }

    public int size() {
        synchronized (lock) {
            return repository.size();
        }
    }

    public void loadRepository(ConcurrentHashMap<String, SomanticWord> concurrentHashMap) {
        synchronized (lock) {
            if (concurrentHashMap != null && !concurrentHashMap.isEmpty()) {
                repository = concurrentHashMap;
            }
        }
    }

    public ConcurrentHashMap<String, SomanticWord> getRepositoryToSave() {
        synchronized (lock) {
            return new ConcurrentHashMap<String, SomanticWord>(repository);
        }
    }
}
