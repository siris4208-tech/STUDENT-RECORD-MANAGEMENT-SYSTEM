import java.util.*;

class Edge {
    int src, dest, weight;

    Edge(int s, int d, int w) {
        src = s;
        dest = d;
        weight = w;
    }
}

public class StudentGraphSystem {

    static final int INF = 1000000000;

    // ---------------- Dijkstra ----------------
    static void dijkstra(int V, List<List<Edge>> graph, int src) {
        int[] dist = new int[V + 1];
        Arrays.fill(dist, INF);
        dist[src] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[]{src, 0});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int u = curr[0];

            for (Edge e : graph.get(u)) {
                int v = e.dest;
                int w = e.weight;

                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    pq.add(new int[]{v, dist[v]});
                }
            }
        }

        System.out.println("Dijkstra Shortest Path from Student " + src + ":");
        for (int i = 1; i <= V; i++) {
            if (i != src)
                System.out.println(src + " → " + i + " = " + dist[i]);
        }
    }

    // ---------------- Bellman-Ford ----------------
    static void bellmanFord(int V, List<Edge> edges, int src) {
        int[] dist = new int[V + 1];
        Arrays.fill(dist, INF);
        dist[src] = 0;

        for (int i = 1; i < V; i++) {
            for (Edge e : edges) {
                if (dist[e.src] != INF && dist[e.src] + e.weight < dist[e.dest]) {
                    dist[e.dest] = dist[e.src] + e.weight;
                }
            }
        }

        // Check negative cycle
        for (Edge e : edges) {
            if (dist[e.src] != INF && dist[e.src] + e.weight < dist[e.dest]) {
                System.out.println("\nBellman-Ford:");
                System.out.println("Negative Cycle Detected!");
                return;
            }
        }

        System.out.println("\nBellman-Ford:");
        System.out.println("No Negative Cycle Detected");
    }

    // ---------------- Floyd-Warshall ----------------
    static void floydWarshall(int[][] graph, int V) {
        int[][] dist = new int[V + 1][V + 1];

        for (int i = 1; i <= V; i++) {
            for (int j = 1; j <= V; j++) {
                dist[i][j] = graph[i][j];
            }
        }

        for (int k = 1; k <= V; k++) {
            for (int i = 1; i <= V; i++) {
                for (int j = 1; j <= V; j++) {
                    if (dist[i][k] < INF && dist[k][j] < INF &&
                            dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        System.out.println("\nFloyd-Warshall Matrix:");
        for (int i = 1; i <= V; i++) {
            for (int j = 1; j <= V; j++) {
                if (dist[i][j] == INF)
                    System.out.print("∞   ");
                else
                    System.out.print(dist[i][j] + "   ");
            }
            System.out.println();
        }
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {

        int V = 4; // Number of students

        // Graph for Dijkstra
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i <= V; i++) graph.add(new ArrayList<>());

        // Edges
        graph.get(1).add(new Edge(1, 2, 5));
        graph.get(1).add(new Edge(1, 3, 10));
        graph.get(2).add(new Edge(2, 3, 3));
        graph.get(3).add(new Edge(3, 4, 1));

        // Edge list for Bellman-Ford
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(1, 2, 5));
        edges.add(new Edge(1, 3, 10));
        edges.add(new Edge(2, 3, 3));
        edges.add(new Edge(3, 4, 1));

        // Matrix for Floyd-Warshall
        int[][] matrix = new int[V + 1][V + 1];

        for (int i = 1; i <= V; i++) {
            for (int j = 1; j <= V; j++) {
                if (i == j) matrix[i][j] = 0;
                else matrix[i][j] = INF;
            }
        }

        matrix[1][2] = 5;
        matrix[1][3] = 10;
        matrix[2][3] = 3;
        matrix[3][4] = 1;

        int source = 1;

        dijkstra(V, graph, source);
        bellmanFord(V, edges, source);
        floydWarshall(matrix, V);
    }
}