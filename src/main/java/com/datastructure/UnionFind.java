package com.datastructure;

public class UnionFind {
  private int[] sz;
  private int[] id;
  private int componentNum;
  private int size;

  public UnionFind(int sz) {
    if (sz <= 0) throw new IllegalArgumentException("Size <= 0 is not allowed");
    this.sz = new int[sz];
    this.id = new int[sz];
    for (int i = 0; i < sz; i++) {
      id[i] = i;
      this.sz[i] = 1;
    }
    componentNum = sz;
    size = sz;
  }

  public int find(int p) {
    int root = id[p];
    if (root != p) {
      root = id[p] = find(id[p]);
    }
    return root;
  }

  public int size() {
    return size;
  }

  public int components() {
    return componentNum;
  }

  public int componentSize(int p) {
    return sz[find(p)];
  }

  public boolean connected(int p, int q) {
    return find(p) == find(q);
  }

  public boolean unify(int p, int q) {
    int rp = find(p);
    int rq = find(q);
    if (rp == rq) {
      return true;
    }

    if (sz[rp] > sz[rq]) {
      sz[rp] += sz[rq];
      id[rq] = rp;
    } else {
      sz[rq] += sz[rp];
      id[rp] = rq;
    }
    componentNum -= 1;
    return true;
  }
}
