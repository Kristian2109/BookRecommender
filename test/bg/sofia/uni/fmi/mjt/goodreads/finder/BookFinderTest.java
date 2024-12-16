package bg.sofia.uni.fmi.mjt.goodreads.finder;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BookFinderTest {

    private BookFinder bookFinder;
    private TextTokenizer tokenizer;

    @BeforeEach
    void setUp() {
        tokenizer = Mockito.mock(TextTokenizer.class);
        Mockito.when(tokenizer.tokenize(Mockito.anyString())).thenAnswer(invocation -> invocation.getArgument(0));
        bookFinder = new BookFinder(books, tokenizer);
    }

    @Test
    void allBooks() {
        assertEquals(books, bookFinder.allBooks());
    }

    @Test
    void searchByAuthor() {

        Book pauloCoehloBook  = new Book("9", "The Alchemist", "Paulo Coelho",
                "A philosophical novel about following your dreams and personal legend.",
                List.of("Fiction", "Adventure", "Philosophy"), 4.6, 324789,
                "https://example.com/the-alchemist");

        List<Book> result = bookFinder.searchByAuthor("Paulo Coelho");
        assertEquals(pauloCoehloBook, result.get(0));
        assertEquals(1, result.size());
    }

    @Test
    void allGenres() {
    }

    @Test
    void searchByGenres() {
    }

    @Test
    void searchByKeywords() {
    }

    Set<Book> books = Set.of(
            new Book("1", "The Catcher in the Rye", "J.D. Salinger",
                    "A classic novel about teenage rebellion and angst.",
                    List.of("Fiction", "Classic", "Literature"), 4.2, 124523, "https://example.com/catcher-in-the-rye"),

            new Book("2", "To Kill a Mockingbird", "Harper Lee",
                    "A deeply moving novel about racial injustice in the Deep South.",
                    List.of("Fiction", "Classic", "Drama"), 4.8, 354687, "https://example.com/to-kill-a-mockingbird"),

            new Book("3", "1984", "George Orwell",
                    "A dystopian novel about a totalitarian regime and constant surveillance.",
                    List.of("Fiction", "Dystopian", "Classic"), 4.6, 276834, "https://example.com/1984"),

            new Book("4", "Pride and Prejudice", "Jane Austen",
                    "A romantic comedy about manners, marriage, and morality in 19th century England.",
                    List.of("Fiction", "Romance", "Classic"), 4.4, 214896, "https://example.com/pride-and-prejudice"),

            new Book("5", "The Hobbit", "J.R.R. Tolkien",
                    "A fantasy adventure following the journey of Bilbo Baggins.",
                    List.of("Fiction", "Fantasy", "Adventure"), 4.7, 387654, "https://example.com/the-hobbit"),

            new Book("6", "Sapiens: A Brief History of Humankind", "Yuval Noah Harari",
                    "An exploration of human history from prehistoric times to modern-day.",
                    List.of("Non-fiction", "History", "Anthropology"), 4.5, 173921, "https://example.com/sapiens"),

            new Book("7", "The Great Gatsby", "F. Scott Fitzgerald",
                    "A tragic story of wealth, love, and the American Dream.",
                    List.of("Fiction", "Classic", "Tragedy"), 4.3, 198765, "https://example.com/the-great-gatsby"),

            new Book("8", "Becoming", "Michelle Obama",
                    "A memoir by the former First Lady of the United States.",
                    List.of("Non-fiction", "Biography", "Inspiration"), 4.9, 273567, "https://example.com/becoming"),

            new Book("9", "The Alchemist", "Paulo Coelho",
                    "A philosophical novel about following your dreams and personal legend.",
                    List.of("Fiction", "Adventure", "Philosophy"), 4.6, 324789, "https://example.com/the-alchemist"),

            new Book("10", "Dune", "Frank Herbert",
                    "An epic science fiction novel set in a desert world with political intrigue and adventure.",
                    List.of("Fiction", "Science Fiction", "Adventure"), 4.8, 451278, "https://example.com/dune")
    );

    private static final int PAULO_COEHLO_INDEX = 8;
}