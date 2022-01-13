package org.hui.java.tutorials;

/**
 * Comparable Demo.
 * @author Hui.Liu
 * @since 2022-01-13 22:01
 */
public class Name implements Comparable<Name> {
    private final String firstName, lastName;

    public Name(String firstName, String lastName) {
        if (firstName == null || lastName == null) {
            throw new NullPointerException();
        }
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String firstName() {
        return firstName;
    }
    public String lastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof Name)) {
            return false;
        }
        Name n = (Name) o;
        return n.firstName.equals(firstName) && n.lastName.equals(lastName);
    }

    @Override
    public int hashCode() {
        return 31 * firstName.hashCode() + lastName.hashCode();
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    @Override
    public int compareTo(Name n) {
        int lastCmp = lastName.compareTo(n.lastName);
        return (lastCmp != 0 ? lastCmp : firstName.compareTo(n.firstName));
    }
}
