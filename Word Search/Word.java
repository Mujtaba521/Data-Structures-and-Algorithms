/*
 * @author MUJTABA KHAN
 * @student Number 250966314
 * @Class CS 2210 Daniel Page
 */


/**
 *
 * @author Mujtaba Khan
 */
public class Word {

    /**
     * @param args the command line arguments
     */
    
    private String word;
    private int score;
    
    /**
     *
     * @param word
     * @param score
     */
    public Word(String word, int score){
        this.word = word;
        this.score = score;
    }
    
    /**
     *
     * @return KEY
     */
    public String getKey(){
        return word;
    }
    
    /**
     *
     * @return INT VALUE FOR KEY
     */
    public int getValue(){
        return score;
    }
    
}
