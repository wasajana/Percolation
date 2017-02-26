import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

// Estimates percolation threshold for an N-by-N percolation system.
public class PercolationStats {
    private double[] cal;
    private int test;
    private Percolation grid;
    
    // Perform T independent experiments (Monte Carlo simulations) on an 
    // N-by-N grid.
    public PercolationStats(int N, int T) {
        this.test = T;
        cal = new double[test];
        for (int i = 0; i < test; i++) {
            grid = new Percolation(N);
            int count = 0;
            while (!grid.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                if (!grid.isOpen(row, col)) {
                    grid.open(row, col);
                    count = count + 1;
                }
            }
            cal[i] = count / (double) (N * N);
        }
    }
    // Sample mean of percolation threshold.
    public double mean() {
        double average = StdStats.mean(cal);
        return average;
    }

    // Sample standard deviation of percolation threshold.
    public double stddev() {
        double deviation = StdStats.stddev(cal);
        return deviation;
    }

    // Low endpoint of the 95% confidence interval.
    public double confidenceLow() {
        double low = mean() - ((1.96 * stddev()) / Math.sqrt(test));
        return low;
    }

    // High endpoint of the 95% confidence interval.
    public double confidenceHigh() {
        double high = mean() + ((1.96 * stddev()) / Math.sqrt(test));
        return high;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(N, T);
        StdOut.printf("mean           = %f\n", stats.mean());
        StdOut.printf("stddev         = %f\n", stats.stddev());
        StdOut.printf("confidenceLow  = %f\n", stats.confidenceLow());
        StdOut.printf("confidenceHigh = %f\n", stats.confidenceHigh());
    }
}
