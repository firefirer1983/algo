package com.datastructure;

public class MergeSort {

  public void sort(int[] ary) {
    // 总共有 n-1个pass
    int[] sorted = _sort(ary, 0, ary.length);
    for (int i = 0; i < ary.length; i++) {
      ary[i] = sorted[i];
    }
  }

  private int[] _sort(int[] ary, int begin, int end) {

    if (begin == end - 1) return new int[] {ary[begin]};

    int mid = begin + ((end - begin) / 2);
    int[] a = _sort(ary, begin, mid);
    int[] b = _sort(ary, mid, end);
    return merge(a, b);
  }

  public int[] merge(int[] lary, int[] rary) {
    int[] ret = new int[lary.length + rary.length];
    int i = 0;
    int j = 0;
    int position = 0;
    while (i < lary.length && j < rary.length) {
      if (lary[i] < rary[j]) {
        ret[position] = lary[i];
        i += 1;
      } else {
        ret[position] = rary[j];
        j += 1;
      }
      position += 1;
    }

    while (i < lary.length) {
      ret[position] = lary[i];
      i += 1;
      position += 1;
    }

    while (j < rary.length) {
      ret[position] = rary[j];
      j += 1;
      position += 1;
    }

    return ret;
  }
}
