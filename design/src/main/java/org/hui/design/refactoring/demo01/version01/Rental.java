package org.hui.design.refactoring.demo01.version01;

/**
 * Rental（租凭）.
 * Rental 表示某个顾客租了一部影片.
 */
public class Rental {
    private Movie movie;
    private int daysRented;

    public Rental(Movie movie, int dayRented) {
        this.movie = movie;
        this.daysRented = dayRented;
    }

    public Movie getMovie() {
        return movie;
    }

    public int getDaysRented() {
        return daysRented;
    }
}
