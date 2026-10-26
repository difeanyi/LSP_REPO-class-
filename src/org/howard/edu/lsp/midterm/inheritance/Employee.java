package org.howard.edu.lsp.midterm.inheritance;

/**
 * Abstract base class for all employee types. Stores the hours worked and
 * hourly rate common to every employee and declares {@link #calculatePay()},
 * which each subclass overrides with its own pay rules. This replaces the
 * conditional type checks in the original EmployeePayCalculator with
 * polymorphism.
 *
 * @author David Ifeanyi
 */
public abstract class Employee {

    private final double hoursWorked;
    private final double hourlyRate;

    /**
     * Creates an employee with the given hours and rate.
     *
     * @param hoursWorked the number of hours worked in the week (nonnegative)
     * @param hourlyRate  the hourly pay rate (nonnegative)
     */
    public Employee(double hoursWorked, double hourlyRate) {
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    /**
     * Returns the number of hours worked.
     *
     * @return the hours worked
     */
    public double getHoursWorked() {
        return hoursWorked;
    }

    /**
     * Returns the hourly pay rate.
     *
     * @return the hourly rate
     */
    public double getHourlyRate() {
        return hourlyRate;
    }

    /**
     * Calculates this employee's pay for the week.
     *
     * @return the pay amount
     */
    public abstract double calculatePay();
}
