package bg.sofia.uni.fmi.mjt.goodreads.finder;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BookFinderTest {

    private BookFinder bookFinder;

    @BeforeEach
    void setUp() {
        TextTokenizer tokenizer = Mockito.mock(TextTokenizer.class);
        Mockito.when(tokenizer.tokenize(Mockito.anyString()))
                .thenAnswer(invocation -> List.of(invocation.getArgument(0, String.class).split(" ")));
        bookFinder = new BookFinder(TEST_BOOKS, tokenizer);
    }

    @Test
    void allBooks() {
        assertEquals(TEST_BOOKS, bookFinder.allBooks());
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
        Set<String> actualUniqueGenres = bookFinder.allGenres();
        assertEquals(UNIQUE_GENRES, actualUniqueGenres, "Genres are not equal");
    }

    @Test
    void searchByGenresMatchAll() {
        Set<String> keywords = Set.of("Fiction", "Adventure");
        List<Book> fictionAdventureBooks = List.of(
                new Book("5", "The Hobbit", "J.R.R. Tolkien",
                        "A fantasy adventure following the journey of Bilbo Baggins.",
                        List.of("Fiction", "Fantasy", "Adventure"), 4.7, 387654,
                        "https://example.com/the-hobbit"),

                new Book("9", "The Alchemist", "Paulo Coelho",
                        "A philosophical novel about following your dreams and personal legend.",
                        List.of("Fiction", "Adventure", "Philosophy"), 4.6, 324789,
                        "https://example.com/the-alchemist"),

                new Book("10", "Dune", "Frank Herbert",
                        "An epic science fiction novel set in a desert world with political intrigue and adventure",
                        List.of("Fiction", "Science Fiction", "Adventure"), 4.8, 451278,
                        "https://example.com/dune")
        );
        assertBookListsEqual(fictionAdventureBooks, bookFinder.searchByGenres(keywords, MatchOption.MATCH_ALL));

    }

    @Test
    void searchByGenresMatchAny() {
        Set<String> genres = Set.of("Dystopian", "Non-fiction");
        List<Book> booksWithKeywords = List.of(
                new Book("3", "1984", "George Orwell",
                        "A dystopian novel about a totalitarian regime and constant surveillance.",
                        List.of("Fiction", "Dystopian", "Classic"), 4.6, 276834,
                        "https://example.com/1984"),

                new Book("6", "Sapiens: A Brief History of Humankind", "Yuval Noah Harari",
                        "An exploration of human history from prehistoric times to modern-day.",
                        List.of("Non-fiction", "History", "Anthropology"), 4.5, 173921,
                        "https://example.com/sapiens"),

                new Book("8", "Becoming", "Michelle Obama",
                        "A memoir by the former First Lady of the United States.",
                        List.of("Non-fiction", "Biography", "Inspiration"), 4.9, 273567,
                        "https://example.com/becoming")
        );

        List<Book> result = bookFinder.searchByGenres(genres, MatchOption.MATCH_ANY);
        assertBookListsEqual(booksWithKeywords, result);
    }


    @Test
    void searchByKeywordsMatchAll() {
        Set<String> keywords = Set.of("novel", "about", "and");
        List<Book> booksWithKeywords = List.of(
                new Book("1", "The Catcher in the Rye", "J.D. Salinger",
                        "A classic novel about teenage rebellion and angst.",
                        List.of("Fiction", "Classic", "Literature"), 4.2, 124523,
                        "https://example.com/catcher-in-the-rye"),
                new Book("3", "1984", "George Orwell",
                        "A dystopian novel about a totalitarian regime and constant surveillance.",
                        List.of("Fiction", "Dystopian", "Classic"), 4.6, 276834,
                        "https://example.com/1984"),
                new Book("9", "The Alchemist", "Paulo Coelho",
                        "A philosophical novel about following your dreams and personal legend.",
                        List.of("Fiction", "Adventure", "Philosophy"), 4.6, 324789,
                        "https://example.com/the-alchemist")
        );
        assertBookListsEqual(booksWithKeywords, bookFinder.searchByKeywords(keywords, MatchOption.MATCH_ALL));

    }


    @Test
    void searchByKeywordsMatchAny() {
        Set<String> keywords = Set.of("Great", "adventure");
        List<Book> booksWithKeywords = List.of(
                new Book("7", "The Great Gatsby", "F. Scott Fitzgerald",
                        "A tragic story of wealth, love, and the American Dream.",
                        List.of("Fiction", "Classic", "Tragedy"), 4.3, 198765,
                        "https://example.com/the-great-gatsby"),
                new Book("5", "The Hobbit", "J.R.R. Tolkien",
                        "A fantasy adventure following the journey of Bilbo Baggins.",
                        List.of("Fiction", "Fantasy", "Adventure"), 4.7, 387654,
                        "https://example.com/the-hobbit"),
                new Book("10", "Dune", "Frank Herbert",
                        "An epic science fiction novel set in a desert world with political intrigue and adventure",
                        List.of("Fiction", "Science Fiction", "Adventure"), 4.8, 451278,
                        "https://example.com/dune")
                );

        assertBookListsEqual(booksWithKeywords, bookFinder.searchByKeywords(keywords, MatchOption.MATCH_ANY));
    }

    private static final Set<Book> TEST_BOOKS = Set.of(
            new Book("1", "The Catcher in the Rye", "J.D. Salinger",
                    "A classic novel about teenage rebellion and angst.",
                    List.of("Fiction", "Classic", "Literature"), 4.2, 124523,
                    "https://example.com/catcher-in-the-rye"),

            new Book("2", "To Kill a Mockingbird", "Harper Lee",
                    "A deeply moving novel about racial injustice in the Deep South.",
                    List.of("Fiction", "Classic", "Drama"), 4.8, 354687,
                    "https://example.com/to-kill-a-mockingbird"),

            new Book("3", "1984", "George Orwell",
                    "A dystopian novel about a totalitarian regime and constant surveillance.",
                    List.of("Fiction", "Dystopian", "Classic"), 4.6, 276834,
                    "https://example.com/1984"),

            new Book("4", "Pride and Prejudice", "Jane Austen",
                    "A romantic comedy about manners, marriage, and morality in 19th century England.",
                    List.of("Fiction", "Romance", "Classic"), 4.4, 214896,
                    "https://example.com/pride-and-prejudice"),

            new Book("5", "The Hobbit", "J.R.R. Tolkien",
                    "A fantasy adventure following the journey of Bilbo Baggins.",
                    List.of("Fiction", "Fantasy", "Adventure"), 4.7, 387654,
                    "https://example.com/the-hobbit"),

            new Book("6", "Sapiens: A Brief History of Humankind", "Yuval Noah Harari",
                    "An exploration of human history from prehistoric times to modern-day.",
                    List.of("Non-fiction", "History", "Anthropology"), 4.5, 173921,
                    "https://example.com/sapiens"),

            new Book("7", "The Great Gatsby", "F. Scott Fitzgerald",
                    "A tragic story of wealth, love, and the American Dream.",
                    List.of("Fiction", "Classic", "Tragedy"), 4.3, 198765,
                    "https://example.com/the-great-gatsby"),

            new Book("8", "Becoming", "Michelle Obama",
                    "A memoir by the former First Lady of the United States.",
                    List.of("Non-fiction", "Biography", "Inspiration"), 4.9, 273567,
                    "https://example.com/becoming"),

            new Book("9", "The Alchemist", "Paulo Coelho",
                    "A philosophical novel about following your dreams and personal legend.",
                    List.of("Fiction", "Adventure", "Philosophy"), 4.6, 324789,
                    "https://example.com/the-alchemist"),

            new Book("10", "Dune", "Frank Herbert",
                    "An epic science fiction novel set in a desert world with political intrigue and adventure",
                    List.of("Fiction", "Science Fiction", "Adventure"), 4.8, 451278,
                    "https://example.com/dune")
    );

    private static void assertBookListsEqual(List<Book> first, List<Book> second) {
        List<Book> firstSorted = first.stream().sorted(Comparator.comparing(Book::ID)).toList();
        List<Book> secondSorted = second.stream().sorted(Comparator.comparing(Book::ID)).toList();
        assertEquals(firstSorted, secondSorted, "Lists of books are not equal");
    }

    private static final Set<String> UNIQUE_GENRES  = Set.of(
            "Fiction",
            "Classic",
            "Literature",
            "Drama",
            "Dystopian",
            "Romance",
            "Fantasy",
            "Adventure",
            "Non-fiction",
            "History",
            "Anthropology",
            "Tragedy",
            "Biography",
            "Inspiration",
            "Philosophy",
            "Science Fiction"
    );
}