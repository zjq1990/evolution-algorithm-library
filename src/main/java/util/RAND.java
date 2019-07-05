package util;


import java.util.Random;

/**
 * @author zhangjiaqi (jiaqi@shanshu.ai)
 * @date 2019/07/04
 **/
public class RAND {

    public static double getDoubleRandomBetween(double lower, double upper){
        Random rand = new Random();
        return rand.nextDouble()*(upper - lower) + lower;
    }

    public static int getIntRandomExcept(int i, int nPop) {
        if(i < 0){
            System.out.println("i cannot be less than zero");
        }
        Random rand = new Random();
        int k = -1;
        while (k != i) {
            k = rand.nextInt(nPop);
        }
        return k;
    }
}
