package org.howard.edu.lsp.assignment3;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * The load stage: writes payroll records to the output file.
 *
 * <p>This class is the only place that knows the output format — the header,
 * the column order, and how each value is rendered as text. Nothing upstream
 * has to know that the destination is a CSV file at all.
 *
 * @author David Ifeanyi
 */
public class PayrollCsvWriter {

    private static final String HEADER =
            "EmployeeID,Name,Department,HoursWorked,HourlyRate,GrossPay,PayLevel,EmploymentStatus";

    private final String outputPath;
    private final ETLReport report;

    public PayrollCsvWriter(String outputPath, ETLReport report) {
        this.outputPath = outputPath;
        this.report = report;
    }

    /**
     * Writes the header row followed by one row per record, reporting each row
     * written to the {@link ETLReport}.
     *
     * @throws IOException if the output file cannot be written
     */
    public void write(List<PayrollRecord> records) throws IOException {
        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write(HEADER);
            writer.write("\n");

            for (PayrollRecord record : records) {
                writer.write(toCsvRow(record));
                writer.write("\n");
                report.recordTransformed();
            }
        }
    }

    private String toCsvRow(PayrollRecord record) {
        Employee employee = record.getEmployee();

        return String.join(",",
                String.valueOf(employee.getEmployeeId()),
                employee.getName(),
                employee.getDepartment(),
                String.format("%.2f", employee.getHoursWorked()),
                String.format("%.2f", employee.getHourlyRate()),
                record.getGrossPay().toPlainString(),
                record.getPayLevel().getLabel(),
                record.getEmploymentStatus().getLabel());
    }
}
