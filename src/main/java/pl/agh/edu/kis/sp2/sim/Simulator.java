package pl.agh.edu.kis.sp2.sim;

import pl.agh.edu.kis.sp2.sim.generator.GraphGenerator;
import pl.agh.edu.kis.sp2.sim.generator.LocalizationGenerator;
import pl.agh.edu.kis.sp2.sim.generator.WeatherGenerator;

public class Simulator {

    private WeatherGenerator weatherGenerator;
    private LocalizationGenerator localizationGenerator;
    private GraphGenerator graphGenerator;

    public Simulator() {
        this.weatherGenerator = new WeatherGenerator();
        this.localizationGenerator = new LocalizationGenerator();
        this.graphGenerator = new GraphGenerator();
    }

    public void showRandValues() {
        System.out.println("Localization ---------- " + localizationGenerator.generateLocalization().toString());
        System.out.println("Weather ---------- " + weatherGenerator.generateWeather().toString());
    }

    public WeatherGenerator getWeatherGenerator() {
        return weatherGenerator;
    }

    public LocalizationGenerator getLocalizationGenerator() {
        return localizationGenerator;
    }

    public GraphGenerator getGraphGenerator() {
        return graphGenerator;
    }
}
