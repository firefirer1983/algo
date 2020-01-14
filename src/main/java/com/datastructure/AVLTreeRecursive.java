/**
 * This file contains an implementation of an AVL tree. An AVL tree is a special type of binary tree
 * which self balances itself to keep operations logarithmic.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.datastructure;

import com.datastructure.TreePrinter.PrintableNode;

import java.util.Iterator;

class AVLTreeRecursive<T extends Comparable<T>> implements Iterable {

  class Node implements PrintableNode {

    T value;
    int height, bf;
    Node left, right;

    public Node(T val) {
      value = val;
      height = 0;
      bf = 0;
    }

    @Override
    public PrintableNode getLeft() {
      return left;
    }

    @Override
    public PrintableNode getRight() {
      return right;
    }

    @Override
    public String getText() {
      return value.toString();
    }
  }

  Node root;
  private int nodeCount = 0;

  public int size() {
    return nodeCount;
  }

  public int height() {
    return root.height;
  }

  public boolean isEmpty() {
    return nodeCount == 0;
  }

  public boolean contains(T val) {
    if (val == null) return false;
    return contains(root, val);
  }

  private boolean contains(Node node, T val) {
    if (node == null) return false;
    int cmp = val.compareTo(node.value);
    if (cmp < 0) return contains(node.left, val);
    if (cmp > 0) return contains(node.right, val);
    return true;
  }

  public boolean insert(T val) {

    if (val == null) return false;

    if (contains(root, val)) return false;

    root = insert(root, val);
    nodeCount += 1;

    return true;
  }

  private Node insert(Node node, T val) {

    if (node == null) return new Node(val);

    int cmp = val.compareTo(node.value);

    if (cmp < 0) {
      node.left = insert(node.left, val);
    } else {
      node.right = insert(node.right, val);
    }

    update(node);

    return balance(node);
  }

  public boolean remove(T val) {
    if (val == null) return false;

    if (!contains(val)) return false;

    root = remove(root, val);
    nodeCount -= 1;
    return true;
  }

  private Node remove(Node node, T val) {
    int cmp = val.compareTo(node.value);
    if (cmp < 0) {
      node.left = remove(node.left, val);
    } else if (cmp > 0) {
      node.right = remove(node.right, val);
    } else {

      if (node.left == null) return node.right;
      if (node.right == null) return node.left;

      if (node.left.height > node.right.height) {
        T subtitude = findMinValue(node.right);
        node.value = subtitude;
        node.right = remove(node.right, subtitude);
      } else {
        T subtitude = findMaxValue(node.left);
        node.value = subtitude;
        node.left = remove(node.left, subtitude);
      }
    }
    update(node);
    return balance(node);
  }

  private T findMinValue(Node node) {
    while (node.left != null) node = node.left;
    return node.value;
  }

  private T findMaxValue(Node node) {
    while (node.right != null) node = node.right;
    return node.value;
  }

  private void update(Node node) {
    if (node == null) return;

    int leftHeight = node.left == null ? -1 : node.left.height;
    int rightHeight = node.right == null ? -1 : node.right.height;

    node.height = Math.max(leftHeight, rightHeight) + 1;
    node.bf = rightHeight - leftHeight;
  }

  private Node balance(Node node) {
    if (node == null) return null;

    if (node.bf == -2) {

      if (node.left.bf <= 0) {
        return rightRotation(node);
      } else {
        return leftRightCase(node);
      }

    } else if (node.bf == 2) {

      if (node.right.bf >= 0) {
        return leftRotation(node);
      } else {
        return rightLeftCase(node);
      }
    }
    return node;
  }

  private Node leftRotation(Node node) {
    Node newParent = node.right;
    node.right = newParent.left;
    newParent.left = node;
    update(node);
    update(newParent);
    return newParent;
  }

  private Node rightRotation(Node node) {
    Node newParent = node.left;
    node.left = newParent.right;
    newParent.right = node;
    update(node);
    update(newParent);
    return newParent;
  }

  private Node leftRightCase(Node node) {
    node.left = leftRotation(node.left);
    return rightRotation(node);
  }

  private Node rightLeftCase(Node node) {
    node.right = rightRotation(node.right);
    return leftRotation(node);
  }

  public java.util.Iterator<T> iterator() {

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

        while (trav != null && trav.left != null) {
          stack.push(trav.left);
          trav = trav.left;
        }

        Node node = stack.pop();

        if (node.right != null) {
          stack.push(node.right);
          trav = node.right;
        }

        return node.value;
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  @Override
  public String toString() {
    return TreePrinter.getTreeDisplay(root);
  }

  // Make sure all left child nodes are smaller in value than their parent and
  // make sure all right child nodes are greater in value than their parent.
  // (Used only for testing)
  public boolean validateBSTInvarient(Node node) {
    if (node == null) return true;
    T val = node.value;
    boolean isValid = true;
    if (node.left != null) isValid = isValid && node.left.value.compareTo(val) < 0;
    if (node.right != null) isValid = isValid && node.right.value.compareTo(val) > 0;
    return isValid && validateBSTInvarient(node.left) && validateBSTInvarient(node.right);
  }
}
