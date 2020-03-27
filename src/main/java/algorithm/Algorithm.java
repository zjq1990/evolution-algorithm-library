package algorithm;

/**
 * @author zhangjiaqi
 * @date 2019/07/04
 **/
public interface Algorithm {

    /**
     * 初始化函数
     */
    public void init();

    /**
     * 计算适应值
     */
    public double calculateFitness(double[] x);

    /**
     * 迭代优化过程
     */
    public void evolve();
}
