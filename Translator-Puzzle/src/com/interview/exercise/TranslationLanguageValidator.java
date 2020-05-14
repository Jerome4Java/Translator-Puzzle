package com.interview.exercise;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TranslationLanguageValidator {

	static List<String> langaugeList = new ArrayList<>();
	static boolean validator = false;

	public static void main(String[] args) {
		List<String> inputData = null;
		inputData = readInputData(inputData);
		try {
			processData(inputData);
		} catch (InvalidDataException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param inputData
	 * @throws InvalidDataException 
	 * 
	 * Perform various operations such as validation, data segregation, index positioning...
	 */
	private static void processData(List<String> inputData) throws InvalidDataException {
		validateData(inputData);
		langaugeList.remove(0);
		Set<String> result = findDuplicateBySetAdd(langaugeList);
		if (!result.isEmpty()) {
			validator = true;
			result.forEach(position -> findPosition(position));
		}
		if (!validator) {
			System.out.println("Impossible");
		}
	}

	/**
	 * @param inputData
	 * @throws InvalidDataException 
	 * 
	 * validate the data to match the no of translator in the first line with following no of lines
	 * check M/2 is applicable or not in the first line. If yes then divide by 2 and print line.
	 */
	private static void validateData(List<String> inputData) throws InvalidDataException {
		String[] firstLine = inputData.get(0).split(" ");
		int following_lines_count = inputData.size() - 1;
		int translators_count = Integer.valueOf(firstLine[1]);
		if (translators_count % 2 == 0) {
			validator = true;
			System.out.println(firstLine[0] + " " + translators_count / 2);
		}
		if (translators_count == following_lines_count) {
			inputData.forEach(firstNumber -> addOnlyLanguages(firstNumber));
		} else {
			//System.out.println("Invalid Data");
			throw new InvalidDataException("Invalid Data");
		}
	}

	/**
	 * @param inputData
	 * @return List<String>
	 * 
	 * Read the input data from file and store it in the list
	 */
	private static List<String> readInputData(List<String> inputData) {
		try {
			inputData = Files.lines(Paths.get("input.txt")).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputData;
	}

	/**
	 * @param position2
	 * 
	 * It helps to find the list index position of the same occurrences of languages.
	 */
	private static void findPosition(String position2) {
		int pos1 = langaugeList.indexOf(position2);
		int pos2 = langaugeList.lastIndexOf(position2);
		System.out.println(pos1 + " " + pos2);

	}

	/**
	 * @param list
	 * @return Set<String>
	 * 
	 * Filter repeated languages and add it in the set to calculate it's index position
	 */
	public static Set<String> findDuplicateBySetAdd(List<String> list) {
		Set<String> items = new HashSet<>();
		return list.stream().filter(n -> !items.add(n)).collect(Collectors.toSet());

	}

	/**
	 * @param firstNumber
	 * 
	 * remove the elements after spaces to create the langauge specific list
	 */
	private static void addOnlyLanguages(String firstNumber) {
		firstNumber = firstNumber.substring(0, firstNumber.indexOf(" "));
		langaugeList.add(firstNumber);
	}

}
