/**
 * An implementation of a hash-table using separate chaining with a linked list.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.datastructure;

import java.util.*;

class Entry<K, V> {
  int hash;
  K key;
  V value;

  public Entry(K key, V value) {
    this.key = key;
    this.value = value;
    this.hash = key.hashCode();
  }

  public boolean equals(Entry<K, V> other) {
    if (this.hash != other.hashCode()) return false;
    return key.equals(other.key);
  }

  public String toString() {
    return key + " => " + value;
  }
}

class HashTableSeparateChaining<K, V> implements Iterable<K> {
  private int size, threshold, capacity;
  private double maxLoadFactor;
  private LinkedList<Entry<K, V>>[] table;
  private static final double DEFAULT_LOAD_FACTOR = 0.75;
  private static final int DEFAULT_CAPACITY = 3;

  public HashTableSeparateChaining() {
    this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
  }

  public HashTableSeparateChaining(int capacity) {
    this(capacity, DEFAULT_LOAD_FACTOR);
  }

  public HashTableSeparateChaining(int capacity, double maxLoadFactor) {
    if (capacity < 0) throw new IllegalArgumentException("Illegal Capacity");
    if (maxLoadFactor <= 0 || Double.isNaN(maxLoadFactor) || Double.isInfinite(maxLoadFactor))
      throw new IllegalArgumentException("Illegal maxLoadFactor");
    this.capacity = capacity;
    this.maxLoadFactor = maxLoadFactor;
    this.table = new LinkedList[capacity];
    this.threshold = (int) (this.capacity * this.maxLoadFactor);
  }

  private int normalizeIndex(int hash) {
    return (hash & 0x7FFFFFFF) % capacity;
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public void clear() {
    Arrays.fill(table, null);
    size = 0;
  }

  public boolean hasKey(K key) {
    if (key == null) return false;
    int bucketIndex = normalizeIndex(key.hashCode());
    LinkedList<Entry<K, V>> bucket = table[bucketIndex];
    if (bucket == null) return false;
    return bucketSeek(key, bucket) != null;
  }

  public boolean containsKey(K key) {
    return hasKey(key);
  }

  public V get(K key) {
    if (key == null) return null;
    int bucketIndex = normalizeIndex(key.hashCode());
    if (table[bucketIndex] == null) return null;
    Entry<K, V> item = bucketSeek(key, table[bucketIndex]);
    if (item == null) return null;
    return item.value;
  }

  public V add(K key, V val) {
    return insert(key, val);
  }

  public V put(K key, V val) {
    return insert(key, val);
  }

  private V insert(K key, V val) {
    if (key == null) throw new IllegalArgumentException("Null key");
    Entry<K, V> entry = new Entry<>(key, val);
    int bucketIndex = normalizeIndex(entry.hash);

    return bucketInsert(bucketIndex, entry);
  }

  public V remove(K key) {
    if (key == null) throw new IllegalArgumentException("Null key");
    int bucketIndex = normalizeIndex(key.hashCode());
    LinkedList<Entry<K, V>> bucket = table[bucketIndex];
    if (bucket == null) return null;
    return bucketRemove(key, bucket);
  }

//  1. 插入新的item, 应该返回老的item的value值.
//  2. 如果key已经存在, 则应该更新 value,返回老的值.
//  3. 如果key 不存在, 插入,并且返回 null 标志没有前值.
  private V bucketInsert(int bucketIndex, Entry<K, V> entry) {
    LinkedList<Entry<K, V>> bucket = table[bucketIndex];
    if (bucket == null) table[bucketIndex] = bucket = new LinkedList<>();

    Entry<K, V> exist = bucketSeek(entry.key, bucket);
    if (exist == null) {
      bucket.add(entry);
      size += 1;
      if (size > threshold) resize();
      return null;
    } else {
      V oldValue = exist.value;
      exist.value = entry.value;
      return oldValue;
    }
  }

  private Entry<K, V> bucketSeek(K key, LinkedList<Entry<K, V>> bucket) {
    for (Entry<K, V> item : bucket) {
      if (key.equals(item.key)) {
        return item;
      }
    }
    return null;
  }

  private V bucketRemove(K key, LinkedList<Entry<K, V>> bucket) {
    Entry<K, V> item = bucketSeek(key, bucket);
    if (item == null) return null;
    bucket.remove(item);
    size -= 1;
    return item.value;
  }

  //  1. 新建一个table, capacity 为原的两倍.
  //  2. 轮询老table的每个bucket，再轮询每个bucket里面的每个元素, 插入到新的表中(重normalizeIndex)
  //  3. 删除原表及其元素
  private void resize() {
    capacity *= 2;
    threshold = (int) (maxLoadFactor * capacity);
    LinkedList<Entry<K, V>>[] larger = new LinkedList[capacity];
    for (int i = 0; i < table.length; i++) {
      LinkedList<Entry<K, V>> bucket = table[i];
      if (bucket != null) {
        for (Entry<K, V> item : bucket) {
          int bucketIndex = normalizeIndex(item.hash);
          LinkedList<Entry<K, V>> largerBucket = larger[bucketIndex];
          if (largerBucket == null) larger[bucketIndex] = largerBucket = new LinkedList<>();
          largerBucket.add(item);
        }
        bucket.clear();
        bucket = null;
      }
    }
    table = larger;
  }

  public List<K> keys() {
    List<K> keys = new ArrayList<>(size);
    for (int i = 0; i < table.length; i++) {
      LinkedList<Entry<K, V>> bucket = table[i];
      if (bucket != null) {
        for (Entry<K, V> item : bucket) {
          keys.add(item.key);
        }
      }
    }
    return keys;
  }

  public List<V> values() {
    List<V> values = new ArrayList<>(size);
    for (int i = 0; i < table.length; i++) {
      LinkedList<Entry<K, V>> bucket = table[i];
      if (bucket != null) {
        for (Entry<K, V> item : bucket) {
          values.add(item.value);
        }
      }
    }
    return values;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("{");
    for (int i = 0; i < table.length; i++) {
      if (table[i] == null) continue;
      for (Entry<K, V> item : table[i]) builder.append(" " + item.toString());
    }
    builder.append(" }");
    return builder.toString();
  }

  // Return an iterator to iterate over all the keys in this map
  @Override
  public java.util.Iterator<K> iterator() {
    final int elementCount = size();
    return new java.util.Iterator<K>() {

      int bucketIndex = 0;
      java.util.Iterator<Entry<K, V>> bucketIter = (table[0] == null) ? null : table[0].iterator();

      @Override
      public boolean hasNext() {

        // An item was added or removed while iterating
        if (elementCount != size) throw new java.util.ConcurrentModificationException();

        // No iterator or the current iterator is empty
        if (bucketIter == null || !bucketIter.hasNext()) {

          // Search next buckets until a valid iterator is found
          while (++bucketIndex < capacity) {
            if (table[bucketIndex] != null) {

              // Make sure this iterator actually has elements -_-
              java.util.Iterator<Entry<K, V>> nextIter = table[bucketIndex].iterator();
              if (nextIter.hasNext()) {
                bucketIter = nextIter;
                break;
              }
            }
          }
        }
        return bucketIndex < capacity;
      }

      @Override
      public K next() {
        return bucketIter.next().key;
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }
}
