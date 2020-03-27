package algorithm.test;

import algorithm.GA;
import org.junit.Test;

import static org.junit.Assert.*;

public class GATest {

    @Test
    public void evolve() {
        GA ga = new GA();
        ga.evolve();
    }
}