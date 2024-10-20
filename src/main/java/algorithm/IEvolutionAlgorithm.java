package algorithm;

import util.TestFunction;


public interface IEvolutionAlgorithm {

    /**
     * 计算适应值
     */
    default double calculateFitness(double[] x) {
        return TestFunction.rosenBrock(x);
    }

    /**
     * 初始化
     */
    public void init();

    /**
     * 迭代优化过程
     */
    public void evolve();
}
