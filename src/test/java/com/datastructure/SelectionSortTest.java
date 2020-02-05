package com.datastructure;

import org.junit.Before;
import org.junit.Test;

public class SelectionSortTest {
  private int[] ary;

  @Before
  public void setup() {
    ary = new int[] {8, 9, 1, 7, 2, 3, 11};
  }

  @Test
  public void testSort() {
    SelectionSort algo = new SelectionSort();
    algo.sort(ary);
    for (int i = 0; i < ary.length; i++) {
      System.out.println(ary[i]);
    }
  }
}
