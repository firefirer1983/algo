package com.datastructure;

import org.junit.Before;
import org.junit.Test;

public class RadixSortTest {
  private int[] ary;

  @Before
  public void setup() {
    ary = new int[] {8, 91, 132, 71, 2123, 30, 11};
  }

  @Test
  public void testSort() {
    RadixSort algo = new RadixSort();
    algo.sort(ary);
    for (int i = 0; i < ary.length; i++) {
      System.out.println(ary[i]);
    }
  }
}
