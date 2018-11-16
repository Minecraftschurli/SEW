import api.RandomArray;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
class MasterMindGame implements Serializable {

    final List<Color> COLORS = new ArrayList<>();
    final String name;
    final int colors;
    final int slots;
    final int maxTries;
    private final boolean debug;
    private final int[] computerNumbers;
    int currentTry;
    int currentColor;
    private int[] userNumbers;
    private Dimension windowSize;
    private MasterMindGUI gui;

    /**
     * creates the game object
     *
     * @param colors int
     * @param slots  int
     * @param tries  int
     * @param debug  int
     */
    MasterMindGame(int colors, int slots, int tries, int[] input, boolean preventDouble, boolean debug) {
        this.name = "Mastermind";
        this.windowSize = new Dimension(1080, 720);
        this.colors = colors;
        this.slots = slots;
        this.maxTries = tries;
        this.debug = debug;
        this.currentTry = 0;
        this.computerNumbers = input != null && input.length == slots && valuesSmallerThan(colors, input) ? input : new RandomArray.Generator(preventDouble).getIntArray(slots, 0, colors);
        this.userNumbers = new int[slots];

        populateColors(colors, COLORS);
        if (this.debug) {
            for (int computerNumber : computerNumbers)
                System.out.print(computerNumber);
        }
    }

    /**
     * @param i
     */
    static void populateColors(final int i, List<Color> colors) {
        double interval = 360.0 / (i);
        for (float x = 0; x < 360; x += interval)
            colors.add(Color.getHSBColor(x / 360, 1, 1));
    }

    @Contract(pure = true)
    private boolean valuesSmallerThan(int colors, @NotNull int[] input) {
        for (int i : input) {
            if (i >= colors)
                return false;
        }
        return true;
    }

    /**
     * binds the gui to the game element
     *
     * @param gui gui to bind to the game
     */
    void bind(MasterMindGUI gui) {
        this.gui = gui;
    }

    /**
     * @param e
     */
    void onCheck(ActionEvent e) {
        if (currentTry > maxTries) {
            JOptionPane.showMessageDialog(null, "<html><font color=#FF0000 style=bold>YOU HAVE LOST!</font></html>", "YOU HAVE LOST!", JOptionPane.PLAIN_MESSAGE);
            for (int computerNumber : computerNumbers) {
                System.out.print(computerNumber + " ");
            }
            System.exit(0);
        }
        for (int i = 0; i < userNumbers.length; i++) {
            userNumbers[i] = gui.colorsPerTry[currentTry][i].getValue();
        }
        int[] blackAndWhite = testBlackAndWhite(new int[][]{computerNumbers, userNumbers});
        gui.setBlackAndWhite(blackAndWhite[0], blackAndWhite[1]);
        tryCounter();
        gui.repaint();
        if (blackAndWhite[0] == slots) {
            JOptionPane.showMessageDialog(null, "<html><font color=#00CC00 style=bold>YOU HAVE WON!</font></html>", "YOU HAVE WON!", JOptionPane.PLAIN_MESSAGE);
            System.exit(0);
        }
        //JOptionPane.showMessageDialog(null, "<html><background color=#DDAABB><font color=#FF0000>"+blackAndWhite[0]+"</font> <font color=#FFFFFF>"+blackAndWhite[1]+"</font></html>", "", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * TODO comment
     */
    private void tryCounter() {
        currentTry++;
    }

    /**
     * TODO comment
     * TODO redo
     *
     * @param numbers int[][]
     * @return int[]
     */
    @NotNull
    @Contract(value = "_ -> new", pure = true)
    private int[] testBlackAndWhite(@NotNull int[][] numbers) {
        List<Integer> cNumbers = new ArrayList<>();
        List<Integer> uNumbers = new ArrayList<>();
        for (int i : numbers[0]) {
            cNumbers.add(i);
        }
        for (int i : numbers[1]) {
            uNumbers.add(i);
        }
        int black = 0;
        int white = 0;
        for (int i = 0; i < uNumbers.size(); i++) {
            int pos = cNumbers.indexOf(uNumbers.get(i));
            if (pos >= 0) {
                if (pos == i) black++;
                else white++;
            }
        }
        return new int[]{black, white};
    }

    public synchronized void save(File file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException ignored) {
        }
    }
}
