package com.datastructure;

import org.junit.Before;
import org.junit.Test;

public class MergeSortTest {
  private int[] ary;

  @Before
  public void setup() {
    ary = new int[] {8, 9, 1, 7, 2, 3, 11, 100, 50, 4};
  }

  @Test
  public void testSort() {
    MergeSort algo = new MergeSort();
    algo.sort(ary);
    for (int i = 0; i < ary.length; i++) {
      System.out.println(ary[i]);
    }
  }
}
