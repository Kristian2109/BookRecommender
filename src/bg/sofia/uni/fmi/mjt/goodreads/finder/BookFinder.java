package bg.sofia.uni.fmi.mjt.goodreads.finder;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BookFinder implements BookFinderAPI {
    private final Set<Book> books;
    private final TextTokenizer tokenizer;

    public BookFinder(Set<Book> books, TextTokenizer tokenizer) {
        this.books = books;
        this.tokenizer = tokenizer;
    }

    public Set<Book> allBooks() {
        return Collections.unmodifiableSet(books);
    }

    @Override
    public List<Book> searchByAuthor(String authorName) {
        return books.stream()
                .filter(book -> book.author().equals(authorName))
                .toList();
    }

    @Override
    public Set<String> allGenres() {
        return books.stream()
                .flatMap(book -> book.genres().stream())
                .collect(Collectors.toSet());
    }

    @Override
    public List<Book> searchByGenres(Set<String> genres, MatchOption option) {
        return books.stream()
                .filter(book -> {
                    Set<String> genresSet = new HashSet<>(book.genres());
                    if (option == MatchOption.MATCH_ALL) {
                        return genresSet.containsAll(genres);
                    } else if (option == MatchOption.MATCH_ANY) {
                        genresSet.retainAll(genres);
                        return genresSet.size() != 0;
                    }
                    return false;
                })
                .toList();
    }

    @Override
    public List<Book> searchByKeywords(Set<String> keywords, MatchOption option) {
        return books.stream()
                .filter(book -> {
                    Set<String> titleWords = new HashSet<>(tokenizer.tokenize(book.title()));
                    Set<String> descriptionWords = new HashSet<>(tokenizer.tokenize(book.description()));

                    if (option == MatchOption.MATCH_ALL) {
                        return titleWords.containsAll(keywords) || descriptionWords.containsAll(keywords);
                    } else if (option == MatchOption.MATCH_ANY) {
                        titleWords.retainAll(keywords);
                        descriptionWords.retainAll(keywords);
                        return titleWords.size() != 0 || descriptionWords.size() != 0;
                    }
                    return false;
                })
                .toList();
    }
    
}
