/**
 * @author [Tensae Laki]
 *
 * This class contains the methods used for conducting a simple sentiment analysis.
 */

import java.util.*;

public class Analyzer {

	/**
	 * This method calculates the weighted average for each word in all the Sentences.
	 * This method is case-insensitive and all words should be stored in the Map using
	 * only lowercase letters.
	 * 
	 * @param sentences Set containing Sentence objects with words to score
	 * @return Map of each word to its weighted average; null if input is null
	 */
	public static Map<String, Double> calculateWordScores(Set<Sentence> sentences) {
		/*
		 * Implement this method in Step 2
		 */

			if(sentences == null){
				return null;
			}

			if(sentences.isEmpty()){
				return new HashMap<>();
			}

			Map<String, Integer> tempSums = new HashMap<>();
			Map<String, Integer> tempCount = new HashMap<>();
			Map<String, Double> result = new HashMap<>();

			for(Sentence sentence: sentences){
				if(sentence.getScore() > 2 || sentence.getScore() < -2){
					continue;
				}
				else if(sentence.getText() == null || sentence.getText().isBlank()){
					continue;
				}

				String[] parts = sentence.getText().trim().split("\\s+");
				for(String word: parts){
					if (Character.isLetter(word.charAt(0)) != true){
						continue;
					}
					String lower = word.toLowerCase();

					tempCount.put(lower, tempCount.getOrDefault(lower, 0) + 1);
					tempSums.put(lower, tempSums.getOrDefault(lower, 0) + sentence.getScore());

				}
			}

			for(String key: tempCount.keySet()){
				int sum = tempSums.get(key);
				int count = tempCount.get(key);
				result.put(key, (double) sum / count);
			}


		return result;
	}
	
	/**
	 * This method determines the sentiment of the input sentence using the average of the
	 * scores of the individual words, as stored in the Map.
	 * This method is case-insensitive and all words in the input sentence should be
	 * converted to lowercase before searching for them in the Map.
	 * 
	 * @param wordScores Map of words to their weighted averages
	 * @param sentence Text for which the method calculates the sentiment
	 * @return Weighted average scores of all words in input sentence; null if either input is null
	 */
	public static double calculateSentenceScore(Map<String, Double> wordScores, String sentence) {
		/*
		 * Implement this method in Step 3
		 */
		
		if(wordScores == null || wordScores.isEmpty() == true){
			return 0;
		}

		if(sentence == null || sentence.isBlank()){
			return 0;
		}

		double total = 0.0;
		int count = 0;
		String[] parts = sentence.trim().split("\\s+");
		for(String word : parts){
			if (Character.isLetter(word.charAt(0)) != true){
				continue;
			}
			String lower = word.toLowerCase();

			total += wordScores.getOrDefault(lower, 0.0);
			count++;
		}

		if (count == 0){
			return 0;
		}

		return total / count;
	}

    /**
     * Use this main() method for testing your calculateWordScores and
     * calculateSentenceScore methods with different inputs.
     * Note that this is _NOT_ the main() method for the whole sentiment analysis program!
     * Just use it for testing this class. It is not considered for grading.
     */
    public static void main(String[] args) {
		Set<Sentence> sentences = new HashSet<>();
    	sentences.add(new Sentence(2, "I like cake and could eat cake all day ."));
    	sentences.add(new Sentence(1, "I hope the dog does not eat my cake ."));
    	sentences.add(new Sentence(5, "bad score"));
   	 	Map<String, Double> scores = calculateWordScores(sentences);

    	System.out.println(scores);

    	if (Math.abs(scores.get("cake") - 5.0 / 3) > 0.0001) System.out.println("wrong score for cake");
    	if (scores.get("eat") != 1.5) System.out.println("wrong score for eat");
    	if (!scores.containsKey("i")) System.out.println("I wasn't lowercased");
   		if (scores.containsKey(".")) System.out.println("period wasn't skipped");
    	if (scores.containsKey("bad")) System.out.println("invalid sentence wasn't skipped");
    	if (calculateWordScores(null) != null) System.out.println("null input should return null");
    	if (!calculateWordScores(new HashSet<>()).isEmpty()) System.out.println("empty input should return empty map");

		Map<String, Double> ws = new HashMap<>();
		ws.put("dogs", 1.5);
		ws.put("are", 0.0);
		ws.put("cute", 2.0);

		if (Math.abs(calculateSentenceScore(ws, "dogs are cute") - 3.5 / 3) > 0.0001) System.out.println("wrong basic average");
		if (calculateSentenceScore(ws, "dogs are funny") != 0.5) System.out.println("unseen word should count as 0");
		if (calculateSentenceScore(ws, "dogs are ?smart") != 0.75) System.out.println("?smart should be skipped");
		if (calculateSentenceScore(ws, "DOGS") != 1.5) System.out.println("sentence wasn't lowercased");
		if (calculateSentenceScore(ws, "dogs dogs cute") != (1.5 + 1.5 + 2.0) / 3) System.out.println("repeated word should count twice");
		if (calculateSentenceScore(ws, "? !") != 0) System.out.println("all-skipped sentence should return 0");
		if (calculateSentenceScore(null, "dogs") != 0) System.out.println("null map should return 0");
		if (calculateSentenceScore(ws, null) != 0) System.out.println("null sentence should return 0");
		if (calculateSentenceScore(new HashMap<>(), "dogs") != 0) System.out.println("empty map should return 0");

   		System.out.println("done");
    }

}
