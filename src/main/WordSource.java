package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

public class WordSource {

	public static String bookStr = "";

	public static HashMap<Character, Integer> characterCount;
	public static HashMap<Character, Double> characterPercentageCount;

	public static List<char[]> orthographicNeighbors;

	static {
		WordSource.loadBook();
		WordSource.calculateBookWordStatistics();
		WordSource.findOrthographicNeighbors();
	}

	public static void loadBook() {
		Scanner scan = null;
		try {
			scan = new Scanner(new File("heartofdarkness.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (scan.hasNext()) {
			String str = scan.nextLine();
			WordSource.bookStr += str;
		}
		WordSource.bookStr = WordSource.bookStr.replaceAll(" ", "");
		WordSource.bookStr = WordSource.bookStr.toLowerCase();
		// bookStr = bookStr.substring(0, bookStr.length());

	}

	public static void calculateBookWordStatistics() {

		WordSource.characterCount = new HashMap<>();
		WordSource.characterPercentageCount = new LinkedHashMap<>();

		for (char c : WordSource.bookStr.toCharArray()) {
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
			if ((c < 'a' || c > 'z') && c != ',' && c != '.' && c != '/' && c != ';' && c != '\'') {
				continue;
			}
			if (WordSource.characterCount.containsKey(c)) {
				WordSource.characterCount.put(c, WordSource.characterCount.get(c) + 1);
			} else {
				WordSource.characterCount.put(c, 1);
			}
		}

		int totalChars = 0;
		for (int val : WordSource.characterCount.values()) {
			totalChars += val;
		}
		List<Character> chars = new ArrayList<>();
		for (char c : WordSource.characterCount.keySet()) {
			chars.add(c);
		}
		Collections.sort(chars, (o1, o2) -> {
			double dec = WordSource.characterCount.get(o2) - WordSource.characterCount.get(o1);
			return dec == 0 ? 0 : dec > 0 ? 1 : -1;
		});

		for (char c : chars) {
			WordSource.characterPercentageCount.put(c, (double) WordSource.characterCount.get(c) / totalChars);
		}

		System.out.println(WordSource.characterPercentageCount);

	}

	public static void findOrthographicNeighbors() {
		List<String> words = new ArrayList<>();
		WordSource.orthographicNeighbors = new ArrayList<>();
		try {
			Scanner scan = new Scanner(new File("wordlist.txt"));
			while (scan.hasNext()) {
				String word = scan.nextLine().toLowerCase();
				for (String pword : words) {
					if (WordSource.differsByOne(word, pword)) {
						WordSource.orthographicNeighbors.add(WordSource.differingChars(word, pword));

					}
				}
				words.add(word);
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static boolean differsByOne(String a, String b) {
		if (a.length() != b.length())
			return false;
		int numDiffer = 0;
		for (int i = 0; i < a.length(); i++) {
			if (a.charAt(i) != b.charAt(i)) {
				numDiffer++;
			}
			if (numDiffer > 1)
				return false;
		}
		if (numDiffer == 1)
			return true;
		return false;
	}

	public static char[] differingChars(String a, String b) {
		for (int i = 0; i < a.length(); i++) {
			if (a.charAt(i) != b.charAt(i))
				return new char[] { a.charAt(i), b.charAt(i) };
		}
		return null;
	}

}
