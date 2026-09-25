/**
 * @author [Tensae Laki]
 *
 * This class holds the main() method for the sentiment analysis program.
 */

import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        // implement this method in Step 4

        if(args.length == 0){
            System.out.println("no input file");
            return;
        }

        Set<Sentence> file_out =  Reader.readFile(args[0]);
        if(file_out == null){
            System.out.println("bad input file");
            return;
        }

        Map<String, Double> wordScored = Analyzer.calculateWordScores(file_out);

        Scanner scan = new Scanner(System.in);
        while(true){
            System.out.println("Enter a sentence: ");
            String input = scan.nextLine();
            if(input.equals("quit")){
                break;
            }else{
                System.out.println(Analyzer.calculateSentenceScore(wordScored, input));
            }
        }
        
    }
}
