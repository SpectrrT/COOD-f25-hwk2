/**
 * @author [Tensae Laki]
 *
 * This class contains a method for reading from a file and creating Sentence objects
 * for a sentiment analysis program.
 */

import java.util.Set;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashSet;

public class Reader {
	/**
	 * This method reads sentences from the input file, creates a Sentence object
	 * for each, and returns a Set of the Sentences.
	 * 
	 * @param filename Name of the input file to be read
	 * @return Set containing one Sentence object per sentence in the input file; null if filename is null
	 */
	public static Set<Sentence> readFile(String filename) {
		/*
		 * Implement this method in Step 1
		 */

		if(filename == null){
			return null;
		}

		File file_ting = new File(filename);
		Set<Sentence> sentence_m = new HashSet<>();

		try(Scanner scan = new Scanner(file_ting)){
			while(scan.hasNextLine()){
				String line = scan.nextLine();
				String[] parts = line.trim().split("\\s+", 2);
				if(parts.length < 2 || parts[1].isBlank()){
					continue;
				}
				try {int number = Integer.parseInt(parts[0]); 
					if (number >= -2 && number <= 2){
						Sentence text_sentence = new Sentence(number, parts[1]);
						sentence_m.add(text_sentence);
					}
					else{
						continue;
					}
				}
				 catch(NumberFormatException e){
					continue;
				}
			}



		} catch(FileNotFoundException e){
			return null;
		}


		return sentence_m;
	}

    /**
     * Use this main() method for testing your Reader.readFile method with different inputs.
     * Note that this is _NOT_ the main() method for the whole sentiment analysis program!
     * Just use it for testing this class. It is not considered for grading.
     */
    public static void main(String[] args) {

    }
}
