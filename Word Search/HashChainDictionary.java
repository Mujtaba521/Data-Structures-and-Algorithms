/*
 * @author MUJTABA KHAN
 * @student Number 250966314
 * @Class CS 2210 Daniel Page
 */


/**
 *
 * @author Mujtaba Khan
 */
public class HashChainDictionary implements DictionaryADT{
    
    private Node[] hashTable;       //HASH TABLE
    private int M;                  //SIZE OF HASH TABLE
    private int n;                  //ACTUAL SIZE OF TABLE
    private Node pointer;           //POINTER

    //CONSTRUCTOR FOR DICTIONARY ADT USING A LINKED LIST STRUCTURE 
    /**
     *
     * @param size
     */
        public HashChainDictionary(int size){
        size = 74759;           //SELECTED SIZE (PRIME NUMBER BETWEEN 74,000 AND 75,000)
        M = size;               
        hashTable = new Node[M];    
    }
    
    //HASH FUNCTION USED FOR HASHING A STRING 
    private int hashFunction(String inputWord){
        int hashValue = 3;                           
        char word[]; 
        word = inputWord.toCharArray();                      //BREAKS WORDS DOWN INTO INDIVIDUAL CHARACTER ARRAY 
        for(int i = 0;i<word.length;i++){
            hashValue = (hashValue * 31 + word[i]) % M;     //HASHES EACH INDIVIDUAL CHARACTER AND ACCUMULATES IT ALL      
        }
        return hashValue;       //KEY VALUE RETURNED
    }
    
    //INSERTS WORD OBJECT INTO HASH TABLE
    public int put(Word word) throws DictionaryException {
        Node newNode;                                                   //CREATES NEW NODE
        newNode = new Node(word);
        int hashValue = hashFunction(newNode.getElement().getKey());    //KEY
        pointer = hashTable[hashValue];                                 //STARTING POINT
        
        //STARTS AT THE BEGGINING AND LOOPS THROUGH LINKED LIST 
        while(pointer != null){
            if (pointer.getElement().getKey().equals(newNode.getElement().getKey())){   //IF KEY ALREADY IN HASH TABLE
                throw new DictionaryException("KEY ALREADY FOUND");                     //ERROR MESSAGE THROWN
            }
            pointer = pointer.getNext();                                                //KEEPS LOOPING THROUGH
        }
        
        pointer = hashTable[hashValue];                                                 //CHECKS FOR COLLISIONS 
        if(pointer != null){                                                            //IF THERE IS A PRE-EXISTING CHAIN
            newNode.setNext(pointer);                                                   
            hashTable[hashValue] = newNode;     
            n += 1;
            return 1;                                                                   //COLLISION
        }
        else{                                                                           //NO COLLISION
            hashTable[hashValue] = newNode;
            n += 1;
            return 0;
        }        
    }
    
    //REMOVES GIVEN WORD FROM HASH TABLE 
    public Word remove(String inputWord) throws NoKeyException {
        Node prev;                                      //SETS PREVIOUS NODE
        prev = null;                                    
        boolean wasFound;                               //VALUE TO CHECK IF KEY IN HASH TABLE
        wasFound = false;
        int hashValue = hashFunction(inputWord);        
        pointer = hashTable[hashValue];                 //STARTING VALUE 

        //LOOPS THROUGH LINKED LIST 
        while(pointer != null){
            if(pointer.getElement().getKey().equals(inputWord)){        //CHECKS IF CURRENT NODE CONTAINS WANTED DATA
                if(prev != null){                                       //CASE #1: FIRST NODE IN CHAIN
                    prev.setNext(pointer.getNext());                    
                }
                else{                                                   //CASE #2: NODE ANYWHERE ELSE BUT FIRST POSITION
                    hashTable[hashValue] = pointer.getNext();
                }
                wasFound = true;
                n -= 1;
                break;
            }
            else{                                                       //CONTINUE LOOPING THROUGH CHAIN
            prev = pointer;
            pointer = pointer.getNext();   
            }
        }
        if(wasFound == false){
            throw new NoKeyException("KEY NOT IN TABLE");               //IF NOT FOUND THROW EXCEPTION
        }
      return pointer.getElement();                                      //RETURN VALUE IF REMOVED SUCCESSFULLY
    }
    
    //RETURNS WORD OBJECT WHEN GIVEN DATA
    public Word get(String inputWord) { 
        int hashValue = hashFunction(inputWord);
        pointer = hashTable[hashValue];                             //STARTING VALUE
        
        //LOOPS THROUGH LINKED LIST
        while(pointer != null){
            if(pointer.getElement().getKey().equals(inputWord)){    //IF VALUE FOUND RETURN WORD OBJECT
                return pointer.getElement();
            }
            else{
                pointer = pointer.getNext();                        //ELSE CONTINUE LOOPING 
            }
        }
        return null;                                                //DEFAULT OUTPUT WHEN WORD NOT IN LIST
    }
    
    //ACTIVE SIZE OF HASH TABLE
    public int size() {
        return n;
    }

}
