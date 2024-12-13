package bg.sofia.uni.fmi.mjt.goodreads.book;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookTest {

    @Test
    void createsBookOnValidInput() {
        Book expectedBook = new Book(
                "0",
                "To Kill a Mockingbird",
                "Harper Lee",
                "Description\"To Kill A Mockingbird\"",
                List.of("Classics", "Fiction", "Historical Fiction", "School", "Literature", "Young Adult"),
                4.27,
                5691311,
                "https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird"
        );

        String[] tokens = {
                "0",
                "To Kill a Mockingbird",
                "Harper Lee",
                "Description\"To Kill A Mockingbird\"",
                "['Classics', 'Fiction', 'Historical Fiction', 'School', 'Literature', 'Young Adult']",
                "4.27",
                "5,691,311",
                "https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird"
        };

        assertEquals(Book.of(tokens), expectedBook, "");
    }

    @Test
    void throwOnInvalidCount() {
        String[] tokens = {
                "0",
                "Description\"To Kill A Mockingbird\"",
                "['Classics', 'Fiction', 'Historical Fiction', 'School', 'Literature', 'Young Adult']",
                "4.27",
                "5,691,311",
                "https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird"
        };

        assertThrows(IllegalArgumentException.class, () -> Book.of(tokens));
    }

    @Test
    void throwsOnInvalidRating() {
        String[] tokens = {
                "0",
                "To Kill a Mockingbird",
                "Harper Lee",
                "Description\"To Kill A Mockingbird\"",
                "['Classics', 'Fiction', 'Historical Fiction', 'School', 'Literature', 'Young Adult']",
                "4.27aaa",
                "5,691,311",
                "https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird"
        };

        assertThrows(IllegalArgumentException.class, () -> Book.of(tokens));
    }
}