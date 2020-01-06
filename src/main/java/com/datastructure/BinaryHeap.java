package com.datastructure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BinaryHeap<T extends Comparable<T>> {
  private int heapSize = 0;
  private int heapCapacity = 0;
  private List<T> heap = null;

  public BinaryHeap(T[] elems) {

    heapSize = heapCapacity = elems.length;

    heap = new ArrayList<T>(heapCapacity);
    for (int i = 0; i < heapSize; i++) {
      heap.add(elems[i]);
    }
    for (int i = Math.max(heapSize / 2 - 1, 0); i >= 0; i--) {
      sink(i);
    }
  }

  public BinaryHeap(int sz) {
    heap = new ArrayList<T>(sz);
  }

  public BinaryHeap() {
    this(1);
  }

  public int size() {
    return heapSize;
  }

  public void clear() {
    for (int i = 0; i < heapSize; i++) {
      heap.set(i, null);
    }
    heapSize = 0;
  }

  public T peek() {
    if (isEmpty()) {
      return null;
    }
    return heap.get(0);
  }

  public T poll() {
    return removeAt(0);
  }

  public boolean contains(T elem) {
    for (int i = 0; i < heapSize; i++) {
      if (heap.get(i).equals(elem)) {
        return true;
      }
    }
    return false;
  }

  public void add(T elem) {
    if (elem == null) throw new IllegalArgumentException();
    if (heapSize < heapCapacity) {
      heap.set(heapSize, elem);

    } else {
      heap.add(elem);
      heapCapacity += 1;
    }
    heapSize += 1;
    swim(heapSize - 1);
  }

  private boolean less(int m, int n) {
    T e1 = heap.get(m);
    T e2 = heap.get(n);

    return e1.compareTo(e2) <= 0;
  }

  private void swap(int prev, int next) {
    T e1 = heap.get(prev);
    T e2 = heap.get(next);
    heap.set(prev, e2);
    heap.set(next, e1);
  }

  private void sink(int k) {
    while (k < heapSize) {
      int left = 2 * k + 1;
      int right = 2 * k + 2;
      int smallest = left;
      if (right < heapSize && less(right, left)) {
        smallest = right;
      }
      if (smallest >= heapSize || less(k, smallest)) {
        break;
      }

      swap(k, smallest);
      k = smallest;
    }
  }

  private void swim(int k) {
    int parent = (k - 1) / 2;
    while (k > 0 && less(k, parent)) {
      swap(k, parent);
      k = parent;
      parent = (k - 1) / 2;
    }
  }

  public boolean isEmpty() {
    return heapSize == 0;
  }

  public boolean remove(T elem) {
    for (int i = 0; i < heapSize; i++) {
      if (heap.get(i).equals(elem)) {
        T rem = removeAt(i);
        return rem != null;
      }
    }
    return false;
  }

  private T removeAt(int k) {
    if (isEmpty()) return null;

    T removed = heap.get(k);
    T tail = heap.get(heapSize - 1);

    if (k == heapSize - 1) {
      heap.set(heapSize - 1, null);
      heapSize -= 1;
    } else {
      swap(k, heapSize - 1);
      heap.set(heapSize - 1, null);
      heapSize -= 1;
      sink(k);
      if (heap.get(k).equals(tail)) {
        swim(k);
      }
    }
    return removed;
  }

  public boolean isMinHeap(int k) {
    if (k >= heapSize) return true;

    int left = 2 * k + 1;
    int right = 2 * k + 2;

    if (left < heapSize && !less(k, left)) return false;
    if (right < heapSize && !less(k, right)) return false;

    return isMinHeap(left) && isMinHeap(right);
  }

  public String toString() {
    return heap.toString();
  }
}
