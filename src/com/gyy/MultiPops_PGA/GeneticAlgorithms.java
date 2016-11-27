
package com.gyy.MultiPops_PGA;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

/**
 * @author Gloria
 * 
 */
public class GeneticAlgorithms {
    public static long parRuns = 1; 

    public static int nSuccess = 0; 

    public static void run() throws IOException{
        
        ParEngine parEngine = new ParEngine();
      //  ParPress.printInitialInfo();
        long starttime = System.currentTimeMillis();
        for (int r = 0; r <ParEngine.parRuns; r++) {
          //  ParPress.printRunInitialInfo(r);
            nSuccess += parEngine.RUN(r);
          //  ParPress.printRunFinalInfo(r);
        }
        long endtime = System.currentTimeMillis();
       // System.out.println("the total evolve time: "+(endtime-starttime));
        
        DecimalFormat df = new DecimalFormat("######0.00000");
        FileWriter fw = new FileWriter("data_txt/PGA_SphereModel_10000.txt", true);  
        BufferedWriter bw = new BufferedWriter(fw);  
        PrintWriter pw = new PrintWriter(bw); 
        pw.println("bestFitness: "+df.format(-ParEngine.bestSoFar.getFitness())); 
        pw.println("the total evolve time: " + (endtime - starttime));
        pw.flush();
        pw.close(); 
        fw.close();  
        
    }
    
    public static void main(String [] args) throws IOException{
    	File f = new File("data_txt/PGA_SphereModel_10000.txt");
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
