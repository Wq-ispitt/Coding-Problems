package com.question.amazon;

import java.util.*;

public class RobotStartEndPath {

    /**
     * robot Start -> End -> Start
     * 'X' 'S' ' ' 'I'
     * <p>
     * |X|S| | |
     * | | | | |
     * |X| | |I|
     * | | |X| |
     * <p>
     * return any valid paths from start to end then back to start.
     */
    
    public static void main(String args[]) {
        char[][] input1 = new char[][]{
                {'X', 'X', 'X', 'X', 'X', 'S', 'X', 'X'},
                {'X', 'X', 'X', 'X', 'X', ' ', 'X', 'X'},
                {'X', 'X', 'X', 'X', 'X', ' ', 'X', 'X'},
                {'X', ' ', ' ', ' ', ' ', ' ', 'X', 'X'},
                {'X', 'X', 'X', 'I', ' ', ' ', ' ', ' '}};

        char[][] input2 = {
                {'X', 'X', 'X', 'S'},
                {'X', 'X', ' ', ' '},
                {'I', ' ', ' ', ' '}};

        char[][] input3 = {
                {'X', 'X', 'X', 'S'},
                {'X', 'X', ' ', 'X'},
                {'I', ' ', ' ', ' '}};

        char[][] input4 = {
                {'X', 'X', 'X', 'S'},
                {'X', 'X', ' ', 'X'},
                {'I', 'S', ' ', ' '}};
        
        RobotStartEndPath robot = new RobotStartEndPath();
        
        List<List<int[]>> result1 = robot.findPath(input1);
        System.out.println("Result for Input 1: ");
        for (List<int[]> path : result1) {
            for (int[] point : path) {
                System.out.print("(" + point[0] + "," + point[1] + ") ");
            }
            System.out.println();
        }
        
        System.out.println("Result for Input 2: ");
        List<List<int[]>> result2 = robot.findPath(input2);
        for (List<int[]> path : result2) {
            for (int[] point : path) {
                System.out.print("(" + point[0] + "," + point[1] + ") ");
            }
            System.out.println();
        }

        System.out.println("Result for Input 3: ");
        List<List<int[]>> result3 = robot.findPath(input3);
        for (List<int[]> path : result3) {
            for (int[] point : path) {
                System.out.print("(" + point[0] + "," + point[1] + ") ");
            }
            System.out.println();
        }

        System.out.println("Result for Input 4: ");
        List<List<int[]>> result4 = robot.findPath(input4);
        for (List<int[]> path : result4) {
            for (int[] point : path) {
                System.out.print("(" + point[0] + "," + point[1] + ") ");
            }
            System.out.println();
        }
    }
    
    private int row;
    private int col;
    private int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    /**
     * Start from 'S' perform dfs and mark curr cell as visited, until we reach end 'I'
     * 
     * @param board Given 2d char array, board with obstacle 'X' and available cell ' '
     * @return After 'S'->'I' return complete path
     */
    public List<List<int[]>> findPath(char[][] board) {
        if (board == null || board.length == 0) return new ArrayList<>();

        row = board.length;
        col = board[0].length;
        int[][] visited = new int[row][col];
        List<List<int[]>> res = new ArrayList<>();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] == 'S') {
                    visited[i][j] = 1;
                    res = dfs(new ArrayList<>(), board, i, j, visited);
                }
            }
        }
        //deep copy original as temp, reverse temp and append to original list
        for(List<int[]> list : res) {
            List<int[]> tempList = new ArrayList<>(list);
            Collections.reverse(tempList);
            list.addAll(tempList);
        }
        
        return res;
    }
    /**
     * Define dfs as List<List<int[]>> : returns up/down/left/right directions path list
     * @param curPath Path starting from S until current row and col, list of (row, col), 
     * @param board Given 2d board
     * @param row Current row in board
     * @param col Current col in board
     * @param visited int 2d array representing visited as 1, not visited as 0(initially)
     * @return
     */
    private List<List<int[]>> dfs(List<int[]> curPath, char[][] board, int row, int col, int[][] visited) {
        
        int[] curPoint = new int[]{row, col};
        curPath.add(curPoint);
        
        List<List<int[]>> res = new ArrayList<>();
        if (board[row][col] == 'I') {
            res.add(new ArrayList<>(curPath));//deep copy accumulated path until this recursion stack
            return res;
        }

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];
            if (newRow < 0 || newRow == this.row || newCol < 0 || newCol == this.col || board[newRow][newCol] == 'X' || visited[newRow][newCol] == 1) continue;

            visited[newRow][newCol] = 1;
            List<List<int[]>> candidates = dfs(new ArrayList<>(curPath), board, newRow, newCol, visited);
            //keep record of current path so far, deep copy it in next dfs call, so that we can exit when we meet 'I' in future
            visited[newRow][newCol] = 0;
            //reset curr row and col, since all dfs calls pop out
            res.addAll(candidates);
            //candidates here represents 1/4 dfs call, we add all valid dfs path
        }

        //if no valid path - we cannot reach I from S, final res will be empty
        return res;
    }
}
