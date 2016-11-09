/**
 * 
 */
package com.gyy.lifetime_GAVaPS;


/**
 * @author Gloria
 * 
 */
public class Individual {
    static int defaultChromLength =20;
    static int defaultGeneLength = 10;

    public Chromosome chrom;

    private double x1,x2;

    private double indivFitness = 0;

    private int indivLifetime = 0;

    private int indivAge = 0;

    final int MinLT = 1;

    final int MaxLT = 7;

    public Individual() {
        defaultGeneLength = 10;
        chrom = new Chromosome(defaultChromLength);
        generateIndividual();
    }


    //获取个体的适应度值
    public double getFitness(){
            indivFitness = calFitness();
        return indivFitness;
    }
    public void resetFitness(){
        this.indivFitness = 0;
    }
    //计算个体的适应度值
    public double calFitness(){
        decode();
        indivFitness = function(x1,x2);
        return indivFitness;
    }
    //计算个体的age值，每执行一次进化算法，就加1
    public int increaseAge(){
        return indivAge ++;          
    }
    public int getIndivAge() {
        return indivAge;
    }
    public void resetAge(){
        this.indivAge = 0;
    }

    public void setIndivAge(int indivAge) {
        this.indivAge = indivAge;
    }


    public int getLiftime(){
        return indivLifetime;
    }
    public void resetLifetime(){
        this.indivLifetime = 0;
    }
    public int calLifetime(double bestFitness,double worstFitness, double avgFitness){
        indivFitness = getFitness();
        if (indivFitness <= avgFitness) {
            indivLifetime = (int) (MinLT + 1.0 / 2 * (MaxLT - MinLT) * (indivFitness - worstFitness)
                    / (avgFitness - worstFitness));
        } else {
            indivLifetime = (int) (1.0 / 2 * (MinLT + MaxLT) + 1.0 / 2 * (MaxLT - MinLT)
                    * (indivFitness - avgFitness) / (bestFitness - avgFitness));
        }
        return indivLifetime;        
    }

    public int getChromlen() {
        return chrom.getLength();
    }

    public boolean setChrom(int begin, int end, String gene) {
        return chrom.setGene(begin, end, gene);
    }

    public String getChrom(int begin, int end) {
        return chrom.getGene(begin, end);
    }

    // 编码
    public void coding() {
        String code1,code2;
        code1 = codingVariable(x1);
        code2 = codingVariable(x2);       
        chrom.setGene(0, 9, code1);
        chrom.setGene(10, 19, code2);
    }

    // 解码
    public void decode() {
        String gene1,gene2;
        gene1 = chrom.getGene(0, 9);
        gene2 = chrom.getGene(10, 19);
        x1 = decodeGene(gene1);
        x2 = decodeGene(gene2);
    }

    private String codingVariable(double x) {
        double y = (((x + 2.048) * 1023) / 4.096);
        String code = Integer.toBinaryString((int) y);

        StringBuffer codeBuf = new StringBuffer(code);
        for (int i = code.length(); i < defaultGeneLength; i++)
            codeBuf.insert(0, '0');

        return codeBuf.toString();
    }

    private double decodeGene(String gene) {
        int value;
        double decode;
        value = Integer.parseInt(gene, 2);
        decode = value / 1023.0 * 4.096 - 2.048;

        return decode;
    }

    public void mutateSingleBit(int index) {
        String gene, gn;
        gene = chrom.getGene(index, index);
        gn = gene.equals("0") ? "1" : "0";
        chrom.setGene(index, index, gn);
    }

    public String toString() {
        String str = "";
        // /str = "基因型:" + chrom + "  ";
        // /str+= "表现型:" + "[x1,x2]=" + "[" + x1 + "," + x2 + "]" + "\t";
        str += "函数值:" + function(x1,x2) + "\n";
        return str;
    }

   /* *//**
     * G1函数 y = -xsin(10πx)+1 -2.0 =< x <= 1.0
     *//*
    public static double function(double x) {
        double fun;
        fun = -x * (Math.sin(10 * (Math.PI) * x)) + 1;

        return fun;
    }*/
    
    /*
     * Rosenbrock函数:f(x1,x2) = 100*(x1**2 - x2)**2 + (1 - x1)**2
     * 在当x在[-2.048 ,2.048]内时，
     * 函数有两个极大点:
     * f(2.048 , -2.048) = 3897.7342
     * f(-2.048,-2.048) =3905.926227
     * 其中后者为全局最大点。
     */
    public static double function(double x1, double x2){
        double fun;
        fun = (100*Math.pow((x1*x1-x2), 2)+Math.pow((1-x1), 2)); 
        return fun;
    }

    // 随机产生个体
    public void generateIndividual() {
        // Math.random()的取值范围是[0,1]
        x1 = Math.random() * 4.096 - 2.048;
        x2 = Math.random() * 4.096 - 2.048;;

        // 同步编码和适应度
        coding();
        calFitness();
    }
    
    
    public Individual clone() {
        Individual inv = new Individual();
        try {
            inv.chrom = chrom.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return inv;
    }
}
