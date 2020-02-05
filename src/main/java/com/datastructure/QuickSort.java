package com.datastructure;

public class QuickSort {

  public void sort(int[] ary) {
    // 总共有 n-1个pass
    int n = ary.length;
    _sort(ary, 0, n - 1);
  }

  private void _sort(int[] ary, int begin, int end) {
//    System.out.println("begin: " + begin + " end: " + end);
    int pivot = begin + (end - begin) / 2;
    int l = begin;
    int r = end;
    while (r > l) {
      while (ary[l] < ary[pivot]) {
        l += 1;
      }

      while (ary[r] > ary[pivot]) {
        r -= 1;
      }

      if (l >= r) break;

      int tmp = ary[l];
      ary[l] = ary[r];
      ary[r] = tmp;

      if (l == pivot) r -= 1;

      if (r == pivot) l += 1;
    }

    if (l == r) {
      l += 1;
      r -= 1;
    }

//    for (int i = 0; i < ary.length; i++) System.out.println(ary[i]);
    if (l < end) _sort(ary, l, end);
    if (r > begin) _sort(ary, begin, r);
  }
}
