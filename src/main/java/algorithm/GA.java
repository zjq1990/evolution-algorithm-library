package algorithm;


import java.util.Random;

/**
 * 遗传算法 genetic algorithm
 */
public class GA implements IEvolutionAlgorithm{

    int NP = 20;//种群数量
    int size = 10;//个体长度
    //double CR = 0.5;// 交叉概率
    int IterMax = 1000;// 最大迭代次数

    // 最优值和最优解
    public double bestValue = Double.MAX_VALUE;
    public double[] bestIndividual;

    double[][] x = new double[NP][size];
    double[][]  xCrossover= new double[NP][size];
    double[][] xMutation = new double[NP][size];
    double[] fitness = new double[NP];

    Random r = new Random();




    // 选择操作(轮盘赌选择可以遗传下一代的染色体)
    private int selection(double[][] X) {
        double totalScore = 0;
        for (int i = 0; i < X.length; i++) {
            totalScore += calculateFitness(X[i]);
        }
        double slice = Math.random() * totalScore;
        double sum = 0;
        for (int i = 0; i < X.length; i++) {
            sum += calculateFitness(X[i]);
            if (sum > slice) {
                return i;
            }
        }
        return -1;
    }

    //交叉操作  线性重组  子个体 = 父个体1+a*(父个体2-父个体1)
    private double[] crossover(int p1, int p2, double[][] X) {
        int size = X[0].length;
        double[] child = new double[size];
        Random r = new Random();
        double alpha = r.nextDouble();
        for (int i = 0; i < size; i++) {
            child[i] = X[p1][i] + alpha * (X[p2][i] - X[p1][i]);

        }
        return child;
    }

    //变异操作  扰动变异 Xnew = X +/- 0.5*delta
    private double[] mutation(double[] parent) {
        double[] child = new double[parent.length];
        int xMax = 1;
        int xMin = 0;

        for (int i = 0; i < parent.length; i++) {
            double delta = 0;
            for (int j = 0; j < 20; j++) {
                if (Math.random() > 0.5) {
                    delta += 1 / Math.pow(2, j);
                }
            }
            if (Math.random() > 0.5) {
                child[i] = parent[i] + 0.1 * delta;
            } else {
                child[i] = parent[i] - 0.1 * delta;
            }

            if (child[i] > xMax) {
                child[i] = xMax;
            } else if (child[i] < xMin) {
                child[i] = xMin;
            }
        }
        return child;
    }

    @Override
    public void init(){
        // 初始化种群： 实数编码方式（0，1）
        for (int i = 0; i < NP; i++) {
            for (int j = 0; j < size; j++) {
                x[i][j] = r.nextDouble();
            }
            fitness[i] = calculateFitness(x[i]);
            if (fitness[i] < bestValue) {
                bestValue = fitness[i];
            }
        }
    }


    @Override
    public void evolve() {
        init();

        // searching iteration
        int k = 0;
        int p1, p2 = 0;
        while (k <= IterMax) {

            for (int i = 0; i < NP; i = i + 2) {
                // 选择
                p1 = selection(x);
                p2 = selection(x);
                //crossover 交叉
                xCrossover[i] = crossover(p1, p2, x);
                xCrossover[i + 1] = crossover(p1, p2, x);
                //mutation 变异
                xMutation[i] = mutation(xCrossover[i]);
                xMutation[i + 1] = mutation(xCrossover[i + 1]);

                if (calculateFitness(x[p1]) > calculateFitness(xMutation[i])) {
                    x[i] = xMutation[i];
                }
                if (calculateFitness(x[p2]) > calculateFitness(xMutation[i + 1])) {
                    x[i + 1] = xMutation[i + 1];
                }
            }

            for (int i = 0; i < NP; i++) {
                fitness[i] = calculateFitness(x[i]);
                if (fitness[i] < bestValue) {
                    bestValue = fitness[i];
                    bestIndividual = x[i];
                }
            }

            System.out.println("第" + k + "代： " + bestValue);
            k++;
        }
    }

}
