package org.howard.edu.lsp.midterm.crccards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Maintains a collection of books and coordinates borrowing and returning.
 * Books are stored in a {@link HashMap} keyed by ISBN, which gives fast
 * lookup and makes duplicate ISBNs easy to detect. The Library enforces a
 * limit of three books borrowed at one time and delegates each book's state
 * change to the {@link Book} itself.
 *
 * @author David Ifeanyi
 */
public class Library {

    private static final int MAX_BORROWED = 3;

    private final Map<String, Book> books = new HashMap<>();
    private int borrowedCount = 0;

    /**
     * Adds a book to the library.
     *
     * @param book the book to add
     * @throws IllegalArgumentException if a book with the same ISBN already exists
     */
    public void addBook(Book book) {
        if (books.containsKey(book.getIsbn())) {
            throw new IllegalArgumentException("Duplicate ISBN: " + book.getIsbn());
        }
        books.put(book.getIsbn(), book);
    }

    /**
     * Finds a book by ISBN.
     *
     * @param isbn the ISBN to look up
     * @return the matching Book, or null if no book has that ISBN
     */
    public Book findBook(String isbn) {
        return books.get(isbn);
    }

    /**
     * Borrows the book with the given ISBN.
     *
     * @param isbn the ISBN of the book to borrow
     * @throws IllegalArgumentException if no book has that ISBN
     * @throws IllegalStateException    if the book is already borrowed or the
     *                                  three-book borrowing limit has been reached
     */
    public void borrowBook(String isbn) {
        Book book = requireBook(isbn);
        if (!book.isAvailable()) {
            throw new IllegalStateException("Book " + isbn + " is already borrowed.");
        }
        if (borrowedCount >= MAX_BORROWED) {
            throw new IllegalStateException("Borrowing limit of " + MAX_BORROWED + " reached.");
        }
        book.borrowBook();
        borrowedCount++;
    }

    /**
     * Returns the book with the given ISBN.
     *
     * @param isbn the ISBN of the book to return
     * @throws IllegalArgumentException if no book has that ISBN
     * @throws IllegalStateException    if the book is not currently borrowed
     */
    public void returnBook(String isbn) {
        Book book = requireBook(isbn);
        book.returnBook();
        borrowedCount--;
    }

    /**
     * Returns all books that are currently available.
     *
     * @return a list of available books, empty if there are none
     */
    public List<Book> getAvailableBooks() {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.isAvailable()) {
                result.add(book);
            }
        }
        return result;
    }

    /**
     * Returns all books by the given author (exact, case-sensitive match),
     * including borrowed books.
     *
     * @param author the author to search for
     * @return a list of matching books, empty if there are none
     */
    public List<Book> getBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthor().equals(author)) {
                result.add(book);
            }
        }
        return result;
    }

    /**
     * Returns all books sorted by ISBN in ascending lexicographical order,
     * regardless of borrowing status. The library itself is not modified.
     *
     * @return a new sorted list of all books, empty if the library is empty
     */
    public List<Book> getBooksSortedByIsbn() {
        List<Book> result = new ArrayList<>(books.values());
        result.sort((a, b) -> a.getIsbn().compareTo(b.getIsbn()));
        return result;
    }

    /**
     * Returns the number of books currently borrowed from this library.
     *
     * @return the borrowed book count
     */
    public int getBorrowedBookCount() {
        return borrowedCount;
    }

    private Book requireBook(String isbn) {
        Book book = books.get(isbn);
        if (book == null) {
            throw new IllegalArgumentException("No book with ISBN " + isbn);
        }
        return book;
    }
}
