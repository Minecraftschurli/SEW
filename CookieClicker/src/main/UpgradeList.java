package main;

import java.util.*;
import java.util.function.BiConsumer;

public class UpgradeList {

    private final List<String> keys = new ArrayList<>();
    private final List<Integer> values = new ArrayList<>();
    
    public int size() {
        return this.keys.size();
    }

    public boolean isEmpty() {
        return this.keys.isEmpty();
    }

    public boolean containsKey(Object key) {
        for (String key1 : keys) {
            if (key.equals(key1)){
                return true;
            }
        }
        return false;
    }

    public boolean containsValue(Object value) {
        for (Integer value1 : values) {
            if (value.equals(value1)){
                return true;
            }
        }
        return false;
    }

    public Integer get(Object key) {
        if (!this.containsKey(key))return null;
        for (int i = 0; i < this.size(); i++) {
            if (this.keys.get(i).equals(key)){
                return values.get(i);
            }
        }
        return null;
    }
    
    public Integer getValue(int i){
        return values.get(i);
    }

    public String getKey(int i){
        return keys.get(i);
    }

    public Integer put(String key, Integer value) {
        if (this.containsKey(key)) {
            Integer old = this.get(key);
            for (int i = 0; i < this.size(); i++) {
                if (this.keys.get(i).equals(key)){
                    this.values.set(i,value);
                }
            }
            return old;
        } else {
            this.keys.add(key);
            this.values.add(value);
            return null;
        }
    }

    public void put(int index, Integer value) {
        this.values.set(index,value);
    }

    public Integer remove(Object key) {
        Integer out = this.get(key);
        for (int i = 0; i < this.size(); i++) {
            if (this.keys.get(i).equals(key)){
                this.keys.remove(i);
                this.values.remove(i);
                return out;
            }
        }
        return out;
    }

    public void putAll(Map<? extends String, ? extends Integer> m) {
        m.forEach((BiConsumer<String, Integer>) this::put);
    }

    public void clear() {
        this.keys.clear();
        this.values.clear();
    }
    
    public Collection<Integer> values() {
        return this.values;
    }

    void forEach(BiConsumer<? super String, ? super Integer> action) {
        Objects.requireNonNull(action);
        for (int i = 0; i < this.size(); i++) {
            String k = keys.get(i);
            Integer v = values.get(i);
            action.accept(k, v);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        forEach((s, integer) -> builder.append("\t").append(s).append(": ").append(integer).append("\n"));
        return builder.toString();
    }
}
