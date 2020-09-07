package com.question.amazon;


import java.util.*;

/**
 * Given an underected connected graph with n nodes labeled 1..n.
 * A bridge (cut edge) is defined as an edge which, when removed, makes the graph disconnected (or more precisely, increases the number of connected components in the graph).
 * Equivalently, an edge is a bridge if and only if it is not contained in any cycle.
 * The task is to find all bridges in the given graph. Output an empty list if there are no bridges.
 *
 * Input:
 * n, an int representing the total number of nodes.
 * edges, a list of pairs of integers representing the nodes connected by an edge.
 * 
 * Input: n = 5, edges = [[1, 2], [1, 3], [3, 4], [1, 4], [4, 5]]
 * Output: [[1, 2], [4, 5]]
 * Explanation:
 * There are 2 bridges:
 * 1. Between node 1 and 2
 * 2. Between node 4 and 5
 * If we remove these edges, then the graph will be disconnected.
 * If we remove any of the remaining edges, the graph will remain connected.
 * 
 */
public class CriticalConnections {
    
    /**
     * 1.构图
     * 2.从任意节点开始，用类似后续遍历的方式dfs，不断的利用子节点的low value更新父节点的low value
     * 3.当前节点的low value大于父节点，他们之间的桥就是一条critical connection
     * @param n
     * @param edges
     * @return
     */
    static int time = 0;
    public static List<List<Integer>> criticalConnections(int n, List<List<Integer>> edges) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer>[] graph = new List[n + 1];
        
        buildGraph(graph, edges);
        
        int[] discoverTime = new int[n + 1];//the time when the node is visited first time
        int[] low = new int[n + 1];//the earliest v
        int[] parent = new int[n + 1];
        Arrays.fill(discoverTime, -1);
        Arrays.fill(low, -1);
        Arrays.fill(parent,-1);
        
        for (int i = 1; i <= n; i++) {
            if (discoverTime[i] == -1) {
                dfs(i, parent, low, discoverTime, res, graph);
            }
        }
        
        res.sort((l1, l2) -> l1.get(0) - l2.get(0));
        
        return res;
    }
    
    private static void dfs(int u, int[] parent, int[] low, int[] disc, List<List<Integer>> res, List<Integer>[] graph) {
        
        if (disc[u] != -1) return;
        
        low[u] = disc[u] = time++;
        //for all neighbors of current u
        for (int v : graph[u]) {
            //if v not visited
            if (disc[v] == -1) {
                parent[v] = u;
                dfs(v, parent, low, disc, res, graph);
                
                if (low[v] > disc[u]) {//if is a critical connection
                    res.add(Arrays.asList(u, v));
                }
                
                low[u] = Math.min(low[u], low[v]);
            } else if (parent[u] != v) { //if u is not parent of v
                low[u] = Math.min(low[u], disc[v]);
            }
        }
    }
    
    private static void buildGraph(List<Integer>[] graph, List<List<Integer>> edges) {
        for (List<Integer> edge : edges) {
            int from = edge.get(0);
            int to = edge.get(1);
            if (graph[from] == null) graph[from] = new ArrayList<>();
            if (graph[to] == null) graph[to] = new ArrayList<>();
            graph[from].add(to);
            graph[to].add(from);
        }
    }
    
    public static void main(String[] args) {
        int n1 = 5;
        List<List<Integer>> e1 = new ArrayList<>();
        e1.add(new ArrayList<>(Arrays.asList(1, 2)));
        e1.add(new ArrayList<>(Arrays.asList(1, 3)));
        e1.add(new ArrayList<>(Arrays.asList(3, 4)));
        e1.add(new ArrayList<>(Arrays.asList(1, 4)));
        e1.add(new ArrayList<>(Arrays.asList(4, 5)));
        
        int n2 = 6;
        List<List<Integer>> e2 = new ArrayList<>();
        e2.add(new ArrayList<>(Arrays.asList(1, 2)));
        e2.add(new ArrayList<>(Arrays.asList(1, 3)));
        e2.add(new ArrayList<>(Arrays.asList(2, 3)));
        e2.add(new ArrayList<>(Arrays.asList(2, 4)));
        e2.add(new ArrayList<>(Arrays.asList(2, 5)));
        e2.add(new ArrayList<>(Arrays.asList(4, 6)));
        e2.add(new ArrayList<>(Arrays.asList(5, 6)));
        
        int n3 = 9;
        List<List<Integer>> e3 = new ArrayList<>();
        e3.add(new ArrayList<>(Arrays.asList(1, 2)));
        e3.add(new ArrayList<>(Arrays.asList(1, 3)));
        e3.add(new ArrayList<>(Arrays.asList(2, 3)));
        e3.add(new ArrayList<>(Arrays.asList(3, 4)));
        e3.add(new ArrayList<>(Arrays.asList(3, 6)));
        e3.add(new ArrayList<>(Arrays.asList(4, 5)));
        e3.add(new ArrayList<>(Arrays.asList(6, 7)));
        e3.add(new ArrayList<>(Arrays.asList(6, 9)));
        e3.add(new ArrayList<>(Arrays.asList(7, 8)));
        e3.add(new ArrayList<>(Arrays.asList(8, 9)));
        
        System.out.println(criticalConnections(n1, e1));
        System.out.println(criticalConnections(n2, e2));
        System.out.println(criticalConnections(n3, e3));
    }
}
