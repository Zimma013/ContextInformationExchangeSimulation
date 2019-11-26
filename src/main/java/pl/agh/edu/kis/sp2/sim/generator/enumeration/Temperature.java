package pl.agh.edu.kis.sp2.sim.generator.enumeration;

import java.util.Random;

public enum Temperature {
    T1,T2,T3;
    public static Temperature getRandomTemperature() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}