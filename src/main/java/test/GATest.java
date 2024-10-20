package test;

import algorithm.GA;
import org.junit.Test;

import java.util.Arrays;

public class GATest {

    @Test
    public void test() {
        GA ga = new GA();
        ga.evolve();
        System.out.println("-------------------------------------");
        System.out.println(Arrays.toString(ga.bestIndividual));
        System.out.println(ga.bestValue);
        System.out.println("-------------------------------------");
    }
}