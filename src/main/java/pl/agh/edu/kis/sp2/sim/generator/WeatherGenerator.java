package pl.agh.edu.kis.sp2.sim.generator;

import pl.agh.edu.kis.sp2.sim.generator.wftr.Weather;
import pl.agh.edu.kis.sp2.sim.generator.enumeration.FogAmount;
import pl.agh.edu.kis.sp2.sim.generator.enumeration.WindDirection;

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
                .fog(FogAmount.randomFogAmount())
                .rainAmount(new BigDecimal(randomGenerator.nextDouble() * 10 * randomGenerator.nextDouble()).setScale(4, RoundingMode.HALF_UP))
                .temperature(new BigDecimal(randomGenerator.nextDouble() * 10 * randomGenerator.nextDouble()).setScale(2, RoundingMode.HALF_UP))
                .wind(new BigDecimal(randomGenerator.nextDouble() * 10 * randomGenerator.nextDouble()).setScale(4, RoundingMode.HALF_UP))
                .windDirection(WindDirection.randomWindDirection())
                .build();
    }
}
