package org.howard.edu.lsp.assignment3;

/**
 * Whether an employee is full-time or part-time, based on hours worked.
 *
 * @author David Ifeanyi
 */
public enum EmploymentStatus {

    PART_TIME("Part-Time"),
    FULL_TIME("Full-Time");

    private static final double FULL_TIME_MINIMUM_HOURS = 30;

    private final String label;

    EmploymentStatus(String label) {
        this.label = label;
    }

    /**
     * Returns the status implied by the given number of hours worked.
     */
    public static EmploymentStatus forHours(double hoursWorked) {
        return hoursWorked < FULL_TIME_MINIMUM_HOURS ? PART_TIME : FULL_TIME;
    }

    /**
     * Returns the label used in the output file, which differs from the
     * constant name.
     */
    public String getLabel() {
        return label;
    }
}
