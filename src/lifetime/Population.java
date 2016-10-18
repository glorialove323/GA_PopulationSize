package lifetime;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Gloria
 *
 */

public class Population {
	//list类型，可以随意进行添加和删除
   List<Individual> mIndividualList;
   
   public Population(int populationSize){
//	   mIndividualList = new LinkedList<Individual>();
	   mIndividualList = new ArrayList<Individual>();
		   for(int i=0;i<populationSize;i++){
			   Individual newIndividual = new Individual();
			   mIndividualList.add(newIndividual);
		   }
   }//给定一个population size之后，生成这个大小的种群，存放在list里面
   
   //获取种群的一个特定的个体
   public Individual getIndividual(int index){
	   return mIndividualList.get(index);
   }
   //获得种群中最佳的个体
   public Individual getFittest(){
	   Individual fittest = mIndividualList.get(0);
	   for(int i=1;i<size();i++){
		   //迭代比较适应度值大小，得到结果最佳的个体，可以进行进一步优化（用迭代器）
		   if(fittest.getFitness()<=getIndividual(i).getFitness()){
			   fittest = getIndividual(i);
		   }
	   }
	   return fittest;
   }
   //获取当前种群中的最高适应度值
   public double getMaxFit(){
	   Individual idv = null;
	   double maxFitness = getIndividual(0).getFitness();
	   for(int i=1;i<size();i++){
		   double fitness = getIndividual(i).getFitness();
		   if(maxFitness <= fitness){
			   maxFitness = fitness;
			   idv = getIndividual(i);
		   }
	   }
	   idv.dump();
	   
	   return maxFitness;
   }
   //获取当前种群中的最低适应度值
   public double getWorstFit(){
	   double worstFitness = getIndividual(0).getFitness();
	   for(int i=1;i<size();i++){
		   double fitness = getIndividual(i).getFitness();
		   if(worstFitness>= fitness){
			   worstFitness = fitness;
		   }
	   }
	   return worstFitness;
   }
   //获取当前种群中的平均适应度值
   public double getAvgFit(){
	   double sumFitness =0;
	   double avgFitness = 0;
	   for(int i=0;i<size();i++){
		   sumFitness += getIndividual(i).getFitness();
	   }
	   avgFitness = sumFitness/size();
	   return avgFitness;
   }
   //获取种群规模
   public int size(){
	   return mIndividualList.size();
   }
   //将新产生的个体加入到种群的列表中，并且添加是无序的
   public void saveIndividual(Individual indiv) {
       mIndividualList.add(indiv);
   }
   //从种群中移除某个个体
   public void deleteIndividual(Individual indiv){
	   mIndividualList.remove(indiv);
   }
   
   //从种群中移除某个个体
   public void deleteIndividual(int nIndex){
	   mIndividualList.remove(nIndex);
   }
}