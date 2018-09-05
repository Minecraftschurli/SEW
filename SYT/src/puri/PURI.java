package puri;

import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class PURI {

    private static final char P = 'P';
    private static final char U = 'U';
    private static final char R = 'R';
    private static final char I = 'I';

    private final HashMap<Character, Double> map = new HashMap<Character, Double>(4);
    private final HashMap<Character, JFormattedTextField> fields = new HashMap<Character, JFormattedTextField>(4);
    private boolean dirty = false;
    private JFrame frame;
    private JPanel panel;

    public PURI() {
        this(null, null);
    }

    public PURI(Pair<Character, Double> in1, Pair<Character, Double> in2) {
        frame = new JFrame();
        panel = new JPanel();
        frame.setLayout(new BorderLayout());
        panel.setLayout(new GridLayout(0, 3));

        map.put(P, null);
        map.put(U, null);
        map.put(R, null);
        map.put(I, null);

        if ((in1 != null && in2 != null)) {
            if ((!map.containsKey(in1.getKey()) || !map.containsKey(in2.getKey()))) this.markDirty();
            else {
                map.put(in1.getKey(), in1.getValue());
                map.put(in2.getKey(), in2.getValue());
            }
        }

        map.forEach((character, aDouble) -> {
            JFormattedTextField field = new JFormattedTextField(new JFormattedTextField.AbstractFormatter() {
                @Override
                public Object stringToValue(String text) throws java.text.ParseException {
                    if (text == null || text.equals("")) return "";
                    try {
                        return Double.parseDouble(text);
                    } catch (NumberFormatException e) {
                        throw new java.text.ParseException(e.getMessage(), 0);
                    }
                }

                @Override
                public String valueToString(Object value) throws java.text.ParseException {
                    if (value instanceof Double ||
                            value instanceof Long ||
                            value instanceof Integer ||
                            value instanceof Float ||
                            value instanceof Short) return value + "";
                    else if (value instanceof String) return (String) value;
                    else throw new java.text.ParseException("", 0);
                }
            });
            field.setName(character.toString());
            field.setValue(aDouble);
            fields.put(character, field);
        });

        map.forEach((character, aDouble) -> {
            panel.add(new JLabel(getName(character), SwingConstants.RIGHT));
            panel.add(fields.get(character));
            panel.add(new JLabel(getUnit(character).toString()));
        });

        frame.add(panel, BorderLayout.CENTER);
        JButton calculate = new JButton("Calculate");
        calculate.addActionListener(this::update);
        frame.add(calculate, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        update(null);
    }

    public static void main(String[] args) {
        new PURI();
    }

    private void update(java.awt.event.ActionEvent actionEvent) {
        fields.forEach((character, jFormattedTextField) -> {
            Object o = jFormattedTextField.getValue();
            if (o != null) {
                if (o instanceof Long) map.put(character, (double) (long) o);
                else if (o instanceof Double) map.put(character, (double) o);
            } else map.put(character, null);
        });

        calculate();
        map.forEach((character, aDouble) -> fields.get(character).setValue(aDouble));
    }

    private Double g(char c) {
        return map.get(c);
    }

    private void s(char c, Double d) {
        map.put(c, d);
    }

    public void calculate() {
        if (this.dirty) return;
        java.util.concurrent.atomic.AtomicInteger i = new java.util.concurrent.atomic.AtomicInteger(0);
        map.forEach((character, aDouble) -> {
            if (aDouble == null) i.incrementAndGet();
        });
        if (i.get() > 2) return;
        if (g(P) != null && g(U) != null) {
            s(R, Math.pow(g(U), 2) / g(P));
            s(I, g(P) / g(U));
        } else if (g(P) != null && g(R) != null) {
            s(U, Math.sqrt(g(P) * g(R)));
            s(I, Math.sqrt(g(P) / g(R)));
        } else if (g(P) != null && g(I) != null) {
            s(U, g(P) / g(I));
            s(R, g(P) / Math.pow(g(I), 2));
        } else if (g(U) != null && g(R) != null) {
            s(P, Math.pow(g(U), 2) / g(R));
            s(I, g(U) / g(R));
        } else if (g(U) != null && g(I) != null) {
            s(P, g(U) * g(I));
            s(R, g(U) / g(I));
        } else if (g(R) != null && g(I) != null) {
            s(P, g(R) * Math.pow(g(I), 2));
            s(U, g(R) * g(I));
        }
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        map.forEach((character, aDouble) -> out.append(character).append(" = ").append(aDouble).append(getUnit(character)).append("\n"));
        return out.toString();
    }

    private Character getUnit(char c) {
        switch (c) {
            case P:
                return 'W';
            case U:
                return 'V';
            case R:
                return '\u03a9';
            case I:
                return 'A';
            default:
                this.markDirty();
                return null;
        }
    }

    private String getName(char c) {
        switch (c) {
            case P:
                return "Power:  ";
            case U:
                return "Voltage:  ";
            case R:
                return "Resistance:  ";
            case I:
                return "Current:  ";
            default:
                this.markDirty();
                return null;
        }
    }

    private void markDirty() {
        map.clear();
        this.dirty = true;
    }
}
