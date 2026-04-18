package Trees.RedBlackTree;

public class RBT <T extends Comparable<T>> {
    private final Node<T> NIL = new Node<>();
    private Node<T> root = NIL;

    public RBT() {
        NIL.setLeft(NIL);
        NIL.setRight(NIL);
        NIL.setParent(NIL);
    }

    public Node<T> add(T data) {
        Node<T> node = new Node<>(data);
        node.setLeft(NIL);
        node.setRight(NIL);

        Node<T> x = this.root;
        Node<T> y = NIL;

        while (x != NIL) {
            y = x;
            if (node.getData().compareTo(x.getData()) < 0) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }

        node.setParent(y);
        if (y == NIL) {
            this.root = node;
        }
        else if (node.getData().compareTo(y.getData()) < 0) {
            y.setLeft(node);
        } else {
            y.setRight(node);
        }
        return insertFixup(node);
    }

    private Node<T> insertFixup(Node<T> z) {
        while (z.getParent().isRed()) {
            if (z.getParent() == getGrandparent(z).getLeft()) {
                Node<T> uncle = getGrandparent(z).getRight();

                if (uncle.isRed()) {
                    z.getParent().setRed(false);
                    uncle.setRed(false);
                    getGrandparent(z).setRed(true);
                    z = getGrandparent(z);
                } else {
                    if (z == z.getParent().getRight()) {
                        z = z.getParent();
                        leftRotate(z);
                    }
                    z.getParent().setRed(false);
                    getGrandparent(z).setRed(true);
                    rightRotate(getGrandparent(z));
                }
            } else {
                Node<T> uncle = getGrandparent(z).getLeft();

                if (uncle.isRed()) {
                    z.getParent().setRed(false);
                    uncle.setRed(false);
                    getGrandparent(z).setRed(true);
                    z = getGrandparent(z);
                } else {
                    if (z == z.getParent().getLeft()) {
                        z = z.getParent();
                        rightRotate(z);
                    }
                    z.getParent().setRed(false);
                    getGrandparent(z).setRed(true);
                    leftRotate(getGrandparent(z));
                }
            }
        }
        this.root.setRed(false);
        return z;
    }

    private void leftRotate(Node<T> x) {
        Node<T> y = x.getRight();
        x.setRight(y.getLeft());

        if (y.getLeft() != NIL) y.getLeft().setParent(x);
        y.setParent(x.getParent());

        if (x.getParent() == NIL) {
            this.root = y;
        }
        else if (x == x.getParent().getLeft()) {
            x.getParent().setLeft(y);
        } else {
            x.getParent().setRight(y);
        }

        y.setLeft(x);
        x.setParent(y);
    }

    private void rightRotate(Node<T> y) {
        Node<T> x = y.getLeft();
        y.setLeft(x.getRight());

        if (x.getRight() != NIL) x.getRight().setParent(y);

        x.setParent(y.getParent());

        if (y.getParent() == NIL) {
            this.root = x;
        }
        else if (y == y.getParent().getRight()) {
            y.getParent().setRight(x);
        } else {
            y.getParent().setLeft(x);
        }
        x.setRight(y);
        y.setParent(x);
    }

    private Node<T> getGrandparent(Node<T> n) {
        return n.getParent().getParent();
    }

    private Node<T> getUncle(Node<T> n) {
        Node<T> gp = getGrandparent(n);
        if (gp == NIL) return NIL;
        return (n.getParent() == gp.getLeft()) ? gp.getRight() : gp.getLeft();
    }
}