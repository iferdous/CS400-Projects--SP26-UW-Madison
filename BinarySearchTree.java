/** 
 * Author: Ismam Ferdous
 * Email: iferdous@wisc.edu
 * Course: CS 400
 * Assignment: P101.BinarySearchTree
 */

/** 
* This class implements a BST data structure. It provides methods for inserting, 
searching, and deleting valules while maintaining the properties of a proper BST.
* This class also impelements the SortedCollection interface.
 */

public class BinarySearchTree<T extends Comparable<T>> implements SortedCollection<T>{
    protected BinaryNode<T> root;



    // Construct an empty BST

    public BinarySearchTree() {
        this.root = null;
    }

    /**
     * Returns true when no nodes have been inserted yet.
     * @return true if the BST is empty, and if not, false.
     */
    @Override 
    public boolean isEmpty() {
        return root == null;
    }

    /** 
     * Returns the number of nodes in the BST
     * @return the number of nodes in the BST
     */

    @Override 
    public int size() {
        return sizeHelper(root);
    }

    /** 
     * Below is a helper method for size() that recursively counts the number of nodes in the BST.
     * @param node the current node we're looking at 
     * @return the number of nodes in the BST
     */

    protected int sizeHelper(BinaryNode<T> node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + sizeHelper(node.getLeft()) + sizeHelper(node.getRight());
        }
    }

    /** 
     * Inserting a new value into the BST 
     * @param data the value that should be inserted
     * @throws NullPointerException if data is null
     */
    
    @Override 
    public void insert(T data) throws NullPointerException { 
        if (data == null) { 
            throw new NullPointerException("You may not insert null value into the BST");    
        }

        BinaryNode<T> newNode = new BinaryNode<T>(data);

        if(root == null) {
            root = newNode;
        } else {
            insertHelper(newNode, root);
        }
    }

    /**
     * Performs the BST to recursively insert the newNode which has already been 
     * initialized with the data to be inserted, into the subtree.
     * when the subtree is null, then the method will do nothing. 
     * 
     * @param newNode the node to be inserted
     * @param subtree the subtree to insert the newNode into
     */
    protected void insertHelper(BinaryNode<T> newNode, BinaryNode<T> subtree) {
        if (subtree == null){ 
            return;
        }

        int comparison = newNode.getData().compareTo(subtree.getData());

        if(comparison <= 0) { 
            // inserting in the left subtree (this here handles duplicates by putting them into the left subtree)
            if(subtree.getLeft() == null) {
                subtree.setLeft(newNode);
                newNode.setUp(subtree);
            } else {
                insertHelper(newNode, subtree.getLeft());
            }
        } else { 
            //insert in the right subtree 
            if(subtree.getRight() == null) {
                subtree.setRight(newNode);
                newNode.setUp(subtree);
            } else {
                insertHelper(newNode, subtree.getRight());  
            }
        }
    }

    /**
     * Check if the whether data is in the tree.
     * @param find the value to check in the collection 
     * @return true if the value is in the collection, false otherwise
     */
    @Override 
    public boolean contains(Comparable<T> find) {
    if (find == null) {
        return false;
    }   

    BinaryNode<T> current = root;   
    while (current != null) {
        int comparison = find.compareTo(current.getData());
        if (comparison == 0) {
            return true; // value found
        } else if (comparison < 0) {
            current = current.getLeft(); // go left
        } else {
            current = current.getRight(); // go right
        }
    }
    return false; // not found
}

    //removing all elements from the BST 
    @Override
    public void clear() {
        root = null;
    }


    // ========= Testing Methods ==========: 

    /** 
     * Here we will test basic insertion operations w/ integers
     * It will create different shaped trees and checks sizes
     * 
     * @return true if all tests pass, false otherwise
     */
    public boolean test1() { 
        // create a simple tree with 3 nodes
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();

        // insert nodes
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);

        // checking that tree has 3 nodes : 
        if (tree.size() != 3) {
            System.out.println("Test 1 Failed: Expected size 3, got " + tree.size());
            return false;
        }

        //check that tree is not empty
        if (tree.isEmpty()) {
            System.out.println("Test 1 Failed: Tree should not be empty");
            return false; 
        }

        // check if the tree contains all values 
        if (!tree.contains(10) || !tree.contains(5) || !tree.contains(15)) {
            System.out.println("Test 1 Failed: Tree does not contain all inserted values");
            return false;
        }

        //test inserting a duplicate value 

        tree.insert(10); //duplicate value already useed above
        if(tree.size() != 4) {
            System.out.println("Test 1 Failed: The tree should have 4 nodes after duplicate, but has" + tree.size());
            return false;
        }

        System.out.println("Test 1 Passed!");
        return true;
    }



    /** 
     * Test 2: Testing with strings. This will create a tree with words and checks operations
     * @return true if all tests pass, false otherwise
     */

    public boolean test2() {
        //creating a tree with strings

        BinarySearchTree<String> tree = new BinarySearchTree<>();

        //insert in some words :

        tree.insert("keyboard");
        tree.insert("mouse");
        tree.insert("monitor"); 

        //checking size 

        if (tree.size() != 3) {
            System.out.println("Test 2 Failed: Expected size 3, got " + tree.size());
            return false;
        }   

        //checking if it contains 

        if (!tree.contains("keyboard") || !tree.contains("mouse") || !tree.contains("monitor")) {
            System.out.println("Test 2 Failed: Tree does not contain all inserted words");
            return false;
        }

        // checking if it has any non-existent words:

        if(tree.contains("headphones")) {
            System.out.println("Test 2 Failed: Tree should not contain 'headphones'");
            return false;
        }

        // checking clear method 
        tree.clear();
        if (!tree.isEmpty() || tree.size() != 0) {
            System.out.println("Test 2 Failed: Tree should be empty after clear");
            return false;
        }

        System.out.println("Test 2 Passed!");
        return true;
    }

    /** 
     * Test 3 : Testing edge cases, tests clear method and empty tree operations.
     * @return true if all tests pass, false otherwise
     */

    public boolean test3() {

        //test the clear and edge cases : 
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();

        //test the empty tree operations:
        if (!tree.isEmpty() || tree.size() != 0) {
            System.out.println("Test 3 Failed: New tree should be empty");
            return false;
        }

        // adding some numbers : 
        tree.insert(20);
        tree.insert(10);
        tree.insert(30);
        tree.insert(50);
        tree.insert(125);
        tree.insert(175);
        tree.insert(90);

        //tree should have 7 nodes now

        if (tree.size() != 7) {
            System.out.println("Test 3 Failed: Expected size 7, got " + tree.size());
            return false;
        }

        //checking values @ different positions 
        if (!tree.contains(20) || !tree.contains(10) || !tree.contains(30) || 
            !tree.contains(50) || !tree.contains(125) || !tree.contains(175) || 
            !tree.contains(90)) {
            System.out.println("Test 3 Failed: Tree does not contain all inserted values");
            return false;
        }

        // now clear the tree

        tree.clear();

        // tree should be empty now
        if (!tree.isEmpty() || tree.size() != 0) {
            System.out.println("Test 3 Failed: Tree should be empty after clear");
            return false;
        }

        //try to see if tree has any leftover values:

        if(tree.contains(20) || tree.contains(10) || tree.contains(30)) {
            System.out.println("Test 3 Failed: Tree should not contain any values after clear");
            return false;
        }

        System.out.println("Test 3 Passed!");
        return true;
    }

    /** 
     * Test 4: Tests null handling and simple operations.
     * @return true if all tests pass, false otherwise
     */

    public boolean test4() {
        //test 4 : testing the null handling:

        BinarySearchTree<Integer> tree = new BinarySearchTree<>();

        //Testing inserting null value:

        try {
            tree.insert(null);
            System.out.println("Test 4 Failed: Inserting null should throw NullPointerException");
            return false;
        } catch (NullPointerException e) {
            // expected behavior
        }


        //test contains with with null(should return false) :
        if (tree.contains(null)) {
            System.out.println("Test 4 Failed: contains(null) should return false");
            return false;
        }   

        //test adding more values: 
        tree.insert(40);
        tree.insert(20);        
        tree.insert(68);   
        tree.insert(24);   

        if(tree.size() != 4) {
            System.out.println("Test 4 Failed: Expected size 4, got " + tree.size());
            return false;
        }

        System.out.println("Test 4 Passed!");
        return true;

    }

    /* 
    Running all the tests with output:
    * @param args command line arguments (not used) 
     */

    public static void main(String[] args) {

        System.out.println("Running BinarySearchTree Tests...");

        //creating a new tree to run tests on:
        BinarySearchTree<Integer> bstTest = new BinarySearchTree<>();

        //run each test and keep track of overall pass/fail status: 

        boolean allTestsPassed = true;

        //test 1 : 
        System.out.println("Test 1: Basic Insertion and Size Check with Integers");
        boolean test1Result = bstTest.test1();
        if(!test1Result) {
            allTestsPassed = false;
        }

        //test 2 :
        System.out.println("Test 2: Insertion and Contains Check with Strings");
        boolean test2Result = bstTest.test2();   
        if(!test2Result) {
            allTestsPassed = false;
        }   

        //test 3 :
        System.out.println("Test 3: Edge Cases and Clear Method");  
        boolean test3Result = bstTest.test3();
        if(!test3Result) {  
            allTestsPassed = false;
        }   

        //test 4 :
        System.out.println("Test 4: Null Handling and Simple Operations");  
        boolean test4Result = bstTest.test4();
        if(!test4Result) {
            allTestsPassed = false;
        }

        //final result
        if(allTestsPassed) {
            System.out.println("All Tests Passed!");
        } else {
            System.out.println("Some Tests Failed. Please check the output above for details.");    
        }
    }
}