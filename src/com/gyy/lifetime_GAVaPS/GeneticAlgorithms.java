/**
 * 
 */
package com.gyy.lifetime_GAVaPS;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;


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
        maxGeneration  = 3;  
        populationSize = 20;  
        crossoverRate = 0.65;  
        mutateRate = 0.015;
        reproductionRatio = 0.4;
    }  
    public static void main(String[] args)throws IOException{  

        FileWriter fw = new FileWriter("result_GAVaPS.txt");  
        BufferedWriter bw = new BufferedWriter(fw);  
        PrintWriter pw = new PrintWriter(bw);  
          
        Population pop = new Population(populationSize);  
        pop.initPopulation();
  
        System.out.println(pop.toString());
        //pw.println("初始种群:\n" + pop);  
        while(!Evolve.isEvolutionDone()){
            Evolve.evolve(pop);
            pw.println("generation "+Evolve.getGeneration()+":current popsize  "+pop.getPopSize());
            pw.print("current bestIndividual: fitness" + pop.bestIndividual );  
            pw.print("bestIndvidual: fitness" + pop.currentBest );  
            pw.println(""); 
           // pw.flush();
        }  
        pw.println();  
       // pw.println("第"+ pop.getGeneration()  + "代群体:\n" + pop);  
       // pw.flush();
        pw.close();  
    }  
      
    public void print(){  

    }  
}
