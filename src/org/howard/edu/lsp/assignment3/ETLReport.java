package org.howard.edu.lsp.assignment3;

/**
 * Tallies what the pipeline did and prints the run summary.
 *
 * <p>The counts are private and are raised through named methods, so the stage
 * that observes an event is the stage that reports it, and no stage can reach
 * into the totals.
 *
 * @author David Ifeanyi
 */
public class ETLReport {

    private int rowsRead;
    private int rowsTransformed;
    private int rowsSkipped;

    /**
     * Records that a data row was read from the input.
     */
    public void recordRead() {
        rowsRead++;
    }

    /**
     * Records that a row was transformed and written to the output.
     */
    public void recordTransformed() {
        rowsTransformed++;
    }

    /**
     * Records that a row could not be used and was skipped.
     */
    public void recordSkipped() {
        rowsSkipped++;
    }

    public int getRowsRead() {
        return rowsRead;
    }

    public int getRowsTransformed() {
        return rowsTransformed;
    }

    public int getRowsSkipped() {
        return rowsSkipped;
    }

    /**
     * Prints the run summary to standard output.
     */
    public void printSummary(String outputPath) {
        System.out.println("Rows read: " + rowsRead);
        System.out.println("Rows transformed: " + rowsTransformed);
        System.out.println("Rows skipped: " + rowsSkipped);
        System.out.println("Output file: " + outputPath);
    }
}
