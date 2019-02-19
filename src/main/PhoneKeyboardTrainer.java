package main;

import machinelearning.geneticalgorithm.GAEnvironment;

public class PhoneKeyboardTrainer extends KeyboardTrainer {

	public PhoneKeyboardTrainer() {
		super(null);

	}

	@Override
	public double calculateFitness(Keyboard a, GAEnvironment<Keyboard> env) {
		double fitness = 0;

		for (char[] orthowords : WordSource.orthographicNeighbors) {
			if (PhoneKeyboardTrainer.areCloseKeys(orthowords[0], orthowords[1], a)) {
				fitness -= 1;
			}
		}
		// fitness /= 100;
		return fitness;
	}

	public static boolean areCloseKeys(char a, char b, Keyboard keyboard) {
		int[] local1 = keyboard.getCharacterLocations().get(a);
		int[] local2 = keyboard.getCharacterLocations().get(b);
		double x1 = local1[0], y1 = local1[1];
		double x2 = local2[0], y2 = local2[1];
		x1 += y1 / 2;
		x2 += y2 / 2;
		double dist = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
		return dist < 1.5;
	}
}
