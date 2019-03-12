package algorithm;


import java.util.*;

/**
 * Created by zhangjackie on 17/10/20.
 * 粒子群算法 Particle Swarm Optimization
 */
public class PSO {
    // Control Parameters
    private static final int GENMAX = 1000;
    private static final int DIMENSION = 2;
    private static final int POPSIZE = 100;
    private static final double C1 = 1.5, C2 = 2;
    private static double W = 1; // 惯性因子 Inertia Weight
    private static final double Wdamp = 0.99;
    private static double[][] X = new double[POPSIZE][DIMENSION]; // 粒子位置
    private static final double XMAX = 10, XMIN = -10;
    private static double[][] V = new double[POPSIZE][DIMENSION]; // 粒子速度
    private static final double VMAX = 0.1 * (XMAX - XMIN); // 最大限制速度
    private static double VMIN = -VMAX;
    private static double[][] pbest = new double[POPSIZE][DIMENSION]; // 个体最优
    private static double[] gbest = new double[DIMENSION]; // 种群最优
    private static double[] fitness = new double[POPSIZE];


    // 初始化
    private static void init() {
        for (int i = 0; i < POPSIZE; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                X[i][j] = (XMAX - XMIN) * rand() + XMIN; // Initialize Position
                V[i][j] = 0; // Initialize Velocity
            }
            // Evaluation
            fitness[i] = calFitness(X[i]);
            // Local best
            pbest[i] = X[i];
        }
        // global best
        int[] sortedIndex = getSortedIndex(fitness);
        gbest = X[sortedIndex[0]];
    }

    //更新位置信息
    private static void updateX() {
        for (int i = 0; i < POPSIZE; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                X[i][j] = X[i][j] + V[i][j];
                if (X[i][j] > XMAX) {
                    X[i][j] = XMAX;
                } else if (X[i][j] < XMIN) {
                    X[i][j] = XMIN;
                }
            }
            //Update personal best
            double newFitness = calFitness(X[i]);
            if (newFitness < fitness[i]) {
                pbest[i] = X[i];
            }
            //Update fitness
            fitness[i] = newFitness;
        }
        // Update global best
        int[] sortedIndex = getSortedIndex(fitness);
        gbest = X[sortedIndex[0]];
    }

    //更新速度信息
    private static void updateV() {
        Random random = new Random();
        for (int i = 0; i < POPSIZE; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                V[i][j] = W * V[i][j]
                        + C1 * random.nextDouble() * (pbest[i][j] - X[i][j])
                        + C2 * random.nextDouble() * (gbest[j] - X[i][j]);
                if (V[i][j] > VMAX) {
                    V[i][j] = VMAX;
                } else if (V[i][j] < VMIN) {
                    V[i][j] = VMIN;
                }
            }
        }
    }

    // 计算适应值
    private static double calFitness(double[] x) {
        return Fitness.deJong(x);
    }

    // 主函数
    public static void psoMain() {
        int gen = 0;
        init();
        while (gen < GENMAX) {
            updateV();
            updateX();

            System.out.println("第" + gen + "代：" + calFitness(gbest));
            gen++;
            W = W * Wdamp;
        }
    }

    //获得升序排序后的索引值
    private static int[] getSortedIndex(double[] fit) {
        TreeMap<Double, Integer> map = new TreeMap<Double, Integer>();
        int[] sortedIndex = new int[fit.length];
        for (int i = 0; i < fit.length; i++) {
            map.put(fit[i], i);
        }
        int n = 0;
        Iterator<Map.Entry<Double, Integer>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Double, Integer> me = it.next();
            sortedIndex[n++] = me.getValue();
        }
        return sortedIndex;
    }


    private static double rand() {
        return Math.random();
    }

    public static void main(String[] args) {
        PSO.psoMain();
        System.out.println(Arrays.toString(PSO.gbest));
        System.out.println(calFitness(PSO.gbest));
    }
}
