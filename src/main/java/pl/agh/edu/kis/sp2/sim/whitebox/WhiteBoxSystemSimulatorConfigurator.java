package pl.agh.edu.kis.sp2.sim.whitebox;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import pl.agh.edu.kis.sp2.sim.generator.agent.Agent;
import pl.agh.edu.kis.sp2.sim.generator.graph.LocalizationVertex;
import pl.agh.edu.kis.sp2.sim.generator.wftr.WeatherSensor;

import java.util.List;

public class WhiteBoxSystemSimulatorConfigurator {

	private WhiteBoxSystemSimulator simulator;

	public WhiteBoxSystemSimulatorConfigurator() {
		this.simulator = new WhiteBoxSystemSimulator();
	}

	public WhiteBoxSystemSimulatorConfigurator population(List<Agent> val) {
		this.simulator.setPopulation(val);
		return this;
	}

	public WhiteBoxSystemSimulatorConfigurator mountainRoutesGraph(SimpleWeightedGraph<LocalizationVertex, DefaultWeightedEdge> val) {
		this.simulator.setMountainRoutesGraph(val);
		return this;
	}

	public WhiteBoxSystemSimulatorConfigurator rootVertex(LocalizationVertex val) {
		this.simulator.setRootVertex(val);
		return this;
	}

	public WhiteBoxSystemSimulatorConfigurator simulatedWeatherConditionsMode(int val) {
		this.simulator.setSimulatedWeatherConditionsMode(val);
		return this;
	}

	public WhiteBoxSystemSimulatorConfigurator localizationDataRedundancyCoefficient(double val) {
		this.simulator.setLocalizationDataRedundancyCoefficient(val);
		return this;
	}

	public WhiteBoxSystemSimulatorConfigurator weatherSensors(List<WeatherSensor> val) {
		this.simulator.setWeatherSensors(val);
		return this;
	}

	public WhiteBoxSystemSimulator build() {
		return this.simulator;
	}
}
