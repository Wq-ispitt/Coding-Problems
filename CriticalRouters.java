package com.question.amazon;

import java.util.*;

/**
 * You are given an undirected connected graph.
 * An articulation point (or cut vertex) is defined as a vertex which, when removed along with associated edges, makes the graph disconnected (or more precisely, increases the number of connected components in the graph).
 * The task is to find all articulation points in the given graph.
 *
 * Input:
 * The input to the function/method consists of three arguments:
 *
 * numNodes, an integer representing the number of nodes in the graph.
 * numEdges, an integer representing the number of edges in the graph.
 * edges, the list of pair of integers - A, B representing an edge between the nodes A and B.
 * Output:
 * Return a list of integers representing the critical nodes.
 * 
 * Input: numNodes = 7, numEdges = 7, edges = [[0, 1], [0, 2], [1, 3], [2, 3], [2, 5], [5, 6], [3, 4]]
 * 
 * Output: [2, 3, 5]
 */
public class CriticalRouters {

    private static int time = 0;
    
    static List<Integer> getCriticalRouters(int numNodes, int numEdges, int[][] edges) {
        List<Integer> res = new ArrayList<>();
        if (edges == null || edges.length == 0 || numEdges <= 0 || numNodes <= 0) return res;
        
        //build undirected adj list graph, based on edges
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            graph.putIfAbsent(from, new HashSet<>());
            graph.putIfAbsent(to, new HashSet<>());
            graph.get(from).add(to);
            graph.get(to).add(from);
        }
        System.out.println("Built graph: " + graph);
        //keep low: the earliest time neighbor v
        int[] low = new int[numNodes];
        //parent: from u -> v, current v's parent is u
        int[] parent = new int[numNodes];
        //discovertime: time when node first visited
        int[] disc = new int[numNodes];
        Arrays.fill(parent, -1);
        Arrays.fill(disc, -1);
        Arrays.fill(low,  -1);
        Set<Integer> critNodes = new HashSet<>();
        //starts from unvisited node, perform dfs
        for (int i = 0; i < numNodes; i++) {
            if (disc[i] == -1) {
                dfs(i, parent, low, disc, graph, critNodes);
            }
        }
        
        //System.out.println(critNodes);
        
        return new ArrayList<>(critNodes);
    }
    
    static void dfs(int curr, int[] parent, int[] low, int[] disc, Map<Integer, Set<Integer>> graph, Set<Integer> critNodes) {
        if (disc[curr] != -1) return;
        
        low[curr] = disc[curr] = time++;
        int children = 0;
        for (int neighbor : graph.get(curr)) {
            if (disc[neighbor] == -1) {
                children++;
                parent[neighbor] = curr;
                dfs(neighbor, parent, low, disc, graph, critNodes);
                //neighbor is curr's neighbors, when low[neighbor] > disc[curr], edge (curr,neighbor) is a bridge
                if (low[neighbor] > disc[curr] && parent[curr] != -1 || (parent[curr] == -1 && children >= 2)) {
                    critNodes.add(curr);
                }
//                if (low[neighbor] > disc[curr]) {
//                    critNodes.add(curr);
//                }
                low[curr] = Math.min(low[curr], low[neighbor]);
            } else if (parent[curr] != neighbor) {
                //check if not circle
                low[curr] = Math.min(low[curr], disc[neighbor]);
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println(getCriticalRouters(7, 7, new int[][] {{0, 1}, {0, 2}, {1, 3}, {2, 3}, {2, 5}, {5, 6}, {3, 4}}));
        System.out.println();
        System.out.println(getCriticalRouters(4, 4,new int[][] {{0, 1}, {1, 2}, {2, 0}, {1, 3}}));

        System.out.println("--------------");

        System.out.println(findarticulationPoints(7, 7, new int[][] {{0, 1}, {0, 2}, {1, 3}, {2, 3}, {2, 5}, {5, 6}, {3, 4}}));
        System.out.println();
        System.out.println(findarticulationPoints(4, 4,new int[][] {{0, 1}, {1, 2}, {2, 0}, {1, 3}}));
        
    }
    
    private static int t;
    static List<Integer> findarticulationPoints(int numNodes, int numEdges, int[][] edges) {
        t = 0;
        if (edges == null || edges.length == 0 || numEdges <= 0 || numNodes <= 0) return new ArrayList<>();
        //build adj graph, use set since nodes are unique 
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            graph.putIfAbsent(from, new HashSet<>());
            graph.putIfAbsent(to, new HashSet<>());
            graph.get(from).add(to);
            graph.get(to).add(from);
        }
        System.out.println("Built graph: " + graph);
        Set<Integer> points = new HashSet<>();
        Set<Integer> visited = new HashSet<>();
        //discover/visited time: time of reaching the node
        Map<Integer, Integer> discTime = new HashMap<>();
        //low: min of visited time of all adj nodes reachable from given node in dfs
        Map<Integer, Integer> lowTime = new HashMap<>();
        //parent: parent of given node in dfs
        Map<Integer, Integer> parent = new HashMap<>();
        
        for (int i = 0; i < numNodes; i++) {
            if (discTime.get(i) == null) {
                DFS(i, visited, points, discTime, lowTime, parent, graph);
            }
            
        }
       
        
        
        return new ArrayList<>(points);
    }
    
    static void DFS(int curr, Set<Integer> visited, Set<Integer> points, Map<Integer, Integer> discTime, Map<Integer, Integer> lowTime, Map<Integer, Integer> parent, Map<Integer, Set<Integer>> graph) {
        
        visited.add(curr);
        discTime.put(curr, t);
        lowTime.put(curr, t);
        t++;
        int childCount = 0;
        boolean isArtiPoint = false;
        
        for (int adj : graph.get(curr)) {
            //ignore if adj is same as parent
            if (parent.get(curr) != null && adj == parent.get(curr)) {
                continue;
            }
            if (!visited.contains(adj)) {
                parent.put(adj, curr);
                childCount++;
                DFS(adj, visited, points, discTime, lowTime, parent,graph);
                
                if (lowTime.get(adj) > discTime.get(curr)) {
                    isArtiPoint = true;
                } else {
                    lowTime.put(curr, Math.min(lowTime.get(curr), lowTime.get(adj)));
                }
            } else {
                //if adj is already visited, see if you can get better low time
                lowTime.put(adj, Math.min(lowTime.get(curr), discTime.get(adj)));
            }
        }
        
        //if either check meets then curr vertex is articulation point
        //1. root vertex with two independent children 2.low time of adj vertex >= visited time
        if ((parent.get(curr) == null && childCount >= 2) || parent.get(curr) != null && isArtiPoint) {
            points.add(curr);
        }
    }
}
