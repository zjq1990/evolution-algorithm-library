package algorithm;

import java.util.Random;

/**
 * Created by zhangjackie on 17/7/14.
 */
public class GA {

    private double CalculateFitness(double XTemp[]) {
        return Fitness.rosenBrock(XTemp);
    }

    // 选择操作(轮盘赌选择可以遗传下一代的染色体)
    private int selection(double[][] X) {
        double totalScore = 0;
        for (int i = 0; i < X.length; i++) {
            totalScore += CalculateFitness(X[i]);
        }
        double slice = Math.random() * totalScore;
        double sum = 0;
        for (int i = 0; i < X.length; i++) {
            sum += CalculateFitness(X[i]);
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


    //遗传算法主程序
    public void geneticAlgo() {
        int NP = 20;//种群数量
        int size = 10;//个体长度
        //double CR=0.5;// 交叉概率
        int IterMax = 1000;// 最大迭代次数

        double X[][] = new double[NP][size];
        double XCrossover[][] = new double[NP][size];
        double XMutation[][] = new double[NP][size];
        double fitness[] = new double[NP];
        double Xnew[][] = new double[2 * NP][size];
        double fitnessNew[] = new double[2 * NP];
        Random r = new Random();

        double bestValue = 10000;

        // 初始化种群： 实数编码方式（0，1）
        for (int i = 0; i < NP; i++) {
            for (int j = 0; j < size; j++) {
                X[i][j] = r.nextDouble();
            }
            fitness[i] = CalculateFitness(X[i]);
            if (fitness[i] < bestValue) {
                bestValue = fitness[i];
            }

        }

        // searching iteration
        int k = 0;
        int p1, p2 = 0;
        while (k < IterMax) {

            for (int i = 0; i < NP; i = i + 2) {
                // 选择
                p1 = selection(X);
                p2 = selection(X);
                //crossover 交叉
                XCrossover[i] = crossover(p1, p2, X);
                XCrossover[i + 1] = crossover(p1, p2, X);
                //mutation 变异
                XMutation[i] = mutation(XCrossover[i]);
                XMutation[i + 1] = mutation(XCrossover[i + 1]);

                if (CalculateFitness(X[p1]) > CalculateFitness(XMutation[i])) {
                    X[i] = XMutation[i];
                }
                if (CalculateFitness(X[p2]) > CalculateFitness(XMutation[i + 1])) {
                    X[i + 1] = XMutation[i + 1];
                }
            }

            for (int i = 0; i < NP; i++) {
                fitness[i] = CalculateFitness(X[i]);
                if (fitness[i] < bestValue) {
                    bestValue = fitness[i];
                }
            }

            System.out.println(bestValue);
            k++;
        }
    }

    public static void main(String args[]) {
        //System.out.println(1/Math.pow(2,1));
        GA ga = new GA();
        ga.geneticAlgo();
    }
}
