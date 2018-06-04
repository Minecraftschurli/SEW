package template;

public class Model {
    private boolean gefuellt;

    public Model() {
        this.gefuellt = false;
    }

    public boolean istGefuellt() {
        return this.gefuellt;
    }

    public void setGefuellt(boolean gefuellt) {
        this.gefuellt = gefuellt;
    }
}