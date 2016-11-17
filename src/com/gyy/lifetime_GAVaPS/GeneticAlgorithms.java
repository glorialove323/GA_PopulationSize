/**
 * GAVaPS
 * 缺点：1、当初始种群很差的时候，个体的淘汰率也很大，导致最后的个体削减的很快，实验结果不理想
 * 缺点：2、当初始种群很好的时候，个体的淘汰率很小，导致种群中的个体猛涨，影响进化效率
 * 缺点：3、很难运行到一个比较合适的时机，使得实验结果不错且种群中的个数不猛涨
 * 缺点：4、相比简单遗传算法而言，所需进化时间要久一些
 */
package com.gyy.lifetime_GAVaPS;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

/**
 * @author Gloria
 *
 */
public class GeneticAlgorithms {
    public static double crossoverRate;//交叉概率  
    public static double mutateRate;//变异概率  
    public static int populationSize;//群体大小  
    public static int maxGeneration;
    public static double reproductionRatio;
    public static int chromLen;
    public static int popSize;
      
    static {  
        popSize = 20;
        chromLen = 20;
        maxGeneration  = 40;  
        populationSize = 20;  
        crossoverRate = 0.65;  
        mutateRate = 0.015;
        reproductionRatio = 0.4;
    }  
    public static void main(String[] args) throws IOException{  

        FileWriter fw = new FileWriter("result_GAVaPS.txt");  
        BufferedWriter bw = new BufferedWriter(fw);  
        PrintWriter pw = new PrintWriter(bw);  
          
        Population pop = new Population(populationSize);  
        pop.initPopulation();
  

        System.out.println(pop.toString());
        pw.println("初始种群:\n" + pop);  
        DecimalFormat df = new DecimalFormat("######0.000"); 
        
        long startTime = System.currentTimeMillis();
        while(!Evolve.isEvolutionDone()&&(!Evolve.isPopSizeZero(pop))){
            Evolve.evolve(pop);
            System.out.println("popsize: "+pop.getPopSize());
            pw.println("generation "+Evolve.getGeneration()+":current popsize  "+pop.getPopSize());
            pw.print("current bestIndividual: fitness" + df.format(pop.getBestFitness()));  
            System.out.println("current bestFitness： "+ df.format(pop.getBestFitness()));
            
            //System.out.println("current best individual: "+pop.findBestIndividual());
           
            pw.print("    bestIndvidual: fitness" + df.format(pop.currentBest.getFitness()) );
            System.out.println("bestFitness: "+df.format(pop.currentBest.getFitness()));
            pw.println(""); 
            pw.flush();
        }  
        long endTime = System.currentTimeMillis();
        System.out.println("the total evolve time: " + (endTime - startTime));
        pw.println();  
        pw.println("第"+ Evolve.getGeneration()  + "代群体:\n" + pop);  
        pw.flush();
        pw.close();  
    }  
      
    public void print(){  
    }  
}
