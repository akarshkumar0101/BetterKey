package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Keyboard {
	// y,x
	private final char[][] layout;
	{
		this.layout = new char[3][];
		this.layout[0] = new char[10];
		this.layout[1] = new char[11];
		this.layout[2] = new char[10];
	}
	private final HashMap<Character, int[]> characterLocations;

	String characters = "abcdefghijklmnopqrstuvwxyz;',./";

	public static final double[][] TWO_HANDED_EFFORT_CHART = { { 1.75, 2, 1.5, 1.25, 1.25, 1.25, 1.25, 1.5, 2, 1.75 },
			{ 1, 1, 1, 1, 1.25, 1.25, 1, 1, 1, 1, 1.75 }, { 1.75, 2, 1.5, 1.25, 1.25, 1.25, 1.25, 1.5, 2, 1.75 } };

	public static final double[][] RIGHT_HAND_EFFORT_CHART = { { 1.75, 1.5, 1.25, 1.25, 1.5, 2, 1.75, 1.75, 2, 2.25 },
			{ 1.75, 1.5, 1.25, 1, 1, 1, 1, 1.75, 2, 2.25, 2.25 },
			{ 1.75, 1.5, 1.25, 1.25, 1.5, 2, 1.75, 1.75, 2, 2.25 } };
	public static final double[][] LEFT_HAND_EFFORT_CHART = { { 2.25, 2, 1.75, 1.75, 2, 1.5, 1.25, 1.25, 1.5, 1.75 },
			{ 2.25, 2, 1.75, 1, 1, 1, 1, 1.25, 1.5, 1.75, 2.5 },
			{ 2.25, 2, 1.75, 1.75, 2, 1.5, 1.25, 1.25, 1.5, 1.75 } };

	public static final double[][] TWO_FINGER_EFFORT_CHART = {
			{ 1.75, 1.5, 1.25, 1.25, 1.25, 1.25, 1.25, 1.25, 1.5, 1.75 },
			{ 1.75, 1.5, 1.25, 1, 1.25, 1.25, 1, 1.25, 1.5, 1.75, 2 },
			{ 1.75, 1.5, 1.25, 1.25, 1.25, 1.25, 1.25, 1.25, 1.5, 1.75 } };

	public static final double[][] ONE_FINGER_EFFORT_CHART = { { 2.25, 2, 1.75, 1.5, 1.25, 1.25, 1.5, 1.75, 2, 2.25 },
			{ 2.25, 2, 1.75, 1.5, 1.25, 1, 1.25, 1.5, 1.75, 2, 2.25 },
			{ 2.25, 2, 1.75, 1.5, 1.25, 1.25, 1.5, 1.75, 2, 2.25 } };

	public static final double OTHER_EFFORT = 4;

	public static final Keyboard QWERTY_KEYBOARD = new Keyboard();
	public static final Keyboard D_KEYBOARD = new Keyboard();
	static {
		Keyboard.QWERTY_KEYBOARD.layout[0] = "qwertyuiop".toCharArray();
		Keyboard.QWERTY_KEYBOARD.layout[1] = "asdfghjkl;'".toCharArray();
		Keyboard.QWERTY_KEYBOARD.layout[2] = "zxcvbnm,./".toCharArray();
		Keyboard.QWERTY_KEYBOARD.updateCharacterLocations();
		
		Keyboard.D_KEYBOARD.layout[0] = "',.pyfgcrl".toCharArray();
		Keyboard.D_KEYBOARD.layout[1] = "aoeuidhtns/".toCharArray();
		Keyboard.D_KEYBOARD.layout[2] = ";qjkxbmwvz".toCharArray();
		Keyboard.D_KEYBOARD.updateCharacterLocations();
	}

	public Keyboard() {
		this.characterLocations = new HashMap<>();
	}

	public void randomize() {
		List<Character> chars = new ArrayList<>();
		for (char c : this.characters.toCharArray()) {
			chars.add(c);
		}
		for (int y = 0; y < this.layout.length; y++) {
			for (int x = 0; x < this.layout[y].length; x++) {
				int rand = (int) (Math.random() * chars.size());
				this.layout[y][x] = chars.remove(rand);
			}
		}
		this.updateCharacterLocations();
	}

//	public void setPerfectTwoHandedKeyboard() {
//		this.layout[0] = "k/,rdlupj'".toCharArray();
//		this.layout[1] = "etaomwinhsx".toCharArray();
//		this.layout[2] = "vzbfgcy.q;".toCharArray();
//		this.updateCharacterLocations();
//	}

	public void importKeyboard(String str) {
		int i = 0;
		for (int y = 0; y < this.layout.length; y++) {
			for (int x = 0; x < this.layout[y].length; x++) {
				this.layout[y][x] = str.charAt(i++);
			}
		}
		this.updateCharacterLocations();
	}

	public double effort(Character c, double[][] efforts) {
		c = Character.toLowerCase(c);
		if (c == '\"') {
			c = '\'';
		}
		if (c == ':') {
			c = ';';
		}
		if (c == '<') {
			c = ',';
		}
		if (c == '>') {
			c = '.';
		}
		if (c == '?') {
			c = '/';
		}
		if (this.characterLocations.containsKey(c)) {
			int[] local = this.characterLocations.get(c);
			return efforts[local[0]][local[1]];
		} else
			return Keyboard.OTHER_EFFORT;
	}

	private void updateCharacterLocations() {
		for (int y = 0; y < this.layout.length; y++) {
			for (int x = 0; x < this.layout[y].length; x++) {
				char c = this.layout[y][x];
				this.characterLocations.put(c, new int[] { y, x });
			}
		}
	}

	public void set(int y, int x, Character c) {
		this.layout[y][x] = c;
		this.updateCharacterLocations();
	}

	public Character get(int y, int x) {
		return this.layout[y][x];
	}

	public char[][] getLayout() {
		return this.layout;
	}

	public HashMap<Character, int[]> getCharacterLocations() {
		return this.characterLocations;
	}

	public boolean isSameKey(Character a, Character b) {
		if (a.equals(b) || (a + "").equalsIgnoreCase(b + ""))
			return true;
		return this.isSameKey(a, b, true);
	}

	public boolean isSameKey(Character a, Character b, boolean flip) {
		if (a == '\'' && b == '\"')
			return true;
		if (a == ':' && b == ';')
			return true;
		if (a == '/' && b == '?')
			return true;

		if (flip)
			return this.isSameKey(b, a, false);
		else
			return false;
	}

	public String getStringRepresetnation() {
		String str = "";
		for (char[] row : this.layout) {
			str += String.valueOf(row);
		}
		return str;
	}

	@Override
	public String toString() {
		String str = "";
		for (char[] row : this.layout) {
			str += String.valueOf(row) + "\n";
		}
		return str;
	}
}
