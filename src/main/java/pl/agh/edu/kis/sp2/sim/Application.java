package pl.agh.edu.kis.sp2.sim;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import pl.agh.edu.kis.sp2.sim.generator.AgentGenerator;
import pl.agh.edu.kis.sp2.sim.generator.LocalizationGenerator;
import pl.agh.edu.kis.sp2.sim.generator.WeatherGenerator;
import pl.agh.edu.kis.sp2.sim.generator.agent.Agent;
import pl.agh.edu.kis.sp2.sim.generator.enumeration.Avalanche;
import pl.agh.edu.kis.sp2.sim.generator.graph.LocalizationVertex;
import pl.agh.edu.kis.sp2.sim.generator.wftr.Localization;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.agh.edu.kis.sp2.sim.generator.GraphGenerator.createGraph;


public class Application {

    public static void main(String[] args) {
        WeatherGenerator wg = new WeatherGenerator();
        System.out.println(wg.generateWeather());
        System.out.println(wg.generateWeather());
        System.out.println(wg.generateWeather());
        System.out.println(wg.generateWeather());
        System.out.println(Avalanche.getRandomAvalanche());
//	    Simulator sim = new Simulator();

//	    sim.showRandValues();
//	    sim.showRandValues();
//	    sim.showRandValues();

	    /*LocalizationGenerator localizationGenerator = new LocalizationGenerator();
	    Node a = new Node();
	    a.setLocalization(localizationGenerator.generateLocalization());
	    Node b = new Node();
	    b.setLocalization(localizationGenerator.generateLocalization());
		Edge e = new Edge();
		e.setDestination(b);
		e.setWeight(5);
		a.addEdge(e);*/

//	    System.out.println("Graph ------------ " + );
//		Node root = sim.getGraphGenerator().generateGraphLevel(null, 3, 2);
		/*Node root = sim.getGraphGenerator().generateRandomGraphLevel(new Node.Builder()
				.edges(new ArrayList<>())
				.localization(sim.getLocalizationGenerator()
						.generateLocalization())
				.build(), 4, 5);*/

//		Node n = root;

        //Graph<String, DefaultEdge> stringGraph = createStringGraph();
        LocalizationGenerator localizationGenerator = new LocalizationGenerator();
        AgentGenerator agentGenerator = new AgentGenerator();

        LocalizationVertex l1 = new LocalizationVertex.Builder()
                .agentsInLocalization(new ArrayList<>())
                .agentsMovingToLocalization(new ArrayList<>())
                .localization(localizationGenerator.generateLocalization())
                .vertexId(1)
                .build();
        l1.setAgentsInLocalization(agentGenerator.generateAgentsOnVertex(l1, 30));

        LocalizationVertex l2 = new LocalizationVertex.Builder()
                .agentsInLocalization(new ArrayList<>())
                .agentsMovingToLocalization(new ArrayList<>())
                .localization(localizationGenerator.generateLocalization())
                .vertexId(2)
                .build();
        SimpleWeightedGraph<LocalizationVertex, DefaultWeightedEdge> localizationGraph = createGraph(l1,l2);


        for(int i = 0; i < 3; i++) {
            Iterator<LocalizationVertex> iterator = new DepthFirstIterator<>(localizationGraph, l1);
            while (iterator.hasNext()) {
                LocalizationVertex localizationVertex = iterator.next();
                Set<DefaultWeightedEdge> edges = localizationGraph.outgoingEdgesOf(localizationVertex);
                System.out.println("Current ------ " + localizationVertex);
//            System.out.println(edges);

                edges.forEach(edge -> {
//                localizationVertex.getAgentsInLocalization().forEach(agent -> {
                    List<Agent> agents = localizationVertex.getAgentsInLocalization()
                            .stream()
                            .filter(agent -> localizationGraph.getEdgeWeight(edge) <= agent.getPreferredRouteWeight())
                            .collect(Collectors.toList());
                    System.out.println("Target ------ " + localizationGraph.getEdgeTarget(edge));
                    for (Agent agent : agents) {
//                        if (agent.isWantsToMove()) {

                            if (agent.getVisitedVertexes().contains(localizationVertex)) {
                                localizationGraph.getEdgeTarget(edge).addAgentMovingToLocation(agent, new Localization.Builder().latitude(new BigDecimal(0.00002D)).longitude(new BigDecimal(0.00002D)).build());
                                agent.addVertexToVisited(localizationVertex);
                                localizationVertex.removeAgentFromLocation(agent);
                            }

//                        }
//                        agent.setWantsToMove(new Random().nextBoolean());
                    }
                /* {

                    }*/
//                });
                });
            }
            iterator = new DepthFirstIterator<>(localizationGraph, l1);
            while (iterator.hasNext()) {
                LocalizationVertex localizationVertex = iterator.next();
                localizationVertex.getAgentsInLocalization().addAll(localizationVertex.getAgentsMovingToLocalization());
                localizationVertex.setAgentsMovingToLocalization(new ArrayList<>());
            }

            System.out.println();
            System.out.println();
        }
    }
}
