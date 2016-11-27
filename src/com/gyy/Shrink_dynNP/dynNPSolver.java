package com.gyy.Shrink_dynNP;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
      //  System.out.println("current population size is: "+oldPopSize);
        int gen = maxnfeval/pmax/(oldPopSize);
        double fBestFitness = -(Double.MAX_VALUE);
        for(int i=0;i<gen;i++){
            Crossover cros = new UniformCrossover(0.65, 0.5);
            Mutation muta = new Mutation(0.015);
            Individual[] inv = cros.cross(pop);
            for (int j = 0; j < pop.getPopSize(); j++){
                pop.setIndividual(j, inv[j], 0);
            }
            muta.mutate(pop.getIndividuals());
            //System.out.print(i+":");
            pop.calFitnessValues();
            if (pop.getBestFit() > fBestFitness){
            	fBestFitness = pop.getBestFit();
            }
           // pop.dumpMyself();
        }
        return fBestFitness;

    }
    
    public static Population reduce(Population pop){
    	int oldPopSize = pop.getPopSize();
        int newPopSize = oldPopSize/2;
        Population newPop = new Population(newPopSize);
        Reduction rect = new Reduction();
        rect.reduction(pop, newPop);
      //  System.out.println("new population size is: "+newPop.getPopSize());
        return newPop;
    }
    
    public static void run() throws IOException{
        int i=0;
        Population pop = new IndivPopulation(m_nInitalSize);
        List<Double> listBestFit = new ArrayList<Double>();
        long starttime = System.currentTimeMillis();
        while (i < pmax){
         //   System.out.println("Generation: "+(i+1));
            double fBestFitness = evolve(pop);
            listBestFit.add(fBestFitness);
          //  pop.dumpMyself();
            Population newPop = reduce(pop);
            pop = newPop;
            i++;
        }
      //  System.out.println("bestIndividual: "+pop.getIndividual(pop.getBestPos()));
        
        DecimalFormat df = new DecimalFormat("######0.00000"); 

        double fBestFit = -Double.MAX_VALUE;
        for(i = 0; i < listBestFit.size(); i++){
        	if(listBestFit.get(i)>fBestFit){
        		fBestFit= listBestFit.get(i);
        	}       	
        }
       // System.out.println(" bestFitness: "+df.format(-fBestFit));
        long endtime = System.currentTimeMillis();
        
        FileWriter fw = new FileWriter("data_txt/dynNP_SphereModel_10000.txt", true);  
        BufferedWriter bw = new BufferedWriter(fw);  
        PrintWriter pw = new PrintWriter(bw); 
        pw.println("bestFitness: "+df.format(-fBestFit)); 
        pw.println("the total evolve time: " + (endtime - starttime));
        pw.flush();
        pw.close(); 
        fw.close();
    }
    
   /* public static void main(String args[]){
        long starttime = System.currentTimeMillis();
        run();
        long endtime = System.currentTimeMillis();
        System.out.println("the total evolve time: "+(endtime-starttime));
    }*/
    
    public static void main(String[] args)throws IOException{
    	File f = new File("data_txt/dynNP_SphereModel_10000.txt");
    	if (!f.exists())
    	{
    		f.createNewFile();
    	}
    	FileWriter fw =  new FileWriter(f);
    	fw.write("");
    	fw.close();
    	for(int i = 0;i<10;i++){
    		run();    		
    	}
    }

}
