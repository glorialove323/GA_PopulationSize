/**
 * GPS:Greedy Population Sizing
 * the idea is borrowed from parameter-less GA(PGA),and it limits the 
 * parallel evolution to two populations,while PGA does not hava a 
 * limit on the number of populations.
 * 
 * both populations are evolved until they use up their allocated number
 * of function evaluations. 
 * 
 * 两个种群在运行时要实现并行进化
 */
package com.gyy.MultiPops_GPS;



/**
 * @author Gloria
 *
 */
public class GeneticAlgorithms {

    static int evals = 0;//评估次数
    private static int funEvals;//最大运行评估次数
    
    
    class evolve extends Thread{
        public void run(Population pop){
            int oldPopSize = pop.getPopSize();
            select();
            crossover();
            mutate();
            int newPopSize = pop.getPopSize();
            evals = newPopSize-oldPopSize;    
        }
    }
    
    public static boolean isExpired(){
        if(evals>=getFunEvals()){
            return true;
        }
        return false;
    }
    
    public static int getFunEvals(Population pop){
        funEvals = pop.getPopSize();
        return funEvals;
    }
    
    //----------------------------------------------------  
    //比例选择  
    public static void  select(){  
        double[] rouletteWheel; //赌盘  
        Individual[] childPop = new Individual[size];  
          
        calRelativeFitness();  
          
        //产生赌盘  
        rouletteWheel  = new double[size];  
        rouletteWheel[0] = relativeFitness[0];  
        for(int i=1;i<size -1;i++){  
            rouletteWheel[i] = relativeFitness[i] + rouletteWheel[i - 1];  
        }  
        rouletteWheel[size - 1] = 1;  
          
        //进行赌盘选择,产生新种群  
        for(int i = 0;i < size ; i++){  
            double rnd = rand();  
            for(int j = 0; j < size; j++){  
                if(rnd < rouletteWheel[j]){  
                    childPop[i] = pop[j];  
                    break;  
                }      
            }           
        }  
          
        for(int i = 0;i < size; i++){  
            pop[i] = childPop[i];  
        }  
    }  
      
    //求总适应度  
    private static double calTotalFitness(){  
        double total = 0;  
        for(int i = 0 ; i < size ;i++)  
            total += pop[i].getFitness();  
        return total;  
    }  
          
    //计算相对适应度  
    public static double[] calRelativeFitness(){  
        double totalFitness = calTotalFitness();  
        for(int i = 0 ;i < size ; i++){  
            relativeFitness[i] = pop[i].getFitness() / totalFitness;      
        }  
          
        return relativeFitness;  
    }  

    //单点交叉  
    public static void crossover(){  
        for(int i = 0 ; i < size/2*2; i+=2){  
            int rnd;  
            //随机两两配对  
            rnd = rand(i , size);  

            if(rnd != i)  
                exchange(pop , i , rnd);  
                  
            rnd = rand(i , size);  
            if(rnd != i+1)  
                exchange(pop , i + 1 , rnd);      
                      
            //交叉  
            double random = rand();  

            if(random < GeneticAlgorithms.crossoverRate){  
                cross(i);  
            }              
        }  
    }  
      
    //执行交叉操作  
    private void cross(int i){  
        String chromFragment1,chromFragment2;//基因片段  
          
        int rnd = rand(0 , getChromlen() - 1);//交叉点为rnd之后,可能的位置有chromlen - 1个.  
        chromFragment1 = pop[i].getChrom(rnd + 1 , getChromlen() - 1);  
        chromFragment2 = pop[i+1].getChrom(rnd + 1 , getChromlen() - 1);  
              
        pop[i].setChrom(rnd + 1 , getChromlen() - 1 , chromFragment2);  
        pop[i+1].setChrom(rnd + 1 , getChromlen() - 1 , chromFragment1);              
    }  
      
    //产生随机数  
    private int rand(int start , int end){//产生区间为[start , end)的随机整数  
        return (int)(rand()*(end - start) + start);  
    }  
      
    //交换  
    private void exchange(Individual[] p ,int src , int dest){  
        Individual temp;  
        temp = p[src];  
        p[src] = p[dest];  
        p[dest] = temp;      
    }  

    //变异  
    public static void mutate(){  
        for(int i = 0 ; i < size;i++){  
            for(int j = 0 ;j < getChromlen(); j++){  
                if(rand() < GeneticAlgorithms.mutateRate){  
                    pop[i].mutateSingleBit(j);  
                    ///System.out.print("变异"+ i +" - "+ j + "  ");///  
                }      
            }  
        }  
    }  
}
