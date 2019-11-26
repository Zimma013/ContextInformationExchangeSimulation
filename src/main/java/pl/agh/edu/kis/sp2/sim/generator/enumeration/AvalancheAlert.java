package pl.agh.edu.kis.sp2.sim.generator.enumeration;

import java.util.Random;

public enum AvalancheAlert {
    E6G,E6R,E6M,E6A;
    public static AvalancheAlert getRandomAvalancheAlert() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}