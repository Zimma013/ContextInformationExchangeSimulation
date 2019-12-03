package pl.agh.edu.kis.sp2.sim.whitebox;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import pl.agh.edu.kis.sp2.sim.excel.Data;
import pl.agh.edu.kis.sp2.sim.generator.agent.Agent;
import pl.agh.edu.kis.sp2.sim.generator.graph.LocalizationVertex;
import pl.agh.edu.kis.sp2.sim.generator.wftr.Localization;
import pl.agh.edu.kis.sp2.sim.generator.wftr.WeatherSensor;
import pl.agh.edu.kis.sp2.sim.util.LocalizationDistanceUtility;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.agh.edu.kis.sp2.sim.Application.excelWriter;
import static pl.agh.edu.kis.sp2.sim.Application.data;

public class WhiteBoxSystemSimulator {

	private List<Agent> population;
	private SimpleWeightedGraph<LocalizationVertex, DefaultWeightedEdge> mountainRoutesGraph;
	private LocalizationVertex rootVertex;
	private int simulatedWeatherConditionsMode;
	private double localizationDataRedundancyCoefficient;
	private List<WeatherSensor> weatherSensors;

	private Random generator = new Random();

	WhiteBoxSystemSimulator() {
	}


	public void simulate(int iterationsCount) {
		System.out.println("========================== Starting  parameters ==========================");
		System.out.println("== Population " + population.size());
		System.out.println("== mountainRoutesGraph.vertices " + mountainRoutesGraph.vertexSet().size());
		System.out.println("== mountainRoutesGraph.edges " + mountainRoutesGraph.edgeSet().size());
		System.out.println("== simulatedWeatherConditionsMode " + simulatedWeatherConditionsMode);
		System.out.println("== weatherSensors " + weatherSensors.size());
		System.out.println("==========================================================================");
		System.out.println();

		for(int i = 0; i < iterationsCount; i++) {
			System.out.println("Simulation iteration ----- " + (i + 1));
			System.out.println();

			Iterator<LocalizationVertex> iterator = new BreadthFirstIterator<>(this.mountainRoutesGraph, this.rootVertex);
			while (iterator.hasNext()) {
				LocalizationVertex localizationVertex = iterator.next();
				Set<DefaultWeightedEdge> edges = mountainRoutesGraph.outgoingEdgesOf(localizationVertex);
				System.out.println("Current ------ " + localizationVertex);

				edges.stream()
				.filter(edge -> !mountainRoutesGraph.getEdgeTarget(edge).equals(localizationVertex))
				.forEach(edge -> {

					List<Agent> agents = localizationVertex.getAgentsInLocalization()
						.stream()
						.filter(agent -> mountainRoutesGraph.getEdgeWeight(edge) <= agent.getPreferredRouteWeight())
						.collect(Collectors.toList());

					LocalizationVertex target = mountainRoutesGraph.getEdgeTarget(edge);
					System.out.println("Target ------ " + target);
					for (Agent agent : agents) {
//                        if (agent.isWantsToMove()) {

						// Something is wrong here, besides TPing to next vertex
//						if (agent.getVisitedVertexes().contains(localizationVertex)) {
//							LocalizationVertex destination = mountainRoutesGraph.getEdgeTarget(edge);
						target.addAgentMovingToLocation(agent, new Localization.Builder().latitude(new BigDecimal(0.00002D)).longitude(new BigDecimal(0.00002D)).build());
							agent.setDestinationVertex(target);
							agent.setDistanceToDestination(
									LocalizationDistanceUtility.distance(
											localizationVertex.getLocalization().getLatitude().doubleValue(), localizationVertex.getLocalization().getLongitude().doubleValue(),
											target.getLocalization().getLatitude().doubleValue(), target.getLocalization().getLongitude().doubleValue(),
											'K'));
							agent.addVertexToVisited(localizationVertex);
							localizationVertex.removeAgentFromLocation(agent);
//						}

//                        }
//                        agent.setWantsToMove(new Random().nextBoolean());
					}
				});
			}

			movePopulationWithConstSpeed(10);

			finishPopulationMovement();

			excelWriter.addToDataList(data);
			data = new Data(0,0,0,0,0);
			System.out.println();
			System.out.println();
		}
	}

	private void movePopulationWithConstSpeed(int movementRate) {
		for (LocalizationVertex vertex : mountainRoutesGraph.vertexSet()) {
			vertex.getAgentsMovingToLocalization()
					.forEach(agent -> {
//						System.out.println("Agent distance to dest --------- " + agent.getDistanceToDestination());
								/*double movementSpeed = LocalizationDistanceUtility.distance(
										agent.getCurrentVertex().getLocalization().getLatitude().doubleValue(), agent.getCurrentVertex().getLocalization().getLongitude().doubleValue(),
										agent.getDestinationVertex().getLocalization().getLatitude().doubleValue(), agent.getDestinationVertex().getLocalization().getLongitude().doubleValue(),
										'K') / (movementRate ); //* mountainRoutesGraph.getEdge(agent.getCurrentVertex(), agent.getDestinationVertex()).getWeight()
								agent.setDistanceToDestination(agent.getDistanceToDestination() - movementSpeed);*/

								Localization currentLocalization = agent.getCurrentLocalization();
								agent.setCurrentLocalization(new Localization.Builder()
										.longitude(currentLocalization.getLongitude().subtract(new BigDecimal(generator.nextDouble()/50)).setScale(2, RoundingMode.HALF_UP))
										.latitude(currentLocalization.getLatitude().subtract(new BigDecimal(generator.nextDouble()/50)).setScale(2, RoundingMode.HALF_UP))
										.build());

								double movementSpeed = LocalizationDistanceUtility.distance(
										agent.getCurrentLocalization().getLatitude().doubleValue(), agent.getCurrentLocalization().getLongitude().doubleValue(),
										agent.getDestinationVertex().getLocalization().getLatitude().doubleValue(), agent.getDestinationVertex().getLocalization().getLongitude().doubleValue(),
										'K') / (movementRate ); //* mountainRoutesGraph.getEdge(agent.getCurrentVertex(), agent.getDestinationVertex()).getWeight()
								agent.setDistanceToDestination(agent.getDistanceToDestination() - movementSpeed);
//						System.out.println("Agent distance to dest 2 --------- " + agent.getDistanceToDestination());
							}
					);
		}
	}

	private void finishPopulationMovement() {
		Iterator<LocalizationVertex> iterator = new BreadthFirstIterator<>(this.mountainRoutesGraph, rootVertex);
		while (iterator.hasNext()) {
			LocalizationVertex localizationVertex = iterator.next();
			List<Agent> agentsReachedTheDestination = localizationVertex.getAgentsMovingToLocalization().stream()
					.filter(agent -> agent.getDistanceToDestination() <= 0D)
					.collect(Collectors.toList());

			if (!agentsReachedTheDestination.isEmpty()) {
				System.out.println("Agents that reached destination (source) ----- " + agentsReachedTheDestination.get(0).getCurrentVertex());
				System.out.println("Agents that reached destination (target) ----- " + agentsReachedTheDestination.get(0).getDestinationVertex());
				System.out.println("Agents that reached destination ----- " + agentsReachedTheDestination.size());
				localizationVertex.getAgentsInLocalization().addAll(agentsReachedTheDestination);

				localizationVertex.getAgentsMovingToLocalization().removeAll(agentsReachedTheDestination);
			}


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
