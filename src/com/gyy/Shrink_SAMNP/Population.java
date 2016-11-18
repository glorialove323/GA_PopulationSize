/**
 * 
 */
package com.gyy.Shrink_SAMNP;

import java.util.Arrays;

/**
 * @author Gloria
 * 
 */
public class Population {
    protected int popSize;

    public Individual[] individuals;

    protected int worstPos, bestPos;

    protected double avgFit, worstFit, bestFit;

    protected double[] fitness;
    
    Individual bestIndividual;// 当代适应度最好的个体

    Individual worstIndividual;// 当代适应度最差的个体

    Individual currentBest; // 截止当代之前适应度最好的个体
    
    @SuppressWarnings("unused")
    private int worstIndex;//bestIndividual对应的数组下标  
    
    /*
     * constructor of an empty population
     */
    public Population(int popSize) {
        this.popSize = popSize;
        individuals = new Individual[popSize];
        for (int i = 0; i < individuals.length; i++)
            individuals[i] = new Individual();
        fitness = new double[popSize];
        //initPopulation();
    }
    public Population(int popSize, Population pop) {
        this.popSize = popSize;
        individuals = new Individual[popSize];
        int i;
        for (i = 0; i < pop.getPopSize() && i < individuals.length; i++){
            individuals[i] = pop.getIndividualCopy(i);
        }
        for (; i < individuals.length; i++){
            individuals[i] = new Individual();
             individuals[i].generateIndividual();
        }
            
        fitness = new double[popSize];
        //initPopulation();
    }
    
    public void initPopulation(){
        for(int i =0;i<individuals.length;i++){
            individuals[i].generateIndividual();
            //individuals[i].calFitness();
        }
    }
    
    
    public Individual[] getBestIndividuals(int nSize)
    		throws NullPointerException{
    	if (nSize > popSize){
    		throw new NullPointerException("too big size");
		}
    	Individual[] indivs = new Individual[nSize];
    	if (nSize == popSize){
    		for (int i = 0; i < nSize; i++){
    			indivs[i] = individuals[i];
    		}
    	}
    	else{
        	sortIndividual();
        	for (int i = 0; i < nSize; i++){
        		indivs[i] = individuals[popSize - i - 1];
        	}
    	}
    	return indivs;
    }
    
    
    public void sortIndividual(){
    	Arrays.sort(individuals);
    }

    //获得当前种群中最好的个体
    public void findBestIndividual(){
        bestIndividual = worstIndividual = individuals[0]; 
        for(int i = 1; i <getPopSize();i++){  
            if(individuals[i].getFitness() > bestIndividual.getFitness()){  
                bestIndividual = individuals[i];  
            }  
            if(individuals[i].getFitness() < worstIndividual.getFitness()){  
                worstIndividual = individuals[i];  
                worstIndex = i;  
            }  
        }  
    }
    
    public Individual getBestIndividual(){
    	if (bestIndividual == null){
    		findBestIndividual();
    	}
        return bestIndividual;
    }
    
    //获得截止当前种群中最好的个体
    public void findCurrentBest(){
        if(SAMNPSolver.getGeneration() == 1 ){//初始种群  
            currentBest = (Individual)bestIndividual.clone();  
        }else{  
            if(bestIndividual.getFitness() > currentBest.getFitness())  
                currentBest = (Individual)bestIndividual.clone();  
        }
    }
    
    public Individual getCurrentBest(){
        return currentBest;
    }
    
    public int getPopSize() {
        return this.popSize;
    }
    public Individual[] getIndividuals() {
        return individuals;
    }

    public Individual getIndividual(int index) {
        return individuals[index];
    }

    public int getWorstPos() {
        return worstPos;
    }

    public int getBestPos() {
        return bestPos;
    }

    public double getAvgFit() {
        return avgFit;
    }

    public double getWorstFit() {
        return worstFit;
    }

    public double getBestFit() {
        return bestFit;
    }

    public double[] getFitness() {
        return fitness;
    }

    public double getFitness(int i) {
        return fitness[i];
    }

    public void setWorstPos(int wPos) {
        worstPos = wPos;
    }

    public void setBestPos(int bPos) {
        bestPos = bPos;
    }

    public void setWorstFit(double wFit) {
        worstFit = wFit;
    }

    public void setBestFit(double bFit) {
        bestFit = bFit;
    }
    
    
    public void setIndividual(int position, Individual indiv, double fit) {
        individuals[position] = indiv;
        fitness[position] = fit;
    }
    
    public void setIndividual(Individual indiv[]) {
        for (int i = 0; i < indiv.length && i < popSize; i++){
            individuals[i] = indiv[i];
        }
        
    }
   
    //深拷贝
    public Individual getIndividualCopy(int i){                   
        Individual indiv = new Individual();
        indiv.generateIndividual();
        for(int j = 0; j < 20; j++)
        {
            indiv.setAllele(j, individuals[i].getAllele(j));
        }
        indiv.calFitness();
        return indiv;
    }

    public void calFitnessValues() {
        worstFit = Double.MAX_VALUE;
        bestFit = Double.MIN_VALUE;
        for (int i = 0; i < this.popSize; i++) {
                double newFit = individuals[i].calFitness();
                fitness[i] = newFit;
                if (newFit < worstFit) { 
                    worstPos = i; 
                    worstFit = newFit;
                }
                if (newFit > bestFit) { 
                    bestPos = i; 
                    bestFit = newFit;
                }
            }
        calAvgFitness();
    }

    public double calAvgFitness() {
        double sumFit = 0;
        double BestFit = 0;
        for (int i = 0; i < this.popSize; i++)
        {
            sumFit += fitness[i];
            if (fitness[i] > BestFit)
            {
                BestFit = fitness[i];
            }
        }
        avgFit = (double) sumFit / ((double) this.popSize);
        setBestFit(BestFit);
        return avgFit;
    }

    public String toString() {
        String str = "";
        for (int i = 0; i < this.popSize; i++) {
            for (int j = 0; j < 20; j++)
                str += individuals[i].getAllele(j);
            str += "\n";
        }
        return str;
    }
    
    public void dumpMyself(){
        System.out.println("size : "+popSize+", bestFitness is "+bestFit);
    }
    public int getFeval(){
    	int nFeval = 0;
    	for (int i = 0; i < popSize; i++){
    		if (individuals[i].isChanged()){
    			nFeval++;
    			individuals[i].setChanged(false);
    		}
    	}
    	return nFeval;
    }
}

/*
 * 
 * // List类型，可以随时进行增删 List<Individual> popList;
 * 
 * Individual indiv;// 种群中某个特定的个体 // public Individual[] individuals;
 * 
 * Individual bestIndividual;// 当代适应度最好的个体
 * 
 * Individual worstIndividual;// 当代适应度最差的个体
 * 
 * Individual currentBest; // 截止当代之前适应度最好的个体
 * 
 * // private int popSize;// 种群中个体的个数
 * 
 * private double avgFitness; // 平均适应度
 * 
 * private double bestFitness; // 最高适应度值 //protected int worstPos, bestPos;
 * 
 * 
 * 
 * // 构造函数 public Population(int popSize) { popList = new
 * ArrayList<Individual>(); for (int i = 0; i < popSize; i++) { Individual indiv
 * = new Individual(); popList.add(indiv); } }
 * 
 * // 初始化种群 public void initPopulation() { for (int i = 0; i < getPopSize();
 * i++) { getIndividual(i).generateIndividual(); Individual indiv =
 * getIndividual(i); indiv.setIndivAge(0); } }
 * 
 * public int getBestPos() { return bestPos; }
 * 
 * // 获取种群中的特定个体 public Individual getIndividual(int index) { indiv =
 * popList.get(index); return indiv; }
 * 
 * // 向种群中加入特定的个体 public void addIndividual(Individual indiv) {
 * popList.add(indiv); }
 * 
 * // 在种群中删除特定的个体 public void deleteIndividual(int index) {
 * popList.remove(index); }
 * 
 * // 获取种群的个数 public int getPopSize() { popSize = popList.size(); return
 * popSize; }
 * 
 * // 获取总的适应度值 public double calTotalFitness() { double totalFitness = 0; for
 * (int i = 0; i < popList.size(); i++) { totalFitness +=
 * popList.get(i).getFitness(); } return totalFitness; }
 * 
 * // 获得种群中最佳的个体 public Individual findBestIndividual() { bestIndividual =
 * worstIndividual = popList.get(0); for (int i = 1; i < getPopSize(); i++) { if
 * (popList.get(i).getFitness() > bestIndividual.getFitness()) { bestIndividual
 * = popList.get(i); } if (popList.get(i).getFitness() <
 * worstIndividual.getFitness()) { worstIndividual = popList.get(i); } }
 * 
 * if (Evolve.getGeneration() == 1) {// 初始种群 currentBest = (Individual)
 * bestIndividual.clone(); } else { if (bestIndividual.getFitness() >
 * currentBest.getFitness()) currentBest = (Individual) bestIndividual.clone();
 * } return bestIndividual; }
 * 
 * // 获取平均适应度值 public double getAvgFit() { avgFitness = calTotalFitness() /
 * popList.size(); return avgFitness; }
 * 
 * // 获取当前种群中的最高适应度值 public double getBestFit() { bestFitness =
 * getIndividual(0).getFitness(); for (int i = 1; i < getPopSize(); i++) {
 * double fitness = getIndividual(i).getFitness(); if (bestFitness <= fitness) {
 * bestFitness = fitness; } } return bestFitness; }
 * 
 * 
 * 单点交叉操作
 * 
 * public static void crossover(List<Individual> popList) { for (int i = 0; i <
 * popList.size() / 2 * 2; i += 2) { int rnd; // 随机两两配对 rnd = rand(i,
 * popList.size());
 * 
 * if (rnd != i) exchange(popList, i, rnd);
 * 
 * rnd = rand(i, popList.size()); if (rnd != i + 1) exchange(popList, i + 1,
 * rnd);
 * 
 * // 交叉 double random = rand();
 * 
 * if (random < GeneticAlgorithms.crossoverRate) { cross(popList, i); } } }
 * 
 * // 执行交叉操作 private static void cross(List<Individual> popList, int i) { String
 * chromFragment1, chromFragment2;// 基因片段
 * 
 * int rnd = rand(0, GeneticAlgorithms.chromLen - 1);// 交叉点为rnd之后,可能的位置有chromlen
 * // - 1个. chromFragment1 = popList.get(i).getChrom(rnd + 1,
 * GeneticAlgorithms.chromLen - 1); chromFragment2 = popList.get(i +
 * 1).getChrom(rnd + 1, GeneticAlgorithms.chromLen - 1);
 * 
 * popList.get(i).setChrom(rnd + 1, GeneticAlgorithms.chromLen - 1,
 * chromFragment2); popList.get(i + 1).setChrom(rnd + 1,
 * GeneticAlgorithms.chromLen - 1, chromFragment1); }
 * 
 * // 产生随机数 private static int rand(int start, int end) {// 产生区间为[start ,
 * end)的随机整数 return (int) (rand() * (end - start) + start); }
 * 
 * // 交换 private static void exchange(List<Individual> popList, int src, int
 * dest) { Collections.swap(popList, src, dest); }
 * 
 * 
 * 变异操作
 * 
 * public static void mutate(List<Individual> popList) { for (int i = 0; i <
 * popList.size(); i++) { for (int j = 0; j < GeneticAlgorithms.chromLen; j++) {
 * if (rand() < GeneticAlgorithms.mutateRate) {
 * popList.get(i).mutateSingleBit(j); // /System.out.print("变异"+ i +" - "+ j +
 * "  ");/// } } } }
 * 
 * // 产生随机数 private static double rand() { return Math.random(); }
 * 
 * public String toString() { String str = ""; for (int i = 0; i <
 * popList.size(); i++) str += popList.get(i); return str; }
 * 
 * public String getN() { // TODO Auto-generated method stub return null; }
 */

