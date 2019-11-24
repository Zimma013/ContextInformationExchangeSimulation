package pl.agh.edu.kis.sp2.sim.generator.enumeration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum FogAmount {
    NONE,
    LOW,
    MEDIUM,
    HIGH;

    private static final List<FogAmount> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static FogAmount randomFogAmount()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
