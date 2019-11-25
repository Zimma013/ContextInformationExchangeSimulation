package pl.agh.edu.kis.sp2.sim.generator;

import pl.agh.edu.kis.sp2.sim.generator.agent.Agent;
import pl.agh.edu.kis.sp2.sim.generator.graph.LocalizationVertex;

import java.util.*;

public class AgentGenerator {

    private LocalizationGenerator localizationGenerator;

    public AgentGenerator() {
        this.localizationGenerator = new LocalizationGenerator();
    }

    public List<Agent> generateAgentsOnVertex(LocalizationVertex localizationVertex, int agentsCountBoundary) {
        Random g = new Random();
        int agentsCount = g.nextInt(agentsCountBoundary) + 20;
        List<Agent> agents = new ArrayList<>();
        Set<LocalizationVertex> visited = new HashSet<>();
        visited.add(localizationVertex);

        for (int i = 0; i < agentsCount; i++) {
            agents.add(new Agent.Builder()
                    .currentLocalization(localizationVertex.getLocalization())
                    .currentVertex(localizationVertex)
//                    .preferredRouteWeight(g.nextInt(5) + 1)
                    .preferredRouteWeight(i)
                    .visitedVertexes(visited)
                    .build());
        }

        return agents;
    }
}
