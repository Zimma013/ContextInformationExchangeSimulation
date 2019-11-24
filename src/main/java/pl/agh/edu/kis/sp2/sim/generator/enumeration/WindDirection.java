package pl.agh.edu.kis.sp2.sim.generator.enumeration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum WindDirection {
    N,
    NE,
    E,
    SE,
    S,
    SW,
    W,
    NW;

    private static final List<WindDirection> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static WindDirection randomWindDirection()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
