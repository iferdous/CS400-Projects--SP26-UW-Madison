/** 
 * Author: Ismam Ferdous
 * Email: iferdous@wisc.edu
 * Course: CS 400
 * Assignment: P102.BSTRotation
 */

public class BSTRotation<T extends Comparable<T>> extends BinarySearchTree<T> {

    public BSTRotation() {
        super();
    }

    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a left child of the provided parent, this
     * method will perform a right rotation. When the provided child is a right
     * child of the provided parent, this method will perform a left rotation.
     *
     * @param child  the node being rotated from child to parent position
     * @param parent the node being rotated from parent to child position
     */
    protected void rotate(BinaryNode<T> child, BinaryNode<T> parent) {
        // This here wil reject null inputs 
        if (child == null || parent == null) {
            throw new IllegalArgumentException("Child and parent nodes cannot be null.");
        }

        // The given child must be directly linked as a child of the given parent
        if (child.getUp() != parent) {
            throw new IllegalArgumentException("The provided child is not a direct child of the provided parent.");
        }

        //Check if this is a right roation (child is on the left of parent) or left rotation (child is on the right of parent)  : 
        boolean childIsLeft = (parent.getLeft() == child);
        boolean childIsRight = (parent.getRight() == child);

        if (!childIsLeft && !childIsRight) {
            throw new IllegalArgumentException("The provided child is not a direct child of the provided parent.");
        }

        //We need to remember the grandparent to reattach later 
        BinaryNode<T> grandparent = parent.getUp();

        if (childIsLeft) {
            // there is a right rotation around the parent:
            // - child's right ubtree moves to be parent's left subtree
            // - parent becomes the right child of the child
            BinaryNode<T> middleSubtree = child.getRight();

            parent.setLeft(middleSubtree);
            if (middleSubtree != null)
                middleSubtree.setUp(parent);

            child.setRight(parent);
            parent.setUp(child);
        } else {
            //left rotation around the parent 
            // - childs left subtree moves to be parent's right subtree
            // - parent becomes the left child of the child 
            BinaryNode<T> middleSubtree = child.getLeft();

            parent.setRight(middleSubtree);
            if (middleSubtree != null)
                middleSubtree.setUp(parent);

            child.setLeft(parent);
            parent.setUp(child);
        }

        // Reattach rotated subtree to the grandparent, if it exists.
        child.setUp(grandparent);
        if (grandparent == null) {
            // Parent was the root, child becomes the new root.
            this.root = child;
        } else if (grandparent.getLeft() == parent) {
            // Parent was the left child of grandparent, now child takes that position.
            grandparent.setLeft(child);
        } else if (grandparent.getRight() == parent) {
            // Parent was the right child of grandparent, now child takes that position.
            grandparent.setRight(child);
        } else {
            // check : the parent was not actually attached under grand as expected.
            throw new IllegalArgumentException("parent was not linked from its grandparent");
        }
    }

    //TESTING METHODS 

    /** 
     * Test 1 : this here will perform a right rotation that involves the root node. 
     * Will return true when root, parent, and child pointers are updated correctly. 
     */
    public boolean test1() {
        // building a simple tree, child as a left child. 
        
        BinaryNode<T> parent = new BinaryNode<>((T)(Comparable)10);
        BinaryNode<T> child  = new BinaryNode<>((T)(Comparable)5);

        this.root = parent;
        parent.setLeft(child);
        child.setUp(parent);

        // rotate child up :
        rotate(child, parent);

        //checking the structure after rotation :
        return this.root == child
            && child.getUp() == null
            && child.getRight() == parent
            && child.getLeft() == null
            && parent.getUp() == child
            && parent.getLeft() == null
            && parent.getRight() == null;
    }

    /** 
     * Test 2 : Perform a left rotation that involves the root node.
     * This checks that a subtree is correctly rotated and reattached. 
     */
    public boolean test2() {
        // build a small tree where parent is left child of root and child is right of parent. 

        BinaryNode<T> rootNode = new BinaryNode<>((T)(Comparable)20);
        BinaryNode<T> parent   = new BinaryNode<>((T)(Comparable)10);
        BinaryNode<T> child    = new BinaryNode<>((T)(Comparable)15);
        BinaryNode<T> middle   = new BinaryNode<>((T)(Comparable)12);

        this.root = rootNode;
        rootNode.setLeft(parent);
        parent.setUp(rootNode);
        parent.setRight(child);
        child.setUp(parent);
        child.setLeft(middle);
        middle.setUp(child);

        // perform left rotation around parent
        rotate(child, parent);

        // check the structure after rotation :
        return this.root == rootNode
            && rootNode.getLeft() == child
            && child.getUp() == rootNode
            && child.getLeft() == parent
            && parent.getUp() == child
            && parent.getRight() == middle
            && middle.getUp() == parent;
    }

    /** 
     * Test 3 : 
     * Performing a right rotation inside the tree where both parent and child have subtrees.
     */
    public boolean test3() { 
        //build a tree where parent is left child of root and child is left child of parent. 

        BinaryNode<T> rootNode = new BinaryNode<>((T)(Comparable)30);
        BinaryNode<T> parent   = new BinaryNode<>((T)(Comparable)20);
        BinaryNode<T> child    = new BinaryNode<>((T)(Comparable)10);
        BinaryNode<T> leftLeaf = new BinaryNode<>((T)(Comparable)5);
        BinaryNode<T> rightLeaf= new BinaryNode<>((T)(Comparable)15);
        BinaryNode<T> rightSub = new BinaryNode<>((T)(Comparable)40);

        this.root = rootNode;

        rootNode.setLeft(parent);
        parent.setUp(rootNode); 

        rootNode.setRight(rightSub);
        rightSub.setUp(rootNode);

        parent.setLeft(child);
        child.setUp(parent);

        child.setLeft(leftLeaf);
        leftLeaf.setUp(child);

        child.setRight(rightLeaf);
        rightLeaf.setUp(child);

        //rotate the child above the parent : 
        rotate(child, parent);

        //check the structure after rotation :
        return this.root == rootNode
            && rootNode.getLeft() == child
            && child.getUp() == rootNode
            && child.getLeft() == leftLeaf
            && leftLeaf.getUp() == child
            && child.getRight() == parent
            && parent.getUp() == child
            && parent.getLeft() == rightLeaf
            && rightLeaf.getUp() == parent
            && rootNode.getRight() == rightSub
            && rightSub.getUp() == rootNode;
    }

    //tester output : 
    public static void main(String[] args) {
        BSTRotation<Integer> tree = new BSTRotation<>();
    
        System.out.println("test1: " + tree.test1());
        System.out.println("test2: " + tree.test2());
        System.out.println("test3: " + tree.test3());

    }
}
