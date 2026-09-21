package org.howard.edu.lsp.assignment3;

/**
 * An immutable employee record as it was read from the input file.
 *
 * <p>Hours and rate are kept as primitive doubles, exactly as they were parsed,
 * so that the values written back out are formatted from the same numbers the
 * input supplied. This class knows nothing about CSV, files, or payroll rules.
 *
 * @author David Ifeanyi
 */
public class Employee {

    private final int employeeId;
    private final String name;
    private final String department;
    private final double hoursWorked;
    private final double hourlyRate;

    public Employee(int employeeId, String name, String department,
                    double hoursWorked, double hourlyRate) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }
}
