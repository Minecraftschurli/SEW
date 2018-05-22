package main;

import api.Misc;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;


public class CookieClickerView extends JFrame {

    private int level;
    private final int cookiesPerLevel = 10;
    private CookieClickerControl control;
    private CookieClickerButton button;
    private CookieClickerCookieBar bar;
    private final UpgradeList upgrades = new UpgradeList();
    private final ArrayList<UpgradeButton> upgradeButtons = new ArrayList<>();
    private Container upgradeContainer;

    public CookieClickerView(CookieClickerButton button, CookieClickerCookieBar bar, CookieClickerControl control){
        this.control = control;
        this.button = button;
        this.bar = bar;
        
        this.upgrades.put("Multiplier",0);
        this.upgrades.put("Autoclick",0);

        for (int i = 0; i < upgrades.size(); i++) {
            upgradeButtons.add(new UpgradeButton(upgrades.getKey(i)+"\nLevel: "+upgrades.getValue(i)));
        }

        this.upgradeContainer = new Container();
        this.upgradeContainer.setLayout(new GridLayout(4,upgradeButtons.size()/4));

        for (JButton b : upgradeButtons) {
            this.upgradeContainer.add(b);
            b.addActionListener(this.control);
            b.setVisible(false);
        }

        this.button.addActionListener(this.control);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.add(this.upgradeContainer, BorderLayout.EAST);
        this.add(this.button, BorderLayout.CENTER);
        this.add(this.bar, BorderLayout.NORTH);
        this.pack();
        this.setVisible(true);

        this.addWindowListener(this.control);
    }

    public void levelUp(){
        this.level++;
        long exp = Math.round(Math.exp(this.level))*cookiesPerLevel;
        exp = exp == -10 ? Integer.MAX_VALUE : exp;
        int max = (int)Math.min(exp,Integer.MAX_VALUE-1);
        this.bar.setMaximum(max);
        System.out.println("LevelUp"+max);
        if (this.level==3){
            this.upgradeButtons.get(0).setVisible(true);
        }
        if (this.level==5){
            this.upgradeButtons.get(0).setVisible(true);
        }
        if (this.level==10){
            this.upgradeButtons.get(0).setVisible(true);
            this.upgradeButtons.get(1).setVisible(true);
        }
    }
    
    public void upgradeButtonClicked(JButton upgradeButton){
        if (this.upgradeButtons.contains(upgradeButton)){
            int index = this.upgradeButtons.indexOf(upgradeButton);
            this.upgrades.put(index,this.upgrades.getValue(index)+1);
            upgradeButton.setText(upgrades.getKey(index)+"\nLevel: "+upgrades.getValue(index));
            if (index==0&&((this.level>=3&&this.level<5&&upgrades.getValue(index)>=1)||(this.level>=5&&this.level<10&&upgrades.getValue(index)>=2))){
                upgradeButton.setVisible(false);
                this.addCookies(-(10*(int)Math.pow(2,upgrades.getValue(0))));
            }
            if (index==1){
                this.control.addAutoClicker(upgrades.getValue(index));
                this.addCookies(-(100*(int)Math.pow(2,upgrades.getValue(1))));
            }
        }
    }

    @Override
    public String toString() {
        String out =
                "Level: "+this.level+"\n"+
                "Cookies: "+this.bar.getValue()+"\n"+
                "Upgrades: {\n"+this.upgrades.toString()+"\t}";
        return out;
    }

    public void addCookies(int cookies) {
        this.bar.addValue(cookies*(this.upgrades.get("Multiplier")+1));
    }

    public void processSaveFile(File saveFile) {
        try {
            boolean upgradeSection = false;
            String content = Misc.readFile(saveFile);
            content = content.replace("\t","");
            String[] lines = content.split("\n");
            for (String line : lines) {
                if (line.contains("Level")){
                    this.level = Integer.parseInt(line.substring(line.indexOf(':')+2))-1;
                    this.levelUp();
                }
                if (line.contains("Cookies")){
                    this.bar.setValue(Integer.parseInt(line.substring(line.indexOf(':')+2)));
                    System.out.println("test");
                }
                if (upgradeSection){
                    if (line.contains("}")){
                        upgradeSection = false;
                    } else {
                        this.upgrades.put(line.substring(0, line.indexOf(':')), Integer.parseInt(line.substring(line.indexOf(':') + 2)));
                    }
                }
                if (line.contains("Upgrades")){
                    upgradeSection = true;
                }
            }
        } catch (Exception e) {e.printStackTrace();}
        this.button.test();
    }
}
