package com.gyy.Shrink_dynNP;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class dynNPSolver  {

    static List<Population> m_List = null;
    public static int m_nInitalSize;
    public static int maxnfeval = 100000;
    public static int pmax = 4;
    static{
       m_nInitalSize = 200;
    }
    
    public static double evolve(Population pop){
        int oldPopSize = pop.getPopSize();
        System.out.println("current population size is: "+oldPopSize);
        int gen = maxnfeval/pmax/(oldPopSize);
        double fBestFitness = 0;
        for(int i=0;i<gen;i++){
            Crossover cros = new UniformCrossover(0.65, 0.5);
            Mutation muta = new Mutation(0.015);
            Individual[] inv = cros.cross(pop);
            for (int j = 0; j < pop.getPopSize(); j++){
                pop.setIndividual(j, inv[j], 0);
            }
            muta.mutate(pop.getIndividuals());
            System.out.print(i+":");
            pop.calFitnessValues();
            if (pop.getBestFit() > fBestFitness){
            	fBestFitness = pop.getBestFit();
            }
            pop.dumpMyself();
        }
        return fBestFitness;

    }
    
    public static Population reduce(Population pop){
    	int oldPopSize = pop.getPopSize();
        int newPopSize = oldPopSize/2;
        Population newPop = new Population(newPopSize);
        Reduction rect = new Reduction();
        rect.reduction(pop, newPop);
        System.out.println("new population size is: "+newPop.getPopSize());
        return newPop;
    }
    
    public static void run(){
        int i=0;
        Population pop = new IndivPopulation(m_nInitalSize);
        List<Double> listBestFit = new ArrayList<Double>();
        while (i < pmax){
            System.out.println("Generation: "+(i+1));
            double fBestFitness = evolve(pop);
            listBestFit.add(fBestFitness);
            pop.dumpMyself();
            Population newPop = reduce(pop);
            pop = newPop;
            i++;
        }
        System.out.println("bestIndividual: "+pop.getIndividual(pop.getBestPos()));
        
        DecimalFormat df = new DecimalFormat("######0.0000"); 
        
        for(i = 0; i < listBestFit.size(); i++){
        	System.out.println("--------------------------------");
        	System.out.println("generation : "+i+" bestFuc ="+df.format(1/listBestFit.get(i)));
        }
    }
    
    public static void main(String args[]){
        long starttime = System.currentTimeMillis();
        run();
        long endtime = System.currentTimeMillis();
        System.out.println("the total evolve time: "+(endtime-starttime));
    }
}
