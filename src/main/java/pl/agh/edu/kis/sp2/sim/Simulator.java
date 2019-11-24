package pl.agh.edu.kis.sp2.sim;

import pl.agh.edu.kis.sp2.sim.generator.LocalizationGenerator;
import pl.agh.edu.kis.sp2.sim.generator.WeatherGenerator;

public class Simulator {

    private WeatherGenerator weatherGenerator;
    private LocalizationGenerator localizationGenerator;

    public Simulator() {
        this.weatherGenerator = new WeatherGenerator();
        this.localizationGenerator = new LocalizationGenerator();
    }

    public void showRandValues() {
        System.out.println("Localization ---------- " + localizationGenerator.generateLocalization().toString());
        System.out.println("Weather ---------- " + weatherGenerator.generateWeather().toString());
    }
}
