/**
 * 
 */
package lifetime;

/**
 * @author Gloria
 *
 */
public class Algorithm{
	
	private static double mUniformRate = 0.5;
	public static double mCrossoverRate = 1.0;
	public static double mMutationRate = 0.001;
	final static int ITERATION = 10;
	
	public static Population evolvePopulation(Population pop){
		//iteration=0时，先进行第一次进化
		Population newPopulation  = new Population(pop.size());
		int newPopulationSize = newPopulation.size();
		//先判断能否执行交叉，再进行交叉
		for(int i=0;i<newPopulationSize;i++){
			if(Math.random()<=mCrossoverRate){					
				Individual dadIndividual = randomSelection(newPopulation);
				Individual momIndividual = randomSelection(newPopulation);				
				Individual newIndiv= crossover(dadIndividual, momIndividual);
				newPopulation.saveIndividual(newIndiv);
				System.out.println("CROSSOVER"+((double)i/newPopulation.size())+"<="+mCrossoverRate);
			}
			else{
				newPopulation.saveIndividual(newPopulation.getIndividual(i));
				System.out.println("NO CROSSOVER"+ ((double)i/newPopulation.size())+">"+mCrossoverRate);
			}
		}					
		//执行变异过程，参加重组后产生的个体也参与变异
		for(int i=0;i<newPopulation.size();i++){
			mutate(newPopulation.getIndividual(i));
		}
		double bestFit = newPopulation.getMaxFit();
		double worstFit = newPopulation.getWorstFit();
		double avgFit = newPopulation.getAvgFit();
		
		for(int i =0;i<newPopulationSize;){
			newPopulation.getIndividual(i).calcAge();
			int age = newPopulation.getIndividual(i).getAge();
			int lifetime = newPopulation.getIndividual(i).getLifetime(bestFit, avgFit, worstFit);
			if (age > lifetime){
				newPopulation.deleteIndividual(i);
			}
			else{
				i++;
			}
		}
		//在执行过一次进化之后，开始之后的进化过程
		int t=0;
		while(t<ITERATION){
			t+=1;
	
			//个体是否执行交叉，通过产生一个（0，1）的随机值，如果小于或等于交叉概率，则进行交叉
			newPopulationSize = newPopulation.size();
			for(int i=0;i<newPopulationSize;i++){
				if(Math.random() <= mCrossoverRate){					
					Individual dadIndividual = randomSelection(newPopulation);
					Individual momIndividual = randomSelection(newPopulation);				
					Individual newIndiv= crossover(dadIndividual, momIndividual);
					newPopulation.saveIndividual(newIndiv);
					System.out.println("CROSSOVER"+((double)i/newPopulation.size())+"<="+mCrossoverRate);
				}
				else{
					newPopulation.saveIndividual(newPopulation.getIndividual(i));
					System.out.println("NO CROSSOVER"+ ((double)i/newPopulation.size())+">"+mCrossoverRate);
				}
			}
					
			//执行变异
			for(int i=0;i<newPopulationSize;i++){
				mutate(newPopulation.getIndividual(i));
			}
			
			bestFit = newPopulation.getMaxFit();
			worstFit = newPopulation.getWorstFit();
			avgFit = newPopulation.getAvgFit();
			for(int i =0;i<newPopulationSize;){
				newPopulation.getIndividual(i).calcAge();
				int age = newPopulation.getIndividual(i).getAge();
				int lifetime = newPopulation.getIndividual(i).getLifetime(bestFit, avgFit, worstFit);
				if (age > lifetime){
					newPopulation.deleteIndividual(i);
				}
				else
				{
					i++;
				}
			}
		}
		return newPopulation;
	}
	//执行单点交叉，每次取两个父代，交叉之后产生一个个体
	//个体基因型上的每一位是否执行交叉，通过随机的值是否大于给定的mUniformRate值
	private static Individual crossover(Individual indiv1,Individual indiv2){
		Individual newSol = new Individual(indiv1, indiv2, mCrossoverRate);
		 System.out.println("crossover between " + indiv1.toString() + " and " + indiv2.toString());
	     System.out.println("crossover effect:" + newSol.toString());    
	     return newSol;
	}
	//执行变异操作
	//个体基因型上的每一位如果小于或等于给定的变异概率，则该基因位由“0”换成“1”，由“1”换成“0”
	private static void mutate(Individual indiv){
		indiv.mutate(mMutationRate);
	}
	//random select
	private static Individual randomSelection(Population pop) {
			
            int randomId = (int) (Math.random() * pop.size());
            return pop.getIndividual(randomId);
	}
}