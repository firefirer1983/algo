/**
 * This file contains an implementation of a Red-Black tree. A RB tree is a special type of binary
 * tree which self balances itself to keep operations logarithmic.
 *
 * <p>Great visualization tool: https://www.cs.usfca.edu/~galles/visualization/RedBlack.html
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */

/*
红黑树的特性:
1. 所有的节点不是红色就是黑色.
2. 根节点(root)和叶子节点(nil)都是黑色.
3. 红色节点的子节点必须是黑色的(no adjacent red nodes)
4. 从根到任何一个叶子路径上经过的黑色节点数目都是一样的.


红黑树的插入后的fixup:
1. 如果aunt节点为红色, 则color flip (将grand -> red, parent&aunt -> black)
2. 如果aunt节点为黑色, 则做 rotation, 同时rotation后将 parent->black, children->red
*/
package com.datastructure;

import com.datastructure.TreePrinter.PrintableNode;
import java.util.Iterator;

class RedBlackTree<T extends Comparable<T>> implements Iterable {

  class Node implements PrintableNode {

    Node parent, left, right;
    T value;
    int color = RED;

    public Node(T val, Node parent) {
      value = val;
      this.parent = parent;
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

  static int RED = 1;
  static int BLACK = 0;

  Node root;
  int nodeCount;

  public int size() {
    return nodeCount;
  }

  public boolean isEmpty() {
    return nodeCount == 0;
  }

  public RedBlackTree() {
    nodeCount = 0;
  }

  boolean contains(T val) {
    if (root == null || val == null) return false;
    Node node = root;
    while (node != null) {
      int cmp = val.compareTo(node.value);
      if (cmp < 0) node = node.left;
      else if (cmp > 0) node = node.right;
      else return true;
    }
    return false;
  }

  boolean insert(T val) {
    if (val == null) throw new IllegalArgumentException();

    if (root == null) {
      root = new Node(val, null);
      root.color = BLACK;
      nodeCount += 1;
      return true;
    }

    Node node = root;
    while (node != null) {
      int cmp = val.compareTo(node.value);

      if (cmp < 0) {
        if (node.left == null) {
          node.left = new Node(val, node);
          fixup(node.left);
          nodeCount += 1;
          return true;
        }
        node = node.left;
      } else if (cmp > 0) {
        if (node.right == null) {
          node.right = new Node(val, node);
          fixup(node.right);
          nodeCount += 1;
          return true;
        }
        node = node.right;
      } else return false;
    }
    return false;
  }

  private void fixup(Node node) {

    Node parent = node.parent;
    if (parent == null) {
      node.color = BLACK;
      root = node;
      return;
    }

    Node grandParent = parent.parent;

    if (grandParent == null) return;

    if (parent.color == BLACK || node.color == BLACK) return;

    boolean isLeftChild = (parent.left == node);
    boolean isLeftParent = (grandParent.left == parent);

    Node aunt = isLeftParent ? grandParent.right : grandParent.left;
    boolean isRedAunt = (aunt != null && aunt.color == RED);
    if (isRedAunt) {
      grandParent.color = RED;
      parent.color = BLACK;
      aunt.color = BLACK;
    } else {
      if (isLeftParent) {
        if (isLeftChild) grandParent = leftLeftCase(grandParent);
        else grandParent = leftRightCase(grandParent);
      } else {
        if (isLeftChild) grandParent = rightLeftCase(grandParent);
        else grandParent = rightRightCase(grandParent);
      }
    }

    fixup(grandParent);
  }

  private Node recolor(Node node) {
    node.color = BLACK;
    if (node.left != null) node.left.color = RED;
    if (node.right != null) node.right.color = RED;
    return node;
  }

  private Node leftLeftCase(Node node) {
    return recolor(rightRotate(node));
  }

  private Node rightRightCase(Node node) {
    return recolor(leftRotate(node));
  }

  private Node leftRightCase(Node node) {
    node.left = leftRotate(node.left);
    return recolor(rightRotate(node));
  }

  private Node rightLeftCase(Node node) {
    node.right = rightRotate(node.right);
    return recolor(leftRotate(node));
  }

  private Node rightRotate(Node parent) {

    Node grandParent = parent.parent;
    Node child = parent.left;

    parent.left = child.right;
    if (parent.left != null) parent.left.parent = parent;

    child.right = parent;
    child.parent = grandParent;
    parent.parent = child;

    updateParentChildren(grandParent, parent, child);
    return child;
  }

  private Node leftRotate(Node parent) {

    Node grandParent = parent.parent;
    Node child = parent.right;

    parent.right = child.left;
    if (parent.right != null) parent.right.parent = parent;

    child.left = parent;
    child.parent = grandParent;
    parent.parent = child;

    updateParentChildren(grandParent, parent, child);
    return child;
  }

  private void updateParentChildren(Node parent, Node oldChild, Node newChild) {
    if (parent == null) return;
    if (parent.left == oldChild) parent.left = newChild;
    else parent.right = newChild;
  }

  // Returns as iterator to traverse the tree in order.
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
}
