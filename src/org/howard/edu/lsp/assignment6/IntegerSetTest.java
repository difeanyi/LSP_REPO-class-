package org.howard.edu.lsp.assignment6;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Assignment 6: JUnit 5 tests for IntegerSet.
 *
 * Complete every TODO. Do not remove, rename, or disable test methods.
 * Each test must contain meaningful assertions and run independently.
 */
public class IntegerSetTest {

    /**
     * Builds a new IntegerSet containing the given values.
     *
     * @param values the values to add
     * @return a new set holding those values
     */
    private static IntegerSet setOf(int... values) {
        IntegerSet s = new IntegerSet();
        for (int v : values) {
            s.add(v);
        }
        return s;
    }

    // ===== clear() =====

    /**
     * Create a populated set; call clear(); assert that it is empty and its length is zero.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testClearClearsPopulatedSet() {
        // Arrange
        IntegerSet s = setOf(1, 2, 3);
        // Act
        s.clear();
        // Assert
        assertTrue(s.isEmpty());
        assertEquals(0, s.length());
        assertFalse(s.contains(1));
        assertEquals("[]", s.toString());
    }

    /**
     * Clear an already empty set; verify it remains empty.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testClearClearsEmptySet() {
        // Arrange
        IntegerSet s = new IntegerSet();
        // Act
        s.clear();
        // Assert
        assertTrue(s.isEmpty());
        assertEquals(0, s.length());
        assertEquals("[]", s.toString());
    }

    // ===== length() =====

    /**
     * Add repeated and unique integers; assert the number of distinct elements.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testLengthCountsUniqueElements() {
        // Arrange
        IntegerSet s = setOf(5, 5, 7, 7, 7, -2, 0);
        // Act
        int length = s.length();
        // Assert
        assertEquals(4, length);
    }

    /**
     * Verify a new empty set has length zero.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testLengthEmptySetLength() {
        // Arrange
        IntegerSet s = new IntegerSet();
        // Act
        int length = s.length();
        // Assert
        assertEquals(0, length);
    }

    // ===== equals() =====

    /**
     * Construct sets with identical elements in different insertion orders; assert equality.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testEqualsIgnoresInsertionOrder() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(3, 1, 2);
        // Act & Assert
        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
    }

    /**
     * Construct sets of equal length with different elements; assert inequality.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testEqualsDetectsDifferentContents() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(1, 2, 4);
        // Act & Assert
        assertEquals(a.length(), b.length());
        assertFalse(a.equals(b));
        assertFalse(b.equals(a));
    }

    /**
     * Verify two separately constructed empty sets compare equal.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testEqualsEmptySetsEqual() {
        // Arrange
        IntegerSet a = new IntegerSet();
        IntegerSet b = new IntegerSet();
        // Act & Assert
        assertNotSame(a, b);
        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
    }

    /**
     * Verify a set equals itself and differs from a set of another size.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testEqualsSameObjectAndDifferentSizes() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet bigger = setOf(1, 2, 3, 4);
        // Act & Assert
        assertTrue(a.equals(a));
        assertFalse(a.equals(bigger));
        assertFalse(bigger.equals(a));
    }

    // ===== contains() =====

    /**
     * Verify contains() is true for a present element and false for an absent one.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testContainsPresentAndAbsent() {
        // Arrange
        IntegerSet s = setOf(4, -9, 12);
        // Act & Assert
        assertTrue(s.contains(4));
        assertTrue(s.contains(-9));
        assertFalse(s.contains(5));
        assertFalse(s.contains(9));
    }

    /**
     * Verify an empty set does not contain an arbitrary integer.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testContainsEmptySetContainsNothing() {
        // Arrange
        IntegerSet s = new IntegerSet();
        // Act & Assert
        assertFalse(s.contains(0));
        assertFalse(s.contains(42));
        assertFalse(s.contains(-1));
    }

    // ===== largest() =====

    /**
     * Verify largest() returns the maximum across negative, zero, and positive values.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testLargestNegativeAndPositiveValues() {
        // Arrange
        IntegerSet mixed = setOf(-10, 0, 25, 7);
        IntegerSet allNegative = setOf(-8, -3, -15);
        // Act & Assert
        assertEquals(25, mixed.largest());
        assertEquals(-3, allNegative.largest());
    }

    /**
     * Use assertThrows() to verify IllegalStateException for an empty set.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testLargestEmptySetThrowsException() {
        // Arrange
        IntegerSet s = new IntegerSet();
        // Act & Assert
        assertThrows(IllegalStateException.class, () -> s.largest());
    }

    // ===== smallest() =====

    /**
     * Verify smallest() returns the minimum across negative, zero, and positive values.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testSmallestNegativeAndPositiveValues() {
        // Arrange
        IntegerSet mixed = setOf(-10, 0, 25, 7);
        IntegerSet allPositive = setOf(8, 3, 15);
        // Act & Assert
        assertEquals(-10, mixed.smallest());
        assertEquals(3, allPositive.smallest());
    }

    /**
     * Use assertThrows() to verify IllegalStateException for an empty set.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testSmallestEmptySetThrowsException() {
        // Arrange
        IntegerSet s = new IntegerSet();
        // Act & Assert
        assertThrows(IllegalStateException.class, () -> s.smallest());
    }

    // ===== add() =====

    /**
     * Add the same integer repeatedly; verify only one copy exists.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testAddIgnoresDuplicates() {
        // Arrange
        IntegerSet s = new IntegerSet();
        // Act
        s.add(6);
        s.add(6);
        s.add(6);
        // Assert
        assertEquals(1, s.length());
        assertTrue(s.contains(6));
        assertEquals("[6]", s.toString());
    }

    /**
     * Add Integer.MIN_VALUE, Integer.MAX_VALUE, and zero; verify all are represented.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testAddHandlesIntegerBoundaries() {
        // Arrange
        IntegerSet s = new IntegerSet();
        // Act
        s.add(Integer.MAX_VALUE);
        s.add(0);
        s.add(Integer.MIN_VALUE);
        // Assert
        assertEquals(3, s.length());
        assertTrue(s.contains(Integer.MIN_VALUE));
        assertTrue(s.contains(0));
        assertTrue(s.contains(Integer.MAX_VALUE));
        assertEquals(Integer.MIN_VALUE, s.smallest());
        assertEquals(Integer.MAX_VALUE, s.largest());
        assertEquals("[-2147483648, 0, 2147483647]", s.toString());
    }

    // ===== remove() =====

    /**
     * Remove a present element; verify it is absent and others remain.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testRemoveRemovesExistingElement() {
        // Arrange
        IntegerSet s = setOf(1, 2, 3);
        // Act
        s.remove(2);
        // Assert
        assertFalse(s.contains(2));
        assertTrue(s.contains(1));
        assertTrue(s.contains(3));
        assertEquals(2, s.length());
        assertEquals("[1, 3]", s.toString());
    }

    /**
     * Remove an absent element; verify the set is unchanged.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testRemoveIgnoresAbsentElement() {
        // Arrange
        IntegerSet s = setOf(1, 2, 3);
        // Act
        s.remove(99);
        // Assert
        assertEquals(3, s.length());
        assertTrue(s.equals(setOf(1, 2, 3)));
        assertEquals("[1, 2, 3]", s.toString());
    }

    // ===== union() =====

    /**
     * Verify union() contains all distinct elements from two overlapping sets.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testUnionOverlappingSets() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(3, 4, 5);
        // Act
        IntegerSet result = a.union(b);
        // Assert
        assertEquals("[1, 2, 3, 4, 5]", result.toString());
        assertEquals(5, result.length());
    }

    /**
     * Verify union with an empty set and with the same object.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testUnionEmptyAndSameOperand() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet empty = new IntegerSet();
        // Act
        IntegerSet withEmpty = a.union(empty);
        IntegerSet emptyWithA = empty.union(a);
        IntegerSet withSelf = a.union(a);
        // Assert
        assertEquals("[1, 2, 3]", withEmpty.toString());
        assertEquals("[1, 2, 3]", emptyWithA.toString());
        assertEquals("[1, 2, 3]", withSelf.toString());
        assertNotSame(a, withSelf);
        assertEquals("[1, 2, 3]", a.toString());
        assertTrue(empty.isEmpty());
    }

    /**
     * Verify both inputs remain unchanged; modify the result and verify the inputs are still unchanged.
     * Assignment 5 equivalent: 3 point(s).
     */
    @Test
    public void testUnionDoesNotMutateInputs() {
        // Arrange
        IntegerSet a = setOf(1, 2);
        IntegerSet b = setOf(2, 3);
        // Act
        IntegerSet result = a.union(b);
        // Assert - inputs unchanged by the operation
        assertEquals("[1, 2]", a.toString());
        assertEquals("[2, 3]", b.toString());
        assertNotSame(a, result);
        assertNotSame(b, result);
        // Act - modify the result
        result.add(100);
        result.remove(1);
        result.remove(2);
        // Assert - inputs still unchanged
        assertEquals("[3, 100]", result.toString());
        assertEquals("[1, 2]", a.toString());
        assertEquals("[2, 3]", b.toString());
    }

    // ===== intersect() =====

    /**
     * Verify intersection contains exactly the shared elements.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIntersectOverlappingSets() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3, 4);
        IntegerSet b = setOf(3, 4, 5, 6);
        // Act
        IntegerSet result = a.intersect(b);
        // Assert
        assertEquals("[3, 4]", result.toString());
        assertEquals(2, result.length());
    }

    /**
     * Verify disjoint intersection is empty and self-intersection is unchanged.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIntersectDisjointAndSameOperand() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet disjoint = setOf(7, 8, 9);
        // Act
        IntegerSet none = a.intersect(disjoint);
        IntegerSet self = a.intersect(a);
        // Assert
        assertTrue(none.isEmpty());
        assertEquals("[]", none.toString());
        assertEquals("[1, 2, 3]", self.toString());
        assertNotSame(a, self);
        assertEquals("[1, 2, 3]", a.toString());
    }

    /**
     * Modify the intersection result and verify both input sets remain unchanged.
     * Assignment 5 equivalent: 3 point(s).
     */
    @Test
    public void testIntersectIndependentResult() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(2, 3, 4);
        IntegerSet result = a.intersect(b);
        assertEquals("[2, 3]", result.toString());
        // Act
        result.add(50);
        result.remove(2);
        // Assert
        assertEquals("[3, 50]", result.toString());
        assertEquals("[1, 2, 3]", a.toString());
        assertEquals("[2, 3, 4]", b.toString());
    }

    // ===== diff() =====

    /**
     * Verify A.diff(B) returns elements in A but not B, and distinguish direction.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testDiffDirectionMatters() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3, 4);
        IntegerSet b = setOf(3, 4, 5);
        // Act
        IntegerSet aMinusB = a.diff(b);
        IntegerSet bMinusA = b.diff(a);
        // Assert
        assertEquals("[1, 2]", aMinusB.toString());
        assertEquals("[5]", bMinusA.toString());
        assertFalse(aMinusB.equals(bMinusA));
    }

    /**
     * Verify difference with an empty set and difference with itself.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testDiffEmptyAndSameOperand() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet empty = new IntegerSet();
        // Act
        IntegerSet aMinusEmpty = a.diff(empty);
        IntegerSet emptyMinusA = empty.diff(a);
        IntegerSet aMinusA = a.diff(a);
        // Assert
        assertEquals("[1, 2, 3]", aMinusEmpty.toString());
        assertTrue(emptyMinusA.isEmpty());
        assertTrue(aMinusA.isEmpty());
        assertEquals("[1, 2, 3]", a.toString());
    }

    /**
     * Modify the difference result and verify neither input changes.
     * Assignment 5 equivalent: 3 point(s).
     */
    @Test
    public void testDiffIndependentResult() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(3, 4);
        IntegerSet result = a.diff(b);
        assertEquals("[1, 2]", result.toString());
        assertEquals("[1, 2, 3]", a.toString());
        assertEquals("[3, 4]", b.toString());
        // Act
        result.add(4);
        result.remove(1);
        // Assert
        assertNotSame(a, result);
        assertEquals("[2, 4]", result.toString());
        assertEquals("[1, 2, 3]", a.toString());
        assertEquals("[3, 4]", b.toString());
    }

    // ===== complement() =====

    /**
     * Verify A.complement(B) returns elements in B but not A.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testComplementReverseDifference() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(2, 3, 4, 5);
        // Act
        IntegerSet result = a.complement(b);
        // Assert
        assertEquals("[4, 5]", result.toString());
        assertTrue(result.equals(b.diff(a)));
        assertFalse(result.equals(a.diff(b)));
    }

    /**
     * Verify complement with an empty receiver and with an empty argument.
     * Assignment 5 equivalent: 1 point(s).
     */
    @Test
    public void testComplementEmptyOperands() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet empty = new IntegerSet();
        // Act
        IntegerSet emptyReceiver = empty.complement(a);
        IntegerSet emptyArgument = a.complement(empty);
        // Assert
        assertEquals("[1, 2, 3]", emptyReceiver.toString());
        assertTrue(emptyArgument.isEmpty());
    }

    /**
     * Modify the complement result and verify neither input changes.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testComplementIndependentResult() {
        // Arrange
        IntegerSet a = setOf(1, 2);
        IntegerSet b = setOf(2, 3, 4);
        IntegerSet result = a.complement(b);
        assertEquals("[3, 4]", result.toString());
        // Act
        result.add(1);
        result.remove(3);
        // Assert
        assertNotSame(b, result);
        assertEquals("[1, 4]", result.toString());
        assertEquals("[1, 2]", a.toString());
        assertEquals("[2, 3, 4]", b.toString());
    }

    // ===== isSubsetOf() =====

    /**
     * Verify a proper subset is recognized.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIsSubsetOfProperSubset() {
        // Arrange
        IntegerSet small = setOf(2, 3);
        IntegerSet big = setOf(1, 2, 3, 4);
        // Act & Assert
        assertTrue(small.isSubsetOf(big));
        assertFalse(big.isSubsetOf(small));
    }

    /**
     * Verify a set with an element missing from the other set is not a subset.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIsSubsetOfNotSubset() {
        // Arrange
        IntegerSet a = setOf(1, 2, 9);
        IntegerSet b = setOf(1, 2, 3, 4);
        // Act & Assert
        assertFalse(a.isSubsetOf(b));
    }

    /**
     * Verify equal sets are subsets of each other.
     * Assignment 5 equivalent: 1 point(s).
     */
    @Test
    public void testIsSubsetOfEqualSets() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(3, 2, 1);
        // Act & Assert
        assertTrue(a.isSubsetOf(b));
        assertTrue(b.isSubsetOf(a));
        assertTrue(a.isSubsetOf(a));
    }

    /**
     * Verify the empty set is a subset of a nonempty set, but not vice versa.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIsSubsetOfEmptyCases() {
        // Arrange
        IntegerSet empty = new IntegerSet();
        IntegerSet nonEmpty = setOf(5);
        // Act & Assert
        assertTrue(empty.isSubsetOf(nonEmpty));
        assertFalse(nonEmpty.isSubsetOf(empty));
        assertTrue(empty.isSubsetOf(new IntegerSet()));
    }

    // ===== isDisjoint() =====

    /**
     * Verify disjoint sets return true.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIsDisjointNoOverlap() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(4, 5, 6);
        // Act & Assert
        assertTrue(a.isDisjoint(b));
        assertTrue(b.isDisjoint(a));
    }

    /**
     * Verify overlapping sets return false.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIsDisjointOverlap() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(3, 4, 5);
        // Act & Assert
        assertFalse(a.isDisjoint(b));
        assertFalse(b.isDisjoint(a));
    }

    /**
     * Verify disjointness with an empty set and with the same nonempty object.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIsDisjointEmptyAndSameOperand() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet empty = new IntegerSet();
        // Act & Assert
        assertTrue(a.isDisjoint(empty));
        assertTrue(empty.isDisjoint(a));
        assertFalse(a.isDisjoint(a));
    }

    // ===== symmetricDiff() =====

    /**
     * Verify symmetric difference contains elements appearing in exactly one set.
     * Assignment 5 equivalent: 3 point(s).
     */
    @Test
    public void testSymmetricDiffPartiallyOverlappingSets() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3, 4);
        IntegerSet b = setOf(3, 4, 5, 6);
        // Act
        IntegerSet ab = a.symmetricDiff(b);
        IntegerSet ba = b.symmetricDiff(a);
        // Assert
        assertEquals("[1, 2, 5, 6]", ab.toString());
        assertTrue(ab.equals(ba));
        assertFalse(ab.contains(3));
        assertFalse(ab.contains(4));
    }

    /**
     * Verify symmetric difference of identical sets is empty.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testSymmetricDiffIdenticalSets() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(3, 2, 1);
        // Act
        IntegerSet result = a.symmetricDiff(b);
        IntegerSet self = a.symmetricDiff(a);
        // Assert
        assertTrue(result.isEmpty());
        assertTrue(self.isEmpty());
        assertEquals("[1, 2, 3]", a.toString());
    }

    /**
     * Verify behavior with an empty set and with disjoint sets.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testSymmetricDiffEmptyAndDisjointSets() {
        // Arrange
        IntegerSet a = setOf(1, 2);
        IntegerSet empty = new IntegerSet();
        IntegerSet disjoint = setOf(8, 9);
        // Act
        IntegerSet withEmpty = a.symmetricDiff(empty);
        IntegerSet emptyWithA = empty.symmetricDiff(a);
        IntegerSet withDisjoint = a.symmetricDiff(disjoint);
        // Assert
        assertEquals("[1, 2]", withEmpty.toString());
        assertEquals("[1, 2]", emptyWithA.toString());
        assertEquals("[1, 2, 8, 9]", withDisjoint.toString());
        assertTrue(withDisjoint.equals(a.union(disjoint)));
    }

    /**
     * Modify the result and verify both input sets remain unchanged.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testSymmetricDiffIndependentResult() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(3, 4);
        IntegerSet result = a.symmetricDiff(b);
        assertEquals("[1, 2, 4]", result.toString());
        // Act
        result.add(3);
        result.remove(1);
        result.remove(4);
        // Assert
        assertNotSame(a, result);
        assertNotSame(b, result);
        assertEquals("[2, 3]", result.toString());
        assertEquals("[1, 2, 3]", a.toString());
        assertEquals("[3, 4]", b.toString());
    }

    // ===== isEmpty() =====

    /**
     * Verify a newly constructed set is empty.
     * Assignment 5 equivalent: 1 point(s).
     */
    @Test
    public void testIsEmptyNewSet() {
        // Arrange
        IntegerSet s = new IntegerSet();
        // Act & Assert
        assertTrue(s.isEmpty());
        assertEquals(0, s.length());
    }

    /**
     * Verify a set with an element is not empty.
     * Assignment 5 equivalent: 1 point(s).
     */
    @Test
    public void testIsEmptyPopulatedSet() {
        // Arrange
        IntegerSet s = new IntegerSet();
        // Act
        s.add(10);
        // Assert
        assertFalse(s.isEmpty());
    }

    /**
     * Clear a populated set and verify it is empty.
     * Assignment 5 equivalent: 1 point(s).
     */
    @Test
    public void testIsEmptyAfterClear() {
        // Arrange
        IntegerSet s = setOf(1, 2, 3);
        assertFalse(s.isEmpty());
        // Act
        s.clear();
        // Assert
        assertTrue(s.isEmpty());
    }

    // ===== toString() =====

    /**
     * Verify elements appear in ascending numeric order regardless of insertion order.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testToStringAscendingOrder() {
        // Arrange
        IntegerSet s = setOf(15, -3, 8, 0, 100, -20);
        // Act
        String text = s.toString();
        // Assert
        assertEquals("[-20, -3, 0, 8, 15, 100]", text);
    }

    /**
     * Verify an empty set formats as [].
     * Assignment 5 equivalent: 1 point(s).
     */
    @Test
    public void testToStringEmptyFormatting() {
        // Arrange
        IntegerSet s = new IntegerSet();
        // Act
        String text = s.toString();
        // Assert
        assertEquals("[]", text);
    }

    /**
     * Verify comma-space formatting, brackets, and duplicate suppression.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testToStringExactSpacingAndDuplicates() {
        // Arrange
        IntegerSet s = setOf(3, 1, 3, 2, 1);
        IntegerSet single = setOf(7, 7);
        // Act
        String text = s.toString();
        String singleText = single.toString();
        // Assert
        assertEquals("[1, 2, 3]", text);
        assertEquals("[7]", singleText);
    }

}
