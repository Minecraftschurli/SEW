package main;

import api.Misc;
import api.encryption.AdvDecrypter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Random;


public class CookieClickerView extends JPanel {

    private CookieClickerControl control;
    private CookieClickerButton button;
    public static final UpgradeList upgrades = new UpgradeList();
    private CookieClickerCookieStats cookieStats;
    private Container upgradeContainer;
    private JMenuItem debugMenu;
    private boolean adminOverride;
    private String adminOverrideString;

    public CookieClickerView(CookieClickerButton button, CookieClickerCookieStats bar, CookieClickerControl control) {
        this.control = control;
        this.button = button;
        this.cookieStats = bar;

        this.adminOverride = false;

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
                return true;
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
                return true;
            }

            @Override
            public void performLoad() {
                if (upgradeLevel > 0) control.addAutoClicker(this.upgradeLevel);
            }

            @Override
            public int getCost() {
                return 100 * (int) Math.pow(2, this.upgradeLevel);
            }
        });
        upgrades.put(new CookieClickerUpgrade.CpSUpgrade("Grandma") {

            @Override
            public double getCpS() {
                return 20 * upgradeLevel;
            }

            @Override
            public void performLevelUp() {
                removeCookies(getCost());
                levelUp(1);
            }

            @Override
            public boolean isAvailable() {
                return true;
            }

            @Override
            public void performLoad() {

            }

            @Override
            public int getCost() {
                return 20 * (int) Math.pow(2, this.upgradeLevel);
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
        this.add(this.cookieStats, BorderLayout.NORTH);
        this.setMinimumSize(new Dimension(400,300));
        this.setVisible(true);
    }

    private long getCookies() {
        return this.cookieStats.getCookies();
    }
    
    public void upgradeButtonClicked(UpgradeButton upgradeButton) {
        upgrades.forEachUpgrade(upgrade -> {
            if (upgrade.button == upgradeButton){
                if (upgrade.isAvailable() && upgrade.getCost() < getCookies()) {
                    upgrade.performLevelUp();
                }
            }
        });
        reload();
    }

    public void addCookies(long cookies, int multiplier) {
        this.cookieStats.addCookies(cookies * multiplier);
        reload();
    }

    public void removeCookies(int cookies) {
        this.cookieStats.addCookies(-cookies);
    }

    public void processSaveFile(File saveFile) {
        try {
            boolean upgradeSection = false;
            boolean saveSection = false;
            boolean adminOverride = false;
            String content = "";
            if (saveFile.getName().endsWith(".txt")) {
                content = Misc.readFile(saveFile);
            } else if (saveFile.getName().endsWith(".cookie")) {
                content = new AdvDecrypter(new Random(201L).nextInt()).decrypt(Misc.readFile(saveFile));
            }
            String[] lines = content.split("\n");
            for (String line : lines) {
                line = line.replace("\t", "");
                if (saveSection) {
                    if (line.contains("]")) {
                        saveSection = false;
                    } else {
                        if (line.contains("Cookies")) {
                            this.cookieStats.setCookies(Long.parseLong(line.substring(line.indexOf(':') + 2)));
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
                } else if (adminOverride) {
                    if (line.contains("}")) {
                        adminOverride = false;
                    }
                    if (line.contains("Cookies")) {
                        if (line.substring(line.indexOf(':') + 2).equals("infinite")) {
                            this.cookieStats.setCookies(-1L);
                        } else {
                            this.cookieStats.setCookies(Long.parseLong(line.substring(line.indexOf(':') + 2)));
                        }
                    }
                }
                if (line.equals("[")) {
                    saveSection = true;
                }
                if (line.contains("AdminOverride")) {
                    this.adminOverride = adminOverride = true;
                    saveAdminOverride(content.substring(content.indexOf("AdminOverride"), content.indexOf('}', content.indexOf("AdminOverride"))));
                }
                if (line.contains("Name")) {
                    this.control.master.setTitle(line.substring(line.indexOf(':') + 2));
                }
            }
        } catch (Exception e) {e.printStackTrace();}
        this.button.test();
        control.addCpsToTimer();
    }

    private void saveAdminOverride(String substring) {
        this.adminOverrideString = substring;
    }

    @Override
    public String toString() {
        String out = "Cookies: " + this.cookieStats.getCookies() + "\n" +
                        "Upgrades: {\n" + upgrades.toString() + "\t}";
        return out;
    }

    public String getAdminOverrideSection() {
        return adminOverrideString != null ? adminOverrideString : "";
    }

    public JMenuItem getMenu() {
        return this.debugMenu;
    }

    public void reload() {
        upgrades.forEachUpgrade(upgrade -> upgrade.button.setEnabled(upgrade.isAvailable() && upgrade.getCost() < getCookies()));
        this.control.master.repaint();
    }
}
