package circuit;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class CircuitMap {

    public ArrayList<Wiremap> wiremaps = new ArrayList<>();
    private Circuit[][] map;

    public CircuitMap(Circuit[][] map) {
        this.map = map;
    }

    public Circuit get(Wire.Side side, int x, int y) {
        switch (side) {
            case TOP:
                if (y - 1 < 0) return null;
                return map[y - 1][x];
            case LEFT:
                if (x - 1 < 0) return null;
                return map[y][x - 1];
            case RIGHT:
                if (x + 1 >= map[y].length) return null;
                return map[y][x + 1];
            case BOTTOM:
                if (y + 1 >= map.length) return null;
                return map[y + 1][x];
        }
        return null;
    }

    public void paint(Graphics2D g2D) {
        int i = 0, j = 0;
        Iterator<Wiremap> wiremap = wiremaps.iterator();
        Point p = new Point();
        Point prev = new Point(0, 0);
        for (Circuit[] cA : map) {
            i++;
            for (Circuit c : cA) {
                j++;
                p.setLocation(prev.x, prev.y);
                c.paint(g2D, (Point) p.clone(), c.equals(Circuit.WIRE) ? wiremap.next() : null);
                prev.translate(c.getWidth(), 0);
            }
            prev.x = 0;
            prev.translate(0, 40);
        }
    }
}
