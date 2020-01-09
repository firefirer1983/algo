/**
 * This file contains an implementation of a Binary Search Tree (BST) Any comparable data is allowed
 * within this tree (numbers, strings, comparable Objects, etc...). Supported operations include
 * adding, removing, height, and containment checks. Furthermore, multiple tree traversal Iterators
 * are provided including: 1) Preorder traversal 2) Inorder traversal 3) Postorder traversal 4)
 * Levelorder traversal
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.datastructure;

public class BinarySearchTree<T extends Comparable<T>> {

  private int nodeCount = 0;

  private Node root = null;

  class Node {
    T data;
    Node left, right;

    public Node(Node left, Node right, T val) {
      this.left = left;
      this.right = right;
      this.data = val;
    }
  }

  public boolean add(T val) {
    if (contains(val)) {
      return false;
    }
    root = add(root, val);
    nodeCount += 1;
    return true;
  }

  private Node add(Node n, T val) {
    if (n == null) {
      n = new Node(null, null, val);
    } else {
      if (val.compareTo(n.data) < 0) {
        n.left = add(n.left, val);
      } else {
        n.right = add(n.right, val);
      }
    }
    return n;
  }

  public boolean remove(T val) {
    if (contains(val)) {
      root = remove(root, val);
      nodeCount -= 1;
      return true;
    }
    return false;
  }

  private Node remove(Node n, T val) {
    if(n == null) {
      return null;
    }
    int cmp = val.compareTo(n.data);

    if (cmp < 0) {
      n.left = remove(n.left, val);

    } else if (cmp > 0) {
      n.right = remove(n.right, val);

    } else {

      if (n.left == null) {

        Node ret = n.right;
        n.data = null;
        n = null;
        return ret;
      } else if (n.right == null) {

        Node ret = n.left;
        n.data = null;
        n = null;
        return ret;
      } else {

        Node tmp = findMin(n.right);
        n.data = tmp.data;
        n.right = remove(n.right, tmp.data);
        return n;
      }
    }
    return n;
  }

  private Node findMin(Node n) {
    while (n.left != null) {
      n = n.left;
    }
    return n;
  }

  private Node findMax(Node n) {
    while (n.right != null) {
      n = n.right;
    }
    return n;
  }

  public int height() {
    return height(root);
  }

  private int height(Node n) {
    if (n == null) {
      return 0;
    }

    return Math.max(height(n.left), height(n.right)) + 1;
  }

  public boolean contains(T val) {
    return contains(root, val);
  }

  private boolean contains(Node n, T val) {
    if (n == null) {
      return false;
    }

    int cmp = val.compareTo(n.data);

    if (cmp < 0) {
      return contains(n.left, val);
    } else if (cmp > 0) {
      return contains(n.right, val);
    } else {
      return true;
    }
  }

  public boolean isEmpty() {
    return nodeCount == 0;
  }

  public int size() {
    return nodeCount;
  }

  // This method returns an iterator for a given TreeTraversalOrder.
  // The ways in which you can traverse the tree are in four different ways:
  // preorder, inorder, postorder and levelorder.
  public java.util.Iterator<T> traverse(TreeTraversalOrder order) {
    switch (order) {
      case PRE_ORDER:
        return preOrderTraversal();
      case IN_ORDER:
        return inOrderTraversal();
      case POST_ORDER:
        return postOrderTraversal();
      case LEVEL_ORDER:
        return levelOrderTraversal();
      default:
        return null;
    }
  }

  // Returns as iterator to traverse the tree in pre order
  private java.util.Iterator<T> preOrderTraversal() {

    final int expectedNodeCount = nodeCount;
    final java.util.Stack<Node> stack = new java.util.Stack<>();
    stack.push(root);

    return new java.util.Iterator<T>() {
      @Override
      public boolean hasNext() {
        if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
        return root != null && !stack.isEmpty();
      }

      @Override
      public T next() {
        if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
        Node node = stack.pop();
        if (node.right != null) stack.push(node.right);
        if (node.left != null) stack.push(node.left);
        return node.data;
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  // Returns as iterator to traverse the tree in order
  private java.util.Iterator<T> inOrderTraversal() {

    final int expectedNodeCount = nodeCount;
    final java.util.Stack<Node> stack = new java.util.Stack<>();
    stack.push(root);

    return new java.util.Iterator<T>() {
      Node trav = root;

      @Override
      public boolean hasNext() {
        if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
        return root != null && !stack.isEmpty();
      }

      @Override
      public T next() {

        if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();

        // Dig left
        while (trav != null && trav.left != null) {
          stack.push(trav.left);
          trav = trav.left;
        }

        Node node = stack.pop();

        // Try moving down right once
        if (node.right != null) {
          stack.push(node.right);
          trav = node.right;
        }

        return node.data;
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  // Returns as iterator to traverse the tree in post order
  private java.util.Iterator<T> postOrderTraversal() {
    final int expectedNodeCount = nodeCount;
    final java.util.Stack<Node> stack1 = new java.util.Stack<>();
    final java.util.Stack<Node> stack2 = new java.util.Stack<>();
    stack1.push(root);
    while (!stack1.isEmpty()) {
      Node node = stack1.pop();
      if (node != null) {
        stack2.push(node);
        if (node.left != null) stack1.push(node.left);
        if (node.right != null) stack1.push(node.right);
      }
    }
    return new java.util.Iterator<T>() {
      @Override
      public boolean hasNext() {
        if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
        return root != null && !stack2.isEmpty();
      }

      @Override
      public T next() {
        if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
        return stack2.pop().data;
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  // Returns as iterator to traverse the tree in level order
  private java.util.Iterator<T> levelOrderTraversal() {

    final int expectedNodeCount = nodeCount;
    final java.util.Queue<Node> queue = new java.util.LinkedList<>();
    queue.offer(root);

    return new java.util.Iterator<T>() {
      @Override
      public boolean hasNext() {
        if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
        return root != null && !queue.isEmpty();
      }

      @Override
      public T next() {
        if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
        Node node = queue.poll();
        if (node.left != null) queue.offer(node.left);
        if (node.right != null) queue.offer(node.right);
        return node.data;
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }
}
