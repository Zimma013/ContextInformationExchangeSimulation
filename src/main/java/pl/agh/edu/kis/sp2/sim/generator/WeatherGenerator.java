package pl.agh.edu.kis.sp2.sim.generator;

import pl.agh.edu.kis.sp2.sim.generator.enumeration.*;
import pl.agh.edu.kis.sp2.sim.generator.wftr.Weather;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class WeatherGenerator {

    private Random randomGenerator;

    public WeatherGenerator() {
        randomGenerator = new Random();
    }

    public Weather generateWeather() {
        return new Weather.Builder()
                .fog(Fog.getRandomFog())
                .rainAmount(Rain.getRandomRain())
                .temperature(Temperature.getRandomTemperature())
                .wind(Wind.getRandomWind())
                .windDirection(WindDirection.randomWindDirection())
                .build();
    }
}
