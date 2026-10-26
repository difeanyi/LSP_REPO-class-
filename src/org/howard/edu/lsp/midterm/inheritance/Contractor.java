package org.howard.edu.lsp.midterm.inheritance;

/**
 * A contractor who receives a 15% premium on all hours worked.
 *
 * @author David Ifeanyi
 */
public class Contractor extends Employee {

    private static final double PREMIUM_MULTIPLIER = 1.15;

    /**
     * Creates a contractor.
     *
     * @param hoursWorked the number of hours worked in the week (nonnegative)
     * @param hourlyRate  the hourly pay rate (nonnegative)
     */
    public Contractor(double hoursWorked, double hourlyRate) {
        super(hoursWorked, hourlyRate);
    }

    /**
     * Calculates pay with a 15% premium applied to all hours worked.
     *
     * @return the pay amount
     */
    @Override
    public double calculatePay() {
        return getHoursWorked() * getHourlyRate() * PREMIUM_MULTIPLIER;
    }
}
