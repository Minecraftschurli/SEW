import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
class MasterMindGUI extends JPanel implements Serializable {

    final MasterMindGame game;
    final int offsetX;
    final int offsetY;
    final int slotRadius;
    Pair<Shape, Integer>[][] colorsPerTry;
    private List<Pair<Integer, Integer>> blackAndWhitePerTry = new ArrayList<>();
    private Map<Shape, Color> colorChooser = new HashMap<>();

    /**
     * @param game
     */
    @SuppressWarnings("unchecked")
    MasterMindGUI(MasterMindGame game) {
        this.game = game;
        colorsPerTry = new Pair[this.game.maxTries][this.game.slots];
        this.offsetX = 10;
        this.offsetY = 10;
        this.slotRadius = 15;
        for (int i = 0; i < this.game.maxTries; i++) {
            for (int j = 0; j < this.game.slots; j++) {
                Shape s = new Ellipse2D.Double(offsetX + (10 + slotRadius * 2) * j, offsetY + (10 + slotRadius * 2) * i + (slotRadius * 2 + 10), (slotRadius * 2), (slotRadius * 2));
                colorsPerTry[i][j] = new Pair<>(s, -1);
            }
        }
        AtomicInteger i = new AtomicInteger(0);
        game.COLORS.forEach(color -> colorChooser.put(new Ellipse2D.Double(offsetX + ((slotRadius * 2 + 1) * i.getAndIncrement()), offsetY, (slotRadius * 2), (slotRadius * 2)), color));
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Pair<Shape, Integer>[] pairs = colorsPerTry[game.currentTry];
                for (int i = 0; i < pairs.length; i++) {
                    Pair<Shape, Integer> colors = pairs[i];
                    if (colors.getKey().contains(e.getPoint())) {
                        pairs[i] = new Pair<>(colors.getKey(), game.currentColor);
                    }
                }
                colorChooser.forEach((shape, color) -> {
                    if (shape.contains(e.getPoint())) {
                        game.currentColor = game.COLORS.indexOf(color);
                    }
                });
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        this.setBackground(new Color(0x432A00));
        showFrame();
    }

    /**
     *
     */
    protected void showFrame() {
        JFrame frame = new JFrame(game.name);
        frame.add(this);
        JButton b = new JButton("CHECK");
        b.addActionListener(game::onCheck);
        frame.add(b, BorderLayout.SOUTH);
        Dimension dim = new Dimension((slotRadius * 2 + offsetX) * (game.slots + 2) + offsetX, (slotRadius * 2 + offsetY) * (game.maxTries + 3) + offsetY);
        frame.setSize(dim);
        frame.setMinimumSize(dim);
        frame.setMaximumSize(dim);
        frame.setResizable(false);
        frame.setBackground(new Color(0x432A00));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * @param g
     */
    @Override
    protected final void paintComponent(Graphics g) {
        if (g instanceof Graphics2D) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setBackground(new Color(0x432A00));
            super.paintComponent(g2d);
            paintColorChooser(g2d);
            paintGameBoard(g2d);
            paintBlackWhite(g2d);
        }
    }

    /**
     * @param g
     */
    private void paintColorChooser(@NotNull Graphics2D g) {
        Color c = g.getColor();
        colorChooser.forEach((shape, color) -> {
            g.setColor(color);
            g.fill(shape);
        });
        g.setColor(c);
    }

    /**
     * @param g
     */
    private void paintBlackWhite(@NotNull Graphics2D g) {
        int offsetX = (this.offsetX + game.slots * (10 + slotRadius * 2));
        int offsetY = this.offsetY + 10;
        Color c = g.getColor();
        AtomicInteger i = new AtomicInteger(0);
        blackAndWhitePerTry.forEach(blackWhite -> {
            g.setColor(Color.black);
            for (int j = 0; j < blackWhite.getKey(); j++) {
                g.fillOval(offsetX + (j * slotRadius), offsetY + (slotRadius * 2) + (slotRadius * 2 + this.offsetY) * i.get(), slotRadius, slotRadius);
            }
            g.setColor(Color.white);
            for (int j = 0; j < blackWhite.getValue(); j++) {
                g.fillOval(offsetX + (j * slotRadius), offsetY + (slotRadius * 2) + slotRadius + (slotRadius * 2 + this.offsetY) * i.get(), slotRadius, slotRadius);
            }
            i.incrementAndGet();
            g.setColor(c);
        });
    }

    /**
     * @param g
     */
    private void paintGameBoard(@NotNull Graphics2D g) {
        Color c = g.getColor();
        for (int i = 0; i < game.maxTries; i++) {
            for (int j = 0; j < game.slots; j++) {
                if (colorsPerTry[i][j].getValue() == -1)
                    g.draw(colorsPerTry[i][j].getKey());
                else {
                    g.setColor(game.COLORS.get(colorsPerTry[i][j].getValue()));
                    g.fill(colorsPerTry[i][j].getKey());
                    g.setColor(c);
                }
            }
        }
    }

    /**
     * @param black
     * @param white
     */
    void setBlackAndWhite(int black, int white) {
        blackAndWhitePerTry.add(new Pair<>(black, white));
    }

    static class InputGUI extends MasterMindGUI {

        JFrame frame;
        private int[] input;
        private volatile boolean flag;

        InputGUI(int colors, int slots) {
            super(new MasterMindGame(colors, slots, 1, null, true, false));
            input = new int[slots];
            flag = true;
        }

        @Override
        protected void showFrame() {
            frame = new JFrame();
            frame.add(this);
            JButton b = new JButton("Enter");
            b.addActionListener(e -> {
                for (int i = 0; i < game.slots; i++) {
                    if (colorsPerTry[0][i].getValue() == -1) return;
                    input[i] = colorsPerTry[0][i].getValue();
                }
                flag = false;
                frame.dispose();
            });
            frame.add(b, BorderLayout.SOUTH);
            Dimension dim = new Dimension((slotRadius * 2 + offsetX) * (game.slots + 2) + offsetX, (slotRadius * 2 + offsetY) * (game.maxTries + 3) + offsetY);
            frame.setSize(dim);
            frame.setMinimumSize(dim);
            frame.setMaximumSize(dim);
            frame.setResizable(false);
            frame.setBackground(new Color(0x432A00));
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        }

        int[] getAsIntArray() {
            while (flag) Thread.onSpinWait();
            return input;
        }
    }
}
