package com.question.amazon;

import java.util.*;

/**
 * Given a 2D grid, each cell is either a zombie 1 or a human 0.
 * Zombies can turn adjacent (up/down/left/right) human beings into zombies every hour.
 * Find out how many hours does it take to infect all humans?
 * 
 * Example:
 *
 * Input:
 * [[0, 1, 1, 0, 1],
 *  [0, 1, 0, 1, 0],
 *  [0, 0, 0, 0, 1],
 *  [0, 1, 0, 0, 0]]
 *
 * Output: 2
 *
 * Explanation:
 * At the end of the 1st hour, the status of the grid:
 * [[1, 1, 1, 1, 1],
 *  [1, 1, 1, 1, 1],
 *  [0, 1, 0, 1, 1],
 *  [1, 1, 1, 0, 1]]
 *
 * At the end of the 2nd hour, the status of the grid:
 * [[1, 1, 1, 1, 1],
 *  [1, 1, 1, 1, 1],
 *  [1, 1, 1, 1, 1],
 *  [1, 1, 1, 1, 1]]
 *  
 */
public class ZombieinMatrix {
    //994. Rotting Oranges
    static int minHours(List<List<Integer>> grid) {
        if (grid == null || grid.size() == 0) return -1;
        
        int m = grid.size(), n = grid.get(0).size();
        //starts from zombie-1, perform bfs layer by layer;
        //for human-0, keep a count, when this count is 0 after bfs, ret the hours, hours++ as layer increases
        //queue stores [] - coordinates of cell, enqueue all zombies
        int hours = 0;
        int humanCnt = 0;
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid.get(i).get(j) == 0) {
                    humanCnt++;
                } else {
                    queue.add(new int[] {i, j});
                }
            }
        }
        
        //4 directions array
        int[][] directions = new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        
        while (!queue.isEmpty() && humanCnt > 0) {
            int size = queue.size();
            
            for (int i = 0; i < size; i++) {
                int[] curr = queue.remove();
                for (int[] dir : directions) {
                    int nx = curr[0] + dir[0];
                    int ny = curr[1] + dir[1];
                    //skip out bound case and new cell zombie
                    if (nx < 0 || nx == m || ny < 0 || ny == n || grid.get(nx).get(ny) == 1) {
                        continue;
                    }
                    grid.get(nx).set(ny, 1);
                    queue.add(new int[] {nx, ny});
                    humanCnt--;
                }
            }
            hours++;
        }
        System.out.println(grid);
        
        return humanCnt == 0 ? hours : -1;
    }

    public static void main(String[] args) {
        List<List<Integer>> grid = new ArrayList<>();
        grid.add(Arrays.asList(0, 1, 1, 0, 1));
        grid.add(Arrays.asList(0, 1, 0, 1, 0));
        grid.add(Arrays.asList(0, 0, 0, 0, 1));
        grid.add(Arrays.asList(0, 1, 0, 0, 0));
        System.out.println(grid);
        
        System.out.println(minHours(grid));

        List<List<Integer>> g2 = null;
        List<List<Integer>> g3 = new ArrayList<>();
        System.out.println(minHours(g2));
        System.out.println(minHours(g3));
    }
}
