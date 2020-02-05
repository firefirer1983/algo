package com.datastructure;

public class BubbleSort {

  public void sort(int[] ary) {
    // 总共有 n-1个pass
    int n = ary.length - 1;
    for (int i = 0; i < n; i++) {
      // 每个pass只需要比较从 0 ~ n-i
      for (int j = 0; j < n - i - 1; j++) {
        if (ary[j] > ary[j + 1]) {
          int tmp = ary[j];
          ary[j] = ary[j + 1];
          ary[j + 1] = tmp;
        }
      }
    }
  }
}
