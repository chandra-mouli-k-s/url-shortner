package com.chandramouli.urlshortner;

import java.util.*;

public class Testing {

//    Write a program to print all the LEADERS in the array. An element is leader if it is greater than all the elements to its right side. And the rightmost element is always a leader.
//    For example int the array {16, 17, 4, 3, 5, 2}, leaders are 17, 5 and 2.

//    Given an array a[] of size N which contains elements from 0 to N-1, you need to find all the elements occurring more than once in the given array.
//
//            a[] = {2,3,1,2,3}
//    Output: 2 3

    public static void main(String[] args) {
        System.out.println(isPalindromePossible("qwewqw".toCharArray()));//
    }

    public static List<Integer> findLeader1(List<Integer> intList) {
        List<Integer> leaders = new ArrayList<>();
        for (int i = 0; i < intList.size(); i++) {
            for (int j = i + 1; j < intList.size(); j++) {
                if(intList.get(i).compareTo(intList.get(j)) < 0) {
                    break;
                } else {
                    if (j == intList.size() - 1) {
                        leaders.add(intList.get(i));
                    }
                }
            }
            if (i == intList.size() - 1) {
                leaders.add(intList.get(i));
            }
        }
        return leaders;
    }

    public static List<Integer> findDuplicates(List<Integer> dupeslist) {
        List<Integer> toReturn = new ArrayList<>();
        Collections.sort(dupeslist);
        for (int i = 0; i < dupeslist.size() - 1; i++) {
            if(dupeslist.get(i).equals(dupeslist.get(i + 1))) {
                toReturn.add(dupeslist.get(i));
            }
        }
        return toReturn;
    }

    public static boolean isPalindromePossible(char[] arr) {
        Map<String, Integer> counter = new HashMap<>();
        for (char c : arr) {
            String character = String.valueOf(c);
            counter.put(character, counter.getOrDefault(character, 0) + 1);
        }
        int checkCount = 0;
        for(Integer each : counter.values()) {
            checkCount += each % 2;
        }
        System.out.println(counter);
        return checkCount <= 1;
    }

    //JWT - protocols FTP etc chown - group of permissions in UNIX | SOLID | design patterns | JS | closure | async promises | data normalization | rebase GIT |

//    public static List<Integer> findLeader2(List<Integer> intList) {
//        List<Integer> leaders = new ArrayList<>(intList);
//        for (int i = 0; i < intList.size() - 1; i++) {
//            if (intList.get(i) > intList.get(i + 1)) {
//                leaders.remove(intList.get(i));
//            }
//        }
//        return null;
//    }


}
