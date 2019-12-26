package com.datastructure;

import java.security.InvalidParameterException;

@SuppressWarnings("unchecked")
public class DynamicArray<T> implements Iterable<T> {
  private int len = 0;
  private int capacity = 0;
  private T[] ary;

  public DynamicArray(int capacity) {
    this.capacity = capacity;
    if (capacity > 0) {
      ary = (T[]) new Object[capacity];
    }
  }

  public DynamicArray() {
    this(16);
  }

  public int size() {
    return len;
  }

  public boolean isEmpty() {
    return len == 0;
  }

  public T get(int index) {
    return ary[index];
  }

  public void set(int index, T elm) {
    ary[index] = elm;
  }

  public void add(T elm) {
    if (len >= capacity) {
      if (capacity == 0) {
        capacity = 1;
      } else {
        capacity *= 2;
      }
      T[] tmp = (T[]) new Object[capacity];
      System.arraycopy(ary, 0, tmp, 0, len);
      ary = tmp;
    }
    ary[len] = elm;
    len += 1;
  }

  public int indexOf(T elm) {
    for (int i = 0; i < len; i++) {
      if (elm == null) {
        if (ary[i] == null) {
          return i;
        }
      } else if (elm.equals(ary[i])) {
        return i;
      }
    }
    return -1;
  }

  public boolean cointains(T elm) {
    return indexOf(elm) != -1;
  }

  public T removeAt(int index) {
    T ret = null;
    if (index == len - 1) {
      ret = ary[index];
    } else if (index < len - 1) {
      ret = ary[index];
      System.arraycopy(ary, index, ary, index + 1, len - index - 2);
    } else {
      throw new InvalidParameterException("Out of array range");
    }
    len -= 1;
    return ret;
  }

  public boolean remove(T elm) {

    T ret = null;
    boolean hit = false;
    for (int i = 0; i < len - 1; i++) {
      if (elm == null) {
        if (ary[i] == null) {
          ret = ary[i];
          hit = true;
        }
      } else if (ary[i].equals(elm)) {
        hit = true;
        ret = ary[i];
      }
      if (hit) {
        ary[i] = ary[i + 1];
      }
    }

    if (!hit && elm != null && elm.equals(ary[len - 1])) {
      ret = ary[len - 1];
    }

    if (hit) {
      ary[len - 1] = null;
      len -= 1;
    }
    return hit;
  }

  public java.util.Iterator<T> iterator() {
    return new java.util.Iterator<T>() {
      int index = 0;

      @Override
      public boolean hasNext() {
        return index < len;
      }

      @Override
      public T next() {
        return ary[index++];
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }
}
