package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;

/**
 * The pay band an employee falls into, based on gross pay.
 *
 * <p>Each constant carries the label that belongs in the output file, and the
 * band boundaries live here rather than in the transformer, so that adding or
 * moving a band is a change to this one type.
 *
 * @author David Ifeanyi
 */
public enum PayLevel {

    LOW("Low"),
    STANDARD("Standard"),
    HIGH("High"),
    EXECUTIVE("Executive");

    private static final BigDecimal STANDARD_FLOOR = new BigDecimal("500.00");
    private static final BigDecimal HIGH_FLOOR = new BigDecimal("1000.00");
    private static final BigDecimal EXECUTIVE_FLOOR = new BigDecimal("2000.00");

    private final String label;

    PayLevel(String label) {
        this.label = label;
    }

    /**
     * Returns the band that the given gross pay falls into.
     */
    public static PayLevel forGrossPay(BigDecimal grossPay) {
        if (grossPay.compareTo(STANDARD_FLOOR) < 0) {
            return LOW;
        } else if (grossPay.compareTo(HIGH_FLOOR) < 0) {
            return STANDARD;
        } else if (grossPay.compareTo(EXECUTIVE_FLOOR) < 0) {
            return HIGH;
        } else {
            return EXECUTIVE;
        }
    }

    /**
     * Returns the label used in the output file, which differs from the
     * constant name.
     */
    public String getLabel() {
        return label;
    }
}
