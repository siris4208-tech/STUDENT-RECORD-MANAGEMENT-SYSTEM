import java.util.*;

// Edge class (IMPORTANT: implements Comparable)
class Edge implements Comparable<Edge> {
    int u, v, weight;

    Edge(int u, int v, int weight) {
        this.u = u;
        this.v = v;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}

public class MSTStudentSystem {

    static int V = 4;

    // ---------- PRIM ----------
    static void primMST(int[][] graph) {
        boolean[] visited = new boolean[V + 1];
        int[] key = new int[V + 1];
        int[] parent = new int[V + 1];

        Arrays.fill(key, Integer.MAX_VALUE);
        key[1] = 0;
        parent[1] = -1;

        for (int i = 1; i <= V; i++) {
            int u = -1, min = Integer.MAX_VALUE;

            for (int j = 1; j <= V; j++) {
                if (!visited[j] && key[j] < min) {
                    min = key[j];
                    u = j;
                }
            }

            visited[u] = true;

            for (int v = 1; v <= V; v++) {
                if (graph[u][v] != 0 && !visited[v] && graph[u][v] < key[v]) {
                    key[v] = graph[u][v];
                    parent[v] = u;
                }
            }
        }

        // Store edges
        List<Edge> result = new ArrayList<>();
        int cost = 0;

        for (int i = 2; i <= V; i++) {
            result.add(new Edge(parent[i], i, graph[i][parent[i]]));
            cost += graph[i][parent[i]];
        }

        // Sort edges (THIS LINE CAUSED YOUR ERROR BEFORE)
        Collections.sort(result);

        System.out.println("Minimum Spanning Tree (Prim’s):");
        for (Edge e : result) {
            System.out.println(e.u + " - " + e.v + " = " + e.weight);
        }
        System.out.println("Total Cost = " + cost);
    }

    // ---------- KRUSKAL ----------
    static int find(int[] parent, int i) {
        if (parent[i] != i)
            parent[i] = find(parent, parent[i]);
        return parent[i];
    }

    static void union(int[] parent, int[] rank, int x, int y) {
        int rootX = find(parent, x);
        int rootY = find(parent, y);

        if (rank[rootX] < rank[rootY])
            parent[rootX] = rootY;
        else if (rank[rootX] > rank[rootY])
            parent[rootY] = rootX;
        else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
    }

    static void kruskalMST(List<Edge> edges) {
        Collections.sort(edges);

        int[] parent = new int[V + 1];
        int[] rank = new int[V + 1];

        for (int i = 1; i <= V; i++) {
            parent[i] = i;
            rank[i] = 0;
        }

        int cost = 0;

        System.out.println("\nMinimum Spanning Tree (Kruskal’s):");

        for (Edge e : edges) {
            int x = find(parent, e.u);
            int y = find(parent, e.v);

            if (x != y) {
                System.out.println(e.u + " - " + e.v + " = " + e.weight);
                cost += e.weight;
                union(parent, rank, x, y);
            }
        }

        System.out.println("Total Cost = " + cost);
    }

    // ---------- MAIN ----------
    public static void main(String[] args) {

        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(1, 2, 5));
        edges.add(new Edge(1, 3, 10));
        edges.add(new Edge(2, 3, 3));
        edges.add(new Edge(3, 4, 1));

        int[][] graph = new int[V + 1][V + 1];

        graph[1][2] = graph[2][1] = 5;
        graph[1][3] = graph[3][1] = 10;
        graph[2][3] = graph[3][2] = 3;
        graph[3][4] = graph[4][3] = 1;

        primMST(graph);
        kruskalMST(edges);
    }
}