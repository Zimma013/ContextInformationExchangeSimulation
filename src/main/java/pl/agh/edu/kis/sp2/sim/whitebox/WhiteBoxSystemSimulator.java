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

import static pl.agh.edu.kis.sp2.sim.Application.data;
import static pl.agh.edu.kis.sp2.sim.Application.excelWriter;

public class WhiteBoxSystemSimulator {

	private List<Agent> population;
	private List<Agent> dangerousAnimalPopulation;
	private SimpleWeightedGraph<LocalizationVertex, DefaultWeightedEdge> mountainRoutesGraph;
	private LocalizationVertex rootVertex;
	private int simulatedWeatherConditionsMode;
	private double localizationDataRedundancyCoefficient;
	private List<WeatherSensor> weatherSensors;
	private BigDecimal maxDistanceToLeader;
	private BigDecimal minDistanceToAnimal;
	private int movementRate = 10;
	private double currentHour = 5;

	private Double randomLocalizationCompensationRate = 100D;
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
			System.out.println("========================== Agent routes ==================================");
			findRoutesForAgents();
			System.out.println("========================== Movement ======================================");
			movePopulationWithConstSpeed();
			moveDangerousAnimalsWithConstSpeed();
			System.out.println("========================== Relationship check ============================");
			checkForRelationshipConstraints();
			System.out.println("========================== Finish movement ===============================");
			finishPopulationMovement();
			System.out.println("========================== Generate new weather ==========================");
			generateNewWeatherInSensors();
			System.out.println("==========================================================================");
			System.out.println();
			System.out.println();
            int negation = generator.nextBoolean() ? 1 : -1;

            double randDouble = generator.nextDouble()/10;
            System.out.println();
			data.setNumberofLocationData((int)(100*(localizationDataRedundancyCoefficient + negation * randDouble)));
            data.setNumberofTime(data.getNumberofTime()+1);
            excelWriter.addToDataList(data);
			data = new Data(0,0,0,0,0);
			this.currentHour = this.currentHour+0.10;

			System.out.println();
			System.out.println();
		}
	}

	private void moveDangerousAnimalsWithConstSpeed() {
		for (Agent animal : dangerousAnimalPopulation) {
			Localization currentLocalization = animal.getCurrentLocalization();
			animal.setCurrentLocalization(new Localization.Builder()
					.longitude(currentLocalization.getLongitude()
							.subtract(new BigDecimal(generator.nextDouble()/randomLocalizationCompensationRate)
//								.multiply(new BigDecimal(generator.nextBoolean() ? 1 : -1))
							)
							.divide(new BigDecimal(movementRate + generator.nextInt(15)), 10, RoundingMode.HALF_UP))
					.latitude(currentLocalization.getLatitude()
							.subtract(new BigDecimal(generator.nextDouble()/randomLocalizationCompensationRate)
//								.multiply(new BigDecimal(generator.nextBoolean() ? 1 : -1))
							)
							.divide(new BigDecimal(movementRate + generator.nextInt(15)), 10, RoundingMode.HALF_UP))
					.build());
		}
	}

	private void generateNewWeatherInSensors() {
		weatherSensors.forEach(weatherSensor -> {
			if (generator.nextBoolean()) {
				weatherSensor.generateWeatherData(simulatedWeatherConditionsMode);
			}
		});
	}

	private void findRoutesForAgents() {
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
							.filter(agent -> currentHour >= agent.getMinPreferredHour())
							.filter(agent -> currentHour <= agent.getMaxPreferredHour())
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
	}

	private void checkForRelationshipConstraints() {
		List<Agent> leaders = population.stream()
			.filter(agent -> agent.getLeader() == null && agent.getGroupId() != null)
			.collect(Collectors.toList());
		System.out.println("Leader ids --------------------- " + leaders.stream().map(Agent::getAgentId).collect(Collectors.toList()));
		for (Agent leader : leaders) {
			System.out.println("Agent ids --------------------- " + leader.getGroupAgents().stream().map(Agent::getAgentId).collect(Collectors.toList()));
			System.out.println("Leader groupId --------------------- " + leader.getGroupId());
//			System.out.println("Leader.localization ---------------------------------- " + leader.getCurrentLocalization());
			leader.getGroupAgents().forEach(agent -> {
//				System.out.println("Agent.localization ---------------------------------- " + agent.getCurrentLocalization());
				BigDecimal currentDistanceToLeader = new BigDecimal(LocalizationDistanceUtility.distance(
						agent.getCurrentLocalization().getLatitude().doubleValue(),
						agent.getCurrentLocalization().getLongitude().doubleValue(),
						leader.getCurrentLocalization().getLatitude().doubleValue(),
						leader.getCurrentLocalization().getLongitude().doubleValue(),
						'K'))
//						.setScale(10, RoundingMode.HALF_UP)
						;
//				System.out.println("NOT-SCREAM ------------ agent is " + currentDistanceToLeader.doubleValue() + "KM away from group leader");
//				System.out.println("NOT-SCREAM ------------ Max is " + this.maxDistanceToLeader.doubleValue() + "KM away");
				if (currentDistanceToLeader.doubleValue() >= this.maxDistanceToLeader.doubleValue()) {
					System.out.println("SCREAM ------------------------------------------------------------ ");
					System.out.println("SCREAM ------------ agent is " + currentDistanceToLeader + "KM away from group leader");
					System.out.println("SCREAM ------------------------------------------------------------ ");
					data.setNumberofRelationsData(data.getNumberofRelationsData()+1);
                    data.setNumberofTime(data.getNumberofTime()+1);
				}
			});
		}

		for (Agent animal : dangerousAnimalPopulation) {
			population.forEach(agent -> {
				Double currentDistanceToAnimal = LocalizationDistanceUtility.distance(
						agent.getCurrentLocalization().getLatitude().doubleValue(),
						agent.getCurrentLocalization().getLongitude().doubleValue(),
						animal.getCurrentLocalization().getLatitude().doubleValue(),
						animal.getCurrentLocalization().getLongitude().doubleValue(),
						'K');
				if (currentDistanceToAnimal <= this.minDistanceToAnimal.doubleValue()) {
					System.out.println("SCREAM ANIMAL --------------------------------------------------- ");
					System.out.println("SCREAM ANIMAL --- agent is " + currentDistanceToAnimal + "KM away from dangerous animal");
					System.out.println("SCREAM ANIMAL --------------------------------------------------- ");
					data.setNumberofRelationsData(data.getNumberofRelationsData()+1);
                    data.setNumberofTime(data.getNumberofTime()+1);
				}
			});
		}

	}


	private void checkForRelationshipConstraints(Agent agent) {
		if(agent.getLeader() != null) {
			System.out.println("Agent ids --------------------- " + agent.getLeader().getGroupAgents().stream().map(Agent::getAgentId).collect(Collectors.toList()));
			System.out.println("Leader groupId --------------------- " + agent.getLeader().getGroupId());
//			System.out.println("Leader.localization ---------------------------------- " + leader.getCurrentLocalization());
//				System.out.println("Agent.localization ---------------------------------- " + agent.getCurrentLocalization());
			BigDecimal currentDistanceToLeader = new BigDecimal(LocalizationDistanceUtility.distance(
					agent.getCurrentLocalization().getLatitude().doubleValue(),
					agent.getCurrentLocalization().getLongitude().doubleValue(),
					agent.getLeader().getCurrentLocalization().getLatitude().doubleValue(),
					agent.getLeader().getCurrentLocalization().getLongitude().doubleValue(),
					'K'))
//						.setScale(10, RoundingMode.HALF_UP)
					;
//			System.out.println("NOT-SCREAM ------------ agent is " + currentDistanceToLeader.doubleValue() + "KM away from group leader");
//				System.out.println("NOT-SCREAM ------------ Max is " + this.maxDistanceToLeader.doubleValue() + "KM away");
			if (currentDistanceToLeader.doubleValue() >= this.maxDistanceToLeader.doubleValue()) {
				System.out.println("SCREAM ------------------------------------------------------------ ");
				System.out.println("SCREAM ------------ agent is " + currentDistanceToLeader + "KM away from group leader");
				System.out.println("SCREAM ------------------------------------------------------------ ");
                data.setNumberofRelationsData(data.getNumberofRelationsData()+1);
                data.setNumberofTime(data.getNumberofTime()+1);
			}
		}

		for (Agent animal : dangerousAnimalPopulation) {
			Double currentDistanceToAnimal = LocalizationDistanceUtility.distance(
					agent.getCurrentLocalization().getLatitude().doubleValue(),
					agent.getCurrentLocalization().getLongitude().doubleValue(),
					animal.getCurrentLocalization().getLatitude().doubleValue(),
					animal.getCurrentLocalization().getLongitude().doubleValue(),
					'K');
			if (currentDistanceToAnimal <= this.minDistanceToAnimal.doubleValue()) {
				System.out.println("SCREAM ANIMAL --------------------------------------------------- ");
				System.out.println("SCREAM ANIMAL --- agent is " + currentDistanceToAnimal + "KM away from dangerous animal");
				System.out.println("SCREAM ANIMAL --------------------------------------------------- ");
                data.setNumberofRelationsData(data.getNumberofRelationsData()+1);
                data.setNumberofTime(data.getNumberofTime()+1);
			}
		}
	}

	private void movePopulationWithConstSpeed() {
		for (LocalizationVertex vertex : mountainRoutesGraph.vertexSet()) {
			vertex.getAgentsMovingToLocalization()
					.forEach(agent -> {
//						System.out.println("Agent distance to dest --------- " + agent.getDistanceToDestination());


								Localization currentLocalization = agent.getCurrentLocalization();
								Localization newLocalization = new Localization.Builder()
										.longitude(currentLocalization.getLongitude().add(currentLocalization.getLongitude()
												.subtract(agent.getDestinationVertex().getLocalization().getLongitude())
												.divide(new BigDecimal(movementRate), 10, RoundingMode.HALF_UP)
												.subtract(new BigDecimal(generator.nextDouble()/randomLocalizationCompensationRate)
														.multiply(new BigDecimal(generator.nextBoolean() ? 1 : -1))
														.setScale(10, RoundingMode.HALF_UP)
												)).setScale(10, RoundingMode.HALF_UP))
										.latitude(currentLocalization.getLatitude().add(currentLocalization.getLatitude()
												.subtract(agent.getDestinationVertex().getLocalization().getLatitude())
												.divide(new BigDecimal(movementRate), 10, RoundingMode.HALF_UP)
												.subtract(new BigDecimal(generator.nextDouble()/randomLocalizationCompensationRate)
														.multiply(new BigDecimal(generator.nextBoolean() ? 1 : -1))
														.setScale(10, RoundingMode.HALF_UP)
												)).setScale(10, RoundingMode.HALF_UP)
								)
								.build();
								agent.setCurrentLocalization(newLocalization);

								agent.setWeatherSensor(getClosestWeatherSensor(agent));
								agent.setCurrentWeather(agent.getWeatherSensor().getWeather());

								checkForRelationshipConstraints(agent);

								double movementSpeed = LocalizationDistanceUtility.distance(
										agent.getCurrentLocalization().getLatitude().doubleValue(), agent.getCurrentLocalization().getLongitude().doubleValue(),
										agent.getDestinationVertex().getLocalization().getLatitude().doubleValue(), agent.getDestinationVertex().getLocalization().getLongitude().doubleValue(),
										'K') / (movementRate ); //* mountainRoutesGraph.getEdge(agent.getCurrentVertex(), agent.getDestinationVertex()).getWeight()
								agent.setDistanceToDestination(agent.getDistanceToDestination() - movementSpeed);
//						System.out.println("Agent distance to dest 2 --------- " + agent.getDistanceToDestination());
								data.setNumberofActivityData(data.getNumberofActivityData()+1);
							}
					);
		}
//		checkForRelationshipConstraints();
	}

	private WeatherSensor getClosestWeatherSensor(Agent agent) {
		WeatherSensor closestSensor = weatherSensors.get(0);
		Double minDistance = 99999999D;
		for (WeatherSensor sensor : weatherSensors) {
			Double distance = LocalizationDistanceUtility.distance(sensor.getLocalization().getLatitude().doubleValue(), sensor.getLocalization().getLongitude().doubleValue(), agent.getCurrentLocalization().getLatitude().doubleValue(), agent.getCurrentLocalization().getLongitude().doubleValue(), 'K');
			if (distance <= minDistance) {
				closestSensor = sensor;
			}
		}
//		System.out.println("Closest sensor ------------------------------ " + closestSensor);
		return closestSensor;
	}

	private void finishPopulationMovement() {
		Iterator<LocalizationVertex> iterator = new BreadthFirstIterator<>(this.mountainRoutesGraph, rootVertex);
		while (iterator.hasNext()) {
			LocalizationVertex localizationVertex = iterator.next();
			List<Agent> agentsReachedTheDestination = localizationVertex.getAgentsMovingToLocalization().stream()
					.filter(agent -> agent.getDistanceToDestination() <= 0D)
					.collect(Collectors.toList());

			if (!agentsReachedTheDestination.isEmpty()) {
                data.setNumberofTime(data.getNumberofTime()+1);
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

	public void setMaxDistanceToLeader(BigDecimal maxDistanceToLeader) {
		this.maxDistanceToLeader = maxDistanceToLeader;
	}

	public void setDangerousAnimalPopulation(List<Agent> dangerousAnimalPopulation) {
		this.dangerousAnimalPopulation = dangerousAnimalPopulation;
	}

	public void setMinDistanceToAnimal(BigDecimal minDistanceToAnimal) {
		this.minDistanceToAnimal = minDistanceToAnimal;
	}
}
