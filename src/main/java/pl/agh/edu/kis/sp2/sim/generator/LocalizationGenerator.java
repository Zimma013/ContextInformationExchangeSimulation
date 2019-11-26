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
                .latitude(new BigDecimal(49.2758701 - (coordinateRandomGenerator.nextDouble()/25) /** (coordinateRandomGenerator.nextBoolean() ? 1 : -1)*/)
                        .setScale(10, RoundingMode.HALF_UP))
                .longitude(new BigDecimal(19.9038364 - coordinateRandomGenerator.nextDouble()/25)
                        .setScale(10, RoundingMode.HALF_UP))
                .build();
    }
}