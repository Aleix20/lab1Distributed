package upf.edu;

import java.util.Arrays;
import java.util.List;

import upf.edu.filter.FileLanguageFilter;
import upf.edu.uploader.S3Uploader;

public class TwitterFilter {
	public static void main(String[] args) throws Exception { //We get the arguments from the command line

		List<String> argsList = Arrays.asList(args);
		String language = argsList.get(0);
		String outputFile = argsList.get(1);
		String bucket = argsList.get(2);
		System.out
				.println("Language: " + language + ". Output file: " + outputFile + ". Destination bucket: " + bucket);
		long startTime = System.nanoTime(); //We get the starting time of the execution process
		


		//We process all the files from Eurovision in the language specified
		for (String inputFile : argsList.subList(3, argsList.size())) {
			System.out.println("Processing: " + inputFile);
			final FileLanguageFilter filter = new FileLanguageFilter(inputFile, outputFile);
			filter.filterLanguage(language);
	
		}
		long endTime = System.nanoTime(); //We get the finish time of the execution process
		long duration = (endTime - startTime)/1000000000; //We get the total duration of the execution in ms
		System.out.println("Duration filter files: "+duration+" s");
		final S3Uploader uploader = new S3Uploader(bucket, "solutionalab1/", "default"); 
		uploader.upload(Arrays.asList(outputFile)); //We upload the file
	}
}
