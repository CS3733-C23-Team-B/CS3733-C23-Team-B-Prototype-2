package edu.wpi.teamb.Algorithms;

import java.util.List;

public class Sorting {

  public static void sort(List<String> list) {
    sortHelper(list, 0, list.size() - 1);
  }

  private static void sortHelper(List<String> list, int left, int right) {
    if (left >= right) {
      return;
    }
    int pivotIndex = partition(list, left, right);
    sortHelper(list, left, pivotIndex - 1);
    sortHelper(list, pivotIndex + 1, right);
  }

  private static int partition(List<String> list, int left, int right) {
    int pivotIndex = (left + right) / 2;
    String pivotValue = list.get(pivotIndex);
    swap(list, pivotIndex, right);
    int storeIndex = left;
    for (int i = left; i < right; i++) {
      if (list.get(i).compareTo(pivotValue) < 0) {
        swap(list, storeIndex, i);
        storeIndex++;
      }
    }
    swap(list, right, storeIndex);
    return storeIndex;
  }

  private static void swap(List<String> list, int i, int j) {
    String temp = list.get(i);
    list.set(i, list.get(j));
    list.set(j, temp);
  }
}
