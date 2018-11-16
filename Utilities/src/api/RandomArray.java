package api;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomArray {

    public static class Generator {
        private final boolean preventDoubles;

        public Generator(boolean preventDoubles) {
            this.preventDoubles = preventDoubles;
        }

        public int[] getIntArray(int arrayLength) {
            if (!preventDoubles)
                return new Random().ints(arrayLength).toArray();
            else {
                Random r = new Random();
                List<Integer> out = new ArrayList<>();
                for (int i = 0; i < arrayLength; i++) {
                    int tmp = r.nextInt();
                    if (out.contains(tmp)) {
                        i--;
                        continue;
                    }
                    out.add(tmp);
                }
                int[] outA = new int[arrayLength];
                for (int i = 0; i < out.size(); i++) {
                    outA[i] = out.get(i);
                }
                return outA;
            }
        }

        public int[] getIntArray(int arrayLength, int min, int max) {
            if (!preventDoubles)
                return new Random().ints(arrayLength, min, max).toArray();
            else {
                Random r = new Random();
                List<Integer> out = new ArrayList<>();
                for (int i = 0; i < arrayLength; i++) {
                    int tmp = r.nextInt(max - min) + min;
                    if (out.contains(tmp)) {
                        i--;
                        continue;
                    }
                    out.add(tmp);
                }
                int[] outA = new int[arrayLength];
                for (int i = 0; i < out.size(); i++) {
                    outA[i] = out.get(i);
                }
                return outA;
            }
        }

        public long[] getLongArray(int arrayLength) {
            return new Random().longs(arrayLength).toArray();
        }

        public long[] getLongArray(int arrayLength, long min, long max) {
            return new Random().longs(arrayLength, min, max).toArray();
        }

        public double[] getDoubleArray(int arrayLength) {
            return new Random().doubles(arrayLength).toArray();
        }

        public double[] getDoubleArray(int arrayLength, double min, double max) {
            return new Random().doubles(arrayLength, min, max).toArray();
        }

        public boolean[] getBooleanArray(int arrayLength) {
            Random rand = new Random();
            boolean[] valueBoolean = new boolean[arrayLength];
            for (int i = 0; i < valueBoolean.length; i++) {
                valueBoolean[i] = rand.nextBoolean();
            }
            return valueBoolean;
        }
    }
}
