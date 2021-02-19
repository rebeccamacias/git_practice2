// ******************ERRORS********************************
// Throws UnderflowException as appropriate

import java.lang.reflect.Array;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class UnderflowException extends RuntimeException {
    /**
     * Construct this exception object.
     *
     * @param message the error message.
     */
    public UnderflowException(String message) {
        super(message);
    }
}

public class Tree<E extends Comparable<? super E>> {
    final String ENDLINE = "\n";
    private BinaryNode<E> root;  // Root of tree
    private BinaryNode<E> curr;  // Last node accessed in tree
    private String treeName;     // Name of tree

    /**
     * Create an empty tree
     *
     * @param label Name of tree
     */
    public Tree(String label) {
        treeName = label;
        root = null;
    }

    /**
     * Create BST from Array
     *
     * @param arr   List of elements to be added
     * @param label Name of  tree
     */
    public Tree(E[] arr, String label) {
        root = null;
        treeName = label;
        for (int i = 0; i < arr.length; i++) {
            bstInsert(arr[i]);
        }
    }

    /**
     * O(n^2) getLeaves()x
     * @param preorder traversal of a BST,
     * @param beg beginning index of preorder
     * @param end end index of preorder
     * @param leaves the leaf nodes
     * print the leaves (without creating the tree)
     */
    public static void getLeaves(Integer[] preorder, int beg, int end, ArrayList<Integer> leaves) {
        if (preorder.length < 2){
            for (int i = 0; i < preorder.length; i++){
                leaves.add(preorder[i]);
            }
            return;
        }
        int head = preorder[beg];
        ArrayList<Integer> less = new ArrayList<>(0);
        ArrayList<Integer> greater = new ArrayList<>(0);

        for (int i = 1; i < preorder.length; i++){
            if (preorder[i] < head){
                less.add(preorder[i]);
            }
            if (preorder[i] > head){
                greater.add(preorder[i]);
            }
        }
        Integer[] lessArray = less.toArray(new Integer[less.size()]);
        Integer[] greaterArray = greater.toArray(new Integer[greater.size()]);
        if (lessArray.length > 1){
            getLeaves(lessArray, beg, end - 1, leaves);
        } else if (lessArray.length == 1) {
            leaves.add(lessArray[0]);
        }
        if (greaterArray.length > 1){
            getLeaves(greaterArray, beg, end - 1, leaves);
        } else if(greaterArray.length == 1){
            leaves.add(greaterArray[0]);
        }
    }


    /**
     * Create Tree By Level.  Parents are set.
     * This runs in  O(n)
     * @param arr   List of elements to be added
     * @param label Name of tree
     */
    public void createTreeByLevel(E[] arr, String label) {
        treeName = label;
        if (arr.length <= 0) {
            root = null;
            return;
        }

        ArrayList<BinaryNode<E>> nodes = new ArrayList<BinaryNode<E>>();
        root = new BinaryNode<E>(arr[0]);
        nodes.add(root);
        BinaryNode<E> newr = null;
        for (int i = 1; i < arr.length; i += 2) {
            BinaryNode<E> curr = nodes.remove(0);
            BinaryNode<E> newl = new BinaryNode<E>(arr[i], null, null, curr);
            nodes.add(newl);
            newr = null;
            if (i + 1 < arr.length) {
                newr = new BinaryNode<E>(arr[i + 1], null, null, curr);
                nodes.add(newr);
            }

            curr.left = newl;
            curr.right = newr;
        }
    }

    /**
     * Change name of tree
     *
     * @param name new name of tree
     */
    public void changeName(String name) {
        this.treeName = name;
    }

    /**
     * @return a string displaying the tree contents as a tree with one node per line
     */
    public String toString() {
        if (root == null)
            return (treeName + " Empty tree\n");
        else
            return (treeName + "\n" + toString(root, " "));
    }

    /**
     * This runs in O(n)
     * Return a string displaying the tree contents as a single line
     */
    public String toString2() {
        if (root == null)
            return treeName + " Empty tree";
        else
            return treeName + toString2(root);
    }

    /**
     * Find successor of "curr" node in tree
     * O(log n) successor()x
     * @author Rebecca Zitting
     * @return String representation of the successor
     */
    public String successor() {
        if (curr == null) {curr = root;}
        if (curr == null) {return "null";}
        if (curr.right != null){ //if node has right child, the successor is in this subtree
            curr = curr.right;
            if (curr.parent == root || curr.left != null){
                curr = curr.left;
            }
            if (curr.left == null){ //if no left child, this is the successor
                return curr.element.toString();
            }
            return this.successor();
        }
        if (curr.element.compareTo(curr.parent.element) < 0){ //node was on parent's left, so parent is successor
            curr = curr.parent;
            return curr.element.toString();
        }
        while (curr.element.compareTo(curr.parent.element) > 0){ //
            curr = curr.parent;
        }
        curr = curr.parent;
        return curr.element.toString();
    }

    /**
     * Print all paths from root to leaves
     * O(n) printAllPaths()
     * Given a binary tree, print out all of its root-to-leaf paths one per line.
     */
    public String printAllPaths(String path) {
        if (curr == null){ curr = root; }
        path += (curr.element.toString() + " ");
        if (curr.left == null && curr.right == null){
            path += "\n";
            curr = curr.parent;
            return path;
        }
        curr = curr.left;
        String leftPath = this.printAllPaths(path);
        if (curr.right == null){
            curr = curr.parent;
            return leftPath;
        }
        curr = curr.right;
        String rightPath = this.printAllPaths(path);
        if (curr.left == null && curr.right == null) {
            rightPath += "\n";
            return rightPath;
        }
        curr = curr.parent;
        return leftPath + rightPath;
    }

    /**
     * Counts all non-null binary search trees embedded in tree
     * TODO: NOT DONE O(??) countBST()
     * @return Count of embedded binary search trees
     */
    public Integer countBST(int count) {
        if (root == null) { return count; }
        if (curr == null) { curr = root; }
        if (this.isBST()){
            count++;
        }

        if (curr.left != null) {
            curr = curr.left;
            int leftCount = this.countBST(count);
        }
        if (curr.right != null) {
            curr = curr.right;
            int rightCount = this.countBST(count);
        }
        //leftCount = tree.left.countBST();
        //rightCount = tree,right.countBST();
        return count;
    }
    private boolean isBST(){
        if(curr == null){ return true; }
        if(curr.right == null && curr.left == null){
            if(curr.parent == null){
                return true;
            }
            curr = curr.parent;
            return true;
        }
        curr = curr.left;
        E rightMost = this.rightMost();
        curr = curr.right;
        E leftMost = this.leftMost();
        if (curr.element.compareTo(rightMost) > 0 && curr.element.compareTo(leftMost) < 0){
            return true;
        }
        curr = curr.left;
        boolean leftBST = this.isBST();
        curr = curr.right;
        boolean rightBST = this.isBST();
        boolean isGreaterLeft = curr.element.compareTo(rightMost) < 0;
        boolean isLesserRight = curr.element.compareTo(leftMost) > 0;
        curr = curr.parent;
        return leftBST && rightBST && (isGreaterLeft && isLesserRight);
    }

    public E leftMost() {
        if (curr == null){ return null;}
        if (curr.left == null){
            curr = curr.parent;
            return curr.left.element;}
        curr = curr.left;
        E tempLeft = this.leftMost();
        curr = curr.parent;
        return tempLeft;
    }

    public E rightMost() {
        if (curr == null){ return null; }
        if (curr.right == null){
            curr = curr.parent;
            return curr.right.element; }
        curr = curr.right;
        E tempRight = this.rightMost();
        curr = curr.parent;
        return tempRight;
    }

    /**
     * Insert into a bst tree; duplicates are allowed
     *
     * @param x the item to insert.
     */
    public void bstInsert(E x) {

        root = bstInsert(x, root, null);
    }

    /**
     * Determines if item is in tree
     *
     * @param item the item to search for.
     * @return true if found.
     */
    public boolean contains(E item) {

        return bstContains(item, root);
    }

    /**
     * Remove all paths from tree that sum to less than given value
     * O(n) pruneK()
     * @param sum: minimum path sum allowed in final tree
     */
    public void pruneK(Integer sum) { //wrapper method
        BinaryNode<E> newTree = this.pruneK(sum, 0);
        newTree.toString();
    }

    private BinaryNode<E> pruneK(Integer k, Integer sum){ //helper method, returns the new tree, where we actually do the heavy lifting
        if (curr == null){curr = root;}
        if (curr == null){ return null; }
        sum += (Integer)curr.element;

        if (sum.compareTo(k) >= 0){
            if (curr.left != null){
                curr = curr.left;
                if (this.pruneK(k, sum) == null){
                    curr = curr.parent;
                    curr.left = null;
                }
            }
            return curr;
        } else if (sum.compareTo(k) < 0){
            if (curr.left != null){
                curr = curr.left;
                if (this.pruneK(k, sum) == null) {
                    curr = curr.parent;
                    curr.left = null;
                } else {
                    curr = curr.parent;
                }
            }
            if (curr.right != null){
                curr = curr.right;
                if (this.pruneK(k, sum) == null){
                    curr = curr.parent;
                    curr.right = null;
                } else {
                    curr = curr.parent;
                }
            }
            if (curr.left == null && curr.right == null) { return null; }
        }
        return curr;
    }

    /**
     * Find the least common ancestor of two nodes
     * O(n) lca()
     * @param a first node
     * @param b second node
     * @return String representation of ancestor
     */
    public String lca(E a, E b) {
        BinaryNode<E> ancestor;
        if (curr == null){ curr = root; }
        if ((a.compareTo(curr.element) < 0 && b.compareTo(curr.element) == 0) || a.compareTo(curr.element) == 0 && b.compareTo(curr.element) > 0){
            ancestor = curr;
            return ancestor.element.toString();
        }
        if (a.compareTo(curr.element) < 0 && b.compareTo(curr.element) > 0){
            ancestor = curr;
            return ancestor.element.toString();
        } else if (a.compareTo(curr.element) < 0 && b.compareTo(curr.element) < 0){
            curr = curr.left;
            String ancestorString = this.lca(a, b);
            return ancestorString;
        } else if (a.compareTo(curr.element) > 0 && b.compareTo(curr.element) > 0){
            curr = curr.right;
            String ancestorString = this.lca(a,b);
            return ancestorString;
        }
        return "none";
    }

    /**
     * Balance the tree
     */
    public void balanceTree() {
        //root = balanceTree(root);
    }

    /**
     * In a BST, keep only nodes between range
     * TODO: NOT DONE O(??) keepRange()
     * @param low lowest value
     * @param high highest value
     */
    public void keepRange(E low, E high) {
        if (curr == null){ curr = root; }
        if (curr == null){ return; }
        BinaryNode<E> newTree = this.keepRange(root, low, high);
        System.out.println(newTree.toString());
    }

    public BinaryNode<E> keepRange(BinaryNode<E> curr, E low, E high){
        if (curr == null){
            return null;
        }

        //check children
        //go to the children (recursion)
        //check root, return root

        curr = curr.left;
        if (curr.element.compareTo(low) < 0){
            BinaryNode<E> rightMost = this.keepRange(curr.right, low, high);
            //
            return rightMost;
        }
        if (curr.element.compareTo(high) > 0) {
            BinaryNode<E> leftMost = this.keepRange(curr.left, low, high);
            //
            return leftMost;
        }
        return curr; //return the root because it is in the range
    }

    /**
     * TODO: NOT DONE O(??) maxLevelSum()
     * @return for the level with maximum sum, print the sum of the nodes
     */
    public int maxLevelSum() {
        if (curr == null){ curr = root;}
        if (curr == null) { return 0; }
//        int thisLevelSum = (int)curr.element;

        curr = curr.left;
        int leftNextLevelSum = this.maxLevelSum();
        curr = curr.right;
        int rightNextLevelSum = this.maxLevelSum();
        int nextLevelSum = leftNextLevelSum + rightNextLevelSum;
//        if (nextLevelSum > thisLevelSum){
//            return nextLevelSum;
//        }
//        return thisLevelSum;
        return -999;
    }

    /**
     * TODO: NOT DONE O(??) maxPath()
     * @return the sum of the maximum path between any two leaves
     */
    public Integer maxPath() {
        return -999;
    }

    /**
     * TODO: NOT DONE O(??) isSum()
     * @return true if the tree is a Sum Tree (every non-leaf node is sum of nodes in subtree)
     */
    public boolean isSum() {
        return false;

    }

    /**
     * TODO: NOT DONE O(??) maxPathSum()
     * @return largest path value for any path between two leaves
     */
    public int maxPathSum() { //sum?
        //return maxPathSum(root);
        return 0;
    }

    //PRIVATE

    /**
     * TODO: NOT DONE O(??) createTreeTraversals()
     * @param preorderT  preorder traversal of tree
     * @param postorderT postorder traversal of tree
     * @param name       of tree
     *                   create a full tree (every node has 0 or 2 children) from its traversals.  This is not a BST.
     */

    public void createTreeTraversals(E[] preorderT, E[] postorderT, String name) {
        this.treeName = "Need to Write ";
        //
    }


    /**
     * Internal method to insert into a subtree.
     * In tree is balanced, this routine runs in O(log n)
     *
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<E> bstInsert(E x, BinaryNode<E> t, BinaryNode<E> parent) {
        if (t == null)
            return new BinaryNode<>(x, null, null, parent);

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0) {
            t.left = bstInsert(x, t.left, t);
        } else {
            t.right = bstInsert(x, t.right, t);
        }

        return t;
    }

    /**
     * Internal method to find an item in a subtree.
     * This routine runs in O(log n) as there is only one recursive call that is executed and the work
     * associated with a single call is independent of the size of the tree: a=1, b=2, k=0
     *
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     *          SIDE EFFECT: Sets local variable curr to be the node that is found
     * @return node containing the matched item.
     */
    private boolean bstContains(E x, BinaryNode<E> t) {
        curr = null;
        if (t == null)
            return false;

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            return bstContains(x, t.left);
        else if (compareResult > 0)
            return bstContains(x, t.right);
        else {
            curr = t;
            return true;    // Match
        }
    }

    /**
     * Internal method to return a string of items in the tree in order
     * This routine runs in O(log N)
     * @param root the node that is the root of the subtree
     * @return string containing keys(in order) of binary tree
     */
    private String toString2(BinaryNode<E> root) {
        if (root == null) return "";
        StringBuilder sb = new StringBuilder();
        sb.append(toString2(root.left));
        sb.append(root.element.toString() + " ");
        sb.append(toString2(root.right));
        return sb.toString();
    }

    /**
     * Internal method to return a string of items in the tree in order
     * This routine runs in O(log N)
     * toString() O(n)
     * @param rootNode the node that is the root of the subTree.
     */
    private String toString(BinaryNode<E> rootNode, String indent) {
        StringBuilder sb = new StringBuilder();
        if (rootNode == null){
            return sb.toString();
        }
        sb.append(toString(rootNode.right, indent + " "));
        sb.append(indent);
        sb.append(rootNode.toString());
        sb.append("\n");
        sb.append(toString(rootNode.left, indent + " "));
        return sb.toString();
    }

    /**
     * TODO: NOT DONE O(??) isSum() helper, counts sum of subtree
     * @return sum of SubTree
     * */
    private Integer isSum(BinaryNode<E> curr) {
        return -1;
    }

    private static class BinaryNode<AnyType> {
        AnyType element;            // The data in the node
        BinaryNode<AnyType> left;   // Left child
        BinaryNode<AnyType> right;  // Right child
        BinaryNode<AnyType> parent; // Parent node

        // Constructors
        BinaryNode(AnyType theElement) {
            this(theElement, null, null, null);
        }

        BinaryNode(AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt, BinaryNode<AnyType> pt) {
            element = theElement;
            left = lt;
            right = rt;
            parent = pt;
        }

        /**  DONE  */
        public String toString() {
            StringBuilder sb = new StringBuilder();
//            sb.append("Node:");
            sb.append(element);
            if (parent == null) {
                sb.append("[no parent]");
            } else {
                sb.append("[");
                sb.append(parent.element);
                sb.append("]");
            }

            return sb.toString();
        }
    }

    private static class BSTInfo<AnyType> {
        //construct
    }

    // Test program
    public static void main(String[] args) {

        final String ENDLINE = "\n";


        // Assignment Problem 1 x
        System.out.println("ASSIGNMENT PROBLEM 1");
        Integer[] v1 = {25, 10, 60, 55, 58, 56, 14, 63, 8, 50, 6, 9, 61};
        Tree<Integer> tree1 = new Tree<Integer>(v1, "Tree1:");
        System.out.println(tree1.toString());
        System.out.println(tree1.toString2());
        System.out.println();
//
        // Assignment Problem 2 x
        System.out.println("ASSIGNMENT PROBLEM 2");
        long seed = 436543;
        Random generator = new Random(seed);  // Don't use a seed if you want the numbers to be different each time
        int val = 60;
        final int SIZE = 8;
//
        List<Integer> v2 = new ArrayList<Integer>();
        for (int i = 0; i < SIZE * 2; i++) {
            int t = generator.nextInt(200);
            v2.add(t);
        }
        v2.add(val);
           Integer[] v = v2.toArray(new Integer[v2.size()]);
     Tree<Integer> tree2 = new Tree<Integer>(v, "Tree2:");
        System.out.println(tree2.toString());
        tree2.contains(val);  //Sets the current node inside the tree class.
        int succCount = 5;  // how many successors do you want to see?
        System.out.println("In Tree2, starting at " + val + ENDLINE);
        for (int i = 0; i < succCount; i++) {
            System.out.println("The next successor is " + tree2.successor());
        }
        System.out.println();


        // Assignment Problem 3 x
        System.out.println("ASSIGNMENT PROBLEM 3");
        System.out.println(tree1.toString());
        System.out.println("All paths from tree1");
        System.out.println(tree1.printAllPaths(""));
        System.out.println();


//        // Assignment Problem 4
        System.out.println("ASSIGNMENT PROBLEM 4");
        System.out.println("DID NOT FINISH");
        Integer[] v4 = {66, 75, -15, 3, 65, -83, 83, -10, 16, -7, 70, 200, 71, 90};
        Tree<Integer> treeA = new Tree<Integer>("TreeA");
        treeA.createTreeByLevel(v4, "TreeA");
        System.out.println(treeA.toString());
//        System.out.println("treeA Contains BST: " + treeA.countBST(0));
        System.out.println();

        Integer[] a = {21, 8, 5, 6, 7, 19, 10, 40, 43, 52, 12, 60};
        Tree<Integer> treeB = new Tree<Integer>("TreeB");
        treeB.createTreeByLevel(a, "TreeB");
        System.out.println(treeB.toString());
//        System.out.println("treeB Contains BST: " + treeB.countBST(0));
//
//         Assignment Problem 5

        System.out.println("ASSIGNMENT PROBLEM 5");
        treeA.pruneK(200);
        treeA.changeName("treeA after pruning 200");
        System.out.println(treeA.toString());
        treeB.pruneK(60);
        treeB.changeName("treeB after pruning 60");
        System.out.println(treeB.toString());
//
        // Assignment Problem 6 x

        System.out.println("ASSIGNMENT PROBLEM 6");
        System.out.println(tree1.toString());
        System.out.println("tree1 Least Common Ancestor of (56,61) " + tree1.lca(56, 61) + ENDLINE);
        System.out.println("tree1 Least Common Ancestor of (6,25) " + tree1.lca(6, 25) + ENDLINE);
        System.out.println();
//
//        // Assignment Problem 7
        System.out.println("ASSIGNMENT PROBLEM 7");
        System.out.println("DID NOT FINISH");
        Integer[] v7 = {20, 15, 10, 5, 8, 2, 100, 28, 42};
        Tree<Integer> tree7 = new Tree<>(v7, "Tree7:");

        System.out.println(tree7.toString());
        tree7.balanceTree();
        tree7.changeName("tree7 after balancing");
        System.out.println(tree7.toString());

//         Assignment Problem 8
        System.out.println("ASSIGNMENT PROBLEM 8");
        System.out.println("DID NOT FINISH");
        System.out.println(tree1.toString());
        tree1.keepRange(10, 50);
        tree1.changeName("tree1 after keeping only nodes between 10 and 50");
        System.out.println(tree1.toString());

        tree7.changeName("Tree 7");
        System.out.println(tree7.toString());
        tree7.keepRange(8, 85);
        tree7.changeName("tree7 after keeping only nodes between 8  and 85");
        System.out.println(tree7.toString());
//
        // Assignment Problem 9
        System.out.println("ASSIGNMENT PROBLEM 9");
        System.out.println("DID NOT FINISH");
        Integer[] v9 = {66, -15, -83, 3, -10, -7, 65, 16, 75, 70, 71, 83, 200, 90};
        Tree<Integer> tree4 = new Tree<Integer>(v9, "Tree4:");
        System.out.println(tree4.toString());
//        tree4.maxLevelSum();
//
        // Assignment Problem 10
        System.out.println("ASSIGNMENT PROBLEM 10");
        ArrayList<Integer> leaves = new ArrayList<Integer>();
        Integer[] preorder1 = {10, 3, 1, 7, 18, 13};

        getLeaves(preorder1, 0, preorder1.length - 1, leaves);
        System.out.print("Leaves are ");
        for (int leaf : leaves) {
            System.out.print(leaf + " ");
        }
        System.out.println();

        leaves = new ArrayList<Integer>();
        Integer[] preorder2 = {66, -15, -83, 3, -10, -7, 65, 16, 75, 70, 71, 83, 200, 90};

        getLeaves(preorder2, 0, preorder2.length - 1, leaves);
        System.out.print("Leaves are ");
        for (int leaf : leaves) {
            System.out.print(leaf + " ");
        }
        System.out.println();
        System.out.println();
//
//        // Assignment Problem 11
        System.out.println("ASSIGNMENT PROBLEM 11");
        System.out.println("DID NOT FINISH");
        Tree<Integer> treeC = new Tree<Integer>("TreeC");
        Integer[] data = {44, 9, 13, 4, 5, 6, 7};
        treeC.createTreeByLevel(data, "Sum Tree1 ?");
        if (treeC.isSum()) {
            System.out.println(treeC.toString() + " is Sum Tree");
        } else {
            System.out.println(treeC.toString() + " is NOT a Sum Tree");
        }
        Integer[] data1 = {52, 13, 13, 4, 5, 6, 7, 0, 4};
        treeC.createTreeByLevel(data1, "Sum Tree2 ?");
        if (treeC.isSum()) {
            System.out.println(treeC.toString() + " is Sum Tree");
        } else {
            System.out.println(treeC.toString() + " is NOT a Sum Tree");
        }
        Integer[] data2 = {44, 13, 13, 4, 5, 6, 7, 1, 4};
        treeC.createTreeByLevel(data2, "Sum Tree3?");
        if (treeC.isSum()) {
            System.out.println(treeC.toString() + " is Sum Tree");
        } else {
            System.out.println(treeC.toString() + " is NOT a Sum Tree");
        }
        System.out.println();
//
//        // Assignment Problem 12
        System.out.println("ASSIGNMENT PROBLEM 12");
        System.out.println("DID NOT FINISH");
        treeC.changeName("Tree12");
        System.out.println(treeC.toString() + "MaxPath=" + treeC.maxPath());


        Integer[] data12 = {1, 3, 2, 5, 6, -3, -4, 7, 8};
        treeC.createTreeByLevel(data12, "Another Tree");
        System.out.println(treeC.toString() + "MaxPath=" + treeC.maxPath());
        System.out.println();
//
//
//        // Assignment Problem 13
        System.out.println("ASSIGNMENT PROBLEM 13");
        System.out.println("DID NOT FINISH");
        Integer[] preorderT = {1, 2, 4, 5, 3, 6, 8, 9, 7};
        Integer[] postorderT = {4, 5, 2, 8, 9, 6, 7, 3, 1};
        tree1.createTreeTraversals(preorderT, postorderT, "Tree13 from preorder & postorder");
        System.out.println(tree1.toString());
        Integer[] preorderT2 = {5, 10, 25, 1, 57, 6, 15, 20, 3, 9, 2};
        Integer[] postorderT2 = {1, 57, 25, 6, 10, 20, 9, 2, 3, 15, 5};
        tree1.createTreeTraversals(preorderT2, postorderT2, "Tree from preorder & postorder");
        System.out.println(tree1.toString());
    }
}
