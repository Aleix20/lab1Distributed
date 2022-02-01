package upf.edu.filter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import upf.edu.filter.LanguageFilter;
import upf.edu.parser.SimplifiedTweet;

public class FileLanguageFilter implements LanguageFilter {

	private final String input;
	private final String output;

	public FileLanguageFilter(String input, String output) {
		this.input = input;
		this.output = output;
	}

	@Override
	public void filterLanguage(String language) throws Exception {

		try {

			FileReader fileReader = new FileReader(this.input);
			FileWriter fileWriter = new FileWriter(output);
			BufferedWriter buffWriter = new BufferedWriter(fileWriter);
			BufferedReader buffReader = new BufferedReader(fileReader);
			String strCurrentLine;
			while ((strCurrentLine = buffReader.readLine()) != null) {
				try {
					SimplifiedTweet tweet = SimplifiedTweet.fromJson(strCurrentLine).get();
					if (tweet.getLanguage().equals(language)) {
						buffWriter.append(tweet.toString());
						System.out.println("Tweet saved");

					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

			}

			buffWriter.close();
			buffReader.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

	}

}
