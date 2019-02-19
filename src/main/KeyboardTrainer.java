package main;

import java.util.List;

import data.tuple.Tuple2D;
import machinelearning.geneticalgorithm.GAEnvironment;
import machinelearning.geneticalgorithm.GeneticTrainer;

public class KeyboardTrainer implements GeneticTrainer<Keyboard> {
	private double[][] keyEfforts;

	public KeyboardTrainer(double[][] keyEfforts) {
		super();
		this.keyEfforts = keyEfforts;
	}

	public double[][] getKeyEfforts() {
		return this.keyEfforts;
	}

	@Override
	public double calculateFitness(Keyboard a, GAEnvironment<Keyboard> env) {
		double effort = 0;

		for (int i = 0; i < WordSource.bookStr.length(); i++) {
			char c = WordSource.bookStr.charAt(i);
			effort += a.effort(c, this.keyEfforts);
		}
		effort /= 100;
		return -effort;
	}

	@Override
	public double getMutationChance(Keyboard a, GAEnvironment<Keyboard> env) {
		// if (env.getPopulation().indexOf(a) <= env.getPopulation().size() / 20)
		// return 0;
		// return .15;
		return (double) env.getPopulation().indexOf(a) / env.getPopulation().size();
	}

	@Override
	public double getCrossoverChance(Tuple2D<Keyboard, Keyboard> partners, GAEnvironment<Keyboard> env) {
		return .5;
	}

	@Override
	public Keyboard generateRandom(GAEnvironment<Keyboard> env) {
		Keyboard keyboard = new Keyboard();
		keyboard.randomize();
		return keyboard;
	}

	@Override
	public Keyboard crossover(Keyboard ak, Keyboard bk, GAEnvironment<Keyboard> env) {
		int total = 31;
		int startKeepi = (int) (Math.random() * (total - 4)) + 2;
		int endKeepi = (int) (Math.random() * (total - startKeepi)) + startKeepi;
		// System.out.println(startKeepi + " " + endKeepi);
		String used = "";

		String a = null, b = null;
		if (Math.random() < .5) {
			a = ak.getStringRepresetnation();
			b = bk.getStringRepresetnation();
		} else {
			b = ak.getStringRepresetnation();
			a = bk.getStringRepresetnation();
		}
		char[] ckeys = new char[31];

		for (int i = startKeepi; i < endKeepi; i++) {
			ckeys[i] = b.charAt(i);
			used += b.charAt(i);
		}

		int ai = 0;
		for (int i = 0; i < startKeepi; i++) {
			char nxtchar = a.charAt(ai++);
			while (used.contains(nxtchar + "")) {
				nxtchar = a.charAt(ai++);
			}
			ckeys[i] = nxtchar;
			used += nxtchar;
		}
		for (int i = endKeepi; i < ckeys.length; i++) {
			char nxtchar = a.charAt(ai++);
			while (used.contains(nxtchar + "")) {
				nxtchar = a.charAt(ai++);
			}
			ckeys[i] = nxtchar;
			used += nxtchar;
		}
		Keyboard ck = new Keyboard();
		ck.importKeyboard(String.valueOf(ckeys));

		return ck;
	}

	@Override
	public Keyboard mutate(Keyboard a, GAEnvironment<Keyboard> env) {
		Keyboard keyboard = new Keyboard();

		for (int y = 0; y < keyboard.getLayout().length; y++) {
			for (int x = 0; x < keyboard.getLayout()[y].length; x++) {
				keyboard.set(y, x, a.get(y, x));
			}
		}

		for (int i = 0; i < 1; i++) {
			int y1 = (int) (Math.random() * 3);
			int y2 = (int) (Math.random() * 3);

			int x1 = (int) (Math.random() * keyboard.getLayout()[y1].length);
			int x2 = (int) (Math.random() * keyboard.getLayout()[y2].length);

			char temp = keyboard.get(y1, x1);
			char temp1 = keyboard.get(y2, x2);
			keyboard.set(y1, x1, temp1);
			keyboard.set(y2, x2, temp);
		}
		return keyboard;
	}

	@Override
	public List<Tuple2D<Keyboard, Keyboard>> selectCrossoverPartners(List<Keyboard> population,
			GAEnvironment<Keyboard> env) {
		int amount = population.size() / 3;
		return this.selectCrossoverPartnersSUS(population, amount, env);
	}

	@Override
	public List<Keyboard> killOff(List<Keyboard> population, int numToKill, GAEnvironment<Keyboard> env) {
		// TODO Auto-generated method stub
		return this.killOffWorst(population, numToKill, env);
	}

}
