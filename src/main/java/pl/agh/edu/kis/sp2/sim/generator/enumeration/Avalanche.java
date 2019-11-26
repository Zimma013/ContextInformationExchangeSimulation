package pl.agh.edu.kis.sp2.sim.generator.enumeration;

import java.util.Random;

public enum Avalanche {
    A1,A2,A3,A4,A5;
    public static Avalanche getRandomAvalanche() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}