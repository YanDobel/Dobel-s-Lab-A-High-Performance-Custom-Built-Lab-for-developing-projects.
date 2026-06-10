package Trees.BST.BST;

public class BST <T extends Comparable<T>> {

    private class Node {
        private Node right;
        private Node left;
        private T val;
        private int count;

        public Node(T val) {
            this.right = null;
            this.left = null;
            this.val = val;
            this.count = 1;
        }
    }

    private Node root;
    private boolean removeFlag;

    public BST() {
        this.root = null;
    }

    public void add(T val) {
        this.root = add(val, this.root);
    }

    private Node add(T val, Node current) {
        if (current == null) return new Node(val);
        int cmp = val.compareTo(current.val);
        if (cmp < 0) {
            current.left = add(val, current.left);
        } else if (cmp > 0) {
            current.right = add(val, current.right);
        }
        return current;
    }

    public boolean contains(T val) {
        Node current = this.root;

        while (current != null) {
            int cmp = val.compareTo(current.val);
            if (cmp < 0) {
                current = current.left;
            } else if (cmp > 0) {
                current = current.right;
            } else {
                return true;
            }
        }
        return false;
    }

    private T minVal(Node root) {
        if (root.left == null) {
            return root.val;
        }
        return minVal(root.left);
    }

    public boolean remove(T val) {
        if (this.root == null) throw new RuntimeException("(!) Empty Tree (!)");
        this.removeFlag = false;
        this.root = remove(val, this.root);
        return this.removeFlag;
    }

    private Node remove(T val, Node root) {
        if (root == null) return null;
        int cmp = val.compareTo(root.val);
        if (cmp < 0) {
            root.left = remove(val, root.left);
        } else if (cmp > 0) {
            root.right = remove(val, root.right);
        } else {
            this.removeFlag = true;
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;

            root.val = minVal(root.right);
            root.right = remove(root.val, root.right);
        }
        return root;
    }

    public String inOrder() {
        if (this.root == null) throw new RuntimeException("(!) Empty Tree (!)");
        StringBuilder sb = new StringBuilder();
        inOrder(root, sb);
        return sb.toString().trim();
    }

    private void inOrder(Node current, StringBuilder sb) {
        if (current == null) return;
        inOrder(current.left, sb);
        sb.append(current.val).append(" ");
        inOrder(current.right, sb);
    }

    public String postOrder() {
        if (this.root == null) throw new RuntimeException("(!) Empty Tree (!)");
        StringBuilder sb = new StringBuilder();
        postOrder(root, sb);
        return sb.toString().trim();
    }

    private void postOrder(Node current, StringBuilder sb) {
        if (current == null) return;
        postOrder(current.left, sb);
        postOrder(current.right, sb);
        sb.append(current.val).append(" ");
    }

    public String preOrder() {
        if (this.root == null) throw new RuntimeException("(!) Empty Tree (!)");
        StringBuilder sb = new StringBuilder();
        preOrder(root, sb);
        return sb.toString().trim();
    }

    private void preOrder(Node current, StringBuilder sb) {
        if (current == null) return;
        sb.append(current.val).append(" ");
        preOrder(current.left, sb);
        preOrder(current.right, sb);
    }
}