package org.howard.edu.lsp.midterm.inheritance;

/**
 * A part-time employee who receives straight hourly pay for all hours
 * worked, with no overtime.
 *
 * @author David Ifeanyi
 */
public class PartTimeEmployee extends Employee {

    /**
     * Creates a part-time employee.
     *
     * @param hoursWorked the number of hours worked in the week (nonnegative)
     * @param hourlyRate  the hourly pay rate (nonnegative)
     */
    public PartTimeEmployee(double hoursWorked, double hourlyRate) {
        super(hoursWorked, hourlyRate);
    }

    /**
     * Calculates straight hourly pay: hours worked times hourly rate.
     *
     * @return the pay amount
     */
    @Override
    public double calculatePay() {
        return getHoursWorked() * getHourlyRate();
    }
}
