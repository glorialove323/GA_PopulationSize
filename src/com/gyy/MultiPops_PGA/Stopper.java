/**
 * Stopper:判断循环什么时候停止
 * 1、给定固定的iteration次数，达到上限之后就结束操作
 * 2、种群处于收敛状态，当平均适应度等于最大是适应度的时候
 */
package com.gyy.MultiPops_PGA;

import com.gyy.MultiPops_PGA.SGA.Population;

/**
 * @author Gloria
 *
 */
public class Stopper{
    
    public static int   maxNGen = 200000;            // Maximal number of generations to perform.             Default = 200
    public static long  maxFitCalls= 200000;        // Maximal number of fitness calls.                      Default = -1 (unbounded)
    public static int   allFitnessEqual = -1;    // Stop if all individuals have the same fitness.        Default = -1 (ignore)
    public static float maxOptimal= (float) 0.00001;         // Proportion of optimal individuals threshold           Default = -1 (ignore)
    public static int   foundBestFit= -1;       // Stop if the optimum was found?                        Default = -1 (-1 -> no; 1 -> yes)
    
    private static boolean success = false; 
    
    public static boolean foundOptimum(){return success;}
    
    public static void setSuccess(boolean suc){success = suc;}
    
    public static boolean criteria(int nGen, Population population){
        return nGeneration(nGen)               || 
               fitnessCalls();                    
  }
    
    private static boolean nGeneration(int nGen){return nGen > maxNGen;}
    
    public static boolean fitnessCalls(){return (maxFitCalls == -1)? false: ParEngine.fitCalls >= maxFitCalls;}
   /* 
    private static boolean optimalThreshold(Population population){ // NOTE: optimalThreshold criteria depends on the optimum value!
        int N = population.getPopSize();
        if(maxOptimal == -1)
            return false;
        double[] fitness = population.getFitness();
        int nOptimum = 0;
        for(int i = 0; i < N; i++)
            if(fitness[i] >= GeneticAlgorithms.optimValue)
                nOptimum++;
        //System.out.println("nOptimum = " +nOptimum);
        return ((float)nOptimum)/((float)N) >= maxOptimal;
    }*/
}

