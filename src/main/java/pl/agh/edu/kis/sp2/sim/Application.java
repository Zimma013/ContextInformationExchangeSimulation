package pl.agh.edu.kis.sp2.sim;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.*;
import org.jgrapht.io.ComponentNameProvider;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.GraphExporter;
import org.jgrapht.traverse.DepthFirstIterator;
import pl.agh.edu.kis.sp2.sim.generator.LocalizationGenerator;
import pl.agh.edu.kis.sp2.sim.generator.graph.Edge;
import pl.agh.edu.kis.sp2.sim.generator.graph.Node;
import pl.agh.edu.kis.sp2.sim.generator.wftr.Localization;

import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.server.ExportException;
import java.util.Iterator;
import java.util.List;


public class Application {

    public static void main(String[] args) throws URISyntaxException, ExportException, org.jgrapht.io.ExportException {
	    Simulator sim = new Simulator();

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
		Node root = sim.getGraphGenerator().generateGraphLevel(null, 3, 2);
		/*Node root = sim.getGraphGenerator().generateRandomGraphLevel(new Node.Builder()
				.edges(new ArrayList<>())
				.localization(sim.getLocalizationGenerator()
						.generateLocalization())
				.build(), 4, 5);*/

		Node n = root;

		//Graph<String, DefaultEdge> stringGraph = createStringGraph();
		SimpleDirectedWeightedGraph<Localization, DefaultWeightedEdge> localizationGraph = createGraph();
		// note undirected edges are printed as: {<v1>,<v2>}
		System.out.println("-- toString output");
		//System.out.println(stringGraph.toString());
		System.out.println(localizationGraph.toString());
		System.out.println();
		//List shortest_path =   DijkstraShortestPath.findPathBetween(localizationGraph, "vertex1", "vertex5");
		//System.out.println(shortest_path);


		// create a graph based on URI objects
		//Graph<URI, DefaultEdge> hrefGraph = createHrefGraph();

		// find the vertex corresponding to www.jgrapht.org
		//URI start = hrefGraph
//				.vertexSet().stream().filter(uri -> uri.getHost().equals("www.jgrapht.org")).findAny()
//				.get();
//
//
//		// perform a graph traversal starting from that vertex
//		System.out.println("-- traverseHrefGraph output");
//		traverseHrefGraph(hrefGraph, start);
//		System.out.println();
//
//		System.out.println("-- renderHrefGraph output");
//		renderHrefGraph(hrefGraph);
//		System.out.println();


	}

	/**
	 * Creates a toy directed graph based on URI objects that represents link structure.
	 *
	 * @return a graph based on URI objects.
	 */
//	private static Graph<URI, DefaultEdge> createHrefGraph()
//			throws URISyntaxException
//	{
//
//		Graph<URI, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);
//
//		URI google = new URI("http://www.google.com");
//		URI wikipedia = new URI("http://www.wikipedia.org");
//		URI jgrapht = new URI("http://www.jgrapht.org");
//
//		// add the vertices
//		g.addVertex(google);
//		g.addVertex(wikipedia);
//		g.addVertex(jgrapht);
//
//		// add edges to create linking structure
//		g.addEdge(jgrapht, wikipedia);
//		g.addEdge(google, jgrapht);
//		g.addEdge(google, wikipedia);
//		g.addEdge(wikipedia, google);
//
//
//		return g;
//	}
//
//	/**
//	 * Traverse a graph in depth-first order and print the vertices.
//	 *
//	 * @param hrefGraph a graph based on URI objects
//	 *
//	 * @param start the vertex where the traversal should start
//	 */
//	private static void traverseHrefGraph(Graph<URI, DefaultEdge> hrefGraph, URI start)
//	{
//		Iterator<URI> iterator = new DepthFirstIterator<>(hrefGraph, start);
//		while (iterator.hasNext()) {
//			URI uri = iterator.next();
//			System.out.println(uri);
//		}
//	}
//
//	/**
//	 * Render a graph in DOT format.
//	 *
//	 * @param hrefGraph a graph based on URI objects
//	 */
//	private static void renderHrefGraph(Graph<URI, DefaultEdge> hrefGraph)
//			throws ExportException, org.jgrapht.io.ExportException {
//
//		// use helper classes to define how vertices should be rendered,
//		// adhering to the DOT language restrictions
//		ComponentNameProvider<URI> vertexIdProvider = new ComponentNameProvider<URI>()
//		{
//			public String getName(URI uri)
//			{
//				return uri.getHost().replace('.', '_');
//			}
//		};
//		ComponentNameProvider<URI> vertexLabelProvider = new ComponentNameProvider<URI>()
//		{
//			public String getName(URI uri)
//			{
//				return uri.toString();
//			}
//		};
//		GraphExporter<URI, DefaultEdge> exporter =
//				new DOTExporter<>(vertexIdProvider, vertexLabelProvider, null);
//		Writer writer = new StringWriter();
//		exporter.exportGraph(hrefGraph, writer);
//		System.out.println(writer.toString());
//	}
//
//	/**
//	 * Create a toy graph based on String objects.
//	 *
//	 * @return a graph based on String objects.
//	 */
//	private static Graph<String, DefaultEdge> createStringGraph()
//	{
//		Graph<String, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
//
//		String v1 = "v1";
//		String v2 = "v2";
//		String v3 = "v3";
//		String v4 = "v4";
//
//		// add the vertices
//		g.addVertex(v1);
//		g.addVertex(v2);
//		g.addVertex(v3);
//		g.addVertex(v4);
//
//		// add edges to create a circuit
//		g.addEdge(v1, v2);
//		g.addEdge(v2, v3);
//		g.addEdge(v3, v4);
//		g.addEdge(v4, v1);
//
//		return g;
//	}
	private static SimpleDirectedWeightedGraph<Localization,DefaultWeightedEdge> createGraph()
	{
		SimpleDirectedWeightedGraph<Localization, DefaultWeightedEdge> g = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		LocalizationGenerator localizationGenerator = new LocalizationGenerator();
		Localization v1 = localizationGenerator.generateLocalization();
		Localization v2 = localizationGenerator.generateLocalization();
		Localization v3 = localizationGenerator.generateLocalization();
		Localization v4 = localizationGenerator.generateLocalization();

		// add the vertices
		g.addVertex(v1);
		g.addVertex(v2);
		g.addVertex(v3);
		g.addVertex(v4);

		// add edges to create a circuit
		DefaultWeightedEdge e1 = g.addEdge(v1, v2);
		g.setEdgeWeight(e1, 1);
		DefaultWeightedEdge e2 = g.addEdge(v2, v3);
		g.setEdgeWeight(e2, 2);
		DefaultWeightedEdge e3 = g.addEdge(v3, v4);
		g.setEdgeWeight(e3, 3);
		DefaultWeightedEdge e4 = g.addEdge(v4, v1);
		g.setEdgeWeight(e4, 4);

		return g;
	}

}
