package pl.agh.edu.kis.sp2.sim.generator;

import pl.agh.edu.kis.sp2.sim.generator.agent.Agent;
import pl.agh.edu.kis.sp2.sim.generator.graph.Node;
import pl.agh.edu.kis.sp2.sim.generator.wftr.Localization;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AgentGenerator {

    private LocalizationGenerator localizationGenerator;

    public AgentGenerator() {
        this.localizationGenerator = new LocalizationGenerator();
    }

    public List<Agent> generateAgentsOnVertex(Localization localization, int agentsCountBoundary) {
        Random g = new Random();
        int agentsCount = g.nextInt(agentsCountBoundary);
        List<Agent> agents = new ArrayList<>();

        for (int i = 0; i < agentsCount; i++) {
            agents.add(new Agent.Builder()
                    .currentLocalization(localization)
                    .preferredRouteWeight(g.nextInt(5) + 1)
                    .build());
        }

        return agents;
    }
}
