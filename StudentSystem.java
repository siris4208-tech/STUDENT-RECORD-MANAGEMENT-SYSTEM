import java.util.*;

public class StudentSystem {

    // ---------- ACTIVITY SELECTION (GREEDY) ----------
    static void activitySelection(int[] start, int[] end, int n) {
        int[][] activities = new int[n][2];

        for (int i = 0; i < n; i++) {
            activities[i][0] = start[i];
            activities[i][1] = end[i];
        }

        // sort by end time
        Arrays.sort(activities, Comparator.comparingInt(a -> a[1]));

        System.out.println("\nSelected Activities:");
        int count = 1;
        System.out.println("(" + activities[0][0] + "," + activities[0][1] + ")");

        int lastEnd = activities[0][1];

        for (int i = 1; i < n; i++) {
            if (activities[i][0] >= lastEnd) {
                System.out.println("(" + activities[i][0] + "," + activities[i][1] + ")");
                lastEnd = activities[i][1];
                count++;
            }
        }

        System.out.println("Maximum Activities = " + count);
    }

    // ---------- KNAPSACK (DP) ----------
    static void knapsack(int[] wt, int[] val, int n, int W) {
        int[][] dp = new int[n + 1][W + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= W; w++) {
                if (wt[i - 1] <= w)
                    dp[i][w] = Math.max(val[i - 1] + dp[i - 1][w - wt[i - 1]], dp[i - 1][w]);
                else
                    dp[i][w] = dp[i - 1][w];
            }
        }

        System.out.println("\nMaximum Value = " + dp[n][W]);
    }

    // ---------- LCS (DP) ----------
    static void lcs(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();

        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1))
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }

        // reconstruct LCS
        int i = n, j = m;
        String lcs = "";

        while (i > 0 && j > 0) {
            if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                lcs = s1.charAt(i - 1) + lcs;
                i--; j--;
            } else if (dp[i - 1][j] > dp[i][j - 1])
                i--;
            else
                j--;
        }

        System.out.println("\nLCS = " + lcs);
        System.out.println("Length = " + dp[n][m]);
    }

    // ---------- MAIN ----------
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Activity Selection Input
        int n = sc.nextInt();
        int[] start = new int[n];
        int[] end = new int[n];

        for (int i = 0; i < n; i++) start[i] = sc.nextInt();
        for (int i = 0; i < n; i++) end[i] = sc.nextInt();

        activitySelection(start, end, n);

        // Knapsack Input
        int items = sc.nextInt();
        int[] wt = new int[items];
        int[] val = new int[items];

        for (int i = 0; i < items; i++) wt[i] = sc.nextInt();
        for (int i = 0; i < items; i++) val[i] = sc.nextInt();

        int W = sc.nextInt();

        knapsack(wt, val, items, W);

        // LCS Input
        String s1 = sc.next();
        String s2 = sc.next();

        lcs(s1, s2);
    }
}