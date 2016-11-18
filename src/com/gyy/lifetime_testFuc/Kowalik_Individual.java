/**
 * 
 */
package com.gyy.lifetime_testFuc;

/**
 * @author Gloria
 * 
 */
public class Kowalik_Individual {
    
    static int defaultChromLength = 40;

    static int defaultGeneLength = 10;   
    
    final int MinLT = 1;

    // GAVaPS
    final int MaxLT = 7;

    // APGA
    final int MaxLT = 15;

    private double x1, x2,x3,x4;
    
    private double MAX = 5;

    private double MIN = -5;
    
    // 计算个体的适应度值
    public double calFitness() {
        decode();
        indivFitness = function(x1, x2,x3,x4);
        return indivFitness;
    }

    // 编码
    public void coding() {
        String code1, code2,code3,code4;
        code1 = codingVariable(x1);
        code2 = codingVariable(x2);
        code3 = codingVariable(x3);
        code4 = codingVariable(x4);
        chrom.setGene(0, 9, code1);
        chrom.setGene(10, 19, code2);
        chrom.setGene(20, 29, code3);
        chrom.setGene(30, 39, code4);
    }
    
 // 解码
    public void decode() {
        String gene1, gene2,gene3,gene4;
        gene1 = chrom.getGene(0, 9);
        gene2 = chrom.getGene(10, 19);
        gene3 = chrom.getGene(20,29);
        gene4 = chrom.getGene(30,39);
        x1 = decodeGene(gene1);
        x2 = decodeGene(gene2);
        x3 = decodeGene(gene3);
        x4 = decodeGene(gene4);
    }
    
    public String toString() {
        String str = "";
        
        str = "基因型:" + chrom + "  ";
        str += "表现型:" + "x1=" + x1 + "," + "x2="+ x2 + "x3=" + x3 +"x4=" + x4 +"\t";
        
        str += "函数值:" + function(x1, x2,x3,x4) + "\n";

        return str;
    }
    
 // 随机产生个体
    public void generateIndividual() {

        x1 = Math.random() * (MAX - MIN) + MIN;
        x2 = Math.random() * (MAX - MIN) + MIN;
        x3 = Math.random() * (MAX - MIN) + MIN;
        x4 = Math.random() * (MAX - MIN) + MIN;
        coding();
        calFitness();
    }

    
    public static double function(double x1, double x2, double x3, double x4) {
        double fun = 0;
        double[] a1 = new double[] { 0.1957, 0.1947, 0.1735, 0.16, 0.0844, 0.0627, 0.0456, 0.0342, 0.0323, 0.0235,
                0.0246 };
        double[] b1 = new double[] { 0.25, 0.5, 1, 2, 4, 6, 8, 10, 12, 14, 16 };
        for (int i = 0; i < 11; i++) {
            fun = fun + Math.pow((a1[i] - (x1 * (b1[i] * b1[i] + b1[i] * x2)) / (b1[i] * b1[i] + b1[i] * x3 + x4)), 2);
        }
        return -fun;
    }
}
