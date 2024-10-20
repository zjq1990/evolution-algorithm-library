package test;

import algorithm.PSO;
import org.junit.Test;

import java.util.Arrays;

public class PSOTest {

    @Test
    public void test() {
        PSO pso = new PSO();
        pso.evolve();
        System.out.println("-------------------------------------");
        System.out.println(Arrays.toString(pso.bestIndividual));
        System.out.println(pso.bestValue);
        System.out.println("-------------------------------------");
    }
}