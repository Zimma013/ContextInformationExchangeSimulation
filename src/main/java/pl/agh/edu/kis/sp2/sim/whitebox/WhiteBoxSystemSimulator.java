package pl.agh.edu.kis.sp2.sim.whitebox;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import pl.agh.edu.kis.sp2.sim.generator.agent.Agent;
import pl.agh.edu.kis.sp2.sim.generator.graph.LocalizationVertex;
import pl.agh.edu.kis.sp2.sim.generator.wftr.Localization;
import pl.agh.edu.kis.sp2.sim.generator.wftr.WeatherSensor;
import pl.agh.edu.kis.sp2.sim.util.LocalizationDistanceUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WhiteBoxSystemSimulator {

	private List<Agent> population;
	private SimpleWeightedGraph<LocalizationVertex, DefaultWeightedEdge> mountainRoutesGraph;
	private LocalizationVertex rootVertex;
	private int simulatedWeatherConditionsMode;
	private double localizationDataRedundancyCoefficient;
	private List<WeatherSensor> weatherSensors;

	WhiteBoxSystemSimulator() {
	}


	public void simulate(int iterationsCount) {

		for(int i = 0; i < iterationsCount; i++) {

			Iterator<LocalizationVertex> iterator = new DepthFirstIterator<>(this.mountainRoutesGraph, this.rootVertex);
			while (iterator.hasNext()) {
				LocalizationVertex localizationVertex = iterator.next();
				Set<DefaultWeightedEdge> edges = mountainRoutesGraph.outgoingEdgesOf(localizationVertex);
				System.out.println("Current ------ " + localizationVertex);

				edges.forEach(edge -> {
					List<Agent> agents = localizationVertex.getAgentsInLocalization()
							.stream()
							.filter(agent -> mountainRoutesGraph.getEdgeWeight(edge) <= agent.getPreferredRouteWeight())
							.collect(Collectors.toList());
					System.out.println("Target ------ " + mountainRoutesGraph.getEdgeTarget(edge));
					for (Agent agent : agents) {
//                        if (agent.isWantsToMove()) {

						// Something is wrong here, besides TPing to next vertex
//						if (agent.getVisitedVertexes().contains(localizationVertex)) {
							LocalizationVertex destination = mountainRoutesGraph.getEdgeTarget(edge);
									destination.addAgentMovingToLocation(agent, new Localization.Builder().latitude(new BigDecimal(0.00002D)).longitude(new BigDecimal(0.00002D)).build());
							agent.setDestinationVertex(destination);
							agent.setDistanceToDestination(
									LocalizationDistanceUtility.distance(
											localizationVertex.getLocalization().getLatitude().doubleValue(), localizationVertex.getLocalization().getLongitude().doubleValue(),
											destination.getLocalization().getLatitude().doubleValue(), destination.getLocalization().getLongitude().doubleValue(),
											'K'));
							agent.addVertexToVisited(localizationVertex);
							localizationVertex.removeAgentFromLocation(agent);
//						}

//                        }
//                        agent.setWantsToMove(new Random().nextBoolean());
					}
				});
			}

			movePopulationWithConstSpeed(100);

			finishPopulationMovement();

			System.out.println();
			System.out.println();
		}
	}

	private void movePopulationWithConstSpeed(int movementRate) {
		for (LocalizationVertex vertex : mountainRoutesGraph.vertexSet()) {
			vertex.getAgentsMovingToLocalization()
					.forEach(agent ->
							agent.setDistanceToDestination(LocalizationDistanceUtility.distance(
							agent.getCurrentVertex().getLocalization().getLatitude().doubleValue(), agent.getCurrentVertex().getLocalization().getLongitude().doubleValue(),
							agent.getDestinationVertex().getLocalization().getLatitude().doubleValue(), agent.getDestinationVertex().getLocalization().getLongitude().doubleValue(),
							'K') / movementRate)
					);
		}
	}

	private void finishPopulationMovement() {
		Iterator<LocalizationVertex> iterator = new DepthFirstIterator<>(this.mountainRoutesGraph, rootVertex);
		while (iterator.hasNext()) {
			LocalizationVertex localizationVertex = iterator.next();
			localizationVertex.getAgentsInLocalization().addAll(localizationVertex.getAgentsMovingToLocalization().stream()
					.filter(agent -> agent.getDistanceToDestination() <= 0D)
					.collect(Collectors.toList()));
			localizationVertex.setAgentsMovingToLocalization(new ArrayList<>());
		}
	}


	// GETTERS


	public List<Agent> getPopulation() {
		return population;
	}

	public SimpleWeightedGraph<LocalizationVertex, DefaultWeightedEdge> getMountainRoutesGraph() {
		return mountainRoutesGraph;
	}

	public LocalizationVertex getRootVertex() {
		return rootVertex;
	}

	public int getSimulatedWeatherConditionsMode() {
		return simulatedWeatherConditionsMode;
	}

	public double getLocalizationDataRedundancyCoefficient() {
		return localizationDataRedundancyCoefficient;
	}

	public List<WeatherSensor> getWeatherSensors() {
		return weatherSensors;
	}


	// SETTERS


	void setPopulation(List<Agent> population) {
		this.population = population;
	}

	void setMountainRoutesGraph(SimpleWeightedGraph<LocalizationVertex, DefaultWeightedEdge> mountainRoutesGraph) {
		this.mountainRoutesGraph = mountainRoutesGraph;
	}

	void setRootVertex(LocalizationVertex rootVertex) {
		this.rootVertex = rootVertex;
	}

	void setSimulatedWeatherConditionsMode(int simulatedWeatherConditionsMode) {
		this.simulatedWeatherConditionsMode = simulatedWeatherConditionsMode;
	}

	void setLocalizationDataRedundancyCoefficient(double localizationDataRedundancyCoefficient) {
		this.localizationDataRedundancyCoefficient = localizationDataRedundancyCoefficient;
	}

	void setWeatherSensors(List<WeatherSensor> weatherSensors) {
		this.weatherSensors = weatherSensors;
	}
}
