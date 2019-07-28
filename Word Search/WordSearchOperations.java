/*
 * @author MUJTABA KHAN
 * @student Number 250966314
 * @Class CS 2210 Daniel Page
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Mujtaba Khan
 */
public class WordSearchOperations {

    private String[][] letters;
    private int gridSize;
    private int maxScore;
    private HashChainDictionary dict;
    private HashChainDictionary foundWords;
    private int n;

    /**
     *
     * @param fileName
     * @param wordTextFile
     * @throws IOException
     * @throws DictionaryException
     */
    public WordSearchOperations(String fileName, String wordTextFile) throws IOException, DictionaryException {
        dict = new HashChainDictionary(74759);      //CREATES COMPLETE WORD DICTIONARY
        foundWords = new HashChainDictionary(89);   //ONLY FOUND WORD DICTIONARY

        BufferedReader wordsAndScores = new BufferedReader(new FileReader(wordTextFile));       //READS WORD AND SCORE FILE
        BufferedReader gameLayoutFile = new BufferedReader(new FileReader(fileName));           //READS GAME LAYOUT FILE

        Scanner firstFile = new Scanner(new File(wordTextFile));    
        Scanner secondFile = new Scanner(new File(fileName));

        String[] parts = wordsAndScores.readLine().split(",");          //BREAKS LINES DOWN INTO SEPERATE PARTS

        //INITILIZES WORD OBJECT DICT ARRAY
        try {
            while (parts != null && firstFile.hasNext()) {
                String part1 = parts[0];
                int part2 = Integer.parseInt(parts[1]);
                Word newWord = new Word(part1, part2);
                dict.put(newWord);
                parts = wordsAndScores.readLine().split(",");
            }
        } catch (NullPointerException e) {
        }

        gridSize = Integer.parseInt(gameLayoutFile.readLine());     //GETS GRID SIZE
        letters = new String[gridSize][gridSize];                   //INITILIZES GAME BOARD 
        String lines;

        //CREATES GAME BOARD
        for (int i = 0; i < gridSize; i++) {
            lines = gameLayoutFile.readLine();
            for (int j = 0; j < gridSize; j++) {
                letters[i][j] = Character.toString(lines.charAt(j));
            }
        }

        //GETS MAX SCORE 
        String words = gameLayoutFile.readLine();
        while (secondFile.hasNext() && words != null) {
            maxScore += dict.get(words).getValue();
            words = gameLayoutFile.readLine();
        }

        wordsAndScores.close();
        gameLayoutFile.close();
        firstFile.close();
        secondFile.close();

    }

    /**
     *
     * @return NUMBER OF WORDS FOUND SO FAR
     */
    public int getNumWordsFound() {
        return n;
    }

    /**
     *
     * @return SIZE OF GRID
     */
    public int getSize() {
        return gridSize;
    }

    /**
     *
     * @return MAX SCORE/STOPPING POINT
     */
    public int getMaxScore() {
        return maxScore;
    }

    /**
     *
     * @param i
     * @param j
     * @return LETTER AT THE iTH and jTH POSITION 
    */
    public String getLetter(int i, int j) {
        return letters[i][j];
    }

    //CHECKS TO SEE IF ANY SUBSTRINGS OF A WORD ARE IN DICTIONARY
    ArrayList<Word> checkWords(String string) {
        ArrayList<Word> confirmedWords = new ArrayList<>();
        Word newWord;
        int value;
        for (int i = 0; i <= string.length(); i++) {            //LOOPS THROUGH ALL POSSIBLE SUBSTRINGS TO FIND ONES NEEDED
            for (int j = i + 1; j <= string.length(); j++) {
                if (string.substring(i, j).length() >= 4 && string.substring(i, j).length() <= 7) {
                    if (dict.get(string.substring(i, j)) != null) {
                        value = dict.get(string.substring(i, j)).getValue();        //ADDS CORRECT WORDS TO NEW ARRAYLIST
                        newWord = new Word(string.substring(i, j), value);
                        confirmedWords.add(newWord);
                    }
                }
            }
        }
        return confirmedWords;
    }
    
    //CHECKS LINE TO FIND WORDS FROM DICTIONARY
    ArrayList<Word> findWords(String line) {
        ArrayList<Word> confirmedWords = new ArrayList<>();
        String[] wordStrings = line.split("\\s+");
        for (int i = 0; i < wordStrings.length; i++) {
            confirmedWords = checkWords(wordStrings[i]);
        }
        return confirmedWords;
    }

    //KEEPS FOUND WORDS UP TO DATE SO GAME CAN CONTINUE WORKING
    ArrayList<Word> updateWordList(ArrayList<Word> words) throws DictionaryException {
        for (int i = 0; i < words.size(); i++) {
            if (foundWords.get(words.get(i).getKey()) == null) {
                foundWords.put(words.get(i));
                n += 1;
            } else {
                words.remove(i);
            }
        }
        return words;
    }
}
