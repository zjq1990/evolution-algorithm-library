package algorithm;


import util.RAND;

import java.util.Arrays;
import java.util.Random;


/**
 * Artifical bee Colony
 *
 * @author zhangjiaqi (jiaqi@shanshu.ai)
 * @date 2019/07/04
 **/
public class ABC {


    private final static int nVar = 2;// number of decision variables
    private final static double varMin = -10; // decision variable lower bound
    private final static double varMax = 10; // decision variable uppper bound
    private final static int maxIter = 200;// maximum number of iteration
    private final static int nPop = 100;// population size
    private final double limit = Math.round(0.1 * nVar * nPop);// abandonment limit (trail limit)
    private final static double a = 1; // acceleration coefficient upper bound

    private double[][] pop = new double[nPop][nVar];
    private double[] fitness = new double[nPop];

    public double bestValue = Double.MAX_VALUE; //全局最优解
    public double[] bestIndividual = new double[nVar];//全局最优个体

    private int[] abandonmentCount = new int[nPop];

    private void init() {
        for (int i = 0; i < nPop; i++) {
            for (int j = 0; j < nVar; j++) {
                pop[i][j] = RAND.getDoubleRandomBetween(varMin, varMax);//rand.nextDouble() * (varMax - varMin) + varMin;
            }

            fitness[i] = calculateFitness(pop[i]);
            if (fitness[i] < bestValue) {
                bestValue = fitness[i];
                bestIndividual = pop[i].clone();
            }
            abandonmentCount[i] = 1;
        }
    }

    private double calculateFitness(double[] x) {
        return Fitness.deJong(x);
    }


    private void recruitedBees() {
        double[] newbee = new double[nVar];
        double newbeeFitness;

        for (int i = 0; i < nPop; i++) {
            int k = RAND.getIntRandomExcept(i, nPop);
            double phi = RAND.getDoubleRandomBetween(-1, 1);
            double psai = RAND.getDoubleRandomBetween(0, 1.5);
            for (int j = 0; j < nVar; j++) {
                //newbee[j] = pop[i][j] + phi * (pop[i][j] - pop[k][j]);
                newbee[j] = pop[i][j] + phi * (pop[i][j] - pop[k][j]) + psai * (bestIndividual[j] - pop[i][j]);
            }

            newbeeFitness = calculateFitness(newbee);
            if (newbeeFitness < fitness[i]) {
                pop[i] = newbee.clone();
                fitness[i] = newbeeFitness;
            } else {
                abandonmentCount[i] += 1;
            }
        }
    }

    private void onlookerBees(double[] probability) {
        double[] newbee = new double[nVar];
        double newbeeFitness;

        for (int m = 0; m < nPop; m++) {
            int i = rouletteWheelSelection(probability);
            int k = RAND.getIntRandomExcept(i, nPop);
            double phi = RAND.getDoubleRandomBetween(-1, 1);
            double r1 = 0.5 + Math.pow(Math.cos((Math.PI * i) / (2 * maxIter)), 2);
            double r2 = 0.5 + Math.pow(Math.sin((Math.PI * i) / (2 * maxIter)), 2);
            for (int j = 0; j < nVar; j++) {
                //newbee[j] = pop[i][j] + phi * (pop[i][j] - pop[k][j]);
                newbee[j] = pop[i][j] + r1 * phi * (pop[i][j] - pop[k][j]) + r2 * (bestIndividual[j] - pop[i][j]);
            }

            newbeeFitness = calculateFitness(newbee);
            if (newbeeFitness < fitness[i]) {
                pop[i] = newbee.clone();
                fitness[i] = newbeeFitness;
            } else {
                abandonmentCount[i] += 1;
            }
        }
    }

    private double[] selectionProbability() {
        double fitSum = 0;
        for (int i = 0; i < nPop; i++) {
            fitSum += fitness[i];
        }
        double fitMean = fitSum / nPop;

        double[] F = new double[nPop];
        double FSum = 0;
        for (int i = 0; i < nPop; i++) {
            F[i] = Math.exp(-fitness[i] / fitMean);
            FSum += F[i];
        }

        double[] prob = new double[nPop];
        for (int i = 0; i < nPop; i++) {
            prob[i] = F[i] / FSum;
        }
        return prob;
    }

    private int rouletteWheelSelection(double[] prob) {
        double[] cumsum = new double[nPop];
        double sum = 0;
        for (int i = 0; i < nPop; i++) {
            sum += prob[i];
            cumsum[i] = sum;
        }
        Random random = new Random();
        double rand = random.nextDouble();
        for (int i = 0; i < nPop; i++) {
            if (rand <= cumsum[i]) {
                return i;
            }
        }
        return -1;
    }

    private void scoutBees() {
        for (int i = 0; i < nPop; i++) {
            if (abandonmentCount[i] >= limit) {
                for (int j = 0; j < nVar; j++) {
                    pop[i][j] = RAND.getDoubleRandomBetween(varMin, varMax);
                }
                fitness[i] = calculateFitness(pop[i]);
                abandonmentCount[i] = 0;
            }
        }
    }

    public void evolve() {
        init();

        int iteration = 0;
        while (iteration++ < maxIter) {
            System.out.println("第" + iteration + "代：");

            recruitedBees();

            onlookerBees(selectionProbability());

            scoutBees();

            // update best solution ever found
            for (int i = 0; i < nPop; i++) {
                if (fitness[i] < bestValue) {
                    bestValue = fitness[i];
                    bestIndividual = pop[i].clone();
                    System.out.println(Arrays.toString(bestIndividual) + "|" + bestValue);
                }
            }
        }

        System.out.println(Arrays.toString(bestIndividual) + "|" + bestValue);
    }
}
