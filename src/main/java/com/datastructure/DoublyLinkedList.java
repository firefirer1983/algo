/**
 * A doubly linked list implementation.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.datastructure;

public class DoublyLinkedList<T> implements Iterable<T> {
  private int size;
  private Node<T> head;
  private Node<T> tail;

  public DoublyLinkedList() {
    this.size = 0;
    this.head = null;
    this.tail = null;
  }

  private static class Node<T> {
    private Node<T> prev;
    private Node<T> next;
    private T data;

    public Node(Node<T> prev, Node<T> next, T data) {
      this.prev = prev;
      this.next = next;
      this.data = data;
    }

    @Override
    public String toString() {
      if (data == null) {
        return "null";
      } else {
        return data.toString();
      }
    }
  }

  // Empty this linked list, O(n)
  public void clear() {
    if (!this.isEmpty()) {
      while (head.next != null) {
        head.data = null;
        head = head.next;
        head.prev.next = null;
        head.prev = null;
      }
      size = 0;
    }
  }

  // Return the size of this linked list
  public int size() {
    return size;
  }

  // Is this linked list empty?
  public boolean isEmpty() {
    return size == 0;
  }

  // Add an element to the tail of the linked list, O(1)
  public void add(T elem) {
    this.addLast(elem);
  }

  // Add a node to the tail of the linked list, O(1)
  public void addLast(T elem) {
    if (isEmpty()) {
      tail = new Node<T>(null, null, elem);
      head = tail;
    } else {
      Node<T> node = new Node<T>(tail, null, elem);
      tail.next = node;
      tail = node;
    }
    size += 1;
  }

  // Add an element to the beginning of this linked list, O(1)
  public void addFirst(T elem) {
    if (isEmpty()) {
      head = new Node<T>(null, null, elem);
      tail = head;
    } else {
      Node<T> node = new Node<T>(null, head, elem);
      head.prev = node;
      head = node;
    }
    size += 1;
  }

  // Add an element at a specified index
  public void addAt(int index, T data) throws Exception {
    if (index > 0 && index > size) {
      throw new RuntimeException("index out of range");
    }

    if (index == 0) {
      addFirst(data);
    } else if (index == size) {
      addLast(data);
    } else {
      Node<T> trav = head;
      for (int i = 0; i != index; i++) {
        trav = trav.next;
      }

      Node<T> node = new Node<T>(trav.prev, trav, data);
      node.prev.next = node;
      trav.prev = node;
      size += 1;
    }
  }

  // Check the value of the first node if it exists, O(1)
  public T peekFirst() {
    if (isEmpty()) throw new RuntimeException("Empty list");
    return head.data;
  }

  // Check the value of the last node if it exists, O(1)
  public T peekLast() {
    if (isEmpty()) throw new RuntimeException("Empty list");
    return tail.data;
  }

  // Remove the first value at the head of the linked list, O(1)
  public T removeFirst() {
    if (isEmpty()) throw new RuntimeException("Empty list");
    T data = head.data;
    head.data = null;
    //    当当前节点是唯一节点的时候,就不需操作 prev 和 next, 只需直接给 data = null 即可.
    if (head != tail) {
      head = head.next;
      head.prev.next = null;
      head.prev = null;
    }
    size -= 1;
    return data;
  }

  // Remove the last value at the tail of the linked list, O(1)
  public T removeLast() {
    if (isEmpty()) {
      throw new RuntimeException("Empty list");
    }
    T data = tail.data;
    if (head != tail) {
      tail = tail.prev;
      tail.next.prev = null;
      tail.next = null;
    }
    size -= 1;
    return data;
  }

  // Remove an arbitrary node from the linked list, O(1)
  private T remove(Node<T> node) {
    T data = node.data;
    node.data = null;
    if (head != tail) {
      node.prev.next = node.next;
      node.next.prev = node.prev;
    }
    size -= 1;
    return data;
  }

  // Remove a node at a particular index, O(n)
  public T removeAt(int index) {
    if (isEmpty()) {
      throw new RuntimeException("empty list");
    }
    if (index == 0) {
      return removeFirst();
    } else if (index == size - 1) {
      return removeLast();
    } else {
      Node<T> trav = head;
      for (int i = 0; i != index; i++) {
        trav = trav.next;
      }
      T data = trav.data;
      trav.data = null;
      trav.prev.next = trav.next;
      trav.next.prev = trav.prev;
      size -= 1;
      return data;
    }
  }

  // Remove a particular value in the linked list, O(n)
  public boolean remove(Object obj) {
    Node<T> trav = head;
    while (trav != null) {
      if ((trav.data == null && obj == null) || (trav.data != null && trav.data.equals(obj))) {
        if (trav == head) {
          removeFirst();
        } else if (trav == tail) {
          removeLast();
        } else {
          trav.prev.next = trav.next;
          trav.next.prev = trav.prev;
          size -= 1;
        }
        return true;
      }
      trav = trav.next;
    }
    return false;
  }

  // Find the index of a particular value in the linked list, O(n)
  public int indexOf(Object obj) {
    Node<T> trav = head;
    for (int i = 0; i < size; i++) {
      if ((trav.data == null && obj == null) || (trav.data != null && trav.data.equals(obj))) {
        return i;
      }
      trav = trav.next;
    }
    return -1;
  }

  // Check is a value is contained within the linked list
  public boolean contains(Object obj) {
    return indexOf(obj) != -1;
  }

  @Override
  public java.util.Iterator<T> iterator() {
    return new java.util.Iterator<T>() {
      Node<T> trav = head;

      @Override
      public boolean hasNext() {
        return trav != null;
      }

      @Override
      public T next() {
        T data = trav.data;
        trav = trav.next;
        return data;
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    Node<T> trav = head;
    sb.append("[");
    while (trav != null) {
      sb.append((trav.data));
      trav = trav.next;
      if (trav != null) {
        sb.append(", ");
      }
    }
    sb.append("]");
    return sb.toString();
  }
}
