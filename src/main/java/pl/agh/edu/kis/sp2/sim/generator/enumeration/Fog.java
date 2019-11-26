package pl.agh.edu.kis.sp2.sim.generator.enumeration;

import java.util.Random;

public enum Fog {
    F1,F2,F3;
    public static Fog getRandomFog() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}