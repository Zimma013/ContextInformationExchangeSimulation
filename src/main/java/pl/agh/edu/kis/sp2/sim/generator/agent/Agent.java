package pl.agh.edu.kis.sp2.sim.generator.agent;

import pl.agh.edu.kis.sp2.sim.generator.graph.LocalizationVertex;
import pl.agh.edu.kis.sp2.sim.generator.wftr.Localization;

import java.util.Set;

public class Agent {
    private Localization currentLocalization;
    private LocalizationVertex currentVertex;
    private LocalizationVertex destinationVertex;
    private int preferredRouteWeight;
    private Set<LocalizationVertex> visitedVertexes;
    private boolean wantsToMove;
    private double distanceToDestination;


    private Agent(Builder builder) {
        setCurrentLocalization(builder.currentLocalization);
        setCurrentVertex(builder.currentVertex);
        setDestinationVertex(builder.destinationVertex);
        setPreferredRouteWeight(builder.preferredRouteWeight);
        setVisitedVertexes(builder.visitedVertexes);
        setWantsToMove(builder.wantsToMove);
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

    public void setDestinationVertex(LocalizationVertex destinationVertex) {
        this.destinationVertex = destinationVertex;
    }

    public void setPreferredRouteWeight(int preferredRouteWeight) {
        this.preferredRouteWeight = preferredRouteWeight;
    }

	public LocalizationVertex getDestinationVertex() {
		return destinationVertex;
	}

	public double getDistanceToDestination() {
		return distanceToDestination;
	}

	public void setDistanceToDestination(double distanceToDestination) {
		this.distanceToDestination = distanceToDestination;
	}

	public static final class Builder {
        private Localization currentLocalization;
        private LocalizationVertex currentVertex;
        private LocalizationVertex destinationVertex;
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

        public Builder destinationVertex(LocalizationVertex val) {
            destinationVertex = val;
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
