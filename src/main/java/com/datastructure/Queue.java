package com.datastructure;

import java.util.Iterator;
import java.util.LinkedList;

public class Queue<T> implements Iterable<T> {

  private java.util.LinkedList<T> queue = new java.util.LinkedList<T>();

  public Queue() {}

  public Queue(T elm) {
    offer(elm);
  }

  public void offer(T elm) {
    queue.addLast(elm);
  }

  public T poll() {
    if (isEmpty()) {
      throw new java.util.EmptyStackException();
    }
    return queue.removeFirst();
  }

  public T peek() {
    if (isEmpty()) {
      throw new java.util.EmptyStackException();
    }
    return queue.peek();
  }

  public T peekFirst() {
    if (isEmpty()) {
      throw new java.util.EmptyStackException();
    }
    return queue.peekFirst();
  }

  public int size() {
    return queue.size();
  }

  public boolean isEmpty() {
    return queue.size() == 0;
  }

  public boolean contains(T elm) {
    for (T trav : queue) {
      if ((elm == null && trav == null) || (elm != null && elm.equals(trav))) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Iterator<T> iterator() {
    return queue.iterator();
  }
}
