package au27a_e;

import java.awt.*;

public class Model {

    public static final int SHAPE_EMPTY = 0;
    public static final int SHAPE_CIRCLE = 1;
    public static final int SHAPE_SQUARE = 2;
    public static final int SHAPE_LINE = 3;

    private boolean filled;
    private boolean shaded;
    private int shape;
    private Point objPos;
    private Dimension objSize;
    private Color objColor;
    private int sizePercent;

    public Model() {
        this.filled = false;
        this.shaded = false;
        this.sizePercent = 50;
        this.objPos = new Point(10, 10);
        this.objSize = new Dimension(100, 100);
        this.objColor = Color.RED;
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

    public int getShape() {
        return shape;
    }

    public void setShape(int shape) {
        switch (shape) {
            case SHAPE_EMPTY:
            case SHAPE_CIRCLE:
            case SHAPE_SQUARE:
            case SHAPE_LINE:
                this.shape = shape;
                break;
            default:
                break;
        }
    }

    public Point getObjPos() {
        return new Point(objPos);
    }

    public void setObjPos(Point objPos) {
        this.objPos = new Point(objPos);
    }

    public void moveObj(int x, int y) {
        this.objPos.translate(x, y);
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

    public Color getObjColor() {
        return new Color(objColor.getRGB());
    }

    public void setObjColor(Color objColor) {
        this.objColor = new Color(objColor.getRGB());
    }

    public int getSizePercent() {
        return sizePercent;
    }

    public void setSizePercent(int sizePercent) {
        if (sizePercent <= 100 && sizePercent >= 0) {
            this.sizePercent = sizePercent;
        }
    }
}