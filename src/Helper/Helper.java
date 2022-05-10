package Helper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import Utilities.SortedList;

public class Helper {
    Map<String,Integer> letterScores = new HashMap<>();
    List<Word> unscoredWords = new ArrayList<>();
    static SortedList scoredWords = new SortedList();
    static int turn = 0;
    static GUI g;

    String alphabet = "abcdefghijklmnopqrstuvwxyz";

    Helper() throws FileNotFoundException {
        readWords();
        setLetterScores();
        StartWordScore();
        g = new GUI();

    }

    void setLetterScores(){
        for(int i = 0;i<26;i++){
            letterScores.put(String.valueOf(alphabet.charAt(i)),0);
        }
        for(Word w:unscoredWords){
            String chars = "";
            for(int i=0;i<5;i++){
                if(!Contains(w.word.charAt(i),chars)){
                    chars = chars + w.word.charAt(i);
                    letterScores.put(String.valueOf(w.word.charAt(i)),letterScores.get(String.valueOf(w.word.charAt(i)))+1);
                }

            }
        }
    }

    static void submit(Character[] letters, int[] states){
        for(int i = 0;i<5;i++){
            switch (states[i]) {
                case 1 -> omitIncorrectPosition(letters[i], i);
                case 2 -> omitByCorrect(letters[i], i);
                case 3 -> omitCompletely(letters[i]);
            }
        }

        g.wp.setBestWords();
        turn++;


    }

    static String[] top5(){
        String[] top = new String[5];

        for(int i = 0;i<5;i++){
            top[i] = scoredWords.get(i).word;
        }

        return top;
    }

    static void omitCompletely(Character c){
        scoredWords.list.removeIf(w -> Contains(c, w.word));
    }

    static void omitIncorrectPosition(Character c, int index){
        scoredWords.list.removeIf(word -> correctPosition(c,index, word.word));
    }

    static boolean correctPosition(Character c, int index, String word) {
        return word.charAt(index) == c;
    }

    static void omitByCorrect(Character c, int index){
        scoredWords.list.removeIf(word -> !correctPosition(c,index, word.word));
    }

    static boolean Contains(char c, String word){
        for(int i = 0; i<word.length();i++){
            if(word.charAt(i) == c){
                return true;
            }
        }

        return false;
    }

    void readWords() throws FileNotFoundException {
        try (BufferedReader br = new BufferedReader(new FileReader("5words.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                Word newWord = new Word(line);
                unscoredWords.add(newWord);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void StartWordScore(){
        for(Word w: unscoredWords){
            String word = w.word;
            String chars = "";
            for(int i = 0;i<word.length(); i++){
                if(!Contains(word.charAt(i),chars)){
                    chars = chars + word.charAt(i);
                    w.wordScore+=letterScores.get(String.valueOf(word.charAt(i)));
                }
            }
            scoredWords.add(w);
        }
    }

    public static boolean isAlphabetical(char c){
        String alphalower = "abcdefghijklmnopqrstuvwxyz";

        for(int i = 0;i<alphalower.length();i++){
            if(alphalower.charAt(i) == c){
                return true;
            }
        }

        return false;
    }

    public class Word implements Comparable<Word> {
        public String word;
        int wordScore;

        Word(String word){
            this.word = word;
            this.wordScore = 0;
        }

        @Override
        public int compareTo(Word o) {
            return Integer.compare(o.wordScore, this.wordScore);
        }
    }
}
