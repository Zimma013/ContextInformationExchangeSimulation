package pl.agh.edu.kis.sp2.sim.generator.agent;

import pl.agh.edu.kis.sp2.sim.generator.graph.LocalizationVertex;
import pl.agh.edu.kis.sp2.sim.generator.wftr.Localization;
import pl.agh.edu.kis.sp2.sim.generator.wftr.Weather;
import pl.agh.edu.kis.sp2.sim.generator.wftr.WeatherSensor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Agent {
    private Long agentId;
    private Localization currentLocalization;
    private LocalizationVertex currentVertex;
    private LocalizationVertex destinationVertex;
    private int preferredRouteWeight;
    private double minPreferredHour;
    private double maxPreferredHour;

    public double getMinPreferredHour() {
        return minPreferredHour;
    }

    public void setMinPreferredHour(double minPreferredHour) {
        this.minPreferredHour = minPreferredHour;
    }

    public double getMaxPreferredHour() {
        return maxPreferredHour;
    }

    public void setMaxPreferredHour(double maxPreferredHour) {
        this.maxPreferredHour = maxPreferredHour;
    }

    private Set<LocalizationVertex> visitedVertexes;
    private boolean wantsToMove;
    private double distanceToDestination;
    private Long groupId;
    private Agent leader;
    private List<Agent> groupAgents;
    private WeatherSensor weatherSensor;
    private Weather currentWeather;


    private Agent(Builder builder) {
        agentId = builder.agentId;
        setCurrentLocalization(builder.currentLocalization);
        setCurrentVertex(builder.currentVertex);
        setDestinationVertex(builder.destinationVertex);
        setPreferredRouteWeight(builder.preferredRouteWeight);
        setMinPreferredHour(builder.minPreferredHour);
        setMaxPreferredHour(builder.maxPreferredHour);
        setVisitedVertexes(builder.visitedVertexes);
        setWantsToMove(builder.wantsToMove);
        setDistanceToDestination(builder.distanceToDestination);
        groupId = builder.groupId;
        leader = builder.leader;
        setGroupAgents(builder.groupAgents);
        setWeatherSensor(builder.weatherSensor);
        currentWeather = builder.currentWeather;
    }

    public Long getAgentId() {
        return agentId;
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

    public Long getGroupId() {
        return groupId;
    }

    public Agent getLeader() {
        return leader;
    }

    public List<Agent> getGroupAgents() {
        return groupAgents;
    }

    public void setGroupAgents(List<Agent> groupAgents) {
        this.groupAgents = groupAgents;
    }

    public WeatherSensor getWeatherSensor() {
        return weatherSensor;
    }

    public void setWeatherSensor(WeatherSensor weatherSensor) {
        this.weatherSensor = weatherSensor;
    }

    public Weather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(Weather currentWeather) {
        this.currentWeather = currentWeather;
    }

    public static final class Builder {
        private Localization currentLocalization;
        private LocalizationVertex currentVertex;
        private LocalizationVertex destinationVertex;
        private int preferredRouteWeight;
        private double minPreferredHour;
        private double maxPreferredHour;
        private Set<LocalizationVertex> visitedVertexes;
        private boolean wantsToMove;
        private double distanceToDestination;
        private Long groupId;
        private Agent leader;
        private List<Agent> groupAgents;
        private WeatherSensor weatherSensor;
        private Weather currentWeather;
        private Long agentId;

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
        public Builder minPreferredHour(double val) {
            minPreferredHour = val;
            return this;
        }
        public Builder maxPreferredHour(double val) {
            maxPreferredHour = val;
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

        public Builder distanceToDestination(double val) {
            distanceToDestination = val;
            return this;
        }

        public Builder groupId(Long val) {
            groupId = val;
            return this;
        }

        public Builder leader(Agent val) {
            leader = val;
            return this;
        }

        public Builder groupAgents(List<Agent> val) {
            groupAgents = val;
            return this;
        }

        public Builder weatherSensor(WeatherSensor val) {
            weatherSensor = val;
            return this;
        }

        public Builder currentWeather(Weather val) {
            currentWeather = val;
            return this;
        }

        public Agent build() {
            return new Agent(this);
        }

        public Builder agentId(Long val) {
            agentId = val;
            return this;
        }
    }

    @Override
    public String toString() {
        return "Agent{" +
                "agentId=" + agentId +
//                ", currentLocalization=" + currentLocalization +
//                ", currentVertex=" + currentVertex +
//                ", destinationVertex=" + destinationVertex +
//                ", preferredRouteWeight=" + preferredRouteWeight +
//                ", visitedVertexes=" + visitedVertexes +
//                ", wantsToMove=" + wantsToMove +
//                ", distanceToDestination=" + distanceToDestination +
                ", groupId=" + groupId +
                ", leader=" + leader +
                ", groupAgents=" + groupAgents.stream().map(Agent::getAgentId).collect(Collectors.toList()) +
                '}';
    }
}
