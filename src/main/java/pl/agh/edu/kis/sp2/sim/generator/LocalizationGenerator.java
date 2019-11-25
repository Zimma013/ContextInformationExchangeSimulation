package pl.agh.edu.kis.sp2.sim.generator;

import pl.agh.edu.kis.sp2.sim.generator.wftr.Localization;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class LocalizationGenerator {

    private Random coordinateRandomGenerator;

    public LocalizationGenerator() {
        coordinateRandomGenerator = new Random();
    }

    public Localization generateLocalization() {
        return new Localization.Builder()
                .latitude(new BigDecimal(coordinateRandomGenerator.nextDouble() + 50D)
                        .setScale(7, RoundingMode.HALF_UP))
                .longitude(new BigDecimal(coordinateRandomGenerator.nextDouble() + 20D)
                        .setScale(7, RoundingMode.HALF_UP))
                .build();
    }
}