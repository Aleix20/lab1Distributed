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
			//We open the files to read their content 
			FileReader fileReader = new FileReader(this.input);
			FileWriter fileWriter = new FileWriter(output, true);
			BufferedWriter buffWriter = new BufferedWriter(fileWriter);
			BufferedReader buffReader = new BufferedReader(fileReader);
			String strCurrentLine;
			//We create a counter to get the total number of tweets
			int counterTweet=0;
			while ((strCurrentLine = buffReader.readLine()) != null) {
				try {
					SimplifiedTweet tweet = SimplifiedTweet.fromJson(strCurrentLine).get();
					//We only take into account the tweets in that specified language
					if (tweet.getLanguage().equals(language)) {
						counterTweet++;
						buffWriter.append(tweet.toString());
						buffWriter.append("\n");
						//System.out.println("Tweet saved");

					}
				} catch (Exception e) {
					//System.out.println(e.getMessage());
				}

			}
			System.out.println("There are "+ counterTweet +" in that language " + language);
			buffWriter.close();
			buffReader.close();
		} catch (IOException e) {
			//System.out.println("An error occurred.");
			e.printStackTrace();
		}

	}

}
