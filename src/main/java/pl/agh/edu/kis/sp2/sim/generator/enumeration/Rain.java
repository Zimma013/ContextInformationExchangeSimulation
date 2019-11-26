package pl.agh.edu.kis.sp2.sim.generator.enumeration;

import java.util.Random;

public enum Rain {
    R1,R2,R3;
    public static Rain getRandomRain() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}