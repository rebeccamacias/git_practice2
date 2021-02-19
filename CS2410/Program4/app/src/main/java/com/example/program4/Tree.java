package com.example.program4;

public class Tree {
    public TreeNode root;

    public void generate(int depth, int startX, int startY, int endX, int endY) {
        root = new TreeNode();
        root.depth = 0;
        root.line = new Line(startX, startY, endX, endY);
        generate(root, 1, depth);
    }

    private void generate(TreeNode node, int currentDepth, int maxDepth) {
        if (node == null) return;
        if (currentDepth == maxDepth) return;

        node.left = new TreeNode();
        node.right = new TreeNode();

        //right Line
        int rightAngle = (int)(Math.random() * 180);
        int xRight = node.line.getX2() + (int)(Math.cos(Math.toRadians(rightAngle)) * 100);
        int yRight = node.line.getY2() - (int)(Math.sin(Math.toRadians(rightAngle)) * 100);
        node.right.line = new Line(node.line.getX2(), node.line.getY2(), xRight, yRight); //TODO

        //left Line
        int leftAngle = (int)(Math.random() * 180);
        int xLeft = node.line.getX2() + (int)(Math.cos(Math.toRadians(leftAngle)) * 100);
        int yLeft = node.line.getY2() - (int)(Math.sin(Math.toRadians(leftAngle)) * 100);
        node.left.line = new Line(node.line.getX2(), node.line.getY2(), xLeft, yLeft);

        node.right.depth = currentDepth;
        node.left.depth = currentDepth;

        generate(node.left, currentDepth + 1, maxDepth);
        generate(node.right, currentDepth + 1, maxDepth);
    }

    public TreeNode getRoot() {
        return root;
    }

    public class TreeNode {
        int depth;
        Line line;
        TreeNode left;
        TreeNode right;

        public TreeNode getLeft(){
            return this.left;
        }

        public TreeNode getRight(){
            return this.right;
        }
    }
}
