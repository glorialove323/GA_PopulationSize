package com.gyy.Shrink_SAMNP;

import java.util.List;

public class SAMNPSolver {

	private static int generation; // 种群的代数
	static List<Population> m_List = null;
	public static int m_nInitalSize;
	public static int maxnfeval = 100000;
	public static int pmax = 4;
	public static double fPopsizeFloat = 0.05;

	static {
		m_nInitalSize = 200;
	}

	/*
	 * public static void evolve(Population pop) { Crossover cros = new
	 * UniformCrossover(0.65, 0.5); Mutation muta = new Mutation(0.015);
	 * Individual[] inv = cros.cross(pop); for (int i = 0; i < pop.getPopSize();
	 * i++){ pop.setIndividual(i, inv[i], 0); }
	 * muta.mutate(pop.getIndividuals()); }
	 */
	public static Population IncreaseNP(Population pop) {
		int nPopSize = pop.getPopSize();
		nPopSize = (int)((double)nPopSize * (double)(1.0 + fPopsizeFloat));
		Population newPop = new Population(nPopSize);
		newPop.initPopulation();
		Individual[] invs = pop.getIndividuals();
		for (int i = 0; i < invs.length; i++){
			newPop.setIndividual(i, invs[i], 0);
		}
		newPop.calFitnessValues();
		return newPop;
	}

	public static Population DecreaseNP(Population pop) {
		int nPopSize = pop.getPopSize();
		nPopSize = (int)((double)nPopSize * (double)(1.0 - fPopsizeFloat));
		Population newPop = new Population(nPopSize);
		newPop.initPopulation();
		Individual[] invs = pop.getBestIndividuals(nPopSize);
		for (int i = 0; i < invs.length; i++){
			newPop.setIndividual(i, invs[i], 0);
		}
		newPop.calFitnessValues();
		return newPop;
	}

	public static int evolve(Population pop) {
		Crossover cross = new UniformCrossover(0.65, 0.5);
		Mutation muta = new Mutation(0.015);
		cross.cross(pop);
		muta.mutate(pop.getIndividuals());
		pop.calFitnessValues();
		return pop.getFeval();
	}

	public static void run() {
		Population pop = new IndivPopulation(m_nInitalSize);
		int nFeval = 0;
		pop.calFitnessValues();
		nFeval = pop.getPopSize();
		double fBestFiness = 0;
		Individual inv = pop.getBestIndividual();
		int p = 0;
		generation = 1;
		while (nFeval < maxnfeval) {
			nFeval += evolve(pop);
			double fCurBestFitness = pop.getBestFit();
			if (fCurBestFitness > fBestFiness) {
				fBestFiness = fCurBestFitness;
				inv = pop.getBestIndividual();
				p = 0;
			} else {
				p++;
			}
			System.out.print(generation+": ");
			pop.dumpMyself();
			if (p >= 4) {				
				pop = IncreaseNP(pop);
				p = 0;			
				generation++;
			} else if (p == 0) {
				pop = DecreaseNP(pop);
				generation++;
			}
		}
		System.out.println("Best : " + fBestFiness);
		inv.dumpMyself();
		
	}

	public static int getGeneration() {
		return generation;
	}

	public static void main(String args[]) {
		run();
	}
}
