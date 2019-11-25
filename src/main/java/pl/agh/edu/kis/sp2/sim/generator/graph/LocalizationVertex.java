package pl.agh.edu.kis.sp2.sim.generator.graph;

import pl.agh.edu.kis.sp2.sim.generator.agent.Agent;
import pl.agh.edu.kis.sp2.sim.generator.wftr.Localization;

import java.util.List;

public class LocalizationVertex {
    private int vertexId;
    private Localization localization;
    private List<Agent> agentsInLocalization;

    public LocalizationVertex() {
    }

    private LocalizationVertex(Builder builder) {
        vertexId = builder.vertexId;
        localization = builder.localization;
        agentsInLocalization = builder.agentsInLocalization;
    }

    public int getVertexId() {
        return vertexId;
    }

    public Localization getLocalization() {
        return localization;
    }

    public List<Agent> getAgentsInLocalization() {
        return agentsInLocalization;
    }

    public static final class Builder {
        private int vertexId;
        private Localization localization;
        private List<Agent> agentsInLocalization;

        public Builder() {
        }

        public Builder vertexId(int val) {
            vertexId = val;
            return this;
        }

        public Builder localization(Localization val) {
            localization = val;
            return this;
        }

        public Builder agentsInLocalization(List<Agent> val) {
            agentsInLocalization = val;
            return this;
        }

        public LocalizationVertex build() {
            return new LocalizationVertex(this);
        }
    }

    @Override
    public String toString() {
        return "LocalizationVertex{" +
                "vertexId=" + vertexId +
                ", localization=" + localization +
                ", agentsInLocalization=" + agentsInLocalization.size() +
                '}';
    }
}
