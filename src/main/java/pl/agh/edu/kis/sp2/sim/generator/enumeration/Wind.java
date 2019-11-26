package pl.agh.edu.kis.sp2.sim.generator.enumeration;

import java.util.Random;

public enum Wind {
    W1,W2,W3;
    public static Wind getRandomWind() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}