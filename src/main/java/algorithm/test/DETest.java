package algorithm.test;

import algorithm.DE;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class DETest {

    @Test
    public void evolve() {
        DE de = new DE();
        de.evolve();
        System.out.println(Arrays.toString(de.bestIndividual));
        System.out.println(de.bestValue);
    }
}