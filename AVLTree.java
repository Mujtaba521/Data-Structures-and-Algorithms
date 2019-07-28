
import static java.lang.Integer.max;
import java.util.ArrayList;
import java.util.Stack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Mujtaba Khan
 */
public class AVLTree implements AVLTreeADT {

    private int size;   //Size variable
    private AVLTreeNode root;   //root node

    public AVLTree() {  //basic constructor 
        this.size = 0;  //initilize size 
        this.root = new AVLTreeNode();  //initilize root

    }

    public void setRoot(AVLTreeNode node) {
        this.root = node;   //changes root to node
    }

    public AVLTreeNode root() {
        return this.root;   //return root
    }

    public boolean isRoot(AVLTreeNode node) {
        return node == this.root;   //returns T if node is root
    }

    public int getSize() {
        return this.size;
    }

    public AVLTreeNode get(AVLTreeNode node, int key) { //loops through tree to see if tree contains node or returns postion it should be 

        if (node.isLeaf()) {    //if root is a leaf then it should be here
            return node;
        } else {
            if (key == node.getKey()) { //found node
                return node;
            } else if (key < node.getKey()) {   //checcks to se if should go left 
                return get(node.getLeft(), key);
            } else {                            //if not left then go right
                return get(node.getRight(), key);
            }
        }

    }

    public AVLTreeNode smallest(AVLTreeNode node) { //returns smallest node in tree

        if (node.isLeaf()) {    //if root doesnt have any children then there is no smallest
            return null;
        } else {                //if there is children it gets the farthest left child
            AVLTreeNode p = node;
            while (p.isInternal()) {
                p = p.getLeft();
            }
            return p.getParent();
        }

    }

    //inserts node into tree
    public AVLTreeNode put(AVLTreeNode node, int key, int data) throws TreeException {

        AVLTreeNode temp = get(node, key);
        if (temp.isInternal()) {    //if node was already in tree
            throw new TreeException("Key Already in Tree");
        } else {    //sets node and recomputes height
            this.size += 1;
            temp.setHeight(1);
            temp.setData(data);
            temp.setKey(key);
            temp.setLeft(new AVLTreeNode(temp));
            temp.setRight(new AVLTreeNode(temp));
            recomputeHeight(temp);
            return temp;
        }
    }

    //deltes node from tree
    public AVLTreeNode remove(AVLTreeNode node, int key) throws TreeException {

        AVLTreeNode p = get(node, key);     //find where node is in tree
        if (p == null) {    //node doesnt exist in tree
            throw new TreeException("Node Not In Tree");
        } else {
            boolean isLeft = p.getLeft().isLeaf();
            boolean isRight = p.getRight().isLeaf();    //checks left and right children of p
            AVLTreeNode c;

            if ((isLeft || isRight)) {  //if there is only 1 or 0 children
                if (!p.isRoot()) {
                    p = p.getParent();
                }
                if (isLeft) {
                    c = p.getRight();
                } else {
                    c = p.getLeft();
                }

                if (p == this.root) {
                    this.root = c;
                } else {
                    p.setRight(c);
                }
            } else {        //case 2 if there are 2 children
                this.size -= 1;
                AVLTreeNode s = smallest(p.getRight());
                p.setKey(s.getKey());
                p.setData(s.getData());
                remove(s, s.getKey());

            }
        }

        return p;
    }

    //returns inorder list of nodes (smallest to largest)
    public ArrayList<AVLTreeNode> inorder(AVLTreeNode node) {
        ArrayList<AVLTreeNode> list = new ArrayList<>();
        inorderRec(node, list);
        return list;
    }

    //helper method for inorder
    public void inorderRec(AVLTreeNode node, ArrayList<AVLTreeNode> list) {
        if (node.isInternal()) {    //only works on internal nodes
            inorderRec(node.getLeft(), list);
            list.add(node);
            inorderRec(node.getRight(), list);
        }
    }

    //recalculates height for node and parent nodes
    public void recomputeHeight(AVLTreeNode node) {
        while (node != null) {
            node.setHeight(1 + Math.max(node.getLeft().getHeight(), node.getRight().getHeight()));
            node = node.getParent();
        }
    }

    //rebalances tree if not AVL
    public void rebalanceAVL(AVLTreeNode r, AVLTreeNode v) {
        if (!v.isLeaf()) {  //if v has children recaluclates height
            recomputeHeight(v);
        }
        int heightdiff = heightDiff(v); //gets the difference in height
        while (!v.isRoot() && (heightdiff == 1 || heightdiff == 0 || heightdiff == -1)) {   //continues while v is not root and there is no height diff
            v = v.getParent();  //gets the parent of v to continue loop
            heightdiff = heightDiff(v); //recalculates height diff
            if (heightdiff > 1) {   //unbalanced tree
                if (v.getLeft().getLeft().getHeight() >= v.getLeft().getRight().getHeight()) {  //left side leaning
                    v = rightRotate(v);     //right rotate (RR)

                } else {
                    v = leftRightRotate(v); //left right rotate (LR)
                }
            } else if (heightdiff < -1) {   //unbalanced tree
                if (v.getRight().getRight().getHeight() >= v.getRight().getLeft().getHeight()) {    //right side leaning
                    v = leftRotate(v);      //left rotate (LL)

                } else {
                    v = rightLeftRoate(v);  //right left rotate (RL)
                }
            }
            recomputeHeight(v); //recalculates height 

        }
        setRoot(v); //sets root once loop is finished and entire tree is made sure to be balanced
    }

    public void putAVL(AVLTreeNode node, int key, int data) throws TreeException {  //put function for AVL tree
        put(node, key, data);   //calls regular put method
        AVLTreeNode p = get(node, key); //gets the place where p was inserted into tree
        rebalanceAVL(node, p);  //rebalances tree if nessecary

    }

    public void removeAVL(AVLTreeNode node, int key) throws TreeException { //remove function for AVL tree
        remove(node, key);  //calls regular remove method
        AVLTreeNode p = get(node, key); //gets the place where p was removed
        rebalanceAVL(node, p);  //rebalances tree if nessecary
    }

    private AVLTreeNode leftRotate(AVLTreeNode n) { //simple left rotation

        AVLTreeNode newParentNode = n.getRight();
        AVLTreeNode mid = newParentNode.getLeft();

        newParentNode.setLeft(n);
        n.setRight(mid);

        recomputeHeight(n);
        recomputeHeight(newParentNode);

        return newParentNode;
    }

    private AVLTreeNode rightRotate(AVLTreeNode n) {    //simple right rotation
        AVLTreeNode newParentNode = n.getLeft();
        AVLTreeNode mid = newParentNode.getRight();

        newParentNode.setRight(n);
        n.setLeft(mid);

        recomputeHeight(n);
        recomputeHeight(newParentNode);

        return newParentNode;
    }

    private AVLTreeNode leftRightRotate(AVLTreeNode n) {    //left right rotation
        n.setLeft(leftRotate(n.getLeft()));

        return rightRotate(n);
    }

    private AVLTreeNode rightLeftRoate(AVLTreeNode n) {     //right left rotation
        n.setRight(rightRotate(n.getRight()));
        return leftRotate(n);
    }

    private int heightDiff(AVLTreeNode n) { //calculates height difference
        if (n.isLeaf()) {
            return 0;
        }
        if (n.getLeft() == null && n.getRight() != null) {
            return 0 - n.getRight().getHeight();
        }
        if (n.getRight() == null && n.getLeft() != null) {
            return n.getLeft().getHeight() - 0;
        }
        return n.getLeft().getHeight() - n.getRight().getHeight();
    }

    public AVLTreeNode taller(AVLTreeNode node, boolean onLeft) {       //taller method
        if (node.getLeft().getHeight() > node.getRight().getHeight()) {
            return node.getLeft();
        } else if (node.getLeft().getHeight() < node.getRight().getHeight()) {
            return node.getRight();
        } else if (node.getLeft().getHeight() == node.getRight().getHeight() && onLeft) {
            return node.getLeft();
        } else {
            return node.getRight();
        }
    }
}
