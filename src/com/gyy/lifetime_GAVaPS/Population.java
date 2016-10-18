/**
 * 
 */
package com.gyy.lifetime_GAVaPS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * @author Gloria
 * 
 */
class Population {

    private int generation; // 种群的代数

    private int popSize; // 群体大小

    private double averageFitness; // 平均适应度

    private double[] relativeFitness; // 相对适应度

    private int chromlen;// 基因长度

    Individual bestIndividual;// 当前代适应度最好的个体

    Individual worstIndividual;// 当前代适应度最差的个体

    Individual currentBest;// 到目前代为止最好的个体

    private int worstIndex;// bestIndividual对应的数组下标

    /*
     * 生命周期lifetime
     */
    private int indivlifetime;// 每个个体的生命周期

    private int MinLT; // 最小生命周期

    private int MaxLT; // 最大生命周期

    /*
     * 适应度
     */
    private double avgFitness; // 平均适应度

    private double bestFitness; // 最大适应度

    private double worstFitness; // 最小适应度

    List<Individual> popList=new ArrayList<Individual>();
    
    public Population(int popSize) {
        this.generation = 0;  
        this.popSize = popSize;  
        this.averageFitness = 0;  
        this.chromlen = 20; 
     
        for(int i = 0;i<popSize;i++){
            //G1Individual indiv = new G1Individual(chromlen);
            Individual indiv = new G1Individual(chromlen);
            popList.add(indiv);
        }   
    }
    //初始化种群
    public void initPopulation(){  
        for(int i = 0;i < popSize;i++){  
            popList.get(i).generateIndividual();  
        }            
        findBestAndWorstIndividual();                  
    }  

    /*
     * 计算个体的lifetime值 需要每个个体的fitness，当前种群中最好的fitness，最差的fitness，平均fitness
     */
    // 求总适应度
    private double calTotalFitness() {
        double total = 0;
        for (int i = 0; i <popList.size(); i++)
            total += popList.get(i).getFitness();
        return total;
    }

    // 返回最大适应度值
    public double getBestFitness() {
        bestFitness = 0;
        bestIndividual = popList.get(0);
        for (int i = 1; i < popList.size(); i++) {
            if (popList.get(i).fitness > bestIndividual.fitness) {
                bestIndividual = popList.get(i);
            }
        }
        bestFitness = bestIndividual.getFitness();
        return bestFitness;
    }

    // 返回最小适应度值
    public double getWorstFitness() {
        worstFitness = 0;
        worstIndividual = popList.get(0);
        for (int i = 1; i < popList.size(); i++) {
            if (popList.get(i).fitness < worstIndividual.fitness) {
                worstIndividual = popList.get(i);
                worstIndex = i;
            }
        }
        worstFitness = worstIndividual.getFitness();
        return worstFitness;
    }

    // 返回平均适应度值
    public double getAvgFitness() {
        avgFitness = 0;
        avgFitness = calTotalFitness() / popList.size();
        return avgFitness;
    }

    // 计算相对适应度
    public double[] calRelativeFitness() {
        double totalFitness = calTotalFitness();
        for (int i = 0; i < popList.size(); i++) {
            relativeFitness[i] = popList.get(i).getFitness() / totalFitness;
        }
        return relativeFitness;
    }

    // 找出适应度最大的个体
    public void findBestAndWorstIndividual() {
        bestIndividual = worstIndividual = popList.get(0);
        for (int i = 1; i < popList.size(); i++) {
            if (popList.get(i).fitness > bestIndividual.fitness) {
                bestIndividual = popList.get(i);
            }
            if (popList.get(i).fitness < worstIndividual.fitness) {
                worstIndividual = popList.get(i);
                worstIndex = i;
            }
        }

        if (generation == 0) {// 初始种群
            currentBest = (Individual) bestIndividual.clone();
        } else {
            if (bestIndividual.fitness > currentBest.fitness)
                currentBest = (Individual) bestIndividual.clone();
        }
    }

    // 获得生命周期
    public int getLifetime() {
        return indivlifetime;
    }

    // 计算生命周期
    public int calLifetime(int lifetime, double maxFitness, double minFitness, double avgFitness) {
        this.indivlifetime = lifetime;
        this.bestFitness = maxFitness;
        this.worstFitness = minFitness;
        this.avgFitness = avgFitness;
        for (int i = 0; i < popList.size(); i++) {
            Individual indiv = popList.get(i);
            double indivFitness = indiv.getFitness();
            if (indivFitness <= avgFitness) {
                indivlifetime = (int) (MinLT + 1.0 / 2 * (MaxLT - MinLT) * (indivFitness - minFitness)
                        / (avgFitness - minFitness));
            } else {
                indivlifetime = (int) (1.0 / 2 * (MinLT + MaxLT) + 1.0 / 2 * (MaxLT - MinLT)
                        * (indivFitness - avgFitness) / (maxFitness - avgFitness));
            }
        }
        return indivlifetime;
    }

    /*
     * GAVaPS的操作过程：
     *  1、构造一个auxPopulation，种群规模为原来的规模的40% 
     *  2、对该种群进行交叉和变异
     *  3、将这些个体加入到原来的种群中去 (暂定全部加入到原来的种群中去)
     *  4、根据lifetime机制来消除个体
     */

    //========================recombine操作==========================
    //--------------------------------------------------------------
    public void recombine() {
        int auxPopSize = (int) (popList.size() * 0.4);

        ArrayList<Individual> auxPopList = new ArrayList<Individual>();

        for (int i = 0; i < auxPopSize; i++) {
            auxPopList.add(popList.get((int) (Math.random() * popList.size())));
        }
        crossover(auxPopList);
        mutate(auxPopList);

        for (int i = 0; i < auxPopList.size(); i++) {
            popList.add(auxPopList.get(i));
        }
    }

    //============================elimination操作======================
    //----------------------------------------------------------------
    public void elimination(ArrayList<Individual> popList, int lifetime, double maxFitness, double minFitness,
            double avgFitness) {
        int delete = 0;
        for (int i = 0; i < popList.size();) {
            Individual indiv = popList.get(i);
            int indivLifetime = getLifetime();
            int indivAge = indiv.getAge();

            System.out.println("indiv [" + i + "]" + " lifetime: " + indivLifetime + " age: " + indivAge);

            // 执行删除机制
            if (indivAge >= indivLifetime) {
                popList.remove(i);
                delete = delete + 1;
                System.out.println("indiv [" + i + "]" + " lifetime: " + indivLifetime + " is removed...");
            } else {
                i++;
                System.out.println("lifetime >= age, cannot be removed...");
            }
        }
        System.out.println("delete individuals: " + delete);
        System.out.println("after elimination, current poplation size:" + popList.size());
    
    }
        
    /*
     * 单点交叉操作
     */
    public void crossover(ArrayList<Individual> popList) {
        for (int i = 0; i < popList.size() / 2 * 2; i += 2) {
            int rnd;
            // 随机两两配对
            rnd = rand(i, popList.size());

            if (rnd != i)
                exchange(popList, i, rnd);

            rnd = rand(i, popList.size());
            if (rnd != i + 1)
                exchange(popList, i + 1, rnd);

            // 交叉
            double random = rand();

            if (random < GeneticAlgorithms.crossoverRate) {
                cross(i);
            }
        }
    }

    // 执行交叉操作
    private void cross(int i) {
        String chromFragment1, chromFragment2;// 基因片段

        int rnd = rand(0, getChromlen() - 1);// 交叉点为rnd之后,可能的位置有chromlen - 1个.
        chromFragment1 = popList.get(i).getChrom(rnd + 1, getChromlen() - 1);
        chromFragment2 = popList.get(i+1).getChrom(rnd + 1, getChromlen() - 1);

        popList.get(i).setChrom(rnd + 1, getChromlen() - 1, chromFragment2);
        popList.get(i+1).setChrom(rnd + 1, getChromlen() - 1, chromFragment1);
    }

    // 产生随机数
    private int rand(int start, int end) {// 产生区间为[start , end)的随机整数
        return (int) (rand() * (end - start) + start);
    }

    // 交换
    @SuppressWarnings("unused")
    private void exchange(ArrayList<Individual> popList, int src, int dest) {
        Collections.swap(popList, src,dest);
    }

    /*
     * 变异操作
     */
    public void mutate(ArrayList<Individual> popList) {
        for (int i = 0; i < popList.size(); i++) {
            for (int j = 0; j < getChromlen(); j++) {
                if (rand() < GeneticAlgorithms.mutateRate) {
                    popList.get(i).mutateSingleBit(j);
                    // /System.out.print("变异"+ i +" - "+ j + "  ");///
                }
            }
        }
    }

    // 进化
    public void evolve() {
        recombine();
        evaluate();
    }

    // 计算目标函数值、适应度、找出最优个体。
    public void evaluate() {
        // 同步目标函数值和适应度
        for (int i = 0; i < popList.size(); i++) {
            popList.get(i).calTargetValue();
            popList.get(i).calFitness();
        }

        // 使用最优保存策略(Elitist Model)保存最优个体
        findBestAndWorstIndividual();
        //popList.set(worstIndex, (Individual) currentBest.clone());

        generation++;
    }

    // 判断进化是否完成
    public boolean isEvolutionDone() {
        if (generation < GeneticAlgorithms.maxGeneration)
            return false;
        return true;
    }

    // 计算平均适应度
    public double calAverageFitness() {
        for (int i = 0; i < popList.size(); i++) {
            averageFitness += popList.get(i).getFitness();
        }
        averageFitness /= popList.size();

        return averageFitness;
    }

    // 产生随机数
    private double rand() {
        return Math.random();
    }

    public int getChromlen() {
        return chromlen;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public int getGeneration() {
        return generation;
    }

    public String toString() {
        String str = "";
        for (int i = 0; i < popList.size(); i++)
            str += popList.get(i);
        return str;
    }
}
