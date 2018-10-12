package circuit;

import static circuit.Circuit.AND;
import static circuit.Circuit.WIRE;

public class Model {

    public CircuitMap map;

    public Model() {
        Circuit[][] cm = {
                {WIRE, AND, WIRE},
                {WIRE, AND, WIRE, AND}
        };
        this.map = new CircuitMap(cm);
        Wiremap wiremap = new Wiremap(map, 0, 0);
        wiremap.wires.add(new Wire(Wire.Side.LEFT, 0, Wire.Side.RIGHT, 0));
        wiremap.wires.add(new Wire(Wire.Side.LEFT, 1, Wire.Side.RIGHT, 1));
        this.map.wiremaps.add(wiremap);
        wiremap = new Wiremap(map, 2, 0);
        wiremap.wires.add(new Wire(Wire.Side.LEFT, 0, Wire.Side.BOTTOM, 0));
        this.map.wiremaps.add(wiremap);
        wiremap = new Wiremap(map, 0, 1);
        wiremap.wires.add(new Wire(Wire.Side.LEFT, 0, Wire.Side.RIGHT, 0));
        wiremap.wires.add(new Wire(Wire.Side.LEFT, 1, Wire.Side.RIGHT, 1));
        this.map.wiremaps.add(wiremap);
        wiremap = new Wiremap(map, 2, 1);
        wiremap.wires.add(new Wire(Wire.Side.LEFT, 0, Wire.Side.RIGHT, 1));
        wiremap.wires.add(new Wire(Wire.Side.TOP, 0, Wire.Side.RIGHT, 0));
        this.map.wiremaps.add(wiremap);
    }
}
