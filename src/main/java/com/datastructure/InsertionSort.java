package com.datastructure;

public class InsertionSort {

  public void sort(int[] ary) {
    int n = ary.length;
    for (int i = 1; i < n; i++) {
      int rightPosition = i;
      for (int j = i - 1; j >= 0; j--) {
        if (ary[i] < ary[j]) {
          rightPosition = j;
        }
      }
      if (rightPosition != i) {
        int tmp = ary[i];
        int j = i;
        while (j > rightPosition) {
          ary[j] = ary[j - 1];
          j -= 1;
        }
        ary[rightPosition] = tmp;
      }
    }
  }
}
