/**
 * 
 */
package com.gyy.lifetime_GAVaPS;

/**
 * @author Gloria
 * 
 */
public class Individual {
    /*static int defaultChromLength = 20;

    static int defaultGeneLength = 10;
*/
    static int defaultChromLength = 40;

    static int defaultGeneLength = 10;   
    
    public Chromosome chrom;

    //private double x1, x2;
    
    private double x1, x2,x3,x4;

    private double indivFitness = 0;

    private int indivLifetime = 0;

    private int indivAge = 0;

    final int MinLT = 1;

    final int MaxLT = 7;

    private double MAX = 5;

    private double MIN = -5;


    public Individual() {
        chrom = new Chromosome(defaultChromLength);
        generateIndividual();
    }

    // 获取个体的适应度值
    public double getFitness() {
        indivFitness = calFitness();
        return indivFitness;
    }

    public void resetFitness() {
        this.indivFitness = 0;
    }

   /* // 计算个体的适应度值
    public double calFitness() {
        decode();
        indivFitness = function(x1, x2);
        return indivFitness;
    }*/
    

    // 计算个体的适应度值
    public double calFitness() {
        decode();
        indivFitness = function(x1, x2,x3,x4);
        return indivFitness;
    }

    // 计算个体的age值，每执行一次进化算法，就加1
    public int increaseAge() {
        return indivAge++;
    }

    public int getIndivAge() {
        return indivAge;
    }

    public void resetAge() {
        this.indivAge = 0;
    }

    public void setIndivAge(int indivAge) {
        this.indivAge = indivAge;
    }

    public int getLiftime() {
        return indivLifetime;
    }

    public void resetLifetime() {
        this.indivLifetime = 0;
    }

    public int calLifetime(double bestFitness, double worstFitness, double avgFitness) {
        indivFitness = getFitness();
        if (indivFitness <= avgFitness) {
            indivLifetime = (int) (MinLT + 1.0 / 2 * (MaxLT - MinLT) * (indivFitness - worstFitness)
                    / (avgFitness - worstFitness));
        } else {
            indivLifetime = (int) (1.0 / 2 * (MinLT + MaxLT) + 1.0 / 2 * (MaxLT - MinLT) * (indivFitness - avgFitness)
                    / (bestFitness - avgFitness));
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

   /* // 编码
    public void coding() {
        String code1, code2;
        code1 = codingVariable(x1);
        code2 = codingVariable(x2);
        chrom.setGene(0, 9, code1);
        chrom.setGene(10, 19, code2);
    }

    // 解码
    public void decode() {
        String gene1, gene2;
        gene1 = chrom.getGene(0, 9);
        gene2 = chrom.getGene(10, 19);
        x1 = decodeGene(gene1);
        x2 = decodeGene(gene2);
    }*/
    
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

    private String codingVariable(double x) {
        double y = (((x + Math.abs(MIN)) * Math.pow(2, 10)) / (MAX + Math.abs(MIN)));
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
        decode = value / (Math.pow(2, 10)) * (MAX + Math.abs(MIN)) + MIN;

        return decode;
    }

    public void mutateSingleBit(int index) {
        String gene, gn;
        gene = chrom.getGene(index, index);
        gn = gene.equals("0") ? "1" : "0";
        chrom.setGene(index, index, gn);
    }

    /*public String toString() {
        String str = "";
        
       // str = "基因型:" + chrom + "  ";
        //str+= "表现型:" + "[x1,x2]=" + "[" + x1 + "," + x2 + "]" + "\t";
        
        str += "函数值:" + function(x1, x2) + "\n";

        return str;
    }*/
    public String toString() {
        String str = "";
        
        str = "基因型:" + chrom + "  ";
        str += "表现型:" + "x1=" + x1 + "," + "x2="+ x2 + "x3=" + x3 +"x4=" + x4 +"\t";
        
        str += "函数值:" + function(x1, x2,x3,x4) + "\n";

        return str;
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

   /* // 随机产生个体
    public void generateIndividual() {

        x1 = Math.random() * (MAX - MIN) + MIN;
        x2 = Math.random() * (MAX - MIN) + MIN;
        coding();
        calFitness();
    }*/
 // 随机产生个体
    public void generateIndividual() {

        x1 = Math.random() * (MAX - MIN) + MIN;
        x2 = Math.random() * (MAX - MIN) + MIN;
        x3 = Math.random() * (MAX - MIN) + MIN;
        x4 = Math.random() * (MAX - MIN) + MIN;
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
