/*
 * @author MUJTABA KHAN
 * @student Number 250966314
 * @Class CS 2210 Daniel Page
 */


/**
 *
 * @author Mujtaba Khan
 */
public class Node {
    
    private Word ele;
    private Node next;
    
    /**
     *
     * @param data
     */
        public Node(Word data){
        this.ele = data; 
        next = null;
    }
    
    /**
     *
     * @param nextNode
     */
    public void setNext(Node nextNode){
        next = nextNode;
    }
    
    /**
     *
     * @return NEXT NODE
     */
    public Node getNext(){
        return next;
    }
    
    /**
     *
     * @return ELEMENT WITHIN NODE
     */
    public Word getElement(){
        return ele;
    }
    
}
