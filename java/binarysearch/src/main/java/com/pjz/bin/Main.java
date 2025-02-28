package com.pjz.bin;

import java.util.*;

public class Main {
    public static List<String> solution(int n, List<String> s, List<Integer> x) {
        // PLEASE DO NOT MODIFY THE FUNCTION SIGNATURE
        // write code here
        ArrayList<Integer> selected = new ArrayList<>();
        Integer max = -1;

        ArrayList<String> rank = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (x.get(j) > max) {
                    if (!selected.contains(j)) {
                        max = x.get(j);
                        rank.add(i,s.get(j));
                        selected.add(i,j);
                    }
                }
            }
            max = -1;
        }


        return rank;
    }

    public static void main(String[] args) {
        System.out.println(solution(4, Arrays.asList("a", "b", "c", "d"), Arrays.asList(1, 2, 2, 1)).equals(Arrays.asList("b", "c", "a", "d")));
        System.out.println(solution(3, Arrays.asList("x", "y", "z"), Arrays.asList(100, 200, 200)).equals(Arrays.asList("y", "z", "x")));
        System.out.println(solution(5, Arrays.asList("m", "n", "o", "p", "q"), Arrays.asList(50, 50, 30, 30, 20)).equals(Arrays.asList("m", "n", "o", "p", "q")));
    }
}
