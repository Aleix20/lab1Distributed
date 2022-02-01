package upf.edu;

import java.util.Arrays;
import java.util.List;

import upf.edu.filter.FileLanguageFilter;
import upf.edu.uploader.S3Uploader;

public class TwitterFilter {
	public static void main(String[] args) throws Exception {

//		List<String> argsList = Arrays.asList(args);
//		String language = argsList.get(0);
//		String outputFile = "outputLab1.json";
//		String bucket = argsList.get(2);
//		System.out
//				.println("Language: " + language + ". Output file: " + outputFile + ". Destination bucket: " + bucket);
//		for (String inputFile : argsList.subList(3, argsList.size())) {
//			System.out.println("Processing: " + inputFile);
//			final FileLanguageFilter filter = new FileLanguageFilter(inputFile, outputFile);
//			filter.filterLanguage(language);
		
//		}

		final S3Uploader uploader = new S3Uploader("pg.cm.am.lsds", "prefix", "default");
		uploader.upload(Arrays.asList("output.txt"));
	}
}
