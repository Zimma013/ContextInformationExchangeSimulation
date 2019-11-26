package pl.agh.edu.kis.sp2.sim;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.*;
import org.jgrapht.io.ComponentNameProvider;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.GraphExporter;
import org.jgrapht.traverse.DepthFirstIterator;
import pl.agh.edu.kis.sp2.sim.generator.AgentGenerator;
import pl.agh.edu.kis.sp2.sim.generator.LocalizationGenerator;
import pl.agh.edu.kis.sp2.sim.generator.WeatherGenerator;
import pl.agh.edu.kis.sp2.sim.generator.agent.Agent;
import pl.agh.edu.kis.sp2.sim.generator.graph.Edge;
import pl.agh.edu.kis.sp2.sim.generator.graph.LocalizationVertex;
import pl.agh.edu.kis.sp2.sim.generator.graph.Node;
import pl.agh.edu.kis.sp2.sim.generator.wftr.Localization;

import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class Application {

    public static void main(String[] args) throws URISyntaxException, ExportException, org.jgrapht.io.ExportException {
        WeatherGenerator wg = new WeatherGenerator();
        System.out.println(wg.generateWeather());
        System.out.println(wg.generateWeather());
        System.out.println(wg.generateWeather());
        System.out.println(wg.generateWeather());
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
        SimpleDirectedWeightedGraph<LocalizationVertex, DefaultWeightedEdge> localizationGraph = createGraph(l1,l2);
        // note undirected edges are printed as: {<v1>,<v2>}
        System.out.println("-- toString output");
        System.out.println("-- (1,2):20");
        System.out.println("-- (2,3):2");
        System.out.println("-- (3,4):3");
        System.out.println("-- (1,4):4");
        System.out.println("-- (3,2):4");
        System.out.println("-- (1,3):1");
        //System.out.println(stringGraph.toString());
//		System.out.println(localizationGraph.toString());
        System.out.println();


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

                        if (agent.getVisitedVertexes().contains(localizationVertex)) {
                            localizationGraph.getEdgeTarget(edge).addAgentMovingToLocation(agent, new Localization.Builder().latitude(new BigDecimal(0.00002D)).longitude(new BigDecimal(0.00002D)).build());
                            agent.addVertexToVisited(localizationVertex);
                            localizationVertex.removeAgentFromLocation(agent);
                        }
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

    /**
     * Creates a toy directed graph based on URI objects that represents link structure.
     *
     * @return a graph based on URI objects.
     */

    private static SimpleDirectedWeightedGraph<LocalizationVertex,DefaultWeightedEdge> createGraph(LocalizationVertex localizationV1,LocalizationVertex localizationV2)
    {
        SimpleDirectedWeightedGraph<LocalizationVertex, DefaultWeightedEdge> g = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
        LocalizationGenerator localizationGenerator = new LocalizationGenerator();

        LocalizationVertex v1 = localizationV1;

        LocalizationVertex v2 = localizationV2;
        LocalizationVertex v3 = new LocalizationVertex.Builder()
                .agentsInLocalization(new ArrayList<>())
                .agentsMovingToLocalization(new ArrayList<>())
                .localization(localizationGenerator.generateLocalization())
                .vertexId(3)
                .build();
        LocalizationVertex v4 = new LocalizationVertex.Builder()
                .agentsInLocalization(new ArrayList<>())
                .agentsMovingToLocalization(new ArrayList<>())
                .localization(localizationGenerator.generateLocalization())
                .vertexId(4)
                .build();

        // add the vertices
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);

        // add edges to create a circuit
        DefaultWeightedEdge e1 = g.addEdge(v1, v2);
        g.setEdgeWeight(e1, 20);
        DefaultWeightedEdge e2 = g.addEdge(v2, v3);
        g.setEdgeWeight(e2, 2);
        DefaultWeightedEdge e3 = g.addEdge(v3, v4);
        g.setEdgeWeight(e3, 3);
        DefaultWeightedEdge e4 = g.addEdge(v1, v4);
        g.setEdgeWeight(e4, 4);
        DefaultWeightedEdge e5 = g.addEdge(v3, v2);
        g.setEdgeWeight(e5, 4);
        DefaultWeightedEdge e6 = g.addEdge(v1, v3);
        g.setEdgeWeight(e6, 1);



        return g;
    }

}
