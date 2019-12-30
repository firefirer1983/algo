package com.datastructure;

import java.util.Iterator;
import java.util.LinkedList;

public class Stack<T> implements Iterable<T> {
  private LinkedList<T> stack = new LinkedList<T>();

  public Stack() {}

  public Stack(T elm) {
    push(elm);
  }

  public int size() {
    return stack.size();
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  public void push(T elm) {
    stack.addLast(elm);
  }

  public T pop() {
    if (isEmpty()) {
      throw new java.util.EmptyStackException();
    }
    return stack.removeLast();
  }

  public T peek() {
    if (isEmpty()) {
      throw new java.util.EmptyStackException();
    }
    return stack.peekLast();
  }

  @Override
  public Iterator<T> iterator() {
    return stack.iterator();
  }
}
