import algorithm.ABC;
import algorithm.DE;
import algorithm.GA;
import algorithm.PSO;

import java.util.Arrays;

/**
 * @author zhangjiaqi (jiaqi@shanshu.ai)
 * @date 2019/07/03
 **/
public class Test {

    @org.junit.Test
    public void GATest() {
        GA ga = new GA();
        ga.evolve();
    }

    @org.junit.Test
    public void DETest() {
        DE de = new DE();
        de.evolve();
        System.out.println(Arrays.toString(de.bestIndividual));
        System.out.println(de.bestValue);
    }

    @org.junit.Test
    public void PSOTest() {
        PSO pso = new PSO();
        pso.evolve();
        System.out.println(Arrays.toString(pso.bestIndividual));
        System.out.println(pso.bestValue);
    }

    @org.junit.Test
    public void ABCTest(){
        ABC abc = new ABC();
        abc.evolve();
    }
}
