package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;

/**
 * An employee together with the payroll values computed for them.
 *
 * <p>This is the result of the transform stage and the input to the load stage.
 * It holds a reference to the original {@link Employee} rather than copying its
 * fields, so the employee's data has one home. Like {@code Employee}, it is
 * immutable and knows nothing about the output format.
 *
 * @author David Ifeanyi
 */
public class PayrollRecord {

    private final Employee employee;
    private final BigDecimal grossPay;
    private final PayLevel payLevel;
    private final EmploymentStatus employmentStatus;

    public PayrollRecord(Employee employee, BigDecimal grossPay,
                         PayLevel payLevel, EmploymentStatus employmentStatus) {
        this.employee = employee;
        this.grossPay = grossPay;
        this.payLevel = payLevel;
        this.employmentStatus = employmentStatus;
    }

    public Employee getEmployee() {
        return employee;
    }

    public BigDecimal getGrossPay() {
        return grossPay;
    }

    public PayLevel getPayLevel() {
        return payLevel;
    }

    public EmploymentStatus getEmploymentStatus() {
        return employmentStatus;
    }
}
