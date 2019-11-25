package pl.agh.edu.kis.sp2.sim.generator.agent;

import pl.agh.edu.kis.sp2.sim.generator.graph.Node;
import pl.agh.edu.kis.sp2.sim.generator.wftr.Localization;

public class Agent {
    private Localization currentLocalization;
    private Node currentNode;
    private int preferredRouteWeight;

    private Agent(Builder builder) {
        currentLocalization = builder.currentLocalization;
        currentNode = builder.currentNode;
        preferredRouteWeight = builder.preferredRouteWeight;
    }

    public Localization getCurrentLocalization() {
        return currentLocalization;
    }

    public Node getCurrentNode() {
        return currentNode;
    }

    public int getPreferredRouteWeight() {
        return preferredRouteWeight;
    }

    public void moveAgent() {
        System.out.println(currentNode.getEdges().stream().min((o1, o2) -> (int) o1.getWeight() - preferredRouteWeight - (int) o2.getWeight() - preferredRouteWeight).isPresent());
    }

    public static final class Builder {
        private Localization currentLocalization;
        private Node currentNode;
        private int preferredRouteWeight;

        public Builder() {
        }

        public Builder currentLocalization(Localization val) {
            currentLocalization = val;
            return this;
        }

        public Builder currentNode(Node val) {
            currentNode = val;
            return this;
        }

        public Builder preferredRouteWeight(int val) {
            preferredRouteWeight = val;
            return this;
        }

        public Agent build() {
            return new Agent(this);
        }
    }
}
