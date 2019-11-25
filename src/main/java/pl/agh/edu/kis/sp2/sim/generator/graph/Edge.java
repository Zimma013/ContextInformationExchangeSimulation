package pl.agh.edu.kis.sp2.sim.generator.graph;

public class Edge {
    public Node getDestination() {
        return destination;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    private Node destination;
    private double weight;
}