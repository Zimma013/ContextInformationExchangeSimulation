package pl.agh.edu.kis.sp2.sim.generator.graph;

public class Edge {
    private Node destination;
    private Node origin;
    private int weight;

    public Edge() {
    }

    private Edge(Builder builder) {
        setDestination(builder.destination);
        setOrigin(builder.origin);
        setWeight(builder.weight);
    }

    public Node getDestination() {
        return destination;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Node getOrigin() {
        return origin;
    }

    public void setOrigin(Node origin) {
        this.origin = origin;
    }


    public static final class Builder {
        private Node destination;
        private Node origin;
        private int weight;

        public Builder() {
        }

        public Builder destination(Node val) {
            destination = val;
            return this;
        }

        public Builder origin(Node val) {
            origin = val;
            return this;
        }

        public Builder weight(int val) {
            weight = val;
            return this;
        }

        public Edge build() {
            return new Edge(this);
        }
    }

    @Override
    public String toString() {
        return "Edge{" +
                "destination=" + destination +
//                ", origin=" + origin +
                ", weight=" + weight +
                "}";
    }
}