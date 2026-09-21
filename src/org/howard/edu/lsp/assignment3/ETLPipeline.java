package org.howard.edu.lsp.assignment3;

import java.io.IOException;
import java.util.List;

/**
 * Entry point for the employee payroll ETL pipeline.
 *
 * <p>This class does no reading, no arithmetic, and no formatting. It builds
 * the three stages, runs them in order, and reports the result, so the sequence
 * of the pipeline is visible in one short method.
 *
 * @author David Ifeanyi
 */
public class ETLPipeline {

    private static final String INPUT_PATH = "data/employees.csv";
    private static final String OUTPUT_PATH = "data/transformed_employees.csv";

    public static void main(String[] args) {
        ETLReport report = new ETLReport();

        EmployeeCsvExtractor extractor =
                new EmployeeCsvExtractor(INPUT_PATH, new EmployeeCsvParser(), report);
        PayrollTransformer transformer = new PayrollTransformer();
        PayrollCsvWriter writer = new PayrollCsvWriter(OUTPUT_PATH, report);

        try {
            List<Employee> employees = extractor.extract();
            List<PayrollRecord> records = transformer.transformAll(employees);
            writer.write(records);
        } catch (IOException e) {
            System.out.println("Error processing files: " + e.getMessage());
            return;
        }

        report.printSummary(OUTPUT_PATH);
    }
}
