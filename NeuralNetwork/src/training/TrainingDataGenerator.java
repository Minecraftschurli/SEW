package training;

@FunctionalInterface
public interface TrainingDataGenerator {
    double[][] getIOMatrix();
}
