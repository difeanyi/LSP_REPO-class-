package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * The transform stage: applies the payroll rules to employees.
 *
 * <p>All of the pay policy lives here — the overtime threshold and multiplier,
 * the departmental bonus, and the rounding of the final figure — so that a rule
 * change touches one class. The pay band and employment status rules belong to
 * {@link PayLevel} and {@link EmploymentStatus}, which this class asks rather
 * than duplicating.
 *
 * <p>Order matters: overtime is applied first, then the departmental bonus, and
 * the result is rounded only once, at the end.
 *
 * @author David Ifeanyi
 */
public class PayrollTransformer {

    private static final BigDecimal OVERTIME_THRESHOLD_HOURS = new BigDecimal("40");
    private static final BigDecimal OVERTIME_MULTIPLIER = new BigDecimal("1.5");
    private static final BigDecimal BONUS_MULTIPLIER = new BigDecimal("1.05");
    private static final String BONUS_DEPARTMENT = "IT";
    private static final int CURRENCY_SCALE = 2;

    /**
     * Transforms every employee in the given list, preserving their order.
     */
    public List<PayrollRecord> transformAll(List<Employee> employees) {
        List<PayrollRecord> records = new ArrayList<>();
        for (Employee employee : employees) {
            records.add(transform(employee));
        }
        return records;
    }

    /**
     * Computes the gross pay, pay band, and employment status for one employee.
     */
    public PayrollRecord transform(Employee employee) {
        BigDecimal grossPay = calculateGrossPay(employee);

        return new PayrollRecord(
                employee,
                grossPay,
                PayLevel.forGrossPay(grossPay),
                EmploymentStatus.forHours(employee.getHoursWorked()));
    }

    private BigDecimal calculateGrossPay(Employee employee) {
        BigDecimal hours = BigDecimal.valueOf(employee.getHoursWorked());
        BigDecimal rate = BigDecimal.valueOf(employee.getHourlyRate());

        BigDecimal pay = applyOvertime(hours, rate);
        pay = applyDepartmentBonus(pay, employee.getDepartment());

        return pay.setScale(CURRENCY_SCALE, RoundingMode.HALF_UP);
    }

    private BigDecimal applyOvertime(BigDecimal hours, BigDecimal rate) {
        if (hours.compareTo(OVERTIME_THRESHOLD_HOURS) <= 0) {
            return hours.multiply(rate);
        }

        BigDecimal regularPay = OVERTIME_THRESHOLD_HOURS.multiply(rate);
        BigDecimal overtimeHours = hours.subtract(OVERTIME_THRESHOLD_HOURS);
        BigDecimal overtimePay = overtimeHours.multiply(rate).multiply(OVERTIME_MULTIPLIER);

        return regularPay.add(overtimePay);
    }

    private BigDecimal applyDepartmentBonus(BigDecimal pay, String department) {
        if (BONUS_DEPARTMENT.equals(department)) {
            return pay.multiply(BONUS_MULTIPLIER);
        }
        return pay;
    }
}
