package org.howard.edu.lsp.assignment2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author David Ifeanyi
 */
public class ETLPipeline {

    private static final String INPUT_PATH = "data/employees.csv";
    private static final String OUTPUT_PATH = "data/transformed_employees.csv";

    private static final BigDecimal FORTY = new BigDecimal("40");
    private static final BigDecimal OVERTIME_MULTIPLIER = new BigDecimal("1.5");
    private static final BigDecimal IT_BONUS_MULTIPLIER = new BigDecimal("1.05");
    private static final BigDecimal THIRTY = new BigDecimal("30");

    public static void main(String[] args) {
        int rowsRead = 0;
        int rowsTransformed = 0;
        int rowsSkipped = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_PATH));
             FileWriter writer = new FileWriter(OUTPUT_PATH)) {

            writer.write("EmployeeID,Name,Department,HoursWorked,HourlyRate,GrossPay,PayLevel,EmploymentStatus\n");

            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                rowsRead++;

                String transformedRow = transformRow(line);
                if (transformedRow == null) {
                    rowsSkipped++;
                } else {
                    writer.write(transformedRow);
                    writer.write("\n");
                    rowsTransformed++;
                }
            }

        } catch (IOException e) {
            System.out.println("Error processing files: " + e.getMessage());
            return;
        }

        System.out.println("Rows read: " + rowsRead);
        System.out.println("Rows transformed: " + rowsTransformed);
        System.out.println("Rows skipped: " + rowsSkipped);
        System.out.println("Output file: " + OUTPUT_PATH);
    }

    private static String transformRow(String line) {
        if (line.trim().isEmpty()) {
            return null;
        }

        String[] fields = line.split(",", -1);
        if (fields.length != 5) {
            return null;
        }

        String idField = fields[0].trim();
        String nameField = fields[1].trim().toUpperCase();
        String deptField = fields[2].trim();
        String hoursField = fields[3].trim();
        String rateField = fields[4].trim();

        int employeeId;
        try {
            employeeId = Integer.parseInt(idField);
        } catch (NumberFormatException e) {
            return null;
        }

        double hoursWorked;
        double hourlyRate;
        try {
            hoursWorked = Double.parseDouble(hoursField);
            hourlyRate = Double.parseDouble(rateField);
        } catch (NumberFormatException e) {
            return null;
        }

        if (hoursWorked < 0 || hourlyRate < 0) {
            return null;
        }

        BigDecimal hours = BigDecimal.valueOf(hoursWorked);
        BigDecimal rate = BigDecimal.valueOf(hourlyRate);

        BigDecimal pay;
        if (hours.compareTo(FORTY) <= 0) {
            pay = hours.multiply(rate);
        } else {
            BigDecimal overtimeHours = hours.subtract(FORTY);
            BigDecimal regularPay = FORTY.multiply(rate);
            BigDecimal overtimePay = overtimeHours.multiply(rate).multiply(OVERTIME_MULTIPLIER);
            pay = regularPay.add(overtimePay);
        }

        if (deptField.equals("IT")) {
            pay = pay.multiply(IT_BONUS_MULTIPLIER);
        }

        BigDecimal grossPay = pay.setScale(2, RoundingMode.HALF_UP);

        String payLevel = determinePayLevel(grossPay);
        String employmentStatus = hours.compareTo(THIRTY) < 0 ? "Part-Time" : "Full-Time";

        return String.join(",",
                String.valueOf(employeeId),
                nameField,
                deptField,
                String.format("%.2f", hoursWorked),
                String.format("%.2f", hourlyRate),
                grossPay.toPlainString(),
                payLevel,
                employmentStatus);
    }

    private static String determinePayLevel(BigDecimal grossPay) {
        if (grossPay.compareTo(new BigDecimal("500.00")) < 0) {
            return "Low";
        } else if (grossPay.compareTo(new BigDecimal("1000.00")) < 0) {
            return "Standard";
        } else if (grossPay.compareTo(new BigDecimal("2000.00")) < 0) {
            return "High";
        } else {
            return "Executive";
        }
    }
}
