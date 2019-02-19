package main;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JComponent;

import machinelearning.geneticalgorithm.GAEnvironment;

public class KeyboardPanel extends JComponent {

	private static final long serialVersionUID = 2937198982387724753L;

	private Keyboard keyboard;
	private int currentGeneration;
	private double currentFitness;
	private double startFitness;
	private GAEnvironment<Keyboard> env;

	private double[][] efforts;

	public KeyboardPanel(Keyboard keyboard) {
		this.setKeyboard(keyboard);
	}

	public Keyboard getKeyboard() {
		return this.keyboard;
	}

	public void setKeyboard(Keyboard keyboard) {
		this.keyboard = keyboard;
	}

	public int getCurrentGeneration() {
		return this.currentGeneration;
	}

	public void setCurrentGeneration(int generationNum) {
		this.currentGeneration = generationNum;
	}

	public double getStartFitness() {
		return this.startFitness;
	}

	public void setStartFitness(double startFitness) {
		this.startFitness = startFitness;
	}

	public double getCurrentFitness() {
		return this.currentFitness;
	}

	public void setCurrentFitness(double currentFitness) {
		this.currentFitness = currentFitness;
	}

	public double[][] getEfforts() {
		return this.efforts;
	}

	public void setEfforts(double[][] efforts) {
		this.efforts = efforts;
	}

	public GAEnvironment<Keyboard> getGAEnvironment() {
		return this.env;
	}

	public void setGAEnvironment(GAEnvironment<Keyboard> env) {
		this.env = env;
	}

	@Override
	public void paintComponent(Graphics g) {
		int FONT_SIZE = 20;
		g.setFont(new Font("default", Font.BOLD, FONT_SIZE));

		int width = this.getWidth();
		int height = this.getHeight();

		String genStr = "Generation: " + this.currentGeneration;
		int strwidth = g.getFontMetrics().stringWidth(genStr);
		g.drawString(genStr, width / 2 - strwidth / 2, 50);

		g.setFont(new Font("default", Font.PLAIN, FONT_SIZE));

		String numStr = "Number of organisms: " + this.env.getPopulation().size();
		strwidth = g.getFontMetrics().stringWidth(numStr);
		g.drawString(numStr, width / 2 - strwidth / 2, 50 + 2 * FONT_SIZE);

		String startStr = "Start fitness: " + this.startFitness;
		strwidth = g.getFontMetrics().stringWidth(startStr);
		g.drawString(startStr, width / 2 - strwidth / 2, 50 + 4 * FONT_SIZE);

		g.setFont(new Font("default", Font.BOLD, FONT_SIZE));

		String fitStr = "Best fitness: " + this.currentFitness;
		strwidth = g.getFontMetrics().stringWidth(fitStr);
		g.drawString(fitStr, width / 2 - strwidth / 2, 50 + 6 * FONT_SIZE);

		int sqrlen = width / 13;

		int centy = height / 2 - sqrlen;

		int ssx = sqrlen;
		int sy = centy - sqrlen / 2;
		for (char[] row : this.keyboard.getLayout()) {

			int sx = ssx;
			for (char c : row) {
				int[] locale = this.keyboard.getCharacterLocations().get(c);
				if (this.efforts != null && this.efforts[locale[0]][locale[1]] == 1) {
					g.drawString("*", sx + FONT_SIZE / 3, sy + FONT_SIZE);
				}

				g.drawRect(sx, sy, sqrlen, sqrlen);

				String charStr = c + "";
				// String weightStr = this.efforts[locale[0]][locale[1]] + "";

				String dispStr = charStr;
				int dispStrWidth = g.getFontMetrics().stringWidth(dispStr);

				g.drawString(dispStr, sx + sqrlen / 2 - dispStrWidth / 2, sy + sqrlen / 2 + FONT_SIZE / 2);

				sx += sqrlen;
			}
			sy += sqrlen;

			ssx += sqrlen / 2;
		}

	}

}
