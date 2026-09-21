package org.howard.edu.lsp.assignment3;

import java.util.Optional;

/**
 * Turns a single line of the input file into an {@link Employee}.
 *
 * <p>Every rule for rejecting a row lives here: blank lines, wrong field
 * counts, non-numeric values, and negative hours or rates. An unusable row
 * comes back as an empty {@link Optional}, which states the outcome more
 * clearly than returning {@code null} would.
 *
 * <p>This class touches no files, so the rejection rules can be exercised
 * without any input on disk.
 *
 * @author David Ifeanyi
 */
public class EmployeeCsvParser {

    private static final int EXPECTED_FIELD_COUNT = 5;

    /**
     * Parses one data line, or returns an empty {@code Optional} if the line
     * cannot be used.
     */
    public Optional<Employee> parse(String line) {
        if (line.trim().isEmpty()) {
            return Optional.empty();
        }

        String[] fields = line.split(",", -1);
        if (fields.length != EXPECTED_FIELD_COUNT) {
            return Optional.empty();
        }

        int employeeId;
        try {
            employeeId = Integer.parseInt(fields[0].trim());
        } catch (NumberFormatException e) {
            return Optional.empty();
        }

        double hoursWorked;
        double hourlyRate;
        try {
            hoursWorked = Double.parseDouble(fields[3].trim());
            hourlyRate = Double.parseDouble(fields[4].trim());
        } catch (NumberFormatException e) {
            return Optional.empty();
        }

        if (hoursWorked < 0 || hourlyRate < 0) {
            return Optional.empty();
        }

        String name = fields[1].trim().toUpperCase();
        String department = fields[2].trim();

        return Optional.of(
                new Employee(employeeId, name, department, hoursWorked, hourlyRate));
    }
}
