package com.gyy.Shrink_SAMNP;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
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
	public static Population IncreaseNP(Population pop) {
		int nPopSize = pop.getPopSize();
		nPopSize = (int) ((double) nPopSize * (double) (1.0 + fPopsizeFloat));
		Population newPop = new Population(nPopSize);
		newPop.initPopulation();
		Individual[] invs = pop.getIndividuals();
		for (int i = 0; i < invs.length; i++) {
			newPop.setIndividual(i, invs[i], 0);
		}
		newPop.calFitnessValues();
		return newPop;
	}

	public static Population DecreaseNP(Population pop) {
		int nPopSize = pop.getPopSize();
		nPopSize = (int) ((double) nPopSize * (double) (1.0 - fPopsizeFloat));
		Population newPop = new Population(nPopSize);
		newPop.initPopulation();
		Individual[] invs = pop.getBestIndividuals(nPopSize);
		for (int i = 0; i < invs.length; i++) {
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

	public static void run() throws IOException {
		Population pop = new IndivPopulation(m_nInitalSize);
		int nFeval = 0;
		pop.calFitnessValues();
		nFeval = pop.getPopSize();
		double fBestFiness = -Double.MAX_VALUE;
		Individual inv = pop.getBestIndividual();
		int p = 0;
		generation = 1;

		long starttime = System.currentTimeMillis();

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
			//System.out.print(generation + ": ");
		//	pop.dumpMyself();
			if (p >= 4) {
				pop = IncreaseNP(pop);
				p = 0;
				generation++;
			} else if (p == 0) {
				pop = DecreaseNP(pop);
				generation++;
			}
		}

		DecimalFormat df = new DecimalFormat("######0.00000");

	//	System.out.println("BestFitness : " + df.format(-fBestFiness));
	//	inv.dumpMyself();
		long endtime = System.currentTimeMillis();
	//	System.out.println("the total evolve time: " + (endtime - starttime));

		FileWriter fw = new FileWriter("data_txt/SAMNP_SphereModel_10000.txt", true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw);
		pw.println("bestFitness: " + df.format(-fBestFiness));
		pw.println("the total evolve time: " + (endtime - starttime));
		pw.flush();
		pw.close();
		fw.close();
	}

	public static int getGeneration() {
		return generation;
	}

	public static void main(String[] args) throws IOException {
		File f = new File("data_txt/SAMNP_SphereModel_10000.txt");
		if (!f.exists()) {
			f.createNewFile();
		}
		FileWriter fw = new FileWriter(f);
		fw.write("");
		fw.close();
		for (int i = 0; i < 10; i++) {
			run();
		}
	}
}
