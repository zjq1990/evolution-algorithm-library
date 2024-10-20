package algorithm;


import java.util.Random;

/**
 * 差分进化算法  differential evolution
 */
public class DE implements IEvolutionAlgorithm {
    private static final int NP = 10;// 种群规模
    private static final int size = 2;// 个体的长度
    private static final double xMin = -5;// 最小值
    private static final double xMax = 5;// 最大值
    private static final double F = 0.6;// 变异的控制参数
    private static final double CR = 0.8;// 杂交的控制参数
    private static final int kMax = 1000; //最大迭代次数

    private double[][] x = new double[NP][size];// 个体
    private double[][] xMutation = new double[NP][size];//变异后个体
    private double[][] xCrossOver = new double[NP][size];//交叉后个体
    private double[] fitness = new double[NP];// 适应值
    private double[] newFitness = new double[NP];//经过变异交叉操作后适应值


    public double bestValue = Double.MAX_VALUE; //全局最优解
    public double[] bestIndividual = new double[size];//全局最优个体

    @Override
    public void init() {
        Random r = new Random();
        for (int i = 0; i < NP; i++) {
            for (int j = 0; j < size; j++) {
                x[i][j] = xMin + r.nextDouble() * (xMax - xMin);
            }
            fitness[i] = calculateFitness(x[i]);
        }
    }

    //变异操作
    private void mutation() {
        Random rand = new Random();
        for (int i = 0; i < NP; i++) {
            int r1 = 0, r2 = 0, r3 = 0;
            // 取3个不同的随机数r1,r2,r3
            while (r1 == i || r2 == i || r3 == i || r1 == r2 || r1 == r3 || r2 == r3) {
                r1 = rand.nextInt(NP);
                r2 = rand.nextInt(NP);
                r3 = rand.nextInt(NP);
            }
            for (int j = 0; j < size; j++) {
                xMutation[i][j] = x[r1][j] + F * (x[r2][j] - x[r3][j]);
                //区域边界检查
                xMutation[i][j] = boundary(xMutation[i][j], xMax, xMin);
            }
        }
    }

    //边界处理
    private double boundary(double x, double max, double min) {
        double left = 0;
        while (x > max || x < min) {
            if (x > max) {
                left = x - max;
                x = max - left;
            }
            if (x < min) {
                left = min - x;
                x = min + left;
            }
        }
        return x;
    }

    //交叉操作
    private void crossover() {
        Random rand = new Random();
        for (int i = 0; i < NP; i++) {
            for (int j = 0; j < size; j++) {
                double r = rand.nextDouble();
                if (r <= CR) {
                    xCrossOver[i][j] = xMutation[i][j];
                } else {
                    xCrossOver[i][j] = x[i][j];
                }
            }
        }
    }

    //选择操作
    private void selection() {
        for (int i = 0; i < NP; i++) {
            newFitness[i] = calculateFitness(xCrossOver[i]);
            if (newFitness[i] < fitness[i]) {
                for (int j = 0; j < size; j++) {
                    x[i][j] = xCrossOver[i][j];
                }
                fitness[i] = newFitness[i];
            }
        }
    }


    @Override
    public void evolve() {

        init();

        int k = 0;
        while (k++ < kMax) {
            //更新最优解
            for (int i = 0; i < NP; i++) {
                if (bestValue > fitness[i]) {
                    bestValue = fitness[i];
                    bestIndividual = x[i];
                }
            }

            //变异
            mutation();
            //交叉
            crossover();
            //选择
            selection();

            System.out.println("第" + k + "代：" + bestValue);
        }

    }



}
