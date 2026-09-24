import java.io.*;
import java.util.*;


/*
 * Implements a text search engine for a collection of documents in the same directory.
 */

public class WordSearch {
	
	public static Map<String, Set<String>> buildMap(String dirName) {
		File dir = new File(dirName);	// create a File object for this directory
		
		// make sure it exists and is actually a directory
		if (dir.exists() == false || dir.isDirectory() == false) {
            // this tells the caller "you gave me bad input"
			throw new IllegalArgumentException(dirName + " does not exist or is not a directory");
		}
		
		File[] files = dir.listFiles();		// get the Files in the specified directory
		
		// Implement the rest of this method starting from here!
		Map<String, Set<String>> file_stufz = new HashMap<>();

		// this is for debugging, just to make sure it's reading the right files
        for (File file : files) {

			try(Scanner scan = new Scanner(file)){
				while(scan.hasNext()){
					String word = scan.next();
					word = word.toLowerCase();
					if(file_stufz.containsKey(word)){
						Set<String> existingSet = file_stufz.get(word);
						existingSet.add(file.getName());
					}
					else{
						Set<String> newSet = new HashSet<>();
						newSet.add(file.getName());
						file_stufz.put(word, newSet);
					}
				}
				
			} catch(FileNotFoundException e){
				e.printStackTrace();
			}

            System.out.println(file.getName());
        }

		return file_stufz;
		
		// return Collections.EMPTY_MAP; // change this as necessary
		
	}
	
	public static List<String> search(String[] terms, Map<String, Set<String>> map) {
		// Implement this method starting from here!

		ArrayList<String> result = new ArrayList<>();

		if (terms == null){
			return new ArrayList<>();
		}
		else if (terms.length == 0){
			return new ArrayList<>();
		}
		
		Map<String, Integer> matchCounts = new HashMap<>();

		for(String item: terms){
			item = item.toLowerCase();
			Set<String> val = map.get(item);
			if(val == null){
				continue;
			}
			else{
				for(String filename: val){
					if (matchCounts.containsKey(filename)){
						matchCounts.put(filename, matchCounts.get(filename) + 1);
					}
					else {
						matchCounts.put(filename, 1);
					}
				}

				
			}
			
		}

		result.addAll(matchCounts.keySet());
		result.sort((a, b) -> {
			int comparison = Integer.compare(matchCounts.get(b), matchCounts.get(a));
			if (comparison != 0){
				return comparison;
			}
			return a.compareTo(b);
		});
	
		return result;
		// return Collections.EMPTY_LIST; // change this as necessary
	}
	
	public static void main(String[] args) {
		Map<String, Set<String>> map = buildMap(args[0]);
		System.out.println(map); 					// for debugging purposes
		
		System.out.print("Enter a term to search for: ");
		
		try (Scanner in = new Scanner(System.in)) { // create a Scanner to read from stdin
			String input = in.nextLine();			// read the entire line that was entered
			String[] terms = input.split(" ");		// separate tokens based on a single whitespace
			List<String> list = search(terms, map);	// search for the tokens in the Map
			for (String file : list) {				// print the results
				System.out.println(file);
			}
		}
		catch (Exception e) {
			// oops! something went wrong
			e.printStackTrace();
		}
	}

}
