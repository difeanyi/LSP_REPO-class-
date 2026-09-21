package org.howard.edu.lsp.assignment3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The extract stage: reads the input file and produces the employees it
 * contains.
 *
 * <p>This class owns the file and the read loop. It delegates every decision
 * about a line's contents to an {@link EmployeeCsvParser}, and reports each
 * line it reads and each line it could not use to the {@link ETLReport}.
 *
 * @author David Ifeanyi
 */
public class EmployeeCsvExtractor {

    private final String inputPath;
    private final EmployeeCsvParser parser;
    private final ETLReport report;

    public EmployeeCsvExtractor(String inputPath, EmployeeCsvParser parser, ETLReport report) {
        this.inputPath = inputPath;
        this.parser = parser;
        this.report = report;
    }

    /**
     * Reads the input file, skipping its header row, and returns every employee
     * that could be parsed from it.
     *
     * @throws IOException if the input file cannot be read
     */
    public List<Employee> extract() throws IOException {
        List<Employee> employees = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                report.recordRead();

                Optional<Employee> employee = parser.parse(line);
                if (employee.isPresent()) {
                    employees.add(employee.get());
                } else {
                    report.recordSkipped();
                }
            }
        }

        return employees;
    }
}
