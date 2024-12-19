package bg.sofia.uni.fmi.mjt.goodreads.recommender;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;

import java.util.Map;

public interface BookRecommenderAPI {

    /**
     * Searches for books that are similar to the provided one.
     *
     * @param originBook the book we should calculate similarity with.
     * @param maxN       - the maximum number of entries returned
     * @return a Map<Book, Double> representing the top maxN the closest books with their similarity to originBook ordered by their similarity score
     * @throws IllegalArgumentException if the originBook is null.
     * @throws IllegalArgumentException if maxN is smaller or equal to 0.
     */
    Map<Book, Double> recommendBooks(Book originBook, int maxN);
}
