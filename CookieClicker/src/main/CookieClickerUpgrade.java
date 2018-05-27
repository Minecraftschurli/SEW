package main;

public abstract class CookieClickerUpgrade {

    protected Integer upgradeLevel;
    private final String name;
    public final UpgradeButton button;

    public CookieClickerUpgrade(String name){
        this.upgradeLevel = 0;
        this.button = new UpgradeButton(name+" \nLevel: "+upgradeLevel);
        this.button.setName(name);
        this.name = name;
    }

    protected void levelUp(int level){
        this.upgradeLevel += level;
        this.button.setText(this.name+" \nLevel: "+this.upgradeLevel);
    }

    public abstract void performLevelUp();

    public abstract boolean isAvailable();

    public int getUpgradeLevel() {
        return upgradeLevel;
    }

    public String getName(){
        return name;
    }

    public CookieClickerUpgrade setUpgradeLevel(Integer value) {
        this.upgradeLevel = value;
        this.button.setText(this.name+" \nLevel: "+this.upgradeLevel);
        return this;
    }
}
