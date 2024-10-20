package test;

import algorithm.ABC;
import org.junit.Test;

import java.util.Arrays;

public class ABCTest {

    @Test
    public void test() {
        ABC abc = new ABC();
        abc.evolve();
        System.out.println("-------------------------------------");
        System.out.println(Arrays.toString(abc.bestIndividual));
        System.out.println(abc.bestValue);
        System.out.println("-------------------------------------");
    }
}