package api;

import java.util.HashMap;

public class CustomHashMap<K, V> extends HashMap<K, V> {
    @Override
    public V put(K key, V value) {
        super.put(key, value);
        return get(key);
    }
}
