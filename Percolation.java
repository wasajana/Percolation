import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// Models an N-by-N per9colation system.
public class Percolation {
    private int N;
    private WeightedQuickUnionUF verify;
    private WeightedQuickUnionUF solution;
    private int sink;
    private int source = 0;
    private boolean[][] box;
    private int count = 0;
    private int length;

    // Create an N-by-N grid, with all sites blocked.
    public Percolation(int N) {
        this.length = N;
        this.box = new boolean[length][length];

        this.sink = (length*length) + 1;  
        verify = new WeightedQuickUnionUF(length*length+2);
        solution = new WeightedQuickUnionUF(length*length+2);

        for (int i = 0; i < length; i++) {
            verify.union(encode(0, i), source);
            solution.union(encode(0, i), source);
        }

        for (int j = 0; j < length; j++) {
            verify.union(encode(N-1, j), sink);
        }
    }

    // Open site (i, j) if it is not open already.
    public void open(int i, int j) {

        box[i][j] = true;
        count += 1;
        if ((i-1) >= 0 && isOpen(i-1, j)) {
            verify.union(encode(i, j), encode(i-1, j)); 
            solution.union(encode(i, j), encode(i-1, j));
        }
        if ((i+1) < length && isOpen(i+1, j)) {
            verify.union(encode(i, j), encode(i+1, j));
            solution.union(encode(i, j), encode(i+1, j));
        }
        if ((j-1) >= 0 && isOpen(i, j-1)) {
            verify.union(encode(i, j), encode(i, j-1));
            solution.union(encode(i, j), encode(i, j-1));
        }
        if ((j+1) < length && isOpen(i, j+1)) {
            verify.union(encode(i, j), encode(i, j+1));
            solution.union(encode(i, j), encode(i, j+1));
        }

    }
    // Is site (i, j) open?
    public boolean isOpen(int i, int j) {
        return box[i][j];
    }

    // Is site (i, j) full?
    public boolean isFull(int i, int j) {
        return solution.connected(source, encode(i, j));
    }

    // Number of open sites.
    public int numberOfOpenSites() {
        return count;
    }

    // Does the system percolate?
    public boolean percolates() {
        return verify.connected(0, sink);
    }

    // An integer ID (1...N) for site (i, j).
    private int encode(int i, int j) {
        int number = length * i + 1 + j;
        return number;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Percolation perc = new Percolation(N);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        StdOut.println(perc.numberOfOpenSites() + " open sites");
        if (perc.percolates()) {
            StdOut.println("percolates");
        }
        else {
            StdOut.println("does not percolate");
        }

        // Check if site (i, j) optionally specified on the command line
        // is full.
        if (args.length == 3) {
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[2]);
            StdOut.println(perc.isFull(i, j));
        }
    }
}
