
package com.gyy.MultiPops_PGA;

/**
 * @author Gloria
 * 
 */
public class GeneticAlgorithms {
    public static long parRuns = 1; 

    public static int nSuccess = 0; 
    
    public static double optimValue = 3905;

    public static void main(String[] args) {
        
        ParEngine parEngine = new ParEngine();
        ParPress.printInitialInfo();
        long starttime = System.currentTimeMillis();
        for (int r = 0; r <ParEngine.parRuns; r++) {
            ParPress.printRunInitialInfo(r);
            nSuccess += parEngine.RUN(r);
            ParPress.printRunFinalInfo(r);
        }
        long endtime = System.currentTimeMillis();
        System.out.println("the total evolve time: "+(endtime-starttime));
    }
}
