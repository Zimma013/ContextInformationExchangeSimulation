package pl.agh.edu.kis.sp2.sim;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import pl.agh.edu.kis.sp2.sim.excel.Data;
import pl.agh.edu.kis.sp2.sim.excel.ExcelWriter;
import pl.agh.edu.kis.sp2.sim.generator.AgentGenerator;
import pl.agh.edu.kis.sp2.sim.generator.GraphGenerator;
import pl.agh.edu.kis.sp2.sim.generator.LocalizationGenerator;
import pl.agh.edu.kis.sp2.sim.generator.WeatherSensorGenerator;
import pl.agh.edu.kis.sp2.sim.generator.agent.Agent;
import pl.agh.edu.kis.sp2.sim.generator.graph.LocalizationVertex;
import pl.agh.edu.kis.sp2.sim.whitebox.WhiteBoxSystemSimulator;
import pl.agh.edu.kis.sp2.sim.whitebox.WhiteBoxSystemSimulatorConfigurator;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Application {
    public static Data data = new Data(0,0,0,0,0);
    public static ExcelWriter excelWriter = new ExcelWriter();
    public static void main(String[] args) throws IOException {
        int simulatedWeatherConditionsMode = 1;
        LocalizationGenerator localizationGenerator = new LocalizationGenerator();
        LocalizationVertex l1 = new LocalizationVertex.Builder()
                .agentsInLocalization(new ArrayList<>())
                .agentsMovingToLocalization(new ArrayList<>())
                .localization(localizationGenerator.generateLocalization())
                .vertexId(1)
                .build();
        List<Agent> population = new AgentGenerator().generateAgentsOnVertex(l1, 300, Arrays.asList(1L, 2L, 3L, 4L));
        l1.setAgentsInLocalization(population);

        List<Agent> dangerousAnimalPopulation = new AgentGenerator().generateAgents(50);

        SimpleWeightedGraph<LocalizationVertex, DefaultWeightedEdge> localizationGraph = GraphGenerator.createGraph(l1,3, 4);

        WhiteBoxSystemSimulator simulator = new WhiteBoxSystemSimulatorConfigurator()
                .localizationDataRedundancyCoefficient(0.35)
                .mountainRoutesGraph(localizationGraph)
                .rootVertex(l1)
                .population(population)
                .simulatedWeatherConditionsMode(simulatedWeatherConditionsMode)
                .weatherSensors(new WeatherSensorGenerator().generateWeatherSensors(8, simulatedWeatherConditionsMode))
                .maxDistanceToLeader(new BigDecimal(0.05D))
                .minDistanceToAnimal(new BigDecimal(0.01D))
                .dangerousAnimalPopulation(dangerousAnimalPopulation)
                .build();

        simulator.simulate(200);
        String excelFilePath = "NiceContextData.xls";

        excelWriter.writeExcel(excelWriter.getDataList(), excelFilePath);

    }
}
