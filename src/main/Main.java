package main;

import machinelearning.geneticalgorithm.GAEnvironment;
import thread.Threading;
import ui.FrameWrapper;

public class Main {

	public static final KeyboardTrainer twoHandsTrainer = new KeyboardTrainer(Keyboard.TWO_HANDED_EFFORT_CHART);
	public static final KeyboardTrainer rightHandTrainer = new KeyboardTrainer(Keyboard.RIGHT_HAND_EFFORT_CHART);
	public static final KeyboardTrainer leftHandTrainer = new KeyboardTrainer(Keyboard.LEFT_HAND_EFFORT_CHART);
	public static final KeyboardTrainer twoFingerTrainer = new KeyboardTrainer(Keyboard.TWO_FINGER_EFFORT_CHART);
	public static final KeyboardTrainer oneFingerTrainer = new KeyboardTrainer(Keyboard.ONE_FINGER_EFFORT_CHART);

	public static final PhoneKeyboardTrainer phoneKeyboardTrainer = new PhoneKeyboardTrainer();

	public static final boolean SLEEP = false;

	public static void main(String[] args) throws Exception {
		KeyboardTrainer trainer = Main.twoHandsTrainer;
		GAEnvironment<Keyboard> env = new GAEnvironment<>(10, trainer);

		FrameWrapper<KeyboardPanel> frame = new FrameWrapper<>("Keyboard frame", 900, 600, true, false);
		frame.setComponent(new KeyboardPanel(Keyboard.QWERTY_KEYBOARD));
		frame.setVisible(true);

		frame.getComponent().setEfforts(trainer.getKeyEfforts());
		frame.getComponent().setGAEnvironment(env);

		env.calculateFitnesses();
		env.sortPopulation();

		double startFitness = env.getFitnesses().get(env.getPopulation().get(env.getPopulation().size() - 1));

		frame.getComponent().setStartFitness(startFitness);

		// TextFileWriter writer = new TextFileWriter("right hand fitnesses.txt");

		Keyboard dboard = Keyboard.D_KEYBOARD;
		double fit = trainer.calculateFitness(dboard, null);
		System.out.println("D: " + fit);

		int n = 0;
		while (n <= 4000000) {
			if (Main.SLEEP) {
				Threading.sleep(500);
			}

			env.runGeneration();
			env.calculateFitnesses();
			env.sortPopulation();

			Keyboard bestBoard = env.getPopulation().get(0);
			if (n % 25 == 0) {
				System.out.println("Generation: " + n);
				String str = "";
				for (Keyboard board : env.getPopulation()) {
					str += env.getFitnesses().get(board) + " ";
				}
				System.out.println(str);

				System.out.println(bestBoard);
				// writer.println(env.getFitnesses().get(bestBoard));
				// writer.flush();
			}

			frame.getComponent().setKeyboard(bestBoard);
			frame.getComponent().setCurrentGeneration(n);
			frame.getComponent().setCurrentFitness(env.getFitnesses().get(bestBoard));
			frame.repaint();

			n++;
		}
		// writer.close();

	}

}
