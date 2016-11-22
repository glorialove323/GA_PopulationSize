/**
 * 
 */
package com.gyy.MultiPops_GPSGA;

/**
 * @author Gloria
 * 
 */
public class Individual {
    static int defaultGeneLength;
    static int defaultChromLength = 20;

    public Chromosome chrom;

    private double x1,x2;
    
    private double MAX = 600;

    private double MIN = -600;


    private double indivFitness = 0;

    private char[] individual = new char[20];
    private boolean m_bChanged = false;
 
    
    public void change(boolean bChanged){
        m_bChanged = bChanged;
    }
    
    public boolean isChanged(){
        return m_bChanged;
    }
    //shadow copy
    public Individual(char[] individual) {
        this.individual = individual;
    }
    //not shadow copy
    public Individual(Individual indiv) {
        individual = indiv.getIndividual();
    }
    
    public char[] getIndividual(){return individual;}
//
//    public char[] copyIndividual(){
//        char[] copy = new char[ParEngine.chromLen];
//        for(int i = 0; i < ParEngine.chromLen; i++){
//            char c = individual[i];
//            copy[i] = c;
//        }
//        return copy;
//    }
    public Individual() {
        m_bChanged = true;
        defaultGeneLength = 10;
        chrom = new Chromosome(defaultChromLength);
        //generateIndividual();
    }

    public char getAllele(int j) {
        return individual[j];
    }

    public void setAllele(int j, char c) {
        individual[j] = c;
    }

    // 获取个体的适应度值
    public double getFitness() {
        return indivFitness;
    }

    // 计算个体的适应度值
    public double calFitness() {
        decode();
        indivFitness = function(x1,x2);
        m_bChanged = false;
        return indivFitness;
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

    public String toString() {
        String str = "";
        str += "函数值:" + function(x1,x2) + "\n";

        return str;
    }
    
    public static double function(double x1, double x2) {
        double fun;
        double fun1 = x1*x1/4000+x2*x2/4000;
        double fun2 = Math.cos(x1/Math.sqrt(1))*Math.cos(x2/Math.sqrt(2));
        fun = fun1-fun2+1;
        return 1/fun;
    }
    // 随机产生个体
    public void generateIndividual() {
        chrom = new Chromosome(defaultChromLength);
        x1 = Math.random() * (MAX - MIN) + MIN;
        x2 = Math.random() * (MAX - MIN) + MIN;
        coding();
        //calFitness();
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
