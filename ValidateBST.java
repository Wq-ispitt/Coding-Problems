package com.question.dailyLC;

public class ValidateBST {

    public static void main(String[] args) {
        TreeNode test1 = new TreeNode(5, new TreeNode(1), new TreeNode(4));
        test1.right.left = new TreeNode(3);
        test1.right.left = new TreeNode(6);
        
        TreeNode test2 = new TreeNode(2, new TreeNode(1), new TreeNode(3));
        
        ValidateBST validateBST = new ValidateBST();
        System.out.println(validateBST.isValidBST(test1));
        System.out.println(validateBST.isValidBST2(test1));
        ValidateBST validateBST2 = new ValidateBST();
        System.out.println(validateBST2.isValidBST(test2));
        System.out.println(validateBST2.isValidBST2(test2));
    }

    //I.divide & conquer, TC: O(n) n is number of nodes, we need to visit each node to check if it's in valid node value range; SC: O(h) h is height of tree, worst case skewed tree
    public boolean isValidBST(TreeNode root) {
        return divideConquer(root, null, null);
    }

    private boolean divideConquer(TreeNode root, TreeNode minNode, TreeNode maxNode) {
        //base case
        if (root == null) return true;
        if (minNode != null && minNode.val >= root.val) return false;
        if (maxNode != null && maxNode.val <= root.val) return false;
        //recurse down
        boolean isLeftValid = divideConquer(root.left, minNode, root);
        boolean isRightValid = divideConquer(root.right, root, maxNode);
        //current stack
        return isLeftValid && isRightValid;
    }

    //II.inorder recursion, left-root-right, values are sorted
    private TreeNode prev;//initially null
    public boolean isValidBST2(TreeNode root) {
        return inOrder(root);
    }

    private boolean inOrder(TreeNode root) {
        if (root == null) return true;
        //first recurse on left subtree, no need to validate right if false
        boolean isLeft = inOrder(root.left);
        if (!isLeft) return false;
        //values should be sorted, check current value with previous value
        if (prev != null && prev.val >= root.val) return false;
        prev = root;//current root is new prev, update prev
        //recurse on right subtree
        boolean isRight = inOrder(root.right);

        return isRight;
    }

     private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {
        }
        TreeNode(int val) {
            this.val = val;
        }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
