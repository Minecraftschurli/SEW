package circuit;

public class Controller {

    private View v;
    private Model m;

    public Controller() {
        this.m = new Model();
        this.v = new View(this, this.m);
    }

    public static void main(String[] args) {
        new Controller();
    }
}
