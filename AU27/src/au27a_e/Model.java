package au27a_e;


import api.CustomHashMap;
import api.Easteregg;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

public class Model {

    private final CustomHashMap<Integer, GeomObj> objects = new CustomHashMap<>();

    private Integer selected;

    public Model() {
        selected = 0;
        GeomObj obj = new GeomObj();
        obj.filled = false;
        obj.shaded = false;
        obj.sizePercent = 50;
        obj.pos = new Point(10, 10);
        obj.objSize = new Dimension(100, 100);
        obj.color = Color.RED;
        this.addObj(obj);
    }

    public static Polygon calcPolygon(int sides, double radius, Point pos, int degrees) {
        Point center = new Point(pos);
        center.translate((int) radius, (int) radius);
        Point[] points = new Point[sides];
        for (int i = 0; i < sides; i++) {
            points[i] = new Point(center.x + (int) (radius * Math.cos(2.0 * Math.PI * i / sides)), center.y + (int) (radius * Math.sin(2.0 * Math.PI * i / sides)));
        }
        int[] X = new int[sides];
        int[] Y = new int[sides];
        for (int i = 0; i < sides; i++) {
            points[i].setLocation(
                    (points[i].getX() - center.x) * Math.cos(Math.toRadians(-90 + degrees)) - (points[i].getY() - center.y) * Math.sin(Math.toRadians(-90 + degrees)) + center.x,
                    (points[i].getX() - center.x) * Math.sin(Math.toRadians(-90 + degrees)) + (points[i].getY() - center.y) * Math.cos(Math.toRadians(-90 + degrees)) + center.y);
            X[i] = points[i].x;
            Y[i] = points[i].y;
        }

        return new Polygon(X, Y, sides);
    }

    public void moveZUp() {
        if (selected >= objects.size() - 1) return;
        AtomicReference<GeomObj> last = new AtomicReference<>();
        last.set(objects.get(selected));
        objects.put(selected, objects.get(selected + 1));
        objects.put(selected + 1, last.get());
        selected++;
    }

    public void moveZDown() {
        if (selected <= 0) return;
        AtomicReference<GeomObj> last = new AtomicReference<>();
        last.set(objects.get(selected));
        objects.put(selected, objects.get(selected - 1));
        objects.put(selected - 1, last.get());
        selected--;
    }

    public boolean isFilled() {
        return this.objects.get(selected).isFilled();
    }

    public void setFilled(boolean filled) {
        this.objects.get(selected).setFilled(filled);
    }

    public boolean isShaded() {
        return this.objects.get(selected).isShaded();
    }

    public void setShaded(boolean shaded) {
        this.objects.get(selected).setShaded(shaded);
    }

    public Shape getShape() {
        return this.objects.get(selected).getShape();
    }

    public void setShape(Shape shape) {
        if (Arrays.asList(Shape.values()).contains(shape)) {
            this.objects.get(selected).setShape(shape);
        }
    }

    public Point getObjPos() {
        return new Point(this.objects.get(selected).getPos());
    }

    public void setObjPos(Point objPos) {
        this.objects.get(selected).setPos(new Point(objPos));
    }

    public void moveObj(int x, int y) {
        this.objects.get(selected).pos.translate(x, y);
    }

    public Dimension getObjSize() {
        return new Dimension(this.objects.get(selected).getObjSize());
    }

    public void setObjSize(Dimension objSize) {
        this.objects.get(selected).setObjSize(new Dimension(objSize));
    }

    public void resizeObj(int by) {
        this.objects.get(selected).getObjSize().height += by;
        this.objects.get(selected).getObjSize().width += by;
    }

    public Color getObjColor() {
        return new Color(this.objects.get(selected).getColor().getRGB());
    }

    public void setObjColor(Color objColor) {
        this.objects.get(selected).setColor(new Color(objColor.getRGB()));
    }

    public int getSizePercent() {
        return this.objects.get(selected).getSizePercent();
    }

    public void setSizePercent(int sizePercent) {
        if (sizePercent <= 100 && sizePercent >= 0) {
            this.objects.get(selected).setSizePercent(sizePercent);
        }
    }

    public void addObj(GeomObj obj) {
        this.objects.put(objects.size(), obj);
        this.selected = objects.size() - 1;
    }

    public void forEachObj(BiConsumer<Integer, GeomObj> consumer) {
        this.objects.forEach(consumer);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        this.objects.forEach((integer, geomObj) -> b.append(integer).append(": ").append(geomObj.toString()).append("\n"));
        return b.toString();
    }

    public void removeObj() {
        removeObj(this.selected);
    }

    public void removeObj(int key) {
        if (this.objects.containsKey(key)) {
            HashMap<Integer, GeomObj> objHashMap = new HashMap<>();
            this.objects.remove(key);
            AtomicInteger index = new AtomicInteger(0);
            this.objects.forEach((integer, geomObj) -> objHashMap.put(index.getAndIncrement(), geomObj));
            this.objects.clear();
            this.objects.putAll(objHashMap);
        }
    }

    public GeomObj getSelected() {
        return this.objects.get(this.selected);
    }

    public void setSelected(int selected) {
        if (objects.containsKey(selected)) {
            this.selected = selected;
        }
    }

    public int getRotation() {
        return this.objects.get(selected).getRotation();
    }

    public void setRotation(int rotation) {
        this.objects.get(selected).setRotation(rotation);
    }

    public void loadFromFields(String[][] fields) {
        for (String[] objVals : fields) {
            Integer index = Integer.parseInt(objVals[0].substring(0, objVals[0].indexOf(':')));
            GeomObj obj = new GeomObj();
            Point pos = new Point();
            Dimension dim = new Dimension();
            int r, g, b;
            r = g = b = 0;
            for (String val : objVals) {
                if (val.contains(index + ": ")) {
                    val = val.replace(index + ": ", "");
                }
                switch (val.substring(0, val.indexOf('='))) {
                    case "filled":
                        obj.filled = Boolean.parseBoolean(val.substring(val.indexOf('=') + 1));
                        break;
                    case "shaded":
                        obj.shaded = Boolean.parseBoolean(val.substring(val.indexOf('=') + 1));
                        break;
                    case "shape":
                        obj.shape = Shape.parseShape(val.substring(val.indexOf('=') + 1));
                        break;
                    case "size":
                        obj.sizePercent = Integer.parseInt(val.substring(val.indexOf('=') + 1));
                        break;
                    case "height":
                        dim.height = Integer.parseInt(val.substring(val.indexOf('=') + 1).replace("]", ""));
                        break;
                    case "pos":
                        pos.x = Integer.parseInt(val.substring(val.lastIndexOf('=') + 1).replace("]", ""));
                        break;
                    case "y":
                        pos.y = Integer.parseInt(val.substring(val.indexOf('=') + 1).replace("]", ""));
                        break;
                    case "color":
                        r = Integer.parseInt(val.substring(val.lastIndexOf('=') + 1).replace("]", ""));
                        break;
                    case "g":
                        g = Integer.parseInt(val.substring(val.indexOf('=') + 1).replace("]", ""));
                        break;
                    case "b":
                        b = Integer.parseInt(val.substring(val.indexOf('=') + 1).replace("]", ""));
                        break;
                }
            }
            obj.color = new Color(r, g, b);
            obj.objSize = dim;
            obj.pos = pos;
            objects.put(index, obj);
        }
    }

    public int objCount() {
        return objects.size();
    }

    public GeomObj get(int i) {
        return objects.get(i);
    }

    public enum Shape {

        EMPTY,
        CIRCLE,
        LINE(2),
        TRIANGLE(3),
        SQUARE,
        PENTAGON(5),
        HEXAGON(6),
        HEPTAGON(7),
        OCTAGON(8),
        NONAGON(9),
        DODECAGON(12);

        int polygonSides;

        Shape() {
        }

        Shape(int sides) {
            this.polygonSides = sides;
        }

        public static Shape parseShape(String string) {
            for (Shape shape : Shape.values()) {
                if (shape.name().equals(string)) {
                    return shape;
                }
            }
            return Shape.EMPTY;
        }
    }

    public static class GeomObj {
        private final AtomicReference<Easteregg> easteregg = new AtomicReference<>();
        private boolean filled;
        private boolean shaded;
        private Shape shape;
        private Point pos;
        private Dimension objSize;
        private Color color;
        private int sizePercent;
        private int rotation;

        public GeomObj() {
            this.filled = false;
            this.shaded = false;
            this.sizePercent = 50;
            this.shape = Shape.CIRCLE;
            this.pos = new Point(10, 10);
            this.objSize = new Dimension(100, 100);
            this.color = Color.RED;
        }

        public boolean isFilled() {
            return this.filled;
        }

        public void setFilled(boolean filled) {
            this.filled = filled;
        }

        public boolean isShaded() {
            return shaded;
        }

        public void setShaded(boolean shaded) {
            this.shaded = shaded;
        }

        public Shape getShape() {
            return shape;
        }

        public void setShape(Shape shape) {
            if (Arrays.asList(Shape.values()).contains(shape)) {
                this.shape = shape;
            }
        }

        public Point getPos() {
            return new Point(pos);
        }

        public void setPos(Point pos) {
            this.pos = new Point(pos);
        }

        public void moveObj(int x, int y) {
            this.pos.translate(x, y);
        }

        public Dimension getObjSize() {
            return new Dimension(objSize);
        }

        public void setObjSize(Dimension objSize) {
            this.objSize = new Dimension(objSize);
        }

        public void resizeObj(int by) {
            this.objSize.height += by;
            this.objSize.width += by;
        }

        public Color getColor() {
            return new Color(color.getRGB());
        }

        public void setColor(Color color) {
            this.color = new Color(color.getRGB());
        }

        public int getSizePercent() {
            return sizePercent;
        }

        public void setSizePercent(int sizePercent) {
            if (sizePercent <= 100 && sizePercent >= 0) {
                this.sizePercent = sizePercent;
            }
        }

        @Override
        public String toString() {
            return "filled=" + this.filled +
                    ",shaded=" + this.shaded +
                    ",shape=" + this.shape +
                    ",size=" + this.sizePercent +
                    ",pos=" + this.pos.toString() +
                    ",color=" + this.color.toString();
        }

        public void setEasteregg(Easteregg easteregg) {
            this.easteregg.set(easteregg);
        }

        public boolean hasEasteregg() {
            return this.easteregg.get() != null;
        }

        public void easteregg() {
            this.easteregg.get().easteregg();
        }

        public int getRotation() {
            return this.rotation;
        }

        public void setRotation(int rotation) {
            this.rotation = rotation;
        }
    }
}