package pl.agh.edu.kis.sp2.sim.generator.agent;

import pl.agh.edu.kis.sp2.sim.generator.graph.LocalizationVertex;
import pl.agh.edu.kis.sp2.sim.generator.wftr.Localization;

import java.util.Set;

public class Agent {
    private Localization currentLocalization;
    private LocalizationVertex currentVertex;
    private int preferredRouteWeight;
    private Set<LocalizationVertex> visitedVertexes;
    private boolean wantsToMove;



    private Agent(Builder builder) {
        setCurrentLocalization(builder.currentLocalization);
        setCurrentVertex(builder.currentVertex);
        preferredRouteWeight = builder.preferredRouteWeight;
        setVisitedVertexes(builder.visitedVertexes);
        wantsToMove = builder.wantsToMove;
    }

    public Localization getCurrentLocalization() {
        return currentLocalization;
    }

    public void setCurrentLocalization(Localization currentLocalization) {
        this.currentLocalization = currentLocalization;
    }

    public int getPreferredRouteWeight() {
        return preferredRouteWeight;
    }

    public LocalizationVertex getCurrentVertex() {
        return currentVertex;
    }

    public void setCurrentVertex(LocalizationVertex currentVertex) {
        this.currentVertex = currentVertex;
    }

    public void addVertexToVisited(LocalizationVertex currentVertex) {
        this.visitedVertexes.add(currentVertex);
    }

    public Set<LocalizationVertex> getVisitedVertexes() {
        return visitedVertexes;
    }

    public void setVisitedVertexes(Set<LocalizationVertex> visitedVertexes) {
        this.visitedVertexes = visitedVertexes;
    }

    public boolean isWantsToMove() {
        return wantsToMove;
    }

    public void setWantsToMove(boolean wantsToMove) {
        this.wantsToMove = wantsToMove;
    }

    public static final class Builder {
        private Localization currentLocalization;
        private LocalizationVertex currentVertex;
        private int preferredRouteWeight;
        private Set<LocalizationVertex> visitedVertexes;
        private boolean wantsToMove;

        public Builder() {
        }

        public Builder currentLocalization(Localization val) {
            currentLocalization = val;
            return this;
        }

        public Builder currentVertex(LocalizationVertex val) {
            currentVertex = val;
            return this;
        }

        public Builder preferredRouteWeight(int val) {
            preferredRouteWeight = val;
            return this;
        }

        public Builder visitedVertexes(Set<LocalizationVertex> val) {
            visitedVertexes = val;
            return this;
        }

        public Builder wantsToMove(boolean val) {
            wantsToMove = val;
            return this;
        }

        public Agent build() {
            return new Agent(this);
        }
    }
}
