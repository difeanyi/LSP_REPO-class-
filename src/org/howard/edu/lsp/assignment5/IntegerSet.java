package org.howard.edu.lsp.assignment5;

import java.util.Objects;
import java.util.TreeSet;

/**
 * Represents a mathematical set of integers. A set never contains duplicate
 * values, and its elements are always kept in ascending numerical order.
 *
 * <p>Every operation that returns an {@code IntegerSet} produces a new,
 * independent object and never modifies either input set.</p>
 *
 * @author David Ifeanyi
 */
public class IntegerSet {
    /** Internal storage; a sorted set keeps elements unique and ordered. */
    private final TreeSet<Integer> set = new TreeSet<>();

    /**
     * Constructs an empty integer set.
     */
    public IntegerSet() {
    }

    /**
     * Removes all elements from this set, leaving it empty.
     */
    public void clear() {
        set.clear();
    }

    /**
     * Returns the number of elements in this set.
     *
     * @return the number of unique elements in this set
     */
    public int length() {
        return set.size();
    }

    /**
     * Determines whether this set contains exactly the same elements as
     * another set, regardless of insertion order.
     *
     * @param b the set to compare against
     * @return {@code true} if both sets contain the same elements;
     *         {@code false} otherwise, including when {@code b} is {@code null}
     */
    public boolean equals(IntegerSet b) {
        if (this == b) {
            return true;
        }
        if (b == null) {
            return false;
        }
        return set.equals(b.set);
    }

    /**
     * Determines whether this set is equal to another object. Two objects are
     * equal only if the other object is an {@code IntegerSet} containing
     * exactly the same elements.
     *
     * @param o the object to compare against
     * @return {@code true} if {@code o} is an equal {@code IntegerSet};
     *         {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof IntegerSet && equals((IntegerSet) o);
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
     *
     * @return the hash code of this set's elements
     */
    @Override
    public int hashCode() {
        return set.hashCode();
    }

    /**
     * Determines whether this set contains the given value.
     *
     * @param value the value to look for
     * @return {@code true} if the value is in this set; {@code false} otherwise
     */
    public boolean contains(int value) {
        return set.contains(value);
    }

    /**
     * Returns the largest element in this set.
     *
     * @return the largest element
     * @throws IllegalStateException if this set is empty
     */
    public int largest() {
        if (set.isEmpty()) {
            throw new IllegalStateException("Cannot get largest element of an empty set");
        }
        return set.last();
    }

    /**
     * Returns the smallest element in this set.
     *
     * @return the smallest element
     * @throws IllegalStateException if this set is empty
     */
    public int smallest() {
        if (set.isEmpty()) {
            throw new IllegalStateException("Cannot get smallest element of an empty set");
        }
        return set.first();
    }

    /**
     * Adds an element to this set. Duplicate values are ignored.
     *
     * @param item the value to add
     */
    public void add(int item) {
        set.add(item);
    }

    /**
     * Removes an element from this set. Has no effect if the value is absent.
     *
     * @param item the value to remove
     */
    public void remove(int item) {
        set.remove(item);
    }

    /**
     * Returns a new set containing every element that is in this set, in
     * {@code b}, or in both.
     *
     * @param b the other set
     * @return a new set representing the union
     * @throws NullPointerException if {@code b} is {@code null}
     */
    public IntegerSet union(IntegerSet b) {
        IntegerSet result = copy();
        result.set.addAll(nonNull(b).set);
        return result;
    }

    /**
     * Returns a new set containing only the elements shared by this set and
     * {@code b}.
     *
     * @param b the other set
     * @return a new set representing the intersection
     * @throws NullPointerException if {@code b} is {@code null}
     */
    public IntegerSet intersect(IntegerSet b) {
        IntegerSet result = copy();
        result.set.retainAll(nonNull(b).set);
        return result;
    }

    /**
     * Returns a new set containing the elements in this set that are not in
     * {@code b}.
     *
     * @param b the other set
     * @return a new set representing this set minus {@code b}
     * @throws NullPointerException if {@code b} is {@code null}
     */
    public IntegerSet diff(IntegerSet b) {
        IntegerSet result = copy();
        result.set.removeAll(nonNull(b).set);
        return result;
    }

    /**
     * Returns a new set containing the elements in {@code b} that are not in
     * this set (that is, {@code b} minus this set).
     *
     * @param b the other set
     * @return a new set representing {@code b} minus this set
     * @throws NullPointerException if {@code b} is {@code null}
     */
    public IntegerSet complement(IntegerSet b) {
        return nonNull(b).diff(this);
    }

    /**
     * Determines whether every element of this set is also in {@code b}.
     * An empty set is a subset of every set.
     *
     * @param b the other set
     * @return {@code true} if this set is a subset of {@code b};
     *         {@code false} otherwise
     * @throws NullPointerException if {@code b} is {@code null}
     */
    public boolean isSubsetOf(IntegerSet b) {
        return nonNull(b).set.containsAll(set);
    }

    /**
     * Determines whether this set and {@code b} share no elements.
     * An empty set is disjoint from every set.
     *
     * @param b the other set
     * @return {@code true} if the sets have no elements in common;
     *         {@code false} otherwise
     * @throws NullPointerException if {@code b} is {@code null}
     */
    public boolean isDisjoint(IntegerSet b) {
        for (int value : nonNull(b).set) {
            if (set.contains(value)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a new set containing the elements that occur in exactly one of
     * this set and {@code b}.
     *
     * @param b the other set
     * @return a new set representing the symmetric difference
     * @throws NullPointerException if {@code b} is {@code null}
     */
    public IntegerSet symmetricDiff(IntegerSet b) {
        IntegerSet result = union(b);
        result.set.removeAll(intersect(b).set);
        return result;
    }

    /**
     * Determines whether this set has no elements.
     *
     * @return {@code true} if this set is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /**
     * Returns the elements of this set in ascending order, separated by a
     * comma and one space, inside square brackets (for example,
     * {@code [-3, 0, 8, 15]}). An empty set returns {@code []}.
     *
     * @return the string representation of this set
     */
    @Override
    public String toString() {
        return set.toString();
    }

    /**
     * Returns a new set containing the same elements as this set.
     *
     * @return an independent copy of this set
     */
    private IntegerSet copy() {
        IntegerSet result = new IntegerSet();
        result.set.addAll(set);
        return result;
    }

    /**
     * Verifies that an operand set is not {@code null}.
     *
     * @param b the set to check
     * @return {@code b}, if it is not {@code null}
     * @throws NullPointerException if {@code b} is {@code null}
     */
    private static IntegerSet nonNull(IntegerSet b) {
        return Objects.requireNonNull(b, "set argument must not be null");
    }
}
