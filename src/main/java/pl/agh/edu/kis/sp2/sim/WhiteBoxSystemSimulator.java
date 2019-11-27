package pl.agh.edu.kis.sp2.sim;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import pl.agh.edu.kis.sp2.sim.generator.agent.Agent;
import pl.agh.edu.kis.sp2.sim.generator.graph.LocalizationVertex;
import pl.agh.edu.kis.sp2.sim.generator.wftr.Localization;

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

	private WhiteBoxSystemSimulator(Builder builder) {
		population = builder.population;
		mountainRoutesGraph = builder.mountainRoutesGraph;
		rootVertex = builder.rootVertex;
		simulatedWeatherConditionsMode = builder.simulatedWeatherConditionsMode;
		localizationDataRedundancyCoefficient = builder.localizationDataRedundancyCoefficient;
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

						if (agent.getVisitedVertexes().contains(localizationVertex)) {
							mountainRoutesGraph.getEdgeTarget(edge).addAgentMovingToLocation(agent, new Localization.Builder().latitude(new BigDecimal(0.00002D)).longitude(new BigDecimal(0.00002D)).build());
							agent.addVertexToVisited(localizationVertex);
							localizationVertex.removeAgentFromLocation(agent);
						}

//                        }
//                        agent.setWantsToMove(new Random().nextBoolean());
					}
				});
			}

			finishPopulationMovement();

			System.out.println();
			System.out.println();
		}
	}

	private void finishPopulationMovement() {
		Iterator<LocalizationVertex> iterator = new DepthFirstIterator<>(this.mountainRoutesGraph, rootVertex);
		while (iterator.hasNext()) {
			LocalizationVertex localizationVertex = iterator.next();
			localizationVertex.getAgentsInLocalization().addAll(localizationVertex.getAgentsMovingToLocalization());
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

	public static final class Builder {
		private List<Agent> population;
		private SimpleWeightedGraph<LocalizationVertex, DefaultWeightedEdge> mountainRoutesGraph;
		private LocalizationVertex rootVertex;
		private int simulatedWeatherConditionsMode;
		private double localizationDataRedundancyCoefficient;

		public Builder() {
		}

		public Builder population(List<Agent> val) {
			population = val;
			return this;
		}

		public Builder mountainRoutesGraph(SimpleWeightedGraph<LocalizationVertex, DefaultWeightedEdge> val) {
			mountainRoutesGraph = val;
			return this;
		}

		public Builder rootVertex(LocalizationVertex val) {
			rootVertex = val;
			return this;
		}

		public Builder simulatedWeatherConditionsMode(int val) {
			simulatedWeatherConditionsMode = val;
			return this;
		}

		public Builder localizationDataRedundancyCoefficient(double val) {
			localizationDataRedundancyCoefficient = val;
			return this;
		}

		public WhiteBoxSystemSimulator build() {
			return new WhiteBoxSystemSimulator(this);
		}
	}
}
