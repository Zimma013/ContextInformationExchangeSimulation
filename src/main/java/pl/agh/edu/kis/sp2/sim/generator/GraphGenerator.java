package pl.agh.edu.kis.sp2.sim.generator;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import pl.agh.edu.kis.sp2.sim.generator.graph.LocalizationVertex;

import java.util.ArrayList;

public class GraphGenerator {
    public static SimpleWeightedGraph<LocalizationVertex, DefaultWeightedEdge> createGraph(LocalizationVertex localizationV1, LocalizationVertex localizationV2)
    {
        SimpleWeightedGraph<LocalizationVertex, DefaultWeightedEdge> g = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
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
        DefaultWeightedEdge e5 = g.addEdge(v1, v3);
        g.setEdgeWeight(e5, 1);



        return g;
    }
}
