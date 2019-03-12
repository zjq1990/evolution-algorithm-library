package algorithm;

/**
 * Created by zhangjackie on 17/10/19.
 * benchmark test Functions
 */
public class Fitness {

    // De Jong function (Sphere model)
    // -5.12 <= xi <= 5.12
    // global minimum: f(x) = 0, x(i) = 0
    public static double deJong(double[] x) {
        double fitVal = 0;
        for (int i = 0; i < x.length; i++) {
            fitVal += x[i] * x[i];
        }
        return fitVal;
    }

    // RosenBrock function  known as banana function
    // -2.048 <= xi <= 2.048
    // min(f(x*)) = f(1,1,...1) = 0
    public static double rosenBrock(double[] x) {
        double fitVal = 0;
        for (int i = 0; i < x.length - 1; i++) {
            fitVal += 100 * (x[i + 1] - x[i] * x[i]) * (x[i + 1] - x[i] * x[i]) + (1 - x[i]) * (1 - x[i]);
        }
        return fitVal;
    }


    //Rastrigin function
    // -5.12 <= xi <= 5.12
    // global minimum: f(x) = 0, x(i) = 0
    public static double rastrigin(double[] x) {
        double fitVal = 0;
        for (int i = 0; i < x.length; i++) {
            fitVal += x[i] * x[i] - 10 * Math.cos(2 * Math.PI * x[i]) + 10;
        }
        return fitVal;
    }

    //Schwefel's functionb
    // f(x*)= 0, xi* = 0
    public static double schwefel1(double[] x) {
        double fitVal1 = 0, fitVal2 = 1;
        for (int i = 0; i < x.length; i++) {
            fitVal1 += Math.abs(x[i]);
            fitVal2 *= Math.abs(x[i]);
        }
        return fitVal1 + fitVal2;
    }

    public static double schwefel2(double[] x) {
        double fitVal = 0;
        for (int i = 0; i < x.length; i++) {
            double tmp = 0;
            for (int j = 0; j < i; j++) {
                tmp += x[j];
            }
            fitVal += tmp * tmp;
        }
        return fitVal;
    }

    public static double schwefel3(double[] x) {
        double fitVal = Math.abs(x[0]);
        for (int i = 1; i < x.length; i++) {
            if (fitVal < Math.abs(x[i])) {
                fitVal = Math.abs(x[i]);
            }
        }
        return fitVal;
    }


    //Step function
    //min(f(x*)) = f(0,0...,0) = 0
    public static double stepFun(double[] x) {
        double fitVal = 0;
        for (int i = 0; i < x.length; i++) {
            fitVal += Math.floor(x[i] + 0.5) * Math.floor(x[i] + 0.5);
        }
        return fitVal;
    }


    //Quartic function
    // min(f(x*)) = f(0,0...,0) =0
    public static double quartic(double[] x) {
        double fitVal = 0;
        for (int i = 0; i < x.length; i++) {
            fitVal += i * Math.pow(x[i], 4) + Math.random();
        }
        return fitVal;
    }


    // Ackley's function
    // -32 <= xi <= 32
    // global minimum: f(x) = 0, x(i)=0
    public static double ackley(double[] x) {
        double a = 20, b = 0.2, c = 2 * Math.PI;
        double n = x.length;
        double fsum = 0, fcos = 0;
        for (int i = 0; i < x.length; i++) {
            fsum += x[i] * x[i];
            fcos += Math.cos(c * x[i]);
        }
        return -a * Math.exp(-b * Math.sqrt(fsum / n)) - Math.exp(fcos / n) + a + Math.exp(1);
    }

    //Griewangk function
    // -600 <= xi <= 600
    // global minimum: f(x) = 0, x(i) = 0
    public static double griewangk(double[] x) {
        double fitVal1 = 0;
        for (int i = 0; i < x.length; i++) {
            fitVal1 += x[i] * x[i];
        }
        double fitVal2 = 1;
        for (int i = 0; i < x.length; i++) {
            fitVal2 *= Math.cos(x[i] / Math.sqrt(i));
        }
        return fitVal1 / 4000 + fitVal2 + 1;
    }


    //Schekel's Foxholes function
    // |xi| <= 65.56
    // min(f(x*)) = f(-32,-32) = 1
    public static double schekel(double[] x) {
        double[][] a = {{-32, -16, 0, 16, 32, -32, -16, 0, 16, 32, -32, -16, 0, 16, 32, -32, -16, 0, 16, 32, -32, -16, 0, 16, 32},
                {-32, -32, -32, -32, -32, -16, -16, -16, -16, -16, 0, 0, 0, 0, 0, 16, 16, 16, 16, 16, 32, 32, 32, 32, 32}};
        double fitVal = 0;
        for (int j = 0; j < 25; j++) {
            double temp = 0;
            for (int i = 0; i < 2; i++) {
                temp += Math.pow(x[i] - a[i][j], 6);
            }
            fitVal += 1 / temp;
        }
        return 1 / (fitVal + 1 / 500);
    }

    //Six-Hump Camel-Back function
    // |xi| <= 5
    // min(f(x*)) = f(0.08983,-0.7126) = f(-0.08983,0.7126) = -1.0316285
    public static double sixHump(double[] x) {
        return 4 * Math.pow(x[0], 2) - 2.1 * Math.pow(x[0], 4) + x[0] * x[1] - 4 * Math.pow(x[1], 2) + 4 * Math.pow(x[1], 4);
    }

    //Branin function
    // -5<= x1 <=10, 0<=x2<=15
    // min(f(x*)) = f(-3.142,2.275) = f(3.142,2.275) = f(9.425,2.425) = 0.398
    public static double branin(double[] x) {
        return Math.pow(x[1] - 5.1 * x[0] * x[0] / (4 * Math.PI * Math.PI) + 5 * x[0] / Math.PI - 6, 2)
                + 10 * (1 - 1 / (8 * Math.PI)) * Math.cos(x[0]) + 10;
    }


    //Goldstein-Price function
    // |xi| <=2
    // min(f(x*)) = f(0,-1) = 3
    public static double goldstein(double[] x)
    {
        return (1+(x[0]+x[1]+1)*(x[0]+x[1]+1)*(19-14*x[0]+3*x[0]*x[0]-14*x[1]+6*x[0]*x[1]+3*x[1]*x[1]))
                *(30+(2*x[0]-3*x[1])*(2*x[0]-3*x[1])*(18-32*x[0]+12*x[0]*x[0]+48*x[1]-36*x[0]*x[1]+27*x[1]*x[1]));
    }


    //Schaffer
    // |xi|<= 100
    // min(f(x*)) = f(0,0) = -1
    public static double schaffer(double[] x){
        return (Math.sin(Math.sqrt(x[0]*x[0]+x[1]*x[1]))*Math.sin(Math.sqrt(x[0]*x[0]+x[1]*x[1])) -0.5)
                /(1+ 0.001*(x[0]*x[0]+x[1]*x[1]))/(1+ 0.001*(x[0]*x[0]+x[1]*x[1])) - 0.5 ;
    }

    //sum of different power function
    // -1 <= xi <= 1
    //global minimum: f(x) = 0, x(i) = 0
    public static double power(double[] x) {
        double fitVal = 0;
        for (int i = 0; i < x.length; i++) {
            fitVal += Math.pow(Math.abs(x[i]), i + 1);
        }
        return fitVal;
    }


    // Michalewicz function
    // 0 <= xi <= 10
    public static double michalewicz(double[] x) {
        double m = 10, fitVal = 0;
        for (int i = 0; i < x.length; i++) {
            fitVal += Math.sin(x[i])
                    * Math.pow(Math.sin(i * x[i] * x[i] / Math.PI), 2 * m);
        }
        return -fitVal;
    }

    //Axis parallel hyper-ellipsoid function
    //-5.12 <= xi <= 5.12
    // global minimum: f(x) = 0, x(i) = 0
    public static double axisHyperEllipsoid(double[] x) {
        double fitVal = 0;
        for (int i = 0; i < x.length; i++) {
            fitVal += i * x[i] * x[i];
        }
        return fitVal;
    }


    /////////////////////  TSP  /////////////////////////////////
    // 50 city
    private double[] city_x = {
            178,272,176,171,650,499,267,703,408,437,491,74,532,
            416,626,42,271,359,163,508,229,576,147,560,35,714,
            757,517,64,314,675,690,391,628,87,240,705,699,258,
            428,614,36,360,482,666,597,209,201,492,294};
    private double[] city_y = {
            170,395,198,151,242,556,57,401,305,421,267,105,525,
            381,244,330,395,169,141,380,153,442,528,329,232,48,
            498,265,343,120,165,50,433,63,491,275,348,222,288,
            490,213,524,244,114,104,552,70,425,227,331};


}
