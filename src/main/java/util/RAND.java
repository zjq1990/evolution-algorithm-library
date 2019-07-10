package util;


import java.util.Random;

/**
 * @author zhangjiaqi (jiaqi@shanshu.ai)
 * @date 2019/07/04
 **/
public class RAND {

    public static double getDoubleRandomBetween(double lower, double upper) {
        Random rand = new Random();
        return rand.nextDouble() * (upper - lower) + lower;
    }

    public static int getIntRandomExcept(int i, int nPop) {
        if (i < 0) {
            System.out.println("i cannot be less than zero");
        }
        Random rand = new Random();
        int k = -1;
        while (k != i) {
            k = rand.nextInt(nPop);
        }
        return k;
    }

    /**
     * 获取闭区间n个不同的随机整数[min, max]
     **/
    public static int[] randomArray(int min, int max, int n) {
        int len = max - min + 1;

        if (max < min || n > len) {
            return null;
        }

        int[] source = new int[len];
        for (int i = min; i < min + len; i++) {
            source[i - min] = i;
        }

        int[] result = new int[n];
        Random rd = new Random();
        int index = 0;
        for (int i = 0; i < result.length; i++) {
            //待选数组0到(len-2)随机一个下标
            index = Math.abs(rd.nextInt() % len--);
            //将随机到的数放入结果集
            result[i] = source[index];
            //将待选数组中被随机到的数，用待选数组(len-1)下标对应的数替换
            source[index] = source[len];
        }
        return result;
    }
}
