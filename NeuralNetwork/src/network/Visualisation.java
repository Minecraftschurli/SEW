package network;

import network.neuron.Connection;
import network.neuron.Neuron;
import network.structure.NeuralNetLayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Visualisation {

    private static final int[] size = new int[1];
    private static final int[] space = new int[1];
    private static String PATH = "C:\\Users\\georg\\Documents\\Schule\\SEW\\NeuralNetwork\\resources\\";
    private static int offsetX = 10;
    private static int offsetY = 20;
    private static int width = 50;
    private static int height = width;
    private static int length = 180;
    private static int max = 0;

    public static void visualize(NeuralNetwork nn) {
        JFrame frame = new JFrame(nn.name);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        for (int i = 0; i < nn.getLayerCount(); i++) {
            NeuralNetLayer l = nn.getLayer(i);
            if (l.neurons.size() > max) max = l.neurons.size();
        }
        size[0] = offsetY + (max * (height + offsetY));
        JPanel canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (g instanceof Graphics2D) {
                    Graphics2D g2d = (Graphics2D) g;
                    drawNN(g2d, nn);
                }
            }
        };
        canvas.setPreferredSize(new Dimension(nn.getLayerCount() * (width + length), size[0] + 40));
        canvas.setMinimumSize(new Dimension(nn.getLayerCount() * (width + length), size[0] + 40));
        frame.setPreferredSize(new Dimension(nn.getLayerCount() * (width + length), size[0] + 40));
        frame.setMinimumSize(new Dimension(nn.getLayerCount() * (width + length), size[0] + 40));
        frame.add(canvas);
        frame.setVisible(true);

        BufferedImage image = new BufferedImage(nn.getLayerCount() * (width + length), size[0] + 40, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(new Color(0xFFFFFF));
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        drawNN(g2d, nn);
        try {
            ImageIO.write(image, "jpg", new File(PATH + nn.name + ".jpg"));
        } catch (IOException ignored) {
        }
    }

    private static synchronized void drawNN(Graphics2D g2d, NeuralNetwork nn) {
        Map<Neuron, Point> map = new HashMap<>();
        g2d.setColor(new Color(0x000000));
        Font f = g2d.getFont();
        g2d.setFont(new Font(f.getName(), f.getStyle(), 20));

        for (int i = 0; i < nn.getLayerCount(); i++) {
            NeuralNetLayer l = nn.getLayer(i);
            space[0] = -((height * l.neurons.size() - size[0]) / (l.neurons.size() + 1));
            for (int j = 0; j < l.neurons.size(); j++) {
                int x = offsetX + (i * (width + length));
                int y = space[0] + ((space[0] + height) * j) + offsetY;
                Point p = new Point(x, y), pp = new Point(p);
                g2d.drawOval(p.x, p.y, width, height);
                pp.translate(offsetX / 2 - 2, -2);
                g2d.drawString((i == 0 ? "i" : i == (nn.getLayerCount() - 1) ? "o" : "h" + i + ",") + j, pp.x + (i == 0 || i == (nn.getLayerCount() - 1) ? 10 : 0), pp.y);
                p.translate(width / 2, height / 2);
                map.put(l.neurons.get(j), p);
            }
        }
        for (int i = 1; i < nn.getLayerCount(); i++) {
            NeuralNetLayer layer = nn.getLayer(i);
            for (int j = 0; j < layer.neurons.size(); j++) {
                Neuron neuron = layer.neurons.get(j);
                for (int k = 0; k < neuron.inputConnections.size(); k++) {
                    Connection connection = neuron.inputConnections.get(k);
                    if (connection.getFromNeuron() == nn.biasNeuron) continue;
                    Point from = map.get(connection.getFromNeuron()), to = map.get(connection.getToNeuron());
                    g2d.setColor(new Color(connection.getWeight() > 0.0 ? 0xFF0000 : connection.getWeight() < 0.0 ? 0x0000FF : 0x000000));
                    if (connection.getWeight() != 0.0)
                        g2d.drawLine(from.x + (width / 2), from.y, to.x - (width / 2), to.y);
                }
            }
        }
    }
}
