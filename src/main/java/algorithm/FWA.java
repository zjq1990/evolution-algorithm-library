package algorithm;


import lombok.extern.slf4j.Slf4j;
import util.RAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 烟花算法 Firework algorithm
 *
 * @author zhangjiaqi (jiaqi@shanshu.ai)
 * @date 2019/07/09
 **/
@Slf4j
public class FWA {

    private final static int nPop = 5;
    private final static int dim = 2;

    private final static double varMin = -10; // decision variable lower bound
    private final static double varMax = 10; // decision variable uppper bound
    private final static double a = 0.04;// spark lower bound
    private final static double b = 0.8;// spark upper bound
    private final static double epsilon = Double.MIN_VALUE;
    private final static int m = 50;
    private final static int mHat = 5;
    private final static double maxExplosionAmplitude = 40;// maximum explosion amplitude
    private final static int maxIter = 3000;

    private double[][] fireworks = new double[nPop][dim];// locations of firework
    private double[] fitness = new double[nPop];
    private List<double[]> explosionSpark = new ArrayList<double[]>();
    private List<Double> explosionSparkFitness = new ArrayList<Double>();

    private double bestValue = Double.MAX_VALUE; //全局最优解
    private double[] bestIndividual = new double[dim];//全局最优个体
    private int bestIndex = 0;

    private double calculateFitness(double[] x) {
        return Fitness.deJong(x);
    }

    private void init() {
        for (int i = 0; i < nPop; i++) {
            for (int j = 0; j < dim; j++) {
                fireworks[i][j] = RAND.getDoubleRandomBetween(varMin, varMax);//rand.nextDouble() * (varMax - varMin) + varMin;
            }

            fitness[i] = calculateFitness(fireworks[i]);
            if (fitness[i] < bestValue) {
                bestValue = fitness[i];
                bestIndex = i;
                bestIndividual = fireworks[i].clone();
                log.info(Arrays.toString(bestIndividual) + "|" + bestValue);
            }
        }
    }

    // calculation of sparks number
    private double[] calculateSparkNum(double[] fitness) {
        double maxFitness = Double.MIN_VALUE;
        for (double f : fitness) {
            if (f > maxFitness) {
                maxFitness = f;
            }
        }

        double sum = 0;
        for (double f : fitness) {
            sum += (maxFitness - f);
        }

        double[] s = new double[nPop];
        for (int i = 0; i < nPop; i++) {
            s[i] = m * (maxFitness - fitness[i] + epsilon) / (sum + epsilon);
            // control the rate between value a and value b
            if (s[i] < a * m) {
                s[i] = Math.round(a * m);
            } else if (s[i] > b * m) {
                s[i] = Math.round(b * m);
            } else {
                s[i] = Math.round(s[i]);
            }
        }
        return s;
    }

    // calculation of explosion amplitude
    private double[] calculateAmplitude(double[] fitness) {
        double minFitness = Double.MAX_VALUE;
        for (double f : fitness) {
            if (f < minFitness) {
                minFitness = f;
            }
        }

        double sum = 0;
        for (double f : fitness) {
            sum += (f - minFitness);
        }

        double[] A = new double[nPop];
        for (int i = 0; i < nPop; i++) {
            A[i] = maxExplosionAmplitude * (fitness[i] - minFitness + epsilon) / (sum + epsilon);
        }
        return A;
    }

    // generate "explosion sparks" Algorithm1
    private void explosionSpark() {
        double[] amplitude = calculateAmplitude(fitness);
        double[] sparkNum = calculateSparkNum(fitness);

        int index = fireworks.length;
        for (int i = 0; i < nPop; i++) {
            int count = 1;
            double[] spark;
            double fitness;
            while (count < sparkNum[i]) {
                index += 1;
                spark = explosionOperator(amplitude[i], fireworks[i]);
                fitness = calculateFitness(spark);
                explosionSpark.add(spark);
                explosionSparkFitness.add(fitness);
                if (fitness < bestValue) {
                    bestValue = fitness;
                    bestIndividual = spark.clone();
                    bestIndex = index;
                    log.info(Arrays.toString(bestIndividual) + "|" + bestValue);
                }
                count++;
            }
        }
    }

    private double[] explosionOperator(double amplitude, double[] X) {
        Random rand = new Random();
        int[] z = new int[dim];
        for (int i = 0; i < dim; i++) {
            z[i] = (int) Math.round(rand.nextDouble());
        }

        double[] spark = new double[dim];
        for (int i = 0; i < dim; i++) {
            if (z[i] == 1) {
                double h = amplitude * RAND.getDoubleRandomBetween(-1, 1);
                spark[i] = X[i] + h;
                if (spark[i] < varMin || spark[i] > varMax) {
                    spark[i] = RAND.getDoubleRandomBetween(varMin, varMax);
                }
            } else {
                spark[i] = X[i];
            }
        }
        return spark;
    }

    // generate "Gaussion sparks" Algorithm2
    private void gaussionSpark() {
        int index = fireworks.length + explosionSpark.size();
        for (int i = 0; i < mHat; i++) {
            index++;
            int randIndex = new Random().nextInt(nPop);
            double[] spark = gaussionOperator(fireworks[randIndex]);
            double fitness = calculateFitness(spark);
            explosionSpark.add(spark);
            explosionSparkFitness.add(fitness);
            if (fitness < bestValue) {
                bestValue = fitness;
                bestIndividual = spark.clone();
                bestIndex = index;
                log.info(Arrays.toString(bestIndividual) + "|" + bestValue);
            }
        }
    }

    private double[] gaussionOperator(double[] X) {
        Random rand = new Random();
        int[] z = new int[dim];
        for (int i = 0; i < dim; i++) {
            z[i] = (int) Math.round(rand.nextDouble());
        }

        double[] spark = new double[dim];
        double e = rand.nextGaussian() + 1;
        for (int i = 0; i < dim; i++) {
            if (z[i] == 1) {
                spark[i] = X[i] * e;
                if (spark[i] < varMin || spark[i] > varMax) {
                    spark[i] = RAND.getDoubleRandomBetween(varMin, varMax);
                }
            } else {
                spark[i] = X[i];
            }
        }
        return spark;
    }

    private double[] probability(double[] fitness, List<Integer> assigned) {
        double[] R = new double[fitness.length];
        for (int i = 0; i < fitness.length; i++) {
            if (!assigned.contains(i)) {
                for (int j = 0; j < fitness.length; j++) {
                    R[i] += Math.abs(fitness[i] - fitness[j]);
                }
            } else {
                R[i] = 0;
            }
        }

        double sumR = 0;
        for (int i = 0; i < fitness.length; i++) {
            sumR += R[i];
        }

        double[] prob = new double[fitness.length];
        for (int i = 0; i < fitness.length; i++) {
            prob[i] = R[i] / sumR;
        }
        return prob;
    }

    private int rouletteWheelSelection(double[] prob) {
        double[] cumsum = new double[prob.length];
        double sum = 0;
        for (int i = 0; i < prob.length; i++) {
            sum += prob[i];
            cumsum[i] = sum;
        }
        Random random = new Random();
        double rand = random.nextDouble();
        for (int i = 0; i < prob.length; i++) {
            if (rand <= cumsum[i]) {
                return i;
            }
        }
        return -1;
    }


    private double[][] merge(double[][] pop1, List<double[]> pop2) {
        int n1 = pop1.length;
        int n2 = pop2.size();
        double[][] pop = new double[n1 + n2][pop1[0].length];
        for (int i = 0; i < (n1 + n2); i++) {
            if (i < n1) {
                pop[i] = pop1[i];
            } else {
                pop[i] = pop2.get(i - n1);
            }
        }
        return pop;
    }

    private double[] merge(double[] fitness1, List<Double> fitness2) {
        int n1 = fitness1.length;
        int n2 = fitness2.size();
        double[] fit = new double[n1 + n2];
        for (int i = 0; i < (n1 + n2); i++) {
            if (i < n1) {
                fit[i] = fitness1[i];
            } else {
                fit[i] = fitness2.get(i - n1);
            }
        }
        return fit;
    }


    public void evolve() {

        init();

        int iteration = 0;
        while (iteration++ < maxIter) {
            System.out.println("第" + iteration + "代：");

            explosionSpark();

            gaussionSpark();

            double[][] newLocation = new double[nPop][dim];
            newLocation[0] = bestIndividual.clone();
            List<Integer> assigned = new ArrayList<Integer>();
            assigned.add(bestIndex);

            double[][] pop = merge(fireworks, explosionSpark);
            double[] fit = merge(fitness, explosionSparkFitness);
            int i = 1;
            while (i < nPop) {
                double[] prob = probability(fit, assigned);
                int selectedIndex = rouletteWheelSelection(prob);
                newLocation[i] = pop[selectedIndex];
                assigned.add(selectedIndex);
                i = i + 1;
            }

            fireworks = newLocation.clone();

            explosionSpark = new ArrayList<double[]>();
            explosionSparkFitness = new ArrayList<Double>();

        }

        log.info("目标最优解");
        log.info(Arrays.toString(bestIndividual) + "|" + bestValue);

    }

}
