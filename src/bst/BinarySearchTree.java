package bst;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class BinarySearchTree<T> {

    private static class Node<T> {
        private final int key;
        private final T value;
        private int subtreeSize = 1;
        private Node<T> left;
        private Node<T> right;

        Node(int key, T value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node<T> root;

    public int getIndex(int key) {
        Node<T> node = root;
        int index = 0;
        while (node != null) {
            if (node.key == key) {
                if (node.left != null) {
                    index += node.left.subtreeSize;
                }
                return index;
            } else if (node.key > key) {
                node = node.left;
            } else {
                if (node.left != null) {
                    index += node.left.subtreeSize;
                } else {
                    index++;
                }
                node = node.right;
            }
        }
        return -1;
    }

    public T add(int key, T value) {
        Node<T> parent = null;
        Node<T> node = root;
        boolean leftChild = true;
        while (node != null) {
            node.subtreeSize++;
            if (node.key == key) {
                // dispose old node
                T oldValue = node.value;
                addChildToParent(parent, new Node<>(key, value), leftChild, node.left, node.right);
                // Compensate additional one.
                subtractThroughBranchUntilKey(key);
                return oldValue;
            } else if (node.key > key) {
                parent = node;
                leftChild = true;
                node = node.left;
            } else {
                parent = node;
                leftChild = false;
                node = node.right;
            }
        }
        addChildToParent(parent, new Node<>(key, value), leftChild, null, null);
        return null;
    }

    public Iterator<Integer> createIterator() {
        return new ForwardIterator();
    }

    public Iterator<Integer> createReverseIterator() {
        return new BackwardIterator();
    }

    public int getMaxDeepth() {
        return getMaxDeepth(root);
    }

    private int getMaxDeepth(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(getMaxDeepth(node.left), getMaxDeepth(node.right));
    }

    public int getMinDeepth() {
        return getMinDeepth(root);
    }

    private int getMinDeepth(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.min(getMaxDeepth(node.left), getMaxDeepth(node.right));
    }

    private class ForwardIterator implements Iterator<Integer> {

        private final Deque<Node<T>> stack;

        ForwardIterator() {
            stack = new LinkedList<>();
            addLeftBranchStartingWith(root);
        }

        private void addLeftBranchStartingWith(Node<T> node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public Integer next() {
            Node<T> nextNode = stack.poll();
            addLeftBranchStartingWith(nextNode.right);
            return nextNode.key;
        }
    }

    private class BackwardIterator implements Iterator<Integer> {

        private final Deque<Node<T>> stack;

        BackwardIterator() {
            stack = new LinkedList<>();
            addRightBranchStartingWith(root);
        }

        private void addRightBranchStartingWith(Node<T> node) {
            while (node != null) {
                stack.push(node);
                node = node.right;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public Integer next() {
            Node<T> nextNode = stack.poll();
            addRightBranchStartingWith(nextNode.left);
            return nextNode.key;
        }
    }

    private void subtractThroughBranchUntilKey(int key) {
        Node<T> node = root;
        while (node != null) {
            if (node.key == key) {
                return;
            } else if (node.key > key) {
                node.subtreeSize--;
                node = node.left;
            } else {
                node.subtreeSize--;
                node = node.right;
            }
        }
    }

    private void addChildToParent(Node<T> parent, Node<T> node, boolean isLeft, Node<T> leftChild, Node<T> rightChild) {
        node.left = leftChild;
        node.right = rightChild;
        if (parent != null) {
            if (isLeft) {
                parent.left = node;
            } else {
                parent.right = node;
            }
        } else {
            root = node;
        }
    }

}
