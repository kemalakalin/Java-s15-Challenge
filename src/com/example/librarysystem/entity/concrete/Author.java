package com.example.librarysystem.entity.concrete;

import com.example.librarysystem.entity.AbstractPerson;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Author extends AbstractPerson {
    private final Set<Book> books = new HashSet<>();

    public Author(Long id, String name) {
        super(id, name);
    }

    // Secondary constructor for detailed information
    public Author(Long id, String name, Address address, String phoneNumber, String email) {
        super(id, name, address, phoneNumber, email);
    }

    public Set<Book> getBooks() {
        return Collections.unmodifiableSet(books);
    }

    public void addBook(Book book) {
        Objects.requireNonNull(book, "Book argument cannot be null");
        if (book.getAuthor() != null && !book.getAuthor().equals(this)) {
            throw new IllegalArgumentException("This book is already assigned to another author");
        }
        this.books.add(book);
        if (book.getAuthor() == null) {
            book.setAuthor(this);
        }
    }

    public void removeBook(Book book) {
        Objects.requireNonNull(book, "Book argument cannot be null");
        if (this.books.remove(book)) {
            if (this.equals(book.getAuthor())) {
                book.setAuthor(null);
            }
        }
    }

    public void showBooks() {
        System.out.println(this.getName() + " has written:");
        for (Book book : books) {
            System.out.println("- " + book.getName());
        }
    }

    @Override
    public void whoYouAre() {
        System.out.println("I'm author " + getName());
    }

    @Override
    public String toString() {
        return "Author: " + getName() + " [Books written: " + books.size() + "]";
    }
}