package algorithm.test;

import algorithm.PSO;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class PSOTest {

    @Test
    public void evolve() {
        PSO pso = new PSO();
        pso.evolve();
        System.out.println(Arrays.toString(pso.bestIndividual));
        System.out.println(pso.bestValue);
    }
}