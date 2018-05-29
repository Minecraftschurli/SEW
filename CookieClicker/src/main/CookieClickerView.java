package main;

import api.Misc;
import api.encryption.AdvDecrypter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Random;


public class CookieClickerView extends JPanel {

    private int level;
    private final int cookiesPerLevel = 10;
    private CookieClickerControl control;
    private CookieClickerButton button;
    private CookieClickerCookieBar bar;
    private static final UpgradeList upgrades = new UpgradeList();
    private Container upgradeContainer;
    private JMenuItem debugMenu;

    public CookieClickerView(CookieClickerButton button, CookieClickerCookieBar bar, CookieClickerControl control) {
        this.control = control;
        this.button = button;
        this.bar = bar;

        this.debugMenu = new JMenuItem("Debug");
        this.debugMenu.addActionListener(this.control);

        this.setLayout(new BorderLayout());
        
        upgrades.put(new CookieClickerUpgrade("Multiplier") {
            @Override
            public void performLevelUp() {
                removeCookies(getCost());
                levelUp(1);
            }

            @Override
            public boolean isAvailable() {
                return (level >= 3 && level < 5 && this.upgradeLevel < 1) || (level >= 5 && level < 10 && this.upgradeLevel < 2) || (level >= 10);
            }

            @Override
            public void performLoad() {
            }

            @Override
            public int getCost() {
                return 10 * (int) Math.pow(2, this.upgradeLevel);
            }
        });
        upgrades.put(new CookieClickerUpgrade("Autoclick") {

            @Override
            public void performLevelUp() {
                removeCookies(getCost());
                levelUp(1);
                control.addAutoClicker(this.upgradeLevel);
            }

            @Override
            public boolean isAvailable() {
                return level>=10;
            }

            @Override
            public void performLoad() {
                control.addAutoClicker(this.upgradeLevel);
            }

            @Override
            public int getCost() {
                return 100 * (int) Math.pow(2, this.upgradeLevel);
            }
        });

        this.upgradeContainer = new Container();
        this.upgradeContainer.setLayout(new GridLayout(4,upgrades.size()/4));

        upgrades.forEachUpgrade(upgrade -> {
            UpgradeButton b = upgrade.button;
            this.upgradeContainer.add(b);
            b.addActionListener(this.control);
            b.setVisible(true);
        });

        this.button.addActionListener(this.control);

        this.add(this.upgradeContainer, BorderLayout.EAST);
        this.add(this.button, BorderLayout.CENTER);
        this.add(this.bar, BorderLayout.NORTH);
        this.setMinimumSize(new Dimension(400,300));
        this.setVisible(true);
    }

    private int getCookies() {
        return this.bar.getValue();
    }

    public void levelUp() {
        long exp = Math.round(Math.exp(this.level / 2.5)) * cookiesPerLevel;
        exp = exp == -10 ? Integer.MAX_VALUE : exp;
        long last = exp;
        this.level++;
        exp = Math.round(Math.exp(this.level / 2.5)) * cookiesPerLevel;
        exp = exp == -10 ? Integer.MAX_VALUE : exp;
        int max = (int)Math.min(exp,Integer.MAX_VALUE-1);
        this.bar.setMaximum(max);
        System.out.println("Level: " + level + " Next: " + (max - last));
    }
    
    public void upgradeButtonClicked(UpgradeButton upgradeButton) {
        upgrades.forEachUpgrade(upgrade -> {
            if (upgrade.button == upgradeButton){
                if (upgrade.isAvailable() && upgrade.getCost() < getCookies()) {
                    upgrade.performLevelUp();
                }
            }
        });
    }

    public void addCookies(int cookies) {
        this.bar.addValue(cookies * (upgrades.getLevelForName("Multiplier") + 1));
        upgrades.forEachUpgrade(upgrade -> upgrade.button.setEnabled(upgrade.isAvailable()));
    }

    public void removeCookies(int cookies) {
        this.bar.addValue(-cookies);
    }

    public void processSaveFile(File saveFile) {
        try {
            boolean upgradeSection = false;
            boolean saveSection = false;
            String content = "";
            if (saveFile.getName().endsWith(".txt")) {
                content = Misc.readFile(saveFile);
            } else if (saveFile.getName().endsWith(".cookie")) {
                content = new AdvDecrypter(new Random(201L).nextInt()).decrypt(Misc.readFile(saveFile));
            }
            content = content.replace("\t","");
            String[] lines = content.split("\n");
            for (String line : lines) {
                if (saveSection) {
                    if (line.contains("]")) {
                        saveSection = false;
                    } else {
                        if (line.contains("Level")) {
                            this.level = Integer.parseInt(line.substring(line.indexOf(':') + 2)) - 1;
                            this.levelUp();
                        }
                        if (line.contains("Cookies")) {
                            this.bar.setValue(Integer.parseInt(line.substring(line.indexOf(':') + 2)));
                            System.out.println("test");
                        }
                        if (upgradeSection) {
                            if (line.contains("}")) {
                                upgradeSection = false;
                            } else {
                                if (upgrades.containsKey(line.substring(0, line.indexOf(':')))) {
                                    upgrades.set(line.substring(0, line.indexOf(':')), Integer.parseInt(line.substring(line.indexOf(':') + 2)));
                                }
                            }
                        }
                        if (line.contains("Upgrades")) {
                            upgradeSection = true;
                        }
                    }
                }
                if (line.equals("[")) {
                    saveSection = true;
                }
            }
        } catch (Exception e) {e.printStackTrace();}
        this.button.test();
    }

    @Override
    public String toString() {
        String out =
                "Level: "+this.level+"\n"+
                        "Cookies: "+this.bar.getValue()+"\n"+
                        "Upgrades: {\n" + upgrades.toString() + "\t}";
        return out;
    }

    public JMenuItem getMenu() {
        return this.debugMenu;
    }
}
