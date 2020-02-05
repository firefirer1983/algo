package com.datastructure;
import java.util.Arrays;

public class RadixSort {
  public void sort(int[] ary) {
    int maxMember = ary[0];
    for (int i = 0; i < ary.length; i++) {
      if (ary[i] > maxMember) maxMember = ary[i];
    }

    int maxDigitNum = String.valueOf(maxMember).length();

    int[] memberCounts = new int[10];

    int[][] buckets = new int[10][ary.length];

    for (int i = 0, n = 1; i < maxDigitNum; i++, n *= 10) {
      for (int k = 0; k < ary.length; k++) {
        int digit = (ary[k] / n) % 10;
        buckets[digit][memberCounts[digit]] = ary[k];
        memberCounts[digit] += 1;
      }

//      for (int j = 0; j < 10; j++) {
//        System.out.println(Arrays.toString(buckets[j]));
//      }
//      System.out.println(Arrays.toString(memberCounts));
      int c = 0;
      for (int l = 0; l < 10; l++) {
        if (memberCounts[l] > 0) {
          for (int j = 0; j < memberCounts[l]; j++) {
//            System.out.println("ary[" + c + "]" + "=buckets[" + l + "]" + "[" + j + "]");
            ary[c] = buckets[l][j];
            c += 1;
          }
        }
        memberCounts[l] = 0;
      }
    }
  }
}
