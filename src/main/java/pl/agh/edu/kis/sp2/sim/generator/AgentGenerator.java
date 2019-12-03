package pl.agh.edu.kis.sp2.sim.generator;

import pl.agh.edu.kis.sp2.sim.generator.agent.Agent;
import pl.agh.edu.kis.sp2.sim.generator.graph.LocalizationVertex;

import java.util.*;

public class AgentGenerator {

    private LocalizationGenerator localizationGenerator;

    public AgentGenerator() {
        this.localizationGenerator = new LocalizationGenerator();
    }

    public List<Agent> generateAgentsOnVertex(LocalizationVertex localizationVertex, int agentsCountBoundary, List<Long> groupIds) {
        Random g = new Random();
        int agentsCount = g.nextInt(agentsCountBoundary) + 20;
        List<Agent> agents = new ArrayList<>();
        Set<LocalizationVertex> visited = new HashSet<>();
        visited.add(localizationVertex);

        for (int i = 0; i < agentsCount; i++) {
            Long groupId = g.nextBoolean() ? groupIds.get(g.nextInt(groupIds.size())) : null;
            Agent agent = new Agent.Builder()
                    .agentId((long) i)
                    .currentLocalization(localizationVertex.getLocalization())
                    .currentVertex(localizationVertex)
                    .preferredRouteWeight(g.nextInt(4) + 1)
//                    .preferredRouteWeight(i)
                    .wantsToMove(g.nextBoolean())
                    .visitedVertexes(visited)
                    .groupId(groupId)
                    .leader(groupId != null ? agents
                            .stream()
                            .filter(a -> a.getGroupId() != null && a.getGroupId().equals(groupId))
                            .findFirst()
                            .orElse(null): null)
                    .groupAgents(new ArrayList<>())
                    .build();

            if (agent.getLeader() != null) {
                agent.getLeader().getGroupAgents().add(agent);
            }
            System.out.println(agent);
            agents.add(agent);
        }

        return agents;
    }
}
