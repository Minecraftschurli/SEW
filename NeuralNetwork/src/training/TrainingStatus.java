package training;

import java.io.Serializable;

public enum TrainingStatus implements Serializable {
    TRAINED,
    TRAINING,
    UNTRAINED;

    TrainingStatus() {
    }
}
