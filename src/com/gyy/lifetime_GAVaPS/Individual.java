/**
 * 
 */
package com.gyy.lifetime_GAVaPS;

/**
 * @author Gloria
 * 
 */
public class Individual {
    static int defaultGeneLength = 20;

    public Chromosome chrom;

    private double x;

    private double indivFitness = 0;

    private int indivLifetime = 0;

    private int indivAge = 0;

    final int MinLT = 1;

    final int MaxLT = 7;

    public Individual() {
        chrom = new Chromosome(defaultGeneLength);
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
        indivFitness = function(x);
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
        String code;
        code = codingVariable(x);
        chrom.setGene(0, 19, code);
    }

    // 解码
    public void decode() {
        String gene;
        gene = chrom.getGene(0, 19);
        x = decodeGene(gene);
    }

    private String codingVariable(double x) {
        double y = (((x + 2) * Math.pow(2, 20)) / 3);
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
        decode = value / (Math.pow(2, 20)) * 3 - 2;

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
        str += "函数值:" + function(x) + "\n";

        return str;
    }

    /**
     * G1函数 y = -xsin(10πx)+1 -2.0 =< x <= 1.0
     */
    public static double function(double x) {
        double fun;
        fun = -x * (Math.sin(10 * (Math.PI) * x)) + 1;

        return fun;
    }

    // 随机产生个体
    public void generateIndividual() {
        // Math.random()的取值范围是[0,1]
        x = Math.random() * 3 - 2;

        // 同步编码和适应度
        coding();
        calFitness();
    }
}
