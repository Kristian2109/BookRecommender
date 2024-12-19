import bg.sofia.uni.fmi.mjt.goodreads.BookLoader;
import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.descriptions.TFIDFSimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        try(FileReader reader = new FileReader("C:\\Users\\krist\\Downloads\\goodreads_data (1).csv");) {
            Set<Book> books = BookLoader.load(reader);
            String stopwords = "a\nan\nthe\nof\nand\nfor\nover\n";
            Reader stopwordsReader = new StringReader(stopwords);
            SimilarityCalculator calculator = new TFIDFSimilarityCalculator(books, new TextTokenizer(stopwordsReader));

            List<Book> simBooks = books.stream().limit(2).toList();

            double similarity = calculator.calculateSimilarity(simBooks.get(0), simBooks.get(1));

            System.out.println(similarity);
        } catch (FileNotFoundException e) {
            System.out.println("Invalid File Name");
        } catch (Exception e) {
            System.out.println("Unknown error");
        }
    }
}
