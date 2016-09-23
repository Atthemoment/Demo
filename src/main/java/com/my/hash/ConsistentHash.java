package com.my.hash;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHash {

 private final HashFunction hashFunction;
 private final int numberOfReplicas;
 private final SortedMap<HashCode,Integer> circle = new TreeMap<HashCode, Integer>();

 public ConsistentHash(HashFunction hashFunction, int numberOfReplicas,
     Collection<Integer> nodes) {
   this.hashFunction = hashFunction;
   this.numberOfReplicas = numberOfReplicas;

   for (Integer node : nodes) {
     add(node);
   }
 }

 public void add(Integer node) {
   for (int i = 0; i < numberOfReplicas; i++) {
     circle.put(hashFunction.hashInt(node), node);
   }
 }

 public void remove(Integer node) {
   for (int i = 0; i < numberOfReplicas; i++) {
     circle.remove(hashFunction.hashInt(node));
   }
 }

 public Integer get(String key) {
   if (circle.isEmpty()) {
     return null;
   }
    HashCode hash = hashFunction.hashString(key,StandardCharsets.UTF_8);
   if (!circle.containsKey(hash)) {
     SortedMap<HashCode, Integer> tailMap = circle.tailMap(hash);
     hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
   }
   return circle.get(hash);
 }

}