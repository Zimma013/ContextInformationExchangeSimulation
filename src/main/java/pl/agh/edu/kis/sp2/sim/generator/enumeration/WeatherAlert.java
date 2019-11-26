package pl.agh.edu.kis.sp2.sim.generator.enumeration;

import java.util.Random;

public enum WeatherAlert {
    E1,E2,E3,E4,E5;
    public static WeatherAlert getRandomWeatherAlert() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}