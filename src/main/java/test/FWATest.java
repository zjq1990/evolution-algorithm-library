package test;

import algorithm.FWA;
import org.junit.Test;

import java.util.Arrays;

public class FWATest {

    @Test
    public void test() {
        FWA fwa = new FWA();
        fwa.evolve();
        System.out.println("-------------------------------------");
        System.out.println(Arrays.toString(fwa.bestIndividual));
        System.out.println(fwa.bestValue);
        System.out.println("-------------------------------------");
    }
}