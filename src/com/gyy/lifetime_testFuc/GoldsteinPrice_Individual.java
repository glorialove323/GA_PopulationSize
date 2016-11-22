/**
 * 
 */
package com.gyy.lifetime_testFuc;

/**
 * @author Gloria
 *
 */
public class GoldsteinPrice_Individual {
    final int MinLT = 1;

    // GAVaPS
    //final int MaxLT = 7;

    // APGA
    //final int MaxLT = 15;

    private double MAX = 2;

    private double MIN = -2;

    public static double function(double x1, double x2) {
        double fun;
        fun = (1+Math.pow((x1+x2+1), 2)*(19-4*x1+3*x1*x1-14*x2+6*x1*x2+3*x2*x2))*(30+Math.pow((2*x1-3*x2), 2)*(18-32*x1+12*x1*x1+48*x2-36*x1*x2+27*x2*x2));
        return -fun;
    }
}
