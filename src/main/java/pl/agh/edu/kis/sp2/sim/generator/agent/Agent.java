package pl.agh.edu.kis.sp2.sim.generator.agent;

import pl.agh.edu.kis.sp2.sim.generator.graph.Node;
import pl.agh.edu.kis.sp2.sim.generator.wftr.Localization;

public class Agent {
    private Localization currentLocalization;
    private int preferredRouteWeight;

    private Agent(Builder builder) {
        currentLocalization = builder.currentLocalization;
        preferredRouteWeight = builder.preferredRouteWeight;
    }

    public Localization getCurrentLocalization() {
        return currentLocalization;
    }

    public int getPreferredRouteWeight() {
        return preferredRouteWeight;
    }

    public static final class Builder {
        private Localization currentLocalization;
        private int preferredRouteWeight;

        public Builder() {
        }

        public Builder currentLocalization(Localization val) {
            currentLocalization = val;
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
