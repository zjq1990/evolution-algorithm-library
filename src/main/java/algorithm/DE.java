package algorithm;


import java.util.Random;

/**
 * 差分进化算法  differential evolution
 */
public class DE implements IEvolutionAlgorithm {
    private static final int NP = 1000;// 种群规模
    private static final int size = 2;// 个体的长度
    private static final double xMin = -5;// 最小值
    private static final double xMax = 5;// 最大值
    private static final double F = 0.6;// 变异的控制参数
    private static final double CR = 0.8;// 杂交的控制参数
    private static final int maxCycle = 1000; //最大迭代次数

    private double[][] x = new double[NP][size];// 个体
    private double[][] xMutation = new double[NP][size];
    private double[][] xCrossOver = new double[NP][size];
    private double[] fitness = new double[NP];// 适应值
    private double[] fitnessCrossOver = new double[NP];//经过变异交叉操作后适应值


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
        Random r = new Random();
        for (int i = 0; i < NP; i++) {
            int r1 = 0, r2 = 0, r3 = 0;
            while (r1 == i || r2 == i || r3 == i || r1 == r2 || r1 == r3
                    || r2 == r3) {// 取r1,r2,r3
                r1 = r.nextInt(NP);
                r2 = r.nextInt(NP);
                r3 = r.nextInt(NP);
            }
            for (int j = 0; j < size; j++) {
                xMutation[i][j] = x[r1][j] + F * (x[r2][j] - x[r3][j]);
                //区域边界检查
                if (xMutation[i][j] < xMin) {
                    xMutation[i][j] = xMin;
                } else if (xMutation[i][j] > xMax) {
                    xMutation[i][j] = xMax;
                }
            }
        }
    }

    //边界处理
    private double boundary(double x, double xmax, double xmin) {
        double left = 0;
        while (x > xmax || x < xmin) {
            if (x > xmax) {
                left = x - xmax;
                x = xmax - left;
            }
            if (x < xmin) {
                left = xmin - x;
                x = xmin + left;
            }
        }
        return x;
    }

    //交叉操作
    private void crossover() {
        Random r = new Random();
        for (int i = 0; i < NP; i++) {
            for (int j = 0; j < size; j++) {
                double rTemp = r.nextDouble();
                if (rTemp <= CR) {
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
            fitnessCrossOver[i] = calculateFitness(xCrossOver[i]);
            if (fitnessCrossOver[i] < fitness[i]) {
                for (int j = 0; j < size; j++) {
                    x[i][j] = xCrossOver[i][j];
                }
                fitness[i] = fitnessCrossOver[i];
            }
        }
    }


    @Override
    public void evolve() {
        double[] bestValueGeneration = new double[maxCycle]; //每代最佳值
        double[][] bestIndividualGeneration = new double[maxCycle][size];// 每代最优个体

        init();

        int gen = 0;
        while (gen < maxCycle) {
            //保存最佳个体值
            for (int i = 0; i < NP; i++) {
                if (bestValueGeneration[gen] < fitness[i]) {
                    bestValueGeneration[gen] = fitness[i];
                    bestIndividualGeneration[gen] = x[i];
                }
            }

            mutation();
            crossover();
            selection();

            System.out.println("第" + gen + "代：" + bestValueGeneration[gen]);
            gen++;
        }
        bestValue = bestValueGeneration[maxCycle - 1];
        bestIndividual = bestIndividualGeneration[maxCycle - 1];
    }

    

}
