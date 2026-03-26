package com.example.librarysystem.entity.concrete;

import com.example.librarysystem.entity.enums.Category;
import java.util.*;

public class Library {
    private static final String LIBRARY_NAME = "Istanbul Central Library";
    private final Address address = new Address("İstanbul", 3434, "Kayışdağı");

    private final List<Book> books = new ArrayList<>();
    private final Set<Reader> readers = new HashSet<>();
    private final Set<Author> authors = new HashSet<>();
    private final Map<Reader, Set<Book>> storeData = new HashMap<>();
    private Librarian librarian;

    public Library() {}

    // Getters
    public List<Book> getBooks() { return Collections.unmodifiableList(books); }
    public Set<Reader> getReaders() { return Collections.unmodifiableSet(readers); }
    public Set<Author> getAuthors() { return Collections.unmodifiableSet(authors); }
    public Librarian getLibrarian() { return librarian; }
    public Address getAddress() { return address; }

    public void setLibrarian(Librarian librarian) { this.librarian = librarian; }

    // Operations
    public void addBook(Book book) {
        Objects.requireNonNull(book);
        if (!books.contains(book)) books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public void addReader(Reader reader) {
        readers.add(Objects.requireNonNull(reader));
    }

    public void removeReader(Reader reader) {
        readers.remove(reader);
    }

    public void addAuthor(Author author) {
        authors.add(Objects.requireNonNull(author));
    }

    public void removeAuthor(Author author) {
        authors.remove(author);
    }

    // Filter Methods
    public List<Book> filterByAuthor(Author author) {
        return books.stream()
                .filter(b -> Objects.equals(b.getAuthor(), author))
                .toList();
    }

    public List<Book> filterByCategory(Category category) {
        return books.stream()
                .filter(b -> b.getCategory() == category)
                .toList();
    }

    public void lendBook(Reader reader, Book book) {
        if (books.contains(book) && readers.contains(reader)) {
            storeData.computeIfAbsent(reader, k -> new HashSet<>()).add(book);
            reader.addBook(book);

            if (librarian != null) {
                System.out.println(librarian.createBill(reader, book));
            }

            books.remove(book);
        }
    }

    public void takeBook(Reader reader, Book book) {
        Set<Book> borrowed = storeData.get(reader);
        if (borrowed != null && borrowed.remove(book)) {
            reader.removeBook(book);
            books.add(book);
        }
    }
}