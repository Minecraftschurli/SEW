package circuit;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Wire {

    private final Side from;
    private final ArrayList<Side> to = new ArrayList<>();
    private final ArrayList<Integer> toIndex = new ArrayList<>();
    private int fromIndex;

    public Wire(Side from, int fromIndex, Side to, int toIndex) {
        this.from = from;
        this.fromIndex = fromIndex;
        this.to.add(to);
        this.toIndex.add(toIndex);
    }

    public void addTo(Side side, int index) {
        to.add(side);
        toIndex.add(index);
    }

    public Side getFrom() {
        return from;
    }

    public int getFromIndex() {
        return fromIndex;
    }

    public List<Side> getTo() {
        return new ArrayList<>(to);
    }

    public Side getTo(int i) {
        return to.get(i);
    }

    public int getToIndex(int i) {
        return toIndex.get(i);
    }

    enum Side {
        LEFT, RIGHT, TOP, BOTTOM;


        @NotNull
        @Contract(pure = true)
        public Side inverse() {
            switch (this) {
                case BOTTOM:
                    return TOP;
                case RIGHT:
                    return LEFT;
                case LEFT:
                    return RIGHT;
                case TOP:
                    return BOTTOM;
                default:
                    return null;
            }
        }
    }
}
