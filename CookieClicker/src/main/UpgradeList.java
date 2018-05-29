package main;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class UpgradeList {

    private final ArrayList<CookieClickerUpgrade> upgrades = new ArrayList<>();
    
    public int size() {
        return this.upgrades.size();
    }

    public boolean isEmpty() {
        return this.upgrades.isEmpty();
    }

    public boolean containsKey(String key) {
        for (CookieClickerUpgrade key1 : upgrades) {
            if (key.equals(key1.getName())){
                return true;
            }
        }
        return false;
    }

    public boolean containsValue(Integer value) {
        for (CookieClickerUpgrade value1 : upgrades) {
            if (value.equals(value1.getUpgradeLevel())){
                return true;
            }
        }
        return false;
    }

    public Integer getLevelForName(String name) {
        if (!this.containsKey(name))return null;
        for (int i = 0; i < this.size(); i++) {
            if (this.upgrades.get(i).getName().equals(name)){
                return upgrades.get(i).getUpgradeLevel();
            }
        }
        return null;
    }
    
    public Integer getLevelForIndex(int i){
        return upgrades.get(i).getUpgradeLevel();
    }

    public String getNameForIndex(int i){
        return upgrades.get(i).getName();
    }

    public Integer put(CookieClickerUpgrade upgrade) {
        if (this.containsKey(upgrade.getName())) {
            Integer old = this.getLevelForName(upgrade.getName());
            for (int i = 0; i < this.size(); i++) {
                if (this.upgrades.get(i) == upgrade){
                    this.upgrades.set(i,upgrade);
                }
            }
            return old;
        } else {
            this.upgrades.add(upgrade);
            return null;
        }
    }

    public void set(String name, Integer level) {
        forEachUpgrade(upgrade -> {
            if (upgrade.getName().equals(name)){
                upgrade.setUpgradeLevel(level);
                upgrade.performLoad();
            }
        });
    }

    public Integer remove(String key) {
        Integer out = this.getLevelForName(key);
        for (int i = 0; i < this.size(); i++) {
            if (this.upgrades.get(i).equals(key)){
                this.upgrades.remove(i);
                return out;
            }
        }
        return out;
    }

    public void clear() {
        this.upgrades.clear();
    }

    public void forEach(BiConsumer<? super String, ? super Integer> action) {
        Objects.requireNonNull(action);
        for (int i = 0; i < this.size(); i++) {
            String name = upgrades.get(i).getName();
            Integer level = upgrades.get(i).getUpgradeLevel();
            action.accept(name, level);
        }
    }

    public void forEachUpgrade(Consumer<? super CookieClickerUpgrade> action) {
        Objects.requireNonNull(action);
        for (int i = 0; i < this.size(); i++) {
            action.accept(get(i));
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        forEach((s, integer) -> builder.append("\t").append(s).append(": ").append(integer).append("\n"));
        return builder.toString();
    }

    public CookieClickerUpgrade get(int index) {
        return upgrades.get(index);
    }
}
