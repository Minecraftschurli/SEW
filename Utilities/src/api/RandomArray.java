package api;

import java.util.Random;

public class RandomArray {

	private int[] valueInt;
	private long[] valueLong;
    private short[] valueShort;
	private double[] valueDouble;
	private boolean[] valueBoolean;
	private Random rand;

    public RandomArray() {
		this.rand = new Random();
	}
	
	
	
	public int[] getIntArray(int arrayLength){
		this.valueInt = new int[arrayLength];
		int[] hInt = rand.ints().toArray();
		for (int i = 0; i < this.valueInt.length; i++) {
			this.valueInt[i] = hInt[i];
		}
		return valueInt;
	}
	
	public long[] getLongArray(int arrayLength){
		this.valueLong = new long[arrayLength];
		long[] hLong = this.rand.longs().toArray();
		for (int i = 0; i < this.valueLong.length; i++) {
			this.valueLong[i] = hLong[i];
		}
		return valueLong;
	}
	
	public double[] getDoubleArray(int arrayLength){
		this.valueDouble = new double[arrayLength];
		double[] hDouble = this.rand.doubles().toArray();
		for (int i = 0; i < this.valueDouble.length; i++) {
			this.valueDouble[i] = hDouble[i];
		}
		return valueDouble;
	}
	
	public boolean[] getBooleanArray(int length){
		this.valueBoolean = new boolean[length];
		for (int i = 0; i < this.valueBoolean.length; i++) {
			this.valueBoolean[i] = this.rand.nextBoolean();
		}
		return valueBoolean;
	}
	
}
