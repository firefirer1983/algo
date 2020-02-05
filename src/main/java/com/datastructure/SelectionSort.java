package com.datastructure;

public class SelectionSort {

  public void sort(int[] ary) {
    int n = ary.length;
    for (int i = 0; i < n - 1; i++) {
      int maxPosition = 0;
      for (int j = 1; j < n - i; j++) {
        if (ary[j] > ary[maxPosition]) {
          maxPosition = j;
        }
      }
      int tmp = ary[maxPosition];
      ary[maxPosition] = ary[n - i - 1];
      ary[n - i - 1] = tmp;
    }
  }
}
