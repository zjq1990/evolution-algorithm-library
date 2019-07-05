package util;


import com.sun.tools.javac.util.Pair;

/**
 * Created by zhouyi on 4/21/16.
 * 计算两个导航坐标之间的直线距离和夹角
 * https://tech.meituan.com/lucene-distance.html
 */
public class Geo {
    private static final double EARTH_RADIUS = 6371008.8;

    public static double simpleIntDist(int latitude1, int longitude1, int latitude2, int longitude2) {
        double lat1 = latitude1 / 1000000.0;
        double lng1 = longitude1 / 1000000.0;
        double lat2 = latitude2 / 1000000.0;
        double lng2 = longitude2 / 1000000.0;
        double dx = (lng1 - lng2); // 经度差值
        double dy = (lat1 - lat2); // 纬度差值
        double b = (lat1 + lat2) / 2.0; // 平均纬度
        double Lx = toRadians(dx) * Math.cos(toRadians(b)) * EARTH_RADIUS; // 东西距离
        double Ly = toRadians(dy) * EARTH_RADIUS; // 南北距离
        return Math.sqrt(Lx * Lx + Ly * Ly); // 用平面的矩形对角距离公式计算总距离
    }

    public static double simpleDoubleDist(double lat1, double lng1, double lat2, double lng2) {
        double dx = (lng1 - lng2); // 经度差值
        double dy = (lat1 - lat2); // 纬度差值
        double b = (lat1 + lat2) / 2.0; // 平均纬度
        double Lx = toRadians(dx) * Math.cos(toRadians(b)) * EARTH_RADIUS; // 东西距离
        double Ly = toRadians(dy) * EARTH_RADIUS; // 南北距离
        return Math.sqrt(Lx * Lx + Ly * Ly); // 用平面的矩形对角距离公式计算总距离
    }

    private static double toRadians(double dx) {
        return dx * 3.141592653589793D / 180.0D;
    }


    public static double getX(double lat1, double lng1, double lat2, double lng2) {
        double dx = (lng2 - lng1); // 经度差值
        double b = (lat1 + lat2) / 2.0; // 平均纬度
        return toRadians(dx) * Math.cos(toRadians(b)) * EARTH_RADIUS;// 东西距离
    }

    public static double getY(double lat1, double lat2) {
        double dy = (lat2 - lat1); // 纬度差值
        return toRadians(dy) * EARTH_RADIUS; // 南北距离
    }

    public static Pair<Double, Double> getVector(int lat1, int lng1, int lat2, int lng2) {
        double lat, lng;
        lat = getX(lat1 / 1000000.0, lng1 / 1000000.0, lat2 / 1000000.0, lng2 / 1000000.0);
        lng = getY(lat1 / 1000000.0, lat2 / 1000000.0);
        return Pair.of(lat, lng);
    }

    public static Pair<Double, Double> getVector(double lat1, double lng1, double lat2, double lng2) {
        double lat, lng;
        lat = getX(lat1, lng1, lat2, lng2);
        lng = getY(lat1, lat2);
        return Pair.of(lat, lng);
    }

    /**
     * 计算两向量夹角 （反余弦方法，取值在0-180之间）
     */
    public static double getTheta(double x1, double y1, double x2, double y2) {
        double r1 = Math.sqrt(x1 * x1 + y1 * y1);
        double r2 = Math.sqrt(x2 * x2 + y2 * y2);
        double theta;
        if (r1 > 0 && r2 > 0) {
            theta = Math.acos(Math.min(1, (x1 * x2 + y1 * y2) /
                    (Math.sqrt(x1 * x1 + y1 * y1) * Math.sqrt(x2 * x2 + y2 * y2))));
        } else {
            theta = 0;
        }
        return theta;
    }
}
