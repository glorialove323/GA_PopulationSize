/**
 * 
 */
package lifetime;

/**
 * @author Gloria
 *
 */
public class Main {
	 public static double[][] points;
	 public static int popNo = 20;
	 public static int genNo = 10;
	 
	public static void main(String args[]){
		Population myPop = new Population(20);
		myPop = Algorithm.evolvePopulation(myPop);
		double bestFitness = myPop.getMaxFit();
		System.out.println("best  :" + bestFitness);
		/*
		int generationCount = 0;
		points = new double[genNo][popNo];
		while(generationCount<genNo){
			for(int i=0;i<popNo;i++){
				points[generationCount][i]=Coding.binDecode(myPop.getIndividual(i).toString());
			}
			 generationCount++;
			 myPop = Algorithm.evolvePopulation(myPop);
		}		
		*/
	}
}
