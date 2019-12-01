package pl.agh.edu.kis.sp2.sim.generator;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import pl.agh.edu.kis.sp2.sim.generator.graph.LocalizationVertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GraphGenerator {

    public static SimpleWeightedGraph<LocalizationVertex, DefaultWeightedEdge> createGraph(LocalizationVertex rootVertex, int vertexCount, int maxWeight) {
        Random gen = new Random();
        LocalizationGenerator localizationGenerator = new LocalizationGenerator();

        SimpleWeightedGraph<LocalizationVertex, DefaultWeightedEdge> g = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        // add the vertices
        g.addVertex(rootVertex);

        for (int i = 0; i < vertexCount; i++) {
            g.addVertex(new LocalizationVertex.Builder()
                    .agentsInLocalization(new ArrayList<>())
                    .agentsMovingToLocalization(new ArrayList<>())
                    .localization(localizationGenerator.generateLocalization())
                    .vertexId(rootVertex.getVertexId() + i + 1)
                    .build());
        }

        for (LocalizationVertex vertex : g.vertexSet()) {

            // Copy constructor used to not use or damage graph's set of vertices
            List<LocalizationVertex> verticesWithoutCurrent = new ArrayList<>(g.vertexSet());

            // Removed to nullify probability of trying to create an edge to self
            verticesWithoutCurrent.remove(vertex);

            // Adding 1 ensures cohesion of graph
            int edgeCount = gen.nextInt(vertexCount/2) + 1;

            // Add edges
            for (int i = 0; i < edgeCount; i++) {

                // Random generates random int from 0 inclusive to bounds exclusive, so if used as a random index generator of list it'll always remain within array index bounds
                DefaultWeightedEdge edge = null;

                // If edge was added properly (did'nt exist previously)
                while (edge == null) {
                    edge = g.addEdge(vertex, verticesWithoutCurrent.get(gen.nextInt(verticesWithoutCurrent.size())));
                    if (edge != null) {
                        int weight = gen.nextInt(maxWeight) + 1;
                        g.setEdgeWeight(edge, weight);

                        System.out.println("Weight of edge --- " + edge.toString() + " --------- w: " + weight);
                    }
                }
            }

        }

        return g;
    }
}
