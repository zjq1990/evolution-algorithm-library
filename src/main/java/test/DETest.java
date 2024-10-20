package test;

import algorithm.DE;
import org.junit.Test;

import java.util.Arrays;

public class DETest {

    @Test
    public void test() {
        DE de = new DE();
        de.evolve();
        System.out.println("-------------------------------------");
        System.out.println(Arrays.toString(de.bestIndividual));
        System.out.println(de.bestValue);
        System.out.println("-------------------------------------");
    }
}