package pl.agh.edu.kis.sp2.sim;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import pl.agh.edu.kis.sp2.sim.generator.AgentGenerator;
import pl.agh.edu.kis.sp2.sim.generator.GraphGenerator;
import pl.agh.edu.kis.sp2.sim.generator.LocalizationGenerator;
import pl.agh.edu.kis.sp2.sim.generator.graph.LocalizationVertex;

import java.util.ArrayList;


public class Application {

    public static void main(String[] args) {
        LocalizationGenerator localizationGenerator = new LocalizationGenerator();
        LocalizationVertex l1 = new LocalizationVertex.Builder()
                .agentsInLocalization(new ArrayList<>())
                .agentsMovingToLocalization(new ArrayList<>())
                .localization(localizationGenerator.generateLocalization())
                .vertexId(1)
                .build();

        SimpleWeightedGraph<LocalizationVertex, DefaultWeightedEdge> localizationGraph = GraphGenerator.createGraph(l1,3, 4);

        WhiteBoxSystemSimulator simulator = new WhiteBoxSystemSimulator.Builder()
                .localizationDataRedundancyCoefficient(0.35)
                .mountainRoutesGraph(localizationGraph)
                .rootVertex(l1)
                .population(new AgentGenerator().generateAgentsOnVertex(l1, 300))
                .simulatedWeatherConditionsMode(1)
                .build();

        simulator.simulate(4);

    }
}
