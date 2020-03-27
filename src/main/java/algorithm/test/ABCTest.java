package algorithm.test;

import algorithm.ABC;
import org.junit.Test;

import static org.junit.Assert.*;

public class ABCTest {

    @Test
    public void evolve() {
        ABC abc = new ABC();
        abc.evolve();
    }
}