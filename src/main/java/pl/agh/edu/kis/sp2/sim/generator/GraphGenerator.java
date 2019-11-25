package pl.agh.edu.kis.sp2.sim.generator;

import pl.agh.edu.kis.sp2.sim.generator.graph.Edge;
import pl.agh.edu.kis.sp2.sim.generator.graph.Node;

import java.util.ArrayList;
import java.util.Random;

public class GraphGenerator {
    private LocalizationGenerator localizationGenerator;

    public GraphGenerator() {
        this.localizationGenerator = new LocalizationGenerator();
    }

    //returns root node
    public Node generateGraphLevel(Node rootNode, int nodeCount, int subLevelCount) {

        if (rootNode == null) {
            rootNode = new Node.Builder()
                    .localization(localizationGenerator.generateLocalization())
                    .edges(new ArrayList<>())
                    .build();

            nodeCount--;
        }

        if (nodeCount <= 0) {
            return rootNode;
        }

        for (int i = 0; i < nodeCount ; i++) {

            Node node = new Node.Builder()
                    .edges(new ArrayList<>())
                    .localization(localizationGenerator.generateLocalization())
                    .build();

            rootNode.addEdge(new Edge.Builder()
                    .destination(node)
                    .origin(rootNode)
                    .weight(5)
                    .build());

            if (subLevelCount > 1) {
                subLevelCount--;
                generateGraphLevel(node, nodeCount, subLevelCount);
            }
        }

        return rootNode;
    }

    public Node generateRandomGraphLevel(Node rootNode, int nodeCountBoundary, int subLevelCountBoundary) {

        Random g = new Random();
        if (nodeCountBoundary <= 0) {
            nodeCountBoundary = 1;
        }
        int nodeCount = g.nextInt(nodeCountBoundary) + 1;

        if (rootNode == null) {
            rootNode = new Node.Builder()
                    .localization(localizationGenerator.generateLocalization())
                    .edges(new ArrayList<>())
                    .build();

            nodeCount--;
        }

        if (nodeCount <= 0) {
            return rootNode;
        }

        System.out.println("Random -- (nodeCount) ----- " + nodeCount);
        for (int i = 0; i < nodeCount ; i++) {
            int subLevelCount = g.nextInt(subLevelCountBoundary);
            int nextNodeCount = g.nextInt(nodeCountBoundary);

            System.out.println("Random -- (nextNodeCount) ----- " + nextNodeCount);
            System.out.println("Random -- (subLevelCount) ----- " + subLevelCount);

            Node node = new Node.Builder()
                    .edges(new ArrayList<>())
                    .localization(localizationGenerator.generateLocalization())
                    .build();

            rootNode.addEdge(new Edge.Builder()
                    .destination(node)
                    .origin(rootNode)
                    .weight(5)
                    .build());

            /*node.addEdge(new Edge.Builder()
                    .destination(rootNode)
                    .origin(node)
                    .weight(5D)
                    .build());*/

            if (subLevelCount > 1) {
                generateRandomGraphLevel(node, nextNodeCount, subLevelCount);
            }
        }

        return rootNode;
    }
}
