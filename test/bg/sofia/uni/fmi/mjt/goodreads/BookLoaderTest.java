package bg.sofia.uni.fmi.mjt.goodreads;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookLoaderTest {

    @Test
    void load() {
        String csvData =
            "N,Book,Author,Description,Genres,Avg_Rating,Num_Ratings,URL" + System.lineSeparator() +
                "0,To Kill a Mockingbird,Harper Lee,The unforgettable novel,\"['Classics', 'Fiction']\"," +
                "4.27,5691311,https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird" +
                System.lineSeparator() +
                "1,1984,George Orwell,Totalitarian regime and surveillance,\"['Fiction', 'Dystopian']\"," +
                "4.6,276834,https://example.com/1984" + System.lineSeparator() +
                "2,Pride and Prejudice,Jane Austen,Romantic comedy about manners,\"['Fiction', 'Romance']\"," +
                "4.4,214896,https://example.com/pride-and-prejudice" + System.lineSeparator() +
                "3,The Hobbit,J.R.R. Tolkien,Fantasy adventure,\"['Fiction', 'Fantasy']\"," +
                "4.7,387654,https://example.com/the-hobbit" + System.lineSeparator();

        Set<Book> expectedBooks = Set.of(
            new Book("0", "To Kill a Mockingbird", "Harper Lee", "The unforgettable novel",
                List.of("Classics", "Fiction"),
                4.27, 5691311, "https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird"),

            new Book("1", "1984", "George Orwell", "Totalitarian regime and surveillance",
                List.of("Fiction", "Dystopian"), 4.6, 276834, "https://example.com/1984"),

            new Book("2", "Pride and Prejudice", "Jane Austen", "Romantic comedy about manners",
                List.of("Fiction", "Romance"), 4.4, 214896, "https://example.com/pride-and-prejudice"),

            new Book("3", "The Hobbit", "J.R.R. Tolkien", "Fantasy adventure",
                List.of("Fiction", "Fantasy"), 4.7, 387654, "https://example.com/the-hobbit")
        );

        Reader reader = new StringReader(csvData);
        Set<Book> actualBooks = BookLoader.load(reader);

        assertEquals(expectedBooks, actualBooks, "Books aren't equal");
    }
}