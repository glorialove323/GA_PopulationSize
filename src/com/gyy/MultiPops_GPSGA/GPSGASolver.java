package com.gyy.MultiPops_GPSGA;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class GPSGASolver  {

    static List<Population> m_List = null;
    static int MaxFitnessCalls = 10000;
    public static int m_nInitalSize;
    static{
       m_nInitalSize = 20;
    }

    public static void evolve(Population pop)
    {
        Crossover cros = new UniformCrossover(0.65, 0.5);
        Mutation muta = new Mutation(0.015);  
        Individual[] inv = cros.cross(pop);
        for (int i = 0; i < pop.getPopSize(); i++){
            pop.setIndividual(i, inv[i], 0);
        }
        muta.mutate(pop.getIndividuals());
    }
    public static boolean stop(){
    	//
    	int nFitnessCallsCount = 0;
        for (int i = 0; i < m_List.size(); i++){
            nFitnessCallsCount += m_List.get(i).getFitnessCalls();
        }
//        System.out.println("nFitnessCallsCount = "+nFitnessCallsCount);
        if (nFitnessCallsCount > MaxFitnessCalls){
        	return true;
        }
    	return false;
    }
    public static void evolve(Population pop, int fitnessCalls){
    	
    	while(fitnessCalls > 0){
    		Selection selection = new TourWithoutReplacement(pop.getPopSize(), 2);
        	SelectedSet selectedSet = selection.select(pop);
        	
        	Crossover crossover = new UniformCrossover(0.65, 0.5);
            Individual[] newIndividuals = crossover.cross(selectedSet);
            
            Mutation mutation = new Mutation(0.0);
            mutation.mutate(newIndividuals);
            FullReplacement replacement = new FullReplacement();
            replacement.replace(pop, newIndividuals); 
            pop.calAvgFitness();
            
            fitnessCalls -= newIndividuals.length;
            pop.incFinessCalls(newIndividuals.length);
    	}

    }
    
    public static void run() throws IOException{
        m_List = new ArrayList<Population>();
        int i = 0;
        int j = 0;
        int nPopSize = m_nInitalSize;
        int M = 2 * nPopSize;
        Population pop1 = new Population(nPopSize);
        Population pop2 = new Population(nPopSize * 2);
        m_List.add(i, pop1);
        m_List.add(i+1, pop2);
        
        long starttime = System.currentTimeMillis();
        while (stop() == false){
            evolve(pop1, M);
            evolve(pop2, M);
            if (pop1.expired(pop2)){
                i++;
                pop1 = pop2;
                pop2 = new Population(pop1.getPopSize() * 2, pop1);
                M = pop2.getPopSize();
                m_List.add(i+1, pop2);
                evolve(pop2, pop1.getFitnessCalls());
            }
        }
        long endtime = System.currentTimeMillis();
        int nFitnessCallsCount = 0;
        double fBestFit = -Double.MAX_VALUE;
        for (j = 0; j < m_List.size(); j++){
            Population pop = m_List.get(j);
            
            //pop.dumpMyself();
            
            if (fBestFit < pop.getBestFit()){
            	fBestFit = pop.getBestFit();
            }
            nFitnessCallsCount += pop.getFitnessCalls();
        }
        DecimalFormat df = new DecimalFormat("######0.00000");
       // System.out.println("the best fitness value is "+df.format(fBestFit) );
      //  System.out.println("the fitnessCalls is "+nFitnessCallsCount);
       // System.out.println("the total evolve time: "+(endtime-starttime));
        
        FileWriter fw = new FileWriter("data_txt/GPSGA_SphereModel_10000.txt", true);  
        BufferedWriter bw = new BufferedWriter(fw);  
        PrintWriter pw = new PrintWriter(bw); 
        pw.println("bestFitness: "+df.format(-fBestFit)); 
        pw.println("the total evolve time: " + (endtime - starttime));
        pw.flush();
        pw.close(); 
        fw.close();   
    }
    
    public static void main(String [] args) throws IOException{
    	File f = new File("data_txt/GPSGA_SphereModel_10000.txt");
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
