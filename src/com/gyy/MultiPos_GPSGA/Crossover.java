/**
 * Crossover:
 * use uniform crossover
 */
package com.gyy.MultiPos_GPSGA;

import java.util.Random;


/**
 * @author Gloria
 *
 */
abstract class Crossover{
    protected double pCrossover;
    abstract public Individual[] cross(Population selectedSet);
}

class UniformCrossover extends Crossover{
    private double pSwap;
    
    public UniformCrossover(double pCrossover, double pSwap){
        this.pCrossover = pCrossover;
        this.pSwap = pSwap;
    }
    
    public Individual[] cross(Population selectedSet){
        int NS = selectedSet.getPopSize();
        Individual[] newIndividuals = new Individual[NS];
        for(int i = 0; i < NS - 1; i += 2){                               
            Individual indiv1 = selectedSet.getIndividualCopy(i),
                       indiv2 = selectedSet.getIndividualCopy(i+1);
            if(random.nextDouble() < pCrossover)
                for(int j = 0; j < 20; j++)  
                    if(random.nextDouble() < pSwap){
                        char allele = indiv1.getAllele(j);
                        indiv1.setAllele(j, indiv2.getAllele(j));
                        indiv2.setAllele(j, allele);
                        indiv1.change(true);
                        indiv2.change(true);
                    }
            newIndividuals[i] = indiv1;
            newIndividuals[i+1] = indiv2;
        }
        return newIndividuals;
    }
    
    public static Random        random = new Random();
}

