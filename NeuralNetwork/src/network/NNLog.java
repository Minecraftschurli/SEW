package network;

import java.io.Serializable;

/**
 *
 */
public class NNLog implements Serializable {

    /**
     *
     */
    private final NeuralNetwork nn;

    protected StringBuffer log;

    /**
     * @param nn
     */
    public NNLog(NeuralNetwork nn) {
        this.nn = nn;
        this.log = new StringBuffer();
    }

    /**
     * @param s
     */
    public final void log(final String s) {
        log.append(s);
        log.append('\n');
    }
}
