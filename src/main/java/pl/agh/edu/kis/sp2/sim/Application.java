package pl.agh.edu.kis.sp2.sim;

import pl.agh.edu.kis.sp2.sim.generator.LocalizationGenerator;
import pl.agh.edu.kis.sp2.sim.generator.graph.Edge;
import pl.agh.edu.kis.sp2.sim.generator.graph.Node;

public class Application {

    public static void main(String[] args) {
	    Simulator sim = new Simulator();

	    sim.showRandValues();
	    sim.showRandValues();
	    sim.showRandValues();

	    LocalizationGenerator localizationGenerator = new LocalizationGenerator();
	    Node a = new Node();
	    a.setLocalization(localizationGenerator.generateLocalization());
	    Node b = new Node();
	    b.setLocalization(localizationGenerator.generateLocalization());
		Edge e = new Edge();
		e.setDestination(b);
		e.setWeight(5);
		a.addEdge(e);
    }
}
